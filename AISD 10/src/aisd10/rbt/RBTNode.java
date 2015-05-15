package aisd10.rbt;

public class RBTNode<T extends Comparable<T>,K> implements Comparable<RBTNode<T,K>> {

	private RBTNode<T,K> leftSon;
	private RBTNode<T,K> rightSon;
	private RBTNode<T,K> parent;
	private T key;
	private K value;
	private RBTColor color;

	public RBTNode(T key, K value) {
		this.key = key;
		this.value = value;
		color = RBTColor.RED;
		parent = null;
		linkLeft(new RBTNode<T,K>());
		linkRight(new RBTNode<T,K>());
	}

	private RBTNode() {
		key = null;
		color = RBTColor.BLACK;
	}

	public void linkLeft(RBTNode<T,K> node) {
		node.parent = this;
		leftSon = node;
	}

	public void linkRight(RBTNode<T,K> node) {
		node.parent = this;
		rightSon = node;
	}

	public void rotateLeft() {
		// zamiana polaczenia pomiedzy ojcem tego wezla a ojcem lewego syna tego wezla czyli ten wezel
		if(parent!=null)
		{
			if(parent.leftSon==this)
				parent.leftSon = rightSon;
			else
				parent.rightSon = rightSon;
		}
		rightSon.parent = parent;
		
		parent = rightSon;
		
		rightSon.leftSon.parent = this;
		RBTNode<T,K> temp = rightSon.leftSon;
		rightSon.leftSon = this;
		rightSon = temp;
	}
	
	public void rotateRight()
	{
		if(parent!=null)
		{
			if(parent.leftSon==this)
				parent.leftSon = leftSon;
			else
				parent.rightSon = leftSon;
		}
		leftSon.parent = parent;
		parent  = leftSon;
		
		leftSon.rightSon.parent = this;
		RBTNode<T,K> temp = leftSon.rightSon;
		leftSon.rightSon = this;
		leftSon = temp;
	}

	public RBTColor getColor() {
		return color;
	}

	public void setColor(RBTColor color) {
		this.color = color;
	}

	public RBTNode<T,K> getLeftSon() {
		return leftSon;
	}

	public RBTNode<T,K> getRightSon() {
		return rightSon;
	}

	public RBTNode<T,K> getParent() {
		return parent;
	}

	public T getKey() {
		return key;
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}

	@Override
	public int compareTo(RBTNode<T,K> o) {
		return key.compareTo(o.key);
	}

}
