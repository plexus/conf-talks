A character is one of the things you see on my screen.

For the purpose of this presentation only these are considered characters.

---

These characters are called digits.

---

These characters are called letters.

---

These characters are called special characters.

---

A number is the digit zero, or a digit other than zero, followed by zero or more digits.

---

A string is a double quote, followed by zero or more characters except double quote, followed by a double quote.

---

"True" and "false" are called booleans. These are the only booleans.

---

A primitive is a number, a string, or a boolean.

---

The characters 'opening parenthesis', 'closing paranthesis', 'single quote', 'double quote', 'comma', 'semicolon', and 'backtick' are called "reserved special characters".

---

A symbol is any combination of letters, digits, and special characters that are not reserved special characters.

---

An atom is a primitive or a symbol.

An atom is a kind of value. There are other values, but we haven't spoken of them yet.

---

A cons cell is a pair of two values. It is represented as an opening parenthesis, a value, a full stop, a value, a closing parenthesis.

---

The first value in a cons cell is called the "car".

The second value in a cons cell is called the "cdr".

---

A cons cell is a kind of value. Since a cons cell is a pair of two values, it follows that cons cells can contain cons cells.

---

Nil is a pseudo-value representing the absence of a value. When one or both values in a cons cell are absent, we write "nil" in their place.

---

A convention is something a group of people agree to.

---

By convention nil has a second notation: an opening parenthesis immediately followed by a closing parenthesis.

By convention nil can also be called "the empty list".

---

By convention the "car" of a list is also called the "first element" of the list, and the "cdr" of the list is called the "rest" of the list.

---

A list is the empty list, or a cons cell with a value as its first element, and a list as its rest.

---

By convention we may write a list by only writing the first elements, enclosed by parentheses.

---

An S-expression is an atom, or a list.

---

A binding is a table mapping symbols to values.

---

We can define a procedure called "eval", which takes a binding and an s-expression, and evaluates the s-expression according to specific rules, yielding a result.

The current binding is the binding that eval is currently using.

We will define eval incrementally.

---

A primitive is a number, a string, or a boolean.

---

The result of eval'ing a primitive is the primitive itself.

---

A symbol is any combination of letters, digits, and special characters that are not reserved special characters.

---

The result of eval'ing a symbol is obtained by looking up the value associated with the symbol in the binding.

---

By convention, the first element of a list is said to be in function position, the other elements are in argument position.

---

The result of evaluating a list depends upon the  element in function position. We will define several specific cases.

---

If the first element of the list is the symbol "quote", then the result is the rest of the list.

---

By convention we can omit the outer parentheses and write a single quotation mark, instead of the symbol "quote".

---

A function is a procedure that can calculate an output value based on one or more input values.

The EVAL procedure we have defined is a function

APPLY is function that takes a function and a list of arguments, and invokes the function, computing its result.

---

Built-in functions are functions that we don't have to define ourselves. They have a name, which is a symbol.

---

If the first element of the list is a function, then the EVAL procedure will EVAL every element in the REST of the list, and then APPLY the function to the evaluated arguments.

---

These are some examples using only the definitions we have seen so far.

---

If the element in function position is the symbol "if", then EVAL will evaluate the element in the first argument position. If it is true, then the result of evaluating the if is the result of evaluating the element in the second argument position. If it is false, then the result of evaluating the if is the result of evaluating the element in the third argument position.

---

If the function position contains the symbol def, then EVAL expects a symbol and a value as the second and third elements of the list. The value will be added to the current binding under the given name, and can be used consequently.

---

If the first element of the list is the symbol "lambda", then the second element of the list is called the "argument list", and the remaining elements in the list are called the lambda's "body".

---

When we EVAL a list that starts with "lambda", then the result is a "function object", containing the current binding, the lambda's argument list, and the lambda's body.

---

We can store this function object in the current binding using `def`.

EVAL'ing a list that starts with a name with a function follows the same pattern as built-in functions: EVAL evaluates all arguments, then passes the work on to APPLY, which will APPLY the function object to the arguments.

---

APPLY now proceeds in two steps. It takes the list of argument names, and the supplied arguments, and adds them pair-wise to the binding.

---

Now that the arguments are bound, APPLY can procede to evaluate the body of the lambda.

---

A macro is a procedure that takes one or more s-expressions as its inputs, and returns an s-expression as output.

A macro is defined with `defmacro`. When EVAL encounters the use of a macro, it will not evaluate the arguments, but instead pass it the unevaluated s-expressions. The result of calling the macro is an s-expression that EVAL will evaluate.

---
