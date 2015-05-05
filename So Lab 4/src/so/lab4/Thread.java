package so.lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import so.lab3.structs.Frame;
import so.lab3.structs.Page;
import so.lab3.structs.Request;
import so.lab3.structs.Timer;
import so.lab4.exceptions.RequestsNotGeneratedException;
import so.lab4.lruApprox.LRUApproxFrameManagingAlgorithm;

public class Thread {

	private List<Page> pages;
	private int range = 0;
	private int startingPage = 0;
	private int firstPageID;
	private int pagesQuant;
	private LRUApproxFrameManagingAlgorithm alg;
	private List<Request> requestList;
	private boolean wasGenerated;
	private int requestQuant;

	public Thread(int pagesQuant) {
		this.pagesQuant = pagesQuant;
		pages = new ArrayList<Page>();
		Page p = new Page();
		firstPageID = p.getId();
		pages.add(p);
		for (int i = 1; i < pagesQuant; i++) {
			pages.add(new Page());
		}
		alg = new LRUApproxFrameManagingAlgorithm(pages, new ArrayList<Frame>());
		wasGenerated = false;
	}

	public int getPagesQuant() {
		return pagesQuant;
	}

	public int getRequestQuant() {
		return requestQuant;
	}
	
	public int getFramesQuant()
	{
		return alg.getPhysicMemorySize();
	}

	public List<Request> generateRequests() {
		List<Request> requests = new ArrayList<Request>();
		Random rand = new Random();
		startingPage += rand.nextInt(5) - 2;
		startingPage = Math.max(firstPageID, startingPage);
		startingPage = Math.min(pagesQuant + firstPageID - 1, startingPage);
		range += rand.nextInt(5) - 2;
		if (range <= 0)
			range = rand.nextInt(pagesQuant / 4) + 1;
		if (startingPage + range >= pagesQuant + firstPageID)
			range = pages.size() + firstPageID - startingPage;
		int quant = rand.nextInt(5);
		for (int i = 0; i < quant; i++) {
			int pageNumber;
			do {
				pageNumber = rand.nextInt(range) + startingPage;
			} while (pageNumber >= pagesQuant + firstPageID);
			requests.add(new Request(pageNumber, Timer.currentTime()));
		}
		requestList = new ArrayList<Request>(requests);
		requestQuant = requests.size();
		wasGenerated = true;
		return requests;
	}

	public int processRequests() throws RequestsNotGeneratedException {
		if (wasGenerated) {
			wasGenerated = false;
			return alg.processRequests(requestList);
		} else
			throw new RequestsNotGeneratedException();
	}

	public void addFrame(Frame F) {
		alg.addFrame(F);
	}

	public Frame removeFrame() {
		return alg.removeFrame();
	}

}
