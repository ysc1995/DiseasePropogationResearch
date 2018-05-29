package research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;





public class ScaleFreeNetwork {

	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(args[0]);
		int NumOfNode = scanner.nextInt();
		String[] nodenames = new String[NumOfNode];
		
		for(int i = 0; i < NumOfNode;i++){
			nodenames[i] = scanner.next();
		}
		
		
		ScaleFreeNetwork graph = new ScaleFreeNetwork();
		graph.makescalefreenetwork(NumOfNode, nodenames);
		
		while(scanner.hasNext()){
			graph.addnewnode(scanner.next());
		}
		
		System.out.println(graph);
		
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
	
	
	private void addnewnode(String vertex) {
		this.addVertex(vertex);
		
		vertices.get(vertex).NumOfNeighbor=0;
		for (String key:this.vertices.keySet()){
			Vertex curr = this.vertices.get(key);
			if(key!= vertex){
				boolean connected = false;
				for(int i = 0; i < curr.NumOfNeighbor;i++){
					
					if(Math.random()<0.1 && connected == false){
						this.addEdge(vertex, key);
						this.addEdge(key, vertex);
						curr.NumOfNeighbor++;
						this.vertices.get(vertex).NumOfNeighbor++;
						connected=true;
					}
				}
			}
		}
		
	}



	public void makescalefreenetwork(int numOfNode, String[] nodenames) {
		
		for (int i =0; i < numOfNode; i++){
			
			this.addVertex(nodenames[i]);
			vertices.get(nodenames[i]).NumOfNeighbor=0;
			if(Math.random()<0.1){
				vertices.get(nodenames[i]).highlyconnected = true;
				
			}
			else{
				vertices.get(nodenames[i]).highlyconnected = false;
			}
			
		}
		
		
		for (String key:this.vertices.keySet()){
			Vertex curr = this.vertices.get(key);
			if(curr.highlyconnected==true){
				for(String key2:this.vertices.keySet()){
					 Vertex curr2 = this.vertices.get(key2);
					 double random = Math.random();
					 
					
				
					 if(curr != curr2 && random < 0.8 &&curr2.checked==false){
						 boolean alreadyconnected = false;
						 for(Edge x :this.adjacent.get(key2)){		//for any two vertices, there is a probability p for them to have an edge
							 if(x.target==curr){
								alreadyconnected = true;
								
							 }
							
						 }
						 if(alreadyconnected ==false){
							 this.addEdge(key, key2);
							 this.addEdge(key2, key);
							 this.vertices.get(key).NumOfNeighbor++;
							 this.vertices.get(key2).NumOfNeighbor++;
						 }
						 
					 }
				}
				curr.checked=true;
			}
			else{
				for(String key2:this.vertices.keySet()){
					 Vertex curr2 = this.vertices.get(key2);
					 double random = Math.random();
					 
					
				
					 if(curr != curr2 && random < 0.2&&curr2.checked==false){
						 boolean alreadyconnected = false;
						 for(Edge x :this.adjacent.get(key2)){		//for any two vertices, there is a probability p for them to have an edge
							 if(x.target==curr){
								alreadyconnected = true;
								
							 }
							
						 }
						 if(alreadyconnected ==false){
							 this.addEdge(key, key2);
							 this.addEdge(key2, key);
							 this.vertices.get(key).NumOfNeighbor++;
							 this.vertices.get(key2).NumOfNeighbor++;
						 
						 }
						 
					 }
				}
				curr.checked=true;
			}
			
			
		}
		
		
		
		
		
	}

	



	public ScaleFreeNetwork() {
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
		boolean highlyconnected;
		int NumOfNeighbor;
		String label;
		boolean behavior;
		boolean recovered;
		boolean checked;
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
