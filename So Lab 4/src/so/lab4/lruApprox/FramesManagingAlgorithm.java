package so.lab4.lruApprox;

import java.util.LinkedList;
import java.util.List;

import so.lab3.structs.Frame;
import so.lab3.structs.Page;
import so.lab3.structs.Request;

public abstract class FramesManagingAlgorithm {
	protected List<Frame> physicMemory;
	protected List<Page> virtualMemory;
	protected List<Request> requestList;
	private int pageErrors;

	public FramesManagingAlgorithm(int pagesQuant, int framesQuant,
			List<Request> reqList) {
		requestList = new LinkedList<Request>();
		for (Request r : reqList) {
			requestList.add(new Request(r));
		}
		physicMemory = new LinkedList<Frame>();
		virtualMemory = new LinkedList<Page>();
		for (int i = 0; i < pagesQuant; i++) {
			virtualMemory.add(new Page());
		}
		for (int i = 0; i < framesQuant; i++) {
			physicMemory.add(new Frame());
		}
		pageErrors = 0;
	}

	public FramesManagingAlgorithm(List<Page> pages, List<Frame> frames) {
		requestList = new LinkedList<Request>();
		physicMemory = new LinkedList<Frame>(frames);
		virtualMemory = new LinkedList<Page>(pages);
		pageErrors = 0;
	}
	
	public int getPhysicMemorySize ()
	{
		return physicMemory.size();
	}

	public int getPageErrors() {
		return pageErrors;
	}

	public int processRequests(List<Request> requests) {
		pageErrors = 0;
		for (Request r : requests) {
			requestList.add(new Request(r));
		}
		while (!requestList.isEmpty()) {
			processNextRequest();
		}
		return pageErrors;
	}

	public void addFrame(Frame F) {
		physicMemory.add(F);
	}

	public Frame removeFrame() {
		return physicMemory.remove(0);
	}

	private void processNextRequest() {
		Request r = requestList.remove(0);
		int requestId = r.getPageId();
		if (!isInMemmory(requestId)) {
			registerPageError();
			r.reportError();
			Frame f = chooseFrameToSwapPages();
			f.loadPage(getPageWithId(requestId));
		} else
			getFrameContainingPageWithId(requestId).pageUsed();
	}

	private Page getPageWithId(int id) {
		for (Page p : virtualMemory) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	protected abstract Frame chooseFrameToSwapPages();

	private void registerPageError() {
		pageErrors++;
	}

	private boolean isInMemmory(int id) {
		for (Frame f : physicMemory) {
			if (f.getPage().getId() == id)
				return true;
		}
		return false;
	}

	protected Frame getFrameContainingPageWithId(int id) {
		for (Frame f : physicMemory) {
			if (f.getPageId() == id)
				return f;
		}
		return null;
	}

}
