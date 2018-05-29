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
//infected rate
//recover rate
//all the nodes name
//all nodes' initial behaviors 
//all the edges 



public class SIR {
	
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
		double infectrate = scanner.nextDouble();			//read infected rate
		double recoverrate = scanner.nextDouble();			//read recover rate
		
		String[] Nodes = new String[NumOfNode];
		for (int i = 0; i< NumOfNode; i++){	//read all the nodes' names in the file
			Nodes[i]=scanner.next();
		}
		
		SIR graph = new SIR();
		for (int i = 0; i < NumOfNode;i++){	//add all the nodes to the graph
			graph.addVertex(Nodes[i]);
		}
		
		boolean[] behavior = new boolean[NumOfNode];	//records the behavior of all nodes 
		
		for (int i = 0; i < NumOfNode;i++){	//read all the behaviors in the file
			behavior[i]=scanner.nextBoolean();
		}
		
		
		
		while (scanner.hasNext()){			//add all the edges to the graph
			graph.addEdge(scanner.next(),scanner.next());
		}
		
		graph.findinfected(infectrate, recoverrate ,behavior,NumOfNode,Nodes);

	}
	
	/**
	 * main method to find whether each node is been infected or recovered
	 * set infected to be true, not infected to be false
	 * any uninfected node has a probably to be infected by its neighbor which is been infected 
	 * every node has a chance to recover
	 * if it is recovered, it will not be infected again 
	 * @param infectrate	
	 * @param recoverrate
	 * @param behavior 	boolean array
	 * @param numOfNode		
	 * @param Nodes		String array (names of all the nodes)		
	 */
	public void findinfected(double infectrate, double recoverrate, boolean[] behavior, int NumOfNode,String[] Nodes) {
		boolean[] behaviorcopy = new boolean[NumOfNode];	//a boolean array to record the nodes' next state's behavior
		for (int i = 0; i < NumOfNode; i++){
			behaviorcopy[i]=behavior[i];
			vertices.get(Nodes[i]).behavior=behavior[i];	
			
		}
		
		
		
		int run=0;
		
		while(run<20){
			
			run++;
			
			for (int i = 0; i < NumOfNode; i++){	//go through all the nodes 
				
				
				Vertex curr = vertices.get(Nodes[i]);	//find the vertex representation of the node
				
				
				
				for (Edge x:adjacent.get(curr.label)){	//go through all its adjacency list

					if (x.source.recovered==false){	//if it is not been recovered
						if (x.source.behavior==false && x.target.behavior==true && behaviorcopy[i]!=true){		//find if it is going to be infected
							Double random = Math.random();

							if(random<=infectrate){
								behaviorcopy[i]=true;
								
								System.out.print(x+ " ");
								System.out.println("infected rate: " + random);
								

									
							}
							
						}
							
						
					}
				}
				
				if(curr.behavior==true){	//if it is infected, find out if it is going to recover
					Double random = Math.random();
					System.out.print(curr+ " ");
					System.out.println("recover random:"+random);
					if(random<=recoverrate){
						System.out.println("yes!");
						behaviorcopy[i]=false;
						curr.recovered = true;
					}
				}
			}
			
			
			for (int z = 0; z <NumOfNode; z++){	//update behavior array
				behavior[z] = behaviorcopy[z];
				vertices.get(Nodes[z]).behavior=behaviorcopy[z];	
			}
			
			for (int n = 0; n < NumOfNode ; n++){
				
				System.out.println(Nodes[n]+" "+ behavior[n]);
				
			}
			System.out.println("running time" +run);
			
		}
	}


	public SIR() {
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
		boolean recovered;
		
		
	
		//create a node with its label
		public Vertex(String label) {
			this.label = label;
			recovered=false;
			
	
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
			return "(" + source + " <- " + target+ ")";
		}
		
	}
}
