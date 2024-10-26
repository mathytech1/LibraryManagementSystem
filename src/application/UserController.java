package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// Controller for user interactions
public class UserController extends BookService implements Initializable {
	@FXML
	private TextField oldUsernameTextField; // TextField for the old username
	@FXML
	private TextField usernameTextField; // TextField for new username
	@FXML
	private TextField firstNameTextField; // TextField for first name
	@FXML
	private TextField lastNameTextField; // TextField for last name
	@FXML
	private PasswordField passwordField; // PasswordField for new password
	@FXML
	private PasswordField confirmPasswordField; // PasswordField to confirm the new password
	@FXML
	private AnchorPane scenePane; // Pane to hold scene elements

	private Stage stage; // Primary stage reference
	private Scene scene; // Scene object reference

	private FileManager fileManager = new FileManager(); // Manages reading/writing user data
	private TreeMap<String, User> users; // Stores user data

	// Initializes the controller when the UI is loaded
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTableColumns(); // Set up columns in the TableView
		loadBooksIntoTableView(); // Load books into the TableView
	}

	// Sets cell value factories for each column (Assuming tableView is defined in
	// BookService)
	private void initializeTableColumns() {
		bookIDCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID")); // Book ID column
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle")); // Title column
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author")); // Author column
		pubYearCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publichedYear")); // Publication year
																									// column
		availableCOl.setCellValueFactory(new PropertyValueFactory<Book, String>("isAvailable")); // Availability column
	}

	// Loads books from the file and displays them in the TableView
	private void loadBooksIntoTableView() {
		tableView.getItems().clear(); // Clear existing items
		TreeMap<String, Book> sortedBooks = fileManager.readBooks(); // Read books from file
		ObservableList<Book> books = tableView.getItems(); // Get observable list of books
		for (Book book : sortedBooks.values()) {
			books.add(book); // Add each book to the observable list
		}

		tableView.setItems(books); // Set the list in the TableView
	}

	// Inherited searchBook method from BookService
	@FXML
	@Override
	public void searchBook(MouseEvent event) {
		super.searchBook(event); // Call parent search method
	}

	// Inherited borrowBook method from BookService
	@Override
	@FXML
	public void borrowBook(MouseEvent event) {
		super.borrowBook(event); // Call parent borrow method
	}

	// Inherited returnBook method from BookService
	@FXML
	@Override
	public void returnBook(MouseEvent event) {
		super.returnBook(event); // Call parent return method
	}

	// Navigates to the student info update page
	@FXML
	void updateStudentInfoPage(ActionEvent event) {
		switchScene(event, "updateStudentInfoForm.fxml"); // Switch to the update page
	}

	// Refreshes the user dashboard
	@FXML
	void userRefresh(ActionEvent event) {
		switchScene(event, "userDashboardTest.fxml"); // Switch to user dashboard
	}

	// Scene switching logic
	void switchScene(ActionEvent event, String fxmlFile) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile)); // Load new scene
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get current stage
			scene = new Scene(root); // Create new scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Add styles
			stage.setResizable(false); // Prevent resizing
			stage.setScene(scene); // Set new scene
			stage.show(); // Show new scene
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace for debugging
			printError("Couldn't locate the URL!"); // Error message
		}
	}

	// Logout functionality
	@FXML
	void logout(ActionEvent event) throws IOException {
		LoginController logController = new LoginController(); // Create instance of LoginController
		logController.logout(event); // Call logout method
	}
}
