package so.lab4.framesDistributor;

import java.util.List;

import so.lab4.Thread;
import so.lab4.exceptions.NoFramesToRemoveException;
import so.lab4.exceptions.NoFreeFramesException;
import so.lab4.exceptions.RequestsNotGeneratedException;

public class ErrorFrequencyFramesDistrtibutor extends FramesDistributor {

	private int[] threadPageErrors;
	private int minErrors;
	private int maxErrors;

	public ErrorFrequencyFramesDistrtibutor(int framesQuant,
			List<Thread> runningThreads) {
		super(framesQuant, runningThreads);
		threadPageErrors = new int[runningThreads.size()];
	}

	public void setMinErrors(int minErrors) {
		this.minErrors = minErrors;
	}

	public void setMaxErrors(int maxErrors) {
		this.maxErrors = maxErrors;
	}

	@Override
	protected void distributeFrames() throws NoFreeFramesException,
			NoFramesToRemoveException {
		if (firstCall()) {
			int frames = framesQuant / threads.size();
			for (Thread t : threads) {
				assignFrames(t, frames);
			}
		} else {
			getUnusedFrames();
			assignFramesWhereNeeded();
		}
	}

	private void getUnusedFrames() throws NoFramesToRemoveException {
		for (int i = 0; i < threadPageErrors.length; i++) {
			if (threadPageErrors[i] <= minErrors) {
				Thread t = threads.get(i);
				double framesInThread = t.getFramesQuant();
				int framesToTake = (int) Math.ceil(framesInThread / 10.0);
				removeFrames(t, framesToTake);
			}
		}
	}

	private void assignFramesWhereNeeded() throws NoFreeFramesException {
		for (int i = 0; i < threads.size(); i++) {
			if(threadPageErrors[i]>=maxErrors)
			{
				Thread t = threads.get(i);
				double framesInThread = t.getFramesQuant();
				int framesQuantToAssign = Math.min(freeFrames.size(),(int) Math.ceil(framesInThread / 10.0));
				assignFrames(t, framesQuantToAssign);
				
			}
		}
	}

	@Override
	public void processRequests() throws RequestsNotGeneratedException {

		for (int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
			processedRequests += t.getRequestQuant();
			threadPageErrors[i] = t.processRequests();
			pageErrors += threadPageErrors[i];
		}
	}

	private boolean firstCall() {
		for (int i = 0; i < threadPageErrors.length; i++) {
			if (threadPageErrors[i] != 0)
				return false;
		}
		return true;
	}

}
