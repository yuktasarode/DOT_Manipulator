package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import java.util.*;
public class RWSAlgo implements Algo {
    @Override
    public GraphMani.Path execute(Graph<String, DefaultEdge> graph, String src, String dst) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add(src);
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        String target = "";

        Random random = new Random();

        while (!queue.isEmpty()) {
            String currNode = queue.remove();
            visited.add(currNode);
            System.out.print(currNode+" ");

            List<String> successors = Graphs.successorListOf(graph, currNode);
            System.out.println(successors);
            if (successors == null) {
                continue; // Skip if successors are null
            }

            List<String> shuffledSuccessors = new ArrayList<>(successors);
            Collections.shuffle(shuffledSuccessors, random); // Shuffle the successors randomly

            for (String v : shuffledSuccessors) {
                parent.put(v, currNode);

                if (!visited.contains(v)) {
                    if (v.equals(dst)) {
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
        if (target.isEmpty()) {
            return path;
        } else {
            Stack<String> stack = new Stack<>();
            String u = target;
            while (true) {
                stack.push(u);
                u = parent.get(u);
                if (u.equals(src)) {
                    break;
                }
            }
            path.addNode(src);
            while (!stack.isEmpty()) {
                String node = stack.pop();
                path.addNode(node);
            }
            return path;
        }
    }
    }
