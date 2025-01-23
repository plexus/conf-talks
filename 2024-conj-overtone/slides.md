
# The Next 10 Years of **Overtone**
{:#title}

Clojure/conj 2024, Washington DC
{:.location}

Arne Brasseur, @plexus@toot.cat
{:.me}


----

# **Overtone**

----

# 1. What is **Overtone**?

----

## Overtone is a <br>**Sound and Music Programming** <br> Toolkit for **Clojure**

<!-- ,<br> with great affordances for **live-coding** -->

----

# **Toolkit**

## Endless Possibilities

<!-- ---- -->

<!-- <\!-- ![](elephant.webp) -\-> -->
<!-- ![](elephant_mural.jpg) -->

----

## **YOU** Can

* Create Synthesizers
* Do Algorithmic Composition
* Use Samples
* Do Sound Design
* Use Music Theory
* Hook up audio/MIDI instruments
* Do Live Performances

<!-- * SuperCollider Client -->
<!-- * SynthDef Compiler/Decompiler -->
<!-- * Scheduling Library (at-at) -->
<!-- * Clock / Metronome -->
<!-- * Midi & OSC I/O -->
<!-- * Music Theory Library -->
<!-- * Instrument / Studio abstractions -->
<!-- * Sample / Freesound -->
<!-- * Pre-built Instruments -->
<!-- * GUI Support Code -->
<!-- * Pre-Built Effects & Audio Processing  -->
<!-- * **new** Pattern Library -->

<!-- ---- -->

<!-- ![](overtone_architecture.svg) -->
<!-- {: data-inline="true"} -->

----

### SuperCollider Server

![](modular_synth.jpg)
{:.img-small}

[CC-By-SA &copy; Cory Doctorow](https://commons.wikimedia.org/wiki/File:Modular_Synthesizer_-_Enticingly_technical_synthesizer_1,_Control_Voltage,_Mississippi_Street,_Portland,_Oregon,_USA_%282014-07-12_by_Cory_Doctorow%29.jpg)

<!-- ---- -->

<!-- ```supercollider -->
<!-- SynthDef("subtractive", { arg out, freq = 440; -->
<!--     Out.ar( -->
<!--         out, -->
<!--         LPF.ar( -->
<!--             Pulse.ar(freq, 0.5, 0.1), -->
<!--             Line.kr(8000, 660, 6) -->
<!--         ) -->
<!--     ) -->
<!-- }).add; -->
<!-- ``` -->

<!-- ---- -->

<!-- ```clojure -->
<!-- (definst subtractive [freq 440] -->
<!--   (lpf (pulse :freq freq :width 0.5) -->
<!--        (line :start 8000 :end 660 :dur 2))) -->
<!-- ``` -->

<!-- ---- -->

<!-- # 2. Overtone **History** -->

<!-- ---- -->

<!-- ```git -->
<!-- $ git log --reverse | head -4  -->

<!-- commit 83db1b9ad1df92d964d7c3d77dd43634d32182e3 -->
<!-- Author:  Jeff Rose <rosejn@gmail.com> -->
<!-- Date:    Wed Sep 2 17:26:51 2009 +0200 -->

<!--     first commit -->
<!-- ``` -->

----

![](commits_chart.png)

----

## My Overtone story

- Started learning Clojure Spring of 2013
- Overtone is what really got me hooked
- Revisited it every so often to do some creative coding
- Realized about a year ago that the project was in a pretty sad state

<!-- - Snuck Overtone+Clojure into the Rubymonstas -->

<!-- ---- -->

<!-- ## Fast Forward to 2023 -->

<!-- - Try to run my old Overtone code, and can't get it to boot -->
<!-- - Version on Clojars is from 2019, and is broken -->
<!-- - Bit more luck with latest git version, but still many issues -->

----

## Sad Face 😢

## Overtone could be **Timeless Software**

---

# 2. **Timeless** Software

---

## **SuperCollider** was first released in 1996. <br> It still has an active community.


---

<video controls width="800">
  <source src="pulu.mp4" type="video/mp4" />
</video>

Pulu performing with SuperCollider at Heart of Clojure 2024

----

<!-- --- -->

<!-- ![](eli_header.png) -->

<!-- --- -->

![](eli_book.jpg)

---

![](eli_videos.png)

---

## Making Overtone **Timeless**

- It has to just work
- Make sure the core is solid and stable
- Fix or improve the half baked stuff
- Add the missing pieces

<!-- - Grow an ecosystem  -->

<!-- ---- -->

<!-- ## **Ecosystem** -->

<!-- - Leipzig  -->
<!-- - Disclojure -->
<!-- - Erv -->
<!-- - Piratidal -->
<!-- - Quil  -->
<!-- - Plasticine -->
<!-- - squid.casa/jack -->
<!-- - squid.casa/midi -->
<!-- - Shadertone -->
<!-- - ... -->

----

# 3. The Next **Ten Years** of Overtone

<!-- ---- -->

<!-- ## Making sure we are ready for the future -->

<!-- - Managed to get a hold of Sam, got commit bit and permission to do releases -->
<!-- - Did a new release with accrued improvements (0.11.0) -->
<!-- - Spent the winter of 2023-2024 cleaning things up -->
<!-- - Four more releases since -->

----

## The Next **Ten Years** of Overtone

- A lot has improved in the last 12 months
- Fixed a lot of sharp edges
- We've had five releases
- Pace of development is speeding up (hi Ambrose!)

----

## Pattern Library **(new!)**

- My first big contribution
- Inspired by SuperCollider
- All just seqs and maps under the hood

----

# Overtone is ready **For You**

----

# It's **easy to get started**

----

# It's a lot of **fun**


----

# Next: **1.0**


<!-- ## 0.11.0 (2023-11-02) -->

<!-- <\!-- - accumulated fixes and improvements -\-> -->

<!-- <\!-- ---- -\-> -->

<!-- ## 0.12.3152 (2023-12-26) -->

<!-- <\!-- - Remove embedded SuperCollider server -\-> -->
<!-- <\!-- - Clojure 1.11 / Java 19+ compat -\-> -->
<!-- <\!-- - Many small fixes -\-> -->

<!-- <\!-- ---- -\-> -->

<!-- ## 0.13.3177 (2024-01-05) -->

<!-- <\!-- - Midi hotplug -\-> -->
<!-- <\!-- - Fixes around samples/buffers -\-> -->
<!-- <\!-- - New `loop-buf` ugen -\-> -->

<!-- <\!-- ---- -\-> -->

<!-- ## 0.14.3199 (2024-05-19) -->

<!-- <\!-- - First release of the Pattern Libary **[new]** -\-> -->

<!-- <\!-- ---- -\-> -->

<!-- ## 0.15.* -->

<!-- <\!-- - Pattern library improvements -\-> -->
<!-- <\!-- - Remove reflection/boxed math warnings (Ambrose) -\-> -->
<!-- <\!-- - Persist freesound login -\-> -->

----

## Come Join Us

- Clojurians Slack (#overtone)
- Mailing list (google groups)
- Fediverse! (#overtone, @plexus@toot.cat, @squid.casa@makertube.net)

----

# **Office Hours**

## i.e. Come find me

----

# **Thank You**

<!-- --- -->

<!-- ## Dropped embedded SuperCollider -->

<!-- - Nobody wants to maintain JNI stuff and C build pipelines -->
<!-- - No more JVM Segfaults (!!) -->
<!-- - Easier to stay up to date -->
<!-- - Available on every OS and package manager -->

<!-- --- -->

<!-- ## Linux Audio -->

<!-- _**cue audience laughter**_ -->

<!-- --- -->

<!-- ## Linux Audio -->

<!-- - Is actually good now (Go PipeWire!) -->
<!-- - Try very hard to detect the user's setup and "just work" -->
<!-- - Overtone Wiki: Linux Audio Primer -->


<!-- ---- -->

<!-- # 5. **Maintaining** Overtone -->

<!-- ---- -->

<!-- ## Maintaining Overtone -->

<!-- - Sam and Jeff had a big vision -->
<!-- - It was not fully realized -->
<!-- - Lots of loose ends in the codebase -->
<!-- - I can't go back and read their minds -->

<!-- ---- -->

<!-- <\!-- ## What were they thinking? -\-> -->

<!-- <\!-- - Is this intentional or accidental? -\-> -->
<!-- <\!-- - Is it broken or do I not understand how it's supposed to work? -\-> -->
<!-- <\!-- - Does this serve a purpose or is part of something that never got finished? -\-> -->
<!-- <\!-- - How was this supposed to be used? -\-> -->

<!-- ---- -->

<!-- ## We need to be able to **revisit** some of that code that has been **frozen in time** -->

<!-- ---- -->

<!-- - I'm merging all PRs with minimal scrutiny -->
<!-- - We can always revert if it causes issues -->
