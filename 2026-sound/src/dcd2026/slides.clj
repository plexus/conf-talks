(ns dcd2026.slides
  (:require
   [charred.api :as charred]
   [clojure.walk :as walk]
   [net.cgrand.enlive-html :as enlive]
   [slippery2026 :as slippery]))

(require 'dcd2026.styles)

(def slides-file "slides.md")
(def title "Sound, Music, Synthesis")

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
    [:link {:rel "stylesheet" :href "fonts/raleway.css"}]
    [:link {:rel "stylesheet" :href "fonts/playfair.css"}]
    [:link {:rel "stylesheet" :href "revealjs/reveal.css"}]
    #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/monokai.css"}]
    #_[:link {:rel "stylesheet" :href "/revealjs/plugin/highlight/zenburn.css"}]
    [:link {:rel "stylesheet" :href "revealjs/plugin/highlight/solarized-light.css"}]
    [:link {:id "ornament-styles" :rel "stylesheet" :href "styles.css"}]]
   [:body
    [:div.reveal
     [:div.slides]]
    [:script {:src "revealjs/reveal.js"}]
    [:script {:src "revealjs/plugin/highlight/highlight.js"}]
    [:script {:src "https://cdn.jsdelivr.net/npm/p2@0.7.1/build/p2.min.js"}]
    [:script {:src "https://cdn.jsdelivr.net/npm/scittle@0.8.31/dist/scittle.js"}]
    [:script {:src "reload_styles.js"}]
    [:script "window.reveal_opts = {plugins: [RevealHighlight], ..." (charred/write-json-str (reveal-opts)) "}
Reveal.initialize(window.reveal_opts)"]]])

(defn transform-slides [slides]
  (def slides slides)
  (-> slides
      (enlive/at
          [[:.slide (enlive/has [:.fullscreen])]]
        (fn [slide]
          (-> slide
              (enlive/at [:section] (enlive/set-attr :data-background-image (some (comp :src :attrs) (enlive/select slide [:img]))))
              (enlive/at [:.fullscreen] nil))))
      (enlive/at
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
              (enlive/content content)))))))

(def slippery-opts
  {:out-dir "build"
   :template #'template
   :slides-file slides-file
   :transform #'transform-slides})

(comment
  (defonce server
    (slippery/run-server slippery-opts))

  (defn stop! []
    (server)
    (ns-unmap *ns* 'server)))

(comment (stop!))
