(ns conj-overtone-2024
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

(o/defprop --red-violet "#b9077e")
(o/defprop --pink-lace "#fddef4")
(o/defprop --chateau-green "#1ea34c")
(o/defprop --mirage "#0c182e")
(o/defprop --arapawa "#01095E")
(o/defprop --emerald "#38D07B")
(o/defprop --kiwi "#07DF86")
(o/defprop --overtone-pink "#ff2f92")
(o/defprop --overtone-gray "#3c3a33")
(o/defprop --primary --emerald)
(o/defprop --secondary --arapawa)

(o/defrules styles
  [:html
   {:font-family "B612"
    :font-size "22pt"}]
  [:svg {:font-size "12pt"}]
  [:.reveal-viewport
   {:color --arapawa}]
  [:pre {:text-align "left"
         :line-height "1.5em"
         :font-size "18pt"
         :background-color "#D2CEC8"}]
  [#{:h1 :h2 :h3 :h4 :h5}
   {:font-family "'Cabin'"
    :font-weight 400
    }]
  [:h2 {    :line-height "1.5em"}]
  [:h1 {:font-size "4rem"}]
  [:img {:max-width "100%"}]
  [#{:ol :ul} {:text-align "left"}]
  [:li {:margin-top "0.25em"
        :margin-bottom "0.5em"}]
  [:strong {:color --overtone-pink}]
  [:blockquote
   {:text-align "justify"
    :font-style "italic"
    :border-left (str "6px solid " --overtone-pink)
    :padding "0.1em 1em"
    :line-height "1.5em"
    :background-color "#F8FEFE"}]
  [".slide:has(#title)"
   {:height "100%"}
   [:h1 {:margin-top "23%"
         :font-weight 700}]
   [:p {:font-size "15pt"

        }]
   [:.location {:position "absolute"
                :bottom "0"
                :left "0"}]
   [:.me {:position "absolute"
          :bottom "0"
          :right "0"}]]
  [:.img-small [:img {:max-height "500px"}]]
  [:.scroll [:code {:overflow "auto"
                    :height "650px"}]])

(defn load-slide-html []
  (let [slides (-> "slides.md"
                   slurp-slides
                   inline-svgs
                   split-slides)]
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
                 [:link {:rel "stylesheet" :href "/fonts/ostrich-sans/ostrich-sans.css"}]
                 [:link {:rel "stylesheet" :href "/fonts/cabin/cabin.css"}]
                 [:link {:rel "stylesheet" :href "/fonts/b612/b612.css"}]
                 [:link {:rel "stylesheet" :href "/revealjs/reveal.css"}]
                 #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/monokai.css"}]
                 #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/zenburn.css"}]
                 [:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/solarized-light.css"}]
                 [:style (o/defined-styles)]
                 ]
                [:body
                 [:div.reveal
                  [:div.slides]]
                 [:script {:src "/revealjs/reveal.js"}]
                 [:script {:src "/revealjs/plugin/highlight/highlight.js"}]
                 [:script {:src "/highlight-supercollider.js"}]
                 [:script "Reveal.initialize({controls: false, transition: 'none', plugins: [RevealHighlight],
highlight: {
    beforeHighlight: hljs => hljsDefineSuperCollider(hljs)
  }
})"]]])
     [(enlive/attr-has "class" "slides")] (enlive/append (load-slide-html) ))
   ))

(defn handler []
  (let [dirs (map #(bb-server/file-router % nil)
                  ["./" "./out" "./resources"])]
    (fn [{:keys [uri] :as req}]
      (if (#{"/" "/index.html"} uri)
        {:status 200
         :headers {"content-type" "text/html"}
         :body (index)}
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

(defonce server
  (server/run-server (fn [req] ((handler) req)) {:port 7070}))
