package algs.graph;

import java.util.ArrayList;

public class Vertex {

    private int numValue;
    private String strValue;

    public Vertex(int numValue) {
        this.numValue = numValue;
    }

    public Vertex(String strValue) {
        this.strValue = strValue;
    }

    public int getNumValue() {
        return numValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public String getValueInStringFormat() {
        return (strValue == null) ? String.valueOf(numValue) : strValue;
    }

    public void setNumValue(int numValue) {
        strValue = null;
        this.numValue = numValue;
    }

    public void setStrValue(String strValue) {
        numValue = 0;
        this.strValue = strValue;
    }

    public static ArrayList<Vertex> initGetVertexList(int[] values) {
        ArrayList<Vertex> vertexList = new ArrayList<>(values.length);

        for (int val : values) {
            vertexList.add(new Vertex(val));
        }

        return vertexList;
    }

    public static ArrayList<Vertex> initGetVertexList(String[] values) {
        ArrayList<Vertex> vertexList = new ArrayList<>(values.length);

        for (String val: values) {
            vertexList.add(new Vertex(val));
        }

        return vertexList;
    }


}
