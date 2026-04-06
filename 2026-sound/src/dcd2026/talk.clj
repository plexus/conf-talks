(ns dcd2026.talk
  (:require
   [overtone.live :refer :all]
   [dcd2026.support :refer :all]))

(kbd  nil)
(kbd 1 nil)
(kbd 1 electric-organ)

(demo (sin-osc))
(demo (var-saw 250))
(demo (var-saw 250 :width 1))

(demo
  (mix
   (let [freq 250]
     (for [i (range 1 80)]
       (* 50 (/ 1 (* i i))
          (sin-osc (* freq i)))))))


(init!)
