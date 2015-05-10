package main;

import java.io.PrintStream;
import java.util.Scanner;

import aisd.util.BinaryONPTree;
import aisd.util.InvalidCharacterExcpection;

public class Main {

	public static void main(String[] args) throws InvalidCharacterExcpection

	{
		Scanner in = new Scanner(System.in);
		PrintStream ps = new PrintStream(System.out);
		ps.printf("Podaj wyrazenie do obliczenia%n");
		String statement = in.nextLine();
		BinaryONPTree oNPTree = BinaryONPTree.createTree(statement);
		double result = 0;
		result = oNPTree.calculate();
		ps.printf("Wynik to : %.2f%n", result);
		ps.printf("inorder : %s%n", oNPTree.inorder());
		ps.printf("postorder : %s%n", oNPTree.postorder());
		ps.printf("ilosc lisci : %d%n", oNPTree.lefasQuant());
		ps.printf("wysokosc drzewa : %d%n", oNPTree.height());
		ps.printf("ilosc wezlow : %d%n", oNPTree.nodesQuant());
		ps.printf("drzewo bfs : %s", oNPTree.bfs());
		in.close();
		ps.close();
	}
}
