package research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


public class LatticesNetwork {

	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner("5");
		int NumOfNode = scanner.nextInt();
//		System.out.println(Math.ceil(Math.sqrt(5)));
		
		
		LatticesNetwork graph = new LatticesNetwork();
		String file = graph.makelatticesnetwork(NumOfNode);
		
		System.out.println(file);
	}

	
	
	
	
	//file:
	//number of nodes
	//nodes' names
	//all edges
	private String makelatticesnetwork(int numOfNode) {
		int sqrt = (int) Math.ceil(Math.sqrt(numOfNode));
		
		
		String file = "";
		
		LatticesNetwork network = new LatticesNetwork();
		
		file+=numOfNode + " "+"\n";
		
		for (int i =0; i < numOfNode; i++){
			String x = new Integer(i).toString();
			network.addVertex(x);
			file+=x+" "+ "\n";
		}
		
		int i = 0;
		int j = 0;
		for (String key:network.vertices.keySet()){
			Vertex u = network.vertices.get(key);
			if(j<=sqrt){
				u.i=i;
				u.j=j;
				j++;
			}
			if(j>sqrt&&i<sqrt){
				j=0;
				i++;
				u.i=i;
				u.j=j;
				j++;
			}
			
		}
		
		for(String key: network.vertices.keySet()){
			Vertex curr = network.vertices.get(key);
			for (String key2: network.vertices.keySet()){
				Vertex curr2 = network.vertices.get(key2);
				if(curr!=curr2){
					if(curr2.i==curr.i-1 && curr2.j==curr.j){
						network.addEdge(curr.label, curr2.label);
						network.addEdge(curr2.label, curr.label);
						file+=key+" "+ key2+" "+"\n";
					}
					if(curr2.j==curr.j-1 && curr2.i==curr.i){
						network.addEdge(curr.label, curr2.label);
						network.addEdge(curr2.label, curr.label);
						file+=key+" "+ key2+" "+"\n";
					}
					
					
				}
			}
		}
		
		for(String key: network.vertices.keySet()){
			Vertex curr = network.vertices.get(key);
			System.out.println(key+" "+curr.i+" "+ curr.j);
		}
		
		return file;
	}









	public LatticesNetwork() {
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
		int i;
		int j;
		
	
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
