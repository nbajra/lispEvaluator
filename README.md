# lispEvaluator
This was a project implemented for my CSC 220 class.

/************************************************************************************
*
*
* Specification:
*
* In the language Lisp, each of the four basic arithmetic operators appears
* before an arbitrary number of operands, which are separated by spaces.
* The resulting expression is enclosed in parentheses. The operators behave
* as follows:
*
* (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
*
* (- a b c ...) returns a - b - c - ..., and (- a) returns -a.
*
* (* a b c ...) returns the product of all the operands, and (* a) returns a.
*
* (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a.
*
* Note: + * - / must have at least one operand
*
* You can form larger arithmetic expressions by combining these basic
* expressions using a fully parenthesized prefix notation.
* For example, the following is a valid Lisp expression:
*
*     (+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))
*
* This expression is evaluated successively as follows:
*
*    (+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 1))
*    (+ -6 24 -1.5 1)
*    17.5
*
* Requirements:
*
* - Design and implement an algorithm that uses Java API stacks to evaluate a
*   valid Lisp expression composed of the four basic operators and integer values.
* - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
* - Display result as floting point number with at 2 decimal places
* - Negative number is not a valid "input" operand, e.g. (+ -2 3)
*   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
* - There may be any number of blank spaces, >= 0, in between tokens
*   Thus, the following expressions are valid:
*       (+   (-6)3)
*       (/(+20 30))
*
* - Must use Java API Stack class in this project.
*   Ref: http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
* - Must throw LispExpressionEvaluatorException to indicate errors
* - Must not add new or modify existing data fields
* - Must implement these methods :
*
*       public LispExpressionEvaluator()
*       public LispExpressionEvaluator(String inputExpression)
*      public void reset(String inputExpression)
*      public double evaluate()
*      private void evaluateCurrentOperation()
*
* - You may add new private methods
*
*************************************************************************************/
