package aisd9.huffmanTree;

public class MorseHelper implements Comparable<MorseHelper>{

	String code;
	char ch;
	public MorseHelper(String code, char c) {
		this.code = code;
		ch = c;
	}
	@Override
	public int compareTo(MorseHelper o) {
		return (int) Math.signum(code.length() - o.code.length());
	}
	
	

}
