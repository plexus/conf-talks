# -*- coding: utf-8 -*-

# 1.8
$KCODE='U' # !> variable $KCODE is no longer effective; ignored

'大家好'.split(//) # => ["大", "家", "好"]
'大家好'.unpack('U*') # => [22823, 23478, 22909]

'大家好'.chars.to_a # => ["大", "家", "好"]
'大家好'.codepoints.to_a # => [22823, 23478, 22909]
