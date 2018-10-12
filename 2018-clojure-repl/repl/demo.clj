(ns repl.demo)



(* 100
   (+ 1 1)
   )

(keys {:foo :bar})
;;=> (:foo)

;;(clj-slack.user/list conn)

(->> [{:name "arne" :id 100 :handle "plexus"}]
     first
     keys
     (map name)
     (map symbol))

{:keys [name id handle]}

(require '[sc.api :as sc])

(defn do-the-thing [a b c]
  (* a
     (sc/spy
      (+ b c))))


(is (= (do-the-thing 5 6 7)
       65))

(do-the-thing 8 40 3)
344

(sc/letsc 3 (+ b c))

(sc/defsc 3)

a
;;=> 5
b
;;=> 6
c
;;=> 7
