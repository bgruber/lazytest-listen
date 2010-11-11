lazytest-listen
===============

Stuart Sierra has recently been working on
[lazytest](http://github.com/stuartsierra/lazytest), which among other
things allows for continuous testing. A continuous process begs to be
monitored, and if you're talking to me that means an *auditory display*.

(Auditory displays actually have a history of being used for similar
software development tasks, like debugging. Maybe I'll put some
citations here.)

Two displays are provided. The first plays an annoying whine at a
volume proportional to the fraction of tests which are failing. The
second plays a continuous chord which will grow more or less dissonant
in proportion to the same.

Using
-----

This only works with lazytest tests. Also, I would not try this with
headphones on. The interface is the same as that for
lazytest.watch. At a REPL:

    (use 'lazytest-listen.volume)
    (start ["path/to/src"])

Stop it by hitting CTRL-C.

For the harmonic display, just use the `lazytest-listen.harmony`
namespace instead of `lazytest-listen.volume`.

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

Stuart has eliminated the ability to stop watching in any way other
than killing the process. That means there's no way to shut off any
notes and close the synthesizer when you're done. Not doing that is
fine if you are using the built-in Java synthesizer, because when the
java process terminates, so does the synth. However, if you are
hardcore and are using this with an external MIDI synth, you will
probably get stuck notes.
