(ns dcd2026.talk
  (:require
   [overtone.live :refer :all]
   [dcd2026.support :refer :all]))

(kbd 1 marimba)
(kbd 2 piano)
(kbd 3 electric-organ)

(demo (sin-osc 150))
(demo (var-saw 150))
(demo (var-saw 150 :width 1))
