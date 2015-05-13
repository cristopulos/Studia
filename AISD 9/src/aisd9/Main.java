package aisd9;

import java.io.File;

import aisd9.huffmanTree.HuffmanTree;
import aisd9.huffmanTree.MorseTree;

public class Main {
	
	
	public static void main(String[] args) throws Exception
	{
		File in = new File("in.txt");
		File out = new File("out.txt");
		HuffmanTree tree = new HuffmanTree(in);
		tree.printTree();
		tree.encrypt(out);
		File morseIn = new File("morseIn.txt");
		File morseEncr = new File("morseEncrypted.txt");
		MorseTree mTree = new MorseTree(morseIn);
		System.out.println(mTree.decryptFile(morseEncr));
	}

}
