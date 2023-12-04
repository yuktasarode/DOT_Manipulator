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

6) To output the graph in PNG format:

```java
f.outputGraphics("src/main/newGraph.png","PNG");
```
##  Project Part 2



7) Remove node:
```java
f.removeNode("e");
```

8) Remove nodes:
```java
String[] nodesToRemove = {"h","g"};
f.removeNodes(nodesToRemove);
```

To test removal of edges. I am adding few edges to the graph.
```java

f.addEdge("e","g");
f.addEdge("d","e");
```

9) Remove edge:
```java
 f.removeEdge("a","b");
``` 

I have made a new DOT file (sample2.DOT).

```java
GraphMani new_f = new GraphMani();
new_f.parseGraph("src/main/sample2.DOT");
```

10) For BFS Traversal:

```java
Path result = new_f.GraphSearch("a","e", algo.BFS);
System.out.println(result.toString());

``` 

11) For DFS Traversal:

```java
Path result2 = new_f.GraphSearch("a","e", algo.DFS);
System.out.println(result2.toString());
```
  

##  Project Part 3

12) The 6 refactors were:
    
   -Magic strings like "PNG" is replaced with constants to improve code maintainability
    
   -Made variable declaration location consistent and clear at the top of the code
   
   -Separate the graph parsing logic from the graph file reading in the parseGraph method. This allows for better error handling and more meaningful error messages.
   
   -Simplified the construction of the edges section in the toString method using the forEach loop for clarity and simplicity.
   
   -Improved the logic of toString() in Path class by making it concise and improving readability.
   
   -Refactored the Path class to use the List interface and used contains in containsNode function, made it concise and simplified it.

14) For Template Pattern:
```java
GraphMani new_f = new GraphMani();
new_f.parseGraph("src/main/sample2.DOT");

Path result = new_f.GraphSearch("a","e", algo.BFS);
System.out.println("BFS: "+ result.toString());

Path result2 = new_f.GraphSearch("a","e", algo.DFS);
System.out.println("DFS: " +result2.toString());
```


14) For Strategy Pattern:
```java
GraphMani BFS_Sp = new GraphMani();
BFS_Sp.parseGraph("src/main/sample2.DOT");
Path resBFS_Sp = BFS_Sp.GraphSearch("a", "e", algo.BFS);
System.out.println("BFS: "+ resBFS_Sp.toString());

GraphMani DFS_Sp = new GraphMani();
DFS_Sp.parseGraph("src/main/sample2.DOT");
Path resDFS_Sp = DFS_Sp.GraphSearch("a", "e", algo.DFS);
System.out.println("DFS: "+ resDFS_Sp.toString());
```


15) For Random Walk:
```java
GraphMani RWS_Sp = new GraphMani();
RWS_Sp.parseGraph("src/main/input2.dot");
System.out.println("Random Walk: ");
Path resRWS_Sp = RWS_Sp.GraphSearch("a", "c", algo.RWS);
System.out.println();
System.out.println("RWS: "+ resRWS_Sp.toString());
```

