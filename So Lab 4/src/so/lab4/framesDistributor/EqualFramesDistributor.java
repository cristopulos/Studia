package so.lab4.framesDistributor;

import java.util.List;

import so.lab4.Thread;

public class EqualFramesDistributor extends ProportionalFrameDistributor {

	public EqualFramesDistributor(int framesQuant, List<Thread> runningThreads) {
		super(framesQuant, runningThreads);
	}

	@Override
	protected void computeFrameNumberForEachThread() {
		framesNumber = new int[threads.size()];
		diff = new int[threads.size()];
		for (int i = 0; i < threads.size(); i++) {
			framesNumber[i] = framesQuant / threads.size();
		}
	}

 	

}
