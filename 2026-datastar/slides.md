# Datastar & Clojure

<div class="bottom-right">
<p>
13th of October 2026, Stockholm
</p>
<p>
A talk by <strong>Arne Brasseur</strong> for <strong>Clojure Café</strong>
</p>
<p>
Fediverse: <strong>@plexus@toot.cat</strong>
</p>
</div>

<!-- ---- -->

<!-- <div data-include-svg-slides="resources/funcprogconf_slides.compressed.svg"></div> -->

----

<!-- ## Compass & HTMX -->

<!-- In 2024 we built the Compass app for Heart of Clojure, using HTMX -->

<!-- ```clojure -->
<!-- (defn layout [] -->
<!--   [:html -->
<!--    [:head -->
<!--     [:script {:src "/js/htmx-1.9.12.js"}] ,,,]]]) -->

<!-- (defn accept-invite-button [qr-uuid] -->
<!--   [:button {:hx-post (url-for :contact/add {:qr-uuid qr-uuid})} "Accept invite"] -->
<!-- ``` -->

<!-- * Include a single, small JS file -->
<!-- * Drive the rest from HTML attributes -->
<!-- * The power of Hypertext! -->

<!-- --- -->


# First, some HTML

---

## HTML: `data-*` attributes

The `data-` prefix is a standard HTML extension mechanism. Turns into `el.dataset` values.

```html
<div id="el0" data-foo="hello" data-on:click="alert('hello')">
  <pre id="el0-out"></pre>
  <script>
    var div = document.getElementById("el0")
    var pre = document.getElementById("el0-out")
    pre.innerText = JSON.stringify(div.dataset);
  </script>
</div>
```

<div data-foo="hello" data-on:click="alert('hello')">
<pre></pre>
<script>
(()=>{
let div = document.currentScript.parentNode;
div.querySelector("pre").innerText = JSON.stringify(div.dataset);
})()
</script>
</div>

<!-- --- -->

<!-- ## HTML: `data-*` attributes -->

<!-- Colon `:`, period `.`, and **double** underscore `__` are valid in `data-` -->
<!-- attribute names. -->

<!-- Syntax has no special meaning (in HTML), but Datastar uses them as modifiers. -->

<!-- Dash `-` becomes `camelCase` -->

<!-- ```html -->
<!-- <div data-foo="1" -->
<!--   data-foo-bal="2" -->
<!--   data-foo:bar="3" -->
<!--   data-foo.baz="4" -->
<!--   data-foo__baq="5"> -->
<!--   <pre></pre> -->
<!--   <script> -->
<!--     let div = document.currentScript.parentNode; -->
<!--     let dataset = div.dataset; -->
<!--     div.querySelector("pre").innerText = JSON.stringify(dataset); -->
<!--   </script> -->
<!-- </div> -->
<!-- ``` -->

<!-- <div data-foo="1"  -->
<!--   data-foo-bal="2" -->
<!-- data-foo:bar="3" data-foo.baz="4" data-foo__baq="5"> -->
<!-- <pre></pre> -->
<!-- <script> -->
<!-- (()=>{ -->
<!-- let div = document.currentScript.parentNode; -->
<!-- div.querySelector("pre").innerText = JSON.stringify(div.dataset); -->
<!-- })() -->
<!-- </script> -->
<!-- </div> -->



---

# Datastar Whirlwind Tour

---

## Datastar Hello World

```html
<!-- <script type="module" src="datastar.js"> -->
<div data-signals:cnt="0">
  <button data-on:click="$cnt--">-1</button>
  <button data-on:click="$cnt++">+1</button><br>
  <div data-text="`cnt is ${$cnt}`"></div>
</div>
```


<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>
---

## Datastar

33KB package, gives you:

* Handling of specific `data-` attributes
* Reactive signals: `data-signals:foo`, `$foo`
* JavaScript superset : `$signal`, `@action`
* Backend requests: `@get`, `@post`, `@put`, `@patch`, ...
* Response handling
  * `text/html` — patch DOM
  * `application/json` — patch signals
  * `text/event-stream` — handle SSE events
  * `text/javascript` — run code

---

## `data-...` attributes

Generally take a JavaScript expression, with some extras:

`$foo` is a signal, makes it reactive

`@foo` for built-in "actions" (most notably HTTP fetch: `@get`, `@post`)

Simplest example: `data-text`, sets the inner text of the element.

```html 
<p>Result: <span data-text="1;2;3"></span></p>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## `data-signals` : setting signals

Sets the value of a signal: 

```html
<div data-signals:foo="123"></div>
```
<pre data-signals:foo="123" data-text="JSON.stringify({foo: 123})"></pre>

You can use JavaScript expressions 

```html
<div data-signals:url="window.location.href"></div>
```

<pre data-signals:url="window.location.href" data-text="JSON.stringify({url: $url})"></pre>

<!-- Careful with strings! `data-signals:foo="'quoted string'"` -->

---

## `data-signals` : setting signals

Set nested values with `.`

```html
<div data-signals:time.now="new Date()"></div>
```
<pre data-signals:time.now="new Date()" data-text="JSON.stringify({time: $time})"></pre>

Alternative syntax, set multiple signals with a JS/JSON object

```html
<div data-signals="{foo: 1, bar: {baz: 2}}"></div>
```
<pre data-signals="{foo: 1, bar: {baz: 2}}" data-text="JSON.stringify({foo:$foo, bar:$bar})"></pre>

<!-- --- -->

<!-- ## `data-signals` : setting signals -->

<!-- Kebab-case becomes camelCase -->

<!-- ```html -->
<!-- <div data-signals:foo-bar="3+3"></div> -->
<!-- ``` -->

<!-- <pre data-signals:foo-bar="3+3" data-text="JSON.stringify({fooBar:$fooBar})"></pre> -->

<!-- Unless specified otherwise (example of a modifier) -->

<!-- ```html -->
<!-- <\!-- modifier -\-> -->
<!-- <div data-signals:foo-bar__case.kebab="3+3"></div> -->
<!-- ``` -->
<!-- <pre data-signals:foo-bar__case.kebab="3+3" data-text="JSON.stringify({'foo-bar':$['foo-bar']})"></pre> -->

---

## `data-bind`: Two-way Data Binding

`data-bind` creates a two way binding

```html
<input type="text" data-signals:txt="'...'" data-bind="txt">
<pre data-text="$txt"></pre>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## `data-computed`: Reactive Signal

Creates a read-only, computed signal

```html
<input type="text" data-bind="txt" data-computed:txt-up="$txt.toUpperCase()">
<pre data-text="$txtUp"></pre>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## `data-init`: One-time side effects 

Runs initialisation code on first render

```html
<div data-signals:num="0"
     data-init="setInterval(()=>{$num++},1000)"
     data-text="'<' + $num + '>'">
</div>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## `data-{attr,style,class,show}`

Make various parts of the DOM dynamic: attributes

```html
<input type="text" data-signals:catsay="'meow'" data-bind="catsay"><br>
<img height="400" data-attr:src="'https://cataas.com/cat/says/' + $catsay">
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## `data-{attr,style,class,show}`

Make various parts of the DOM dynamic: styles

```html
<div
  data-signals:n="0"
  data-init="setInterval(()=>{$n+=2},40)"
  data-computed:r="$n % 255"
  data-computed:g="255 - $r"
  data-computed:b="($r + $g) % 255"
  data-style:background-color="`rgba(${$r}, ${$g}, ${$b}, 1.0)`"
  style="width:50px;height:50px;" />
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Backend requests: `@get` 

<!-- * signals added as query param -->
<!-- * `datastar-request` header -->
<!-- * `Accept: text/event-stream` -->

_Note: you are watching a static version of this talk, so the `/echo` endpoint is not available._

```html
<button data-on:click="@get('/echo')">@get</button>
<pre data-text="$request"></pre>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Backend requests: `@post` 

<!-- * signals added as body -->

_Note: you are watching a static version of this talk, so the `/echo` endpoint is not available._

```html
<button data-on:click="@post('/echo')">@post</button>
<pre data-text="$request"></pre>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Backend response `application/json`

Patches signals

```html
<div data-init="@get('/signals')"></div>
```

```
200 OK
Content-Type: application/json

{"foo": 123}
```


```html
<div data-text="`foo is ${$foo}`"</div>
```

foo is 123

---

## Backend response `text/html`

Patches DOM node

```html
<div data-init="@get('/view')"></div>
```

```
200 OK
Content-Type: text/html
datastar-selector: #my-id
datastar-mode: outer

<main id="my-id">new content</main>
```

Can also `append`, `prepend`, `remove`, ...


---

# Aside: Server-Sent Events (SSE)

---

## Server-Sent Events (SSE)

```
200 OK
Content-Type: text/event-stream

event: my-event
data: hello
data: world

event: next-event
data: hejdå

```

Around since 2004, supported in Firefox since 2011, official recommendation since 2015 (`EventSource` / `onMessage`).

Can be long running (but doesn't have to be).

Sort of like a unidirectional websocket, **but** it's just plain HTTP. No connection upgrades, easy to inspect.

---

# Datastar & SSE

---

## Backend response `text/event-stream`

```html
<div data-init="@get('/updates')"></div>
```

```
200 OK
Content-Type: text/event-stream

event: datastar-patch-elements
data: elements <div id="my-div">content</div>

event: datastar-patch-signals
data: signals {foo: 1, bar: 2}
```

Use either to one-shot batch update DOM and signals, or use as long running "updates" channel.

Works really well with compression! (brotli or zstd)

---

# Datastar <3 Clojure

---

## Datastar <3 Clojure

* Author (Delaney Gillilan) hangs out on Clojurians Slack
* Also gave a talk at the Conj
* Official Clojure SDK
* Anders Murphy's game of life demo got lots of attention (HN etc)
* David Nolen was interviewed on their Youtube channel

---

## Clojure backend for Datastar

It's just HTTP & HTML! ✋🎤 Thank you, we're done!

---

## Clojure backend for Datastar

...

Actually doing a long running SSE stream can be a PITA, depending on which server you're using.

This is the primary reason to use the Datastar Clojure SDK.

Bonus: you can trivially add Brötli compression.

---

## Simple, not easy

Even with SDK, not a plug-and-play framework

Many architectures possible

You need to understand the principles and wire things together

Different apps different needs

---

## Hyperlith / Game of Life

Anders Murphy's Datastar+Clojure framework

Game of Life demo that went #1 on HN

Updates every client 20 times per second

Highly optimised! Manifold, SQLite, custom Hiccup handling, virtual threads...

Undocumented, "not for production"

---

## Datastar Architecture

* Naive / manual patching
* Frame-based / continuous
* Reactive

---

## Naive Datastar Architecture

User action triggers HTTP handler → Response patches relevant part(s) of the DOM

* Tedious to manage
* UI is not live
* No multiplayer
* No CQRS

---

## Frame-based Datastar Architecture

Client opens long running `/updates` connection

Continously (e.g. 20fps) send updated DOM to all connected clients on a timer

Tractable thanks to compression (`br`, `zstd`) + morph

This is what Hyperlith does

---

## Reactive Datastar Architecture

Client opens long running `/updates` connection

State changes (DB) trigger DOM update to all connected clients

Same compression + morph benefits

Optionally diff / debounce

Approach I'm trying (with Datomic `tx-report-queue`)

---

## Datastar Clojure SDK

Handful of different artifacts

Separate SSE implementations for http-kit and Ring.

```clojure
{:deps
 {dev.data-star.clojure/ring   {:mvn/version "1.0.0-RC11"}
  dev.data-star.clojure/brotli {:mvn/version "1.0.0-RC11"}}}
```

---

## Datastar Clojure SDK

Returning an SSE response

```clojure
(ns my-app
  (:require
   [starfederation.datastar.clojure.adapter.ring :refer [->sse-response]]
   [starfederation.datastar.clojure.api :as d*]
   [starfederation.datastar.clojure.brotli :as d*-brotli]))

(defn ring-handler [req]
  (->sse-response
   req
   {:d*.sse/write-profile (d*-brotli/->brotli-profile)
    :d*.sse/on-open
    (fn [sse]
      (d*/patch-elements! sse "<div id=\"question\">What do you put in a toaster?</div>")
      (d*/patch-signals! sse "{response: '', answer: 'bread'}"))}))
```

Connection stays open as long as `on-open` doesn't return.

---

## Basic frontend pattern

```clojure
[:body {:data-signals:tabid (pr-str (hash/random-unique-identifier))
        :data-signals:path "window.location.pathname"
        :data-init "@post('/updates')"}
  [:main {:id "main"} 
  ...]]])
```

`tabid` as an identifier for connections

`path` to know the route the user is on

Now we can send them an updated `<main>` via SSE

---

## Using with Jetty

Use virtual threads!

```clojure
(ns my.app.http
  (:require
   [ring.adapter.jetty :as jetty])
  (:import
   (org.eclipse.jetty.util.thread VirtualThreadPool)))

(defn start [{:keys [port host handler]}]
  (jetty/run-jetty
   handler
   {;; Make it ok to have many open connections
    :thread-pool (VirtualThreadPool.)
    :host        host
    :port        port
    :join?       false}))
```

---

## Track Connections

```clojure
(defonce connections (atom {}))

(defn POST-updates [{:keys [body-params] :as req}]
  (let [{:keys! [tabid path]} body-params]
    (->sse-response
     req
     {:d*.sse/write-profile (d*-brotli/->brotli-profile)
      :d*.sse/on-open
      (fn [sse]
        (swap! connections assoc tabid {:request (clean-req req)
                                        :path    path
                                        :sse-gen sse})
        (while true
          (Thread/sleep 1500)
          (d*-proto/send-event! "ping" nil nil)))
      :d*.sse/on-open
      (fn [_]
        (swap! connections dissoc tabid))})
```

---

## Aside: wrap-render

* Return hiccup as `:html/body`
* Extends content negotiation

```clojure
(defn GET-hello [_req]
  {:status 200 :html/body [:p "hello"]})

;; Pseudocode
(defn wrap-render [handler]
  (fn [req]
    (let [res (handler req)]
      (assoc res :body (hiccup/render (:html/body res))))))
```

---

## React to changes

```clojure
;; pseudocode
(defn re-render [db-after {:keys! [sse-gen path request]}]
  (let [;; synthetic GET request with updated DB and current URL
        req  (assoc request
                    :db db-after
                    :uri path
                    :request-method :get)
        ;; dispatch to ring (including routing, session, etc)
        res  (ring-app-handler req)
        ;; render <main>
        html (hiccup/render [layout/main (:html/body res)] {:doctype? false})]
    ;; Send to frontend
    (d*/patch-elements! sse-gen html)))
```
---

## React to changes

Now we can hook up Datomic `tx-report-q`

```clojure
(while true
 (let [tx-report-q                (d/tx-report-queue conn)
       {:keys [db-after tx-data]} (BlockingQueue/.take tx-report-q)]
   (doseq [tab (vals @updates/connections)]
     (re-render db-after tab))))
```

---

## Next steps

* `:html/body [my-page-view some-data]` — effectively lazy evaluation of rendering
* Store previous `:html/body` form, compare, only re-render if changed
* Requires all view data to be passed in as pure data
* `datomic.api/entity` is out, different db basis is always unequal

---

# Thank You! Q&A?

---

```html 
<div data-json-signals></div>
```

<div data-json-signals></div>
