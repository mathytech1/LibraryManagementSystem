package application;

import java.util.Date;

public class Book {
	private String bookID;
	private String bookTitle;
	private String author;
	private Date publichedYear;
	private boolean isAvailable;

	public Book(String bookID, String bookTitle, String author, Date publichedYear, boolean isAvailable) {
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publichedYear = publichedYear;
		this.isAvailable = isAvailable;
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

	public Date getPublichedYear() {
		return publichedYear;
	}

	public void setPublichedYear(Date publichedYear) {
		this.publichedYear = publichedYear;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
