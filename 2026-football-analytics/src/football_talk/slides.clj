(ns football-talk.slides
  (:require
   [charred.api :as charred]
   [clojure.java.io :as io]
   [lambdaisland.ornament :as o]
   [net.cgrand.enlive-html :as enlive]
   [slippery2026 :as slippery]))

(def title "Football Analytics")
(def date "2026-10-22")
(def slides-file "slides.md")

(o/defprop --milk "#f6f6f3")
(o/defprop --cheesecake "#f3f2ee")
(o/defprop --mustard "#e1c451")
(o/defprop --egg-yolk "#d1ab13")
(o/defprop --aubergine "#73005c")
(o/defprop --chocolate "#3b0221")
(o/defprop --hot-pink "#f51e92")
(o/defprop --candy-floss "#cd4a92")
(o/defprop --bubblegum "#eab3d0")

(o/defrules styles
  [:* {:box-sizing "border-box"}]
  [:html {:font-size "100%"}]
  [:body {:font-size "2vw"}]
  [#{:button :input} {:font-size "0.75rem" :padding "0.125rem 0.5rem" :margin-bottom "0.5rem"}]
  [:.reveal
   [:.slide-background {:background-color --cheesecake}]
   [:.slides
    [:section
     {:display "flex"
      :text-align "center"
      :align-content "center"
      :width "100vw"
      :height "100vh"
      :padding "1rem 4rem"
      :counter-reset "index sibling-index() total sibling-count()"}
     ["&:not(:first-child)::after"
      {:position "absolute"
       :bottom "0.5rem"
       :right "0.5rem"
       :content (str  "\"" title " — Arne Brasseur — " date " — [\"counter(index) \" / \" counter(total) \"]\"")
       :font-size "2rem"
       :color --aubergine
       :font-family "sans-serif"}]
     [#{:pre :ul :ol} {:text-align "left"}]]]]
  [:.bottom-right
   {:position "absolute"
    :bottom "1rem"
    :right "2rem"}]
  ["li" {:margin "0.75em 0"}]
  ;; [:.large-svg [:svg {:transform "scale(1.3) translate(0,7%)"}]]
  [:h1 {:font-size "4rem"
        :text-align "center"}]
  [#{:h1 :h2 :h3 :h4 :h5}
   {:font-variant-caps "small-caps"}]
  [:img {:max-width "90%"
         :min-height "60vh"
         :object-fit "contain"
         :max-height "70vh"}
   ])

(defn reveal-opts []
  {:controls false
   :transition "none"
   :hash true
   :disableLayout true})

(defn template []
  [:html
   [:head
    [:title title]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "revealjs/reveal.css"}]
    ;; [:link {:rel "stylesheet" :href "revealjs/plugin/highlight/zenburn.css"}]
    [:link {:rel "stylesheet" :href "revealjs/plugin/highlight/monokai.css"}]
    ;; [:link {:rel "stylesheet" :href "revealjs/plugin/highlight/solarized-light.css"}]
    [:link {:id "ornament-styles" :rel "stylesheet" :href "styles.css"}]]
   [:body
    [:div.reveal
     [:div.slides]]
    [:script {:src "revealjs/reveal.js"}]
    [:script {:src "reload_styles.js"}]
    [:script {:src "revealjs/plugin/highlight.js"}]
    [:script {:src "revealjs/plugin/notes.js"}]
    [:script "window.reveal_opts = {plugins: [RevealHighlight, RevealNotes], ..." (charred/write-json-str (reveal-opts)) "}
    Reveal.initialize(window.reveal_opts)"]
    ]])

(defn transform-slides [slides]
  (def slides slides)
  (enlive/at slides
             [[:.slide (enlive/has [(enlive/attr? :data-include-svg-slides)])]]
             (fn [slide]
               (let [svg (some #(when (= :svg (:tag %))
                                  %)
                               (enlive/get-resource
                                (io/file
                                 (first
                                  (enlive/attr-values
                                   (first (enlive/select slide [(enlive/attr? :data-include-svg-slides)]))
                                   :data-include-svg-slides)))
                                (:parser enlive/*options*)))
                     svg (update svg :attrs dissoc :width :height)]
                 (for [{:keys! [attrs]} (enlive/select svg [:inkscape:page])]
                   (let [{:keys! [x y width height]} attrs]
                     (-> slide
                         (update-in [:attrs :class] #(str % " large-svg"))
                         (assoc :content [(assoc-in svg [:attrs :viewbox] (str x " " y " " width " " height))]))))))))

(def slippery-opts
  {:out-dir "build"
   :template #'template
   :slides-file slides-file
   :transform #'transform-slides})

(defn serve []
  (defonce server
    (slippery/run-server slippery-opts)))

(comment
  (defn stop! []
    (server)
    (ns-unmap *ns* 'server))

  (stop!)

  (slippery/build slippery-opts)
  )
