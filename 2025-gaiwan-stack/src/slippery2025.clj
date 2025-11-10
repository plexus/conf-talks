(ns slippery2025
  (:require
   [babashka.http-server :as bb-server]
   [clojure.java.io :as io]
   [lambdaisland.hiccup :as hiccup]
   [lambdaisland.kramdown :as kramdown]
   [lambdaisland.ornament :as o]
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

(defn render-index [{:keys [template slides-file]}]
  (hiccup/render-html
   (enlive/at (hiccup/html (template))
     [(enlive/attr-has "class" "slides")] (enlive/append (load-slide-html slides-file) ))))

(defn handler [opts]
  (let [dirs (map #(#'bb-server/file-router % nil)
                  ["./" "./out" "./resources"])]
    (fn [{:keys [uri] :as req}]
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
  (server/run-server (fn [req] ((handler opts) req)) {:port (:port opts 7070)}))
