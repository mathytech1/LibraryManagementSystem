package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeMap;

import javafx.collections.ObservableList;
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

public class BookController {
	@FXML
	private TextField bookIdTextField;
	@FXML
	private TextField bookTitleTextField;
	@FXML
	private TextField bookAuthorTextField;
	@FXML
	private DatePicker bookPubYear;
	@FXML
	protected Label errorLabel;
	@FXML
	protected Label successLabel;
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
	@FXML
	protected ToggleGroup group;

	private Stage stage;
	private Scene scene;

	protected FileManager fileManager = new FileManager();
	protected TreeMap<String, Book> books;

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
				printSuccess("Book Added Successfuly!");

				books = fileManager.readBooks();
				for (Book book : books.values()) {
					System.out.println(book);
				}
			}

		} else {
			printError("Please fill all the boxes!");
		}
	}

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
				if (available.equals("Yes")) {
					available = "Available";
				} else {
					available = "Unavailable";
				}
			}

			Book book = new Book(bookID, bookTitle, bookAuthor, publishedYear);
			book.setAvailable(available);

			books.remove(book.getBookID());
			books.put(book.getBookID(), book);
			fileManager.writeBooks(books);

			System.out.println(bookID);
			System.out.println(bookTitle);
			System.out.println(bookAuthor);
			System.out.println(publishedYear);

			printSuccess("Book Updated Successfuly!");
		} else {
			printError("Could not find '" + bookID + "!");
		}
	}

	@FXML
	public void removeBook(MouseEvent event) {
		Book book = tableView.getSelectionModel().getSelectedItem();

		if (book == null) {
			printError("Please select a book first!");
		} else {
			String title = book.getBookTitle();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove");
			alert.setHeaderText("You're about to remove a book!");
			alert.setContentText("Are you sure you want to remove '" + title + "'?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				tableView.getItems().remove(book);
				books = fileManager.readBooks();
				books.remove(book.getBookID());
				fileManager.writeBooks(books);
				printSuccess(book.getBookTitle() + " is successfuly removed!");
			}
		}
	}

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
	void backToAdminDashboard(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
	}

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

	protected void printError(String error) {
		errorLabel.setText(error);
		successLabel.setText("");
	}

	protected void printSuccess(String success) {
		successLabel.setText(success);
		errorLabel.setText("");
	}
}
