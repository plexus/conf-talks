(ns dcd2026.talk
  (:require
   [overtone.live :refer :all]
   [dcd2026.support :refer :all]))

(stop)

(kbd 1 marimba)
(kbd 2 piano)
(kbd 3 electric-organ)
(kbd 4 sine-wave)
(kbd 5 detuned-sines)
(kbd-cc 5 detuned-sines {21 :detune})
(kbd 6 harmonics)
(kbd-cc 6 harmonics {21 :harmonics})

(demo (sin-osc 150))
(demo (var-saw 150))
(demo (var-saw 150 :width 1))
