(ns user)

(defn browse []
  ((requiring-resolve 'clojure.java.browse/browse-url) "http://localhost:7070"))

(defn go []
  ((requiring-resolve 'datastar-talk.slides/serve))
  (browse))
