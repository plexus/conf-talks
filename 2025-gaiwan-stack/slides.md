# The Gaiwan Stack

London Clojurians 2025, Virtual
{:.location}

Arne Brasseur, @plexus@toot.cat
{:.me}

---

## Gaiwan ?


----

![](img/mitesh.webp)

Mitesh
Custodian of Computational Clarity and Minimal Brilliance

----

<div class=cols>

- Chestnut
- lambdaisland.com
- clojurians-log
- nextjournal / Ductile
- ITRevolution video library
- ITRevolution conference platform
- ITRevolution video editor
- Compute Software
- slack-widgets
- 4ever-clojure

<br>

- Eleven
- Pitch
- Transit
- Videra Health
- Humble Chrome extension
- Humble Platform
- Aviation Glass
- Catermonkey
- Compass
- Oak

</div>


----

## Application Harness

- lambdaisland/config + lambdaisland/makina 
- before integrant + Aero

----

## Tooling

- Clojure CLI / deps.edn
- Emacs with Corgi
- Launchpad
- Kaocha
- Portal
- lambdaisland/cli + babashka
- (user/go), (user/refresh), (user/refresh-all) tools.namespace
- tools.build + podman

----

## Web stack

- Jetty!
  - tried http-kit, pedestal, Hirundo/Helidon, Undertow/Pohjavirta
- Reitit
  - ring approach
  - tried interceptors with Sieppari (and with Pedestal), never really needed them
  - content-negotiation with Muuntaja
  - Malli-based validation/coercion
  - var-based route handlers
  - ring-defaults : cookies, session, some other utility stuff
- lambdaisland/hiccup
  - compatible with reagent style hiccup, :<>, [component-fn], {:styles {...}}
  - wrap-render
- Ornament!

----

## Database

- Datomic
  - concise schema
  - wagontrain
  - heavy use of entity API 
  - munged-entity
- PostgreSQL + next.jdbc + Hikari + HoneySQL

----

## Logging

- So far: Logback + Pedestal.log
- Evaluating: Log4j2 (lambdaisland.log4j2)

----

## ClojureScript smorgasbord

- shadow-cljs
- glogi
- reagent
- reitit
- ornament
- dom-types
- deja-fu
- lamdaisland/fetch
- cljs-bean
- kitchen-async
- applied-science/js-interop
