lazytest-listen
===============

Stuart Sierra has recently been working on
[lazytest](http://github.com/stuartsierra/lazytest), which among other
things allows for continuous testing. A continuous process begs to be
monitored, and if you're talking to me that means an *auditory display*.

(Auditory displays actually have a history of being used for similar
software development tasks, like debugging. Maybe I'll put some
citations here.)

Two displays are provided, except by two I mean one because only the
first one is implemented. The first plays an annoying whine at a
volume proportional to the fraction of tests which are failing. The
second plays a continuous chord which will grow more or less dissonant
in proportion to the same.

Using
-----
This only works with lazytest tests. Also, I would not do this with
headphones on.

    (require '(com.iheardata.lazytest-listen [volume :as llv]))
    
    ;; start it up
    (def listen-agent (llv/listen-spec "path/to/src"))
    
    ;; you should hear a short tone to indicate that things have
    ;; started, followed by silence if all of your tests pass. Whenever you
    ;; have failing tests, you will hear an annoying whine.
    
    ;; stop it
    (send listen-agent llv/stop)`

Notes
-----

In the interest of having something that works with hopefully zero
configuration on the widest variety of computers, this just uses
straight up MIDI. Could I have done something more interesting with a
synthesis package? Sure, but this is basically a toy and I wanted
people to be able to just compile it and run.

The audio will be played on Channel 0 on the first synthesizer
javax.sound.midi dishes up. That should probably be the built-in java
soft-synth. On Ubuntu (and maybe other linux distros), you're going to
want to make sure you're using OpenJDK, as the sound setup for the sun
jdk is not pulseaudio-friendly.