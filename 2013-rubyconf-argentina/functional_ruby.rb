  #%##########
  #############
  ####     #####
            #####
             ####
              ####              Functional Programming
              #####
             #######                  in Ruby
            #########
           ##########
          ####### ####
          ######  #####       A lightning talk
         ######    #####
        #######     #####           by @plexus
       #######       ####
      #######         ####               for @RubyConfAr
     #######          #####
    #######            #####
 #############      ###########
 #############      ###########


#%######################################
#
# Lambdas
#
########################################

a = 7
fn = ->(b) { a + b }
fn.(3) # => 10

# CONS CELLS

cons = ->(x,y) {
  ->(f) {
    f.(x,y)
  }
}

head = ->(c) {
  c.(->(x,y) {
      x
    })
}

tail = ->(c) {
  c.(->(x,y) {
      y
    })
}


list = cons.(1, cons.(3, cons.(7, nil)))

nth = ->(list, n) {
  n == 0 ? head.(list) : nth.(tail.(list), n-1)
}

idx = nth.curry.(list)

[1,2,3].map(&idx) # =>
# ~> -:20:in `block in <main>': undefined method `call' for nil:NilClass (NoMethodError)
# ~> 	from -:35:in `call'
# ~> 	from -:35:in `block in <main>'
# ~> 	from -:35:in `call'
# ~> 	from -:35:in `block in <main>'
# ~> 	from -:35:in `call'
# ~> 	from -:35:in `block in <main>'
# ~> 	from -:35:in `call'
# ~> 	from -:35:in `block in <main>'
# ~> 	from -:40:in `map'
# ~> 	from -:40:in `<main>'

#%######################################
#
# Value Objects
#
########################################

class Bottle
  attr_reader :label, :size, :contents

  def initialize(label, size, contents)
    @label, @size, @contents = [label, size, contents].map(&:freeze)
  end

  def add(i)
    self.class.new(label, size, contents + i)
  end

  def inspect
    "<#{@label} bottle, %.2f%%>" % (Float(@contents) / @size * 100)
  end
end

b = Bottle.new('malbec', 75, 0) # => <malbec bottle, 0.00%>
b.add(20) # => <malbec bottle, 26.67%>

#%######################################
#
# Function objects
#
########################################

class GoldenRatio
  def call(x)
  end
end

GoldenRatio.new.(7) # =>

[1,2,5]

#%######################################
#
# Let's have some fun
#
########################################

class Class
  def to_proc
  end
end

# %w[foo bar baz].map(&Regexp) # =>

class Symbol
  def call(*args)
  end
end

numbers = ([ ->{rand(100)} ] * 100).map(&:call)

class Proc
  def &(other)
  end
end

#nums = numbers.select(&:>.(50) & :<.(60))
#nums # =>

#%######################################
#
# Infinite lists
#
########################################

Inf = (1..Float::INFINITY)

def squares
end

squares.take(20) # =>

fizz =
buzz =

fizzbuzz =

#fizzbuzz.take(15).to_a

#%######################################
#
# Hamster
#
########################################

require 'hamster'

Hamster.vector(1,2,3,4)

set = Hamster.set(3,3,4)

hsh = Hamster.hash(name: 'Arne', likes: Hamster.list(:tea, :chocolate, :ruby))

hsh.put(:foo, :bar)

Hamster.stream { :hello }.take(3)

Hamster.iterate(0) {|i| i.next}.take(5)


#%######################################
#
# Lambda Calculus Arithmetic
#
########################################

R = ->(n) { n.(->(i) { i+1 }, 0) }

# Zero, i.e. apply a function to an argument zero times
_0 = ->(f,x) { x }

# Successor, i.e. n+1
succ  = ->(n) { ->(f,x) { f.(n.(f,x)) } }

# Addition, apply f m times, then n times
add = ->(n, m) { ->(f,x) { n.(f, m.(f,x)) } }

# Predecessor, n - 1
# Take a tuple (a=0, b=0) and turn it into (b, b+1), do that n times, so
# (0,0) => (0,1) => (1,2), etc
# After n times the first item of the tuple is n-1.
pred = ->(n) {
    ->(f,x) {
      n.(
        ->(tup) {
          tup.(->(a,b) { ->(h) { h.(b, succ.(b)) } })
        },
        ->(g){ g.(_0, _0) }
      ).(->(r, _) { r.(f,x) })
    }
  }

R[pred.(_4)] # =>

min = ->(m,n) {
    n.(pred, m)
}

R[min.(_4, _2)] # =>

#%
# ~> -:73: syntax error, unexpected '\n', expecting '='
