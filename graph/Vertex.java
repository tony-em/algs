package algs.graph;

import java.util.ArrayList;

public class Vertex {

    private String key;

    public Vertex(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String strValue) {
        this.key = strValue;
    }

    public static ArrayList<Vertex> initVertexList(String[] keys) {
        ArrayList<Vertex> vertexList = new ArrayList<>(keys.length);

        for (String val: keys) {
            vertexList.add(new Vertex(val));
        }

        return vertexList;
    }
}