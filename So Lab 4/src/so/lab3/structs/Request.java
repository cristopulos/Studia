package so.lab3.structs;

public class Request {

	private int pageId;
	private int timeReported;
	private boolean errorOccured = false;

	public Request(int pageId, int timeReported) {
		this.pageId = pageId;
		this.timeReported = timeReported;
	}
	
	public Request (Request r)
	{
		pageId = r.getPageId();
		timeReported = r.getTimeReported();
	}

	public int getPageId() {
		return pageId;
	}

	public int getTimeReported() {
		return timeReported;
	}

	public void reportError() {
		errorOccured = true;
	}

	public boolean isErrorReported() {
		return errorOccured;
	}

}
