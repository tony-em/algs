package algs.trees;

public class BinaryTree {

    private Node rootNode = null;

    private int count = 0;

    private int depth = 0;
    private int i = 0;

    private class Node {

        private int num;
        private Node leftNode;
        private Node rightNode;

        private Node(int num) {
            this.num = num;
        }
    }

    public int getCount() {
        return count;
    }

    public int getHeight() {
        return depth;
    }

    public int getDepth() {
        return depth;
    }

    private void updateDepth1() {
        if (i > depth) depth = i;
        else depth = updateDepth2(rootNode) - 1;
    }

    private int updateDepth2(Node node) {
        int a = (node.leftNode == null) ? 0 : updateDepth2(node.leftNode);
        int b = (node.rightNode == null) ? 0 : updateDepth2(node.rightNode);
        return 1 + ((a >= b) ? a : b);
    }

    public void clear() {
        rootNode = null;
        count = 0;
        depth = 0;
    }


    // Добавление узла в дерево
    public void add(int num) {
        if (rootNode == null) {
            rootNode = new Node(num);
            count++;
        } else {
            recursionAdd(rootNode, num);
        }

        updateDepth1();
        i = 0;
    }

    private void recursionAdd(Node node, int num) {
        i++;
        if (num < node.num) {
            if (node.leftNode == null) {
                node.leftNode = new Node(num);
                count++;
            } else {
                recursionAdd(node.leftNode, num);
            }
        } else {
            if (node.rightNode == null) {
                node.rightNode = new Node(num);
                count++;
            } else {
                recursionAdd(node.rightNode, num);
            }
        }
    }


    // Проверка на существование узла с заданным значением в дереве
    public boolean find(int num) {
        return count != 0 && recursionFind(rootNode, num);
    }

    private boolean recursionFind(Node node, int num) {
        if (node == null) return false;
        else if (node.num == num) return true;

        if (num < node.num) return recursionFind(node.leftNode, num);
        else return recursionFind(node.rightNode, num);
    }

    private Node[] findAndGetNodeWithParent(Node node, Node parent, int num) {
        if (node.num == num && parent == null) return new Node[]{node, null};
        else if (node.num == num) return new Node[]{node, parent};

        if (num < node.num) return findAndGetNodeWithParent(node.leftNode, node, num);
        else return findAndGetNodeWithParent(node.rightNode, node, num);
    }


    //region Обходы дерева

    // Обход в прямом порядке | (Сверху вниз) | (Корень - Левый - Правый)
    public void preOrderTraversal() {
        if (rootNode != null) preOrderTraversal(rootNode);
        else System.out.print("Not nodes");
        System.out.println();
    }

    private void preOrderTraversal(Node node) {
        System.out.print(node.num + " ");
        if (node.leftNode != null) preOrderTraversal(node.leftNode);
        if (node.rightNode != null) preOrderTraversal(node.rightNode);
    }


    // Обход в обратном порядке | (Снизу вверх) | (Левый - Правый - Корень)
    public void postOrderTraversal() {
        if (rootNode != null) postOrderTraversal(rootNode);
        else System.out.print("Not nodes");
        System.out.println();
    }

    private void postOrderTraversal(Node node) {
        if (node.leftNode != null) postOrderTraversal(node.leftNode);
        if (node.rightNode != null) postOrderTraversal(node.rightNode);
        System.out.print(node.num + " ");
    }


    // Обход в симметричном порядке | (Левый - Корень - Правый) | (Упорядоченный по возрастанию массив)
    public void inOrderTraversal() {
        if (rootNode != null) inOrderTraversal(rootNode);
        else System.out.print("Not nodes");
        System.out.println();
    }

    private void inOrderTraversal(Node node) {
        if (node.leftNode != null) inOrderTraversal(node.leftNode);
        System.out.print(node.num + " ");
        if (node.rightNode != null) inOrderTraversal(node.rightNode);
    }


    // Обход в обратном симметричном порядке | (Правый - Корень - Левый) | (Упорядоченный по убыванию массив)
    public void postInOrderTraversal() {
        if (rootNode != null) postInOrderTraversal(rootNode);
        else System.out.print("Not nodes");
        System.out.println();
    }

    private void postInOrderTraversal(Node node) {
        if (node.rightNode != null) postInOrderTraversal(node.rightNode);
        System.out.print(node.num + " ");
        if (node.leftNode != null) postInOrderTraversal(node.leftNode);
    }

    //endregion


    // Максимальный и минимальный элемент в дереве
    public int min() {
        return leftDescent(rootNode).num;
    }

    public int max() {
        return rightDescent(rootNode).num;
    }


    // Спуск до концевого узла левого поддерева выбранного элемента (минимальный элемент)
    private Node leftDescent(Node node) {
        return (node.leftNode == null) ? node : leftDescent(node.leftNode);
    }

    // Спуск до концевого узла правого поддерева выбранного элемента (максимальный элемент)
    private Node rightDescent(Node node) {
        return (node.rightNode == null) ? node : rightDescent(node.rightNode);
    }

    // Удаление узла из дерева
    public void remove(int num) {
        if (count == 0) System.out.println("Not nodes");
        else if (!find(num)) System.out.println("Not found node with value " + num);
        else {
            Node[] nodes = findAndGetNodeWithParent(rootNode, null, num);
            Node current = nodes[0];
            Node parent = nodes[1];

            count--;

            // Если удаляемый узел не имеет правого потомка
            if (current.rightNode == null) {
                if (parent == null) rootNode = current.leftNode;
                else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.leftNode = current.leftNode;
                    }
                    // Правое поддерево
                    else {
                        parent.rightNode = current.leftNode;
                    }
                }
            }
            // Если удаляемый узел имеет правого потомка у которого нет левого потомка
            else if (current.rightNode.leftNode == null) {
                if (parent == null) rootNode = current.rightNode;
                else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.leftNode = current.rightNode;
                        parent.leftNode.leftNode = current.leftNode;
                    }
                    // Правое поддерево
                    else {
                        parent.rightNode = current.rightNode;
                        parent.rightNode.leftNode = current.leftNode;
                    }
                }
            }
            // Если удаляемый узел имеет правого потомка у которого есть левый потомок
            else {
                Node minNode = leftDescent(current.rightNode.leftNode);

                Node[] minNodeWithParent = findAndGetNodeWithParent(current, null, minNode.num);
                minNodeWithParent[1].leftNode = minNodeWithParent[0].rightNode;
                minNode.rightNode = null;

                if (parent == null) {
                    rootNode = minNode;
                    rootNode.rightNode = current.rightNode;
                    rootNode.leftNode = current.leftNode;
                } else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.leftNode = minNode;
                        parent.leftNode.leftNode = current.leftNode;
                        parent.leftNode.rightNode = current.rightNode;
                    }
                    // Правое поддерево
                    else {
                        parent.rightNode = minNode;
                        parent.rightNode.leftNode = current.leftNode;
                        parent.rightNode.rightNode = current.rightNode;
                    }
                }
            }
        }

        updateDepth1();
    }
}