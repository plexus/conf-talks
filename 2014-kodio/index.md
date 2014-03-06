
Kod.io 2014

&nbsp;

Web Services: past, present, and future

&nbsp;

by Arne Brasseur / @plexus

&nbsp;

Use the space bar or arrow keys to browse the slides.

---
{: .noborder .expand}

![](Back_to_the_Future_poster_me_1024.png)

---
{: .noborder}

{: .bigger }
![](twitter.png)


{: .whoami}
![](railsgirls_bln.png) ![](ruby_lambik.png)

---

![](happylambda.jpg)

---
{: .expand }

![](01_web_special.jpg)

---
{: .expand}


![](back2future_me_fire.jpg)


---
{: .expand}

![](bttf_1990.png)

---

![](Tim_Berners-Lee.jpg)

---
{: .expand}

![](nextcube.jpg)


<!-- --- -->

<!-- ![](is_a_server.jpg) -->


---

# WWW

* Worldwide
* Many different platforms
* Has scaled like no other
* Has evolved while largely keeping compatibility

---

# 1993

WWW starts to really take off

Concerns about early performance of HTTP

IETF gets to work on improved URI, HTTP, HTML

---
{: .expand}

![](02_fielding_thunk.jpg)

---

##"Architectural Styles and the Design of Network-based Software Architectures"

---

## Architectural Styles

---
{: .expand}

![](component_connector_data.jpg)

---
{: .expand}

![](architectural_style.jpg)

---

# Properties of WWW

* low entry barrier
* incremental deployment
* transparent references/identifiers
* extensible
* simple

---
{: .expand}

![](anarchy.jpg)

---
{: .expand}

![](rest_derivation.gif)

---
{: .expand}

![](rest_decomposed.jpg)

---
{: .expand}

![](uniform_interface.jpg)


---
{: .expand}

![](03_soap.jpg)

---

# SOAP

* Little to do with the web
* Perceived as light weight compared to CORBA
* RPC style

---
{: .expand data-background-position="0% 0%"}

![](web_standards_fail.gif)

---
{: .expand}

![](not_happy_3.jpg)

---
{: .expand}

![](not_happy_1.jpg)

---
{: .expand data-background-size="55%" data-background-position="50% 0%"}

![](not_happy_2.jpg)

---
{: .expand}

![](04_ajax.jpg)

---

# 2005

## AJAX

---

# More buzzwords

## Web 2.0

## RIA

---

## Web Devs Are Doing it for Themselves

* Pragmatic
* KISS
* <del>XML</del> JSON

---

## But..

* Ad-hoc
* RPC

---
{: .expand}

![](05_crud.jpg)

---

1. Read Fielding's Thesis
1. Pick a few ideas from it
1. CRUD + JSON
1. Calls it REST
1. Watch Fielding throw a hissy fit

---
{: .expand data-background-size="100%" }

![](fielding_hissy_fit.png)

---
{: .expand}

![](06_hypermedia.jpg)

---
{: style="font-size: 110%"}

Hypermedia is the simultaneous presentation of **information and controls** such that the **information becomes the affordance** through which the user (or automaton) **obtains choices and selects actions**.

---

**Hypermedia**
: Media (graphics, video, text) intertwined with hyperlinks, to form a non-linear medium of information

&nbsp;

**Affordance**
: The set of possible actions the user is made aware of


---

# Adopting Hypermedia

1. Define media types
1. Define link relations
1. Drive application state through hypermedia controls

---

HAL Example:

```json
{
  "id": 79,
  "name": "Leicester Square Theatre",
  "_links": {
    "self": {
      "href": "/api/accounts/79"
    },
    "http://api.ticketsolve.com/rel/shows": {
      "href": "/api/shows"
    },
    "http://api.ticketsolve.com/rel/venue": {
      "href": "/api/venues/{id}",
      "templated": true
    }
  }
}
```

---

## Initial Benefits

* Discoverable API
* Simple client, simply follow links

---

## Longer Term Benefits

* Change location of resources
* Evolve API in backwards compatible way

---

## "Software on the scale of decades"

* References can be laid across systems, across orgs
* Will depend on standardized media types, relationships

---
{: .expand }

![](orig/back_to_the_future_part_2_1989_685x385.jpg)

---

## Future

Still a lot of experimentation happening with formats

* HAL
* Collection+JSON
* JSON-LD
* Siren
* JSON-API
* Uber

---

## Future

* Non-local Linking
* Directory Services
* Aggregators
* Vendor-independent client apps

---

# Q ?
