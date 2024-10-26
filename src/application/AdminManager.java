package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// Controller for managing administrative users
public class AdminManager extends RegistrationController implements Initializable {
	@FXML
	protected TableColumn<User, String> usernameCol; // Column for displaying username
	@FXML
	protected TableColumn<User, String> firstNameCol; // Column for first name
	@FXML
	protected TableColumn<User, String> lastNameCol; // Column for last name
	@FXML
	protected TableColumn<User, String> roleCol; // Column for role (e.g., admin, super admin)
	@FXML
	protected TableView<User> tableView; // TableView to display user data
	@FXML
	private TextField firstNameTextField; // TextField for first name input
	@FXML
	private TextField lastNameTextField; // TextField for last name input
	@FXML
	private TextField usernameTextField; // TextField for username input
	@FXML
	private TextField passwordField; // TextField for password input
	@FXML
	private TextField confirmPasswordField; // TextField to confirm password input
	@FXML
	private Label errorLabel; // Label for error messages
	@FXML
	private Label successLabel; // Label for success messages

	private Stage stage; // Primary stage reference
	private Scene scene; // Scene object reference

	private FileManager fileManager = new FileManager(); // Manages reading/writing user data
	private TreeMap<String, User> users; // Stores user data

	// Initializes the controller when the UI is loaded
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTableColumns(); // Set up columns in the TableView
		loadBooksIntoTableView(); // Load users into the TableView
	}

	// Sets cell value factories for each column
	private void initializeTableColumns() {
		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
	}

	// Loads users from the file and displays them in the TableView
	private void loadBooksIntoTableView() {
		tableView.getItems().clear(); // Clear existing items
		TreeMap<String, User> sortedUsers = fileManager.readUsers(); // Read users from file
		ObservableList<User> obsUsers = tableView.getItems();
		for (User user : sortedUsers.values()) {
			obsUsers.add(user); // Add each user to the observable list
		}

		tableView.setItems(obsUsers); // Set the list in the TableView
	}

	// Registers a new admin user and refreshes the TableView
	@Override
	@FXML
	public void adminRegistration(ActionEvent event) {
		super.adminRegistration(event); // Call parent registration method
		loadBooksIntoTableView(); // Refresh TableView to show new user
	}

	// Removes a user selected from the TableView
	@FXML
	void removeUser(MouseEvent event) {
		User user = tableView.getSelectionModel().getSelectedItem(); // Get selected user

		if (user == null) {
			printError("Please select a user first!"); // Error if no user selected
		} else if (user.getRole().equals("Super")) {
			printError("You cannot remove a super admin!"); // Prevent removal of super admin
		} else {
			String lastName = user.getLastName(); // Store last name for message

			// Confirmation dialog before removal
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove");
			alert.setHeaderText("You're about to remove a user!");
			alert.setContentText("Are you sure you want to remove '" + lastName + "'?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				tableView.getItems().remove(user); // Remove user from TableView
				users = fileManager.readUsers();
				users.remove(user.getUsername()); // Remove user from data
				fileManager.writeUsers(users); // Write updated data back to file
				printSuccess(lastName + " is successfully removed!"); // Success message
			}
		}
	}

	// Navigates back to the admin dashboard
	@Override
	@FXML
	public void backToAdminDashboard(ActionEvent event) {
		super.backToAdminDashboard(event); // Call method from parent class
	}
}
