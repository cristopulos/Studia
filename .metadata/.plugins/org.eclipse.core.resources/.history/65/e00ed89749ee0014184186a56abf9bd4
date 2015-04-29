package so.lab4.lruApprox;

import java.util.Collections;
import java.util.List;

import so.lab3.comparators.FrameLastReferenceComparator;
import so.lab3.structs.Frame;
import so.lab3.structs.Request;

public class LRUApproxFrameManbagingAlgorithm extends FramesManagingAlgorithm {

	public LRUApproxFrameManbagingAlgorithm(int pagesQuant, int framesQuant, List<Request> reqList) {
		super(pagesQuant, framesQuant, reqList);
	}

	@Override
	protected Frame chooseFrameToSwapPages() {
		Collections.sort(physicMemory, new FrameLastReferenceComparator());
		Frame frameToChoose = null;
		while (frameToChoose == null) {
			Frame f = physicMemory.get(0);
			if (f.isRecentlyUsed()) {
				f.setRecUsedToFalse();
				Collections.swap(physicMemory, 0, physicMemory.size() - 1);
			} else
				frameToChoose = f;
		}
		return frameToChoose;
	}

}
