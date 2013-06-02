# Web Linguistics : Towards Higher Fluency

## About the talk

The idea for this talk came with digging in to web security after last winter's Rails vulnerabilities. Whole categories of vulnerabilities are due to not properly escaping inputs, at least that is the common wisdom : ESCAPE ALL THE THINGS.

Fact is that having to do escaping manually will always be error prone. Using SafeBuffer to automatically escape in views/templates might seem like a good idea, but it's not addressing the structural problem : that we're manipulating structured data (like a DOM) as plain text.

It can be quite an epiphany to look at it from that angle. Plain text representations should be considered a serialization format. It is not sensible to write that by hand. It's more complex than is commonly acknowledged. What if you have a URL inside CSS inside JSON inside HTML?

So the solution would be to only have structured data in your application, syntax trees, and convert back and forth to plain text representations right before sending it over the wire.

Once at that insight you realize that this actually allows you to write code at a higher level. A good example would be UI widgets, classes that create fragments of the HTML syntax tree. These are composable, or you could have code that completely rewrites part of the tree to implement cross-cutting concerns.

So this would not be a security talk as such, but a call to reconsider current practices in a way that would improve security, provide guaranteed well-formedness of outputs, but especially to write more powerful code.

I have blogged and tweeted about these ideas, and have received some very good feedback, including links to how other languages and frameworks deal with these issues. It's still a bit of an unsolved problem, but there are a few interesting approaches to take ideas from.

As I see this talk will consist of two parts. First there will be an introduction about general concepts : languages, parsers and the "parse/generate in a single pass at the edge of you application" architecture (more catchy name TBA). This part will also go into the benefits of this approach, and the drawbacks and dangers of not using this approach. The concepts here apply to any formal language you might be dealing with in string form.

The second part should balance out the dry theory with some juicy examples of the possibilities this approach offers, in particular focusing on generating HTML.

I chose 40 minutes since I would like to be able to go into both the theoretical and the practical side of things, but I'm sure I can reduce it to 30 minutes as well if needed.

## Public description

Plain text strings are the bread and butter of web programming. When programming for the web one juggles an amazing amount of different languages. However, these strings that we generate are actually plain text representations of data structures. They need to be serialized to be sent over the wire, but that doesn't need to happen until the data leaves the application.

Instead we often end up generating and manipulating textual representations directly. This may seem easier at first, but keeping in mind the subtleties of the representation is hard work, and slipping up can cost us dearly, just think of SQL injection attacks or cross-site scripting.

Security isn't the only reason to rethink how we deal with plain text I/O in our apps. By manipulating data structures rather than strings, we could be coding and reasoning at a higher level.

In this talk you will get a critical assessment of how Ruby apps tend to deal with parsing and generating web related languages. It will make you think, and look at your old code with new eyes. We will have a look at what tooling is there, and which parts are missing, showing practical examples of how you could be doing things differently.

## Bio

Arne is a professional software developer focused on web development. He's been passionate about Ruby since 2006, but has only really become involved in the community after moving to Berlin in 2012.

He has worked in various fields, from e-commerce for telecom, concert tickets and resale, to medical OCR/OMR applications, and has made contributions to several Free and Open Source projects. His personal interests include both natural and formal languages, and he spent the best of two years in the far east learning to speak and write Chinese.


## First draft
# Web Linguistics : Towards Higher Fluency

Web programmers deal with more formal languages than they would think. There are Ruby, HTML, CSS, Javascript, and books have been filled about each of these. Yet this is just the tip of the iceberg. We need to be fluent in each of these to use them effectively, but how fluency is defined varies between language learners.

A language can be learned by engaging with native speakers, to see how it is typically used. What follows is imitation and experimentation to see what works and what doesn't. This is a great way of getting started learning a language, but at some point the learner will reach a plateau. Because she can understand and be understood there is little incentive to master the language further.

This is what happens to many programmers as well. Languages are mostly learned on the job, and at some point we think we know them. What is there left to say about HTML? What else do I need to know about Ruby?

This is not just an issue of no longer improving as a programmer though. Despite decades of research about formal languages, parsers, compilers and code generators we often use very crude string based tools to deal with these languages.

This leads to security issues, which is reason enough to reassess how we do things. We might be able to make whole families of security vulnerabilities extinct. On top of that by dealing with these languages as structured data structures we can make our code less brittle, and more powerful.


# Web Linguistics : Towards Higher Fluency

Plain text strings are the bread and butter of web programming. When programming for the web one juggles an amazing amount of different languages. However, these strings that we generate are actually plain text representations of data structures. They need to be serialized to be sent over the wire, but that doesn't need to happen until the data leaves the application.

Instead we often end up generating and manipulating textual representations directly. This may seem easier at first, but keeping in mind the subtleties of the representation is hard work, and slipping up can cost us dearly, just think of SQL injection attacks or cross-site scripting.

Security isn't the only reason to rethink how we deal with plain text I/O in our apps. By manipulating data structures rather than strings, we could be coding and reasoning at a higher level.

In this talk you will get a critical assessment of how Ruby apps tend to deal with parsing and generating web related languages. It will make you think, and look at your old code with new eyes. We will have a look at what tooling is there, and which parts are missing, showing practical examples of how you could be doing things differently.
