package so.lab4.framesDistributor;

import java.util.ArrayList;
import java.util.List;

import so.lab3.structs.Frame;
import so.lab3.structs.Page;
import so.lab3.structs.Timer;
import so.lab4.Thread;
import so.lab4.exceptions.NoFramesToRemoveException;
import so.lab4.exceptions.NoFreeFramesException;
import so.lab4.exceptions.RequestsNotGeneratedException;

public abstract class FramesDistributor {
	int framesQuant;
	List<Thread> threads;
	int processedRequests;
	int pageErrors;
	List<Frame> freeFrames;

	public FramesDistributor(int framesQuant, List<Thread> runningThreads) {
		freeFrames = new ArrayList<Frame>();
		this.framesQuant = framesQuant;
		for (int i = 0; i < framesQuant; i++)
			freeFrames.add(new Frame());
		threads = new ArrayList<Thread>(runningThreads);
		processedRequests = 0;
		pageErrors = 0;
	}

	public double runSimulation(int time) throws RequestsNotGeneratedException,
			NoFreeFramesException, NoFramesToRemoveException {
		while (Timer.currentTime() <= time) {
			if (Timer.currentTime() % 10 == 0)
				distributeFrames();
			generateRequests();
			processRequests();
			Timer.incrementTime();
		}
		resetsStaticVars();
		return (double) pageErrors / processedRequests;
	}

	protected void generateRequests() {
		for (Thread t : threads) {
			t.generateRequests();
		}
	}

	protected void processRequests() throws RequestsNotGeneratedException {
		for (Thread t : threads) {
			processedRequests += t.getRequestQuant();
			pageErrors += t.processRequests();
		}
	}

	protected abstract void distributeFrames() throws NoFreeFramesException, NoFramesToRemoveException;

	public void resetsStaticVars() {
		Timer.reset();
		Page.reset();
	}

	protected void assignFrames(Thread t, int quant)
			throws NoFreeFramesException {
		for (int i = 0; i < quant; i++) {
			if (!freeFrames.isEmpty()) {
				t.addFrame(freeFrames.remove(0));
			} else
				throw new NoFreeFramesException();
		}
	}

	protected void removeFrames(Thread t, int quant) throws NoFramesToRemoveException {
		for (int i = 0; i < quant; i++) {
			if(freeFrames.size()<=1)
				throw new NoFramesToRemoveException();
			Frame f;
			f = t.removeFrame();
			
			freeFrames.add(f);
		}
	}

}
