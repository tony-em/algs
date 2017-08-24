package algs.expressions;

import java.util.Stack;

public abstract class Evaluator {

    public static String evaluate(String strExpression) {
        String[] symbols = Notations.convertInfixToPostfix(strExpression).split(" ");
        Stack<String> stack = new Stack<>();

        for (String s : symbols) {
            char operator = s.toCharArray()[0];
            if (!Notations.equalsSymbolWithOperator(operator)) {
                stack.push(s);
            } else {
                stack.push(String.valueOf(eval(stack.pop(), stack.pop(), operator)));
            }
        }

        if (stack.size() == 1)
            return stack.pop();
        else {
            StringBuilder sb = new StringBuilder();
            while (stack.size() != 0) {
                sb.insert(0, stack.pop());
            }

            return sb.toString();
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
            default:
                return null;
        }

        return String.valueOf(op1);
    }
}