package algs.trees;

import algs.expressions.Evaluator;
import algs.expressions.Notations;

import java.util.Stack;

public class ParseTree {

    private String expression;
    private Node root;

    public ParseTree(String expression) {
        this.expression = expression;
    }

    private class Node {

        private String symbol;

        private Node left;
        private Node right;

        public Node(String symbol) {
            this.symbol = symbol;
        }
    }

    public ParseTree build() {
        String[] symbols = Notations.convertInfixToPostfix(expression).split(" ");
        Stack<Node> stack = new Stack<>();

        for (String s : symbols) {
            char operator = s.toCharArray()[0];
            if (!Notations.equalsSymbolWithOperator(operator)) {
                stack.push(new Node(s));
            } else {
                Node n = new Node(s);
                n.right = stack.pop();
                n.left = stack.pop();
                stack.push(n);
            }
        }

        try {
            root = stack.pop();
            return this;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public String evaluate() {
        String s = eval(root);
        try {
            Double.parseDouble(s);
            return s;
        } catch (Exception e) {
            return Notations.convertPostfixToInfix(s);
        }
    }

    private String eval(Node node) {
        if (node.left == null && node.right == null) {
            return node.symbol;
        }

        if (Notations.equalsSymbolWithOperator(node.symbol.toCharArray()[0])) {
            String s1 = eval(node.left), s2 = eval(node.right);
            double op1, op2;
            try {
                op1 = Double.parseDouble(s1);
                op2 = Double.parseDouble(s2);
                op1 = Evaluator.getResultOperation(node.symbol.toCharArray()[0], op1, op2);
                s1 = String.valueOf(op1);
            } catch (Exception e) {
                s1 = s1 + " " + s2 + " " + node.symbol;
            }

            return s1;
        }

        return null;
    }

    //region Обходы дерева

    // Обход в прямом порядке | (Сверху вниз) | Префиксная нотация
    public void preOrderTraversal() {
        if (root != null) preOrderTraversal(root);
        else throw new NullPointerException();
        System.out.println();
    }

    private void preOrderTraversal(Node node) {
        System.out.print(node.symbol + " ");
        if (node.left != null) preOrderTraversal(node.left);
        if (node.right != null) preOrderTraversal(node.right);
    }


    // Обход в обратном порядке | (Снизу вверх) | Постфиксная нотация
    public void postOrderTraversal() {
        if (root != null) postOrderTraversal(root);
        else throw new NullPointerException();
        System.out.println();
    }

    private void postOrderTraversal(Node node) {
        if (node.left != null) postOrderTraversal(node.left);
        if (node.right != null) postOrderTraversal(node.right);
        System.out.print(node.symbol + " ");
    }


    // Обход в симметричном порядке | (Левый - Корень - Правый) | Псевдо-инфиксная нотация (без скобок)
    public void inOrderTraversal() {
        if (root != null) inOrderTraversal(root);
        else throw new NullPointerException();
        System.out.println();
    }

    private void inOrderTraversal(Node node) {
        if (node.left != null) inOrderTraversal(node.left);
        System.out.print(node.symbol + " ");
        if (node.right != null) inOrderTraversal(node.right);
    }


    // Обход в обратном симметричном порядке | (Правый - Корень - Левый) | Псевдо-инфиксная нотация (без скобок)
    public void postInOrderTraversal() {
        if (root != null) postInOrderTraversal(root);
        else throw new NullPointerException();
        System.out.println();
    }

    private void postInOrderTraversal(Node node) {
        if (node.left != null) postInOrderTraversal(node.left);
        System.out.print(node.symbol + " ");
        if (node.right != null) postInOrderTraversal(node.right);
    }

    //endregion
}