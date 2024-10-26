package application;

import java.time.LocalDate;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

// Service class for book-related operations
public class BookService {
	@FXML
	protected Label errorLabel; // Label for displaying error messages
	@FXML
	protected Label successLabel; // Label for displaying success messages
	@FXML
	private TextField bookTitleTextField; // TextField for entering book title to search
	@FXML
	protected TableColumn<Book, String> bookIDCol; // Column for Book ID
	@FXML
	protected TableColumn<Book, String> titleCol; // Column for Book Title
	@FXML
	protected TableColumn<Book, String> authorCol; // Column for Book Author
	@FXML
	protected TableColumn<Book, String> availableCOl; // Column for Availability
	@FXML
	protected TableColumn<Book, LocalDate> pubYearCol; // Column for Publication Year
	@FXML
	protected TableView<Book> tableView; // TableView to display books

	protected FileManager fileManager = new FileManager(); // Manages reading/writing book data
	protected TreeMap<String, Book> books; // Stores book data
	private String currentLogin; // Current logged-in user's username
	private TreeMap<String, String> borrowedBooks = new TreeMap<>(); // Tracks borrowed books

	// Searches for a book based on title entered in the text field
	@FXML
	public void searchBook(MouseEvent event) {
		String bookTitle = bookTitleTextField.getText(); // Get book title from input field

		books = fileManager.readBooks(); // Read all books from the file

		if (bookTitle.isEmpty()) {
			printError("Please Enter Book Title!"); // Error if title is empty
		} else {
			int counter = 0; // Counter for matching books
			tableView.getItems().clear(); // Clear previous search results
			for (Book book : books.values()) {
				if (book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
					ObservableList<Book> books = tableView.getItems(); // Get observable list of books
					books.add(book); // Add matching book to the list

					System.out.println(book); // Print matching book details to console
					counter++;
				}
			}
			if (counter == 0) {
				printError("0 matches found for search '" + bookTitle + "'"); // No matches found
			}
		}
	}

	// Handles borrowing a selected book
	@FXML
	public void borrowBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem(); // Get selected book
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex(); // Get index of selected book

		if (selectedBook == null) {
			printError("Please select a book first!"); // Error if no book is selected
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("Unavailable")) {
				printError(selectedBook.getBookTitle() + " is unavailable"); // Error if book is unavailable
			} else {
				books = fileManager.readBooks(); // Read all books again to ensure up-to-date information

				selectedBook.setAvailable("Unavailable"); // Mark book as borrowed
				tableView.getItems().set(selectedIndex, selectedBook); // Update TableView
				printSuccess("You have borrowed " + selectedBook.getBookTitle()); // Success message

				books.remove(selectedBook.getBookID()); // Remove book from the map
				books.put(selectedBook.getBookID(), selectedBook); // Add updated book back to the map

				fileManager.writeBooks(books); // Write updated books back to the file

				currentLogin = fileManager.readLogin(); // Get current user's login information
				borrowedBooks.put(selectedBook.getBookID(), currentLogin); // Track borrowed book

				fileManager.writeBorrowedBooks(borrowedBooks); // Write updated borrowed books
			}
		}
	}

	// Handles returning a selected book
	@FXML
	public void returnBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem(); // Get selected book
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex(); // Get index of selected book

		currentLogin = fileManager.readLogin(); // Read current user's login
		borrowedBooks = fileManager.readBorrowedBooks(); // Read all borrowed books
		books = fileManager.readBooks(); // Read all books

		if (selectedBook == null) {
			printError("Please select a book to be returned!"); // Error if no book is selected
		} else {
			if (borrowedBooks.containsKey(selectedBook.getBookID())
					&& borrowedBooks.get(selectedBook.getBookID()).equals(currentLogin)) {
				selectedBook.setAvailable("Available"); // Mark book as available again
				tableView.getItems().set(selectedIndex, selectedBook); // Update TableView

				books.remove(selectedBook.getBookID()); // Remove book from the map
				books.put(selectedBook.getBookID(), selectedBook); // Add updated book back to the map
				fileManager.writeBooks(books); // Write updated books back to the file
				borrowedBooks.remove(selectedBook.getBookID()); // Remove book from borrowed list
				fileManager.writeBorrowedBooks(borrowedBooks); // Write updated borrowed books
				printSuccess("You have returned " + selectedBook.getBookTitle()); // Success message
			} else {
				printError("You didn't borrow " + selectedBook.getBookTitle()); // Error if user didn't borrow this book
			}
		}
	}

	// Displays error message
	protected void printError(String error) {
		errorLabel.setText(error); // Set error label text
		successLabel.setText(""); // Clear success label
	}

	// Displays success message
	protected void printSuccess(String success) {
		successLabel.setText(success); // Set success label text
		errorLabel.setText(""); // Clear error label
	}
}
