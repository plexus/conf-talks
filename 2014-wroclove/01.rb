nums = %w[
  One
  Two
  Three
  Four
  Five
  Six
]

nums.each do |num|
  if num == 'Two' .. num == 'Four'
    puts num
  end
end

# >> Two
# >> Three
# >> Four
