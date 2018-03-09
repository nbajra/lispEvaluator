/**
 *  Created By Nikita Bajracharya
 *  Copyright © 2017 Nikita Bajrachary. All rights reserved.
 */
package PJ2;
import java.util.*;

public class LispExpressionEvaluator
{
    // Current input Lisp expression
    private String inputExpr;

    // Main expression stack & current operation stack
    private Stack<Object> inputExprStack;
    private Stack<Double> evaluationStack;


    // default constructor
    // set inputExpr to "" 
    // create stack objects
    public LispExpressionEvaluator()
    {
        this("");
    }

    // constructor with an input expression 
    // set inputExpr to inputExpression 
    // create stack objects
    public LispExpressionEvaluator(String inputExpression) 
    {
        inputExpr = inputExpression;
        inputExprStack = new Stack<>();
        evaluationStack = new Stack<>();
    }

    // set inputExpr to inputExpression 
    // clear stack objects
    public void reset(String inputExpression) 
    {
        inputExpr = inputExpression;
        inputExprStack.clear();
        evaluationStack.clear();
    }


    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from inputExprStack and push them onto 
    // 			evaluationStack until you find an operator
    //  	Apply the operator to the operands on evaluationStack
    //          Push the result into inputExprStack
    //
    private void evaluateCurrentOperation() throws LispExpressionEvaluatorException
    {
        while (inputExprStack.size() > 0 && inputExprStack.peek() instanceof Double)
        {
            evaluationStack.push((Double)inputExprStack.pop());
        }

        if (inputExprStack.size() == 0)
        {
            throw new LispExpressionEvaluatorException("Too many ')' in the expression.");
        }

        Character operator = (Character) inputExprStack.pop();

        if (evaluationStack.size() == 0)
        {
            throw new LispExpressionEvaluatorException("No operands found for operator: " + operator);
        }

        Double result = 0d;
        switch(operator)
        {
            case '+':
                while (evaluationStack.size() > 0)
                {
                    result += evaluationStack.pop();
                }
                break;
            case '-':
                if (evaluationStack.size() == 1)
                {
                    result = -evaluationStack.pop();
                }
                else
                {
                    result = evaluationStack.pop();
                    while (evaluationStack.size() > 0)
                    {
                        result -= evaluationStack.pop();
                    }
                }
                break;
            case '*':
                result = evaluationStack.pop();
                while (evaluationStack.size() > 0)
                {
                    result *= evaluationStack.pop();
                }
                break;
            case '/':
                if (evaluationStack.size() == 1)
                {
                    if (evaluationStack.peek() == 0)
                    {
                        throw new LispExpressionEvaluatorException("Zero operand is not allowed for '/' operator");
                    }
                    result = 1 / evaluationStack.pop();
                }
                else
                {
                    result = evaluationStack.pop();
                    while (evaluationStack.size() > 0)
                    {
                        if (evaluationStack.peek() == 0)
                        {
                            throw new LispExpressionEvaluatorException("Zero operand is not allowed for '/' operator");
                        }
                        result /= evaluationStack.pop();
                    }
                }
                break;
        }
        inputExprStack.push(result);
    }

    /**
     * This funtion evaluates current Lisp expression in inputExpr
     * It return result of the expression 
     *
     * The algorithm:  
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the inputExprStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the inputExprStack
     * Step 5		If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto evaluationStack 
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on evaluationStack
     * Step 8			Push the result into inputExprStack
     * Step 9    If you run out of tokens, the value on the top of inputExprStack is
     *           is the result of the expression.
     */
    public double evaluate() throws LispExpressionEvaluatorException
    {
        // only outline is given...
        // you need to add statements/local variables
        // you may delete or modify any statements in this method
        boolean isOperatorExpected = false;


        // use scanner to tokenize inputExpr
        Scanner inputExprScanner = new Scanner(inputExpr);
        
        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");

        // Step 1: Scan the tokens in the string.
        while (inputExprScanner.hasNext())
        {
		
     	    // Step 2: If you see an operand, push operand object onto the inputExprStack
            if (inputExprScanner.hasNextInt())
            {
                // This force scanner to grab all of the digits
                // Otherwise, it will just get one char
                String dataString = inputExprScanner.findInLine("\\d+");

                if (isOperatorExpected)
                {
                    throw new LispExpressionEvaluatorException("Operator expected after '(', but found: " + dataString);
                }
                
                inputExprStack.push(Integer.valueOf(dataString).doubleValue());
            }
            else
            {
                // Get next token, only one char in string token
                String aToken = inputExprScanner.next();
                char item = aToken.charAt(0);
                
                switch (item)
                {
     		        // Step 3: If you see "(", next token shoube an operator
                    case '(':
                        if (isOperatorExpected)
                        {
                            throw new LispExpressionEvaluatorException("Operator expected after '(', but found: " + item);
                        }
                        isOperatorExpected = true;
                        break;
     		        // Step 4: If you see an operator, push operator object onto the inputExprStack
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        if (!isOperatorExpected)
                        {
                            throw new LispExpressionEvaluatorException("Operator '" + item + "' was not expected but found");
                        }
                        isOperatorExpected = false;
                        inputExprStack.push(item);
                        break;
     		        // Step 5: If you see ")"  // steps in evaluateCurrentOperation() :
                    case ')':
                        if (isOperatorExpected)
                        {
                            throw new LispExpressionEvaluatorException("Operator expected after '(', but found: " + item);
                        }
                        evaluateCurrentOperation();
                        break;
                    default:  // error
                        throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                } // end switch
            } // end else
        } // end while
        
        // Step 9: If you run out of tokens, the value on the top of inputExprStack is
        //         is the result of the expression.
        //
        //         return result
        if (inputExprStack.size() != 1)
        {
            throw new LispExpressionEvaluatorException("Lisp expression is not valid. Please, check number of parenthesis.");
        }
       
	    return (Double)inputExprStack.pop();
    }


    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    //=====================================================================

    
    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
	expr.reset(s);
        try {
           result = expr.evaluate();
           System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionEvaluatorException e) {
            System.out.println("Evaluated result :"+e);
        }
        
        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 0))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )) (/ 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";

	evaluateExprTest(test1, expr, "16.50");
	evaluateExprTest(test2, expr, "-378.12");
	evaluateExprTest(test3, expr, "4.50");
	evaluateExprTest(test4, expr, "1.50");
	evaluateExprTest(test5, expr, "Infinity or LispExpressionEvaluatorException");
	evaluateExprTest(test6, expr, "LispExpressionEvaluatorException");
	evaluateExprTest(test7, expr, "LispExpressionException");
	evaluateExprTest(test8, expr, "LispExpressionException");
    }
}
