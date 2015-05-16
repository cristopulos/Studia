package so5.util;

public class Counter {
	
	private static int queryQuant = 0;
	private static int transferQuant = 0;
	
	public static void reset()
	{
		queryQuant = 0;
		transferQuant = 0;
	}
	
	public static void reportQuery()
	{
		queryQuant++;
	}
	
	public static void reportTransfer()
	{
		transferQuant++;
	}

	public static int getQueryQuant() {
		return queryQuant;
	}

	public static int getTransferQuant() {
		return transferQuant;
	}

	
}
