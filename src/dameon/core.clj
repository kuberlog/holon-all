(ns dameon.core
  (require [dameon.face.core :as face]
           [dameon.voice.core :as voice]
           [dameon.brochas-area.core :as brochas-area]
           [dameon.prefrontal-cortex.core :as prefrontal-cortex]
           [clj-time.core :as t]
           [clj-time.coerce :as c]
           [overtone.at-at :as at-at]
           [dameon.visual-cortex.youtube-player :as youtube-player]
           [dameon.visual-cortex.core :as visual-cortex]
           [dameon.eyes.core :as eyes]
           [clojure.core.async :as async]
           [dameon.smart-atom :as smart-atom]
           [dameon.keyboard-listener :as keyboard-listener]))

(import '[java.util.Timer])

(defn init []
  (eyes/see visual-cortex/tree)
  (brochas-area/launch-sphinx-dameon)
  (keyboard-listener/start prefrontal-cortex/input))

(init)

(defn greet-person-if-seen []
  (async/go
    (loop [time-since-last-saw-person (. System currentTimeMillis)] ;in seconds 
      (Thread/sleep 1000)
      (println time-since-last-saw-person)
      (let [sees-person (visual-cortex/sees-person?)
            elapsed-time-since-last-saw-person (/ (- (. System currentTimeMillis) time-since-last-saw-person) 1000)]
        (if (and (> elapsed-time-since-last-saw-person 20) sees-person)
          (voice/speak "Hello There"))
        (recur (if sees-person (. System currentTimeMillis) time-since-last-saw-person))))))

(def greet-process (greet-person-if-seen))

(defn greet [name]
  (face/change-emotion :confused :block true)
  (voice/speak (str "Hello " name ))
  (voice/speak (str "How are you?"))
  (face/change-emotion :exuberant))


(def alarm-not-fired? (atom true))

(defn fire-alarm? [time]
  (if (and (> (. System currentTimeMillis) time) @alarm-not-fired?)
    true
    false))

(def my-pool (at-at/mk-pool))

(defn set-alarm [time]
   (at-at/at (c/to-long (apply t/date-time time))  
             #(do (voice/speak "Hello. It is time to wake up!")
                  (. Thread sleep 3000)
                  (voice/speak "Hello. It is time to wake up!")
                  (. Thread sleep 3000)
                  (voice/speak "Hello. It is time to wake up!")
                  (swap! alarm-not-fired? (fn [x] (identity false))))
             my-pool))










