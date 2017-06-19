
---

Welcome to Emacs Berlin
---
{: style="font-size: 6rem; text-align: center; margin-top: 1em;"}

February 2017 @ Contentful
{: style="text-align: center; margin-top: 1em;"}

SSID: Contentful Guest
PWD: casacontenta
{: style="text-align: center; margin-top: 1em;"}

---

Emacs And Clojure — <br>A Lispy <3 Affair
---
{: style="font-size: 6rem; text-align: center; margin-top: 1em;"}

<br>
by Arne Brasseur (@plexus)
Clojure Berlin — February 2017
{: style="text-align: center"}


----

![](img/lambdaisland-slide.png){:.cover}

---

## A Lispy Love Affair

Clojure ∈ ℒ &nbsp; ∧ &nbsp; Emacs <3 ℒ &nbsp; ⇒ &nbsp; Emacs <3 Clojure

ℒ: The set of all Lisps

---

## Free Stuff!

- paredit
- smartparens
- parinfer
- rainbow-delimiters
- inferior-lisp
<br>
- aggressive-indent-mode
- subword-mode

---

## Clojure Specific Stuff

- clojure-mode
- inf-clojure
- CIDER
- clj-refactor

---

## Clojure Specific Stuff

- clojure-mode

Programming language major mode

---

## Clojure Specific Stuff

- inf-clojure

Interact with a Clojure REPL process. An alternative to `inf-lisp`.

---

## Clojure Specific Stuff

- CIDER

Use a running Clojure process as a backend for lots of functionality (REPL, font-locking, navigation, ...)

---

## Clojure Specific Stuff

- clj-refactor

Clojure specific refactorings

---

## clojure-mode

All you would expect from a programming language major mode (and more!)

- font-lock
- indentation
- navigation
- refactoring

Four variants: `.clj`, `.cljs`, `.cljc`, `.cljx`

---

## clojure-mode font locking

Is pretty basic.

`clojure-mode-extra-font-locking.el`
provides font-locking for built-ins,
but imprecise.

With CIDER you get smart font-locking

---

## clojure-mode indentation

Follows the community style guide. Can be configured in great detail.

``` clojure
(setq clojure-indent-style :always-align)
;; :always-indent | :align-arguments

(put-clojure-indent 'symbol 2)

(define-clojure-indent
  (-> 1)
  (letfn     '(1 ((:defn)) nil))
  (defrecord '(2 :form :form (1))))
```

---

## clojure-mode indentation

``` clojure
;; before
(def profile-route
  (GET "/user/:id" [id]
       {:body (profile-page (fetch-user id))}))

;; init.el
(put-clojure-indent 'GET 2)

;; after
(def profile-route
  (GET "/user/:id" [id]
    {:body (profile-page (fetch-user id))}))
```

---

## Vertical Alignment

`C-c SPC   ::   clojure-align`
`(setq clojure-align-forms-automatically t)`

``` clojure
(defn guide-data [slug title html]
  (let [slug (str "/guides/" slug)]
    {:title   title
     :slug    slug
     :link    [:a {:href slug} title]
     :content html}))
```

---

## inferior-lisp / inf-clojure

For use with "normal" REPLS (i.e. STDIN/STDOUT)

``` clojure
;; inf-clojure
(setq inf-clojure-program "lein figwheel")
(add-hook 'clojure-mode-hook 'inf-clojure-mode)
```

---

## inf-clojure

Enable `inf-clojure-minor-mode` in source buffer.

`C-c C-z` or `inf-clojure` to start the REPL

`C-x C-e` / `C-M-x` / `C-c C-c` evaluate form

---

## inf-clojure

```
C-c C-z inf-clojure-switch-to-repl
C-c C-i inf-clojure-show-ns-vars
C-c C-A inf-clojure-apropos
C-c C-m inf-clojure-macroexpand
C-c C-l inf-clojure-load-file
C-c C-a inf-clojure-show-arglist
C-c C-v inf-clojure-show-var-documentation
C-c C-s inf-clojure-show-var-source
C-c M-n inf-clojure-set-ns
```

---

## CIDER

Based on nREPL

- Code Completion
- Navigating Stacktraces
- Running Tests
- Debugger
- So much more ...

---

## CIDER DEMO

- cider-jack-in
- jump to definition
- code completion
- tests
- debugger

---

## CIDER and CLJS

Depends on `cider-cljs-lein-repl` / `cider-cljs-boot-repl`

``` clojure
(setq cider-cljs-lein-repl
  "(do (figwheel-sidecar.repl-api/start-figwheel!)
     (figwheel-sidecar.repl-api/cljs-repl))")
```

Cool trick: set in `.dir-locals.el` (Currently broken)

---

## clj-refactor

Originally in Emacs Lisp by Magnar Sveen.

Now largely done with an nREPL backend.

Elisp-only stuff has been moved to clojure-mode.

**DEMO**

---

{:.cover style="font-size: 15rem"}
## FIN
