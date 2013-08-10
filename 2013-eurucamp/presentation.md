# Web Linguistics

by [plexus](https://github.com/plexus)

---

# Abolish Your Templates, Burn Your Helpers

---

# Preventing XSS Through Architecture

---

![Screenshot of the Langsec website (langsec.org)](images/langsec_website.png)

---

LANGSEC regards insecurity as a consequence of ad hoc programming of input handling

Treat all valid or expected inputs as a formal language

Treat the respective input-handling routines as a recognizer for that language

The recognizer must match the language in required computation power

---

When input handling is done in ad hoc way, input recognition and validation code

* ends up scattered throughout the program
* does not match the programmers' assumptions about safety and validity of data
* provides ample opportunities for exploitation

---

http://langsec.org

Meredith L. Patterson : "The Science of Insecurity"

---

We can apply similar reasoning to *output* handling

---

Is the output generation scattered through the program?

Does it match the programmers' assumptions?

---

# XSS is a Failure at the Language Level
