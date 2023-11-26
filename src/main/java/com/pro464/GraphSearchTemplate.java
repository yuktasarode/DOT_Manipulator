package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

abstract class GraphSearchTemplate {

    protected Graph<String, DefaultEdge> graph;
    public GraphSearchTemplate(Graph<String,DefaultEdge> graph) {
        this.graph = graph;
    }
    protected abstract GraphMani.Path searchAlgorithm(String start, String end);

    public GraphMani.Path graphSearch(String start, String end) {
        GraphMani.Path res = new GraphMani.Path();
        res = searchAlgorithm(start, end);
        // Common steps for processing the result can go here
        return res;
    }

    protected GraphMani.Path processResult(String start, String target, Map<String, String> parent, GraphMani.Path path) {
        if (target.isEmpty()) {
            // Handle case where target is not found
            // You might want to throw an exception or handle it based on your requirements
        } else {
            Stack<String> stack = new Stack<>();
            String u = target;
            while (true) {
                stack.push(u);
                u = parent.get(u);
                if (u.equals(start)) {
                    break;
                }
            }
            path.addNode(start);
            while (!stack.isEmpty()) {
                String node = stack.pop();
                path.addNode(node);
            }
        }
        if (path.isEmpty()) {
            return null;
        }
        return path;
    }
}

class BFSGraphSearch extends GraphSearchTemplate {
    public BFSGraphSearch(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected GraphMani.Path searchAlgorithm(String start, String end) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        String target = "";

        while (!queue.isEmpty()) {
            String currNode = queue.remove();
            visited.add(currNode);

            for (String v : Graphs.neighborListOf(graph, currNode)) {
                parent.put(v, currNode);
                if (!visited.contains(v)) {
                    if (v.equals(end)) {
                        target = v;
                        break;
                    }
                    queue.add(v);
                }
                if (v.equals(target)) {
                    break;
                }
            }
        }

        GraphMani.Path path = new GraphMani.Path();
        return processResult(start, target, parent, path);
    }
}

class DFSGraphSearch extends GraphSearchTemplate {
    public DFSGraphSearch(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected GraphMani.Path searchAlgorithm(String start, String end) {
        Stack<String> stack = new Stack<>();
        stack.push(start);
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        String target = "";

        while (!stack.isEmpty()) {
            String currNode = stack.pop();
            visited.add(currNode);

            if (currNode.equals(end)) {
                target = currNode;
                break;
            }

            for (String v : Graphs.neighborListOf(graph, currNode)) {
                if (!visited.contains(v)) {
                    parent.put(v, currNode);
                    stack.push(v);
                }
            }
        }

        GraphMani.Path path = new GraphMani.Path();
        return processResult(start, target, parent, path);
    }
}
