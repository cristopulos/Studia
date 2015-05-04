package so.lab4.framesDistributor;

import java.util.List;

import so.lab4.Thread;
import so.lab4.exceptions.NoFreeFramesException;

public class ProportionalFrameDistributor extends FramesDistributor {

	int[] framesNumber;
	int[] diff;

	public ProportionalFrameDistributor(int framesQuant,
			List<Thread> runningThreads) {
		super(framesQuant, runningThreads);
	}

	@Override
	protected void distributeFrames() throws NoFreeFramesException {
		computeFrameNumberForEachThread();
		relocateFrames();
	}

	protected void relocateFrames() throws NoFreeFramesException {
		removeExcessFrames();
		distributeMissingFrames();
	}

	protected void computeFrameNumberForEachThread() {
		int pagesQuant = 0;
		for (Thread t : threads) {
			pagesQuant += t.getPagesQuant();
		}

		framesNumber = new int[threads.size()];
		diff = new int[threads.size()];
		for (int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
			framesNumber[i] = (int) Math.round(framesQuant
					* ((double) t.getPagesQuant() / pagesQuant));
		}
	}

	protected void removeExcessFrames() {
		for (int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
			for (diff[i] = t.getFramesQuant() - framesNumber[i]; diff[i] > 0; diff[i]--) {
				freeFrames.add(t.removeFrame());
			}
		}
	}

	protected void distributeMissingFrames() throws NoFreeFramesException {
		for (int i = 0; i < threads.size(); i++) {
			if (diff[i] < 0) {
				Thread t = threads.get(i);
				assignFrames(t, Math.abs(diff[i]));
			}
		}
	}
}
