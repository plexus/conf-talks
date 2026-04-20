(ns dcd2026.talk
  (:require
   [overtone.live :refer :all]
   [dcd2026.support :refer :all]))

(stop)

(kbd 1 marimba)
(kbd 2 piano)
(kbd 3 electric-organ)
(kbd 4 sine-wave)

(demo (sin-osc 150))
(demo (var-saw 150))
(demo (var-saw 150 :width 1))
