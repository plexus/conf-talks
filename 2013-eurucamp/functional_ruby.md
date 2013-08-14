# Functional Patterns for Ruby

by Arne Brasseur / [plexus](https://github.com/plexus)

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

## Immutability

IceNine!

```ruby
IceNice.deep_freeze(foo)
```

---

## Function Classes

```ruby
def MyCalculation
  def self.call ; self.new.call ; end

  def initialize(param_x = 7)
    @param_x = param_x.freeze
  end

  def call(in_value) ; out_value ; end

  def intermediate
    @intermediate ||= @param_x * @param_x
  end
end
```

---

## Function Classes

Callable like a lambda

``` ruby
@calc = MyCalculation
new_value = @calc.(new_value)
```

---

## Pipelining

``` ruby
class ValueClass
  # ...snip...

  def |(callable)
    callable.call(self)
  end

  def process(callables)
    callables.empty? ? self :
      callables.first.(self).process(callables.drop(1))
  end
end
```

---

## Pipelining

```ruby
new_value = value | MyCalculation \
  | MyCalculation.new(5) | ->(v) { v.foo }
```

```ruby
new_value = value.process(
  MyCalculation,
  MyCalculation.new(5),
  ->(v) { v.foo }
)
```

---

## API design

What about operators that look like they modify the object?

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

(Re)discovered `<<=`

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

# FIN

Twitter / Github : @plexus
