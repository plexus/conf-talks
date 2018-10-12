
# The Art of Tree Shaping with Zippers

----
{: fullscreen-img="img/title_image.png"}

----

## About me

My name is Arne Brasseur (@plexus)

I'm from Belgium, but based in Berlin.
{:.next}

I do Clojure consulting & Training.
{:.next}

---

## Lambda Island

Detailed screencasts about all things Clojure & ClojureScript.

Been going for ~2.5 years.
{:.next}

You should check it out.
{:.next}

---

## Agenda

- Look at `clojure.zip` API
- How does it work? (opening and closing the zipper)
- Clojure implementation
- Practical example with rewrite-clj

---

## What is a zipper?

A pair of (tree data structure, pointer to a node in the tree)

<!-- image of tree with arrow -->

---

## Getting started

``` clojure
(require '[clojure.zip :as z])

(def loc (z/vector-zip [[1 2] [3 4]]))
```

---

## An example

{: viz="tree"}
``` clojure
(-> [[1 2] [3 [4 5] 6]]
    z/vector-zip)
```

---

## An example

{: viz="tree"}
``` clojure
(-> [[1 2] [3 [4 5] 6]]
    z/vector-zip
    z/down)
```

---

## An example

{: viz="tree"}
``` clojure
(-> [[1 2] [3 [4 5] 6]]
    z/vector-zip
    z/down
    z/right)
```

---

## VIZ BABY

{: viz="tree"}
``` clojure
(-> [[1 2] [3 [4 5] 6]]
    z/vector-zip
    z/down        ;; [1 2]
    z/right       ;; [3 [4 5] 6]
    z/down        ;; 3
    z/rightmost   ;; 6
    z/left        ;; [4 5]
    )
```


---

## Can work on any kind of tree

``` clojure
(zipper branch? children make-node root)
```

``` clojure
;; Can this node have children?
(branch? node) ;;=> bool
```

``` clojure
;; Return the children of a branch node.
(children node) ;;=> sequence
```

``` clojure
;; Given an existing node and a new set of child nodes,
;; return a new node with those children.
(make-node node seq) ;;=> node
```



---

Creating zippers

```
zipper
seq-zip
vector-zip
xml-zip
```

Navigating

```
up
down
left
leftmost
right
rightmost
```

Iterating

```
prev
next
end?
```

Inspecting

```
node
root
children
lefts
rights
path
branch?
```

Editing

```
append-child
insert-child
insert-left
insert-right
edit
remove
replace
make-node
```
