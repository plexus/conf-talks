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


#%######################################
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

get,add = -> {
  x = 0
  [
    -> { x },
    -> { x += 1}
  ]
}.()

add.() # =>
add.() # =>
add.() # =>

#
# 3. language support
#

# Enumerable / Enumerator / Proc

map, inject, curry

#%######################################
#
# Value Objects
#
########################################

class Bottle
  attr_reader :size, :contents

  def initialize(label, size, contents)
  end

  def add(i)
  end

  def inspect
    "<#{@label} bottle, %.2f%%>" % (Float(@contents) / @size * 100)
  end
end

#%######################################
#
# Function objects
#
########################################

class GoldenRatio
end

#%######################################
#
# Let's have some fun
#
########################################

class Class
  def to_proc
  end
end

class Symbol
  def call(*args)
  end
end

numbers = ([ ->{rand(100)} ] * 100).map(&:call)

class Proc
  def &(other)
  end
end

#%######################################
#
# Infinite lists
#
########################################

def squares
end

squares.take(20)

infinite = (1..Float::INFINITY).lazy

fizz =
buzz =

fizzbuzz =

fizzbuzz.take(15).to_a

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

#%
