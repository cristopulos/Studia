package aisd.util;

import aisd4.queue.Kolejka;

@SuppressWarnings("rawtypes")
public class BinaryTreeElement <T>{

	public T value;
	public BinaryTreeElement leftSon;
	public BinaryTreeElement rightSon;
	
	
	public BinaryTreeElement(T value, BinaryTreeElement leftSon,
			BinaryTreeElement rightSon) {
		this.value = value;
		this.leftSon = leftSon;
		this.rightSon = rightSon;
	}
	
	public BinaryTreeElement (T value)
	{
		this.value = value;
		leftSon = null;
		rightSon = null;
	}
	
	public int height ()
	{
		int vl,vr;
		if(leftSon == null) vl = 0;
		else vl = leftSon.height()+1;
		if(rightSon == null) vr=0;
		else vr = rightSon.height()+1;
		return (vl>vr ? vl : vr);
	}
	
	public int nodesQuant ()
	{
		int vl,vr;
		if(leftSon == null) vl=0;
		else vl = leftSon.nodesQuant();
		if(rightSon == null) vr=0;
		else vr = rightSon.nodesQuant();
		return vl+vr+1;
	}
	
	public int leafsQuant ()
	{
		int vl,vr;
		if(leftSon == null) vl = 0;
		else vl = leftSon.leafsQuant();
		if(rightSon == null) vr = 0;
		else vr = rightSon.leafsQuant();
		return (vl==0 && vr==0 ? 1 : vl+vr);			
	}
	
	public double calculate() throws InvalidCharacterExcpection
	{
		if(value instanceof Integer)
			return ((Integer) value).doubleValue();
		else if(value instanceof Character)
		{
			switch ((Character)value)
			{
			case '+' : return leftSon.calculate()+rightSon.calculate();
			case '-': return leftSon.calculate()-rightSon.calculate();
			case '*' : return leftSon.calculate()*rightSon.calculate();
			case '/' : return leftSon.calculate()/rightSon.calculate();
			case '%' : return leftSon.calculate()%rightSon.calculate();
			default : throw new InvalidCharacterExcpection(null);
			}
		}
		else
			throw new InvalidCharacterExcpection(null);
	}
	
	public String inorder () throws InvalidCharacterExcpection
	{
		if(value instanceof Integer)
			return ((Integer)value).toString();
		else if(value instanceof Character)
		{
			return "(" + leftSon.inorder() + (Character)value + rightSon.inorder() + ")";
		}
		else
			throw new InvalidCharacterExcpection(null);
	}
	
	public String postorder () throws InvalidCharacterExcpection
	{
		if(value instanceof Integer)
		{
			return ((Integer)value).toString();
		}
		if(value instanceof Character)
		{
			return (Character)value + " " + rightSon.postorder()  + " " + leftSon.postorder();
		}
		else
			throw new InvalidCharacterExcpection(null);
	}
	
	public String bfs()
	{
		StringBuffer sb = new StringBuffer();
		Kolejka<BinaryTreeElement> queue = new Kolejka<BinaryTreeElement>();
		queue.insert(this);
		while(!queue.isEmpty())
		{
			BinaryTreeElement el = queue.remove();
			sb.append(String.format("%s,",el.value.toString()));
			if(el.leftSon!=null)
				queue.insert(el.leftSon);
			if(el.rightSon!=null)
				queue.insert(el.rightSon);
		}
		return sb.toString();
	}
	
	
}
