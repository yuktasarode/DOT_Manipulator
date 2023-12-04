package com.pro464;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
public interface Algo {
   GraphMani.Path execute( Graph<String,DefaultEdge> graph, String src, String dst);
}
