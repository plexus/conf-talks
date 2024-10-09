(ns conj-overtone-2024
  (:require
   [babashka.http-server :as bb-server]
   [lambdaisland.hiccup :as hiccup]
   [lambdaisland.ornament :as o]
   [lambdaisland.kramdown :as kramdown]
   [lambdaisland.xml-select :as xs]
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


(o/defprop --red-violet "#b9077e")
(o/defprop --pink-lace "#fddef4")
(o/defprop --chateau-green "#1ea34c")
(o/defprop --mirage "#0c182e")
(o/defprop --arapawa "#01095E")
(o/defprop --emerald "#38D07B")

(o/defprop --primary --emerald)
(o/defprop --secondary --arapawa)

(o/defrules styles
  [:html
   {:font-size "22pt"}]
  [:body
   {:background-color --secondary
    :color --primary
    :max-width "48em"
    :margin "0 auto"}]
  [#{:h1 :h2 :h3 :h4 :h5}
   {:font-family "'Ostrich Sans'"
    :font-weight "400"}]
  [:h1 {:font-size "3rem"}])

(defn load-slide-html []
  (let [nodes (slurp-slides "slides.md")
        slides (split-slides nodes)]
    (for [nodes slides]
      {:tag :section
       :attrs {:class "slide"}
       :content nodes})))

(defn index []
  (hiccup/render-html
   (enlive/at (hiccup/html
               [:html
                [:head
                 [:title "The Next 10 Years of Overtone"]
                 [:meta {:charset "UTF-8"}]
                 [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
                 [:style (o/defined-styles)]
                 [:link {:rel "stylesheet" :href "assets/fonts/ostrich-sans/ostrich-sans.css"}]
                 [:link {:rel "stylesheet" :href "/reveal.css"}]]
                [:body
                 [:div.reveal
                  [:div.slides]]
                 [:script {:src "/reveal.js"}]
                 [:script "Reveal.initialize({})"]]])
     [(enlive/attr-has "class" "slides")] (enlive/append (load-slide-html) ))
   ))


(defn handler []
  (let [out (bb-server/file-router "out" nil)
        rev (bb-server/file-router "resources/revealjs" nil)]
    (fn [{:keys [uri] :as req}]
      (if (#{"/" "/index.html"} uri)
        {:status 200
         :headers {"content-type" "text/html"}
         :body (index)}
        (let [res (out req)]
          (if (= 404 (:status res))
            (rev req)
            res))))))

(defonce server
  (server/run-server (fn [req] ((handler) req)) {:port 7070}))
