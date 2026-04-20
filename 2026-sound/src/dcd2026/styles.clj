(ns dcd2026.styles
  (:require
   [lambdaisland.ornament :as o]
   [dcd2026.styles.tokens :refer :all :as t]))

(o/defprop --eu-blue "#039")

(o/defrules styles
  [:html
   {:font-family "Raleway"
    :font-size "22pt"}]
  [:video {:width "100%"}]
  ;; [:svg {:font-size "12pt"}]
  ;; [:.reveal-viewport
  ;;  {:color --arapawa
  ;;   :line-height "inherit"}]
  ;; [:.hljs {:background-color --yellow-0
  ;;          :color --gray-11}]
  ;; [:pre {:text-align "left"
  ;;        :line-height "1.5em"
  ;;        :font-size "18pt"
  ;;        }]
  ;; [#{:h1 :h2 :h3 :h4 :h5}
  ;;  {:font-family "'Cabin'"
  ;;   :font-weight 700}]
  ;; [:section>h2:first-child {:margin-top 0}]
  ;; [:h2 {:font-size "2.5rem"
  ;;       :line-height "1.2em"}]
  ;; [:h1 {:font-size "4rem"}]
  ;; [:img {:max-width "100%"}]
  ;; [#{:ol :ul} {:text-align "left"}]
  ;; [:li {:margin-top "0.25em"
  ;;       :margin-bottom "0.5em"}]
  ;; [:strong {:color --primary}]
  ;; [:blockquote
  ;;  {:text-align "justify"
  ;;   :font-style "italic"
  ;;   :border-left (str "6px solid " --oak-green-4)
  ;;   :padding "0.1em 1em"
  ;;   :line-height "1.5em"
  ;;   :background-color --oak-green-2}]

  ;; [:.cols
  ;;  {:display :flex
  ;;   }]

  ;; [".slide:has(#title)"
  ;;  {:height "100%"}
  ;;  [:h1 {:margin-top "23%"
  ;;        :font-weight 700}]
  ;;  [:p {:font-size "15pt"}]
  ;;  [:.location {:position "absolute"
  ;;               :bottom "0"
  ;;               :left "0"}]
  ;;  [:.me {:position "absolute"
  ;;         :bottom "0"
  ;;         :right "0"}]]

  ;; [:.img-small [:img {:max-height "500px"}]]
  ;; [:.scroll [:code {:overflow "auto"
  ;;                   :height "650px"}]]

  ;; [:code {:background-color --yellow-0
  ;;         :color --oak-green-13
  ;;         :font-family "Iosevka Fixed SS14"
  ;;         :line-height "1.3em"
  ;;         :padding "0.1em"}]
  ;; [:#team-imgs
  ;;  {:display :flex
  ;;   :flex-direction :row
  ;;   :height "50vh"}
  ;;  [:>p {:height "100%"
  ;;        :overflow "hidden"
  ;;        :flex-basis "20%"
  ;;        :flex-shrink 0
  ;;        :flex-grow 0}
  ;;   [:img {:height "80%"
  ;;          :object-fit :cover
  ;;          :object-position :center}]]]

  ;; [:.left {:text-align "left"}]
  ;; [:.nobullets {:list-style-type "none"}]

  ;; [:.file-tree
  ;;  [:p
  ;;   {:white-space "pre"
  ;;    :text-align "left"
  ;;    :line-height "1.25em"
  ;;    :font-size "18pt"
  ;;    :background-color --yellow-0
  ;;    :position "relative"
  ;;    :padding "1em"}]]
  )
