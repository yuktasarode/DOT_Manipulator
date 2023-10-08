package com.pro464;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class GraphManiTest {
    GraphMani gm;
    @BeforeEach
    void initialize(){
        gm = new GraphMani();
        gm.parseGraph("src/test/java/test.DOT");
    }

    @Test
    void testparseGraph() {
        assertTrue(gm.getGraph().containsVertex("A"));
        assertTrue(gm.getGraph().containsVertex("B"));
        assertTrue(gm.getGraph().containsVertex("C"));
        assertTrue(gm.getGraph().containsEdge("A", "B"));
        assertTrue(gm.getGraph().containsEdge("B", "C"));
    }

    @Test
    void testAddNode() {
        gm.addNode("A");
        assertTrue(gm.getGraph().containsVertex("A"));
    }

    @Test
    void testAddNodes() {
        String[] labels = {"A", "D"};
        gm.addNodes(labels);
        assertTrue(gm.getGraph().containsVertex("A"));
        assertTrue(gm.getGraph().containsVertex("D"));
    }

    @Test
    void testAddEdge() {
        gm.addNode("A");
        gm.addNode("D");
        gm.addEdge("A", "D");
        assertTrue(gm.getGraph().containsEdge("A", "D"));
    }

    @Test
    void testoutputGraphics(){

    }

    @Test
    void testoutputDOTGraph(){

    }

    @Test
    void testoutputGraph(){

    }

    @Test
    void testtoString(){

    }

}
