# -*- coding: utf-8 -*-


'A' =~ /[\u{41}-\u{7a}]/ # => 0


str = "And so he said, '我叫顾青', and then，'就叫老顾吧'"

CJK_RANGES = [
  19968..40959,
  13312..19903,
  131072..173791,
  63744..64255,
  194560..195103,
  11904..12031,
  12032..12255
].map{ |range| '[\u{%s}-\u{%s}]' % [ range.begin.to_s(16), range.end.to_s(16) ]}
 .map( &Regexp.method(:new) )

CJK_REGEXP = Regexp.union(CJK_RANGES)

str.scan(CJK_REGEXP) # => ["我", "叫", "顾", "青", "就", "叫", "老", "顾", "吧"]
