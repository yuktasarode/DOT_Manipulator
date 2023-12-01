package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import java.util.*;

public class DFSAlgo extends GraphSearchTemplate implements Algo {
    public DFSAlgo(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    public GraphMani.Path execute(Graph<String,DefaultEdge> graph, String src, String dst) {


        Stack<String> stack = new Stack<>();
        stack.push(src);

        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        String target = "";

        while (!stack.isEmpty()) {
            String currNode = stack.pop();
            visited.add(currNode);

            if (currNode.equals(dst)) {
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
        return processResult(src, target, parent, path);
    }
}