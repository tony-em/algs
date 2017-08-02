package algs.graph;

import sun.misc.Queue;

import java.util.ArrayList;

public class Graph {

    private int[][] paths;
    private ArrayList<Vertex> vertList;

    public Graph(ArrayList<Vertex> vertexList, int[][] adjacencyMatrix) {
        initGraph(vertexList, adjacencyMatrix);
    }

    public void setGraphData(ArrayList<Vertex> vertexList, int[][] adjacencyMatrix) {
        initGraph(vertexList, adjacencyMatrix);
    }

    private void initGraph(ArrayList<Vertex> vertexList, int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length != adjacencyMatrix[0].length || adjacencyMatrix.length != vertexList.size()) {
            throw new ArrayIndexOutOfBoundsException("Matrix size is failing the length of the array of vertices...");
        }

        this.paths = adjacencyMatrix;
        this.vertList = vertexList;
    }

    public void clear() {
        paths = null;
        vertList = null;
    }

    public boolean isEmpty() {
        return (paths == null || vertList == null);
    }

    public int getCount() {
        return (isEmpty()) ? 0 : vertList.size();
    }


    // Поиск в глубину
    public void DFS(String startVertexValue) {
        if (isEmpty()) throw new NullPointerException("Graph is not initialized...");

        int startIndex = getVertexIndex(startVertexValue);
        if (startIndex == -1)
            throw new NullPointerException("Vertex is not found...");

        System.out.print("Поиск в глубину (DFS): ");
        recursionDFS(getVertex(startVertexValue), new boolean[vertList.size()]);
        System.out.println();
    }

    private void recursionDFS(Vertex v, boolean[] visitFlags) {
        System.out.print(v.getValueInStringFormat() + " ");
        int index = vertList.indexOf(v);
        visitFlags[index] = true;

        for (int i = 0; i < vertList.size(); i++) {
            if (paths[index][i] != 0 && !visitFlags[i])
                recursionDFS(vertList.get(i), visitFlags);
        }
    }

    public void DFS(int startVertexValue) {
        this.DFS(String.valueOf(startVertexValue));
    }

    // Поиск в ширину
    public void BFS(String startVertexValue) {
        if (isEmpty()) throw new NullPointerException("Graph is not initialized...");

        int startIndex = getVertexIndex(startVertexValue);
        if (startIndex == -1)
            throw new NullPointerException("Vertex is not found...");

        boolean[] vertVisitFlags = new boolean[vertList.size()];

        Queue<Vertex> vertexQueue = new Queue<>();
        vertexQueue.enqueue(vertList.get(startIndex));
        vertVisitFlags[startIndex] = true;

        System.out.print("Поиск в ширину (BFS): ");
        while (!vertexQueue.isEmpty()) {
            Vertex v;

            try {
                v = vertexQueue.dequeue();
            } catch (InterruptedException ie) {
                return;
            }

            System.out.print(v.getValueInStringFormat() + " ");
            int index = getVertexIndex(v.getValueInStringFormat());

            for (int i = 0; i < vertList.size(); i++) {
                if (paths[index][i] != 0 && !vertVisitFlags[i]) {
                    vertVisitFlags[i] = true;
                    vertexQueue.enqueue(vertList.get(i));
                }
            }
        }
        System.out.println();
    }

    public void BFS(int startVertexIndex) {
        this.BFS(String.valueOf(startVertexIndex));
    }


    // Проверка на существование пути между парой вершин
    public boolean path(String beginVertex, String endVertex) {
        Vertex begin = getVertex(beginVertex);
        Vertex end = getVertex(endVertex);

        if (begin != null && end != null) {
            boolean[] booleans = new boolean[vertList.size()];
            recursionDFS(begin, booleans);

            return booleans[getVertexIndex(endVertex)];
        }
        else
            return false;
    }

    public boolean path(int beginVertex, int endVertex) {
        return path(String.valueOf(beginVertex), String.valueOf(endVertex));
    }

    // Степень вершины
    public int vertexDegree(String value) {
        int index = getVertexIndex(value);

        if (index == -1) {
            throw new NullPointerException("Vertex is not found...");
        } else {
            int degree = 0;

            for (int i = 0; i < vertList.size(); i++) {
                if (paths[index][i] != 0) {
                    ++degree;
                }
            }

            return degree;
        }
    }

    public int vertexDegree(int value) {
        return vertexDegree(String.valueOf(value));
    }


    // Индекс вершины в массиве
    private int getVertexIndex(String value) {
        if (vertList.get(0).getStrValue() == null) {
            int val = Integer.parseInt(value);

            for (int i = 0; i < vertList.size(); i++) {
                if (vertList.get(i).getNumValue() == val)
                    return i;
            }

        } else {

            for (int i = 0; i < vertList.size(); i++) {
                if (vertList.get(i).getStrValue().equals(value))
                    return i;
            }
        }

        return -1;
    }

    // Вершина в массиве
    private Vertex getVertex(String value) {
        if (vertList.get(0).getStrValue() == null) {
            int val = Integer.parseInt(value);

            for (int i = 0; i < vertList.size(); i++) {
                Vertex v = vertList.get(i);
                if (v.getNumValue() == val)
                    return v;
            }

        } else {

            for (int i = 0; i < vertList.size(); i++) {
                Vertex v = vertList.get(i);
                if (v.getStrValue().equals(value))
                    return v;
            }
        }

        return null;
    }
}
