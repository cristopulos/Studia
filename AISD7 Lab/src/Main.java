import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import aisd.sorts.BubbleSort;
import aisd.sorts.HeapSort;
import aisd.sorts.InsertSort;
import aisd.sorts.MergeSort;
import aisd.sorts.QuickSort;
import aisd.sorts.SelectSort;
import aisd.sorts.ShellSort;
import aisd.util.NumberGenerator;

public class Main {

	
	public static void reverse(int[] arr)
	{
		for(int i = 0; i < arr.length / 2; i++)
		{
		    int temp = arr[i];
		    arr[i] = arr[arr.length - i - 1];
		    arr[arr.length - i - 1] = temp;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter("out.txt");
		int [] arrToSort= NumberGenerator.generateArray(200000, 200000);
		int [] copy =  Arrays.copyOf(arrToSort, arrToSort.length);
		BubbleSort bubble = new BubbleSort();
		InsertSort insert = new InsertSort();
		SelectSort select = new SelectSort();
		MergeSort merge = new MergeSort();
		QuickSort quick = new QuickSort();
		HeapSort heap = new HeapSort();
		ShellSort shell = new ShellSort();
		
		
		pw.printf("| nazwa algorytmu: | tablica nieposortowana : | tablica posortowana : | tablica odwrotnie posortowana : |%n");
		pw.printf("| %16s |","Babelkowy");
		pw.printf(" %24d |", bubble.sortArray(copy));
		pw.printf(" %21d |", bubble.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",bubble.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Insert");
		pw.printf(" %24d |", insert.sortArray(copy));
		pw.printf(" %21d |", insert.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",insert.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Select");
		pw.printf(" %24d |", select.sortArray(copy));
		pw.printf(" %21d |", select.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",select.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Merge");
		pw.printf(" %24d |", merge.sortArray(copy));
		pw.printf(" %21d |", merge.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",merge.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Quick");
		pw.printf(" %24d |", quick.sortArray(copy));
		pw.printf(" %21d |", quick.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",quick.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Heap");
		pw.printf(" %24d |", heap.sortArray(copy));
		pw.printf(" %21d |", heap.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",heap.sortArray(copy));
		
		copy = Arrays.copyOf(arrToSort, arrToSort.length);
		pw.printf("| %16s |","Shell");
		pw.printf(" %24d |", shell.sortArray(copy));
		pw.printf(" %21d |", shell.sortArray(copy));
		reverse(copy);
		pw.printf(" %31d |%n",shell.sortArray(copy));
		
		pw.close();
		
	
		
		
	}
}
