package application;

import java.io.Serializable;
import java.time.LocalDate;

// Represents a book in the library system with relevant details like title, author, publication year, and availability.
public class Book implements Serializable {
	private static final long serialVersionUID = 1L; // Ensures compatibility for serialization
	private String bookID; // Unique identifier for the book
	private String bookTitle; // Title of the book
	private String author; // Author of the book
	private LocalDate publichedYear; // Year the book was published
	private String isAvailable; // Availability status of the book; defaults to "Unavailable"

	// Constructor to initialize a new book with all essential information
	public Book(String bookID, String bookTitle, String author, LocalDate publichedYear) {
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publichedYear = publichedYear;
		this.isAvailable = "Available"; // By default, a newly added book is marked as "Available"
	}

	// Getter for bookID
	public String getBookID() {
		return bookID;
	}

	// Getter and setter for bookTitle
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	// Getter and setter for author
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	// Getter and setter for published year
	public LocalDate getPublichedYear() {
		return publichedYear;
	}

	public void setPublichedYear(LocalDate publichedYear) {
		this.publichedYear = publichedYear;
	}

	// Getter and setter for availability status
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	// Override toString to provide a readable representation of a Book instance
	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", bookTitle=" + bookTitle + ", author=" + author + ", publichedYear="
				+ publichedYear + ", isAvailable=" + isAvailable + "]";
	}
}
