package com.pro464;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
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
import java.util.Objects;


public class GraphMani {
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
            if(Objects.equals(format, "PNG")) {
                ImageIO.write(image, "PNG", imgFile);
                System.out.println("Successfully saved image of graph to " + path);
            }
        } catch (IOException err) {
            err.printStackTrace();
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








    }
}
