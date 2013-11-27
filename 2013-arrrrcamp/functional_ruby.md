---
{:.rubyconfar}

---

# Functional Patterns in Ruby

---
{:.github-ribbon}


# Hexp

S-expressions for HTML

```ruby
cries = %w[arrrr Arrrr ARRRRRRRRRR]

dom = H[:section, {class: 'arrrr'},
  cries.map {|arrrr| H[:p, arrrr] }
]

dom.replace('.arrrr p') {|element| element.add_class(:battle_cry) }
puts dom.to_html
```

```html
<section class="arrrr">
  <p class="battle_cry">arrrr</p>
  <p class="battle_cry">Arrrr</p>
  <p class="battle_cry">ARRRRRRRRRR</p>
</section>
```

---
{:.big}

## Immutability

FREEZE ALL THE THINGS!

```ruby
class Foo
  def initialize(a,b)
    @a, @b = a.freeze, b.freeze
  end
end
```

---
{:.big}

## Deep Freeze

```ruby
[
 'potatoes'.freeze,
 'fries'.freeze
].freeze
```

There's a gem for that

```ruby
IceNine.deep_freeze([
 'mayonaise',
 'ketchup'
])
```

---

## Callable!

```ruby
class Multiply
  def call(x,y)
    x*y
  end
end

Multiply.new.(2,3)
```

---

## procable !

```ruby
module Callable
  def to_proc
    method(:call).to_proc
  end
end

class Square
  include Callable

  def call(x)
    x*x
  end
end

[1,2,3].map(&Square.new) # => [1, 4, 9]
```

---

## Curryable!

```ruby
module Callable
  # ...
  def curry ; to_proc.curry ; end
end

class Multiply
  include Callable

  def call(x,y) ; x*x ; end
end

multiply_by_3 = Multiply.new.curry.(3)

[1,2,3].map(&multiply_by_3) # => [3, 6, 9]
```

---

## Parameterizable!

```ruby
class Harmonics
  def initialize(ratio = 1.61803398875, harmonic = 2)
    @ratio, @harmonic = ratio, harmonic
  end

  def call(in_value) ; out_value * factor  ; end
  def factor         ; @ratio ** @harmonic ; end
  def self.call      ; new.call            ; end
end
```

---

## Pipelining

``` ruby
class ValueClass
  # ...snip...

  def |(callable)
    callable.call(self)
  end
end
```

```ruby
new_value = value | MyCalculation \
  | MyCalculation.new(5) | ->(v) { v.foo }
```

---

## API design

```ruby
# This does not work
h << foo
h << bla
```

```ruby
# This does... but ugly backslash
h = h << foo \
      << bla
```

---

## API design

`<<=` to the rescue!

```ruby
h = foo
h <<= H[:title, "Web Linguistics"]
h <<= H[:link, rel: 'stylesheet', href: 'stylesheets/stylesheet.css']
h <<= H[:script, src: file_uri_relative('javascripts/highlight.pack.js')]
```

---

## Name your blocks

``` ruby
def do_x
  ->(_) { ... }
end

def only_y
  ->(_) { ... }
end

foo.map(&do_x).filter(&only_y)
```

---

## Persistent Data Structures

AKA Clojure magic sauce

There's a gem for that!

```
gem install hamster
```

---

## Ruby 2.1

* Sorta Generational GC

* Sorta Tail Call Optimization

* Frozen String Literals : `"hello world"f`

---
{:.fin}

# FIN
