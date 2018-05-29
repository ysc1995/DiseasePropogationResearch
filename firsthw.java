package research;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileNotFoundException;

//intput text file:
//number of node
//number of edge
//p value
//all the nodes that have behavior a
//all the edge

public class firsthw {


public static void main (String[] args)throws FileNotFoundException{
	InputStream d = null;
	File file = new File(args[0]);
	
	
	try{
		d = new FileInputStream(file);
	}
	catch(FileNotFoundException a){
		System.out.println("FileNotFound");
		System.exit(0);
	}
	Scanner scanner = new Scanner(file);
	int NumOfNode = scanner.nextInt();
	int NumOfEdge = scanner.nextInt();
	int p = scanner.nextInt();
	Vertex[] adjList = new Vertex[NumOfNode];
	
	int [] v = new int[NumOfNode];
	
	for (int i = 0; i < NumOfNode; i++){
		
		adjList[i].name = i;
	}
	
	while (scanner.hasNext()){
		int v1 = scanner.nextInt();
		int v2 = scanner.nextInt();
		adjList[v1].edge = new Edge(adjList[v2]);
		
	
	
}
}

private static void getfinalbehavior(int[][] node, int[] v, int [] vcopy,int p){
	boolean haschanged = true; 
	
	
	int run = 0;
	while (haschanged != false && run <1000){
		haschanged = false;
		run ++;
		
		
		for (int i = 0; i < 5; i++){
			
			double numA = 0;
			double numB = 0;
			for (int j = 0 ; j <5 ; j ++){
				if (node[i][j]==1){
					if (v[j]==1){
						numA++;
					}
					else {
						numB++;
					}
				}
			}
			if (v[i]==1){
				if (numA/(numB+numA)<p){
					vcopy[i]=0;
					haschanged = true;
				}
				else{
					vcopy[i]=v[i];
				}
				
			}
			if (v[i]==0){
				if (numA/(numB+numA)>=p){
					vcopy[i]=1;
					haschanged = true;
				}
				else{
					vcopy[i]=v[i];
				}
			}
			
			System.out.println("A " +numA+" B "+numB);
			
		}
		
		for (int z = 0; z <5; z++){
			v[z] = vcopy[z];
		}
		
		for (int i = 0; i < 5 ; i++){
			
			System.out.println(i+" "+ v[i]);
			
		}
		System.out.println("running time" +run);
		
	}
	
}
	
	class Edge{
		Vertex next;
		
		
		
		Edge(Vertex adjList){
			next = adjList;
		}
	}
	
	
	
	class Vertex{
		int name;
		Edge edge;
		Vertex(Edge neighbors){
			edge = neighbors;
			
		}
		
	}

}





