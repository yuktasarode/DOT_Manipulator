# CSE 464 2023 - Software Quality Assurance and Testing.

## Project Part 1 

### How to run the project:

1) Download the ```TestGraph.zip``` file from this repository.
2) Import the zip into a Java IDE - IntelliJ IDE.
3) From the menu bar you can click 'Run'-> 'Run 'GraphMani'.
4) You can also run the whole project with 'mvn package'. Since there is support for the surefire plugin this command runs the tests as well. 
5) To run all the tests separately you can 'Run GraphManiTest'.


### List of APIs:

#### Feature 1 - 
1) ```void parseGraph(String filePath)``` - Imports a directed graph from a DOT file.
2) ```public String toString()``` - Gets the information about the graph like the number of nodes & edges and converts the data into string output.
3) ``` void outputGraph(String filePath)``` - Puts the information about the graph into a text file format.

#### Feature 2 - 
1) ```public void addNode(String label)``` - Adds a new node with the given label into the graph if not already present.
2) ```public void addNodes(String[] label)``` - Adds an array of nodes into the graph if not present already.

#### Feature 3 - 
1) ```public void addEdge(String srcLabel,String dstLabel)``` - Adds a new edge with the given source and destination nodes if not already present.

#### Feature 4 - 
1) ```public void outputDOTGraph(String path)``` - Outputs graph in a dot graph format in the given path.
2) ```public void outputGraphics(String path, String format)``` - Outputs graph in png format in the given path with graph visualization.

#### Helper Functions - 
1) ```public Graph<String, DefaultEdge> getGraph()``` - Returns the current graph object.


### Example Code - 
1) Create a new graph object:

```java
GraphMani f = new GraphMani();
```

2) To parse the graph and print it:

```java
f.parseGraph("src/main/sample.DOT");
System.out.println(f.toString());
f.outputGraph("src/main/outputGraph.txt");
```

3) To add node / nodes :

```java
f.addNode("f");
String[] add_Nodes = {"e","g","a","h"};
f.addNodes(add_Nodes);
```

4) To add edge:

```java
f.addEdge("e","f");
```

5) To output the graph in DOT format:

```java
f.outputDOTGraph("src/main/outGraph.DOT");
```

7) To output the graph in PNG format:

```java
f.outputGraphics("src/main/newGraph.png","PNG");
```
##  Project Part 2



8) Remove node:
```java
f.removeNode("e");
```

10) Remove nodes:
```java
String[] nodesToRemove = {"h","g"};
f.removeNodes(nodesToRemove);
```

To test removal of edges. I am adding few edges to the graph.
```java

        f.addEdge("e","g");
        f.addEdge("d","e");
```

12) Remove edge:
```java
 f.removeEdge("a","b");
``` 

I have made a new DOT file (sample2.DOT).

```java
GraphMani new_f = new GraphMani();
new_f.parseGraph("src/main/sample2.DOT");
```

8) For BFS Traversal:

```java
Path result = new_f.GraphSearch("a","e", algo.BFS);
System.out.println(result.toString());

``` 

9) For DFS Traversal:

```java
Path result2 = new_f.GraphSearch("a","e", algo.DFS);
System.out.println(result2.toString());
```
  



