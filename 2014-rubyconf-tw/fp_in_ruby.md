{::options parse_block_html="true" /}

---
{:.center}

Use the space bar or arrow keys to browse the slides.

---
{:.center-text}

## Functional Programming in Ruby

RubyConf Taiwan 2014

by Arne Brasseur / @plexus

----
{:.bullet-points}

## Functional Programming

Values

Functions

Runtime support

---

# Values

---
{:.bullet-points}

## Variables

A name for a place

What's in the place changes over time

```ruby
x = 3
x = 5
x = nil
```

---
{:.bullet-points}

## Values

An unchangeable fact

Different values can reflect facts related to different times

```ruby
{ date: '2014-04-24', hungry: 'very' }
{ date: '2014-04-25', hungry: 'not so much' }
```

---

[The Value of Values - Rich Hickey](http://www.youtube.com/watch?v=-6BsiVyC1kM)

http://www.youtube.com/watch?v=-6BsiVyC1kM

---
{:.bullet-points}

## Values in Ruby

```ruby
true, false, nil
```

```ruby
23, 42, 5.9999
```

```ruby
:foo, :bar
```

That's about it.

---
{:.bullet-points}

## Values by Choice

The biggest virtue of a Ruby programmer is discipline

Make liberal use of `freeze`

This includes aggregates: `Array`, `Hash`, `Object`

---
{:.bullet-points}

## Why Values?

Safe to share

Easy to reason about

Make the best interfaces

---
{:.vocab}

## per·sis·tent

### /pərˈsistənt/

_adjective_

Continuing to exist or endure over a prolonged period.

---
{:.bullet-points}

## per·sis·tent

Old version stays available after "change"

---
{:.code}

```ruby
class HTMLElement
  attr_reader :tag, :attrs, :children

  def initialize(tag, attrs, children)
    @tag      = tag
    @attrs    = attrs.freeze
    @children = children.freeze
  end

  def set_attr(attr, value)
    self.class.new(
      tag,
      attrs.merge(attr => value),
      children
    )
  end
end
```

---

# Functions

---
{:.bullet-points}

## Pure Functions

Same input ⇒ same output

No observable side effects

Follows naturally from using values

---
{:.boutade}

When everything is a value, programming is mapping one set of values to another.

---
{:.bullet-points}

## Pure Functions

Have reproducible results

Highly reusable

Can be memoized

---
{:.bullet-points}

## Computing Functions

Compose

Partial application

Closures

Higher-order functions

---
{:.bullet-points}

## Functions in Ruby

Ruby only has methods and lambdas

---

# Performance

---
{:.bullet-points}

Persistent collection

Generational GC

Tail-call optimization

Compare apples to oranges

---
