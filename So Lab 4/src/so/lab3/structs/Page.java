package so.lab3.structs;

public class Page {

	private static int counter = 0;
	private int id;

	public Page() {
		id = counter++;

	}

	public int getId() {
		return id;
	}

	public static void reset() {
		counter = 0;
	}

}
