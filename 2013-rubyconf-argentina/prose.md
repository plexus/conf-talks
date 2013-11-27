# Part I : Arne becomes knowledgeable, then paranoid, then frustrated

For a little over a year I've been doing contract work for an Irish company called Ticketsolve. Ticketsolve builds a complete box office solution for theaters, concert halls and organizers. It's used to sell tickets on-line or at points of sale, and has lots of advanced reporting and other niftyness built in.

The bulk of it is a Rails application which will soon be 6 years old. Part of my job for the last year has been to get us on to Rails 3.2.

Last winter, when the rails vulnerabilities were dropping like apples in September (or March, depending on your hemisphere), like many Rails shops we became a bit paranoid. Sure, we had gotten the patches out there soon enough, but was out app really secure? As a business owner who has spent years building a profitable business, it's disturbing to realize that a single exploit can be enough to ruin your business.

Because it was a hot topic at the time I prepared a talk for the Berlin Ruby user group covering all the recent Rails exploit in detail. I also talked a bit about general security principles. Because I had been really digging in to the topic I had acquired a healthy dose of paranoia, and I was hoping that some of that would rub off on the audience, making them think a bit harder about what it means to build a secure app.

Around the same time I was working on upgrading our app from Rails 2.3 to Rails 3, and one of the security features that landed in Rails in 3.0 was the HTML SafeBuffer, a mechanism that promised to prevent Cross-Site scripting "by default". This upgrade was not an easy job. It basically meant tracing every string that gets interpolated in a view back to its source to see if it could legitimately contain HTML tags or entities. If so it had to be marked as "html_safe". Failing to do so would result in a messed up user interface, marking too much as html_safe would result in XSS vulnerabilities.

Now if this is hard to do, it also means it's hard to assert whether you're vulnerable to XSS or not. Rails does not help you with this in any way, in fact it makes it really easy to slip up and leave a hole in your defenses.

# Part II : Writing better software

Writing software is hard. Writing good software, software that is reliable, secure, fills a real need and is delivered within time and budget is extremely hard.

--------------------------------------------------------------------------------
v1

When coming up with new techniques and technologies to make building software a little less hard, there are two criteria that are commonly used. Sometimes the goal is to make things easier for the programmer, to have her get more done with less code. This is the *Usability* goal, it is a forward looking "how can I make my job easier". Anything that allows us to crank out functionally equivalent software falls into this category. For startups that want to get their minimal viable product on the market as soon as possible this is an important factor, and it's been a big reason for the growth of Ruby and Rails.

Other attempts to make software development a little less awful are more retrospective. Given that certain categories of security problems and software defects pop up regularly, we try to find ways to squash certain categories of problems wholesale. Because dangling pointers and buffer overruns were so common in the past, people came up with Garbage Collection. The goal here is greater *Reliability*.

The reason that this can be done at all, that we can "fix" complete categories of problems at all, is usually due to a better understanding of the problem, by realizing that there is an underlying abstraction, a level that can be formalized and automated.

These two axes feed into each other, by implementing common abstractions and relaying more work to the machine, the programmer's job becomes easier, and more productive. On the other hand, this might mean adding certain constraints.

--------------------------------------------------------------------------------
v2

Over time people have come up with many ways to make software better, and to make the experience of creating software better. Let's look at some technical approaches

* Garbage collection : automating something that is error-prone
  * higher productivity (less work for the programmer)
  * higher correctness (not possible to make certain bugs)
  * enabler for new technologies (functional)
* imposing constraints *(functional, immutability)
  * higher correctness (impossible to make certain bugs)
  * higher productivity (less confusion)
  * enabler for new technologies : parallelism
* shared abstractions *(data types, state machines)*. Rack, Liberator
  * higher productivity (reuse)
  * higher correctness (get it right once)
  * enabler for new technologies (due to common base, cross cutting constraints, type decorations)

(* Static/strong typing
  * higher correctness (compile time checking of assumptions))

These are all technologies that have been invented to improve software engineering. They might do one or more of the following :

Make software faster to create
Make software easier to understand and reason about
Make software less likely to be buggy
Make software more likely to be secure
Allow programmers to do things that simply weren't possible before

Most of them do come with a tradeoff, however

It's slower
It's harder to learn
It's harder to use

The first argument has been used against garbage collection, managed runtimes, relational databases, etc. As a counterargument one could call upon Moore's law, but more compelling is the fact that when a job is left to the computer it can be further optimized, and might before long supersede the manual version.

The usability argument is a harder one though, because just like programmer productivity it's very hard to quantify, and one might need quite some practice with a new tool before making a judgment call on this one. What some write off as unusable others swear by. Case in point : Haskell. In these cases it might be more a "hard to learn" than a "hard to use" problem.

# Part III, where we actually talk about language, and tie all the above back together

Let's look again at the last example

* HTTP Request/response messages form a language
* Every web application needs to handle these I/Os
* There are many subtleties
* Above mere formality there are language semantics that can be implemented in a generic way

In case of implementing "language management" one needs

- A Syntax Tree representation
- A Parser
- A Generator
- Generic syntax tree manipulation
- Domain-specific manipulation API

And one gets

- a shared abstraction
- that promotes more correct software
- that offloads work from the programmer to the machine

Examples

- Rack
- Liberator
- ARel / ActiveRecord

Ideally these different "managed language" implementations are easily composable

- Nesting, i.e. Javascript in URL in HTML
- Specialization : JSON-API

HTML is hard
But we don't have a generic implementation
Nokogiri comes closest, but not suitable for generation


This is where I kind of slip back into my original "web linguistics" schtick.

Think of the boundaries of your app
The bulk of what crosses those boundaries are formal languages
