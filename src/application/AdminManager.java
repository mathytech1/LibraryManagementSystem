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

public class AdminManager extends RegistrationController implements Initializable {
	@FXML
	protected TableColumn<User, String> usernameCol;
	@FXML
	protected TableColumn<User, String> firstNameCol;
	@FXML
	protected TableColumn<User, String> lastNameCol;
	@FXML
	protected TableColumn<User, String> roleCol;
	@FXML
	protected TableView<User> tableView;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField confirmPasswordField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label successLabel;

	private Stage stage;
	private Scene scene;

	private FileManager fileManager = new FileManager();
	private TreeMap<String, User> users;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTableColumns();
		loadBooksIntoTableView();
	}

	private void initializeTableColumns() {
		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
	}

	private void loadBooksIntoTableView() {
		tableView.getItems().clear();
		TreeMap<String, User> sortedUsers = fileManager.readUsers();
		ObservableList<User> obsUsers = tableView.getItems();
		for (User user : sortedUsers.values()) {
			obsUsers.add(user);
		}

		tableView.setItems(obsUsers);
	}

	@Override
	@FXML
	public void adminRegistration(ActionEvent event) {
		super.adminRegistration(event);
		loadBooksIntoTableView();
	}

	@FXML
	void removeUser(MouseEvent event) {
		User user = tableView.getSelectionModel().getSelectedItem();

		if (user == null) {
			printError("Please select a user first!");
		} else if (user.getRole().equals("Super")) {
			printError("You cannot remove a super admin!");
		} else {
			String lastName = user.getLastName();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove");
			alert.setHeaderText("You're about to remove a user!");
			alert.setContentText("Are you sure you want to remove '" + lastName + "'?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				tableView.getItems().remove(user);
				users = fileManager.readUsers();
				users.remove(user.getUsername());
				fileManager.writeUsers(users);
				printSuccess(user.getLastName() + " is successfuly removed!");
			}
		}
	}

	@Override
	@FXML
	public void backToAdminDashboard(ActionEvent event) {
		super.backToAdminDashboard(event);
	}
}
