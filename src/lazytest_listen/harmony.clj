(ns lazytest-listen.harmony
  (:require [lazytest.watch :as lw])
  (:use [lazytest-listen]
        [clojure.set :only (difference)])
  (:import [javax.sound.midi MidiSystem]))

(def chords [#{48 55 64 72}
             #{48 55 64 71}
             #{48 55 63 71}
             #{48 54 63 71}
             #{48 54 63 67 71}])

(def prog 69)
(def init-vol 127)
(def velocity 127)

(defn midi-transition
  "Turns off all notes in old-pitches that are not in new-pitches, and
   turns on any notes in new-pitches that are not in
   old-pitches. old-pitches and new-pitches are sets of integers
   representing MIDI pitches."
  [old-pitches new-pitches note-on-velocity channel]
  (doseq [pitch (difference old-pitches new-pitches)]
    (.noteOff channel pitch))
  (doseq [pitch (difference new-pitches old-pitches)]
    (.noteOn channel pitch note-on-velocity)))

(defn change-chord [state ci]
  (midi-transition (chords (:ci state)) (chords ci) velocity (get-channel (:synth state) 0))
  (assoc state :ci ci))

(defn start [dirs & options]
  (let [synth (MidiSystem/getSynthesizer)]
    (.open synth)
    (let [chan (get-channel synth 0)]
      (.programChange chan prog)
      (.controlChange chan 7 init-vol)
      (doseq [p (chords 0)] (.noteOn chan p velocity)))
    (let [synth-state (agent {:synth synth
                              :ci 0})
          reporter    #(send synth-state
                             change-chord
                             (Math/round (* 4.0 (fraction-failed %))))]
      (apply lw/start
             dirs
             :report-fn reporter
             options))))

