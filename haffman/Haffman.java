package algs.haffman;

import algs.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public class Haffman {

    public static ArrayList<Symbol> startHaffman(ArrayList<Symbol> weightsList) {
        ArrayList<HaffmanNode> nodes = new ArrayList<>();

        for (int i = 0; i < weightsList.size(); i++) {
            nodes.add(new HaffmanNode(weightsList.get(i).getName(), weightsList.get(i).getWeight()));
        }

        codingHaffmanTree(nodes);
        HaffmanNode rootNode = nodes.get(0);
        ArrayList<Symbol> result = new ArrayList<>();
        writeHaffmanCode(rootNode, result, "");

        return result;
    }

    private static void codingHaffmanTree(ArrayList<HaffmanNode> nodes) {
        if (nodes.size() == 1) return;

        Collections.sort(nodes);

        HaffmanNode node1 = nodes.get(0);
        HaffmanNode node2 = nodes.get(1);

        node1.setBit("1");
        node2.setBit("0");
        HaffmanNode newNode = new HaffmanNode(node1.getName().concat("-" + node2.getName()),
                node1.getWeight() + node2.getWeight());
        newNode.setLeftNode(node1);
        newNode.setRightNode(node2);

        nodes.remove(0);
        nodes.remove(0);

        nodes.add(newNode);

        codingHaffmanTree(nodes);
    }

    private static void writeHaffmanCode(HaffmanNode root, ArrayList<Symbol> symbols, String code) {
        if (root.getLeftNode() == null && root.getRightNode() == null) {
            Symbol s = new Symbol(root.getName(), root.getWeight());
            s.setCode(code.concat(root.getBit()));
            symbols.add(s);

            return;
        }

        if (root.getLeftNode() != null) {
            writeHaffmanCode(root.getLeftNode(), symbols, (root.getBit() == null) ? "" : code.concat(root.getBit()));
        }

        if (root.getRightNode() != null) {
            writeHaffmanCode(root.getRightNode(), symbols, (root.getBit() == null) ? "" : code.concat(root.getBit()));
        }
    }
}
