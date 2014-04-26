# -*- coding: utf-8 -*-
require 'ting'

reader = Ting.reader(:hanyu, :numbers)
pinyin = reader.("wo3 re4 ai4 chi1 fan4")
# => [<Ting::Syllable <initial=Empty, final=Uo, tone=3>>,
#     <Ting::Syllable <initial=Ri, final=E, tone=4>>,
#     <Ting::Syllable <initial=Empty, final=Ai, tone=4>>,
#     <Ting::Syllable <initial=Chi, final=Empty, tone=1>>,
#     <Ting::Syllable <initial=Fo, final=An, tone=4>>]

writer = Ting.writer(:zhuyin, :marks)
writer.(pinyin) # => "ㄨㄛˇ ㄖㄜˋ ㄞˋ ㄔ ㄈㄢˋ"

writer = Ting.writer(:tongyong, :accents)
writer.(pinyin) # => "wǒ rè ài chīh fàn"
zhuyin = Ting.writer(:zhuyin, :marks)
