============================================================

# Burn your idiomatic Ruby

## Abstract

Ruby makes programmers happy because of its elegant style and readable code.
Rubyists like code that looks "right". Clever hacks and obscure use of syntax
are frowned upon, and code linters and metrics are used to enforce a clean and
idiomatic style.

But there's a danger in this attitude. Innovations may look foreign at first,
and by discarding them offhand we may be throwing the baby out with the
bathwater. On the flip side, libraries providing good looking interfaces may be
smuggling complexity into your project that is hidden underneath the surface.

## Details

This talk will look at a handful of gems. Their interfaces look weird at first
because they genuinly bring new concepts or approaches to Ruby.

Secondly we'll look at some elegantly looking DSLs, and pick them apart to see
how complex it is to make them work, and how unflexible they can be when moving
beyond simple use cases, finally contrasting this with a "just Ruby" alternative.

Finally I would like to reflect briefly upon Ruby's history. Ruby code that was
written a decade ago will look weird to us now, and Ruby has always fostered
great creativity in coding, just think of the heritage of _why. Having some
consistency and standardization is a good thing, but we shouldn't reject the
diversity that made Ruby great.

## Pitch

I have been writing Ruby code for almost a decade, have written several gems, and
have read the code of many more. Lately I've been exploring other languages,
that sometimes bring new concepts to the table. I know several gems that attempt
to bring some of these ideas to the Ruby world, and I find that they are too
easily discarded by a knee-jerk "this is not idiomatic".

I've also noticed that gems are adopted so much quicker if they have a "magic"
DSL, something that reads like English and looks great on a slide. Getting on
the bandwagon I've implemented DSLs like these myself, with liberal use of
instance_exec and other metaprogramming tricks, only to find that in real world
usage a mix of simple objects and methods would have been much more flexible and
predictable.

From these experiences I would like to share, to make people think twice before
saying "we have always done it this way".

============================================================

Simple vs Easy

Simple = possibly a learning curve, but as time goes on it is adaptable and flexible

Easy = easy to start, pain curve goes up as complexity goes up
