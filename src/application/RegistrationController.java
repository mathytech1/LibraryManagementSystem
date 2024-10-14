package application;

import java.io.IOException;

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

	public void userRegistration(ActionEvent event) {
		String firstName = registerUserFirstNameTextField.getText();
		String lastName = registerUserLastNameTextField.getText();
		String username = registerUserUsernameTextField.getText();
		String password = registerUserPasswordField.getText();
		String confirmPassword = registerConfirmPasswordField.getText();

		userRegistrationErrorLabel.setText("");
		userRegistrationSuccessLabel.setText("");

		if (firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("")
				|| confirmPassword.equals("")) {
			userRegistrationErrorLabel.setText("Please fill all!");
		} else if (username.equals("mathy")) {
			userRegistrationErrorLabel.setText("Username already exists!");
		} else if (!(password.equals(confirmPassword))) {
			userRegistrationErrorLabel.setText("Password didn't match!");
		} else {
			userRegistrationSuccessLabel.setText("Successfully registered!");
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

}
