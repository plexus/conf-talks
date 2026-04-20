(ns p2-1-grid)

(def canvas (js/document.getElementById "example1-canvas"))
(def ctx (.getContext canvas "2d"))

(defn resize-canvas []
  (set! (.-width canvas) (.-innerWidth js/window))
  (set! (.-height canvas) (.-innerHeight js/window)))

;; (resize-canvas)
;; (js/window.addEventListener "resize" resize-canvas)

(def world (js/p2.World. #js {:gravity #js [0 0]}))

(def offset-x 120)
(def offset-y 80)
(def spacing 80)
(def radius 15)

(def cols 11)
(def rows 5)

(def bar-width 40)
(def bar-height (* spacing (dec rows)))
(def bar-y (+ offset-y (* spacing (/ (dec rows) 2))))

(def left-bar (js/p2.Body. #js {:mass 0
                                :position #js [20 bar-y]
                                :fixedRotation true}))
(.addShape left-bar (js/p2.Box. #js {:width bar-width :height bar-height}))
(.addBody world left-bar)

(def right-bar (js/p2.Body. #js {:mass 0
                                 :position #js [(+ offset-x (* spacing (dec cols)) spacing (/ bar-width 2)) bar-y]
                                 :fixedRotation true}))
(.addShape right-bar (js/p2.Box. #js {:width bar-width :height bar-height}))
(.addBody world right-bar)

(def bodies
  (vec
   (for [y (range rows)
         x (range cols)]
     (let [body (js/p2.Body. #js {:mass 1
                                  :position #js [(+ offset-x (* spacing x))
                                                 (+ offset-y (* spacing y))]
                                  :fixedRotation true})
           shape (js/p2.Circle. #js {:radius radius})]
       (.addShape body shape)
       (.addBody world body)
       body))))

(defn body [x y]
  (let [idx (+ (* y cols) x)]
    (when (and (<= 0 x) (< x cols)
               (<= 0 y) (< y rows))
      (nth bodies idx))))

(defn apply-impulse [x y impulse-x impulse-y]
  (when-let [b (body x y)]
    (prn b)
    (.applyImpulse b #js [impulse-x impulse-y] #js [0 0])))

(defn connect-bodies [body1 body2 rest-length]
  (when (and body1 body2)
    (let [pos1 (.-position body1)
          pos2 (.-position body2)
          dx (- (aget pos2 0) (aget pos1 0))
          dy (- (aget pos2 1) (aget pos1 1))
          rest-len (or rest-length (Math/sqrt (+ (* dx dx) (* dy dy))))]
      (let [spring (js/p2.LinearSpring. body1 body2 #js {:stiffness 800
                                                         :damping 0.01
                                                         :restLength rest-len})]
        (.addSpring world spring)))))

(defn connect-to-bar [bar body bar-x-offset]
  (let [bar-y-pos (aget (.-position bar) 1)
        body-y-pos (aget (.-position body) 1)
        local-anchor-y (- body-y-pos bar-y-pos)]
    (let [spring (js/p2.LinearSpring. bar body #js {:stiffness 800
                                                    :restLength spacing
                                                    :damping 0.01
                                                    :localAnchorA #js [bar-x-offset local-anchor-y]
                                                    :localAnchorB #js [0 0]})]
      (.addSpring world spring))))

(defn setup-grid! []
  (doseq [x (range cols)
          y (range rows)]
    (let [this-body (body x y)]
      (when (= x 0)
        (connect-to-bar left-bar this-body (/ bar-width 2)))
      (when (= x (dec cols))
        (connect-to-bar right-bar this-body (/ bar-width -2)))
      (doseq [x' [(dec x) x (inc x)]
              y' [(dec y) y (inc y)]
              :when (not= [x y] [x' y'])]
        (connect-bodies this-body (body x' y') nil)))))

(defn start-bouncing! []
  (apply-impulse 4 0 0 800)
  (apply-impulse 5 0 0 1200)
  (apply-impulse 6 0 0 800))

(def last-time (atom nil))

(defn should-render? [canvas]
  (when-let [section (.-closest canvas "section.present")]
    (not (nil? section))))

(defn render [world canvas]
  (fn render-loop [timestamp]
    (when (should-render? canvas)
      (when-let [last-ts @last-time]
        (.step world (/ 1 60) (/ (- timestamp last-ts) 1000) 40))
      (reset! last-time timestamp)
      (.clearRect ctx 0 0 (.-width canvas) (.-height canvas))

      (doseq [spring (.-springs world)]
        (let [body1 (.-bodyA spring)
              body2 (.-bodyB spring)
              anchorA (.-localAnchorA spring)
              anchorB (.-localAnchorB spring)
              pos1 (.-position body1)
              pos2 (.-position body2)
              angle1 (.-angle body1)
              angle2 (.-angle body2)
              ax (aget anchorA 0)
              ay (aget anchorA 1)
              bx (aget anchorB 0)
              by (aget anchorB 1)
              x1 (+ (aget pos1 0) (- (* ax (Math/cos angle1)) (* ay (Math/sin angle1))))
              y1 (+ (aget pos1 1) (+ (* ax (Math/sin angle1)) (* ay (Math/cos angle1))))
              x2 (+ (aget pos2 0) (- (* bx (Math/cos angle2)) (* by (Math/sin angle2))))
              y2 (+ (aget pos2 1) (+ (* bx (Math/sin angle2)) (* by (Math/cos angle2))))]
          (.beginPath ctx)
          (.moveTo ctx x1 y1)
          (.lineTo ctx x2 y2)
          (set! (.-strokeStyle ctx) "#9999bb")
          (set! (.-lineWidth ctx) 1.5)
          (.stroke ctx)))

      (doseq [body (.-bodies world)]
        (let [shapes (.-shapes body)]
          (doseq [shape shapes]
            (when (instance? js/p2.Box shape)
              (let [w (.-width shape)
                    h (.-height shape)
                    x (aget (.-position body) 0)
                    y (aget (.-position body) 1)
                    angle (.-angle body)]
                (.save ctx)
                (.translate ctx x y)
                (.rotate ctx angle)
                (set! (.-fillStyle ctx) "#2d2d44")
                (set! (.-strokeStyle ctx) "#7a7ab8")
                (set! (.-lineWidth ctx) 3)
                (.fillRect ctx (/ w -2) (/ h -2) w h)
                (.strokeRect ctx (/ w -2) (/ h -2) w h)
                (.restore ctx))))))

      (doseq [body (.-bodies world)]
        (let [shapes (.-shapes body)]
          (doseq [shape shapes]
            (when (instance? js/p2.Circle shape)
              (let [r (.-radius shape)
                    x (aget (.-position body) 0)
                    y (aget (.-position body) 1)]
                (.save ctx)
                (set! (.-fillStyle ctx) "#ff6b6b")
                (set! (.-strokeStyle ctx) "#c92a2a")
                (set! (.-lineWidth ctx) 2)
                (.beginPath ctx)
                (.arc ctx x y r 0 (* 2 Math/PI))
                (.fill ctx)
                (.stroke ctx)
                (.restore ctx)))))))
    (js/requestAnimationFrame render-loop)))

(setup-grid!)
(js/requestAnimationFrame (render world canvas))

(.addEventListener canvas "click" start-bouncing!)
