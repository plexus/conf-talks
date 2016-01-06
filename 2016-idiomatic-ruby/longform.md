Rubyists, we need to talk. We need to talk about idioms, about what you call
"idiomatic Ruby". What does it mean? Where does it come from? Why would be want
it?

Let's first look at the word, "idiomatic". Idiomatic means "relating to, or
confirming to idiom", so what is idiom?

Idiom has two separate, but related, meanings. An idiom is a way of using
language that is particular to a specific group. It can also mean a specific,
peculiar phrase or expression that is commonly understood, even though its
meaning isn't self-obvious.

Example idioms are "to kick the bucket", or "Bob's your uncle", but the latter
is only common in the British idiom, not the American.

In programming we, too, have idioms. They are like tiny design patterns, ways of
doing things "in the small" that have been proven to work well, and are part of
idiom programmers share.

``` ruby
if __FILE__ = $0

end
```

``` ruby
def sum
  @sum ||= @left + @right
end
```

But being idiomatic isn't just about using idioms, it is also about expressing
yourself in a way that seems "natural". To put it the other way, when something
"sounds weird" to a native speaker, it's a telltale sign you're not being
idiomatic. Should you tell someone: "please follow me along" that will be
readily understood, but it doesn't really sound *right*, whereas "come with me,
please" would be a more idiomatic way of saying it.

Similarly, when programming, you often have more than one way to do something,
and while all the options might be equivalent to the computer, some will "look
weird" to other programmers, again a sign you are no longer being idiomatic.

But who defines what is and isn't idiomatic? At first glance it would appear
that no-one does, after all it's just "how people speak" (or program, as the
case may be), all we can do is describe it.

In reality though a small group of influencers will have a disproportianate
amount of influence on how the idiom, the norm, evolves. These are the
Shakespeares, the Charles Dickens, the T.S. Elliots of programming, the people
writing famous libraries and frameworks, the programming elite, the rockstars we
look up to. When you "lie low" or wish someone "good riddance", you are quoting
Shakespeare word for word. If you use "fail" and "raise" to make a semantic
distinction, you can thank Jim Weirich for that.

There's a second phenomon that has a big influence on idiom, and that is when
description becomes prescription. This is the purpose of a style guide, to
describe the rules that people already use, often unconsciously, and turn them
into guidelines.

There are a few good reasons to do this. Some things are just objectively better
than others, because they are more efficient, or because following the rules
prevents common mistakes. For example, some programmers prefer "yoda conditions"
for equality checks, because it prevents accidentally writing an assignment
instead.

```ruby
if x = 3
  # does x equal 3? ah.. oops
end

if 3 == x
  # would raise an error if we accidentally used an assignment
end
```

Another reason for having a style guide is for consistency. Some things are
simply arbitrary: use of whitecase, camelcase vs snake case, `proc {}` vs
`Proc.new {}`, `foo` vs `foo()`. When code within the same file or project uses
these things inconsitently, there are constantly things that look a bit out of
place, thus wasting brain cycles that could have gone to understanding the
program.

But there's a danger with style guides: they can cause a language to become
stuck in its past. To see how far in the past, we need to only look at Chinese,
up until the early 20th century the norm for written texts was still Classical
Chinese, a language that had last been spoken two-thousand years prior.

Ruby is, as far as programming languages go, a very organic language. It was
inspired by Perl, a language created by a linguist. Ruby is characterized by a
flexible syntax, and by humane interfaces, where redundant aliases are provided
merely to provide joy and expressive power to the programmer.

When you look around for older Ruby code, you can see how its style has evolved.
I think we can roughly split the evolution of Ruby's style and idiom into three
eras: 1995-2005: the pre-Rails era, 2005-2010: the _why era, and 2010-now,
post-why.

In the first era Ruby is still very much a niche language. Early adopters are
mostly coming from Perl or C, and just like there are many styles of Perl (Perl
embraces diversity), so we had several styles of Ruby. Everyone was new to the
language, and so people used what was most familiar to them, Perl-ism, C-ism,
etc.

By the early two-thousands Ruby has matured enough that it is starting to
develop a style of its own. People are coming up with best practices and
"Ruby-isms" that are considered good form. But there is still a lot of diversity
in use of whitespace, placement of parentheses, choice of API aliases.

Then Rails hits the scene, and Ruby's following explodes. The majority of
Rubyists are beginners again. But this time there's a clear blueprint to study
and copy: Rails itself. From naming everything `acts_as_foo`, to widespread
monkey-patching, to elaborate DSLs, the Rails way is the right way, because it's
all people know. Just mix it into ApplicationController! Cargo-culting abounds.

There is still significant variety in how things are done. _why is still around
to encourage people to explore and be creative, and in many little ways people
and teams develop their own styles.

Then we head towards 2010. The early adopters of Rails, and their startups, have
now matured. With a couple years of experience maintaining a Ruby code base
under their belt, people become a bit more cautious. Maybe this free-form
creative programming comes at a price. Maybe these monkey patches and custom
DSLs weren't such a good idea to start with. Then _why disappears, discouraged
and driven away at last, as we would later learn, by the pain of maintaining his
own coding exploits. The wild days are over.
