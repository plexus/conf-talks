# -*- coding: utf-8 -*-
START_INTERVAL = 10
START_FACTOR   = 2.5
MIN_FACTOR     = 1.3

require 'anima'
require 'forwardable'

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
        previous_rating: rating
      )
    )
  end

  def inspect
    "#<Card interval: #{interval} factor: #{factor}>"
  end
end

NullRating = Struct.new(:interval, :factor).new(10, 2.5)

class Rating
  include Anima.new(:score, :previous_rating)

  def interval
    score == 0 ?
      NullRating.interval :
      previous_rating.interval * previous_rating.factor
  end

  def factor
    [
      MIN_FACTOR,
      previous_rating.factor + [-0.15, 0, 0.15, 0.3][score]
    ].max
  end
end

card = Card.new(front: '臺灣', back: 'Taiwan', rating: NullRating) # => #<Card interval: 10 factor: 2.5>
card = card.score!(1) # => #<Card interval: 25.0 factor: 2.5>
card = card.score!(3) # => #<Card interval: 62.5 factor: 2.8>
card = card.score!(3) # => #<Card interval: 175.0 factor: 3.0999999999999996>
card = card.score!(0) # => #<Card interval: 10 factor: 2.9499999999999997>
card = card.score!(0) # => #<Card interval: 10 factor: 2.8>
