package so.lab3.structs;


public class Frame {

	private Page page = null;
	private int loadedTime;
	private int lastReferenceTime;
	private boolean wasRecentlyUsed;

	public Frame() {
		loadedTime = 0;
		lastReferenceTime = 0;
		wasRecentlyUsed = false;
	}

	public void pageUsed() {
		lastReferenceTime = Timer.currentTime();
		wasRecentlyUsed = true;
	}

	public void loadPage(Page p) {
		if (page != null)
			unloadPage();
		lastReferenceTime = Timer.currentTime();
		page = p;
		loadedTime = Timer.currentTime();
	}

	private void unloadPage() {
		page = null;
		wasRecentlyUsed = false;
	}

	public void setRecUsedToFalse() {
		wasRecentlyUsed = false;
	}

	public boolean isRecentlyUsed() {
		return wasRecentlyUsed;
	}

	public int getPageTimeInMemory() {
		return Timer.currentTime() - loadedTime;
	}

	public int getLastPageReferenceTime() {
		return lastReferenceTime;
	}

	public int getPageId() {
		return page.getId();
	}

	public Page getPage() {
		return page;
	}
}
