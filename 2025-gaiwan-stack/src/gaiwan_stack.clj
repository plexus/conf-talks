(ns gaiwan-stack
  (:require
   [charred.api :as charred]
   [clojure.walk :as walk]
   [net.cgrand.enlive-html :as enlive]
   [slippery2025]))

(require 'styles)

(def slides-file "slides.md")
(def title "The Gaiwan Stack")

(defn reveal-opts []
  {:controls false
   :transition "none"
   :hash true})

(defn template []
  [:html
   [:head
    [:title title]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "/fonts/ostrich-sans/ostrich-sans.css"}]
    [:link {:rel "stylesheet" :href "/fonts/cabin/cabin.css"}]
    [:link {:rel "stylesheet" :href "/fonts/b612/b612.css"}]
    [:link {:rel "stylesheet" :href "/revealjs/reveal.css"}]
    #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/monokai.css"}]
    #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/zenburn.css"}]
    [:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/solarized-light.css"}]
    [:link {:id "ornament-styles" :rel "stylesheet" :href "/styles.css"}]]
   [:body
    [:div.reveal
     [:div.slides]]
    [:script {:src "/revealjs/reveal.js"}]
    [:script {:src "/revealjs/plugin/highlight/highlight.js"}]
    [:script {:src "/reload_styles.js"}]
    [:script "window.reveal_opts = {plugins: [RevealHighlight], ..." (charred/write-json-str (reveal-opts)) "}
Reveal.initialize(window.reveal_opts)"]]])


(defn transform-slides [slides]
  (def slides slides)
  (enlive/at slides
    [[:.slide (enlive/has [:.explode-slides])]]
    (fn [slide]
      (for [content (next (reductions
                           conj
                           []
                           (filter map?
                                   (:content
                                    (first
                                     (enlive/select slide [:.explode-slides]))))))]
        (enlive/at slide
          [:.explode-slides]
          (enlive/content content))))))

(defonce server
  (slippery2025/run-server {:template #'template
                            :slides-file slides-file
                            :transform #'transform-slides}))
(defn stop! []
  (server)
  (ns-unmap *ns* 'server))

(comment (stop!))
