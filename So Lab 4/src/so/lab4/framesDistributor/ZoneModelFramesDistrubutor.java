package so.lab4.framesDistributor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import so.lab3.structs.Request;
import so.lab4.Thread;
import so.lab4.exceptions.NoFramesToRemoveException;
import so.lab4.exceptions.NoFreeFramesException;

public class ZoneModelFramesDistrubutor extends FramesDistributor {

	List<List<Request>> threadsRequests;
	int[] desiredFramesQuant;
	int[] diffFromDesiredFrameSQuant;

	public ZoneModelFramesDistrubutor(int framesQuant,
			List<Thread> runningThreads) {
		super(framesQuant, runningThreads);
		threadsRequests = new ArrayList<List<Request>>();
		for (int i = 0; i < threads.size(); i++)
			threadsRequests.add(new ArrayList<Request>());
	}

	@Override
	protected void distributeFrames() throws NoFreeFramesException,
			NoFramesToRemoveException {
		for (int i = 0; i < threads.size(); i++) {

			Thread t = threads.get(i);
			desiredFramesQuant[i] = detrmineZoneSizeOfRequestList(threadsRequests
					.get(i));
			diffFromDesiredFrameSQuant[i] = desiredFramesQuant[i]
					- t.getFramesQuant();
		}
		removeExcessFrames();
		assignNeededFrames();

	}

	@Override
	protected void generateRequests() {

		for (int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
			threadsRequests.get(i).addAll(t.generateRequests());
		}
	}

	private void removeExcessFrames() throws NoFramesToRemoveException {
		for (int i = 0; i < threads.size(); i++) {
			if (diffFromDesiredFrameSQuant[i] < 0) {
				Thread t = threads.get(i);
				removeFrames(t, Math.abs(diffFromDesiredFrameSQuant[i]));
			}
		}
	}

	private void assignNeededFrames() throws NoFreeFramesException {
		for (int i = 0; i < threads.size(); i++) {
			if (diffFromDesiredFrameSQuant[i] > 0) {
				Thread t = threads.get(i);
				assignFrames(t, Math.min(freeFrames.size(),
						diffFromDesiredFrameSQuant[i]));
			}
		}
	}

	private int detrmineZoneSizeOfRequestList(List<Request> requestList) {
		Set<Integer> pageIdSet = new HashSet<Integer>();
		for (Request r : requestList) {
			pageIdSet.add(r.getPageId());
		}
		return pageIdSet.size();
	}

}
