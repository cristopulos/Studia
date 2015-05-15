package aisd10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import aisd10.rbt.RBT;

public class Main {
	
	public static void main (String[] args) throws FileNotFoundException
	{
		File f = new File("in.txt");
		FileToRBTree.createRBTreeFromFile(f);
		RBT<String,List<Integer>> rBTree = FileToRBTree.tree;
		List<String> wordList = FileToRBTree.wordsList;
		Collections.sort(wordList);
		int maxSize = 0;
		for(String word : wordList)
		{
			if(word.length() > maxSize)
				maxSize = word.length();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("%-")
		.append(maxSize)
		.append("s - %s%n");
		for(String word : wordList)
		{
			String temp = rBTree.get(word).toString();
			temp = temp.substring(1, temp.length()-1);
			System.out.printf(sb.toString(), word, temp);
		}
		
	}

}
