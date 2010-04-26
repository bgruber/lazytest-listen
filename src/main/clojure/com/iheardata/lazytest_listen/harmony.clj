(ns com.iheardata.lazytest-listen.harmony
  (:use [com.iheardata.lazytest-listen]
	[clojure.set :only (difference)]))

(defn midi-transition
  "Construct a list of MIDI messages that turns off all notes in
   old-pitches that are not in new-pitches, and turns on any notes in
   new-pitches that are not in old-pitches. old-pitches and
   new-pitches are sets of integers representing MIDI pitches."
  [old-pitches new-pitches note-on-velocity channel]
  (doseq [pitch (difference old-pitches new-pitches)]
		 (.noteOff channel pitch))
  (doseq [pitch (difference new-pitches old-pitches)]
    (.noteOn channel pitch note-on-velocity)))