package algs.expressions;

import java.util.Stack;

public abstract class Notations {

    static final char
            ADD = '+',
            SUB = '-',
            DIV = '/',
            MUL = '*',
            POW = '^',
            BL = '(',
            BR = ')';


    static final char[] operators = new char[]{ADD, SUB, DIV, MUL, POW, BL, BR};

    private enum NotationType {
        PREFIX, INFIX, POSTFIX
    }

    public static int getOperatorPriority(char operator) {
        switch (operator) {
            case ADD:
            case SUB:
                return 1;

            case MUL:
            case DIV:
                return 2;

            case POW:
                return 3;

            default:
                return -1;
        }
    }

    public static boolean equalsSymbolWithOperator(char symbol) {
        for (int i = 0; i < operators.length; i++) {
            if (symbol == operators[i]) {
                return true;
            }
        }

        return false;
    }

    public static String convertInfixToPostfix(String infixExpression) {
        return parseExpression(infixExpression, NotationType.POSTFIX);
    }

    public static String convertInfixToPrefix(String infixExpression) {
        return parseExpression(infixExpression, NotationType.PREFIX);
    }

    public static String convertPostfixToInfix(String postfixExpression) {
        return parseToInfix(postfixExpression, NotationType.POSTFIX);
    }

    public static String convertPrefixToInfix(String prefixExpression) {
        return parseToInfix(prefixExpression, NotationType.PREFIX);
    }

    private static String parseToInfix(String expression, NotationType type) {
        String[] symbols = expression.split(" ");
        Stack<String> stack = new Stack<>();

        for (int i = (type == NotationType.POSTFIX) ? 0 : symbols.length - 1; ; i = (type == NotationType.POSTFIX) ? ++i : --i) {
            if ((type == NotationType.POSTFIX && i > symbols.length - 1) || (type == NotationType.PREFIX && i < 0))
                break;

            char s = symbols[i].toCharArray()[0];
            if (!equalsSymbolWithOperator(s)) {
                stack.push(symbols[i]);
            } else {
                String op2 = stack.pop(), op1 = stack.pop();

                if (type == NotationType.POSTFIX) {
                    stack.push("(" + op1 + s + op2 + ")");
                } else {
                    stack.push("(" + op2 + s + op1 + ")");
                }
            }
        }

        try {
            String s = stack.pop();
            return s.substring(1, s.length() - 1);
        } catch (Exception e) {
            throwException("Incorrect input notation type");
        }

        return null;
    }

    private static String parseExpression(String infixExpression, NotationType type) {
       infixExpression = infixExpression
                .replaceAll(" ", "")
                .replaceAll("\\(-", "(0-");

        if (infixExpression.charAt(0) == '-') {
            infixExpression = "0".concat(infixExpression);
        }

        char[] symbols = infixExpression.toCharArray();
        Stack<Character> stack = new Stack<>();
        StringBuilder str = new StringBuilder();

        boolean isEndOperand = false;
        int bracketsCounter = 0;
        for (int i = (type == NotationType.POSTFIX) ? 0 : symbols.length - 1; ; i = (type == NotationType.POSTFIX) ? ++i : --i) {

            if ((type == NotationType.POSTFIX && i > symbols.length - 1) || (type == NotationType.PREFIX && i < 0))
                break;

            char s = symbols[i];
            if (!equalsSymbolWithOperator(s)) {
                appendStr(Character.toString(s), "", str, type);
                if (!isEndOperand) isEndOperand = true;
            } else if (isEndOperand) {
                appendStr("", " ", str, type);
                isEndOperand = false;
            }

            if ((s == BL && type == NotationType.POSTFIX) || (s == BR && type == NotationType.PREFIX)) {
                stack.push(s);
                bracketsCounter++;

            } else if ((s == BR && type == NotationType.POSTFIX) || (s == BL && type == NotationType.PREFIX)) {
                try {
                    s = stack.pop();
                    while ((s != BL && type == NotationType.POSTFIX) || (s != BR && type == NotationType.PREFIX)) {
                        appendStr(Character.toString(s), " ", str, type);
                        s = stack.pop();
                    }
                } catch (Exception e) {
                    throwException("Brackets are incorrectly placed!");
                }

                bracketsCounter--;

            } else if (equalsSymbolWithOperator(s)) {
                while (!stack.isEmpty() && getOperatorPriority(stack.peek()) >= getOperatorPriority(s)) {
                    appendStr(Character.toString(stack.pop()), " ", str, type);
                }

                stack.push(s);
            }
        }

        if (bracketsCounter != 0) throwException("Brackets are incorrectly placed!");
        if (isEndOperand) {
            appendStr("", " ", str, type);
        }

        while (!stack.isEmpty()) {
            if (stack.size() == 1) {
                appendStr(Character.toString(stack.pop()), "", str, type);
            } else {
                appendStr(Character.toString(stack.pop()), " ", str, type);
            }
        }

        return str.toString();
    }

    private static void appendStr(String s, String sp, StringBuilder str, NotationType type) {
        switch (type) {
            case PREFIX:
                str.insert(0, s).insert(0, sp);
                break;
            case POSTFIX:
                str.append(s).append(sp);
                break;
        }
    }

    private static void throwException(String msg) {
        throw new NullPointerException(msg);
    }
}