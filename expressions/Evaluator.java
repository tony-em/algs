package algs.expressions;

import java.util.Stack;

public abstract class Evaluator {

    public double evaluate(String strExpression) {
        char[] symbols = Notations.convertInfixToPostfix(strExpression).toCharArray();
        Stack<Character> stack = new Stack<>();
        StringBuilder str = new StringBuilder();

        return 0;
    }
}