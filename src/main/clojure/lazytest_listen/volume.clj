(ns lazytest-listen.volume
  (:use [lazytest-listen])
  (:require [com.stuartsierra.lazytest [watch :as lw]])
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

(defn stop [state]
  (let [{:keys [synth watch-dir-agent]} state]
    (stop-synth synth)
    (send watch-dir-agent lw/stop))
  state)

(defn reset [state]
  (let [{:keys [synth watch-dir-agent]} state]
    (doto (get-channel synth 0)
      (.allNotesOff)
      (change-volume 0 init-volume))
    (send watch-dir-agent lw/reset))
  state)

(defn volume [state vol]
  (change-volume (get-channel (:synth state) 0) (:volume state) vol)
  (assoc state :volume vol))

(defn listen-spec [d & options]
  (let [synth (MidiSystem/getSynthesizer)
	agnt (agent {})
	watch-dir-agent (apply lw/watch-spec
			       d
			       :reporter #(send agnt
						volume
						(int (* 127 (fraction-failed %))))
			       options)]
    (.open synth)
    (doto (get-channel synth 0)
      (.programChange prog)
      (change-volume 0 init-volume))
    (send agnt #(assoc %
		  :synth synth
		  :watch-dir-agent watch-dir-agent
		  :volume init-volume))))