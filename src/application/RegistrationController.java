package application;

import java.io.IOException;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Controller for handling user and admin registration, updates, and navigation within the application.
public class RegistrationController {
	@FXML
	private TextField firstNameTextField; // TextField for user's first name
	@FXML
	private TextField lastNameTextField; // TextField for user's last name
	@FXML
	private TextField usernameTextField; // TextField for user's username
	@FXML
	private PasswordField passwordField; // PasswordField for user's password
	@FXML
	private PasswordField confirmPasswordField; // PasswordField for confirming user's password
	@FXML
	private TextField oldUsernameTextField; // TextField for entering the username to be updated
	@FXML
	private Label errorLabel; // Label for displaying error messages
	@FXML
	private Label successLabel; // Label for displaying success messages

	private Stage stage; // Stage for scene management
	private Scene scene; // Scene to be switched to

	private FileManager fileManager = new FileManager(); // FileManager instance for reading/writing user data
	private TreeMap<String, User> users; // Collection of users loaded from file
	private static final String FILE_NAME = "src\\application\\files\\users.dat"; // Path for user data file

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String confirmPassword;
	private String role;

	// Registers a regular user
	@FXML
	public void userRegistration(ActionEvent event) {
		register(event, "Regular");
	}

	// Registers an admin user
	@FXML
	public void adminRegistration(ActionEvent event) {
		register(event, "Admin");
	}

	// Handles user registration logic for both regular and admin users
	private void register(ActionEvent event, String role) {
		firstName = firstNameTextField.getText();
		lastName = lastNameTextField.getText();
		username = usernameTextField.getText();
		password = passwordField.getText();
		confirmPassword = confirmPasswordField.getText();
		this.role = role;

		users = fileManager.readUsers();

		// Validate input fields and attempt to add the new user
		if (isInputFieldsEmpty()) {
			printError("Please fill all!");
		} else if (users.containsKey(username)) {
			printError("Username already exists!");
		} else if (!(password.equals(confirmPassword))) {
			printError("Password didn't match!");
		} else {
			// Create new user and add to the users collection
			User user = new User(username, firstName, lastName, password, role);
			users.put(user.getUsername(), user);

			// Write updated users to file
			fileManager.writeUsers(users);

			// Verify by reading and printing users
			TreeMap<String, User> users2 = fileManager.readUsers();
			for (User user2 : users2.values()) {
				System.out.println(user2);
			}

			// Display success message based on user role
			printSuccess(role.equals("Admin") ? user.getLastName() + " has been successfully added to the admin!"
					: "You have been successfully registered!");
		}
	}

	// Checks if any input fields required for registration are empty
	private boolean isInputFieldsEmpty() {
		return firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()
				|| confirmPassword.isEmpty();
	}

	// Updates the information of a regular user
	@FXML
	void updateUsersInfo(ActionEvent event) {
		updateInfo();
	}

	// Updates the information of an admin user
	@FXML
	void updateAdminInfo(ActionEvent event) {
		updateInfo();
	}

	// Handles user information updates for both regular and admin users
	private void updateInfo() {
		String oldUsername = oldUsernameTextField.getText();
		String newUsername = usernameTextField.getText();
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		if (oldUsername.isEmpty()) {
			printError("Please enter old username!");
		} else if (!password.equals(confirmPassword)) {
			printError("password didn't match");
		} else {
			users = fileManager.readUsers();

			if (users == null) {
				printError("Sorry, No data found!");
			}
			if (users.containsKey(oldUsername)) {
				// Assign previous values if fields are empty
				if (newUsername.isEmpty()) {
					newUsername = users.get(oldUsername).getUsername();
				}
				if (firstName.isEmpty()) {
					firstName = users.get(oldUsername).getFirstName();
				}
				if (lastName.isEmpty()) {
					lastName = users.get(oldUsername).getLastName();
				}
				if (password.isEmpty()) {
					password = users.get(oldUsername).getPassword();
				}

				// Create updated user and replace the old record
				User user = new User(newUsername, firstName, lastName, password, "Regular");
				users.remove(oldUsername);
				users.put(user.getUsername(), user);

				printSuccess("Info updated successfully!");

				fileManager.writeUsers(users);

				// Verify by reading and printing updated users
				users = fileManager.readUsers();
				for (User user2 : users.values()) {
					System.out.println(user2);
				}

			} else {
				printError("Username '" + oldUsername + "' doesn't exist!");
			}
		}
	}

	// Navigates back to the admin dashboard view
	@FXML
	public void backToAdminDashboard(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
	}

	// Navigates back to the user dashboard view
	@FXML
	void backToUserDashBoard(ActionEvent event) {
		switchScene(event, "userDashBoardTest.fxml");
	}

	// Navigates back to the user login view
	@FXML
	public void backToUserLogin(ActionEvent event) {
		switchScene(event, "userLogin.fxml");
	}

	// Helper method to switch between scenes based on an FXML file
	private void switchScene(ActionEvent event, String fxmlFile) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace in case of an I/O error
		}
	}

	// Displays an error message in the errorLabel
	protected void printError(String error) {
		errorLabel.setText(error);
		successLabel.setText("");
	}

	// Displays a success message in the successLabel
	protected void printSuccess(String success) {
		successLabel.setText(success);
		errorLabel.setText("");
	}
}
