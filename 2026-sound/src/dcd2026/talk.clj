(ns dcd2026.talk
  (:require
   [overtone.live :refer :all]
   [dcd2026.support :refer :all]))

(stop)
(init!)
(do
  (kbd 1 marimba)
  (kbd 2 piano)
  (kbd 3 electric-organ)
  (kbd 4 sine-wave)
  (kbd 5 trumpet)
  (kbd 6 detuned-sines)
  (kbd-cc 6 detuned-sines {21 :detune})
  (kbd 7 harmonics)
  (kbd-cc 7 harmonics {21 :harmonics})
  (kbd 8 detuned-saws)
  (kbd-cc 8 detuned-saws {21 :detune})
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; UGens

(demo (sin-osc 300))
(demo (var-saw 300))
(demo (var-saw 300 :width 1))
(demo (square 300))

;; https://wiretext.app/w/J1_3hg1v


;;---------------------------------------------------

;; ┌───────────┐     ┌─────────┐     ┌─────┐
;; │  sin-osc  │────→│  rlpf   │────→│ out │
;; └───────────┘     └─────────┘     └─────┘

(demo (rlpf (square 300) 900))


;;---------------------------------------------------


;;  ┌────────┐
;;  │  line  │
;;  └────────┘
;;       C
;;       ↓ freq
;; ┌───────────┐        ┌─────┐
;; │  sin-osc  │───────→│ out │
;; └───────────┘        └─────┘

(demo (sin-osc (line 200 400 1.0)))


;;---------------------------------------------------


;;  ┌─────────┐        ┌───┐      ┌─────┐
;;  │ var-saw │───────→│ * │─────→│ out │
;;  └─────────┘        └───┘      └─────┘
;;                       ↑
;;                       C
;;                       │
;;                ┌────────────┐
;;                │    line    │
;;                └────────────┘

(demo
  (* (line 0 1 1.0)
     (var-saw)))

;;---------------------------------------------------


;;  ┌─────────┐        ┌───┐      ┌─────┐
;;  │ var-saw │───────→│ * │─────→│ out │
;;  └─────────┘        └───┘      └─────┘
;;                       ↑
;;                       C
;;                       │
;;                 ┌───────────┐
;;                 │  env-gen  │
;;                 └───────────┘

(demo
  (* (env-gen (perc))
     (var-saw)))


;;---------------------------------------------------


;; ┌───────────────┐        ┌───┐         ┌─────┐
;; │ (sin-osc 220) │───────→│ + │────────→│ out │
;; └───────────────┘        └───┘         └─────┘
;;                            ↑
;;                            │
;; ┌────────────────┐    ┌─────────┐
;; │ (sin-osc 440)  │───→│ (* 1/2) │
;; └────────────────┘    └─────────┘

(demo
  (+
   (sin-osc 220)
   (* 1/2 (sin-osc 440))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Additive Synthesis

(demo
  (sin-osc 440))

(demo
  (let [freq 440]
    (* (env-gen (perc))
       (sin-osc freq))))

(demo
  (let [freq 440]
    (* (env-gen (perc))
       (+ (sin-osc freq)
          (* 1/2 (sin-osc (* 2 freq)))
          #_(* 1/3 (sin-osc (* 3 freq)))
          (* 1/3 (sin-osc (* 3.1 freq)))))))

;;---------------------------------------------------
;; Kalimba / Mbira

;; https://freesound.org/people/scissorwork/sounds/625192/
;; https://en.wikipedia.org/wiki/Mbira#/media/File:Mbira_dzavadzimu_1.jpg
((freesound 625192))
(midi->hz 72) ;; C5

(/ 3430 523.0)
(/ 2900 523.0)

(demo
  (let [freq 523]
    (* (env-gen (perc))
       (+ (sin-osc freq)
          (* 0.3 (sin-osc (* 6.55 freq)))
          #_(* 0.05 (sin-osc (* 5.5 freq)))
          #_(* 0.03 (sin-osc (* 8.2 freq)))))))


(demo
  (let [freq 523]
    (+
     (* (sin-osc freq)
        (env-gen (perc 0.01 1.0)))
     (* 0.10
        (env-gen (perc 0.01 0.2))
        (sin-osc (* 5.5 freq)))
     (* 0.3
        (env-gen (perc 0.005 0.5))
        (sin-osc (* 6.55 freq))))))

#_(* (env-gen (perc 0.001 0.03)) (white-noise))

;;---------------------------------------------------





;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Subtractive Synthesis

(demo (var-saw :width 0))
(demo (square))

;;---------------------------------------------------

(demo
  (let [freq 440]
    (rlpf (var-saw freq :width 0)
          (* 5 freq (mouse-y))
          #_0.1)))

(demo
  (let [freq 440]
    (rlpf (square freq :width 0)
          (* 5 freq (mouse-y))
          #_0.1)))

;;---------------------------------------------------

(definst template [freq 440 amp 1 gate 1]
  (let [osc    (var-saw freq :width 1)
        env    (env-gen (adsr) :gate gate :action FREE)
        cutoff (* 5 freq)]
    (rlpf (* amp env osc) cutoff)))

(kbd 16 template)

(definst trumpet [freq 440 amp 1 gate 1]
  (let [osc    (var-saw freq :width 1)
        env    (env-gen (adsr 0.01 0.1 0.7 0.15) :gate gate :action FREE)
        cutoff (lin-lin (env-gen (adsr 0.05 0.2 0.4 0.1))
                        0 1
                        (* 0.8 freq)
                        (* 6 freq))]
    (rlpf (* amp env osc) cutoff 0.7)))

(kbd 16 trumpet)

(pplay
 ::rocky
 (repeat 2
         (pbind {:instrument trumpet
                 :note [:e5 :g5 :a5 :a5 :b5 :e5]
                 :bpm 60
                 :dur  [1/4 3/4 3 1/4 3/4 3]})))

;;---------------------------------------------------

(definst violin [freq 440 amp 1 gate 1]
  (let [osc    (var-saw freq :width 1)
        env    (env-gen (adsr 0.01 0.1 0.7 0.15) :gate gate :action FREE)
        cutoff (lin-lin (env-gen (adsr 0.2 0.4 0.8 0.1))
                        0 1
                        (* 0.8 freq)
                        (* 6 freq))
        tremolo (lin-lin (sin-osc 5) -1 1 0.9 1.1)]
    (rlpf (* tremolo amp env osc) cutoff 0.7)))


(kbd 16 violin)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; AM - Amplitude Modulation
;; FM - Frequency Modulation
;; PM - Phase Modulation

(demo
 (sos (impulse:ar 2)
      0.0 0.05 0.0
      (mouse-y 1.45 1.998 1)
      (mouse-x -0.999 -0.9998 1)))

(sos)
