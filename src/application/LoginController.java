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

public class LoginController {
	@FXML
	private Label errorLabel;
	@FXML
	private Label successLabel;
	@FXML
	private Button loginButton;
	@FXML
	private Label loginlabel;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private TextField usernameTextField;

	private Stage stage;
	private Scene scene;

	private FileManager fileManager = new FileManager();
	private TreeMap<String, User> users;
	private static final String FILE_NAME = "src\\application\\files\\users.dat";
	private String currentLogin;

	@FXML
	void switchToAdminLogin(ActionEvent event) {
		switchScene(event, "adminLogin.fxml");
	}

	@FXML
	void switchToUserLogin(ActionEvent event) {
		switchScene(event, "userLogin.fxml");
	}

	@FXML
	void switchToUserRegistration(ActionEvent event) {
		switchScene(event, "userRegistration.fxml");
	}

	@FXML
	void userLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		users = fileManager.readUsers();

		if (users.containsKey(username) && password.equals(users.get(username).getPassword())
				&& users.get(username).getRole().equals("Regular")) {
			TreeMap<String, Book> books = fileManager.readBooks();
			for (Book book : books.values()) {
				System.out.println(book);
			}

			fileManager.writeLogin(username);
			switchScene(event, "userDashboardTest.fxml");

		} else {
			printEmptyField(username, password);
		}
	}

	@FXML
	void adminLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		users = fileManager.readUsers();

		if (users.containsKey(username) && password.equals(users.get(username).getPassword())
				&& (users.get(username).getRole().equals("Admin") || users.get(username).getRole().equals("Super"))) {

			// Save current Logged in admin to the file
			fileManager.writeLogin(username);
			switchScene(event, "adminDashboardTest.fxml");

			TreeMap<String, Book> books = fileManager.readBooks();
			for (Book book : books.values()) {
				System.out.println(book);
			}
		} else {
			printEmptyField(username, password);
		}
	}

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

			// As the user logout, we should clear current logged in data from the file
			fileManager.writeLogin("");
//			stage = (Stage) scenePane.getScene().getWindow();
//			System.out.println("Successfuly logout");
//			stage.close();
		}
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
