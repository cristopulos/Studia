package aisd9;

import java.io.File;

import aisd9.huffmanTree.HuffmanTree;

public class Main {
	
	
	public static void main(String[] args) throws Exception
	{
		File in = new File("in.txt");
		File out = new File("out.txt");
		HuffmanTree tree = new HuffmanTree(in);
		tree.printTree();
		tree.encrypt(out);
	}

}
