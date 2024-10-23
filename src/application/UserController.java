package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserController extends BookService implements Initializable {
	@FXML
	private TextField oldUsernameTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private AnchorPane scenePane;

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
		bookIDCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		pubYearCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publichedYear"));
		availableCOl.setCellValueFactory(new PropertyValueFactory<Book, String>("isAvailable"));
	}

	private void loadBooksIntoTableView() {
		tableView.getItems().clear();
		TreeMap<String, Book> sortedBooks = fileManager.readBooks();
		ObservableList<Book> books = tableView.getItems();
		for (Book book : sortedBooks.values()) {
			books.add(book);
		}

		tableView.setItems(books);
	}

	@FXML
	@Override
	public void searchBook(MouseEvent event) {
		super.searchBook(event);
	}

	@Override
	@FXML
	public void borrowBook(MouseEvent event) {
		super.borrowBook(event);
	}

	@FXML
	@Override
	public void returnBook(MouseEvent event) {
		super.returnBook(event);
	}

	@FXML
	void updateStudentInfoPage(ActionEvent event) {
		switchScene(event, "updateStudentInfoForm.fxml");
	}

	@FXML
	void userRefresh(ActionEvent event) {
		switchScene(event, "userDashboardTest.fxml");
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

	@FXML
	void logout(ActionEvent event) throws IOException {
		LoginController logController = new LoginController();
		logController.logout(event);
	}
}
