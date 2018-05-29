package research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class hw2 {
	public static void main(String[] args)throws FileNotFoundException{
		InputStream d = null;
		File file = new File("C:/Users/Shaocheng Yang/Desktop/research/hw2.txt");
		
		
		try{
			d = new FileInputStream(file);
		}
		catch(FileNotFoundException a){
			System.out.println("FileNotFound");
			System.exit(0);
		}
		Scanner scanner = new Scanner(file);
		int NumOfNode = scanner.nextInt();
		
		Graph g = new Graph(NumOfNode);
		double p = scanner.nextDouble();
		boolean [] bh = new boolean[NumOfNode];
		
		for (int i = 0; i < NumOfNode; i++){
			int n = scanner.nextInt();
			if (n==1){
				bh[i]=true;
			}
			else bh[i]=false;
			
		}
		while(scanner.hasNextInt()){
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			g.addEdge(a,b);
			g.addEdge(b,a);
		}
		
		
		
		
		getfinalbehavior(p,g,bh,NumOfNode);
		System.out.println(g);
		
	}

	private static void getfinalbehavior(double p, Graph g, boolean[] bh,int NumOfNode) {
		boolean haschanged = true;
		int run = 0;
		boolean [] bhcopy = new boolean[NumOfNode];
		while (haschanged != false && run < 1000){
			haschanged = false;
			run ++;
			
			for(int i = 0; i < NumOfNode;i++){
				double numA = 0;
				double numB = 0;
				for (int j = 0; j < NumOfNode ; j++){
					if(i!=j){
						if(g.isConnected(i, j)){
							if(bh[j]==false){
								numB++;
							}
							else{
								numA++;
							}
						}
					}
				}
				if (bh[i]==true){
					if (numA/(numB+numA)<p){
						bhcopy[i]=false;
						haschanged = true;
					}
					else{
						bhcopy[i]=bh[i];
					}
					
				}
				if (bh[i]==false){
					if (numA/(numB+numA)>=p){
						bhcopy[i]=true;
						haschanged = true;
					}
					else{
						bhcopy[i]=bh[i];
					}
				}
				
				System.out.println("A " +numA+" B "+numB);
			}
		
		
		for (int z = 0; z <NumOfNode; z++){
			bh[z] = bhcopy[z];
		}
		
		for (int i = 0; i < NumOfNode ; i++){
			
			System.out.println(i+" "+ bh[i]);
			
		}
		System.out.println("running time" +run);
		
		}
	}
}

class Graph{
	class Edge{
		int v;
		public Edge(int v){
			this.v =v;
		}
		
		public String toString(){
			return "(" + v + ")";
		}
	}
	
	List<Edge> G[];
	public Graph(int n ){
		G = new LinkedList[n];
		for (int i = 0; i < G.length; i++){
			G[i] = new  LinkedList<Edge>();
		}
	}
	//iterator
	//iterable (while)
	void addEdge(int curr, int v){
		G[curr].add(new Edge(v));
	}
	
	boolean isConnected(int curr, int v){
		for (Edge i : G[curr]){
			if(i.v == v){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String result = "";
		for (int i = 0; i < G.length;i++){
			result += i +": "+G[i]+"\n";
		}
		return result;
	}
	
	
	
	
}
