(ns com.iheardata.lazytest-listen
  (:require [com.stuartsierra.lazytest [report :as lr]]))

(defn get-channel [synth channel]
  (aget (.getChannels synth) channel))

(defn fraction-failed [r]
  (let [{:keys [assertions fail]} (lr/summary r)]
    (/ fail assertions)))