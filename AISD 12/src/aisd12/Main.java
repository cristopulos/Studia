package aisd12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import aisd12.Graph.Edge;
import aisd12.Graph.Graph;
import aisd12.alg.KruskalAlg;

public class Main {
	
	public static void main (String[] args) throws FileNotFoundException
	{
		System.out.printf("Podaj sciezke do pliku : ");
		Scanner sc = new Scanner(System.in); 
		File f = new File(sc.nextLine());
		sc.close();
		sc = new Scanner(f);
		int lamps = sc.nextInt();
		int vertexes = sc.nextInt();
		Graph g = new Graph(lamps,true);
		for(int i=0;i<vertexes;i++)
		{
			g.addVertex(sc.nextInt(), sc.nextInt(), sc.nextInt());
		}
		sc.close();
		int res = KruskalAlg.computeMinTree(g);
		String result;
		if(res == Integer.MAX_VALUE)
			result = "Nie ma drogi laczacej wszystkie lampiony";
		else result = Integer.toString(res);
		System.out.printf("Najmniejsza dlugosc kabla laczacego wszystkie lampiony to : %s%n",result);
		for (Edge e: KruskalAlg.edgeList)
		{
			System.out.println(e);
		}
		
	}

}
