#1

Hi, my name is Arne Brasseur. I'm from Belgium but currently based in Berlin, where I help out with the RailsGirls workshops, and also coach at a weekly study group called the Ruby Monsters.

I enjoy contributing to Open Source, I'm part of team Shoes, which is the most awesome GUI toolkit for Ruby out there.

I am the organizer of a community event called Ruby Lambik, a beer tasting excursion in Belgium the day before Arrrrcamp. Lambik is a unique type of Belgian beer. We did the first edition of Ruby Lambik last October, and will definitely do it again next year, so come and talk if you want to taste a type of beer you have never tasted before.

#2

I am @plexus on Twitter and Github. The hash tag for this talk is #weblinguistics, or you can use the #langsec hash tag, since I'll be talking about language-based security.

#3

This talk will consist of three parts. The first part is about security, and explains how I got to the point that I'm giving a talk about it. In the second part I will explore a number of things that have been invented to make software engineering easier, and less error-prone. Finally we get to the real topic of this talk, formal languages, and how they can help to make your application better and more secure.

If you have seen this talk before you will notice that it has changed a lot. Before I would immediately start talking about formal languages, but I've decided to tell my story and provide more context, so it's easier for you to see the big picture. This means I won't have the chance to go into detail, but feel free to come and talk to me afterwards if you have any questions.

#4

I'm an application developer, but I'm here to talk to you about security. There are many facets to security, and it takes a lot of attention to details to make sure an application has no vulnerabilities. However when we look at web applications there are a few problems that pop up time and again. One of these is Cross Site Scripting (XSS). By the end of this talk I will outline how to prevent XSS wholesale at the architectural level. But I'd first like to explain how I came to this point.

#5

For a little over a year I've been doing contract work for an Irish company called Ticketsolve. Ticketsolve builds a complete box office solution for theaters, concert halls and organizers. It's used to sell tickets on-line or at points of sale, and has lots of advanced reporting and other niftyness built in.

#6

The bulk of it is a Rails application which will soon be 6 years old. Part of my job for the last year has been to get us on to Rails 3.2. This has been a lot of work for several reasons, but we've finally switched to 3.2 last month.

#7

Last year several zero-day vulnerabilities were discovered in Rails, I am sure many of you still vividly remember rushing to upgrade production servers. These problems had to do either with input handling, all the smarts that Rails has to parse and handle incoming requests, and one exploit was a weakness in the sanitize helper, a method intended to strip out malicious javascript from untrusted HTML.

Because it was a hot topic at the time I prepared a talk for the Berlin Ruby user group covering all the recent Rails exploit in detail. I also talked a bit about general security principles. Because I had been really digging in to the topic I had acquired a healthy dose of paranoia, and I was hoping that some of that would rub off on the audience, making them think a bit harder about what it means to build a secure app.

#8

Around the same time I was working on upgrading our app from Rails 2.3 to Rails 3, and one of the security features that landed in Rails in 3.0 was the HTML SafeBuffer, a mechanism that promised to prevent Cross-Site scripting "by default". This upgrade was not an easy job. It basically meant tracing every string that gets interpolated in a view back to its source to see if it could legitimately contain HTML tags or entities. If so it had to be marked as "html_safe". Failing to do so would result in a messed up user interface, marking too much as html_safe would result in XSS vulnerabilities.

#9

Now if this is hard to do, it also means it's hard to assert whether you're vulnerable to XSS or not. Rails does not help you with this in any way, in fact it makes it really easy to slip up and leave a hole in your defenses.

#10

Writing better software. This is the part where we explore some existing techniques and technologies that have made programming better.

#11

Writing software is hard. Writing good software, software that is reliable, secure, fills a real need and is delivered within time and budget is extremely hard. Over time people have come up with many ways to make this process easier and the resulting software better. Some of these techniques are not technical. The agile movement has done a great job of shifting the focus back to the people writing software, and iterative development, pair programming or good testing practices are obviously very important. However here I will look at some technical means to improve software development.

#12

Technical improvements in software engineering either help to make the programmer be more productive, or help to make software more correct, or both.

Higher productivity might be assumed by more concise code, more reuse, or by making code more intention-revealing and less confusing.

Higher correctness can be achieved by making certain categories of bugs impossible, by detecting and preventing security issues.

#13

However the trade of is often that certain restrictions apply, and certain rules have to be followed. The programmer loses a little bit of freedom.

#14

But by imposing these restrictions it becomes possible to let the computer to more work for you. We can shift some of the mental load from the programmer to the computer.

#15

I will look at three technical improvements to make this more concrete. We will first look at garbage collection, then functional programming, and finally have a look at Rack.

#16

When programming in languages without garbage collection like C, all memory used must first be explicitly reserved, and after use it must be released again. Because of this if a program routinely requested memory it didn't release afterwards you would have a memory leak, and gradually the program would eat up all available memory.

On the other hand the program could be holding on to a pointer that points to memory that has already been released. Such dangling pointers when used could crash the program by causing a Segmentation Fault.

#17

The introduction of garbage collection has made us more productive, since we no longer need to do this work of allocating and deallocating memory manually.

#18

It has also made programs more correct and secure. Segmentation faults and memory leaks have become (almost) impossible.

#19

Functional programming has been around since the 50's. It has mostly remained a niche technology, but interest in FP is on the rise.

#20

FP imposes several restrictions, and when you're coming from an imperative language this might be quite a challenge to get used to. You can't update variables, functions can not have side effects, you can't program the same way in Ruby as in Haskell. This is one of the reasons that people shy away from FP.

#21

But programming in a functional style tends to prevent a lot of bugs. By no longer sharing mutable state across different parts of the program, it becomes easier to reason about.

#22

Finally by following the restrictions that functional programming imposes, we allow the computer to do more work. The system can memoize intermediate values, evaluate lazily, and it makes doing concurrency almost trivial.

#23

Finally we'll look at Rack. Rack is the component that sits underneath all Ruby web frameworks, handling the low-level details of HTTP.

#24

Rack has made programmers more productive, there's a lot of low level protocol handling we no longer need to care about, and our apps work on a plethora of application servers out of the box.

#25

Rack has also made apps more correct. Since all this low level handling is only implemented once as open source, many people help to improve it and make it rock solid. And as programmers we no longer need to care about the low level details of request parsing, response buffering, etc.

#26

The reason rack can do this is that HTTP requests and responses form a formal language. Some requests are formally correct, others are not, and there specific rules as to how to parse and generate these. Only Rack needs to know these details. As programmers we deal with objects that represent these requests/responses, providing an adequate more high level abstraction.

#27

And with that we've seamlessly arrived at the formal languages part of this talk.

#28

An application can be considered a box with. There is a clear boundary between the inside and the outside off the app. The app communicates across this boundary with database servers, internal or third party services, and with browsers that contact it.

Virtually all this communication takes the form of a formal language. This could be SQL, HTTP, HTML, or maybe SOAP. In each instance there is a formal grammar that defines what form the communication takes, and how the contents should be interpreted.

#29

It makes sense to think clearly about these boundaries. Seen from outside, all that defines your application are its boundaries. If a different application communicates identically as the original, it is for an outside observer identical.

So it is the boundaries that define the inside, that mandate the complexity, of the application.

#30

When dealing with these formal languages that cross these boundaries, we can look at them in two ways, as strings, or as trees.

#31

At the boundary it will always be a string, or we would not be able to transmit it. But logically that string represents a tree.

Your application logic will work on that tree, adding to it, removing from it, or changing its structure. If it's doing that directly on the string representation, then a big opportunity is lost, and many problems will ensue.

#32

Let's take the case of HTML. When a browser requests a page, your application is responsible for building and sending back a HTML document. HTML documents are tree structures, as captured in the Document Object Model.

Your app builds up that tree, then transmits it over the network by serializing it to a string, and finally the browser reinterprets that string as a tree, and displays it.

If these two are identical, then the communication was succesful.

#33

However, this is not what we do in typical Ruby web applications. Views, helpers, even decorators, cells and other "view technologies" work on strings.

This makes it very easy to make mistake. At all time the programmer is responsible for keeping in mind the complexities of the target language.

The danger is that some user input manages to sneak into the serialized representation, thus changing what is understood on the other side.

#34

Hexp is an attempt to fix this situation, by providing a common abstraction of a HTML document. There is solid groundwork in place, but the project needs help.

#35-41

Hexp imposes certain limitations. If you want to reap the full benefits you need to do the parsing/generating of HTML only at the boundaries, and only work with syntax trees internally.

But your view code can become a lot smarter, because now it knows what it's doing. It's no longer just tossing strings together. It's dealing directly with a HTML document.

This will make your code more correct, because the low level language details are handled elsewhere.

It will make your code more secure, because there is a clear distinction between strings, wherever they come from, and HTML document fragments. Only what is explicitly designed to be HTML will be considered HTML. The details of various HTLM escaping contexts are handled by a single component.

This is possible because HTML is a formal language. Always keep an eye out for thins that are languages, and not handled at the right level of detail.

Finally we can offset some mental load to the system, making our lives as programmers easier.
