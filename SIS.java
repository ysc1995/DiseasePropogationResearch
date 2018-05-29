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
//infected time 
//all the nodes name
//all nodes' initial behaviors 
//all the edges 



public class SIS {
	
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
		int infectedtime = scanner.nextInt();			//read infected time
		
		String[] Nodes = new String[NumOfNode];
		for (int i = 0; i< NumOfNode; i++){	//read all the nodes' names in the file
			Nodes[i]=scanner.next();
		}
		
		SIS graph = new SIS();
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
		
		graph.findinfected(infectrate, infectedtime ,behavior,NumOfNode,Nodes);

	}
	
	/**
	 * main method to find the state of each nodes
	 * set infected to be true, not infected to be false
	 * any uninfected node has a probably to be infected by its neighbor which is been infected 
	 * any infected node has an infected time, after the infected time, the node will recover and can be infected again
	 * @param infectrate	
	 * @param infectedtime
	 * @param behavior 	boolean array
	 * @param numOfNode		
	 * @param Nodes		String array (names of all the nodes)		
	 */
	public void findinfected(double infectrate, int infectedtime, boolean[] behavior, int NumOfNode,String[] Nodes) {
		//System.out.println(infectedtime);
		boolean[] behaviorcopy = new boolean[NumOfNode];	//a boolean array to record the nodes' next state's behavior
		for (int i = 0; i < NumOfNode; i++){
			behaviorcopy[i]=behavior[i];
			vertices.get(Nodes[i]).behavior=behavior[i];	
			if (vertices.get(Nodes[i]).behavior == true){
				vertices.get(Nodes[i]).counter=infectedtime;	//set the infected time for the infected nodes
			}
			
		}
		
		
		
		int run=0;
		
		while(run<20){
			
			run++;
			
			for (int i = 0; i < NumOfNode; i++){	//go through all the nodes 
				
				
				Vertex curr = vertices.get(Nodes[i]);	//find the vertex representation of the node
				
				if(curr.counter == 0){		//if the infected time is past, the node is back to the state that can be infected
					curr.behavior=false;
					behavior[i]=false;
					//TODO: not to change the behavior
					behaviorcopy[i]=false;
					//System.out.println("hi");
					
				}
				if(curr.behavior==true){	//count down for the infected time
					curr.counter--;
					System.out.println(curr.label+" "+curr.counter);
				}
				
				
				for (Edge x:adjacent.get(curr.label)){	//go through all its adjacency list

					if (x.source.behavior==false && x.target.behavior==true && behaviorcopy[i]!=true){		//find if it is going to be infected
						Double random = Math.random();
	
						if(random<=infectrate){
							//System.out.println(random);
							behaviorcopy[i]=true;
							x.source.counter=infectedtime;
						}
								
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


	public SIS() {
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
		int counter;
		String label;
		boolean behavior;
		
		
		
		
	
		//create a node with its label
		public Vertex(String label) {
			this.label = label;
			
	
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
