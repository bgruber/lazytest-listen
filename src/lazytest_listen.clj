(ns lazytest-listen
  (:require [lazytest.results :as lr])
  (:import (javax.sound.midi Synthesizer)))

(defn get-channel [synth channel]
  ;; must type hint Synthesizer to avoid reflection error on Windows.
  (aget (.getChannels ^Synthesizer synth) channel))

(defn stop-synth [synth]
  (.allNotesOff (get-channel synth 0))
  (.close synth))

(defn fraction-failed [r]
  (let [{:keys [total fail]} (lr/summarize r)]
    (/ fail total)))
