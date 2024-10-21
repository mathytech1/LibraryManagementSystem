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

public class BookService {
	@FXML
	protected Label errorLabel;
	@FXML
	protected Label successLabel;
	@FXML
	private TextField bookTitleTextField;
	@FXML
	protected TableColumn<Book, String> bookIDCol;
	@FXML
	protected TableColumn<Book, String> titleCol;
	@FXML
	protected TableColumn<Book, String> authorCol;
	@FXML
	protected TableColumn<Book, String> availableCOl;
	@FXML
	protected TableColumn<Book, LocalDate> pubYearCol;
	@FXML
	protected TableView<Book> tableView;

	protected FileManager fileManager = new FileManager();
	protected TreeMap<String, Book> books;

	@FXML
	public void searchBook(MouseEvent event) {
		String bookTitle = bookTitleTextField.getText();

		books = fileManager.readBooks();

		if (bookTitle.isEmpty()) {
			printError("Please Enter Book Title!");
		} else {
			int counter = 0;
			tableView.getItems().clear();
			for (Book book : books.values()) {
				if (book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
					ObservableList<Book> books = tableView.getItems();
					books.add(book);

					System.out.println(book);
					counter++;
				}
			}
			if (counter == 0) {
				printError("0 matchs found for search '" + bookTitle + "'");
			}
		}
	}

	@FXML
	public void borrowBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		if (selectedBook == null) {
			printError("Please select a book first!");
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("Unavailable")) {
				printError(selectedBook.getBookTitle() + " is unavailable");
			} else {
				books = fileManager.readBooks();

				selectedBook.setAvailable("Unavailable");
				tableView.getItems().set(selectedIndex, selectedBook);
				printSuccess("You have borrowed " + selectedBook.getBookTitle());

				books.remove(selectedBook.getBookID());
				books.put(selectedBook.getBookID(), selectedBook);

				fileManager.writeBooks(books);
			}
		}
	}

	@FXML
	public void returnBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		if (selectedBook == null) {
			printError("Please select a book to be returned!");
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("available")) {
				printError("You didn't borrow " + selectedBook.getBookTitle());
			} else {
				selectedBook.setAvailable("Available");
				tableView.getItems().set(selectedIndex, selectedBook);

				books = fileManager.readBooks();
				books.remove(selectedBook.getBookID());
				books.put(selectedBook.getBookID(), selectedBook);
				fileManager.writeBooks(books);
				printSuccess("You have returned " + selectedBook.getBookTitle());
			}
		}
	}

	protected void printError(String error) {
		errorLabel.setText(error);
		successLabel.setText("");
	}

	protected void printSuccess(String success) {
		successLabel.setText(success);
		errorLabel.setText("");
	}
}
