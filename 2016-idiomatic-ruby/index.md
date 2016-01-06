<!-- IDIOMS AND IDIOMATIC: WHAT AND WHY -->

---
{:.center}

# “Burn Your

# Idiomatic Ruby”

## — @plexus

---

## You say idiomatic

What does it mean?

Why do we want it?

---

## Idiomatic

**id·i·o·mat·ic** \i-dē-ə-ˈma-tik\ _adjective_

Relating to, or conforming to idiom

---

## Idiom

**id·i·om** \i-dē-əm\ _noun_

1. a way of speaking that is particular to a specific group
2. a peculiar phrase or expression that is commonly understood, even though its
meaning isn't self-obvious

---

## Idiom (1)

“Eurospeak”
British English
SMS-language

---

## Idiom (2)

kick the bucket
and Bob's your uncle
pulling your leg

---

## Ruby idioms

Think: "tiny design pattern"

---

## Ruby idioms

Optionally executable

``` ruby
if __FILE__ = $0
  # ...
end
```

---

## Ruby idioms

A method that memoizes

``` ruby
def sum
  @sum ||= @left + @right
end
```

---

## Ruby idioms

Method pre-condition

``` ruby
def filter_negatives(list)
  raise "list can't be empty" if list.empty?

  # ...
end
```

---

## Idiomatic

Confirming to idiom(1) (which tends to use idiom(2))

Sounding “natural”



---
{:.center}

<!-- RUBY STYLE: A HISTORY LESSON -->

# A short history of Ruby idiom

---

## The first era: 1995-2005

“The primal ooze”

Ruby is still very niche
Early adopters from Perl or C
Diversity of styles

---

## The second era: 2005-2010

“Rails runaway train”

Adoption booms through Rails
Ruby lauded for flexibility and creativity (_why!)
Idiom crystalizes around Rails

---

## The third era: 2010-2015

“They grow up so fast”

Big Rails apps maintained for 5+ years
Search for best practices, consolidation
Wide adoption of a common style

---

## The third era: 2010-2015

Resurgence of a “pure Ruby” crowd
More open to novel approaches
Develop their own idiom

---

<!-- A BRIGHT NEW RUBY FUTURE -->

<!-- Since then a lot has been happening in programming language development. New -->
<!-- languages like Rust, Elixir, Elm, Go, Idris are introducing new paradigms. -->
<!-- Functional programming has gone mainstream, and Rubyists are starting to pay -->
<!-- attention. We're seeing a new wave of experimentation. Some of the resulting -->
<!-- code may look foreign at first, but we would be wise not to dismiss it too -->
<!-- quickly because it's "not idiomatic". -->

## Language developments

Since 2009 several game changers

* Go
* Idris
* Elixir
* Elm
* Rust

---

## Kleisli

```ruby
json_string = get_json_from_somewhere

result =
  Try { JSON.parse(json_string) } >-> json {
    Try { json["dividend"].to_i / json["divisor"].to_i }
  }
```

```ruby
maybe_user =
  Maybe(user) >-> user {
    Maybe(user.address)
  } >-> address {
    Maybe(address.street)
  }
```

---

## Kleisli

From Call Sheet

```ruby
result.fmap { |value|
  broadcast :"#{step_name}_success", value
  value
}.or { |value|
  broadcast :"#{step_name}_failure", *args, value
  Left(StepFailure.new(step_name, value))
}
```

---

## Transproc

```ruby
transformation = t(:map_array, t(:symbolize_keys)
 .>> t(:rename_keys, user_name: :user))
 .>> t(:wrap, :address, [:city, :street, :zipcode])
```

---

## Transducers

```ruby
T.transduce(
  T.compose(
     T.map(:succ),
     T.filter(:even?)
  ),
  :<<, [], 0..9
)
# => [2, 4, 6, 8, 10]
```

---

# FIN

---

---

---

## Idiom

From Ancient Greek

&nbsp;

**ἴδιος** (_ídios_)
“private, personal, one's own;  peculiar, separate”.

&nbsp;

**ἰδιοῦσθαι** ‎(_idioûsthai_)
“to make one's own, appropriate to oneself”

&nbsp;

**ἰδίωμα** ‎(_idíōma_)
“a peculiarity, property, a peculiar phraseology, idiom”

---

## Idiot
From Ancient Greek, Latin

&nbsp;

**ἴδιος** (_ídios_)
“private, personal, one's own;  peculiar, separate”.

&nbsp;

**ἰδιώτης** (_idiōtēs_) “person lacking professional skill, a private citizen, individual”

&nbsp;

In Late Latin:

&nbsp;

**idiota**
“uneducated or ignorant person”

---

## Idiom

A way a certain group of people speak.

_I had studied Arabic before, but was not familiar with the local idiom._

An expression whose meaning can't be inferred from the words that make up the expression.

_you're pulling my leg_, _in your neck of the woods_, _I’ve been a rubyist for donkey’s years._

The style of a particular artist or school or movement

---

## Idiomatic

The "natural" way to phrase something

_come with me_ vs _follow me along_

---

It's about being able to personally express yourself.

It's about being understood.

---

“”
