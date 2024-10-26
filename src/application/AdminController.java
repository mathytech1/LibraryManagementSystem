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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// Controller for admin-specific actions, including managing books, admins, and switching between pages
public class AdminController extends BookController implements Initializable {
	@FXML
	private AnchorPane scenePane; // Pane for holding the scene
	private Stage stage; // Reference to the primary stage for scene switching
	private Scene scene; // Scene object to hold new scene content

	// Initialize method called when the admin dashboard is loaded
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTableColumns(); // Set up table column bindings
		loadBooksIntoTableView(); // Populate table with book data from file
	}

	// Binds table columns to properties in the Book class for displaying book
	// details
	private void initializeTableColumns() {
		bookIDCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		pubYearCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publichedYear"));
		availableCOl.setCellValueFactory(new PropertyValueFactory<Book, String>("isAvailable"));
	}

	// Reads book data from file and loads it into the TableView
	private void loadBooksIntoTableView() {
		tableView.getItems().clear(); // Clear existing items from table
		TreeMap<String, Book> sortedBooks = fileManager.readBooks(); // Read books from file
		ObservableList<Book> books = tableView.getItems();

		for (Book book : sortedBooks.values()) {
			books.add(book); // Add each book to the TableView's items list
		}
		tableView.setItems(books); // Refresh the table view with the new data
	}

	// Navigates to the page for adding new books
	@FXML
	public void addBookPage(ActionEvent event) {
		switchScene(event, "addBookForm.fxml");
	}

	// Navigates to the page for updating existing books
	@FXML
	void updateBookPage(ActionEvent event) {
		switchScene(event, "updateBookForm.fxml");
	}

	// Navigates to the page for managing admin accounts
	@FXML
	void manageAdminsPage(ActionEvent event) {
		switchScene(event, "adminRegistration.fxml");
	}

	// Calls the logout function from LoginController to handle admin logout
	@FXML
	void logout(ActionEvent event) throws IOException {
		LoginController logController = new LoginController();
		logController.logout(event);
	}

	// Refreshes the admin dashboard by reloading the admin dashboard scene
	@FXML
	void adminRefresh(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
	}

	// Generalized method to handle scene transitions within the admin interface
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
			printError("Couldn't locate the URL!"); // Display error message if FXML file not found
		}
	}

}
