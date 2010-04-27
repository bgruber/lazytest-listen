(ns com.iheardata.lazytest-listen
  (:require [com.stuartsierra.lazytest [report :as lr]]))

(defn get-channel [synth channel]
  (aget (.getChannels synth) channel))

(defn stop-synth [synth]
  (.allNotesOff (get-channel synth 0))
  (.close synth))

(defn fraction-failed [r]
  (let [{:keys [assertions fail]} (lr/summary r)]
    (/ fail assertions)))