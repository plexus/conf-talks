# -*- coding: utf-8 -*-


str1 = "\x41" # => "A"
str2 = "\u{8001}\u{987e}" # => "老顾"

str1.split // # => ["A"]
str1.chars.to_a # => ["A"]

str2.unpack('U*') # => [32769, 39038]
str2.codepoints.to_a # => [32769, 39038]
