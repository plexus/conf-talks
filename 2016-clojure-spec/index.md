----
{:#cover}

## Introducing clojure.spec

A presentation by [Arne Brasseur](https://devblog.arnebrasseur.net)

for [ClojuTRE 2016](http://clojutre.org/).

10 September 2016

----

![](img/lambdaisland-slide.png){:.cover}

---


<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">Especially for <a href="https://twitter.com/hashtag/ClojuTRE?src=hash">#ClojuTRE</a>, sign up this weekend and get your first month free with this coupon link <a href="https://t.co/HkHoSxnkjP">https://t.co/HkHoSxnkjP</a></p>&mdash; Lambda Island (@lambdaisland) <a href="https://twitter.com/lambdaisland/status/774203033286742016">September 9, 2016</a></blockquote>
<script async src="http://platform.twitter.com/widgets.js" charset="utf-8"></script>

&nbsp;


{:style="font-size: 2rem"}
[https://lambdaisland.com/coupon/CLOJUTRE2016](https://lambdaisland.com/coupon/CLOJUTRE2016)

----

## clojure.spec

New library in Clojure 1.9 (now in alpha)

* Describe shape of data
* Validate data
* "Parse" datastructures
* Instrument functions and macros
* Drive generative testing

---

## Somebody please think of the types!

Clojure is a **dynamic language**, for better or worse

Specs give you some of the benefits of a type system (+ more)

Interesting precedent for how a dynlang handles these concerns

---

## Somebody please think of the types!

Main difference: **runtime** vs **compile time** checks

Specs are checked at runtime = overhead = only in dev env

But: macro expension checked at compile time

<!-- --- -->

<!-- ## Agenda -->

<!-- `10` What does Clojure data look like? -->
<!-- `20` How do I create and register a spec? -->
<!-- `30` What can I do with specs? -->
<!-- `40` `GOTO 20` -->

<!-- --- -->

<!-- ## Agenda -->

<!-- `10` What does Clojure data look like? -->
<!-- `20` How do I create and register a spec? -->
<!-- `21` &nbsp;&nbsp; Maps -->
<!-- `22` &nbsp;&nbsp; Sequences -->
<!-- `30` What can I do with specs? -->
<!-- `40` `GOTO 20` -->

<!-- --- -->

<!-- ## Agenda -->

<!-- `10` What does Clojure data look like? -->
<!-- `20` How do I create and register a spec? -->
<!-- `30` What can I do with specs? -->
<!-- `30` &nbsp;&nbsp; Instrumenting Functions & Macros -->
<!-- `31` &nbsp;&nbsp; Generative Testing -->
<!-- `40` `GOTO 20` -->

<!-- --- -->

<!-- ## Data in Clojure -->

<!-- Vectors `[]` and Maps `{}` are main data structures -->

<!-- Clojurists like "unadorned" data: structs/records mainly used for interop -->

<!-- Result: maps and keywords everywhere -->

<!-- ``` clojure -->
<!-- {:uri "/" -->
<!--  :method :post -->
<!--  :headers {"Accept" "*/*"}} -->
<!-- ``` -->

<!-- ---- -->

<!-- ## Keywords -->

<!-- Like Ruby's `Symbol`, lightweight immutable string -->

<!-- {:style="margin-bottom: 0"} -->
<!-- Used for: -->

<!-- * key lookup in maps/records -->
<!-- * "enum" -->

<!-- ``` clojure -->
<!-- :created-on -->
<!-- :put -->
<!-- :cemerick.friend/identity -->
<!-- ``` -->

<!-- ---- -->

<!-- ![](img/keywords-everywhere.png){:.cover} -->

<!-- ---- -->

<!-- ## Keywords everywhere -->

<!-- Chance of collission -->

<!-- Answer: namespaced keywords -->

<!-- ``` clojure -->
<!-- {:my.audio.lib/encoding :ogg-vorbis -->
<!--  :transfer/encoding     :base64} -->
<!-- ``` -->


----

## Clojure 1.9

Currently in alpha

- Leiningen

``` clojure
(defproject robochef "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]])
```

- Boot

``` clojure
(set-env!
  :dependencies '[[org.clojure/clojure "1.9.0-alpha12"]])
```

---

## An example (bear with me)

We have a Robot Chef which works with recipes

``` clojure
(def tomato-sauce-recipe
  {:robochef/ingredients [250 :g "peeled tomatoes"
                          3 :clove "garlic"
                          5 :g "pepper"]
   :robochef/steps ["heat a pan"
                    "throw everything in"
                    "stir"]})
```

---

## An aside: Namespaced keywords

``` clojure
user> :greetings/kiitos
:greetings/kiitos
```

A keyword containing a slash

Namespace doesn't have to be loaded or even exist

---

## Why namespaced keywords?

Avoid collissions

``` clojure
{:http/method :get
 :robochef/method :stir}
```

Stable semantics

``` clojure
{:robochef/recipe {,,,}}
```

---


## Syntactical Sugar

``` clojure
robochef.core> :robochef.core/ingredients
:robochef.core/ingredients
```

---

## Syntactical Sugar

``` clojure
robochef.core> :robochef.core/ingredients
:robochef.core/ingredients
```

``` clojure
robochef.core> ::ingredients
:robochef.core/ingredients
```

---

## Syntactical Sugar

``` clojure
robochef.core> :robochef.core/ingredients
:robochef.core/ingredients
```

``` clojure
robochef.core> ::ingredients
:robochef.core/ingredients
```

``` clojure
user> (require '[robochef.core :as rc])
nil
user> ::rc/ingredients
:robochef.core/ingredients
```

---

## Namespaced maps

Very common to have the same prefix for all keys

``` clojure
{:robochef/recipe-name "..."
 :robochef/ingredients [,,,]
 :robochef/steps [,,,]
 :robochef/cooking-time 30}
```

---

## Namespaced maps

Very common to have the same prefix for all keys

``` clojure
{:robochef/recipe-name "..."
 :robochef/ingredients [,,,]
 :robochef/steps [,,,]
 :robochef/cooking-time 30}
```

New syntax for this in 1.9 \o/

``` clojure
#:robochef{:recipe-name "..."
           :ingredients [,,,]
           :steps [,,,]
           :cooking-time 30}
```

---

## Namespaced maps

Destructuring support in 1.9 \o/

``` clojure
(def recipe #:robochef{:recipe-name "..."
                       :ingredients [,,,]
                       :steps [,,,]
                       :cooking-time 30})

(let [{:robochef/keys [steps serves]} recipe]
  (doseq [s steps]
    ,,,)
```

----

{:style="margin-top: 25%; text-align: center; font-size: 500%;"}
## clojure.spec


---

## Let's get started!

Load spec in your namespace, aliased to `s`

``` clojure
(ns robochef.core
  (:require [clojure.spec :as s]))
```

----

## An example Spec

``` clojure
;; keep in mind ::recipe == :robochef.core/recipe

(s/def ::recipe (s/keys :req [::ingredients]
                        :opt [::steps]))

(s/def ::ingredients (s/* (s/cat :amount number?
                                 :unit   keyword?
                                 :name   string?)))

(s/def ::steps ,,,)
```

This registers specs in a **global registry**

----

## Basic usage

``` clojure
(s/valid? :robochef/ingredients [5 :g "tea"])
;;=> true

(s/conform :robochef/ingredients [5 :g "tea"])
;; [{:amount 5, :unit :g, :name "tea"}]
```

----

## Invalid & explain

``` clojure
(s/valid? :robochef/ingredients ["10" :g "tea"])
;;=> false

(s/conform :robochef/ingredients ["10" :g "tea"])
;;=> :clojure.spec/invalid

(s/explain :robochef/ingredients ["10" :g "tea"])
;; In: [0] val: "10" fails spec:
;;   :robochef/ingredients at: [:amount] predicate: number?
```

----

## Generators!

``` clojure
(s/exercise :robochef/ingredients 2)
;; ([() []]
;;  [(0 :Hi "0") [{:amount 0, :unit :Hi, :name "0"}]])
```

----

## Spec types

Predicate

{:.indent}
``` clojure
map?
```

Spec object

{:.indent}
``` clojure
(s/or :s string?, :n number?)
(s/coll-of number? :kind vector?)
```

Name of a registered spec

{:.indent}
``` clojure
:robochef/ingredients
```

---

## New predicates \o/

``` clojure
bigdec?             any?
double?             seqable?
int?                indexed?
nat-int?            ident?
neg-int?            qualified-ident?
pos-int?            qualified-keyword?
boolean?            qualified-symbol?
bytes?              simple-ident?
uri?                simple-keyword?
uuid?               simple-symbol?
```

Including generators \o/

<!-- ---- -->

<!-- ## Spec operations -->

<!-- * `describe` -->
<!-- * `valid?` -->
<!-- * `conform` -->
<!-- * `explain` -->
<!-- * `gen` -->

----

## Creating specs

<!-- We have some basic spec objects for collections -->

<!-- * `coll-of` -->
<!-- * `map-of` -->

Two "advanced" types of specs:

* `keys` for dealing with maps
* "regexp" for dealing with sequences

----

## Spec'ing maps

Done with `s/keys`

``` clojure
(def tomato-sauce-recipe
  {:robochef/ingredients [250 :g "peeled tomatoes"
                          3 :clove "garlic"
                          5 :g "pepper"]
   :robochef/steps ["stir"]})

(s/def :robochef/recipe (s/keys :req [:robochef/ingredients]
                                :opt [:robochef/steps]))

(s/def :robochef/ingredients ,,,)

(s/def :robochef/steps ,,,)
```

----

## Spec'ing maps

> Most systems for specifying structures conflate the specification of the key set with the specification of the values designated by those keys. This is a major source of rigidity and redundancy.
> — Rich Hickey

&nbsp;

`s/keys` will look at every key in a map, try to find a spec with that name, use it to validate the corresponding value

----

## `s/keys` "naturally extensible"

``` clojure
(s/def :robochef/recipe (s/keys))

(def recipe {:robochef/ingredients [,,,]
             :robochef/steps [,,,]
             :dinnerparty/serves 6})

(s/def :dinnerparty/serves pos-int?)
```

----

## Spec'ing sequences

Clojure data structures all share an underlying **"sequence"** abstraction.

clojure.spec contains full **regular expression engine** for dealing with these.

<!-- Based on a paper *"Parsing with Derivatives"*. Powerful enough to parse **context-free grammars**! -->

----

## Regexp specs

Five "Regex" operators: `*`, `+`, `?`, `cat`, `alt`

``` clojure
(s/conform (s/* keyword?) [])      ;;=> []
(s/conform (s/* keyword?) [:a])    ;;=> [:a]
(s/conform (s/* keyword?) [:a :b]) ;;=> [:a :b]

(s/conform (s/+ keyword?) [])      ;;=> :clojure.spec/invalid
(s/conform (s/+ keyword?) [:a])    ;;=> [:a]
(s/conform (s/+ keyword?) [:a :b]) ;;=> [:a :b]

(s/conform (s/? keyword?) [])      ;;=> nil
(s/conform (s/? keyword?) [:a])    ;;=> :a
(s/conform (s/? keyword?) [:a :b]) ;;=> :clojure.spec/invalid
```

----

## Regexp operators: cat

`cat` "concatentate" sequence items (first this, then that)

``` clojure
(s/conform (s/cat :num number?, :key keyword?) [5 :b])
;;=> {:num 5, :key :b}
```

{:style="padding-top: 1em"}
Each item gets a **name**

The conformed result is a **map** that can easily be consumed with Clojure's **destructuring**

---

## Regexp operators: alt

`alt` distinguishes "alternatives" (either this, or that, like `|`)

``` clojure
(s/conform (s/alt :num number?,:key keyword?) [5])
;;=> [:num 5]
(s/conform (s/alt :num number?,:key keyword?) [:b])
;;=> [:key :b]
```

{:style="padding-top: 1em"}
Each alternative gets a **name**

The conformed result is a `MapEntry` which can be used with `core.match` **pattern matching**

---

## Regexp spec

``` clojure
(s/def :robochef/ingredients (s/* (s/cat :amount number?
                                         :unit   keyword?
                                         :name   string?)))

{:robochef/ingredients [250 :g "peeled tomatoes"
                        3 :clove "garlic"
                        5 :g "pepper"]}

[{:amount 250, :unit :g, :name "peeled tomatoes"}
 {:amount 3, :unit :clove, :name "garlic"}
 {:amount 5, :unit :g, :name "pepper"}]
```

<!-- ---- -->

<!-- ## Alt vs Or -->

<!-- Clojure spec also has `s/or` which is similar to `s/alt` -->

<!-- ``` clojure -->
<!-- (s/conform (s/or :num number?, :str string?) 5)               ;;=> [:num 5] -->
<!-- (s/conform (s/alt :num number?, :str string?) [5])            ;;=> [:num 5] -->
<!-- ``` -->

<!-- In fact you might wonder why you need both -->

<!-- ``` clojure -->
<!-- (s/conform (s/cat :x (s/or :num number?, :str string?)) [5])  ;;=> {:x [:num 5]} -->
<!-- (s/conform (s/cat :x (s/alt :num number?, :str string?)) [5]) ;;=> {:x [:num 5]} -->
<!-- ``` -->

<!-- ---- -->

<!-- ## Alt vs Or -->

<!-- But `alt` partakes in the current regex, `or` starts a new spec. -->

<!-- ``` clojure -->
<!-- (s/def ::cat-alt (s/cat :1 (s/alt :nums (s/+ number?) -->
<!--                                   :strs (s/+ string?)) -->
<!--                         :2 (s/alt :keys (s/+ keyword?) -->
<!--                                   :syms (s/+ symbol?)))) -->

<!-- (s/conform ::cat-alt [1 2 3 :a :b :c])   ;;=> {:1 [:nums [1 2 3]],   :2 [:keys [:a :b :c]]} -->
<!-- (s/conform ::cat-alt ["a" "b" 'x 'y])    ;;=> {:1 [:strs ["a" "b"]], :2 [:syms [x y]]} -->
<!-- (s/conform ::cat-alt [1 2 :a 'b])        ;;=> :clojure.spec/invalid -->


<!-- (s/def ::cat-or (s/cat :1 (s/or :nums (s/+ number?) -->
<!--                                 :strs (s/+ string?)) -->
<!--                        :2 (s/or :keys (s/+ keyword?) -->
<!--                                 :syms (s/+ symbol?)))) -->

<!-- (s/conform ::cat-or [[1 2 3] [:a :b :c]])  ;;=> {:1 [:nums [1 2 3]],   :2 [:keys [:a :b :c]]} -->
<!-- (s/conform ::cat-or [["a" "b"] ['x 'y]])   ;;=> {:1 [:strs ["a" "b"]], :2 [:syms [x y]]} -->
<!-- ``` -->

<!-- ---- -->

<!-- ## Spec'ing sequences -->

<!-- Suppose we want a spec for the arguments or `case` -->

<!-- ``` clojure -->
<!-- (comment (case x -->
<!--            :foo 7 -->
<!--            :bar 42 -->
<!--            "else")) -->

<!-- (s/def ::case-args (s/cat :form    :clojure.spec/any -->
<!--                           :clauses (s/* (s/cat :test   :clojure.spec/any -->
<!--                                                :result :clojure.spec/any)) -->
<!--                           :else    (s/? :clojure.spec/any))) -->

<!-- (s/conform ::case-args '(x :foo 7 :bar 42 "else")) -->
<!-- ;;=> {:form x, :clauses [{:test :foo, :result 7} {:test :bar, :result 42}], :else "else"} -->
<!-- ``` -->

<!-- Notice how great destructuring works on this conformed data -->

<!-- ``` clojure -->
<!-- (let [conformed (s/conform ::case-args '(x :foo 7 :bar 42 "else")) -->
<!--       {:keys [form clauses else]} conformed] -->
<!--   (doseq [{:keys [test result]} clauses] -->
<!--     ,,,)) -->
<!-- ``` -->

<!-- ---- -->

<!-- ## Putting it all together -->

<!-- ``` clojure -->
<!-- (s/def ::hiccup (s/or :string  string? -->
<!--                       :element ::element)) -->

<!-- (s/def ::element (s/and vector? -->
<!--                         (s/cat :tag keyword? -->
<!--                                :attrs (s/? map?) -->
<!--                                :content (s/* ::hiccup)))) -->

<!-- (s/conform ::hiccup "foo") -->
<!-- (s/conform ::hiccup [:div -->
<!--                      [:p "hello"] -->
<!--                      [:img {:src "/parrot.gif"}]]) -->
<!-- ``` -->


<!-- ---- -->

<!-- ## Spec'ing collections -->

<!-- `(s/map-of <key-spec> <value-spec>)` -->

<!-- ``` clojure -->
<!-- (s/valid? (s/map-of keyword? number) {:x 3}) -->
<!-- ``` -->

<!-- `(s/coll-of <item-spec> <seed-collection>)` -->

<!-- ``` clojure -->
<!-- (s/valid? (s/coll-of string? []) ["foo"]) -->
<!-- ``` -->

<!-- ---- -->

<!-- ## Spec'ing collections -->

<!-- Most powerful: `keys` -->

<!-- Keyword used both for lookup in the map, and to find a spec in the registry -->

<!-- ``` clojure -->
<!-- (s/def ::recipe (s/keys :req [::ingredients] -->
<!--                         :opt [::steps])) -->
<!-- (s/def ::ingredients (s/* ::ingredient)) -->
<!-- (s/def ::ingredient (s/cat :amount number? -->
<!--                            :unit   keyword? -->
<!--                            :name   string?)) -->
<!-- (s/def ::steps (s/* string?)) -->
<!-- (def recipe -->
<!--   {::ingredients [1 :kg "aubergine" -->
<!--                   20 :ml "soy sauce"] -->
<!--    ::steps ["fry the aubergines" -->
<!--             "add soy sauce"]}) -->
<!-- (s/valid? ::recipe recipe) ;;=> true -->
<!-- ``` -->


----

## Instrumenting functions

`fdef` lets you set specs on the arguments, return value, and the relationship between them.

<!-- ``` clojure -->
<!-- (defn cook! [recipe] -->
<!--   (let [{i ::ingredients} (s/conform ::recipe recipe)] -->
<!--     (reduce (fn [acc {:keys [amount]}] -->
<!--               (+ acc amount)) -->
<!--             0 -->
<!--             i))) -->
<!-- (s/fdef cook! -->
<!--   :args (s/cat :recipe ::recipe) -->
<!--   :ret  number? -->
<!--   :fn   #(> (:ret %) (count (-> % :args :recipe ::ingredients)))) -->
<!-- (s/instrument #'cook!) -->
<!-- (s/unstrument #'cook!) -->
<!-- (cook! recipe) -->
<!-- (cook! {}) -->
<!-- ``` -->


``` clojure
(defn cook! [recipe]
  ,,,)

(s/fdef cook! :args (s/cat :recipe ::recipe)
              :ret  number?)

(s/instrument-all)
;; (s/unstrument-all)
```

---

## Instrumenting functions

A macro is **just a function** that takes **"code as data"** and returns "code as data"

We can **instrument it** just like function

**Macro-expension** happens at compile time, so we get **compile-time checks**!

Since `alpha11` macros `let`, `if-let`, `when-let`, `defn`, `fn`, `ns` are checked.

<!-- ---- -->

<!-- ## Some more specs -->

<!-- `and` let's you chain multiple specs. It's like `->` or `..`, each spec gets the -->
<!-- conformed value of the previous spec -->

<!-- ``` clojure -->
<!-- (s/def ::contrived-and (s/and vector? -->
<!--                               #(> (count %) 3) -->
<!--                               (s/cat :sym keyword? -->
<!--                                      :rest (s/* ::s/any)) -->
<!--                               #(#{:x :y :z} (:sym %)))) -->

<!-- (s/conform ::contrived-and [:x 1 2 3]) ;;=> {:sym :x, :rest [1 2 3]} -->
<!-- (s/conform ::contrived-and [:a 1 2 3]) ;;=> :clojure.spec/invalid -->
<!-- ``` -->

<!-- Possible to add "custom conformers" to apply transformations -->

----

## Test.check

``` clojure
(require '[clojure.test.check :as tc])
(require '[clojure.test.check.generators :as gen])
(require '[clojure.test.check.properties :as prop])

(def positive-recipe-prop
  (prop/for-all [r (s/gen ::recipe)]
                (>= (cook! r) 0)))

(tc/quick-check 100 positive-recipe-prop)
;; {:fail [{:robochef/ingredients (-2.0 :+.j/l*4 "")}],
;;  :smallest [{:robochef/ingredients (-1.0 :A "")}}}
```



---

## Recap

* shared vocabulary for "schemas"
* combine and compose
* key-specs for maps, regex-specs for seqs
* validating, conforming, runtime and compile-time checks
* generative testing

----

{:.cover style="font-size: 15rem"}
## FIN
