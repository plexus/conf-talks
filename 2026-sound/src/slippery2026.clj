(ns slippery2026
  (:require
   [babashka.http-server :as bb-server]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [lambdaisland.hiccup :as hiccup]
   [lambdaisland.kramdown :as kramdown]
   [lambdaisland.ornament :as o]
   [lambdaisland.uri :as uri]
   [net.cgrand.enlive-html :as enlive]
   [org.httpkit.server :as server])
  (:import
   (java.io StringReader)))

(defn slurp-slides [filename]
  (-> filename
      slurp
      kramdown/parse-gfm
      StringReader.
      enlive/html-resource
      (enlive/select [:body])
      first
      :content))

(defn split-slides [nodes]
  (reduce
   (fn [acc el]
     (if (= :hr (:tag el))
       (conj acc [])
       (update acc (dec (count acc)) conj el)))
   [[]]
   nodes))

(defn inline-svgs [nodes]
  (-> nodes
      (enlive/at [(enlive/attr= :data-inline "true") :img]
                 (fn [{:keys [attrs]}]
                   (-> attrs
                       :src
                       io/resource
                       slurp
                       StringReader.
                       enlive/html-resource)))
      (enlive/at [:svg]
                 (fn [tag]
                   (update tag :attrs dissoc :width :height)))))

(defn load-slide-html [file]
  (let [slides (-> file
                   slurp-slides
                   inline-svgs
                   split-slides)]
    (for [nodes slides]
      {:tag :section
       :attrs {:class "slide"}
       :content nodes})))

(defn render-index* [{:keys [template slides-file transform]
                      :or {transform identity}}]
  (enlive/at (hiccup/html (template))
    [(enlive/attr-has "class" "slides")]
    (enlive/append (transform (load-slide-html slides-file)))))

(defn render-index [opts]
  (hiccup/render-html (render-index* opts)))

(defn handler [opts]
  (let [dirs (map #(#'bb-server/file-router % nil)
                  ["./" "./out" "./resources" "./src"])]
    (fn [{:keys [uri request-method] :as req}]
      (println " " (str/upper-case (name request-method)) uri)
      (cond
        (#{"/styles.css"} uri)
        {:status 200
         :headers {"content-type" "text/css"}
         :body (o/defined-styles)}

        (#{"/" "/index.html"} uri)
        {:status 200
         :headers {"content-type" "text/html"}
         :body (render-index opts)}

        :else
        (let [req (update req :headers dissoc "range")]
          (some (fn [d]
                  (let [res (d req)]
                    (when (not= 404 (:status res))
                      #_(let [uri (if (= "/" uri) "/index.html" uri)
                              target  (io/file "out" (subs uri 1))]
                          (.mkdirs (io/file (.getParent target)))
                          (spit target (slurp (:body res))))
                      res)))
                dirs))))))

(defn run-server [opts]
  (let [port (:port opts 7070)]
    (println "Starting Slippery server on port" port)
    (server/run-server (fn [req] ((handler opts) req)) {:port port})))

(defn url->bytes [url-str]
  (let [baos (java.io.ByteArrayOutputStream.)]
    (with-open [in (clojure.java.io/input-stream (java.net.URL. url-str))]
      (clojure.java.io/copy in baos))
    (.toByteArray baos)))

(defn file->bytes [^java.io.File file]
  (java.nio.file.Files/readAllBytes (.toPath file)))

(defn to-bytes [s]
  (cond
    (instance? java.io.File s)
    (file->bytes s)
    (string? s)
    (.getBytes s "UTF-8")
    :else
    s))

(defn to-string [o]
  (cond
    (string? o)
    o

    (instance? java.io.File o)
    (slurp o)

    (bytes? o)
    (String. o "UTF-8")

    :else
    (throw (ex-info "Can't convert to string" {:o o :class (class o)}))))

(defn sha256 [s]
  (let [bytes (to-bytes s)
        digest (java.security.MessageDigest/getInstance "SHA-256")
        hash-bytes (.digest digest bytes)]
    (apply str (map #(format "%02x" %) hash-bytes))))

(defn resolve-link [{:keys [handler base]} url]
  (if (:host (uri/uri url))
    (url->bytes url)
    (to-bytes (:body (handler {:request-method :get
                               :uri (str (uri/join base url))})))))

(declare fetch-and-hash)

(defmulti munge-content (fn [opts ext body] (str/lower-case ext)))

(def plain-data-exts #{".woff2" ".mp4" ".png" ".jpg" ".jpeg"})

(defmethod munge-content :default [_ ext body]
  (when-not (plain-data-exts ext)
    (println "WARN: No munge handler for" ext))
  body)

(defmethod munge-content ".css" [opts ext body]
  (-> body
      to-string
      (str/replace #"url\('([^']*)'\)"
                   (fn [[_ url]]
                     (str "url('" (fetch-and-hash opts url) "')")))
      (str/replace #"url\(\"([^\"]*)\"\)"
                   (fn [[_ url]]
                     (str "url('" (fetch-and-hash opts url) "')")))
      (str/replace #"url\(([^'\"]*)\)"
                   (fn [[_ url]]
                     (str "url('" (fetch-and-hash opts url) "')")))
      (str/replace #"@import \"([^\"])\""
                   (fn [[_ url]]
                     (str "@import \"" (fetch-and-hash opts url) "\"")))))

(defmethod munge-content ".js" [opts ext body]
  (-> body
      to-string
      (str/replace #"(fetch|import)\('([^']*)'\)"
                   (fn [[_ fn url]]
                     (str fn "('" (fetch-and-hash opts url) "')")))
      (str/replace #"(fetch|import)\(\"([^\"]*)\"\)"
                   (fn [[_ fn url]]
                     (str fn "('" (fetch-and-hash opts url) "')")))))

(defn fetch-and-hash [{:keys [out-dir base] :as opts} url]
  (if (= "data" (:scheme (uri/uri url)))
    url
    (let [_       (println "Building" url)
          ext     (re-find #"\.[^\.]+$" url)
          content (resolve-link opts url)]
      (if content
        (let [content (munge-content (update opts :base uri/join url) ext content)
              sha     (sha256 content)]
          (with-open [out (io/output-stream (io/file out-dir (str sha ext)))]
            (io/copy content out))
          (str sha ext))
        (do
          (println "WARN: could note resolve" url "from" base)
          url)))))

(defn build [{:keys [out-dir] :as opts}]
  (.mkdirs (io/file out-dir))
  (let [index (render-index* opts)
        opts (assoc opts
                    :handler (handler opts)
                    :base "/")]
    (spit (io/file out-dir "index.html")
          (hiccup/render-html
           (enlive/at index #{[:link] [:script] [:img] [:section] [:source]}
                      (fn [{:keys [attrs] :as el}]
                        (reduce
                         (fn [el attr]
                           (if-let [url (or (get-in el [:attrs attr]) (get-in el [:attrs (name attr)]))]
                             (update el :attrs #(-> % (dissoc attr (name attr))
                                                    (assoc attr (fetch-and-hash opts url))))
                             el))
                         el
                         [:src :href :data-background-image])))))))
