//Project 4 Class Dependency Directed Graph

import java.io.*;
import java.util.*;

class DirectedGraph<T> {

    private Map<T, Integer> mapToInteger;
    private ArrayList<LinkedList<Integer>> edgeList;
    private int vertices = 0;
    private List<Integer> neighbors;
    private StringBuilder output;
    private Stack<String> classNameStack = new Stack<>();


    DirectedGraph() {
        edgeList = new ArrayList<>();
        mapToInteger = new HashMap<>();
    }

    //Creates vertices and edges from tokens
    void buildDGraphFromFile(ArrayList<T[]> tokensArray) {
        for (T[] tokensLine : tokensArray) {
            for (int i = 0; i < tokensLine.length; i++) {
                addVertex(tokensLine[i]);
                if (i != 0) {
                    addEdge(tokensLine[0], tokensLine[i]);
                }
            }
        }
    }


    //Opens txt file, splits contents into ArrayList of tokens
    ArrayList<String[]> tokenizeFile(String fileName) throws IOException {
        String filePath = new File(fileName).getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<String[]> fileArray = new ArrayList<>();
        String line;
        int index = 0;

        while ((line = reader.readLine()) != null) {
            String[] lineArray = line.split("\\s");
            fileArray.add(index,lineArray);
            index++;
        }
        reader.close();
        return fileArray;
    }

    //Adds a vertex to mapToInteger
    private void addVertex(T className) {
        if (!mapToInteger.containsKey(className)) {
            mapToInteger.put(className, vertices);
            LinkedList<Integer> adjacent = new LinkedList<>();
            edgeList.add(vertices, adjacent);
            vertices++;
        }
    }

    //Adds an edge that connects two vertices
    private void addEdge(T vertexBegin, T vertexEnd) {
        int begin = mapToInteger.get(vertexBegin);
        int end = mapToInteger.get(vertexEnd);
        edgeList.get(begin).add(end);
    }

    //When Topological Order button is clicked by user,
    //generate a string to output from classNameStack
    String topOrdGeneration(T startVertex) throws InvalidClassNameException, CycleException {
        if (mapToInteger.get(startVertex) == null) {
            throw new InvalidClassNameException();
        }

        output = new StringBuilder();
        neighbors = new ArrayList<>();
        dfs(mapToInteger.get(startVertex));

        while(!classNameStack.empty()) {
            output.append(classNameStack.pop());
            output.append(" ");
        }
        return output.toString();
    }


    //Returns className(key) from mapToInteger HashMap
    private String getNameFromIndex(int vertex) {
        for (T k : mapToInteger.keySet()) {
            if (mapToInteger.get(k).equals(vertex)) {
                return k.toString();
            }
        }
        return "";
    }

    //Depth-first search algorithm
    private void dfs(int vertex) throws CycleException {
        
        for (Integer x : edgeList.get(vertex)) {
            if (neighbors.contains(x)) {
                throw new CycleException();
            }
            neighbors.add(x);
            dfs(x);
        }
        classNameStack.push(getNameFromIndex(vertex));
    }
}