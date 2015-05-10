package aisd9.comparators;

import java.util.Comparator;

import aisd9.huffmanTree.HuffmanTreeNode;

public class HuffmanTreeNodesQuantReversedComparator implements Comparator<HuffmanTreeNode>{

	@Override
	public int compare(HuffmanTreeNode o1, HuffmanTreeNode o2) {
		int a =(int) Math.signum(-o1.getQuant() + o2.getQuant());
		if(a==0)
		{
			int b = (int)Math.signum(o1.getHeight() - o2.getHeight());
			return b;
		}
		return a;
	}
}
