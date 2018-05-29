package research;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;



public class Random2 {

	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;
	
	
	public static void main(String[] args){
		Scanner scanner = new Scanner("10 0.2");
		int NumOfNode = scanner.nextInt();
		double p = scanner.nextDouble();
		
		PrintWriter out = null;
		try {
			out = new PrintWriter("C:/Users/Shaocheng Yang/Desktop/network.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		Random2 graph = new Random2();
		
		graph.makerandom2(NumOfNode,p,out);
	
		System.out.println(graph);

//		String f = graph.degreedistribution(NumOfNode);
//		System.out.println(f);

		
	}

	



private String degreedistribution(int NumOfNode) {
	String f = "Degree"+ " NumberOfNodes"+"\n";
	int[] degree = new int[NumOfNode];
	int i = 0;
	for (String key:this.vertices.keySet()){
		
		int n = 0;
		//Vertex curr = this.vertices.get(key);
		 for(Edge x :this.adjacent.get(key)){
			 n++;
		 }
		 degree[n]++;
	}
	for (int j = 0; j < NumOfNode; j++){
		f+=j+" 		"+degree[j]+"\n";
	}
	return f;
		
		
	}


public String toString() {
	
	
		StringBuilder s = new StringBuilder();
//		s.append("digraph{\n");
		//edit all vertices 
		
//		for (String key : vertices.keySet()) {
//			
//			s.append(vertices.get(key)+" [label=\""+vertices.get(key)+"\"" +"]"+"\n");
//		}
		
		//edit all edges 
		
		for (String key : vertices.keySet()) {
			ArrayList<Edge> edges = adjacent.get(key);
			for (Edge e : edges) {
				s.append(e.source+" "+e.target + "\n");
			}
		}
		s.append("\n");
		return s.toString();
	}
	
	
	
	public void makerandom2(int numOfNode,double p, PrintWriter out) {
		int n = 0;
		int edgenum = (int) (p*(numOfNode)*(numOfNode-1)/2);
		for (int i =1; i < numOfNode+1; i++){
			String x = new Integer(i).toString();
			this.addVertex(x);
		}
		while(n<edgenum){
			
			boolean connected = false;
			int x = (int) Math.ceil(Math.random()*numOfNode);
			Vertex first = this.vertices.get(new Integer(x).toString());
			int y = (int) Math.ceil(Math.random()*numOfNode);
			Vertex second = this.vertices.get(new Integer(y).toString());
			
			for(Edge e :this.adjacent.get(first.label)){
				if (e.target==second){
					connected = true;
				}
			}
			if(x!=y && connected == false){
				this.addEdge(first.label, second.label);
				this.addEdge(second.label, first.label);
				first.degree++;
				second.degree++;
				n++;
				out.write(first.label+ " "+second.label+ "\r\n");
			}
		}
		out.close();
		
	}
	
	
	
	
	
	
	public Random2() {
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
		int degree;
		
	
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
