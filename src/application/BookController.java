package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// Controller for managing books within the application
public class BookController {
	@FXML
	private TextField bookIdTextField; // TextField for Book ID
	@FXML
	private TextField bookTitleTextField; // TextField for Book Title
	@FXML
	private TextField bookAuthorTextField; // TextField for Book Author
	@FXML
	private DatePicker bookPubYear; // DatePicker for Book Published Year
	@FXML
	protected Label errorLabel; // Label for displaying error messages
	@FXML
	protected Label successLabel; // Label for displaying success messages
	@FXML
	protected TableColumn<Book, String> bookIDCol; // Table column for Book ID
	@FXML
	protected TableColumn<Book, String> titleCol; // Table column for Book Title
	@FXML
	protected TableColumn<Book, String> authorCol; // Table column for Book Author
	@FXML
	protected TableColumn<Book, String> availableCOl; // Table column for Book Availability
	@FXML
	protected TableColumn<Book, LocalDate> pubYearCol; // Table column for Published Year
	@FXML
	protected TableView<Book> tableView; // TableView for displaying books
	@FXML
	protected ToggleGroup group; // ToggleGroup for book availability (Yes/No)

	private Stage stage; // Reference to primary stage
	private Scene scene; // Scene object to hold new scene content

	protected FileManager fileManager = new FileManager(); // Manages file reading and writing
	protected TreeMap<String, Book> books; // Stores book data

	// Adds a new book based on user input
	@FXML
	public void addBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();
		String bookTitle = bookTitleTextField.getText();
		String bookAuthor = bookAuthorTextField.getText();
		LocalDate publishedYear = bookPubYear.getValue();

		if (!bookID.isEmpty() && !bookTitle.isEmpty() && !bookAuthor.isEmpty() && publishedYear != null) {
			books = fileManager.readBooks();
			Book newBook = new Book(bookID, bookTitle, bookAuthor, publishedYear);

			if (books.containsKey(newBook.getBookID())) {
				printError("Book ID already exists!");
			} else {
				books.put(newBook.getBookID(), newBook);
				fileManager.writeBooks(books);
				printSuccess("Book Added Successfully!");

				// Display updated list of books
				books = fileManager.readBooks();
				for (Book book : books.values()) {
					System.out.println(book);
				}
			}

		} else {
			printError("Please fill all the boxes!");
		}
	}

	// Updates an existing book's details
	@FXML
	public void updateBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();
		String bookTitle = bookTitleTextField.getText();
		String bookAuthor = bookAuthorTextField.getText();
		LocalDate publishedYear = bookPubYear.getValue();
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		String available;

		books = fileManager.readBooks();

		if (books.containsKey(bookID)) {
			if (bookTitle.isEmpty()) {
				bookTitle = books.get(bookID).getBookTitle();
			}
			if (bookAuthor.isEmpty()) {
				bookAuthor = books.get(bookID).getAuthor();
			}
			if (publishedYear == null) {
				publishedYear = books.get(bookID).getPublichedYear();
			}
			if (selectedRadioButton == null) {
				available = books.get(bookID).getIsAvailable();
			} else {
				available = selectedRadioButton.getText();
				available = available.equals("Yes") ? "Available" : "Unavailable";
			}

			Book book = new Book(bookID, bookTitle, bookAuthor, publishedYear);
			book.setAvailable(available);

			// Update the book in the TreeMap
			books.put(book.getBookID(), book);
			fileManager.writeBooks(books);

			printSuccess("Book Updated Successfully!");
		} else {
			printError("Could not find '" + bookID + "'!");
		}
	}

	// Removes the selected book from the system
	@FXML
	public void removeBook(MouseEvent event) {
		Book book = tableView.getSelectionModel().getSelectedItem();

		if (book == null) {
			printError("Please select a book first!");
		} else {
			String title = book.getBookTitle();

			// Confirmation dialog for deletion
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove");
			alert.setHeaderText("You're about to remove a book!");
			alert.setContentText("Are you sure you want to remove '" + title + "'?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				tableView.getItems().remove(book); // Remove from TableView
				books = fileManager.readBooks();
				books.remove(book.getBookID()); // Remove from TreeMap
				fileManager.writeBooks(books); // Write updated data back to file
				printSuccess(title + " successfully removed!");
			}
		}
	}

	// Searches for books by title in the stored data
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
					tableView.getItems().add(book);
					counter++;
				}
			}
			if (counter == 0) {
				printError("0 matches found for search '" + bookTitle + "'");
			}
		}
	}

	// Navigates back to the admin dashboard
	@FXML
	void backToAdminDashboard(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
	}

	// Switches to a new scene
	void switchScene(ActionEvent event, String fxmlFile) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			printError("Couldn't locate the URL!");
		}
	}

	// Displays error messages to the user
	protected void printError(String error) {
		errorLabel.setText(error);
		successLabel.setText("");
	}

	// Displays success messages to the user
	protected void printSuccess(String success) {
		successLabel.setText(success);
		errorLabel.setText("");
	}
}
