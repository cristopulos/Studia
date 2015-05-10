package aisd9.huffmanTree;

public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

	private char ch;
	private int quant;
	private boolean onlyValue;
	private HuffmanTreeNode leftSon;
	private HuffmanTreeNode rightSon;
	private HuffmanTreeNode parent;
	private int height;

	public HuffmanTreeNode(char ch, int quant) {
		this.ch = ch;
		this.quant = quant;
		onlyValue = false;
		height = 0;
	}

	public HuffmanTreeNode(int quant) {
		this.quant = quant;
		onlyValue = true;
		height = 0;
	}

	public char getCh() {
		return ch;
	}

	public int getQuant() {
		return quant;
	}

	public boolean isOnlyValue() {
		return onlyValue;
	}

	public void incrementQuant() {
		quant++;
	}

	public HuffmanTreeNode getLeftSon() {
		return leftSon;
	}

	public HuffmanTreeNode getRightSon() {
		return rightSon;
	}

	public HuffmanTreeNode getParent() {
		return parent;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void linkLeft (HuffmanTreeNode node)
	{
		leftSon = node;
		node.parent = this;
		if(height <= node.getHeight())
			height = node.getHeight()+1;
	}
	
	public void linkRight (HuffmanTreeNode node)
	{
		rightSon = node;
		node.parent = this;
		if(height <= node.getHeight())
			height = node.getHeight()+1;
	}

	@Override
	public int compareTo(HuffmanTreeNode o) {
		return (int) Math.signum(quant - o.getQuant());
	}

	public boolean equals (HuffmanTreeNode h)
	{
		if(h.getCh() == ch)
			return true;
		else return false;
	}

}
