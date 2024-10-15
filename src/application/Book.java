package application;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L; //
	private String bookID;
	private String bookTitle;
	private String author;
	private LocalDate publichedYear;
	private boolean isAvailable;

	public Book(String bookID, String bookTitle, String author, LocalDate publichedYear) {
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publichedYear = publichedYear;
		this.isAvailable = true;
	}

	public String getBookID() {
		return bookID;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getPublichedYear() {
		return publichedYear;
	}

	public void setPublichedYear(LocalDate publichedYear) {
		this.publichedYear = publichedYear;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", bookTitle=" + bookTitle + ", author=" + author + ", publichedYear="
				+ publichedYear + ", isAvailable=" + isAvailable + "]";
	}

}
