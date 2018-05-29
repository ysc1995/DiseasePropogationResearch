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
//recover time
//all the nodes name
//all nodes' initial behaviors 
//all the edges 



public class SIRS {
	
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
		int recovertime = scanner.nextInt();			//read recover time
		
		String[] Nodes = new String[NumOfNode];
		for (int i = 0; i< NumOfNode; i++){	//read all the nodes' names in the file
			Nodes[i]=scanner.next();
		}
		
		SIRS graph = new SIRS();
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
		
		graph.findinfected(infectrate, infectedtime , recovertime, behavior,NumOfNode,Nodes);

	}
	
	/**
	 * main method to find the state of each nodes
	 * set infected to be true, not infected to be false
	 * node has a probably to be infected by its neighbor which is been infected 
	 * any infected node has an infected time, after the infected time, the node will go into recover process
	 * recover process has a recover time.	During this time, the node can't be infected or infect others
	 * after the recover time, the node can be infected
	 * @param infectrate	
	 * @param infectedtime
	 * @param recovertime
	 * @param behavior 	boolean array
	 * @param numOfNode		
	 * @param Nodes		String array (names of all the nodes)		
	 */
	public void findinfected(double infectrate, int infectedtime, int recovertime, boolean[] behavior, int NumOfNode,String[] Nodes) {
		//System.out.println(infectedtime);
		boolean[] behaviorcopy = new boolean[NumOfNode];	//a boolean array to record the nodes' next state's behavior
		for (int i = 0; i < NumOfNode; i++){
			behaviorcopy[i]=behavior[i];
			vertices.get(Nodes[i]).behavior=behavior[i];	
			vertices.get(Nodes[i]).recover=false;
			if (vertices.get(Nodes[i]).behavior == true){
				vertices.get(Nodes[i]).infectedcounter=infectedtime;	//set the infected time for the infected nodes
				
			}
			
		}
		
		
		
		int run=0;
		
		while(run<20){
			
			run++;
			
			for (int i = 0; i < NumOfNode; i++){	//go through all the nodes 
				
				
				Vertex curr = vertices.get(Nodes[i]);	//find the vertex representation of the node
				
				if(curr.infectedcounter == 0&&curr.behavior==true){		//if the infected time is passed, the node goes to recover state
					curr.behavior=false;
					behavior[i]=false;
					behaviorcopy[i]=false;
					
					curr.recovercounter=recovertime;
					curr.recover = true;
					//System.out.println("hi");
					
				}
				if(curr.behavior==true){	//count down for the infected time
					curr.infectedcounter--;
					System.out.println(curr.label+" "+"infect time:" +curr.infectedcounter);
				}
				if(curr.recovercounter==0){	//if pass the recover time, the node can be infected again
					curr.recover=false;
				}
				if(curr.recover==true){		//count down for the recover time
					curr.recovercounter--;
					System.out.println(curr.label+" "+"recover time: "+curr.recovercounter);
				}
				
				for (Edge x:adjacent.get(curr.label)){	//go through all its adjacency list

					if (x.source.recover==false&&x.source.behavior==false && x.target.behavior==true && behaviorcopy[i]!=true){		//find if it is going to be infected
						Double random = Math.random();
	
						if(random<=infectrate){
							System.out.println(x+" "+random);
							behaviorcopy[i]=true;
							x.source.infectedcounter=infectedtime;
						}
								
					}		
				}	
			}
			
			
			for (int z = 0; z <NumOfNode; z++){	//update behavior array
				behavior[z] = behaviorcopy[z];
				vertices.get(Nodes[z]).behavior=behaviorcopy[z];	
			}
			
			for (int n = 0; n < NumOfNode ; n++){
				if(behavior[n]==false&&vertices.get(Nodes[n]).recover==true){
					System.out.println(Nodes[n]+" "+"recovering");
				}
				else{
					System.out.println(Nodes[n]+" "+ behavior[n]);
				}
				
			}
			System.out.println("running time" +run);
			
		}
	}


	public SIRS() {
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
	
		boolean recover;
		int infectedcounter;
		int recovercounter;
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
			return "(" + source + " <- " + target+ ")";
		}
		
	}
}
