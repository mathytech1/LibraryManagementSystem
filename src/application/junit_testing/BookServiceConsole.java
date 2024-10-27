package application.junit_testing;

import java.util.TreeMap;

import application.Book;
import application.FileManager;

public class BookServiceConsole {
	private FileManager fileManager;
	private TreeMap<String, Book> books;
	private String currentLogin;
	private TreeMap<String, String> borrowedBooks;

	public String borrowBook(Book selectedB) {
		Book selectedBook = selectedB;

		if (selectedBook == null) {
			return "Please select a book first!"; // Error if no book is selected
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("Unavailable")) {
				return selectedBook.getBookTitle() + " is unavailable"; // Error if book is unavailable
			} else {
				books = fileManager.readBooks(); // Read all books again to ensure up-to-date information

				selectedBook.setAvailable("Unavailable"); // Mark book as borrowed

				books.remove(selectedBook.getBookID()); // Remove book from the map
				books.put(selectedBook.getBookID(), selectedBook); // Add updated book back to the map

				fileManager.writeBooks(books); // Write updated books back to the file

				currentLogin = fileManager.readLogin(); // Get current user's login information
				borrowedBooks.put(selectedBook.getBookID(), currentLogin); // Track borrowed book

				fileManager.writeBorrowedBooks(borrowedBooks); // Write updated borrowed books

				return "You have borrowed " + selectedBook.getBookTitle(); // Success message
			}
		}
	}
}
