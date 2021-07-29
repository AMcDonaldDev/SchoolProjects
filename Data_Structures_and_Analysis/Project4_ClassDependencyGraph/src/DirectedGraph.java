/*
 * File: DirectedGraph.java
 * Author: Allison McDonald
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This class defines the directed graph.
 */

import java.lang.reflect.Array;
import java.util.*;

public class DirectedGraph<V> {
    // Define variables
    private ArrayList<V> vertices;
    private ArrayList<LinkedList<Integer>> adjacencyList;
    private ArrayStack<V> stack;
    private ArrayList<V> discovered;
    private ArrayList<V> finished;
    private Class<V> vType;
    
    // Construct graph
    public DirectedGraph(ArrayList<V[]> data, Class<V> type) {
        // Initialize variables
        vertices = new ArrayList<>();
        adjacencyList = new ArrayList<>();
        this.vType = type;
        
        // Create vertices
        createVertices(data);
        // Add edges
        addEdges(data);
        
        // Initialize array list for dfs
        stack = new ArrayStack<>(vertices.size());
        discovered = new ArrayList<>(vertices.size());
        finished = new ArrayList<>(vertices.size());
        
    }

    // Create array list of vertices
    private void createVertices(ArrayList<V[]> data) {
        // Declare variable
        int index = 0;
        
        for (V[] fileLine : data) {
            for (int i = 0; i < fileLine.length; i++) {
                if (!vertices.contains(fileLine[i])) {
                    vertices.add(fileLine[i]);
                    LinkedList<Integer> neighbor = new LinkedList<>();
                    adjacencyList.add(index, neighbor);
                    index++;
                }
            }
        }
    }
    
    // Add edges to linked list
    private void addEdges(ArrayList<V[]> data) {
        for (V[] fileLine : data) {
            for (int i = 0; i < fileLine.length; i++) {
                if (i != 0) {
                    int parent = vertices.indexOf(fileLine[0]);
                    int child = vertices.indexOf(fileLine[i]);
                    adjacencyList.get(parent).add(child);
                }
            }
        }
    }
    
    // Depth first search
    private void dfs(V className) throws CycleException {
        if (discovered.contains(className)) {
            throw new CycleException();
        }
        if (finished.contains(className)) {
            return;
        }
        discovered.add(className);
        V[] adjacentVertices = getAdjacentVertices(className);
        
        for (V adjacentVertex : adjacentVertices) {
            dfs(adjacentVertex);
        }
        finished.add(className);
        stack.push(className);
    }
    
    // Get adjacent vertices from linked list
    private V[] getAdjacentVertices(V className) {
        int vertexIndex = vertices.indexOf(className);
        LinkedList<Integer> adjacentIndeces = adjacencyList.get(vertexIndex);
        V[] adjacentVertices = (V[]) Array.newInstance(vType, adjacentIndeces.size());
        int index = 0;
        for (int i : adjacentIndeces) {
            adjacentVertices[index] = vertices.get(i);
            index++;
        }
        return adjacentVertices;
    }
    
    // Create topological order
    public String createTopologicalOrder(V className) throws CycleException, InvalidClassNameException {
        if (!vertices.contains(className)) {
            throw new InvalidClassNameException();
        }
        String topologicalOrder = "";
        dfs(className);
        while (!stack.isEmpty()) {
            topologicalOrder += stack.pop() + " ";
        }
        return topologicalOrder;
    }
    
}
