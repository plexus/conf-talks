# -*- coding: utf-8 -*-
class Card
  START_INTERVAL = 10
  MIN_FACTOR     = 1.3

  def initialize(*args)
    @front, @back, @interval, @factor = *args
  end

  def score!(rating)
    update_interval!(rating)
    update_factor!(rating)
    self
  end

  def update_interval!(rating)
    @interval = rating == 0 ?
      START_INTERVAL :
      @interval * @factor
  end

  def update_factor!(rating)
    @factor += (rating-1) * 0.15
    @factor  = [MIN_FACTOR, @factor].max
  end

  def inspect
    "#<Card interval: #{@interval} factor: #{@factor}>"
  end
end

card = Card.new('臺灣', 'Taiwan', 10, 2.5) # => #<Card interval: 10 factor: 2.5>
card.score!(1) # => #<Card interval: 25.0 factor: 2.5>
card.score!(3) # => #<Card interval: 62.5 factor: 2.8>
card.score!(3) # => #<Card interval: 175.0 factor: 3.0999999999999996>
card.score!(0) # => #<Card interval: 10 factor: 2.9499999999999997>
card.score!(0) # => #<Card interval: 10 factor: 2.8>
