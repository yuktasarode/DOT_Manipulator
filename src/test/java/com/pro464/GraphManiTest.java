package com.pro464;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class GraphManiTest {
    GraphMani gm;
    @BeforeEach
    void initialize(){
        gm = new GraphMani();
        gm.parseGraph("src/test/java/test.DOT");
    }


    @AfterEach
    void deletefiles(){

        File file1 = new File("src/test/java/newGraph1.png");
        if (file1.exists()) {
            file1.delete();
        }

        File file2 = new File("src/test/java/outputGraph1.DOT");
        if (file2.exists()) {
            file2.delete();
        }

        File file3 = new File("src/test/java/outputGraph1.txt");
        if (file3.exists()) {
            file3.delete();
        }

        File file4 = new File("src/test/java/outputGraphTest.txt");
        if (file4.exists()) {
            file4.delete();
        }

        File file5 = new File("src/main/outputGraphTest.txt");
        if (file5.exists()) {
            file5.delete();
        }


    }
    @Test
    void testparseGraph() {
        assertTrue(gm.getGraph().containsVertex("a"));
        assertTrue(gm.getGraph().containsVertex("b"));
        assertTrue(gm.getGraph().containsVertex("c"));
        assertTrue(gm.getGraph().containsEdge("a", "b"));
        assertTrue(gm.getGraph().containsEdge("b", "c"));
    }

    @Test
    void testAddNode() {
        gm.addNode("a");
        assertTrue(gm.getGraph().containsVertex("a"));
    }

    @Test
    void testAddNodes() {
        String[] labels = {"a", "d"};
        gm.addNodes(labels);
        assertTrue(gm.getGraph().containsVertex("a"));
        assertTrue(gm.getGraph().containsVertex("d"));
    }

    @Test
    void testAddEdge() {
        gm.addNode("a");
        gm.addNode("d");
        gm.addEdge("a", "d");
        assertTrue(gm.getGraph().containsEdge("a", "d"));
    }

    @Test
    void testoutputGraphics(){
        String path = "src/test/java/newGraph1.png";
        String format = "PNG";

        gm.outputGraphics(path, format);


        File imgFile = new File(path);
        assertTrue(imgFile.exists());


    }

    @Test
    public void testOutputGraphicsOther() {

        String path = "src/test/main/newGraph.jpg";
        String format = "JPEG";


        gm.outputGraphics(path, format);


        File imgFile = new File(path);
        assertFalse(imgFile.exists());
    }

    @Test
    void testoutputDOTGraph(){

        String outputFilePath = "src/test/java/outputGraph1.DOT";
        String expectedFilePath = "src/test/java/expected.DOT";

        gm.outputDOTGraph(outputFilePath);


        String expectedContent = readFile(expectedFilePath);
        String outputContent = readFile(outputFilePath);


        assertEquals(expectedContent, outputContent);

    }


    private String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException err) {
            err.printStackTrace();
            return null;
        }
    }
    @Test
    void testoutputGraph(){
        String testFilePath = "src/test/java/outputGraphTest.txt";

        gm.outputGraph(testFilePath);

        File outFile = new File(testFilePath);
        assertTrue(outFile.exists());

        String expectedContent = gm.toString();
        String fileContent = readFile(testFilePath);
        assertNotNull(fileContent);
        assertEquals(expectedContent, fileContent);


    }

    private void writeFile(String content, String filePath) {
        try {
            Files.write(Paths.get(filePath), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testtoString(){

        String outputFilePath = "src/test/java/outputGraph1.txt";
        String expectedFilePath = "src/test/java/expectedtxt.txt";

        String output = gm.toString();

        writeFile(output, outputFilePath);


        String expectedContent = readFile(expectedFilePath);


        assertEquals(expectedContent, output);


    }

    @Test
    public void testRemoveNode() throws Exception {
        gm.removeNode("a");
        System.out.println(gm.toString());

//        checking number of nodes
        assertEquals(2,  gm.getGraph().vertexSet().size());
        assertFalse(gm.getGraph().containsVertex("a"));
        assertThrows(RuntimeException.class, () -> gm.removeNode("a"));

    }

    @Test
    public void testRemoveNodes() throws Exception {
        gm.addNode("e");
        gm.addEdge("e","a");
        String[] labels = {"a","e","f"};
        assertThrows(RuntimeException.class, () -> gm.removeNodes(labels));
        assertFalse(gm.getGraph().containsVertex("a"));
        assertFalse(gm.getGraph().containsVertex("e"));

//        counting nodes
        assertEquals(2, gm.getGraph().vertexSet().size());

//        counting edges
        assertEquals(1,gm.getGraph().edgeSet().size());


        assertFalse(gm.getGraph().containsEdge("e","a"));
    }

    @Test
    public void testRemoveEdge() throws Exception {

        gm.removeEdge("b", "c");
        System.out.println(gm.toString());

        assertEquals(3,gm.getGraph().vertexSet().size());
        assertEquals(1, gm.getGraph().edgeSet().size());
        assertTrue(gm.getGraph().containsEdge("a", "b"));
        assertFalse(gm.getGraph().containsEdge("b", "c"));

        assertThrows(RuntimeException.class, () -> gm.removeEdge("b", "c"));

    }

    @Test
    public void testBFS(){
        GraphMani g = new GraphMani();
        g.parseGraph("src/test/java/test2.DOT");
        GraphMani.Path res=g.GraphSearch("a","e",GraphMani.algo.BFS);
        String r = res.toString();
        assertEquals("a -> b -> e", r);

        g.addNode("f");
        res=g.GraphSearch("a","f", GraphMani.algo.BFS);

        assertEquals(0, res.toString().length());


    }

    @Test
    public void testDFS(){
        GraphMani g = new GraphMani();
        g.parseGraph("src/test/java/test2.DOT");
        GraphMani.Path res=g.GraphSearch("a","e", GraphMani.algo.DFS);
        String r = res.toString();
        assertEquals("a -> b -> c -> e", r);

        g.addNode("f");
        res=g.GraphSearch("a","f", GraphMani.algo.DFS);

        assertEquals(0, res.toString().length());


    }

    @Test
    public void testRWS(){
        GraphMani g = new GraphMani();
        g.parseGraph("src/test/java/test2.DOT");
        GraphMani.Path res=g.GraphSearch("a","c", GraphMani.algo.RWS);
        assertNotNull(res);


        assertEquals("a -> b -> c", res.toString());





    }


}
