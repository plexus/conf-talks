
# Kaocha: a Functional Test Runner

<!-- ---- -->
<!-- {: fullscreen-img="img/title_image.png"} -->

----

![](img/kaocha_logo.png){: style="margin: 4.5em 0 0 1.3em ; width: 90%"}


<!-- a Functional Test Runner -->
<!-- {: style="font-size: 3rem; text-align: center;"} -->


----
## About me

My name is Arne Brasseur (@plexus)

I'm from Belgium, but based in Berlin.
{:.next}

I do Clojure consulting & training. I'm in Singapore to visit my client,
[runeleven.com](https://runeleven.com).
{:.next}

---
## Lambda Island

Detailed screencasts about all things Clojure & ClojureScript.

Been going for ~3.5 years.
{:.next}

49 high-quality videos
{:.next}

Attractive Team Packages
{:.next}

---
## [Gaiwan.co](http://gaiwan.co)

Boutique Clojure Consultancy and Agency, founded beginning of this year.

Slowly bootstrapping, currently 3 of us.
{:.next}

Available for project work and training.
{:.next}

---
{:.header}

# Kaocha

---
{: fullscreen-img="img/company_logos.png"}

---
{:.header}

# Kaocha Basics

---
## Kaocha Basics

Clojure CLI (deps.edn)

``` clojure
{:aliases
 {:kaocha
  {:extra-deps {lambdaisland/kaocha {:mvn/version "0.0-554"}}
   :main-opts ["-m" "kaocha.runner"]}}}
```

---
## Kaocha Basics

Leiningen (project.clj)

``` clojure
(defproject my-proj "0.1.0"
  :profiles
  {:kaocha
   {:dependencies [[lambdaisland/kaocha "0.0-554"]]}}

  :aliases
  {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]})
```

---
## Kaocha Basics

Boot (build.boot)

``` clojure
(set-env! :source-paths #{"src"}
          :dependencies '[[lambdaisland/kaocha-boot "0.0-14"]])

(require '[kaocha.boot-task :refer [kaocha]])
```

---
## Kaocha Basics

```
$ clj -A:kaocha
[(...)(..F.)]
7 tests, 12 assertions, 1 failures
```

Kaocha finds, loads, and runs tests.
{:.next}

Works with zero config out of the box  ⃰
{:.next}

---
{:.header}

# Why Kaocha?

---
## Guiding Principles

- One tool for all testing needs
{:.next}

- Easy to use, pleasant to work with
{:.next}

- Data driven, good foundation for tooling
{:.next}

- Highly extensible
{:.next}

- "The Holy Grail of Test Tools"
{:.next}

---
## One tool for all testing needs

Independent of project tool<br> <span style="color: #888;">e.g. Leiningen / Clojure CLI / boot</span>
{:.next}

Independent of testing library<br> <span style="color: #888;">e.g. clojure.test, cljs.test, Expectation, Midje, Cucumber</span>
{:.next}

Suitable for any context or workflow<br> <span style="color: #888;">e.g. local, REPL, CI, watch mode</span>
{:.next}

---
## Easy to use, pleasant to work with

Can be adopted with minimal setup
{:.next}

Friendly warning and error messages
{:.next}

Output optimized for human consumption (e.g. deep-diff)
{:.next}

---
{: fullscreen-img="https://raw.githubusercontent.com/lambdaisland/deep-diff/master/screenshot.png"}

---
## Full featured

Has everything you would expect from a test runner in 2019.

``` shell
$ clj -A:kaocha --watch
```

``` shell
$ clj -A:kaocha --fail-fast
```

``` shell
$ clj -A:kaocha --focus my.foo-test
```

Much more provided by plugins

---
## Also Great from a REPL

```
(kaocha.repl/run)     ;; current ns
(kaocha.repl/run-all) ;; everything
;;=> #:kaocha.result{:count 7, :pass 5, :error 1, :fail 1}

(kaocha.repl/run 'my.foo-test 'my.bar-test/baz) ;; specific ns, var
(kaocha.repl/run {:fail-fast? true}) ;; extra config
```

`magnars/kaocha-runner.el`
`liquidz/kaocha-nrepl`
`liquidz/vim-iced-kaocha`

---
## Data driven, good foundation for tooling

``` clojure
$ clj -A:kaocha --print-config
{:kaocha/tests [{:kaocha.testable/id   :unit
                 :kaocha.testable/type :kaocha.type/clojure.test
                 :kaocha/source-paths  ["src"]
                 :kaocha/test-paths    ["test"]
                 :kaocha/ns-patterns   ["-test$"]}]
 :kaocha/fail-fast? false
 :kaocha/color?     true
 :kaocha/reporter   [kaocha.report/dots]
 :kaocha/plugins [:kaocha.plugin/randomize
                  :kaocha.plugin/filter
                  :kaocha.plugin/capture-output]}
```

---
## Data driven, good foundation for tooling

``` clojure
;; tests.edn
#kaocha/v1
{:tests    [{:test-paths ["test/clj"]}]
 :reporter [kaocha.report/documentation]
 :plugins  [:kaocha.plugin/notifier]}
```

`#kaocha/v1` provides shorthand, defaults, and normalization.

---
## Data driven, good foundation for tooling

``` clojure
(-> "tests.edn"
    kaocha.config/load-config
    kaocha.api/test-plan
    kaocha.api/run)
```

It's just a data pipeline!

`config` ---load---> `test-plan` ---run---> `test-result`

Two data transformations, three data structures, each one augmenting the
previous one.

---

``` clojure
$ clj -A:kaocha --print-config
{:kaocha/tests
 [{:kaocha.testable/id   :unit
   :kaocha.testable/type :kaocha.type/clojure.test
   :kaocha/source-paths  ["src"]
   :kaocha/test-paths    ["test"]
   :kaocha/ns-patterns   ["-test$"]}]
 :kaocha/fail-fast? false
 :kaocha/color?     true
 :kaocha/reporter   [kaocha.report/dots]
 :kaocha/plugins [:kaocha.plugin/randomize
                  :kaocha.plugin/filter
                  :kaocha.plugin/capture-output]}
```


---
```clojure
$ clj -A:kaocha --print-test-plan
{:kaocha/tests
 [{:kaocha.testable/id   :unit
   :kaocha.testable/type :kaocha.type/clojure.test
   :kaocha/tests ;; <-- new
   [{:kaocha.testable/id   :my.foo-test
     :kaocha.testable/type :kaocha.type/ns
     :kaocha/tests
     [{:kaocha.testable/id   :my.foo-test/bar-test
       :kaocha.testable/type :kaocha.type/var
       :kaocha.var/test      <test-fn>
       ,,,}]}]}]
 ,,,}
```

---
```clojure
$ clj -A:kaocha --print-result
{:kaocha/tests
 [{:kaocha/tests
   [{:kaocha/tests
     [{:kaocha.testable/id    :my.foo-test/bar-test
       ;; v---- new
       :kaocha.result/error   0
       :kaocha.result/pending 0
       :kaocha.result/pass    0
       :kaocha.result/count   1
       :kaocha.result/fail    1
       :kaocha.result/events [{:file "foo_test.clj",
                               :type :fail,
                               :line 5,
                               :expected (= [2 8 5] (sort [4 8 2])),
                               }]
       ,,,}}]}]}]}
```




---
## Highly extensible

Everybody's needs are different, people have strong opinions about testing and tools.

Solution: lean core, extensible architecture

- Test types
- Plugins
- Reporters


---
## Test Types

``` clojure
(ns kaocha.type.cljs)

(defmethod kaocha.testable/-load :kaocha.type/cljs [testable]
  (merge testable child-tests-and-metadata))

(defmethod kaocha.testable/-run :kaocha.type/cljs [testable test-plan]
  (merge testable results))

(hierarchy/derive! :kaocha.type/cljs :kaocha.testable.type/suite)
```

---
## Plugins

``` clojure
(ns kaocha.plugin.version-filter)

(defn skip? [testable]
  (let [{:keys [min-clojure-version max-clojure-version]}
        (:kaocha.testable/meta testable)]
    ,,,))

(kaocha.plugin/defplugin kaocha.plugin/version-filter
  (pre-test [testable test-plan]
    (if (skip? testable)
      (assoc testable :kaocha.testable/skip true)
      testable)))
```

---
## Plugins

``` clojure
#kaocha/v1 {:plugins [:kaocha.plugin/version-filter]}
```

\- or -

``` clojure
$ clj -A:kaocha --plugin version-filter
```

code

``` clojure
(deftest ^{:min-clojure-version "1.10"} my-spec-test
  ,,,)
```

---
## Plugin Hooks

`cli-options` `config`
`pre-load` `post-load`
`pre-run` `post-run`
`wrap-run`
`pre-test` `post-test`
`pre-report`

---
## Plugins

- version-filter
- notifier
- profiling
- randomize
- junit.xml output
- spect-test-check
- ...

---
## Reporters

Generates the actual output, basically an event handler.

This piggiebacks on clojure.test reporters, but made more general.

---

``` clojure
(defmulti my-reporter :type)

(defmethod my-reporter :kaocha/begin-suite [e]
  (print "["))

(defmethod my-reporter :kaocha/end-suite [e]
  (print "]"))

(defmethod my-reporter :kaocha/begin-group [e]
  (print "("))

(defmethod my-reporter :kaocha/end-group [e]
  (print ")"))
```

`:fail`, `:error`, `:pass`, `:summary`, ...

---
## Kaocha-cljs

Easiest way to start doing ClojureScript testing.

Uses ClojureScript's repl-env abstraction under the hood.

``` clojure
#kaocha/v1
{:tests [{:id :frontend :type :kaocha.type/cljs}
         {:id :backend  :type :kaocha.type/clojure.test}]}
```

``` clojure
$ clj -A:kaocha frontend
```

---
``` shell
$ clj -A:kaocha --help
USAGE: bin/kaocha [OPTIONS]... [TEST-SUITE]...

  -c, --config-file FILE     tests.edn  Config file to read.
      --print-config                    Print out the fully merged and normalized config, then exit.
      --print-test-plan                 Load tests, build up a test plan, then print out the test plan and exit.
      --print-result                    Print the test result map as returned by the Kaocha API.
      --fail-fast                       Stop testing after the first failure.
      --[no-]color                      Enable/disable ANSI color codes in output. Defaults to true.
      --[no-]watch                      Watch filesystem for changes and re-run tests.
      --reporter SYMBOL                 Change the test reporter, can be specified multiple times.
      --plugin KEYWORD                  Load the given plugin.
      --profile KEYWORD                 Configuration profile. Defaults to :default or :ci.
      --version                         Print version information and quit.
      --help                            Display this help message.
  -H, --test-help                       Display this help message.
      --[no-]randomize                  Run test namespaces and vars in random order.
      --seed SEED                       Provide a seed to determine the random order of tests.
      --skip SYM                        Skip tests with this ID and their children.
      --focus SYM                       Only run this test, skip others.
      --skip-meta SYM                   Skip tests where this metadata key is truthy.
      --focus-meta SYM                  Only run tests where this metadata key is truthy.
      --[no-]capture-output             Capture output during tests.
```
{: style="font-size: 0.6em"}

---
{:.header}

# THE END
