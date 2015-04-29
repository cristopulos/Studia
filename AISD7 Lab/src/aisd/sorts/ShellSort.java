package aisd.sorts;

public class ShellSort extends SortAlg {

	public void sort(int[] arr) {
		int increment = arr.length / 2;
		while (increment > 0) {
			for (int i = increment; i < arr.length; i++) {
				int j = i;
				int temp = arr[i];
				while (j >= increment && arr[j - increment] > temp) {
					arr[j] = arr[j - increment];
					j = j - increment;
				}
				arr[j] = temp;
			}
			if (increment == 2) {
				increment = 1;
			} else {
				increment *= (5.0 / 11);
			}
		}
	}

}
