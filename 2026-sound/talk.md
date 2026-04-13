Happy Europe day! Seventy-six years ago the Schuman Declaration created the
European Community of Coal and Steel, laying the groundwork for what would
become the European Union.

On this joyful day I come to you with a talk about sound. What is sound? And how
do we _make_ the computer make sound? We will take sound apart, so that we may
see what's inside, and so that we know which building blocks we have at our
disposal when putting sound together ourselves.

When I strike this glass, what happens? The impact against the glass briefly
pushes some molecules closer together, while pulling others apart. But the
molecules are connected by forces which resist this displacement, like balls
connected by springs or rubber bands. When we push them together, they push
back. When we pull them apart, they pull back. The result is a wave of push and
pull which propagates through the glass, causing the glass to vibrate.

Each molecule ends up moving back and forth around its original position.
Gradually the kinetic energy is converted into heat, or transferred to the
surrounding air as sound energy, and the movement dies down. This kind of bouncy
movement back and forth around a point of equilibrium we call oscillation.

The vibration of the glass in turn pushes and pulls against the surrounding air
molecules, creating areas of slightly lower, or slightly higher air pressure.
These in turn propagate through the space as pressure waves, which eventually
reach our ear drums, causing these in turn to vibrate. Tiny hairs in our inner
ears convert this vibration into nervous signals, causing our brain to perceive
sound.

So sound consists of vibrations, alternating pressure waves, propagating through
a medium, like the air in this room. We can measure these pressure changes, and
convert them into an electric signal, with the use of a microphone. There are
many different ways to make a microphone. For instance, we can have a diaphragm,
connected to a coil, which is suspended in a fixed magnatic field created by a
magnet. The air causes the diaphragm to vibrate, which causes the coil to
vibrate in turn. Moving the coil back and forth inside the magnetic then induces
an alternating electrical current in the coil. We now have an electrical signal
that represents the sound being captured.

We can use a similar contraption, but in reverse, to convert this electrical
signal back into sound. When we drive the coil with the signal, it becomes an
electromagnet. The changing magnetic field will cause it to be repelled and
attracted by the stationary magnet. This causes the diaphraghm to vibrate,
transferring sound energy back to the air molecules in front of the diaphragm.

To bring this signal into the digital domain, we use something called a Analog
to Digital Converter, or ADC. Tens of thousands of times per second, this
measures or "samples" the electrical signal, producing a signed number. Now our
continuous signal has become a long series of numbers, which we can manipulate
through algorithmic means.

We now have three different ways of looking at sound. As pressure waves in the
physical world, as an alternating electrical current, or as a series of numbers
going up and down. In all three cases we have some stable "zero" point. The
current atmospheric pressure, zero volt, and the number zero, and movement in
two directions around that point. If we plot this movement on a graph we get
something like what you see on the top right.

This is called an oscilloscope, it allows us to _see_ the oscillations that are
happening. The horizontal axis depicts time, the vertical axis shows the
amplitude of the signal.

The time it takes for the signal to go from zero to its highest peak, then down
to its lowest peak, and then back to the middle, is called the period. For this
G note that I'm playing, that is about 5 milliseconds. That means this sound
oscillates 200 times per second. This is the base frequency of this sound, also
called its Pitch. The unit for this frequency is Herz, which is the number of
times something happens in a single second. So this sound has a pitch or
frequency of 200Hz.

If I play this note, which is twice as high in pitch, then the ups and down
appear much closer together. It now only takes 2.5ms to complete a full cycle,
so the frequency is closer to 400Hz.

Let's go back to the 200Hz sound. I now play this same frequency, this same
pitch, with three different instruments. The time it takes to complete one whole
cycle, is exactly the same, and yet all three sound completely different.

The first sound mimics a marimba. The sound dies down quickly after the note is
played. The second mimics a piano. The sound drops gradually as long as I keep
the key depressed, when I let go it fades out quickly. The third sound mimics an
organ, which stays at full volume as long as I keep pressing the key. So the
evolution of their amplitude over time is different, this is what is called
their _envelope_.

But there's more going on. The sounds each have a very distinct quality. A flute
sounds different than a clarinet, and both sound entirely different from an
accordion. This _tone quality_ we call the timbre, also called the tone color.
We get a hint of this when looking at our oscilloscope, and paying attention to
the shape of the wave. Some sounds have a nicely rounded waveform, for others
the shape is complex and jagged. To understand how different timbres comes
about, and how to recreate them through synthesis techniques, we first need to
talk about sine waves.

You may remember the sine and cosine functions from high school trigonometry.
The sine wave shape you see on the oscilloscope is what you get if you move
around a circle at a constant speed, and plot the position of one of the axes
over time. It is in other words a way to represent perfectly round, circular motion.

When you stretch or compress an ideal spring, the restoring force is directly
proportional, but opposite, to its displacement from equilibrium. If you set
such a spring into motion and plot the displacement over time, you also get a
sine wave.
 
These examples hopefully convince you of something that is not easy to intuit,
but that is essential for what follows, namely that a sine wave is the most
basic type of wave that occurs in nature. 

The French mathematician and physicist Jean-Baptiste Joseph Fourier figured out
that complex waves and functions can be decomposed into an infinite sum of
simple sine and cosine waves, and so we call such a decomposition Fourier
analysis, and the result a Fourier series.

In the digital domain, the algorithm that achieves this is called the Fast
Fourier Transform, or FFT. By applying this transformation, we can discover the
individual sine waves hidden inside a complex wave form, across the sound
spectrum, which is what you see in the spectrogram on the bottom right.

Let's have a look at this "trumpet" synth. You clearly see these equally spaced
out peaks across the frequency spectrum. The leftmost peak represents the base
frequency. The others are all at multiples of this base frequency. They are
known as harmonics, or overtones.


-----------------------

What is sound? 

- Pitch (base frequency)
- Amplitude (volume, loudness)
- Envelope
- Timbre (frequency content)
- Harmonics
- Tremolo (vol)
- Vibrato (pitch)


- Midi note numbers, note on/off/ctl events
