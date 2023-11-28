package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import java.util.*;

public class DFSAlgo implements Algo {
    @Override
    public GraphMani.Path execute(Graph<String, DefaultEdge> graph, String src, String dst) {


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
        if (target.isEmpty()) {
            return path;
        } else {
            Stack<String> pstack = new Stack<>();
            String u = target;
            while (true) {
                pstack.push(u);
                u = parent.get(u);
                if (u.equals(src)) {
                    break;
                }
            }
            path.addNode(src);
            while (!pstack.isEmpty()) {
                String node = pstack.pop();
                path.addNode(node);
            }
            return path;


        }
    }
}