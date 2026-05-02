(ns dcd2026.support
  (:require
   [overtone.live :refer :all]
   [casa.squid.jack :as jack]))

(comment
  (jack/ports)
  (stop))

(defn init! []
  (run! jack/disconnect
        (jack/connections))

  (let [ports (into #{} (jack/ports))]
    (jack/connect
     [["Overtone:out_1" (some ports
                              ["PCM2704 16-bit stereo audio DAC Digital Stereo (IEC958):playback_FL"
                               "Family 17h/19h/1ah HD Audio Controller Speaker:playback_FL"])]
      ["Overtone:out_2" (some ports
                              ["PCM2704 16-bit stereo audio DAC Digital Stereo (IEC958):playback_FR"
                               "Family 17h/19h/1ah HD Audio Controller Speaker:playback_FR"])]])
    (when (ports "Friture/ALSA Capture [python3.13]:input_FL")
      (jack/connect
       [["Overtone:out_1" "Friture/ALSA Capture [python3.13]:input_FL"]
        ["Overtone:out_2" "Friture/ALSA Capture [python3.13]:input_FR"]]))))

(init!)

(definst sine-wave [freq 440 amp 1 gate 1]
  (* amp
     (env-gen (adsr 0.1 0.1 0.7 0.1) :gate gate :action FREE)
     (sin-osc freq)))

(definst marimba [freq 440 amp 1 gate 1]
  (let [env (env-gen (perc :release 0.7) :gate gate :action FREE)]
    (* amp
       1.8
       env
       (rlpf
        (mix [(sin-osc freq)
              (* 0.9 (sin-osc (* 3.95 freq)))
              (* 0.3 (sin-osc (* 12.55 freq)) )])
        (* 1.5 freq)))))

(definst violin [freq 440
                 hpf-freq {:default 4.5 :max 12}
                 lpf-freq {:default 10.4 :max 12}
                 attack {:default 0.12 :min 0 :max 3}
                 decay  {:default 0.3 :min 0 :max 2}
                 sustain  {:default 1 :min 0 :max 2}
                 release {:default 0.5 :min 0 :max 2}
                 vibrato-freq {:default 6 :min 4 :max 12}
                 vibrato-onset {:default 1 :max 3}
                 vibrato {:default 0.2 :max 5}
                 h1-gain {:default 1 :max 24}
                 h2-gain {:default 1 :max 24}
                 h3-gain {:default 1 :max 24}
                 h4-gain {:default 1 :max 24}
                 h5-gain {:default 1 :max 24}
                 h6-gain {:default 1 :max 24}
                 h7-gain {:default 1 :max 24}
                 h8-gain {:default 1 :max 24}
                 hrq {:default 1 :max 10}
                 gate 1]
  (-> (* (+ (var-saw :freq freq))
         (env-gen (adsr attack decay sustain release)
                  :gate gate
                  :action FREE)
         (+ 1 (* vibrato
                 (sin-osc :freq vibrato-freq)
                 (env-gen (asr vibrato-onset 1 0.3)))))
      (mid-eq freq hrq h1-gain)
      (mid-eq (* 2 freq) hrq h2-gain)
      (mid-eq (* 3 freq) hrq h3-gain)
      (mid-eq (* 4 freq) hrq h4-gain)
      (mid-eq (* 5 freq) hrq h5-gain)
      (mid-eq (* 6 freq) hrq h6-gain)
      (mid-eq (* 7 freq) hrq h7-gain)
      (mid-eq (* 8 freq) hrq h8-gain)
      (hpf :freq (* hpf-freq freq))
      (lpf :freq (* lpf-freq freq))))

(definst electric-organ [freq 440 amp 1 gate 1]
  (let [m1 (* (env-gen (adsr 0 0.5 0.8 0.1 :curve :exp) gate :action FREE)
              (sin-osc freq))
        c1  (* (env-gen (adsr 0 0.8 0.8 0.1 :curve :exp) gate)
               (sin-osc freq m1))
        m2  (* (env-gen (adsr 0 0.3 0.8 0.1 :curve :exp) gate)
               (sin-osc (* 14.02 freq)))
        c2  (* (env-gen (adsr 0 0.6 0.7 0.1 :curve :exp) gate)
               (sin-osc freq  m2))
        m3  (* (env-gen (adsr 0 0.2 0.8 0.1 :curve :exp) gate)
               (sin-osc (* 2.98 freq)))
        c3  (* (env-gen (adsr 0 0.4 0.6 0.1 :curve :exp) gate)
               (sin-osc freq m3))]
    (* amp
       (rlpf
        (mix [c1 c2 c3])
        (* 2 freq)))))

(definst piano [freq 440 amp 1 gate 1]
  (let [m1 (* (env-gen (adsr 0 1.5 0.01 0.1 :curve :exp) gate :action FREE)
              (sin-osc freq))
        c1  (* (env-gen (adsr 0 3 0.01 0.1 :curve :exp) gate)
               (sin-osc freq m1))
        m2  (* (env-gen (adsr 0 0.02 0.01 0.1 :curve :exp) gate)
               (sin-osc (* 13.98 freq)))
        c2  (* (env-gen (adsr 0 3 0.01 0.1 :curve :exp) gate)
               (sin-osc freq m2))
        m3  (* (env-gen (adsr 0 0.2 0.01 0.1 :curve :exp) gate)
               (sin-osc (* 3.02 freq)))
        c3  (* (env-gen (adsr 0 3 0.01 0.1 :curve :exp) gate)
               (sin-osc freq m3))]
    (* amp
       0.5
       (rlpf
        (mix [c1 c2 c3])
        (* 3 freq)))))

(defn param [inst name]
  (some #(when (= name (:name %)) %)
        (:params inst)))

(defn kbd
  ([inst]
   (kbd nil inst))
  ([ch inst]
   (if (nil? inst)
     (do
       (remove-event-handler [::kbd ch :on])
       (remove-event-handler [::kbd ch :off]))
     (do
       (on-event [:midi :note-on]
                 (fn [{:keys [note channel velocity] :as e}]
                   (when (or (nil? ch) (= ch (inc channel)))
                     (prn channel note inst)
                     (event :note
                            :instrument inst
                            :midinote note
                            :dur nil
                            :amp (if (param inst "amp")
                                   (* 1.5 (/ velocity 128) @(:value (param inst "amp")))
                                   1))))
                 [::kbd ch :on])
       (on-event [:midi :note-off]
                 (fn [{:keys [note channel]}]
                   (when (or (nil? ch) (= ch (inc channel)))
                     (prn 'OFF channel note inst)
                     (event :note-end
                            :instrument inst
                            :midinote note)))
                 [::kbd ch :off])))))

(defn kbd-cc [ch synth mapping]
  (let [params     (:params (if (var? synth) @synth synth))
        midi-state (atom {})
        mapping    (into {} (map (juxt val key)) mapping)
        mapping    (into {} (keep (fn [param]
                                    (when-let [c (get mapping (keyword (:name param)))]
                                      [c param])))
                         params)]
    ;;(prn mapping)
    (on-event [:midi :control-change]
              (fn [{:keys [data1 channel data2] :as e}]
                (prn e)
                (when (= channel (dec ch))
                  (when-let [{:keys [name default min max step value]
                              :or {min 0}}
                             (get mapping data1)]
                    (let [synth (if (var? synth) @synth synth)
                          v (/ data2 127)
                          v (+ min (* (- max min) v)) ;; 0-127 -> min-max
                          v (if step
                              (* step (Math/round (double (/ v step))))
                              v)] ;; round to nearest step
                      ;;(println name "=" (double v))
                      (reset! (:value (first (filter (comp #{name} :name) (:params synth)))) v)
                      (ctl synth (keyword name) v)))))
              [::ctl ch])))

(definst trumpet [freq 440 amp 1 gate 1]
  (* amp
     (rlpf (* (env-gen (adsr 0.01 0.1 0.7 0.15) :gate gate :action FREE)
              (var-saw freq :width 0))
           (lin-lin (env-gen (adsr 0.05 0.2 0.4 0.1))
                    0 1
                    (* 0.8 freq)
                    (* 6 freq))
           0.7)))

(definst detuned-saws [freq 440 detune {:min 0 :max 1 :default 0} amp 1 gate 1]
  (let [freq2  (* (lin-exp detune) freq)]
    (* amp
       (env-gen (adsr 0.1 0.1 0.7 0.1) :gate gate :action FREE)
       (+
        (rlpf
         (var-saw freq :width 0 )
         (* 3 freq))

        (rlpf
         (var-saw freq2  :width 0)
         (* 3 freq2))))))

(definst detuned-sines [freq 440 detune {:min 0 :max 1 :default 0} amp 1 gate 1]
  (* amp
     (env-gen (adsr 0.1 0.1 0.7 0.1) :gate gate :action FREE)
     (+
      (sin-osc freq)
      (sin-osc (* (lin-exp detune) freq) ))))

(definst harmonics [freq 440 harmonics {:min 1 :default 9 :max 9} amp 1 gate 1]
  (* amp
     (env-gen (adsr 0.1 0.1 0.7 0.1) :gate gate :action FREE)
     (+ (sin-osc freq)
        (* 1/2 (<= 2 harmonics) (sin-osc (* 2 freq)))
        (* 1/3 (<= 3 harmonics) (sin-osc (* 3 freq)))
        (* 1/4 (<= 4 harmonics) (sin-osc (* 4 freq)))
        (* 1/5 (<= 5 harmonics) (sin-osc (* 5 freq)))
        (* 1/6 (<= 6 harmonics) (sin-osc (* 6 freq)))
        (* 1/7 (<= 7 harmonics) (sin-osc (* 7 freq)))
        (* 1/8 (<= 8 harmonics) (sin-osc (* 8 freq)))
        (* 1/9 (<= 9 harmonics) (sin-osc (* 9 freq))))))

(definst clavinet [freq 440 amp 1 gate 1]
  (let [env (env-gen (perc :attack 0.001 :release 0.4) :gate gate :action FREE)]
    (* amp
       1.2
       env
       (rlpf
        (mix [(sin-osc freq)
              (sin-osc (* 1.98 freq))
              (* 0.7 (sin-osc (* 3.05 freq)))
              (* 0.5 (sin-osc (* 4.97 freq)))
              (* 0.3 (sin-osc (* 6.9 freq)))
              (* 0.1 (sin-osc (* 8.02 freq)))])
        (* 2.5 freq)
        0.8))))

(definst electric-piano [freq 440 amp 1 gate 1]
  (let [env (env-gen (adsr :attack 0.01 :decay 0.3 :sustain 0.4 :release 0.8) :gate gate :action FREE)]
    (* amp
       0.7
       env
       (mix [(sin-osc freq)
             (* 0.6 (sin-osc (* 2.03 freq)))
             (* 0.3 (sin-osc (* 4.07 freq)))
             (* 0.15 (sin-osc (* 6.01 freq)))
             (* 0.08 (sin-osc (* 8.05 freq)))]))))
