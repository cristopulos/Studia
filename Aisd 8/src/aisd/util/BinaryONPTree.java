package aisd.util;


public class BinaryONPTree {
	BinaryTreeElement<Character> topEl = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BinaryONPTree createTree(String ONP) throws InvalidCharacterExcpection {
		ONP = InfixToPostfix.convert(ONP);
		BinaryONPTree result = new BinaryONPTree();
		Stos <BinaryTreeElement> binElStack =  new Stos<BinaryTreeElement>();
		for (int i = 0; i < ONP.length(); i++) {
			char ch = ONP.charAt(i);
			if (Character.isDigit(ch)) {
				StringBuffer sb = new StringBuffer();
				while (ch != '|') {
					sb.append(ch);
					ch = ONP.charAt(++i);
				}
				int temp = Integer.parseInt(sb.toString());
				binElStack.push(new BinaryTreeElement<Integer>(temp));

			} else {
				BinaryTreeElement right = binElStack.pop();
				BinaryTreeElement left = binElStack.pop();
				binElStack.push(new BinaryTreeElement<Character>(ch,left,right));
			}
		}
		result.topEl = binElStack.pop();
		return result;
	}
	
	public int height()
	{
		return topEl.height();
	}
	
	public int nodesQuant ()
	{
		return topEl.nodesQuant();
	}
	
	public int lefasQuant ()
	{
		return topEl.leafsQuant();
	}
	
	public double calculate() throws InvalidCharacterExcpection
	{
		return topEl.calculate();
	}
	
	public String inorder() throws InvalidCharacterExcpection
	{
		return topEl.inorder();
	}
	
	public String postorder () throws InvalidCharacterExcpection
	{
		StringBuffer sb = new StringBuffer(topEl.postorder());
		sb.reverse();
		return sb.toString();
	}
}
