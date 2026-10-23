# Sports Analytics

<div class="bottom-right">
<p>
22nd of October 2026, Taipei
</p>
<p>
A talk by <strong>Arne Brasseur</strong> for <strong>Taipei dbt Meetup</strong>
</p>
<p>
Fediverse: <strong>@plexus@toot.cat</strong>
</p>
</div>


<aside class="notes"> 
Thank you for having me. 
</aside>

--- 

## Hello!

* Arne Brasseur / 白阿仁
* From Belgium!
* First visited Taiwan in 2003
* Studied at 逢甲大學 in 2007-2008
* Clojure consultancy Gaiwan 2019-2026

---

## Hello!

* Worked on a Football Analytics system
* For one of the big international players
* Unfortunately, covered under NDA
* But I can talk about the history, and industry developments
* Mostly focused on football (soccer)

---

## Sports Analytics

![](/analyst.jpg)

<aside class="notes"> 

These days, every major league sports team has a dedicated analytics team,
regardless of the discipline. Whether it's football (or as the Americans call
it, soccer), American football, baseball, basketball, or golf, computer-based
analytics are an integral part of the sport.

Analists help teams to determine winning tacticts, find undervalued players, or
adjust training schedules. 

</aside>


----

## Sports Analytics

* Enrich broadcast and commentary
* Make player buying/selling decisions
* Improve coaching
* Gain tactical insights
* Betting industry


<aside class="notes"> 

Sports Analytics serves multiple purposes. Broadcasters like to enrich their
broadcasts of games, teams use it to find valuable talent, coaches learn how to
maximise the talent they have, and what to focus on in trainings.

Finally, sports and gambling have always gone hand in hand, and betting
companies rely on analytics data to predict odds, and derived metrics can become
a subject for bets themselves.

</aside> 

----

## English Football in the 1950s

![](/historical.jpg)

<aside class="notes"> 

But applying science and mathematics to sports wasn't always common. To find the
roots of modern digital sports analytics, we have to go back to the analogue
age, to England of the 1950s.

Sports culture at the time was insular and hardy. Training consisted mainly of
running laps and lifting weights. There was a distrust of people with pens and
notebooks coming to tell them how to play. The wisdom of the time was that
winning teams relied on intuition and experience, not on maths and science.

On a tactical level things were still extremely basic. If there was a system, it
emerged spontaneously between the players.

</aside>

---

## Herbert Chapman

![](/herbert_chapman.jpg)

° 1878 – ✝ 1934

English football player and (from 1907) manager.

<aside class="notes"> 

One of the first people to really develop a tactical system was Herbert Chapman.
Though his career as a player was fairly unremarkably, it's as the manager of
Arsenal that he would leave his greatest mark on the game.

He first became a manager at Northampton Town in 1908, and won them the title.
This turned out to be a catalyst for his career, and he started coaching at
bigger, more affluent clubs. First Leeds City in 1912, then Huddersfield Town in
1921, until finally becoming the manager of Arsenal in 1925, where he would
change the face of the game.

</aside> 

---

## W-M Formation

![](/wm_formation.png)

Created by Chapman in the 1920s.

<aside class="notes"> 

The level to which clubs applied explicit tactics varied. Chapman however had
strong ideas about how to maximise the potential of a team through clearly
defined roles, positions, and patterns of play.

His transfer to Arsenal in 1925 coincided with the introduction of the offside
rule as we know it today. Before, players had to keep two opponents (plus the
keeper) between themselves and the opposing goal. These days, that's only one.
This meant a quick counter could be deadly, and teams would have to be more
careful about their defense.

Chapman moved one of the midfielders onto the defensive back line, creating a
3-2-2-3 pattern which became known as the WM formation. He combined this with an
emphasis on counterattacking, and fast players on the flanks.

In his first season at the club, Arsenal finished second, just behind Chapman's
old club Huddersfield Town, but it would take five more years for Chapham's
aggressive tactics to mature. In 1930 Arsenal finally took the title.

</aside> 

---

## Charles Reep

![](/reep.webp)

° 1904 – ✝ 2002

<aside class="notes"> 

At this point Charles Reep enters the stage. Reep was originally trained as an
accountant, he knew his way around charts and tables. During the interwar
period, at 24 years old, he won a competition to join the British Royal Air
force, the RAF. He originally joined as an accountant. During the second world
war he was deployed in Germany, and eventually became a Senior Officer with the
title of Wing Commander.

Reep had been a big football fan from a young age, and being stationed with the
RAF gave him an opportunity to attend matches of some of the bigger teams at the
time.

</aside>

---

## Royal Air Force - Football Association

![](/raf_fa.jpeg)

<aside class="notes"> 

Football was a big deal at the RAF. Different bases had their own teams, and
they had frequent competition between them. They even had their own league, the
RAF Football Association. It was founded in 1920, only shortly after the
founding of the RAF itself, at the end of the first world war. Every
year it organised its own Cup between the different bases.

</aside>

---

## Charles Reep's Data Collection

![](/reep_notation.webp)

<aside class="notes"> 

In 1933 a senior Arsenal player who had been trained under Chapman came to
Reep's Air Force Base to give a lecture about Arsenal's attacking play, and the
system behind it. Reep learned about the W-M system, and implemented it while
stationed in Iraq in 1936, where he was in charge of the airbase's football
team.

After the second world war, Reep returns to England, and In 1950 he is attending
a game by his local team, Swindon Town. They are losing, badly. Reep is
frustrated that two decades later clubs still haven't learned from Chapman's
methods.

At half-time he pulls out his notebook, and using a shorthand notation he starts
writing down everything that happens on the pitch. Every pass, shot, and tackle.
He notes down positions and combinations. And back home he spends hours and
hours analysing the data. The field of football analysis is born.

</aside>

---

## Long Ball Tactics

![](long_ball.png)

<aside class="notes"> 

From his notes Reep figures that a full match contains an average of 280
attacking moves, with an average of 2 successful goals. That means the odds of a
given attacking sequence leading to a goal is only 0.71%, or less than 1 in 100.
He figures that with a small adjustment to the odds, he can bring a team's
average from 2 to 3. It's the first time someone has looked at goals with this
statistical rigour.

Looking at goals that actually happen, Reep figures out that 80% of goals happen
after three passes or fewer, and that shots most often lead to goals when taken
from specific areas of the pitch. He advocates for a Long Ball tactic, where
instead developing the game at the midfield and progressing from there,
defenders pass directly to forward wings, who push the ball into the penalty
area.

</aside>

----

## England vs Hungary (1953)


![](/england_hungary_poster.jpg)

<aside class="notes"> 

It will take time for his ideas to get traction, but in 1953 England playing a
orthodox W-M style of play loses spectacularly from the more dynamic,
continental style of Hungary. This opens the door to new ideas, and Reep's long
ball tactics go from novelty to dogma. 

Reep becomes a welcome guest in football circles. He attends, records, and
analyses over 40 games per season, and takes up to 80 hours per game to process
and analyse the results.

In 1955 Reep retires from the RAF, to go work as a full-time football analist.
His ideas would come to dominate English football for decades, even though they
would become heavily criticised later on, and Reep's reputation suffered because
of it.

</aside>

----

## 1960's

![](londonatlas1.jpg)

<aside class="notes"> 

Throughout the 1960s sports analysis starts gaining traction, in football as
well as in other sports. Initially both collection and analysis happens
manually, on paper, but as new technologies come onto the scene, analysis, and
eventually data capturing, will become increasingly digitised.

The first use of a digital computer for sports analysis is done by Richard
Pollard in 1969, using the University of London's Atlas 1 Computer. Using
punchcards, he enters Reep's data for 100 matches into the computer, and waits 24
hours for the printout.

</aside>

----

## 1970's

![](1970_camcorder.jpg)

<aside class="notes"> 

In the 1970s affordable tape and audio recorders become commonplace, easing data
collection. The analysis itself also becomes more sophisticated with the
introduction of time-motion analysis. Rather than just capturing game events
like passes, this also captures player movements: whether they are walking,
running, sprinting.

</aside>

----

## 1980s

![](/ian_franks_david_partridge_1990.jpg)

Ian Franks and David Partridge

<aside class="notes"> 

Computers become smaller and more accessible throughout the 1980s, and multiple
groups and teams develop their own bespoke analysis systems. In Canada Ian
Franks, a PhD in the area of Motor Learning and Control, realised two things.
One: that the way players were training had little relevance to actual gameplay,
and Two: that coaches could not observe and remember key events that happen
during a 90 minute football game.

</aside>

----

## Ian Franks and Canada's Olympic Team

![](canadian_team.png) 

<aside class="notes"> 

Franks got involved with coaching the Canadian Olympic team. His team developed
a system on an Apple II, hooked up to a video recording, that allowed a single
person to annotate an entire match worth of events.

</aside>

----

## Ian Franks and Canada's Olympic Team

![](/ian_touch_pad.png)

Ian Frank using a touchpad to annotate match data

<aside class="notes"> 

In the following years Franks and his team collected and annotated data, not
just of the matches that the Canadian Olympic team played, but of all FIFA world
cup games of 1982. Based on this data they organised a two week course for
the top Canadian coaches.

One of the people in attendance was Charles Hughes, a disciple of Reep, bringing
in the experience they had acquired doing data analysis in the English league.

The computer aided coaching led to questions that may seem obvious now, but were
impossible to answer without easy access to the data.

</aside>

---

## Opta and Professional Sports Analytics

![](opta_analyst.webp)

<aside class="notes"> 

While the eighties are still a wild west, with individual teams working with
university researchers, from the mid-90's we see a burgeouning professional
sport statistics industry, starting with the founding of Opta.

Opta started in 1996, with similar manual encoding of game events, based on VCR
recordings. They would encode some 50 to 60 events per match. It was almost
comically basic, but things would evolve quickly from there.

</aside>

----

## Broadcast & Clubs

![](opta_bbc.webp)

<aside class="notes"> 

Opta served two markets with their event data, statistics for broadcast
television, and for professional clubs. They became the official provider for
Sky, which broadcasts the Premier League in the UK, and the official statistics
partner of the premier league itself.

</aside>

----

## A Closer Look at Event Data

```javascript
{"event": "pass",
 "period": 2,
 "game_clock": "23:46",
 "pos": {"x": 36.5, "y": 12.8},
 "from": {"jersey_num": "10"},
 "to": {"jersey_num": 3}}
```

<aside class="notes"> 

What Charles Reep, Opta, and later companies like StatsBomb base their products on is this kind
of event data. There's an event type, for instance "pass", "interception",
"goal", "player swap", 
- happening at a specific point of time in the game
- somwhere on the pitch
- involving one or more players.

You typically get 2 to 3 thousand of these for a single game.

</aside>

----

## In-Stadium Tracking

![](in_stadium_tracking.jpg)

<aside class="notes"> 

While event data is fairly coarse grained, you can get far more details with
tracking of individuals throughout the game.

In 1998 a company was founded in the UK that would become synonymous with
tracking data: ProZone. ProZone would install 6 to 8 cameras in each stadium.
Carefully callibrated, and combined with machine vision software, it could
capture the position of each player, and the ball, every 25 ms.

Internet was still in the dial-up age, and transferring high resolution video
was out of the question with the bandwidth of the time, so after each game
courriers on motorbikes would bring the recordings to the ProZone office, where
it was processed, and then the results were brought back to the club.

Whereas event data will get you a few thousand data points per game, with
tracking data you get 4 to 5 million data points.

With this system, a few hours after the game, coaches would have detailed data
available, as well as multiple angles of footage, which together they could use
to assess individuals players and game tactics.

It would take a few years to gain traction, but eventually ProZone would become
a household name, not just in the UK, but in Europe and North America as well.

</aside>

----

## Moneyball

![](moneyball.jpg)

<aside class="notes"> 

Meanwhile similar developments were happening in major league baseball. The
movie Moneyball dramatically depicts how the Oakland A's general manager Billy
Beane defied common scouting wisdom at the time, by leaning into what was called
"sabermetrics", using advanced analytics to make making player buying decisions.

While operating on a tiny budget compared to the big teams, under his lead the
A's managed to score 20 games in a row in 2002. The movie is based on the book
with the same title, and while the movie added some fictional elements, the
basic facts are right. The Oakland A's were ahead of their time with their use
of sports analytics, and would soon set a trend across baseball.

The movie came out in 2011, and helped solidify the place of statistics in
professional sports, not just in baseball, but in any professional discipline.

</aside>

----

## xG: Expected Goals


<math xmlns="http://www.w3.org/1998/Math/MathML" display="block">
  <mi>xG</mi>
  <mo>=</mo>
  <mfrac>
    <mn>1</mn>
    <mrow>
      <mn>1</mn>
      <mo>+</mo>
      <msup>
        <mi>e</mi>
        <mrow>
          <mo>−</mo>
          <mo>(</mo>
          <msub>
            <mi>β</mi>
            <mn>0</mn>
          </msub>
          <mo>+</mo>

          <msub>
            <mi>β</mi>
            <mtext>DistanceToGoal</mtext>
          </msub>
          <mo>⋅</mo>
          <mi mathvariant="normal">DistanceToGoal</mi>
          <mo>+</mo>

          <msub>
            <mi>β</mi>
            <mtext>ShotAngle</mtext>
          </msub>
          <mo>⋅</mo>
          <mi mathvariant="normal">ShotAngle</mi>
          <mo>+</mo>

          <msub>
            <mi>β</mi>
            <mtext>IsFoot</mtext>
          </msub>
          <mo>⋅</mo>
          <mi mathvariant="normal">IsFoot</mi>
          <mo>+</mo>

          <mo>⋯</mo>
          <mo>)</mo>
        </mrow>
      </msup>
    </mrow>
  </mfrac>
</math>

xG: based on a number of variables, what's the probability (between 0 and 1)
that a shot scores a goal?

<aside class="notes"> 

By the 2010's analytics was no longer a novelty. By this time virtually every
club employed professional analysts, and worked with an analytics partner to
gain access to event data, tracking data, and/or video.

With this technological baseline established, the innovation now shifts to the
analytics methods themselves.

One of the metrics that would soon become mainstream is xG, or expected goals.
The exact definition and model vary from company to company, but the idea is
fairly simple. When a player takes a shot, in a specific position and situation,
what are the odds that that shot becomes a goal?

</aside>

----

## xG Aggregates

![](xg_table.webp)

<aside class="notes"> 

An interesting feature of xG is that it can be summed, for instance for a player
or a team across a game, or a season. This gives us a way to talk about a team's
performance, beyond just the numbers on the scoreboard.

If a team takes 10 shots with a average xG of 0.4, then you would expect them to
score 4 out of those ten goals. If they scored more maybe they got lucky, and
won't be able to repeat the same result next time.

</aside>

----

## xG as a Predictor

![](xg_predictions.webp)

<aside class="notes"> 

xG is also used as a predictor, for instance Opta regularly puts out their
predictions for how the premier league will finish. So the metric has both
explanatory and predictive power.

</aside>

----

## StatsBomb (2017)

![](statsbomb.png)

<aside class="notes"> 

One of the companies pushing for greater analytical insight is StatsBomb. The
company started out as a blog, applying advanced statistical methods to football
data. Eventually they would launch their own Event Data product, similar to
Opta, but with richer annotations, so analysts can include more variables in
their models.

</aside>

----

## Broadcast Tracking

![](statsbom360.gif)

<aside class="notes"> 

StatsBomb would eventually also add tracking data to their events, but not for
every frame, and not based on in-stadium cameras. Instead, they relied on
broadcast data, the same video you and I get to see at home.

Their product StatsBomb 360 added the position of each player to each of the
3000 or so events of a game, and so allowed players and coaches to analyze
specific stages of play in detail.

</aside>


----

## Broadcast Tracking

![](broadcast_tracking.png)

<aside class="notes"> 

However, using broadcast video instead of multiple in-stadium cameras creates
fairly obvious challenges. For one, rarely are all players visible in the shot.
The camera will often focus on just where the action is. There will also be
moments where you don't see players at all, but instead get a shot of the
referee, the audience, or the bench players. There are also frequently replays
after a goal or intense phase of gameplay, during which you don't see what's
happening on the pitch, and depending on broadcast scheduling the initial or
final minutes of a period may not be broadcast at all.

But on the flip side, broadcast data is available cheaply and at scale, in a way
that in-stadium tracking is not.

</aside>

----

## IMPECT Packing

![](packing.jpg)

<aside class="notes"> 

A newer metric that has been gaining popularity is called "Packing", and was
pioneered by a IMPECT. Packing applies to passes, it compares the number of
opposing players between the player on the ball, and the opponent's goal, before
and after a pass.

In other words: it counts how many players a pass passes by. Teams and players
that have an aggressive style which quickly gets the ball in front of the goal
will score a high Packing rating.

So while xG quantifies the quality of a shot on goal, Packing quantifies the
quality of a pass.

</aside>

---

## Skeletal Tracking

![](skeletal_tracking.webp)

<aside class="notes"> 

The new frontier in football analytics is skeletal tracking. The market leader
here is Genius Sports, with their Second Spectrum technology.

This technology has already been used for some time in basketball, and is now
becoming more popular in football.

With ProZone style in-stadium tracking, which we covered earlier - the system
determines the center of mass of the player, and uses that to place them in a 2D
grid on the field. Second Spectrum however has increased the number of cameras
from 6 to 30. They actually use iPhones in waterproof cases, this turned out to
be more cost effective than professional cameras. They track 29 skeletal points
of each player. And while most tracking uses a similar frame rate as broadcast -
30 to 40 frames per second - Second Spectrum captures a whopping 100 frames per
second.

This way you can create a detailed model of the player position - shoulders,
hips, elbows, and knees - and from there analyse body-orientation, biomechanical
load, and the precise geometry of duels and offsite calls.

</aside>

----

# Thank You! Q&A?

<aside class="notes"> 
</aside>

---

