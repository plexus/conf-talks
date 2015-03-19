{:.center}
Use the space bar or arrow keys to browse the slide.

Some of the images are pretty big, so if you see an empty slide then wait a bit.

---
{:.center style="font-size: 70%"}

# Hypermedia In Practice

## &nbsp;

## RubyConf.{LT,BY}

## 21/22 March 2015

[http://arnebrasseur.net/talks/2015-rubyconf-lt-by](http://arnebrasseur.net/talks/2015-rubyconf-lt-by)

---

{: style="float: left; margin-left: 150px;"}
![](img/avatar.jpg)


{: style="margin-top: 150px;"}
# @plexus

---
{: .center .big-image .huge}

&nbsp;

&nbsp;

![](img/ticketsolve.png)

---
{: .heading}

# A Story

---
{: fullscreen-img="img/once_upon_a_time.jpg"}

---
{: .fragments}

## A Story

September 2013: Rails app is 6 years old

Co-tenancy styling nightmare

Decided time to build a new front-end

---
{: .fragments}

## New Front End

Ember app

Responsive Design

Hypermedia API

---
{: .fragments}

## New Front End

Feature-flagged "slices"

Work from show listing to checkout

Roll out to volunteer customers

<!-- --- -->
<!-- {: fullscreen-img="img/wexford-show.png"} -->

---
{: fullscreen-img="img/steps_show_listing.png"}

---
{: fullscreen-img="img/steps_events.png"}

---
{: fullscreen-img="img/steps_book_tickets.png"}

---
{: fullscreen-img="img/steps_cart.png"}

---
{: fullscreen-img="img/steps_checkout.png"}

---
{: fullscreen-img="img/steps_payment.png"}

---
{: fullscreen-img="img/steps_confirmation.png"}

---
{: .fragments}

## Current Status

Finished end-to-end

In use by a handful of customers

Filling in missing features

<!-- --- -->
<!-- {:.fragments} -->

<!-- ## Backend Stack -->

<!-- A few false starts -->

<!-- First attempt: JSON-API & ActiveModel::Serializers -->

<!-- Quickly gave up on both -->

<!-- --- -->
<!-- {: fullscreen-img="img/knowlegde_change_graph.png"} -->

<!-- --- -->
<!-- {:.fragments} -->

<!-- ## Backend Stack -->

<!-- AMS (at the time) lacking stewardship -->

<!-- No intermediate representation -->

<!-- JSON-API unstable and lacking hypermedia controls -->

<!-- --- -->
<!-- {:.fragments} -->

<!-- ## Backend Stack -->

<!-- API app built with Grape -->

<!-- Still uses Rails' models and service objects -->

<!-- HAL Format + Form Controls -->

<!-- --- -->
<!-- {:.fragments} -->

<!-- ## Frontend Stack -->

<!-- Ember (*not* ember-data) -->

<!-- ember-rails for Handlebars precompilation -->

<!-- Sprockets (future: Ember CLI) -->

<!-- --- -->
<!-- {:.fragments} -->

<!-- ## Ember Hypermedia -->

<!-- Feels emberish: Ember.Object, futures -->

<!-- Allows navigating links, forms -->

<!-- Not open source (yet) -->

---
{: .heading .center}

# Hypermedia*

*also known as REST

---
{: fullscreen-img="img/api_like_the_web.jpg"}

---

## REST

> "Architectural Styles and the Design of Network-based Software Architectures"

{:.center}
— Roy Fielding ([2000](http://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm))

---


<!-- --- -->
<!-- {: fullscreen-img="img/rest_decomposed.jpg"} -->


<!-- --- -->
<!-- {: fullscreen-img="img/uniform_interface.jpg"} -->


## REST ?

![](img/rake_routes.png)

---
{:.fragments}

## Rails-style "RESTful"

Based on pre-shared conventions

Great for exposing DB structure over HTTP

Lacks the most important properties of the web

---
{:.fragments}

## "RESTful" vs REST

Does a browser know the "conventions" your site uses?

Do you need a browser update when URLs change?

No!

---

<!-- ## REST -->

<!-- > REST components perform actions on a **resource** by using a **representation** of the current or intended state of that resource and transferring those representations. -->

<!-- {:.center} -->
<!-- — Roy Fielding -->

<!-- --- -->

<!-- ## Hypermedia -->

<!-- > Hypermedia, an extension of the term hypertext, is a **nonlinear** medium of information which includes graphics, audio, video, plain text and **hyperlinks**. -->

<!-- {:.center} -->
<!-- — Wikipedia -->

<!-- --- -->

## Hypermedia

> Hypermedia is the simultaneous presentation of **information and controls** such that the **information becomes the affordance** through which the consumer **obtains choices and selects actions**

{:.center}
— Roy Fielding

---

## Affordance

> The set of possible actions the user is made aware of

---
{:.fragments}

## REST (2000)

After the fact description of how the web works

Term has been misused (tip: use "resourceful" for what Rails does)

"Real REST" now referred to as Hypermedia

---
{:.heading}

# Hypermedia APIs

---
{:.fragments}

## Hypermedia APIs

Data + Affordances

Can be JSON, HTML, EDN, ...

Existing web standards being repurposed

---
{:.fragments}

## Some Vocab

**Resource**
Anything abstract or concrete you can refer to

**URI**
Identifier for a resource (URL: pointer to ~)

**Representation** (also: Entity)
Serialization of a resource. Specific to _Format_ and _Time_

---
{:.heading .center}

# Media types

## RFC 4288

---
{:.text-scale-3}

```
Network Working Group                                           N. Freed
Request for Comments: 4288                              Sun Microsystems
BCP: 13                                                       J. Klensin
Obsoletes: 2048                                            December 2005
Category: Best Current Practice


         Media Type Specifications and Registration Procedures

Abstract

   This document defines procedures for the specification and
   registration of media types for use in MIME and other Internet
   protocols.
```

---

## Media Types

> Accept: text/html

> Content-type: application/vnd.collection+json

---
{:.fragments}

## Media Types

Originated in email as MIME types

Mechanism for labeling content

IANA keeps a registry

---
{:.heading .center}

# Web Linking

## RFC5988

---
{:.text-scale-3}

```
Internet Engineering Task Force (IETF)                     M. Nottingham
Request for Comments: 5988                                  October 2010
Updates: 4287
Category: Standards Track
ISSN: 2070-1721


                              Web Linking

Abstract

   This document specifies relation types for Web links, and defines a
   registry for them.  It also defines the use of such links in HTTP
   headers with the Link header field.
```

---
{:.text-scale-3}

## Rels

``` html
<link rel="stylesheet"
  href="/stylesheets.css"
  type="text/css">

<link rel="alternate"
  type="application/atom+xml"
  href="/feed.xml">

<link rel="author"
  href="https://plus.google.com/u/0/112268398775525141261">

<link rel="self" href="http://blog.url.com/feed.xml" />
```

---
{:.fragments}

## Rels

Originated in HTML

Usage spread: XFN, Atom

Microformats & IETF keep a registry

---
{:.fragments}

## Rels

Can be registered keyword: alternate, self

Or custom by using URIs

Simply identifiers, but great to point to docs

---
{:.fragments}

# Ship it!

---

Interesting bits

* invalidates
* ErrorMapper
* FormError
* change_quantity
* presence/abscence of form control reflects on GUI
* Ember hypermedia
** resource/link/form objects
** creates right type


<!-- --- -->
<!-- {: .center} -->

<!-- ![](img/book_tickets.png) -->

<!-- --- -->
<!-- {: fullscreen-img="img/calendar.png"} -->

<!-- --- -->
<!-- {: .center} -->

<!-- ![](img/cart.png) -->


<!-- --- -->
<!-- {: .center} -->

<!-- ![](img/pager.png) -->


<!-- --- -->
<!-- {: fullscreen-img="img/html0.png"} -->

<!-- --- -->
<!-- {: fullscreen-img="img/html.png"} -->



<!-- --- -->
<!-- {: fullscreen-img="img/yaks.png"} -->

---
{: fullscreen-img="img/fin.jpg"}
