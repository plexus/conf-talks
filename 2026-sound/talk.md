Happy Europe day! Seventy-six years ago the Schuman Declaration created the
European Community of Coal and Steel, laying the groundwork for what would
become the European Union.

(slide)

On this joyful day I come to you with a talk about sound. What is sound? And how
do we _make_ the computer make sound? We will take sound apart, so that we may
see what's inside, and so that we know which building blocks we have at our
disposal when putting sound together ourselves.

(slide)

When I strike this glass, what happens? 

(slide)

The impact against the glass briefly pushes some of the glass molecules closer
together, while pulling others away from each other. But the molecules are
connected by forces which resist this displacement, like balls connected by
springs or rubber bands. When we push them together, they push back. When we
pull them apart, they pull back. The result is a wave of push and pull which
goes up and down through the glass, causing the glass to vibrate, as you can see
in this physics simulation. What you also notice is that while the individual
molecules erratically bounce back and forth, the system as a whole exhibits a
slower up-and-down motion, a wave going through the medium.

Each molecule ends up moving back and forth around its original position.
Gradually the kinetic energy is converted into heat, or transferred to the
surrounding air as sound energy, and the movement dies down. This kind of bouncy
movement back and forth around a point of equilibrium we call oscillation.

(slide - pressure waves)

The vibration of the glass pushes and pulls against the surrounding air
molecules, creating areas of slightly lower, or slightly higher air pressure.
These in turn propagate through the space as waves of compression and
decompression, which eventually reach our ear drums, causing these in turn to
vibrate. Tiny hairs in our inner ears convert this vibration into nervous
signals, causing our brain to perceive sound.

(slide -microphone)

So sound consists of vibrations, which are alternating pressure waves,
propagating through a medium, like the air in this room. We can measure these
pressure changes, and convert them into an electric signal, with the use of a
microphone.


(slide - coil)

There are many different ways to make a microphone, many of them based on the
principles of electromagnetism. 

(slide - waveform)

They turn the pressure variations in the air into an alternating electrical
current. We now have a signal that represents the sound being captured.

(slide - speaker)

Similarly we can go the other way around, and convert this signal back into
sound. We now have two ways of looking at sound, as pressure variations in a
medium, or as a electrical signal going up and down.

(slide - ???) - missing

To bring this signal into the digital domain, we use something called a Analog
to Digital Converter, or ADC. Tens of thousands of times per second, this
measures or "samples" the electrical signal, producing a signed number. Now our
continuous signal has become a discrete signal: a long series of numbers. We now
have something we can can manipulate through algorithmic means.

(slide - sine wave)

In all three cases we have some stable "zero" point. The current atmospheric
pressure, zero volt, and the number zero, and movement in two directions around
that point. 

(switch to emacs)

On the top right you can see that visualised by something we call an
oscilloscope, it allows us to _see_ the oscillations that are happening. The
horizontal axis depicts time, the vertical axis shows the amplitude of the
signal.

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

(long time levels)

Let's go back to the 200Hz sound. I now play this same frequency, this same
pitch, with three different instruments. The time it takes to complete one whole
cycle, is exactly the same. They have the same period, wavelength, and
frequency, which is three ways to say the same thing, and yet all three sound
completely different.

Let's first consider a superficial difference, the evolution of the sound
volume. The first sound mimics a marimba. The sound dies down quickly after the
note is played. The second mimics a piano. The sound drops gradually as long as
I keep the key depressed, when I let go it fades out quickly. The third sound
mimics an organ, which stays at full volume as long as I keep pressing the key.
So the evolution of their volume or amplitude over time is different, this is
what is called their _envelope_.

But there's more going on. The sounds each have a very distinctive quality.
Consider this marimba, compared to the organ, compared to this trumpet synth.
They all sound entirely different. This _tone quality_ we call the timbre, also
called the tone color. We get a hint of this when looking at our oscilloscope,
and paying attention to the shape of the wave. Some sounds have a nicely rounded
waveform, for others the shape is complex and jagged. To understand how
different timbres comes about, and how to recreate them through synthesis
techniques, we first need to talk about sine waves.

Here's what a sine wave sounds like, and what it looks like in the oscilloscope.
It's a very plain, basic sound, but it is the building block of more complex
sounds.

Turns out that complex sounds are just a combination of different sine waves
added together, and so we can take any sound apart into a sum of individual sine
waves, at different frequencies and loudness. This is what you see in these
peaks in this diagram on the bottom left. The lowest, and generally the loudest
of these is the base frequency, and it determines the pitch we perceive. The
frequencies above the base are called overtones.

<!-- This sound I'm playing now takes just over 2ms to complete a single cycle, going -->
<!-- up and then down and back to neutral. So it does that 440 times per second. We -->
<!-- call that the base frequency of the sound. Base frequency, because there are -->
<!-- other, higher frequencies hiding in there as well. I can take them out one by -->
<!-- one so you can hear the difference. When I remove all the higher frequencies, -->
<!-- what's left is just a sine wave of the base frequency. -->


In principle overtones can appear anywhere across the frequency spectrum, but we
mostly see them at integer multiples of the base frequency, especially in sound
we perceive as tonal or musical. So if the base frequency is at 200Hz, then
you're likely to see overtones at or close to 400Hz, 600Hz, 800Hz, etc. 

This has to do with physics. If a string vibrates at a frequency, such that the
length of the string is a discrete multiple of the wavelength, then you'll get
what is known as a standing wave, where each cycle reinforces the next. The same
is true for any pipe based instruments, including the human voice. 

Sine waves at multiples of the base frequency, we also call harmonics.

- dissonance / harmony

[Dissonance](https://chromatone.center/practice/sound/dissonance/)


<!-- You may remember the sine and cosine functions from high school trigonometry. -->
<!-- The sine wave shape you see on the oscilloscope is what you get if you move -->
<!-- around a circle at a constant speed, and plot the position of one of the axes -->
<!-- over time. It is in other words a way to represent perfectly round, circular motion. -->

<!-- When you stretch or compress an ideal spring, the restoring force is directly -->
<!-- proportional, but opposite, to its displacement from equilibrium. If you set -->
<!-- such a spring into motion and plot the displacement over time, you also get a -->
<!-- sine wave. -->

<!-- The French mathematician and physicist Jean-Baptiste Joseph Fourier figured out -->
<!-- that complex waves and functions can be decomposed into an infinite sum of -->
<!-- simple sine and cosine waves, and so we call such a decomposition Fourier -->
<!-- analysis. -->

<!-- In the digital domain, the algorithm that achieves this is called the Fast -->
<!-- Fourier Transform, or FFT. By applying this transformation, we can discover the -->
<!-- individual sine waves hidden inside a complex wave form, across the sound -->
<!-- spectrum, which is what you see in the spectrogram on the bottom right. -->

<!-- Let's have a look at this "trumpet" synth. You clearly see these equally spaced -->
<!-- out peaks across the frequency spectrum. The leftmost peak represents the base -->
<!-- frequency. The others are all at multiples of this base frequency. They are -->
<!-- known as harmonics, or overtones. -->

---





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

;; https://wiretext.app/w/J1_3hg1v
