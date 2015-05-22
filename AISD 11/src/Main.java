import java.util.Scanner;

import aisd11.alg.Dijkstra;
import aisd11.structs.Graph;

public class Main {

	public static void main(String[] args) {

		System.out.print("Podaj jak wiele ma byc wierzcolkow w grafie : ");
		Scanner sc = new Scanner(System.in);
		int size = sc.nextInt();
		System.out.print("Podaj wspolczynnik spojcosci grafu (1 - wszystkie wierzcholki sa ze soba polaczone, 0 zaden wierzcholek nie ma polaczenia) : ");
		double con = sc.nextDouble();
		Graph g = new Graph(size,con);

		printGraph(g);

		System.out
		.print("Podaj nr miasta od ktorego bedzie wyznaczana dlugosc drogi : ");
		int startingTownId = sc.nextInt();
		while (startingTownId != -1) {
			int[][] result = Dijkstra
					.countDistFromTownWithId(startingTownId, g);

			System.out.println();
			System.out.printf("Miasta osisgalne z miasta nr %d :%n",
					startingTownId);
			System.out.printf("|nr miasta|dlugosc sciezki|sciezka%n");
			System.out.printf("----------------------------------%n");
			for (int i = 0; i < g.size; i++) {
				if (result[i][0] != Integer.MAX_VALUE) {
					StringBuffer sb = new StringBuffer();
					int aux = i;
					sb.append(String.format("%d ,",i));
					while (result[aux][1] != -1) {
						sb.append(String.format("%d ,", result[aux][1]));
						aux = result[aux][1];
					}
					sb.delete(sb.length() - 2, sb.length());
					sb.reverse();
					System.out.printf("|%9d|%15d|%s%n", i, result[i][0],
							sb.toString());
				}
			}
			System.out.println();
			System.out
			.print("Podaj nr miasta od ktorego bedzie wyznaczana dlugosc drogi (lub -1 by zakonczyc dzialanie programu) : ");
			startingTownId = sc.nextInt();
			
		}
		sc.close();

	}

	private static void printGraph(Graph g) {
		for (int i = -1; i < g.size; i++) {
			for (int j = -1; j < g.size; j++) {
				int toPrint;
				if (i == -1) {
					if (j == -1) {
						System.out.printf("nr ");
						continue;
					}
					toPrint = j;
				} else {
					if (j == -1)
						toPrint = i;
					else
						toPrint = g.edges[i][j];
				}
				System.out.printf("|%2d", toPrint);
			}
			System.out.printf("|%n");
			for (int j = -1; j < g.size; j++)
				System.out.printf("---");
			System.out.printf("%n");
		}
	}

}
