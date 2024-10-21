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

public class RegistrationController {
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private TextField oldUsernameTextField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label successLabel;

	private Stage stage;
	private Scene scene;

	private FileManager fileManager = new FileManager();
	private TreeMap<String, User> users;
	private static final String FILE_NAME = "src\\application\\files\\users.dat";

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String confirmPassword;
	private String role;

	@FXML
	public void userRegistration(ActionEvent event) {
		register(event, "Regular");
	}

	@FXML
	public void adminRegistration(ActionEvent event) {
		register(event, "Admin");
	}

	private void register(ActionEvent event, String role) {
		firstName = firstNameTextField.getText();
		lastName = lastNameTextField.getText();
		username = usernameTextField.getText();
		password = passwordField.getText();
		confirmPassword = confirmPasswordField.getText();
		this.role = role;

		users = fileManager.readUsers();

		if (isInputFieldsEmpty()) {
			printError("Please fill all!");
		} else if (users.containsKey(username)) {
			printError("Username already exists!");
		} else if (!(password.equals(confirmPassword))) {
			printError("Password didn't match!");
		} else {
			User user = new User(username, firstName, lastName, password, role);
			users.put(user.getUsername(), user);

			fileManager.writeUsers(users);

			TreeMap<String, User> users2 = fileManager.readUsers();
			for (User user2 : users2.values()) {
				System.out.println(user2);
			}

			printSuccess(role.equals("Admin") ? user.getLastName() + " has been successfully added to the admin!"
					: "You have been successfully registered!");
		}
	}

	private boolean isInputFieldsEmpty() {
		return firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()
				|| confirmPassword.isEmpty();
	}

	@FXML
	void updateUsersInfo(ActionEvent event) {
		updateInfo();
	}

	@FXML
	void updateAdminInfo(ActionEvent event) {
		updateInfo();
	}

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

				User user = new User(newUsername, firstName, lastName, password, "Regular");
				users.remove(oldUsername);
				users.put(user.getUsername(), user);

				printSuccess("Info updated successfuly!");

				fileManager.writeUsers(users);

				users = fileManager.readUsers();
				for (User user2 : users.values()) {
					System.out.println(user2);
				}

			} else {
				printError("Username '" + oldUsername + "' doesn't exist!");
			}
		}
	}

	@FXML
	public void backToAdminDashboard(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
	}

	@FXML
	void backToUserDashBoard(ActionEvent event) {
		switchScene(event, "userDashBoardTest.fxml");
	}

	@FXML
	public void backToUserLogin(ActionEvent event) {
		switchScene(event, "userLogin.fxml");
	}

	private void switchScene(ActionEvent event, String fxmlFile) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
