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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController extends BookController implements Initializable {
	@FXML
	private AnchorPane scenePane;
	private Stage stage;
	private Scene scene;

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
	public void addBookPage(ActionEvent event) {
		switchScene(event, "addBookForm.fxml");
	}

	@FXML
	void updateBookPage(ActionEvent event) {
		switchScene(event, "updateBookForm.fxml");
	}

	@FXML
	void manageAdminsPage(ActionEvent event) {
		switchScene(event, "adminRegistration.fxml");
	}

	@FXML
	void logout(ActionEvent event) {
		LoginController logController = new LoginController();
		logController.logout(event, scenePane);
	}

	@FXML
	void adminRefresh(ActionEvent event) {
		switchScene(event, "adminDashboardTest.fxml");
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

}
