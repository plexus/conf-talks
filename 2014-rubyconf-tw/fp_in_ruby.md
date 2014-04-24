{::options parse_block_html="true" /}

{:.center}
Use the space bar or arrow keys to browse the slides.

---
{:.center .first-page}

## Functional Programming in Ruby

RubyConf Taiwan 2014

by Arne Brasseur / 白阿仁 / @plexus

---
{: .whoami .center}

{: .bigger }
![](my_assets/twitter.png)


![](my_assets/railsgirls_bln.png) ![](my_assets/ruby_lambik.png)

---
{:.full-image}

![](my_assets/dong_ding.jpg)

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
{ date: '2014-04-24', temperature: 28 }
{ date: '2014-04-25', temperature: 31 }
```

---
{:.bullet-points}

## Why Values?

Safe to share

Easy to reason about

Make the best interfaces

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

```ruby
Time, Date, Pathname
```

---
{:.bullet-points}

## Values by Choice

The biggest virtue of a Ruby programmer is discipline

Make liberal use of `freeze`

Aggregates of values are also values

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

Can be done in an optimized way

---
{:.bullet-points}

## Persistent Collections

Share memory but keep immutable semantics

Simple example: linked list

---
{:.bullet-points}

## Persistent Collections

Holy grail: "Hash Array Mapped Trie" (AKA Ideal Hash Tree)

`gem "hamster"` has Hash, List, Set, Stack, Vector, ...

---
{:.center}

![The Value of Values - Rich Hickey](my_assets/value_of_values.png)

[http://bit.ly/value_of_values](http://www.youtube.com/watch?v=-6BsiVyC1kM)

---

# Example

---
{:.center}

## Example: Flashcards

![](my_assets/anki.png)

---
{:.code}

```ruby
class Card
  def initialize(*args)
    @front, @back, @interval, @factor = *args
  end

  def score!(rating)
    update_interval!(rating)
    update_factor!(rating)
    self
  end

  def inspect
    "#<Card interval: #{@interval} "+
        "factor: #{@factor}>"
  end
end
```

---
{:.code}

```ruby
class Card
  MIN_FACTOR = 1.3

  def update_interval!(rating)
    @interval = @interval * @factor
  end

  def update_factor!(rating)
    @factor += (rating-1) * 0.15
    @factor  = [MIN_FACTOR, @factor].max
  end
end
```

---
{:.code}

```ruby
card = Card.new('臺灣', 'Taiwan', 10, 2.5)
# => #<Card interval: 10 factor: 2.5>

card.score!(1)
# => #<Card interval: 25.0 factor: 2.5>
card.score!(3)
# => #<Card interval: 62.5 factor: 2.8>
card.score!(3)
# => #<Card interval: 175.0 factor: 3.099>
card.score!(0)
# => #<Card interval: 10 factor: 2.949>
card.score!(0)
# => #<Card interval: 10 factor: 2.8>
```

---
{:.code}

```ruby
class Card
  include Anima.new(:front, :back, :rating)

  extend Forwardable
  def_delegators :rating, :interval, :factor

  def score!(score)
    self.class.new(
      front: front,
      back: back,
      rating: Rating.new(
        score: score,
        previous: rating
      )
    )
  end
end
```

---
{:.code}

```ruby
class Rating
  include Anima.new(:score, :previous)

  def interval
    previous.interval * previous.factor
  end

  def factor
    [ MIN_FACTOR,
      previous.factor + (score-1)*0.15].max
  end
end

NullRating =
  Struct.new(:interval, :factor).new(10, 2.5)
```


---
{:.code}

```ruby
card = Card.new(
  front: '臺灣', back: 'Taiwan', rating: NullRating
)
# => #<Card interval: 10 factor: 2.5>

card = card.score!(1)
# => #<Card interval: 25.0 factor: 2.5>
card = card.score!(3)
# => #<Card interval: 62.5 factor: 2.8>
card = card.score!(3)
# => #<Card interval: 175.0 factor: 3.099>
card = card.score!(0)
# => #<Card interval: 10 factor: 2.949>
card = card.score!(0)
# => #<Card interval: 10 factor: 2.8>
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

## Pure Functions

Build a core that is purely functional

Call from an "imperative shell"

Best of both worlds

---

# Example

---
{:.lots-of-code}

```ruby
def parse_cli_options(argv)
  opts = OptionParser.new do |opts|
    opts.on('--version', 'Print version') do |name|
      return { message: VERSION }
    end.on('--help', 'Display help') do
      return { message: opts.to_s }
    end
  end

  filenames = opts.parse(argv)
  if filename.length != 2
    return {
      message: "Wrong number of arguments!\n#{opts}",
      exit_code: 1
    }
  end

  { filename: filenames }
end
```

---
{:.code}

```ruby
def run
  result = parse_cli_options(ARGV)

  if result.key?(:filenames)
    perform(result[:filenames])
  end

  if result.key?(:message)
    $stderr.puts result[:message]
  end

  Kernel.exit(result.fetch(:exit_code, 0))
end
```

---
{:.bullet-points}

## Computing Functions

Functions as the unit of reuse

Combine functions into bigger functions

Possible in Ruby, but doesn't come natural

`funkify` gem has interesting approach

---
{:.bullet-points}

## Computing Functions

Closures

Function Composition

Partial application

Higher-order functions

---

# Performance

---
{:.bullet-points}

## Performance

Smart persistent data structures

Generational GC

Tail-Call Optimization

Measure!

---
{:.boutade}

Consider the benefits of value semantics when making performance comparisons.

---

# Conclusion

---
{:.bullet-points}

## Conclusion

Ruby can have the best of both worlds

Try it!

Start with values, see where the journey takes you

---
{:.link-image .center}

![](my_assets/happy_lambda_cover_smaller.png)

http://leanpub.com/happylambda


---

# Thank You