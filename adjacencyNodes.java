package research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

//the file contains:
//number of node
//threshold value p
//all the nodes name
//all nodes' initial behaviors 
//all the edges 



public class adjacencyNodes {
	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;

	public static void main(String[] args) throws FileNotFoundException{
		InputStream d = null;
		File file = new File("C:/Users/Shaocheng Yang/Desktop/research/hw2.txt");	//address may be changed
				
		try{
			d = new FileInputStream(file);
		}
		catch(FileNotFoundException a){
			System.out.println("FileNotFound");
			System.exit(0);
		}
		
		Scanner scanner = new Scanner(file);
		int NumOfNode = scanner.nextInt();	//number of nodes in the graph
		double p = scanner.nextDouble();			//read p value(threshold value)
		
		String[] Nodes = new String[NumOfNode];
		for (int i = 0; i< NumOfNode; i++){	//read all the nodes' names in the file
			Nodes[i]=scanner.next();
		}
		
		adjacencyNodes adj = new adjacencyNodes();
		for (int i = 0; i < NumOfNode;i++){	//add all the nodes to the graph
			adj.addVertex(Nodes[i]);
		}
		
		boolean[] behavior = new boolean[NumOfNode];	//records the behavior of all nodes 
		
		for (int i = 0; i < NumOfNode;i++){	//read all the behaviors in the file
			behavior[i]=scanner.nextBoolean();
		}
		
		
		
		while (scanner.hasNext()){			//add all the edges to the graph
			adj.addEdge(scanner.next(),scanner.next());
		}
		
		adj.GetFinalBehavior(p,behavior,NumOfNode,Nodes);

	}
	
	/**
	 * main method to find the behavior chosen by each nodes
	 * set behavior A to be true
	 * If the number of a node's neighbors choosing A behavior divided by the total number of all 
	 * its neighbors is bigger than or equal to the threshold value,
	 * then the node would choose A behavior.
	 * If the number of a node's neighbors choosing B behavior divided by the total number of all 
	 * its neighbors is bigger than (1- threshold value),
	 * then the node would choose B behavior.
	 * @param p	threshold value
	 * @param behavior 	boolean array
	 * @param numOfNode		
	 * @param Nodes		String array (names of all the nodes)		
	 */
	public void GetFinalBehavior(double p, boolean[] behavior, int NumOfNode,String[] Nodes) {
		boolean[] behaviorcopy = new boolean[NumOfNode];	//a boolean array to record the nodes' next state's behavior
		for (int i = 0; i < NumOfNode; i++){
			behaviorcopy[i]=behavior[i];
			vertices.get(Nodes[i]).behavior=behavior[i];	
		}
		
		
		boolean haschanged = true;			//index when it reaches the final result
		int run=0;
		
		while(haschanged&&run<100){
			haschanged = false;
			run++;
			
			for (int i = 0; i < NumOfNode; i++){	//go through all the nodes 
				
				double numA=0;							//number of A neighbor
				double numB=0;
				Vertex curr = vertices.get(Nodes[i]);	//find the vertex representation of the node
				for (Edge x:adjacent.get(curr.label)){	//go through all its adjacency list
					if (x.target.behavior==true){		//count how many A neighbors and B neighbors 
						numA++;
					}
					else{
						numB++;
					}
					
				}
				
				if (vertices.get(Nodes[i]).behavior==true){	//use the threshold value to determine whether the node 
															//is going to change its behavior
					if (numA/(numB+numA)<p){
						behaviorcopy[i]=false;
						haschanged = true;
					}
					else{
						behaviorcopy[i]=behavior[i];
					}
					
				}
				if (vertices.get(Nodes[i]).behavior==false){
					if (numA/(numB+numA)>=p){
						behaviorcopy[i]=true;
						haschanged = true;
					}
					else{
						behaviorcopy[i]=behavior[i];
					}
				}
				System.out.println("A " +numA+" B "+numB);
			}
			
			
			for (int z = 0; z <NumOfNode; z++){	//update behavior array
				behavior[z] = behaviorcopy[z];
				vertices.get(Nodes[z]).behavior=behaviorcopy[z];	
			}
			
			for (int i = 0; i < NumOfNode ; i++){
				
				System.out.println(Nodes[i]+" "+ behavior[i]);
				
			}
			System.out.println("running time" +run);
		}
	}


	public adjacencyNodes() {
		//create a graph with a treemap of all the vertices, a hashmap for all the adjacent node
		vertices = new TreeMap<String, Vertex>();
		adjacent = new HashMap<String, ArrayList<Edge>>();
		
	}
	
	public void addVertex(String key) {
		//add a new vertex to the graph
		Vertex v = new Vertex(key);
		vertices.put(key, v);
		adjacent.put(key, new ArrayList<Edge>());
	}
	
	public void addEdge(String source, String target) {
		//add a edge between two vertices

		//get source's adjacent list
		ArrayList<Edge> edges = adjacent.get(source);
		Edge e = new Edge(vertices.get(source), vertices.get(target));
		edges.add(e);
		//add edge to source's adjacent list
	}
	




	//create the vertex class
	//each vertex represents a node 
	
	private class Vertex {
		
		String label;
		boolean behavior;
		Vertex parent;
		
	
		//create a node with its label
		public Vertex(String label) {
			this.label = label;
			
			parent = null;
	
		}
		
		
		public String toString() {
			return label;
		}
	
	}
	
	private class Edge {
		//two nodes source and target 
		Vertex source;
		
		Vertex target;
		
		
		//method Edge creates the edge between two nodes
		public Edge(Vertex source, Vertex target) {
			this.source = source;
			this.target = target;
		}
		
		//toString method 
		public String toString() {
			return "(" + source + " -> " + target+ ")";
		}
		
	}
}
