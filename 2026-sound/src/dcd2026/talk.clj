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
;; UGens - Unit Generators

;; Oscillators:

(demo (sin-osc 300)) ;; Sine-wave oscillator
(demo (var-saw 300)) ;; Triangle wave (Variable width sawtooth oscillator)
(demo (var-saw 300 :width 1)) ;; Sawtooth wave
(demo (square 300)) ;; Square wave oscillator

;;---------------------------------------------------

;; Filters

;; ┌──────────┐     ┌────────┐     ┌─────┐
;; │  square  │────→│  rlpf  │────→│ out │
;; └──────────┘     └────────┘     └─────┘

(demo (square 300))
(demo (rlpf (square 300) 800)) ;; Resonant Low-pass Filter


;;---------------------------------------------------


;;  ┌────────┐
;;  │  line  │
;;  └────────┘
;;       C
;;       ↓ freq
;; ┌───────────┐        ┌─────┐
;; │  sin-osc  │───────→│ out │
;; └───────────┘        └─────┘

(demo (sin-osc 400))
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
;; ADSR = Attack Decay Sustain Release

(definst adsr-demo [freq 440 gate 1]
  (*
   (env-gen (adsr :attack  0.2 ;; seconds
                  :decay   0.8 ;; seconds
                  :sustain (db->amp -20) ;; level!
                  :release 1.1 ;; seconds
                  )
            :gate gate :action FREE)
   (var-saw freq)))

(kbd 12 adsr-demo)

;;---------------------------------------------------
;; lin-lin: map source range to destination range

(demo
  (sin-osc (lin-lin (env-gen (perc))
                    :srclo 0   :srchi 1
                    :dstlo 110 :dsthi 440)))

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
          (* 1/3 (sin-osc (* 3 freq)))
          #_(* 1/3 (sin-osc (* 3.1 freq)))))))

;;---------------------------------------------------
;; Kalimba / Mbira

;; https://freesound.org/people/scissorwork/sounds/625192/
;; https://en.wikipedia.org/wiki/Mbira#/media/File:Mbira_dzavadzimu_1.jpg
((freesound 625192))
(midi->hz 72) ;; C4

(/ 3430 523.0)
(/ 2900 523.0)

(demo
  (let [freq 523]
    (* (env-gen (perc))
       (+ (sin-osc freq)
          (* 0.3 (sin-osc (* 6.55 freq)))
          (* 0.05 (sin-osc (* 5.5 freq)))
          #_(* 0.03 (sin-osc (* 8.2 freq)))))))


(demo
  (let [freq 523]
    (+
     (* (sin-osc freq)
        (env-gen (perc 0.01 1.0)))
     (* 0.3
        (env-gen (perc 0.005 0.5))
        (sin-osc (* 6.55 freq)))
     (* 0.05
        (env-gen (perc 0.01 0.2))
        (sin-osc (* 5.5 freq))))))


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
    (-> (* amp env osc)
        (rlpf cutoff))))

(kbd 16 template)

(definst trumpet [freq 440 amp 1 gate 1]
  (let [osc    (var-saw freq :width 1)
        env    (env-gen (adsr 0.01 0.1 0.7 0.15) :gate gate :action FREE)
        cutoff (lin-lin (env-gen (adsr 0.05 0.2 0.4 0.1))
                        0 1
                        (* 0.8 freq) (* 6 freq))]
    (-> (* amp env osc)
        (rlpf cutoff 0.7))))

(kbd 16 trumpet)

(*clock* :bpm 85)

(pplay
 ::rocky
 (repeat 2
         (pbind {:instrument trumpet
                 :note [:e5 :g5 :a5 :a5 :b5 :e5]
                 :dur  [1/4 3/4 3 1/4 3/4 3]})))

;;---------------------------------------------------

(definst violin [freq 440 amp {:min 0 :max 1 :default 1} gate 1]
  (let [osc    (var-saw freq :width 0.9)
        env    (env-gen (adsr 0.03 0.075 0.7 0.3) :gate gate :action FREE)
        cutoff (lin-lin (env-gen (adsr 0.05 0.1 0.8 0.1))
                        0 1
                        (* 5 freq)
                        (* amp 12 freq)) ;; open filter with amp!
        tremolo (lin-lin (sin-osc (line 0 5 1)) -1 1 ;; gradually introduce tremolo
                         (line 1 0.9 2)
                         (line 1 1.1 2))]
    (* amp
       (rlpf (* tremolo env osc) cutoff))))

(kbd 16 violin)
(kbd-cc 16 violin {28 :amp})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; FM Synthesis

;; FM - Frequency Modulation
;; PM - Phase Modulation
;; AM - Amplitude Modulation

;; Amplitude modulation: tremolo
(demo
  (let [lfo (sin-osc 5)]
    (* (lin-lin lfo -1 1 0.9 1.1)
       (sin-osc 440))))

;; Frequency modulation: vibrato
(demo
  (let [lfo (sin-osc 5)]
    (sin-osc (lin-lin lfo -1 1 440 450))))

;; Frequency modulation
(demo
  (let [mod-osc (sin-osc 220)]
    (sin-osc (* 220 (lin-lin mod-osc -1 1 0.6 1.4)))))

;; Phase Modulation
(demo
  (sin-osc 440 :phase (sin-osc 440)))

;; https://en.wikipedia.org/wiki/Yamaha_OPL

(definst dx [freq 440
             amp 1.0
             gate 1.0

             ;; Operator 1
             level1 {:min 0 :max 1 :default 0.8}
             mult1 {:min 0 :max 10 :default 1 :step 1}

             attack1 {:min 0 :max 1.0 :default 0.1}
             decay1 {:min 0 :max 3.0 :default 0.2}
             sustain1 {:min 0 :max 1.0 :default 0.75}
             release1 {:min 0 :max 8.0 :default 0.5}

             ;; Operator 2
             level2 {:min 0 :max 2 :default 0}
             mult2 {:min 0 :max 10 :default 1 :step 1}

             attack2 {:min 0 :max 1.0 :default 0.1}
             decay2 {:min 0 :max 3.0 :default 0.2}
             sustain2 {:min 0 :max 1.0 :default 0.75}
             release2 {:min 0 :max 8.0 :default 0.5}]
  (let [freq1 (* freq (+ mult1 (* 0.5 (< mult1 1)))) ;; 1/2, 1, 2, 3, 4, ...
        freq2 (* freq (+ mult2 (* 0.5 (< mult2 1))))

        env1 (env-gen (adsr attack1 decay1 sustain1 release1) gate)
        env2 (env-gen (adsr attack2 decay2 sustain2 release2) gate)

        op2 (* level2 env2 (sin-osc freq2))
        op1 (* level1 env2 (sin-osc freq1 :phase (* 4.0 op2)))]
    op1))

(kbd 15 dx)
(kbd-cc 15 dx
        {21 :level1
         22 :mult1
         23 :attack1
         24 :sustain1
         25 :level2
         26 :mult2
         27 :attack2
         28 :sustain2})
