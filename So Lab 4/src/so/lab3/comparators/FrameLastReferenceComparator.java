package so.lab3.comparators;

import java.util.Comparator;

import so.lab3.structs.Frame;

public class FrameLastReferenceComparator implements Comparator<Frame> {

	@Override
	public int compare(Frame o1, Frame o2) {
		if(o1.getLastPageReferenceTime()==o2.getLastPageReferenceTime())
			return 0;
		else
			return (o1.getLastPageReferenceTime() < o2.getLastPageReferenceTime() ? 1 : -1);
	}


}
