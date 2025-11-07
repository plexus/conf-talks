(ns user
  (:require [clojure.java.browse :as browse]))

(defn browse []
  (browse/browse-url "http://localhost:7070"))
