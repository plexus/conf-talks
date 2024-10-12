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
(o/defprop --kiwi "#07DF86")
(o/defprop --overtone-pink "#ff2f92")
(o/defprop --overtone-gray "#3c3a33")
(o/defprop --primary --emerald)
(o/defprop --secondary --arapawa)

(o/defrules styles
  [:html
   {:font-family "'Ostrich Sans'"
    :font-size "22pt"
    :font-weight 800}]
  [:.reveal-viewport
   {:color --arapawa}]
  [:pre {:text-align "left"
         :line-height "1.5em"}]
  [#{:h1 :h2 :h3 :h4 :h5}
   {:font-family "'Cabin'"
    :font-weight 400}]
  [:h1 {:font-size "4rem"}]
  [:img {:max-width "100%"}]
  [":has(#title)"
   {:height "100%"}
   [:h1 {:margin-top "23%"
         :font-weight 700}]
   [:strong {:color --overtone-pink}]
   [:p {:font-size "15pt"}]
   [:.location {:position "absolute"
                :bottom "0"
                :left "0"}]
   [:.me {:position "absolute"
          :bottom "0"
          :right "0"}]
   ]
  )

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
                 [:link {:rel "stylesheet" :href "/fonts/ostrich-sans/ostrich-sans.css"}]
                 [:link {:rel "stylesheet" :href "/fonts/cabin/cabin.css"}]
                 [:link {:rel "stylesheet" :href "/revealjs/reveal.css"}]
                 [:style (o/defined-styles)]
                 ]
                [:body
                 [:div.reveal
                  [:div.slides]]
                 [:script {:src "/revealjs/reveal.js"}]
                 [:script "Reveal.initialize({controls: false, transition: 'none'})"]]])
     [(enlive/attr-has "class" "slides")] (enlive/append (load-slide-html) ))
   ))


(defn handler []
  (let [dirs (map #(bb-server/file-router % nil)
                  ["./" "./out" "./resources"])]
    (fn [{:keys [uri] :as req}]
      (prn req)
      (if (#{"/" "/index.html"} uri)
        {:status 200
         :headers {"content-type" "text/html"}
         :body (index)}
        (some (fn [d]
                (let [res (d req)]
                  (when (not= 404 (:status res))
                    res)))
              dirs)))))

(defonce server
  (server/run-server (fn [req] ((handler) req)) {:port 7070}))
