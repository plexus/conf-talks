(ns gaiwan-stack
  (:require
   [lambdaisland.ornament :as o]
   [slippery2025]))

(def slides-file "slides.md")
(def title "The Gaiwan Stack")

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

  [:.cols
   {:display :flex
    }]

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
    [:style (o/defined-styles)]
    ]
   [:body
    [:div.reveal
     [:div.slides]]
    [:script {:src "/revealjs/reveal.js"}]
    [:script {:src "/revealjs/plugin/highlight/highlight.js"}]
    [:script "Reveal.initialize({controls: false, transition: 'none', plugins: [RevealHighlight]})"]]])

(defonce server
  (slippery2025/run-server {:template #'template
                            :slides-file slides-file}))
