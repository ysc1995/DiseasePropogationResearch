package research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;







public class RandomNetwork {

	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner("5000 0.1");
		int NumOfNode = scanner.nextInt();
		double p = scanner.nextDouble();
		
		
		double starttime = System.currentTimeMillis();
		RandomNetwork graph = new RandomNetwork();
		
		String file = graph.makerandomnetwork(NumOfNode,p);
		double endtime = System.currentTimeMillis();
		System.out.println(file);
		System.out.println(endtime-starttime);
	}

	
public String toString() {
		
		StringBuilder s = new StringBuilder();
		s.append("digraph{\n");
		//edit all vertices 
		
		for (String key : vertices.keySet()) {
			
			s.append(vertices.get(key)+" [label=\""+vertices.get(key)+"\"" +"]"+"\n");
		}
		
		//edit all edges 
		
		for (String key : vertices.keySet()) {
			ArrayList<Edge> edges = adjacent.get(key);
			for (Edge e : edges) {
				s.append(e.source+"->"+e.target + "\n");
			}
		}
		s.append("\n}");
		return s.toString();
	}
	
	
	//file:
	//numofNode
	//all the nodes' names
	//all vertex(2 1)
	public static String makerandomnetwork(int numOfNode,double p) {
		String file = "";
		
		RandomNetwork network = new RandomNetwork();
		
		file+=numOfNode + " "+"\n";
		
		for (int i =0; i < numOfNode; i++){
			String x = new Integer(i).toString();
			network.addVertex(x);
			file+=x+" "+ "\n";
		}
		
		for (String key:network.vertices.keySet()){
			 Vertex curr = network.vertices.get(key);
			 
			 for(String key2:network.vertices.keySet()){
				 Vertex curr2 = network.vertices.get(key2);
				 double random = Math.random();
				 
				
			
				 if(curr != curr2 && random < p && curr2.checked==false){
					 boolean alreadyconnected = false;
					 for(Edge x :network.adjacent.get(key2)){		//for any two vertices, there is a probability p for them to have an edge
						 if(x.target==curr){
							alreadyconnected = true;
							
						 }
						
					 }
					 if(alreadyconnected ==false){
						 network.addEdge(key, key2);
						 network.addEdge(key2, key);
						 file+=key+" "+ key2+" "+"\n";
					 }
					 
				 }
			 }
			 
			 curr.checked=true;
		}
		
		
		
		return file;
	}
	
	
	
	
	
	
	public RandomNetwork() {
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
		boolean checked;
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
