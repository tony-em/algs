package algs.expressions;

import java.util.Stack;

public abstract class Evaluator {

    public static String evaluate(String strExpression) {
        String[] symbols = Notations.convertInfixToPostfix(strExpression).split(" ");
        Stack<String> stack = new Stack<>();

        boolean nonCalcFlag = false;
        for (String s : symbols) {
            char operator = s.toCharArray()[0];
            if (!Notations.equalsSymbolWithOperator(operator)) {
                stack.push(s);
            } else {
                String s2 = stack.pop(), s1 = stack.pop();
                double op1, op2;
                try {
                    op1 = Double.valueOf(s1);
                    op2 = Double.valueOf(s2);
                    s1 = String.valueOf(getResultOperation(operator, op1, op2));
                } catch (Exception e1) {
                    s1 = s1 + " " + s2 + " " + operator;
                    if (!nonCalcFlag) {
                        nonCalcFlag = true;
                    }
                }

                stack.push(s1);
            }
        }

        try {
            return (nonCalcFlag) ? Notations.convertPostfixToInfix(stack.pop()) : stack.pop();
        } catch (Exception e) {
            throw new NullPointerException("Incorrect input expression");
        }
    }

    private static String eval(String operand2, String operand1, char operator) {
        double op1, op2;
        try {
            op1 = Double.valueOf(operand1);
            op2 = Double.valueOf(operand2);
        } catch (Exception e1) {
            return operand1 + " " + operand2 + " " + operator;
        }

        return String.valueOf(getResultOperation(operator, op1, op2));
    }

    public static double getResultOperation(char operator, double op1, double op2) {
        switch (operator) {
            case Notations.ADD:
                op1 += op2;
                break;
            case Notations.SUB:
                op1 -= op2;
                break;
            case Notations.MUL:
                op1 *= op2;
                break;
            case Notations.DIV:
                op1 /= op2;
                break;
            case Notations.POW:
                op1 = Math.pow(op1, op2);
                break;
        }

        return op1;
    }
}