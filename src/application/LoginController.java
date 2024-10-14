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
	private TextField userUsernameTextField;
	@FXML
	private TextField adminUsernameTextField;
	@FXML
	private PasswordField userPasswordTextField;
	@FXML
	private PasswordField adminPasswordTextField;
	@FXML
	private Button userLoginButton;
	@FXML
	private Button adminLoginButton;
	@FXML
	private Label userLoginErrorLabel;
	@FXML
	private Label adminLoginErrorLabel;

	private Stage stage;
	private Scene scene;

	public void switchToUserLogin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void switchToAdminLogin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void switchToUserRegistration(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userRegistration.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void userLogin(ActionEvent event) {
		String username = userUsernameTextField.getText();
		String password = userPasswordTextField.getText();
		if (username.equals("user") && password.equals("1234")) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("userDashboard.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (username.equals("") && password.equals("")) {
				userLoginErrorLabel.setText("Enter Credentials pls!");
			} else if (username.equals("")) {
				userLoginErrorLabel.setText("Enter username pls!");
			} else if (password.equals("")) {
				userLoginErrorLabel.setText("Enter password pls");
			} else {
				userLoginErrorLabel.setText("Incorrect Credentials!");
			}

		}
	}

	public void adminLogin(ActionEvent event) {
		String username = adminUsernameTextField.getText();
		String password = adminPasswordTextField.getText();
		if (username.equals("a") && password.equals("")) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (username.equals("") && password.equals("")) {
				adminLoginErrorLabel.setText("Enter Credentials pls!");
			} else if (username.equals("")) {
				adminLoginErrorLabel.setText("Enter username pls!");
			} else if (password.equals("")) {
				adminLoginErrorLabel.setText("Enter password pls");
			} else {
				adminLoginErrorLabel.setText("Incorrect Credentials!");
			}

		}
	}

}
