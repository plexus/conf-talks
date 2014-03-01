{: .noborder}
![](Back_to_the_Future_poster_me_1024.png)

---
{: .noborder .whoami}

### Who am I?

Arne Brasseur

Freelance Ruby Dev

Twitter / Github : @plexus

![](railsgirls_bln.png) ![](ruby_lambik.png)

---

![](happylambda.jpg)

---
{: .expand}

![](01_web_special.jpg)

---

![](Tim_Berners-Lee.jpg)

---

![](nextcube.jpg)

---

![](is_a_server.jpg)

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
{: .expand}

## Architectural Styles

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

![](web_standards_fail.gif)

---

![](not_happy_3.jpg)

---

![](not_happy_1.jpg)

---

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

But..

* Ad-hoc
* RPC

---
{: .expand}

![](05_crud.jpg)

---

* Fielding gets revisited
* Rails comes up with CRUD+JSON
* Calls it REST
* Fielding throws a hissy fit

---

![](fielding_hissy_fit.png)

---
{: .expand}

![](06_hypermedia.jpg)

---

> the information becomes the affordance through which the user (or automaton) obtains choices and selects actions.

Affordance
: The set of possible actions the user is made aware of

Hypermedia
: Media (graphics, video, text) intertwined with hyperlinks, to form a non-linear medium of information

---

* Define media types
* Define link relations
* Drive application state through hypermedia controls

---

Example:

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

## 7. The Future

![](back2future_me_fire.jpg)

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
