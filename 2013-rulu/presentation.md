# Web Linguistics
## Towards Higher Fluency

by [@plexus](http://twitter.com/plexus)

---
= data-x="1000"

# tl;dr in Haiku

For formal language

Avoid plain strings at all cost

Use data structures

---
= data-x="2000"

## Formal languages

* HTML, CSS, Javascript
* HAML, SASS/SCSS, Coffeescript
* JSON, XML, YAML
* SQL, Ruby, Regex
* URL, HTTP request/response, Mbox/MIME

---

## Formal language is

* A (infinite) set of strings
* A productive grammar

---

## Formal language theory

* deals with well-formedness
* is a string part of the language
* doesn't say much about applications

----
= data-x="3000"

# An example
## Innocent HTML fragment

````html
&lt;p>
  &lt;strong>Rübÿ&lt;/strong>
  needs more
  &lt;abbr title="Heavy Metal Umlauts">HMÜ&lt;/abbr>
&lt;/p>
````

----

## Levels of interpretation

* Bytes
* Characters
* Tokens
* Syntax tree
* Semantics

----
= data-x="4000"

## Bytes

```ruby
["<", "p", ">", "\n",
 " ", " ", "<", "e", "m", ">",
 "R", "\xC3", "\xBC", "b", "\xC3", "\xBF",
 "<", "/", "e", "m", ">"]
````

----
## Characters

```ruby
["<", "p", ">", "\n",
 " ", " ", "<", "e", "m", ">",
 "R", "ü", "b", "ÿ",
 "<", "/", "e", "m", ">"]
````

----
## Tokens

```ruby
["<", "p", ">", "\n  ",
 "<", "em", ">", "Rübÿ",
 "<", "/", "em", ">"]
````

----
## HTML Tokens

```ruby
["&lt;p>", "\n  ", "&lt;em>", "Rübÿ", "&lt;/em>", "&lt;/p>"]
````

----
## Abstract Syntax Tree

<img src="../graphs/html.svg" height="500px;" />

---
## Semantics

* What does it mean
* What does it do

````
<p>
  <strong>Rübÿ</strong>
  needs more
  <abbr title="Heavy Metal Umlauts">HMÜ</abbr>
</p>
````

---

The closest we get to representing semantics

is through syntax trees

and yet we are dealing with HTML at the character level

---

## Security

* XSS, SQLi
* Common wisdom : proper escaping

---

## The problem

````ruby
"&lt;p>#{@text}&lt;/p>"
````

---

## We think we're doing this

Add a single text node inside the paragraph

````dot
graph para {
  p -- text;
}
````

---

## Instead we're doing this

Add an arbitrary subtree in our HTML

````dot
graph para {
  p -- script;
  script -- evil_code();
}
````

---

# The problem

Semantics of string are twofold

* a string
* a textual representation of HTML

---

## Security

We try to tell our code which one it is

````ruby
html_escape
html_safe?
raw
...
````

That's a lot of manual book keeping

---

What side of the escape are we on?

````
&amp;amp;quot;Ruby&amp;amp;quot; &amp;amp;gt; &amp;amp;quot;PHP&amp;amp;quot;
````

---

## Rails Templates
### Pidgin vs Creole

````erb
&lt;ul class="nav">
  &lt;% unless @cart.empty? %>
    &lt;li>&lt;%= link_to raw("&lt;i class='icon-cart'>&lt;/i>  Cart"),
                          cart_path %>
    &lt;/li>
  &lt;% end -%>
&lt;/ul>
````

---

## Pidgin

* Ad-hoc mix of two languages
* No fixed rules or grammar
* No native speakers

---

### Creole

* Second generation
* One language begins to dominate
* Proper grammar emerges

---

## In Summary

Manually escaping is hard

Generating correct HTML is hard

Strings are very low level when reasoning<br />
about application semantics

---

## Try something different

* Plain text coming in?
* => parse to data structure
* Plain text going out?
* => Generate from data structure

---

## Inside the application
### No more strings

---

## Fringe Benefits

MOAR POWER

---

````ruby
class Nav
  def initialize(cart)
    @cart=cart
  end

  def to_dom
    [:ul, [
        home_link,
        cart_link
      ].compact
    ]
  end

  def cart_link
    unless @cart.empty?
      [:li, {class: 'cart-icon'}, link_to('Cart', cart_path)]
    end
  end
end
````

---

````ruby
class MyController
  def index
    page = SignupPage.new
    if request.post?
      page = page.rewrite(PopulateFormFields.new(params))
    end
    render Layout.new(page)
  end
end
````
