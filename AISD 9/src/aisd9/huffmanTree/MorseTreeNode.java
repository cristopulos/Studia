package aisd9.huffmanTree;

public class MorseTreeNode {
	
	private char ch;
	private MorseTreeNode parent;
	private MorseTreeNode leftSon;
	private MorseTreeNode rightSon;
	public MorseTreeNode(char ch) {
		this.ch = ch;
	}
	
	
	public void linkLeft (MorseTreeNode node)
	{
		leftSon = node;
		node.parent = this;
	}
	
	public void linkRight (MorseTreeNode node)
	{
		rightSon = node;
		node.parent = this;
	}


	public char getCh() {
		return ch;
	}


	public MorseTreeNode getParent() {
		return parent;
	}


	public MorseTreeNode getLeftSon() {
		return leftSon;
	}


	public MorseTreeNode getRightSon() {
		return rightSon;
	}
	
	

}
