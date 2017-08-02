package trees;

public class AVLTree {

    private class Node {

        private int num;

        private Node parent;
        private Node leftNode;
        private Node rightNode;

        private Node(int num) {
            this.num = num;
        }

        private void setLeftNode(Node node) {
            if (node != null) node.parent = this;
            this.leftNode = node;
        }

        private void setRightNode(Node node) {
            if (node != null) node.parent = this;
            this.rightNode = node;
        }

        // Высоты левого и правого поддеревьев узла
        private int getLeftHeight() {
            return getSubtreeHeight(leftNode);
        }

        private int getRightHeight() {
            return getSubtreeHeight(rightNode);
        }

        // Коэффициент сбалансированности левого и правого поддерева узла
        private int getBalanceFactor() {
            return getLeftHeight() - getRightHeight();
        }

        // Высота узла
        private int getSubtreeHeight(Node node) {
            if (node != null) {
                return 1 + Math.max(getSubtreeHeight(node.leftNode), getSubtreeHeight(node.rightNode));
            } else
                return 0;
        }

        private void rebalance() {
            if (getState() == -1) {
                if (this.leftNode != null && this.leftNode.getBalanceFactor() < 0)
                    LR_Rotation();
                else
                    R_Rotation();
            }
            else if (getState() == 1) {
                if (this.rightNode != null && this.rightNode.getBalanceFactor() > 0)
                    RL_Rotation();
                else
                    L_Rotation();
            }
        }

        // -1: левое поддерево перевешивает
        //  1: правое поддерево перевешивает
        //  0: поддеревья сбалансированы
        //  Значения высот =1/-1 означает полусбалансированные поддеревья. Они не противоречат принципам АВЛ-деревьев
        private int getState() {
            if (getBalanceFactor() > 1)
                return -1;
            if (getBalanceFactor() < -1)
                return 1;

            return 0;
        }

        // region Повороты (балансировка) поддеревьев

        private void L_Rotation() {
            Node newRootNode = this.rightNode;
            repRootNode(newRootNode);

            this.setRightNode(newRootNode.leftNode);
            newRootNode.setLeftNode(this);
        }

        private void R_Rotation() {
            Node newRootNode = this.leftNode;
            repRootNode(newRootNode);

            this.setLeftNode(newRootNode.rightNode);
            newRootNode.setRightNode(this);
        }

        private void LR_Rotation() {
            this.leftNode.L_Rotation();
            this.R_Rotation();
        }

        private void RL_Rotation() {
            this.rightNode.R_Rotation();
            this.L_Rotation();
        }

        private void repRootNode(Node newRootNode) {
            if (this.parent == null) {
                rootNode = newRootNode;
            } else {
                if (this.parent.leftNode == this) {
                    this.parent.setLeftNode(newRootNode);
                }
                else if (this.parent.rightNode == this) {
                    this.parent.setRightNode(newRootNode);
                }
            }

            newRootNode.parent = this.parent;
           // this.parent = newRootNode;
        }

        // endregion
    }

    private Node rootNode = null;

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void clear() {
        rootNode = null;
        count = 0;
    }


    // Добавление узла в дерево
    public void add(int num) {
        if (rootNode == null) {
            rootNode = new Node(num);
            count++;
        } else {
            recursionAdd(rootNode, num);
        }
    }

    private void recursionAdd(Node node, int num) {
        if (num < node.num) {
            if (node.leftNode == null) {
                node.setLeftNode(new Node(num));
                count++;
            } else {
                recursionAdd(node.leftNode, num);
            }
        } else {
            if (node.rightNode == null) {
                node.setRightNode(new Node(num));
                count++;
            } else {
                recursionAdd(node.rightNode, num);
            }
        }

        node.rebalance();
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

    private Node findAndGetNode(Node node, int num) {
        if (node == null) return null;
        else if (node.num == num) return node;

        if (num < node.num) return findAndGetNode(node.leftNode, num);
        else return findAndGetNode(node.rightNode, num);
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
            Node current = findAndGetNode(rootNode, num);
            Node parent = current.parent;

            count--;

            // Если удаляемый узел не имеет правого потомка
            if (current.rightNode == null) {
                if (parent == null) {
                    rootNode = current.leftNode;
                    rootNode.parent = null;
                }
                else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.setLeftNode(current.leftNode);
                    }
                    // Правое поддерево
                    else {
                        parent.setRightNode(current.leftNode);
                    }
                }
            }
            // Если удаляемый узел имеет правого потомка у которого нет левого потомка
            else if (current.rightNode.leftNode == null) {
                if (parent == null) {
                    rootNode = current.rightNode;
                    rootNode.parent = null;
                }
                else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.setLeftNode(current.rightNode);
                        parent.leftNode.setLeftNode(current.leftNode);
                    }
                    // Правое поддерево
                    else {
                        parent.setRightNode(current.rightNode);
                        parent.rightNode.setLeftNode(current.leftNode);
                    }
                }
            }
            // Если удаляемый узел имеет правого потомка у которого есть левый потомок
            else {
                Node minNode = leftDescent(current.rightNode.leftNode);

                minNode.parent.setLeftNode(minNode.rightNode);
                minNode.rightNode = null;

                if (parent == null) {
                    rootNode = minNode;
                    rootNode.parent = null;
                    rootNode.setRightNode(current.rightNode);
                    rootNode.setLeftNode(current.leftNode);
                } else {
                    // Левое поддерево
                    if (parent.num > current.num) {
                        parent.setLeftNode(minNode);
                        parent.leftNode.setLeftNode(current.leftNode);
                        parent.leftNode.setRightNode(current.rightNode);
                    }
                    // Правое поддерево
                    else {
                        parent.setRightNode(minNode);
                        parent.rightNode.setLeftNode(current.leftNode);
                        parent.rightNode.setRightNode(current.rightNode);
                    }
                }
            }

            if (parent != null)
                parent.rebalance();
            if (rootNode != null && rootNode.getState() != 0)
                rootNode.rebalance();
        }
    }
}