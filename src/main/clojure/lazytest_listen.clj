(ns lazytest-listen
  (:require [lazytest.results :as lr]))

(defn get-channel [synth channel]
  (aget (.getChannels synth) channel))

(defn stop-synth [synth]
  (.allNotesOff (get-channel synth 0))
  (.close synth))

(defn fraction-failed [r]
  (let [{:keys [total fail]} (lr/summarize r)]
    (/ fail total)))