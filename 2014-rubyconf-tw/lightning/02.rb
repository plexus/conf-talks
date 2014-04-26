# -*- coding: utf-8 -*-

require 'analects'

## Rakefile

# Analects.init_rake_tasks

# rake analects:download:all

# ~/.analects

A = Analects::Library.new

regexp = /^[奶末]茶$/

A.cedict.select {|trad,simp| [trad, simp].any? {|ch| ch =~ regexp} } # => [["奶茶", "奶茶", "nai3 cha2", "/milk tea/"], ["末茶", "末茶", "mo4 cha2", "/tea powder (matcha)/"]]

A.chise_ids.select {|_, char, _| char == '親' } # => [["U+89AA", "親", "⿰亲見"]]

A.chise_ids.to_a.sample(3) # => [["U-00024BE3", "𤯣", "⿱生自"], ["U-00020384", "𠎄", "⿰⺅敦"], ["U-0002B125", "𫄥", "⿰纟丽"]]

Analects::Tokenizer.new.tokenize("为待那个朋友拿哟出来，咿呀噢哎…") # =>
# => ["为", "待", "那个", "朋友", "拿", "哟", "出来", "，", "咿", "呀", "噢", "哎", "…"]
