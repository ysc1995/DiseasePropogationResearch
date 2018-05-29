package research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Matrix {

	public static void main(String[] args) throws FileNotFoundException {
		InputStream d = null;
		File file = new File("C:/Users/Shaocheng Yang/Desktop/graph.txt");	//address may be changed
				
		try{
			d = new FileInputStream(file);
		}
		catch(FileNotFoundException a){
			System.out.println("FileNotFound");
			System.exit(0);
		}
		
		Scanner scanner = new Scanner(file);
		int num = scanner.nextInt();
		int matrix[][]= new int[num][num];
		
		
		while (scanner.hasNextInt()){
			int a=scanner.nextInt();
			int b = scanner.nextInt();
			matrix[a-1]
		}
	}

}

