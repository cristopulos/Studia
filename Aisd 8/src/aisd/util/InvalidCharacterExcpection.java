package aisd.util;

public class InvalidCharacterExcpection extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046183284920379871L;
	public String statement;
	public InvalidCharacterExcpection(String statement) {
		this.statement = statement;
	}

}
