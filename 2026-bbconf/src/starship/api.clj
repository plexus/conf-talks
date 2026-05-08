(ns starship.api
  (:require
   [muuntaja.core :as muuntaja]
   [muuntaja.format.charred :as charred-format]
   [reitit.ring :as ring]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :as ring-defaults]))

(defn routes []
  [["/starship"
    {:post {:handler (fn [req] (println "CREATE NEW STARSHIP" req))}}]
   ["/health"
    {:name :health
     :get {:handler (fn [_] {:body "OK"})}}]])

(defn handler []
  (ring/ring-handler
   (ring/router
    (routes)
    {:muuntaja
     (muuntaja/create
      (-> muuntaja/default-options
          (muuntaja/install charred-format/format "application/json")))
     :middleware [;; ↓↓↓ request passes through middleware top-to-bottom ↓↓↓
                  reitit-parameters/parameters-middleware
                  reitit-muuntaja/format-negotiate-middleware
                  reitit-muuntaja/format-response-middleware
                  reitit-muuntaja/format-request-middleware
                  reitit-coercion/coerce-response-middleware
                  reitit-coercion/coerce-request-middleware
                  reitit-parameters/parameters-middleware
                  reitit-muuntaja/format-negotiate-middleware
                  reitit-muuntaja/format-response-middleware
                  reitit-muuntaja/format-request-middleware
                  reitit-coercion/coerce-response-middleware
                  reitit-coercion/coerce-request-middleware
                  ;; ↑↑↑ response passes through middleware bottom-to-top ↑↑↑
                  ]})
   nil
   {:middleware [;; vvvvvvv  Request goes down
                 [ring-defaults/wrap-defaults ring-defaults/api-defaults]
                 ;; ^^^^^^^  Response goes up
                 ]}))

(def jetty
  (jetty/run-jetty
   (handler)
   {:port 1234
    :join? false}))
