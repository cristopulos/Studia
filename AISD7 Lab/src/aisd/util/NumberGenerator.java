package aisd.util;

import java.util.Random;

public class NumberGenerator {
	public static int[] generateArray (int size, int maxNumber)
	{
		Random rand = new Random(1337);
		int[] tab =  new int[size];
		for(int i=0; i<size; i++)
		{
			tab[i] = rand.nextInt(maxNumber);
		}
		
		return tab;
	}

}
