package application.exceptions;

// Thrown when a user attempts to borrow a book that’s unavailable.
public class BookNotAvailableException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotAvailableException(String message) {
		super(message);
	}

}
