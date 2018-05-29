package research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;





public class ScaleFree2 {

	
	Map<String, Vertex> vertices;
	
	Map<String, ArrayList<Edge>> adjacent;
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(args[0]);
		int NumOfNode = scanner.nextInt();
		int NumOfEdge = scanner.nextInt();
		
	
		
		
		ScaleFree2 graph = new ScaleFree2();
		graph.makescalefreenetwork(NumOfNode, NumOfEdge);
		
		
		
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
	
	
	



	public void makescalefreenetwork(int numOfNode, int numOfEdge) {
		int DegreeSum= 0;
		int[] degrees = new int[numOfNode+1];
		
		for (int i =1; i < numOfNode+1; i++){
			String x = new Integer(i).toString();
			this.addVertex(x);
			this.vertices.get(x).NumOfNeighbor=0;
			degrees[i]=i;
			
		}
		int x = (int) Math.ceil(Math.random()*numOfNode);
		Vertex first = this.vertices.get(new Integer(x).toString());
		
		if(numOfNode>1){
			boolean created = false;
			while(created==false){
				int y = (int) Math.ceil(Math.random()*numOfNode);
				if(y!= x){
					Vertex second = this.vertices.get(new Integer(y).toString());
					created =true;
					this.addEdge(first.lable, second.lable);
					this.addEdge(second.lable, first.lable);
					first.NumOfNeighbor++;
					second.NumOfNeighbor++;
					DegreeSum=DegreeSum+2;
					for(int z = x; z < numOfNode+1; z++){
						degrees[z]++;
					}
					
					for(int z = y; z < numOfNode+1; z++){
						degrees[z]++;
					}
					
					
					
				}
				
			}
		}
		int nodelab1 = (int) Double.NEGATIVE_INFINITY;
		int nodelab2 = (int) Double.NEGATIVE_INFINITY;
		for(int i = 1 ; i < numOfEdge; i++){
			
				int m = (int) Math.ceil(Math.random()*(numOfNode+DegreeSum));
				for(int j = 0 ; j < numOfNode; j++){
					if(degrees[j]<m &&degrees[j+1]>=m){
						nodelab1 = j+1;
					}
				}
				Vertex source = this.vertices.get(new Integer(nodelab1).toString());
				
				boolean created = false;
				
				
				while(created==false){
					boolean alreadyconnected = false;
					int n = (int) Math.ceil(Math.random()*numOfNode+DegreeSum);
					for(int k = 0 ; k < numOfNode; k++){
						if(degrees[k]<n &&degrees[k+1]>=n){
							nodelab2 = k+1;
						}
					}
					Vertex target = this.vertices.get(new Integer(nodelab2).toString());
					for(Edge e : this.adjacent.get(source.lable)){
						if (e.target == target){
							alreadyconnected = true;
						}
					}
					if(target!=source && alreadyconnected == false){
						created =true;
						this.addEdge(target.lable, source.lable);
						this.addEdge(source.lable, target.lable);
						target.NumOfNeighbor++;
						source.NumOfNeighbor++;
						DegreeSum = DegreeSum+2;
						
						for(int z = nodelab1; z < numOfNode+1; z++){
							degrees[z]++;
						}
						
						for(int z = nodelab2; z < numOfNode+1; z++){
							degrees[z]++;
						}
						
					}
				}
		}
			
		
	}

	



	public ScaleFree2() {
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
		
		int NumOfNeighbor;
		String lable;
		boolean behavior;
		boolean recovered;
		boolean checked;
		int i;
		int j;
		
	
		//create a node with its label
		public Vertex(String label) {
			this.lable = label;
			recovered=false;
			
	
		}
		
		
		public String toString() {
			return lable;
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
