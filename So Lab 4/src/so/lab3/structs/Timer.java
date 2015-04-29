package so.lab3.structs;


public class Timer {

	private static int time = 0;

	public static void incrementTime() {
		time++;
	}

	public static void reset() {
		time = 0;
	}

	public static int currentTime() {
		return time;
	}

}
