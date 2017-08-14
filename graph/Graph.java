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
            throw new NullPointerException("Adjacency matrix size is failing the length of the array of vertices...");
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
    public void DFS(String startVertexKey) {
        checkGraph(startVertexKey);

        System.out.print("DFS: ");
        recursionDFS(getVertex(startVertexKey), new boolean[vertList.size()]);
        System.out.println();
    }

    private void recursionDFS(Vertex v, boolean[] visitFlags) {
        System.out.print(v.getKey() + " ");
        int index = vertList.indexOf(v);
        visitFlags[index] = true;

        for (int i = 0; i < vertList.size(); i++) {
            if (paths[index][i] != 0 && !visitFlags[i])
                recursionDFS(vertList.get(i), visitFlags);
        }
    }

    // Поиск в ширину
    public void BFS(String startVertexKey) {
        int startIndex = checkGraph(startVertexKey);
        boolean[] vertVisitFlags = new boolean[vertList.size()];

        Queue<Vertex> vertexQueue = new Queue<>();
        vertexQueue.enqueue(vertList.get(startIndex));
        vertVisitFlags[startIndex] = true;

        System.out.print("BFS: ");
        while (!vertexQueue.isEmpty()) {
            Vertex v;

            try {
                v = vertexQueue.dequeue();
            } catch (InterruptedException ie) {
                return;
            }

            System.out.print(v.getKey() + " ");
            int index = getVertexIndex(v.getKey());

            for (int i = 0; i < vertList.size(); i++) {
                if (paths[index][i] != 0 && !vertVisitFlags[i]) {
                    vertVisitFlags[i] = true;
                    vertexQueue.enqueue(vertList.get(i));
                }
            }
        }
        System.out.println();
    }


    // Проверка на существование пути между парой вершин
    public boolean path(String beginVertex, String endVertex) {
        Vertex begin = getVertex(beginVertex);
        Vertex end = getVertex(endVertex);

        if (begin != null && end != null) {
            boolean[] booleans = new boolean[vertList.size()];
            recursionDFS(begin, booleans);

            return booleans[getVertexIndex(endVertex)];
        } else return false;
    }

    // Степень вершины
    public int vertexDegree(String vertexKey) {
        int index = getVertexIndex(vertexKey);

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


    // Индекс вершины в массиве
    private int getVertexIndex(String vertexKey) {
        for (int i = 0; i < vertList.size(); i++) {
            if (vertList.get(i).getKey().equals(vertexKey))
                return i;
        }

        return -1;
    }

    // Вершина в массиве
    private Vertex getVertex(String vertexKey) {
        for (int i = 0; i < vertList.size(); i++) {
            Vertex v = vertList.get(i);
            if (v.getKey().equals(vertexKey))
                return v;
        }

        return null;
    }

    private int checkGraph(String key) {
        if (isEmpty()) throw new NullPointerException("Graph is not initialized...");

        int startIndex = getVertexIndex(key);
        if (startIndex == -1)
            throw new NullPointerException("Vertex is not found...");

        return startIndex;
    }
}