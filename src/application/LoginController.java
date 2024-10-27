package application;

import java.io.IOException;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Controller for managing user and admin login, logout, and scene transitions
public class LoginController {
	@FXML
	private Label errorLabel; // Label for displaying error messages
	@FXML
	private Label successLabel; // Label for displaying success messages
	@FXML
	private Button loginButton; // Button to initiate login
	@FXML
	private Label loginlabel; // Label indicating login status or instructions
	@FXML
	private PasswordField passwordTextField; // Field for user password input
	@FXML
	private TextField usernameTextField; // Field for user username input

	private Stage stage; // Stage object for scene transitions
	private Scene scene; // Scene to be displayed

	private FileManager fileManager = new FileManager(); // Handles file operations
	private TreeMap<String, User> users; // Stores user data loaded from file
	private static final String FILE_NAME = "src\\application\\files\\users.dat"; // Path to user data file
	private String currentLogin; // Tracks the currently logged-in username

	// Switches to admin login screen
	@FXML
	void switchToAdminLogin(ActionEvent event) {
		switchScene(event, "adminLogin.fxml");
	}

	// Switches to user login screen
	@FXML
	void switchToUserLogin(ActionEvent event) {
		switchScene(event, "userLogin.fxml");
	}

	// Switches to user registration screen
	@FXML
	void switchToUserRegistration(ActionEvent event) {
		switchScene(event, "userRegistration.fxml");
	}

	// Processes user login, checks credentials, and directs to user dashboard if
	// valid
	@FXML
	public void userLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		users = fileManager.readUsers();

		// Validate user credentials and role
		if (users.containsKey(username) && password.equals(users.get(username).getPassword())
				&& users.get(username).getRole().equals("Regular")) {
			// Print books to console (for debug or logging purposes)
			TreeMap<String, Book> books = fileManager.readBooks();
			for (Book book : books.values()) {
				System.out.println(book);
			}

			// Record successful login and proceed to user dashboard
			fileManager.writeLogin(username);
			switchScene(event, "userDashboardTest.fxml");

		} else {
			printEmptyField(username, password); // Handle and display errors if login fails
		}
	}

	// Processes admin login, verifies credentials, and directs to admin dashboard
	// if valid
	@FXML
	void adminLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		users = fileManager.readUsers();

		// Validate admin credentials and role
		if (users.containsKey(username) && password.equals(users.get(username).getPassword())
				&& (users.get(username).getRole().equals("Admin") || users.get(username).getRole().equals("Super"))) {

			// Record current admin login and proceed to admin dashboard
			fileManager.writeLogin(username);
			switchScene(event, "adminDashboardTest.fxml");

			// Print books to console (for debug or logging purposes)
			TreeMap<String, Book> books = fileManager.readBooks();
			for (Book book : books.values()) {
				System.out.println(book);
			}
		} else {
			printEmptyField(username, password); // Handle and display errors if login fails
		}
	}

	// Displays appropriate error messages if any login fields are left empty or
	// incorrect
	void printEmptyField(String username, String password) {
		if (username.isEmpty() && password.isEmpty()) {
			printError("Enter Credentials pls!");
		} else if (username.isEmpty()) {
			errorLabel.setText("Enter username pls!");
		} else if (password.isEmpty()) {
			errorLabel.setText("Enter password pls");
		} else {
			errorLabel.setText("Incorrect Credentials!");
		}
	}

	// Handles logout functionality and prompts confirmation before exiting
	public void logout(ActionEvent event) throws IOException {
		currentLogin = fileManager.readLogin();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText(currentLogin + ", Are you sure you want to exit?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();

			// Clear logged-in user data after successful logout
			fileManager.writeLogin("");
		}
	}

	// Switches between different scenes within the application
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
			printError("Couldn't locate the URL!"); // Display error if scene file not found
		}
	}

	// Displays error messages in the errorLabel
	protected void printError(String error) {
		errorLabel.setText(error);
		successLabel.setText("");
	}

	// Displays success messages in the successLabel
	protected void printSuccess(String success) {
		successLabel.setText(success);
		errorLabel.setText("");
	}

}
