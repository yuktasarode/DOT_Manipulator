package com.pro464;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class context {
    //strategy
    Algo algointerface ;

    public context(Algo algointerface){
        this.algointerface = algointerface;
    }
    public GraphMani.Path execute(Graph<String, DefaultEdge> g, String src, String dst) {
        return this.algointerface.execute( g, src, dst);
    }

}
