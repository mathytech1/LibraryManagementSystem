package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button loginButton;
	@FXML
	private Label loginErrorLabel;

	private Stage stage;
	private Scene scene;

	public void switchToUserLogin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToAdminLogin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToUserRegistration(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userRegistration.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void userLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		if (username.equals("") && password.equals("")) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("userDashboard.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setResizable(false);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (username.equals("") && password.equals("")) {
				loginErrorLabel.setText("Enter Credentials pls!");
			} else if (username.equals("")) {
				loginErrorLabel.setText("Enter username pls!");
			} else if (password.equals("")) {
				loginErrorLabel.setText("Enter password pls");
			} else {
				loginErrorLabel.setText("Incorrect Credentials!");
			}

		}
	}

	public void adminLogin(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		if (username.equals("a") && password.equals("")) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setResizable(false);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (username.equals("") && password.equals("")) {
				loginErrorLabel.setText("Enter Credentials pls!");
			} else if (username.equals("")) {
				loginErrorLabel.setText("Enter username pls!");
			} else if (password.equals("")) {
				loginErrorLabel.setText("Enter password pls");
			} else {
				loginErrorLabel.setText("Incorrect Credentials!");
			}

		}
	}
}
