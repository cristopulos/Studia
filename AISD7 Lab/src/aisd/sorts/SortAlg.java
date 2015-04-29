package aisd.sorts;

public abstract class SortAlg {

	abstract void sort (int[] arr);
	public long sortArray (int[] arr)
	{
		long start = System.currentTimeMillis();
		sort(arr);
		long end = System.currentTimeMillis();
		return end - start;
	}
}
