package application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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

// Handles registration form actions.
public class RegistrationController {
	@FXML
	private TextField registerUserFirstNameTextField;
	@FXML
	private TextField registerUserLastNameTextField;
	@FXML
	private TextField registerUserUsernameTextField;
	@FXML
	private PasswordField registerUserPasswordField;
	@FXML
	private PasswordField registerConfirmPasswordField;
	@FXML
	private Label userRegistrationErrorLabel;
	@FXML
	private Label userRegistrationSuccessLabel;

	private Stage stage;
	private Scene scene;
	private HashMap<String, User> users;
	private static final String FILE_NAME = "src\\application\\files\\users.dat";

	public void userRegistration(ActionEvent event) {
		String firstName = registerUserFirstNameTextField.getText();
		String lastName = registerUserLastNameTextField.getText();
		String username = registerUserUsernameTextField.getText();
		String password = registerUserPasswordField.getText();
		String confirmPassword = registerConfirmPasswordField.getText();

		userRegistrationErrorLabel.setText("");
		userRegistrationSuccessLabel.setText("");

		users = readUsers();

		if (firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("")
				|| confirmPassword.equals("")) {
			userRegistrationErrorLabel.setText("Please fill all!");
		} else if (users.containsKey(username)) {
			userRegistrationErrorLabel.setText("Username already exists!");
		} else if (!(password.equals(confirmPassword))) {
			userRegistrationErrorLabel.setText("Password didn't match!");
		} else {
			User user = new User(username, firstName, lastName, password, "Regular");
			users.put(user.getUsername(), user);

			writeUsers(users);

			HashMap<String, User> users2 = readUsers();
			for (User user2 : users2.values()) {
				System.out.println(user2);
			}

			userRegistrationSuccessLabel.setText("Successfully registered!");
		}

	}

	public void adminRegistration(ActionEvent event) {
		String firstName = registerUserFirstNameTextField.getText();
		String lastName = registerUserLastNameTextField.getText();
		String username = registerUserUsernameTextField.getText();
		String password = registerUserPasswordField.getText();
		String confirmPassword = registerConfirmPasswordField.getText();

		userRegistrationErrorLabel.setText("");
		userRegistrationSuccessLabel.setText("");

		users = readUsers();

		if (firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("")
				|| confirmPassword.equals("")) {
			userRegistrationErrorLabel.setText("Please fill all!");
		} else if (users.containsKey(username)) {
			userRegistrationErrorLabel.setText("Username already exists!");
		} else if (!(password.equals(confirmPassword))) {
			userRegistrationErrorLabel.setText("Password didn't match!");
		} else {
			User user = new User(username, firstName, lastName, password, "Admin");
			users.put(user.getUsername(), user);

			writeUsers(users);

			HashMap<String, User> users2 = readUsers();
			for (User user2 : users2.values()) {
				System.out.println(user2);
			}

			userRegistrationSuccessLabel.setText("Successfully registered!");
		}

	}

	public void backToAdminDashboard(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
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

	public void backToUserLogin(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
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

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUsers() {
		users = new HashMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			if (new File(FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty user list.");
			} else {
				users = (HashMap<String, User>) reader.readObject(); // Reading the entire map
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found, initializing empty user list.");
		} catch (EOFException e) {
			System.out.println("Reached end of file, no users found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	private void writeUsers(HashMap<String, User> user2) {
		// TODO Auto-generated method stub
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			writer.writeObject(user2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
