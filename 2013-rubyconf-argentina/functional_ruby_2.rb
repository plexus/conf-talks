  ############
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


########################################
#
# Functional programming
#
########################################

#
# 1. Restrictions
#

# Immutability
x = 'Ola'.freeze
y = 7
z = true

# Pure functions
def square(x)
  x * x
end

# Not allowed
x << 's'
y += 1
attr_writer / attr_accessor

#
# 2. Lambdas
#

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

list = cons.(1, cons.(3, cons.(7, cons.(9, nil))))

head.(tail.(list)) # => 3

nth = ->(list, n) {
  n == 0 ? head.(list) : nth.(tail.(list), n-1)
}

nth.(list, 1) # => 3

idx = nth.curry.(list)

[1,2,3].map(&idx) # => [3, 7, 9]

#
# 3. language support
#

# Enumerable / Enumerator / Proc

map, inject, curry

########################################
#
# Value Objects
#
########################################

class Bottle
  attr_reader :size, :contents

  def initialize(label, size, contents)
    @label, @size, @contents = [label, size, contents].map(&:freeze)
  end

  def add(i)
    self.class.new(@label, @size, @contents + i)
  end

  def inspect
    "<#{@label} bottle, %.2f%%>" % (Float(@contents) / @size * 100)
  end
end

########################################
#
# Function objects
#
########################################

class GoldenRatio
  def call(num)
    num * 1.618
  end
end

#%#######################################
#
# Let's have some fun
#
########################################

class Class
  def to_proc
    ->(*args) { new(*args) }
  end
end

class Symbol
  def call(*args)
    ->(obj){ obj.public_send(self, *args) }
  end
end

class Proc
  def &(other)
    ->(*args) { self.(*args) && other.(*args) }
  end
end

numbers = ([ ->{rand(100)} ] * 100).map(&:call)

numbers.select(&:>.(50) & :<.(60)) # => [53, 52, 59, 53, 56, 56, 54, 55, 51, 58, 53, 57, 53]

#%#######################################
#
# Infinite lists
#
########################################

Inf = (1..Float::INFINITY)

def squares
  Inf.lazy.map { |i| i * i }
end

squares.take(20) # => #<Enumerator::Lazy: #<Enumerator::Lazy: #<Enumerator::Lazy: 1..Infinity>:map>:take(20)>
squares.take(20).to_a # => [1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256, 289, 324, 361, 400]

fizz = Inf.lazy.map { |i| ['Fizz',nil,nil][i%3] }
buzz = Inf.lazy.map { |i| ['Buzz',nil,nil,nil,nil][i%5] }

fizzbuzz = Inf.lazy.map do |i|
  str = [fizz.next, buzz.next].join
  if str.empty?
    i
  else
    str
  end
end

fizzbuzz.take(15).to_a # => [1, 2, "Fizz", 4, "Buzz", "Fizz", 7, 8, "Fizz", "Buzz", 11, "Fizz", 13, 14, "FizzBuzz"]

########################################
#
# Hamster
#
########################################

require 'hamster'

Hamster.vector(1,2,3,4) # => [1, 2, 3, 4]

Hamster.set(3,3,4) # => {3, 4}

Hamster.hash(name: 'Arne', likes: Hamster.list(:tea, :chocolate, :ruby)) # => {:name => "Arne", :likes => [:tea, :chocolate, :ruby]}


Hamster.stream { :hello }.take(3) # => [:hello, :hello, :hello]

Hamster.iterate(0) {|i| i.next}.take(5) # => [0, 1, 2, 3, 4]


########################################
#
# Lambda Calculus Arithmetic
#
########################################
# Integer arithmetic in the lambda calculus

# This is our only admonission, it turns a lambda calculus "number" into an
# actual interger, so we can inspect our results. For the rest we only use
# lambdas

R = ->(n) { n.(->(i) { i+1 }, 0) }

# Zero, i.e. apply a function to an argument zero times
_0 = ->(f,x) { x }

# Successor, i.e. n+1
succ  = ->(n) { ->(f,x) { f.(n.(f,x)) } }

_1 = succ.(_0)
_2 = succ.(_1)
_3 = succ.(_2)
_4 = succ.(_3)

R[_0] # => 0
R[_3] # => 3
R[_4] # => 4

# Addition, apply f m times, then n times
add = ->(n, m) { ->(f,x) { n.(f, m.(f,x)) } }

R[add.(_2, _3)] # => 5

# Predecessor, n - 1
# Take a tuple (a=0, b=0) and turn it into (b, b+1), do that n times, so
# (0,0) => (0,1) => (1,2), etc
# After n times the first item of the tuple is n-1.
# Caveat : a tuple in this case is a lambda that applies a lambda to two arguments
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

R[pred.(_4)] # => 3

min = ->(m,n) {
    n.(pred, m)
}

R[min.(_4, _2)] # => 2
