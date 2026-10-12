
<script>
window.cat_api_key="live_gH8O7Z1zI35tAYO61lZLKQOzc5m0UjtShvzNLZOxTAmNYN15RAwgAAou7zYpiiDb"
</script>

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

----

<div data-include-svg-slides="resources/funcprogconf_slides.compressed.svg"></div>

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
<div data-foo="hello">
  <pre></pre>
  <script>
    let div = document.currentScript.parentNode;
    let dataset = div.dataset;
    div.querySelector("pre").innerText = JSON.stringify(dataset);
  </script>
</div>
```

<div data-foo="hello">
<pre></pre>
<script>
(()=>{
let div = document.currentScript.parentNode;
div.querySelector("pre").innerText = JSON.stringify(div.dataset);
})()
</script>
</div>

---

## HTML: `data-*` attributes

Colon `:`, period `.`, and **double** underscore `__` are valid in `data-`
attribute names.

Syntax has no special meaning (in HTML), but Datastar uses them as modifiers.

Dash `-` becomes `camelCase`

```html
<div data-foo="1"
  data-foo-bal="2"
  data-foo:bar="3"
  data-foo.baz="4"
  data-foo__baq="5">
  <pre></pre>
  <script>
    let div = document.currentScript.parentNode;
    let dataset = div.dataset;
    div.querySelector("pre").innerText = JSON.stringify(dataset);
  </script>
</div>
```

<div data-foo="1" 
  data-foo-bal="2"
data-foo:bar="3" data-foo.baz="4" data-foo__baq="5">
<pre></pre>
<script>
(()=>{
let div = document.currentScript.parentNode;
div.querySelector("pre").innerText = JSON.stringify(div.dataset);
})()
</script>
</div>


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

`$foo` to refer to a signal

`@foo` for built-in "actions" (most notably HTTP fetch: `@get`, `@post`)

Can contain arbitrary code, value of final expression is used.

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

Careful with strings! `data-signals:foo="'quoted string'"`

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

---

## `data-signals` : setting signals

Kebab-case becomes camelCase

```html
<div data-signals:foo-bar="3+3"></div>
```

<pre data-signals:foo-bar="3+3" data-text="JSON.stringify({fooBar:$fooBar})"></pre>

Unless specified otherwise (example of a modifier)

```html
<!-- modifier -->
<div data-signals:foo-bar__case.kebab="3+3"></div>
```
<pre data-signals:foo-bar__case.kebab="3+3" data-text="JSON.stringify({'foo-bar':$['foo-bar']})"></pre>

---

## Other ways to set signals: `data-bind`

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

## Other ways to set signals: `data-computed`

`data-computed` creates a reactive signal

```html
<input type="text" data-bind="txt" data-computed:txt-up="$txt.toUpperCase()">
<pre data-text="$txtUp"></pre>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Signals to DOM: `data-{text,attr,style,class,show}`

`data-text` populates the inner text of an element

`data-init` runs one-time initialisation

```html
<div
  data-signals:num="0"
  data-init="setInterval(()=>{$num++},1000)"
  data-text="'<' + $num + '>'"</div>
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Signals to DOM: `data-{text,attr,style,class,show}`

`data-attr` to populate an attribute

```html
<input type="text" data-signals:catsay="'meow'" data-bind="catsay"><br>
<img height="400" data-attr:src="'https://cataas.com/cat/says/' + $catsay">
```

<script>
var el = document.currentScript.parentNode;
el.innerHTML+=el.querySelector('.language-html').innerText;
</script>

---

## Signals to DOM: `data-{text,attr,style,class,show}`

`data-style` set style properties

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

* signals added as query param
* `datastar-request` header
* `Accept: text/event-stream`

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

* signals added as body

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

## Backend response `text/event-stream`

```html
<div data-init="@get('/updates')"></div>
```

```
200 OK
Content-Type: text/event-stream

event: datastar-patch-elements
data: elements <div id="my-div">content</div>

event: datastar-patch-elements
data: selector #other-id
data: mode remove

event: datastar-patch-signals
data: signals {foo: 1, bar: 2}

...
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

It's just a web app! ✋🎤 Thank you, we're done!

---

## Clojure backend for Datastar

...

Actually doing a long running SSE stream can be a PITA, depending on which server you're using.

This is the primary reason to use the Datastar Clojure SDK.

Bonus: you can trivially add Brötli compression.

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

## Let's talk architecture

Can we have holy grail of full reactivity from database to UI?

UI always up to date

Multiplayer out of the box

No need to track fine-grained patching

**Yes! But...** devil is in the details

---

## Basic frontend pattern

```html
<body data-init="@get('/updates')">
  <main id="main">
  </main>
</body> 
```

```
event: datastar-patch-elements
data: elements <main id="main">
data: elements   first version
data: elements </main>

event: datastar-patch-elements
data: elements <main id="main">
data: elements   second version
data: elements </main>

...
```

Frontend continuously receives the full `<main>` element, either on a timer, or reactively on each change.

---

## Basic frontend pattern

Is it wasteful to send the full page over the wire time and time again?

No! Brotli or zstandard make most of it go away! "Compression is your vdom"

Datastar "morphs" in any changes: only does necessary DOM changes.

Shifts the hard work to the backend, can your backend handle it?

---

## Hyperlith / Game of Life

Anders Murphy's demo that went #1 on HN

Updates every client 20 times per second

Highly optimised! Manifold, SQLite, custom Hiccup handling, virtual threads...

---

## My approach

Much more modest needs 

Datomic is primary database

Keep navigation as full page loads


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

<div data-json-signals></div>
