package com.pro464;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.*;

public class GraphMani {

    enum algo {
        BFS,
        DFS,
        RWS
    }

    private static final String PNG_IMG_FORMAT = "PNG";

    private Graph<String, DefaultEdge> g;


    public static class Path {
        private List<String> nodes;
        public boolean isEmpty() {
            return nodes.isEmpty();
        }

        Path() {
            nodes = new ArrayList<>();
        }

        public void addNode(String node) {
            nodes.add(node);
        }

        public boolean containsNode(String searchNode) {
            return nodes.contains(searchNode);
        }

        @Override
        public String toString() {
            return String.join(" -> ", nodes);
        }
    }

    Path GraphSearch( String start, String end, algo a){

        Algo algointerface = null;
        context c = null;
        switch (a){
            case BFS:
                algointerface = new BFSAlgo();
                break;

            case DFS:
                algointerface = new DFSAlgo();
                break;

            case RWS:
                algointerface = new RWSAlgo();
                break;
        }
        c = new context(algointerface);

        return c.execute(g, start, end);

    }

    Path GraphSearchDFS(String start, String end) {
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

            for (String v : Graphs.neighborListOf(g, currNode)) {
                if (!visited.contains(v)) {
                    parent.put(v, currNode);
                    stack.push(v);
                }
            }
        }
        Path path = new Path();
        if(target.isEmpty()) {
            return null;
        } else {
            Stack<String> pstack = new Stack<>();
            String u = target;
            while(true) {
                pstack.push(u);
                u = parent.get(u);
                if (u.equals(start)) {
                    break;
                }
            }
            path.addNode(start);
            while(!pstack.isEmpty()) {
                String node = pstack.pop();
                path.addNode(node);
            }
            return path;
        }
    }


    Path GraphSearchBFS(String start, String end) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        String target = "";
        while(!queue.isEmpty()) {
            String currNode = queue.remove();
            visited.add(currNode);
            for (String v : Graphs.neighborListOf(g,currNode)) {
                parent.put(v,currNode);
                if(!visited.contains(v)) {
                    if(v.equals(end)) {
                        target=v;
                        break;
                    }
                    queue.add(v);
                }
                if(v.equals(target)) {
                    break;
                }
            }
        }
        Path path = new Path();
        if(target.isEmpty()) {
            return null;
        } else {
            Stack<String> stack = new Stack<>();
            String u = target;
            while(true) {
                stack.push(u);
                u = parent.get(u);
                if (u.equals(start)) {
                    break;
                }
            }
            path.addNode(start);
            while(!stack.isEmpty()) {
                String node = stack.pop();
                path.addNode(node);
            }
            return path;
        }
    }




    void parseGraph(String filePath) {

        try {
            String graphC = Files.readString(Paths.get(filePath));
            parseGraphHelper(graphC);
        } catch (IOException err) {
            System.err.println("Error reading this file: "+err.getMessage());
        }
    }

    private void parseGraphHelper(String graphC){
        g = new SimpleDirectedGraph<>(DefaultEdge.class);

        DOTImporter<String, DefaultEdge> dotImp = new DOTImporter<>();
        dotImp.setVertexFactory(label -> label);

        try{
            dotImp.importGraph(g, new StringReader(graphC));

        }
        catch(Exception err){
            System.err.println("Error parsing the content of the graph: "+err.getMessage());
        }

    }

    public Graph<String, DefaultEdge> getGraph() {
        return g;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Total number of nodes: ").append(g.vertexSet().size()).append("\n");
        str.append("Total number of edges: ").append(g.edgeSet().size()).append("\n");
        str.append("Node Labels: ").append(g.vertexSet()).append("\n");
        str.append("Edges: ");

        g.edgeSet().forEach(e -> {
            String src = g.getEdgeSource(e);
            String trg = g.getEdgeTarget(e);
            str.append(src).append(" -> ").append(trg).append(", ");
        });

        if (str.length() > 2) {
            str.setLength(str.length() - 2);
        }

        return str.toString();
    }

    void outputGraph(String filePath){


        File outFile = new File(filePath);
        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write(toString());
        } catch (IOException err) {
            err.printStackTrace();
        }

    }

    public void addNode(String label){
        if (g.containsVertex(label) )
        {
            System.out.println("The node " + label + " is already present in the graph.");
        }
        else
        {
            g.addVertex(label);
            System.out.println("The node " + label + " has been added to the graph");
        }

    }

    public void addNodes(String[] label){
        for (String gn : label) {
            addNode(gn);
        }

    }

    public void addEdge(String srcLabel,String dstLabel){
        if (g.containsEdge(srcLabel, dstLabel)) {
            System.out.println("Cannot add edge from " + srcLabel + " to " + dstLabel + ". Already present.");
        } else {
            g.addEdge(srcLabel, dstLabel);
            System.out.println("Edge has been added from " + srcLabel + " to " + dstLabel);
        }

    }

    public void outputDOTGraph(String path){
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        StringWriter writer = new StringWriter();
        exporter.setVertexIdProvider(v -> v);
        exporter.exportGraph(g, writer);
        String graphString = writer.toString();
        try {
            Files.write(Paths.get(path), graphString.getBytes());
            System.out.print("Exported graph to DOT format successfully.\n");
        } catch (IOException err) {
            err.printStackTrace();
        }

    }

    public void outputGraphics(String path, String format){

        JGraphXAdapter<String, DefaultEdge> gAdpt = new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(gAdpt);
        layout.execute(gAdpt.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(gAdpt, null, 2, Color.WHITE, true, null);
        File imgFile = new File(path);
        try {
            if(Objects.equals(format, PNG_IMG_FORMAT)) {
                ImageIO.write(image, PNG_IMG_FORMAT, imgFile);
                System.out.println("Successfully saved image of graph to " + path);
            }
        } catch (IOException err) {
            err.printStackTrace();
        }

    }


    public void removeNode(String label) {
        if (g.containsVertex(label)) {
            g.removeVertex(label);
            System.out.println("Node " + label + " is removed successfully");
        } else {
            throw new RuntimeException("Node " + label + " is not present in the graph");
        }
    }
    public void removeNodes(String[] label) {
        for (String l : label) {
            removeNode(l);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        if (g.containsEdge(srcLabel, dstLabel)) {
            g.removeEdge(srcLabel, dstLabel);
            System.out.println("Edge is successfully removed between " + srcLabel + " to " + dstLabel);
        } else {
            throw new RuntimeException("Edge does not exist between " + srcLabel + " to " + dstLabel);
        }
    }


    public static void main(String[] args){
        GraphMani f = new GraphMani();
        f.parseGraph("src/main/sample.DOT");
        System.out.println(f.toString());

        f.outputGraph("src/main/outputGraph.txt");

        f.addNode("f");
        String[] add_Nodes = {"e","g","a","h"};
        f.addNodes(add_Nodes);

        f.addEdge("e","f");
        System.out.println(f.toString());
        f.addEdge("a","b");

        f.outputDOTGraph("src/main/outGraph.DOT");

        f.outputGraphics("src/main/newGraph.png","PNG");

        System.out.println("=================================Part 2===============================");
        //Project part 2

        f.addEdge("e","g");
        f.addEdge("d","e");

        f.removeNode("e");
        System.out.println(f.toString());


        String[] nodesToRemove = {"h","g"};
        f.removeNodes(nodesToRemove);
        System.out.println(f.toString());

        f.removeEdge("a","b");
        System.out.println(f.toString());


        GraphMani new_f = new GraphMani();
        new_f.parseGraph("src/main/sample2.DOT");

        Path result = new_f.GraphSearch("a","e", algo.BFS);
        System.out.println("BFS: "+ result.toString());

        Path result2 = new_f.GraphSearch("a","e", algo.DFS);
        System.out.println("DFS: " +result2.toString());

        System.out.println("=================================Part 3===============================");

        GraphMani BFS_Sp = new GraphMani();
        BFS_Sp.parseGraph("src/main/sample2.DOT");

        Path resBFS_Sp = BFS_Sp.GraphSearch("a", "e", algo.BFS);
        System.out.println("BFS: "+ resBFS_Sp.toString());

        GraphMani DFS_Sp = new GraphMani();
        DFS_Sp.parseGraph("src/main/sample2.DOT");


        Path resDFS_Sp = BFS_Sp.GraphSearch("a", "e", algo.DFS);
        System.out.println("DFS: "+ resDFS_Sp.toString());


        GraphMani RWS_Sp = new GraphMani();
        RWS_Sp.parseGraph("src/main/input2.dot");
        System.out.println(RWS_Sp);


        Path resRWS_Sp = RWS_Sp.GraphSearch("a", "c", algo.RWS);
        System.out.println("RWS: "+ resRWS_Sp.toString());



    }
}
