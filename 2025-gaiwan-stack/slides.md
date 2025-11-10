# The Gaiwan Stack
{:#title}

London Clojurians 2025, Virtual
{:.location}

Arne Brasseur, @plexus@toot.cat
{:.me}

---

# GAIWAN ?

----

![](img/power_rangers.webp)

----

<div id="team-imgs">
![](img/mitesh.png)
Mitesh

![](img/bettina.png)
Bettina

![](img/arne.png)
Arne

![](img/joanne.png)
Joanne

![](img/laurence.png)
Laurence
</div>

<div id="team-names">
</div>

----

## History

{:.nobullets}
* **2014** Chestnut
* **2017** Lambda Island
* **2019** Gaiwan
* **2019** Heart of Clojure
* **2020** Mitesh joins
* **2021** Laurence joins
* **2023** Bettina joins
* **2024** Heart of Clojure
* **2025** Joanne joins

----

<div class=cols style="gap:4rem;">

<div>
{:.left}
## Commercially 
- Clojure Consulting
- Application Development
- Fractional CTO
- Layer 2/3
  - Tooling and DevEx
  - Social Circuitry
</div>

<div>
{:.left}
## Community
- Maintain lambdaisland.*
- ClojureVerse
- Heart of Clojure
- Oak
</div>

</div>
----

## lambdaisland/*

<div class=cols style="gap:4rem;">

<div>
- **Kaocha**
- **Launchpad**
- **Ornament**
- **CLI**
- URI
- Config
- Plenish
- Regal
- Hiccup
</div>
<div>
- Glögi
- Embedkit
- Ansi
- classpath
- cljbox2d
- deja-fu
- dom-types
- dotenv
- makina
</div>
<div>
- morf
- nrepl-proxy
- reitit-jaatya
- deep-diff
- witchcraft
- xml-select
- chui
- fetch
- edn-lines
</div>
</div>

----

## Projects / Products

<div class=cols>

- **Chestnut**
- **Lambda Island**
- **clojurians-log**
- nextjournal / Ductile
- **ITRevolution video library**
- **ITRevolution conference platform**
- **ITRevolution video editor**
- Compute Software
- **slack-widgets**
- **4ever-clojure**

<br>

- Eleven
- Pitch
- Transit
- Videra Health
- **Humble Chrome extension**
- Humble Platform
- Aviation Glass
- Catermonkey
- **Compass**
- **Oak**

</div>


----

## Gaiwan Stack

Changes a little with each project

But the **deltas** get smaller

Slowly converging on a set of components we **really like**

Combination of **objective reasons** and technological **taste**

**Standardisation** is valuable in and of itself

----

<div class=cols style="gap:4rem;">
<div>
<div class="file-tree">
├── deps.edn
├── bb.edn
├── bin
│   ├── dev
│   ├── kaocha
│   └── launchpad
├── src
├── test
├── resources
│   └── my-project
│       ├── system.edn
│       ├── config.edn
│       ├── dev.edn
│       ├── test.edn
│       └── prod.edn
├── dev
│   └── user.clj
├── deps.local.edn
└── config.local.edn
</div>
</div>
<div>

### Initial project setup

In the past we used **Integrant** and **Aero**

Now we use **Makina** and **lambdaisland/config**

**Launchpad** is essential dev tooling

**lambdaisland/cli** makes it trivial to add scripts/tasks with great **ergonomics**

</div>
</div>

----

<div class=cols style="gap:4rem;">
<div>
<div class="file-tree">
├── **deps.edn**
├── **bb.edn**
├── bin
│   ├── dev
│   ├── kaocha
│   └── **launchpad**
├── src
├── test
├── resources
│   └── my-project
│       ├── system.edn
│       ├── config.edn
│       ├── dev.edn
│       ├── test.edn
│       └── prod.edn
├── dev
│   └── user.clj
├── **deps.local.edn**
└── config.local.edn
</div>
</div>
<div>

### `bin/launchpad`

"One Stop Shop REPL Launcher"

- nREPL, CIDER, refactor-nrepl
- auto-connect editor
- `deps.edn`, `deps.local.edn` hot-reloading
- shadow-cljs REPL
- `.env`/`.env.local` -> `System/getenv` (with hot reload!)
- `(user/portal)`
- sensible defaults: `clojure.main.report=stderr`, `-XX:-OmitStackTraceInFastThrow`

</div>
</div>

----

```clojure
;; bb.edn
{:deps 
 {com.lambdaisland/launchpad {:mvn/version "0.45.201-alpha"}}}

;; bin/launchpad
#!/usr/bin/env bb
(require '[lambdaisland.launchpad :as launchpad])
(launchpad/main {})
```

----

```clojure
;; ~/.clojure/deps.edn
{:launchpad/options
 {:emacs true
  :portal true
  :namespace-maps false}}
  
;; ~/deps.local.edn
{:deps {foo/bar {:local/root "..."}}}
```

---

{: style="font-size: 63%"}
```
➜ bin/launchpad --help
NAME
  launchpad 

FLAGS
  -v, --verbose               Print debug information                                                
  -p, --nrepl-port PORT       Start nrepl on port. Defaults to 0 (= random)                          
  -b, --nrepl-bind ADDR       Bind address of nrepl, by default "127.0.0.1". (default "127.0.0.1")   
      --[no-]emacs            Shorthand for --cider-nrepl --refactor-nrepl --cider-connect           
      --[no-]vs-code          Alias for --cider-nrepl                                                
      --[no-]cider-nrepl      Include CIDER nREPL dependency and middleware                          
      --[no-]refactor-nrepl   Include refactor-nrepl dependency and middleware                       
      --[no-]cider-connect    Automatically connect Emacs CIDER                                      
      --[no-]portal           Include djblue/portal as a dependency, and define (user/portal)        
      --[no-]sayid            Include Sayid dependency and middleware                                
      --[no-]debug-repl       Include gfredericks/debug-repl dependency and middleware               
      --[no-]go               Call (user/go) on boot                                                 
      --[no-]namespace-maps   Disable *print-namespace-maps* through nREPL middleware                
      --[no-]prefix           Disable per-process prefix                                             
      --[no-]execute          Parse and execute the first :exec-fn found in aliases    
```

----

<div class=cols style="gap:4rem;">
<div>
<div class="file-tree">
├── deps.edn
├── **bb.edn**
├── bin
│   ├── **dev**
│   ├── kaocha
│   └── launchpad
├── src
├── test
├── resources
│   └── my-project
│       ├── system.edn
│       ├── config.edn
│       ├── dev.edn
│       ├── test.edn
│       └── prod.edn
├── dev
│   └── user.clj
├── deps.local.edn
└── config.local.edn
</div>
</div>
<div>

### `lambdaisland/cli`

Standard `bin/dev` script in each project

Amalgamation of dev/build/release tooling

Much better ergonomics than `-X` or `bb task`

</div>
</div>

----

{: style="font-size: 73%"}
```js
~/Gaiwan/Oak $ bin/dev --help
NAME
  bin/dev 

SYNOPSIS
  bin/dev [-v | --verbose] [-h | --help] [init | uberjar | write-styles]

FLAGS
  -v, --verbose   Increase verbosity                   
  -h, --help      Show help text for a (sub-)command   

SUBCOMMANDS
  init           Create local files which are not checked in          
  uberjar        Build uberjar                                        
  write-styles   Compile Ornament styles down to target/oak/styles.css
```

----

{: style="font-size: 81%"}
```clj
#!/usr/bin/env bb
(defn uberjar
  "Build uberjar"
  {:flags ["--target-dir" {:doc "Target directory" 
                           :default "target"}]}
  [{:keys [target-dir] :as opts}]
  ,,,)

(def flags
  ["-v, --verbose" "Increase verbosity"
   "-h, --help"    "Show help text for a (sub-)command"])

(def commands
  ["uberjar"      #'uberjar
   "write-styles" #'write-styles])

(cli/dispatch* {:name "bin/dev" :flags flags :commands commands})
```
----

<div class=cols style="gap:4rem;">
<div>
<div class="file-tree">
├── deps.edn
├── bb.edn
├── bin
│   ├── dev
│   ├── kaocha
│   └── launchpad
├── src
├── test
├── resources
│   └── my-project
│       ├── system.edn
│       ├── **config.edn**
│       ├── **dev.edn**
│       ├── **test.edn**
│       └── **prod.edn**
├── dev
│   └── user.clj
├── deps.local.edn
└── **config.local.edn**
</div>
</div>
<div>

### lambdaisland/config

`(config/get :http/port)`

- `resources/foo/config.edn`
- `resources/foo/<ENV>.edn`
- `/etc/foo.edn`
- `~/.config/foo.edn`
- `config.local.edn`
- `(System/getProperty "foo.http.port")`
- `(System/getenv "FOO__HTTP__PORT")`

</div>
</div>

----

<div class=cols style="gap:4rem;">
<div>
<div class="file-tree">
├── deps.edn
├── bb.edn
├── bin
│   ├── dev
│   ├── kaocha
│   └── launchpad
├── src
├── test
├── resources
│   └── my-project
│       ├── **system.edn**
│       ├── config.edn
│       ├── dev.edn
│       ├── test.edn
│       └── prod.edn
├── dev
│   └── user.clj
├── deps.local.edn
└── config.local.edn
</div>
</div>
<div>

### Makina

- Integrant alternative
- "Reloadable workflow", tools.namespace/refresh
- No global registry
- Pure library + Convention-based "framework"

**Still fairly new, Oak is our first project to use Makina.**

</div>
</div>


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

Main stack is

- Web browser: **Jetty**
- Routing: **Reitit**
- HTML rendering: **lambdaisland/hiccup**
- CSS rendering: **Ornament**

----

## Web Browser: **Jetty**

**Jetty** (`ring-jetty-adapter`) is simply a **rock solid** choice

It's still actively being improved

Each time we try an alternative at some point we run into an issue and decide to go back to Jetty

That includes: http-kit, Pedestal, Hirundo/Helidon, Undertow/Pohjavirta

----

## Routing: **Reitit**

We use **Reitit** with **Ring**

Content-negotiation with **Muuntaja**, Malli-based validation/coercion
thank you Metosin!

We've used interceptors with Sieppari (and with Pedestal), never really felt that we needed them

---

## **Reitit** setup

```clojure
(reitit.ring/router
 (build-routes)
 {:data
  {:coercion (reitit.coercion.malli/create ,,,)
   :muuntaja (muuntaja.core/create ,,,)
   :middleware
   [reitit.ring.middleware.muuntaja/format-negotiate-middleware
    reitit.ring.middleware.muuntaja/format-response-middleware
    reitit.ring.middleware.muuntaja/format-request-middleware
    reitit.ring.coercion/coerce-response-middleware
    reitit.ring.coercion/coerce-request-middleware]}})
```

---

## Muuntaja Content Negotiation

```clojure
$ curl http://localhost:8000 -H 'Accept: application/json'
{"foo": "bar"}

$ curl http://localhost:8000 -H 'Accept: application/edn'
{foo: "bar"}

$ curl http://localhost:8000 -H 'Content-Type: application/json' \
  -d '{"foo": "bar"}'
```

----

## Malli coercions

Use reitit `:compile` to extra var metadata

```
(defn PUT-contact
  {:parameters
   {:query {:id int?}
    :body
    [:map {:closed true}
     [:group-id {:optional true} uuid?]
     [:name string?]
     [:email string?]]}}
  [req]
  (-> req :parameters :body :group-id) ;;=> #uuid "..."
  )

(defn routes []
  ["/contact/:id" {:put #'PUT-contact}])
```

----

## HTML: lambdaisland/hiccup

"Yet Another Hiccup Implementation"

Compatible with reagent style hiccup

```clojure
[:<>
 [component-fn] 
 [:span {:styles {:color "blue"}}]]
```

----

## HTML: lambdaisland/hiccup

`lambdaisland.hiccup.middleware/wrap-render`

Extends content-negotiation

```clojure
(defn PUT-contact [req]
  (let [contact (load-contact (-> req :parameters :query :id))]
    {:status 200
     :body contact ;; API response
     :html/body [contact-page contact] ;; HTML response
     }))

(defn routes []
  ["/contact" {:html/layout layout-fn
               :middleware [wrap-render]}
   ["/:id" {:put #'PUT-contact}]])
```

----

## CSS: **Ornament**

<div class=cols style="gap:1rem;">
<div>
```clojure
(ns my-ns
  (:require 
    [lambdaisland.ornament :as o]))

(o/defstyled simple :span
  {:color "#fff"})

(hiccup/render [simple "hello"])
```
</div>
<div>

{: style="visibility:hidden"}
```



```

```css
.my_ns__simple {color: "#fff"}
```

```html
<span class='my_ns__simple'>hello</span>
```
</div>
</div>

----

```clojure
;; garden-style CSS maps
(o/defstyled simple :span
  {:color "#fff"})
  
;; tailwind-style tokens
(o/defstyled button :button
  :rounded-full :bg-gray-100 :border :px-3 :m-1)

;; CSS properties
(o/defprop --primary-dark "#000")

(o/defstyled brand :div
  :font-bold :flex :items-center :text-5xl :gap-2
  {:color --primary-dark})
```

----

```clojure
;; With body function
(o/defstyled welcome-message :div
  :text-center :p-4
  ([]
   [:<> "Hello, World"]))

;; With props
(o/defstyled greeting :div
  :font-semibold
  ([{:keys [name]}]
   [:div (str "Hello, " name)]))
```

----

```clojure
(o/defstyled nav-item :div
  :flex :items-center :cursor-pointer :p-3

  [:&.active 
   {:background-color t/--white-2
    :border-left (str "3px solid " t/--dark-purple)}]
  [:&.inactive
   {:opacity 0.7}]

  ([{:keys [active? on-click]} & children]
   (into 
    [:<>
     {:class (if active? "active" "inactive")
      :on-click on-click}]
    children)))

;; Usage
[nav-item {:active? true :on-click #(>e [:nav/select])} "Home"]
```

----

## **Ornament Rocks**

Only shown the tip of the iceberg

Clojure and ClojureScript

Can read in standard design token JSON

Breakpoints, inheritance, customize tokens

Maintain the full power of CSS

----

## Database: **Datomic** or **PostgreSQL**

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
