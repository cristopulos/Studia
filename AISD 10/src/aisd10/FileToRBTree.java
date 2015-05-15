package aisd10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aisd10.rbt.RBT;

public class FileToRBTree {

	public static RBT<String,List<Integer>> tree;
	public static List<String> wordsList;
	
	public static RBT<String,List<Integer>> createRBTreeFromFile (File f) throws FileNotFoundException
	{
		RBT<String,List<Integer>> result = new RBT<String,List<Integer>>();
		List<String> wordList = new ArrayList<String>();
		Scanner sc = new Scanner(f);
		int line = 1;
		while(sc.hasNextLine())
		{
			String lineText = sc.nextLine();
			Pattern p  = Pattern.compile("[a-z|¹|ê|œ|¿|Ÿ|æ|³|ó|ñ]+");
			Matcher m = p.matcher(lineText);
			List<String> words = new ArrayList<String>();
			while(m.find())
			{
				words.add(lineText.substring(m.start(), m.end()));
			}
			for(String word : words)
			{
				if(!wordList.contains(word))
					wordList.add(word);
				List<Integer> temp;
				if(result.contains(word))
				{
					temp = result.get(word);
				} else {
					temp = new ArrayList<Integer>();
				}
				temp.add(line);
				result.put(word, temp);
			}
			line++;
		}
		sc.close();
		wordsList = wordList;
		tree = result;
		return result;
	}
}
