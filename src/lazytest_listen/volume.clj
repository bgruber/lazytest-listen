(ns lazytest-listen.volume
  (:use [lazytest-listen])
  (:require [lazytest.watch :as lw])
  (:import [javax.sound.midi MidiSystem]))

(def prog 78) ;; GM whistle
(def pitch 100)
(def init-volume 20)

(defn change-volume
  "Set the volume of the channel by changing controller 7, but never
  set it to 0. If the requested volume is 0, noteOff instead."
  [channel old-vol new-vol]
  (when-not (= old-vol new-vol)
    (if (= 0 new-vol)
      (.noteOn channel pitch 0)
      (do
        (.controlChange channel 7 new-vol)
        (when (= 0 old-vol)
          (.noteOn channel pitch 127))))))

(defn volume [state vol]
  (change-volume (get-channel (:synth state) 0) (:volume state) vol)
  (assoc state :volume vol))

(defn start [dirs & options]
  (let [synth (MidiSystem/getSynthesizer)]
    (.open synth)
    (doto (get-channel synth 0)
      (.programChange prog)
      (change-volume 0 init-volume))
    (let [synth-state (agent {:synth  synth
                              :volume init-volume})
          reporter    #(send synth-state
                             volume
                             (int (* 127 (fraction-failed %))))]
      (apply lw/start
             dirs
             :report-fn reporter
             options))))
