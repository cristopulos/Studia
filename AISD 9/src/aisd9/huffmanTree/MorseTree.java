package aisd9.huffmanTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MorseTree {

	private MorseTreeNode root;
	
	public MorseTree(File f) throws FileNotFoundException {
		root = new MorseTreeNode('\n');
		List<MorseHelper> list = generateCharListFromFile(f);
		Collections.sort(list);
		generateTreeFromCharList(list);
	}
	
	
	
	private List<MorseHelper> generateCharListFromFile (File f) throws FileNotFoundException
	{
		Scanner sc = new Scanner(f);
		List<MorseHelper> result = new ArrayList<MorseHelper>();
		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			char c = line.charAt(0);
			String code = line.substring(1, line.length());
			MorseHelper temp = new MorseHelper(code, c);
			result.add(temp);
			
		}
		sc.close();
		return result;
	}
	
	private void generateTreeFromCharList (List<MorseHelper> list)
	{
		for(int i=0; i<list.size();i++)
		{
			MorseHelper helper = list.get(i);
			MorseTreeNode cur = root;
			for (int j=0;j<helper.code.length()-1;j++)
			{
				if(helper.code.charAt(j) == '.')
					cur = cur.getLeftSon();
				else
					cur = cur.getRightSon();
			}
			if (helper.code.charAt(helper.code.length()-1) == '.')
				cur.linkLeft(new MorseTreeNode(helper.ch));
			else
				cur.linkRight(new MorseTreeNode(helper.ch));
		}
	}
	
	public String decryptFile (File f) throws FileNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		Scanner sc = new Scanner (f);
		sc.useDelimiter("\\|");
		while(sc.hasNext())
		{
		String character = sc.next();
		MorseTreeNode cur = root;
		for(int i=0; i<character.length();i++)
		{
			if(character.charAt(i)=='.')
				cur = cur.getLeftSon();
			else
				cur = cur.getRightSon();
		}
		sb.append(cur.getCh());
		}
		sc.close();
		return sb.toString();
	}

}
