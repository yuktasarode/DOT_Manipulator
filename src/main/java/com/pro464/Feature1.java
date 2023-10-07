package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.StringReader;


public class Feature1 {
    private Graph<String, DefaultEdge> g;

    void parseGraph(String filePath) {
        String graphC = null;
        try {
            graphC = Files.readString(Paths.get(filePath));
        } catch (IOException err) {
            err.printStackTrace();
        }

        g = new SimpleDirectedGraph<>(DefaultEdge.class);

        DOTImporter<String, DefaultEdge> dotImp = new DOTImporter<>();
        dotImp.setVertexFactory(label -> label);
        dotImp.importGraph(g, new StringReader(graphC));



    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Total number of nodes: ").append(g.vertexSet().size()).append("\n");
        str.append("Total number of edges: ").append(g.edgeSet().size()).append("\n");
        str.append("Node Labels: ").append(g.vertexSet()).append("\n");
        str.append("Edges: ");
        for (DefaultEdge e : g.edgeSet()) {
            String src = g.getEdgeSource(e);
            String trg = g.getEdgeTarget(e);
            str.append(src).append(" -> ").append(trg).append(", ");
        }
        String res = str.toString();
        res=res.substring(0, res.length() - 2);
        return res;
    }

    void outputGraph(String filePath){


        File outFile = new File(filePath);
        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write(toString());
        } catch (IOException err) {
            err.printStackTrace();
        }

    }


    public static void main(String[] args){
        Feature1 f = new Feature1();
        f.parseGraph("src/main/sample.DOT");
        System.out.println(f.toString());
        f.outputGraph("src/main/outputGraph.txt");

    }
}
