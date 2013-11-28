
def squares
  (1..Float::INFINITY).each { |i| i * i }
end

squares.take(20)
# ~> -:3:in `squares': uninitialized constant Float::INIFITY (NameError)
# ~> 	from -:6:in `<main>'
