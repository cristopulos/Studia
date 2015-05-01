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
			BinaryONPTree ONPTree = BinaryONPTree.createTree(statement); 
			double result = 0;
			result = ONPTree.calculate();
			ps.printf("Wynik to : %.2f%n",result);
			ps.printf("inorder : %s%n",ONPTree.inorder());
			ps.printf("postorder : %s%n",ONPTree.postorder());
			ps.printf("ilosc lisci : %d%n",ONPTree.lefasQuant());
			ps.printf("wysokosc drzewa : %d%n",ONPTree.height());
			ps.printf("ilosc wezlow : %d%n",ONPTree.nodesQuant());
			in.close();
			ps.close();
	}
}
