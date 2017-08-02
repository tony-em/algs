package algs.haffman;

class HaffmanNode implements Comparable<HaffmanNode> {

    private HaffmanNode leftNode;
    private HaffmanNode rightNode;
    private double weight;
    private String bit;
    private String name;

    HaffmanNode(String name, double weight) {
        this.weight = weight;
        this.name = name;
    }

    HaffmanNode getLeftNode() {
        return leftNode;
    }

    HaffmanNode getRightNode() {
        return rightNode;
    }

    void setLeftNode(HaffmanNode leftNode) {

        this.leftNode = leftNode;
    }

    void setRightNode(HaffmanNode rightNodeNode) {
        this.rightNode = rightNodeNode;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    void setBit(String bit) {
        this.bit = bit;
    }

    void setName(String name) {
        this.name = name;
    }

    double getWeight() {
        return weight;
    }

    String getBit() {
        return bit;
    }

    String getName() {
        return name;
    }

    @Override
    public int compareTo(HaffmanNode node) {
        return (this.weight > node.getWeight()) ? 1 : -1;
    }
}
