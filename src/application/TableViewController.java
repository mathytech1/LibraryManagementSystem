package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController implements Initializable {

	@FXML
	private TableColumn<Book, String> bookIDCol;

	@FXML
	private TableColumn<Book, String> titleCol;

	@FXML
	private TableColumn<Book, String> authorCol;

	@FXML
	private TableColumn<Book, LocalDate> pubYearCol;

	@FXML
	private TableColumn<Book, String> availableCOl;

	@FXML
	private TableView<Book> tableView;
	private BookManagementController bm = new BookManagementController();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookIDCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		pubYearCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publichedYear"));
		availableCOl.setCellValueFactory(new PropertyValueFactory<Book, String>("isAvailable"));

		TreeMap<String, Book> storedBooks = bm.readBooks();
		ObservableList<Book> books = tableView.getItems();
		for (Book book : storedBooks.values()) {
			books.add(book);
		}

		tableView.setItems(books);
	}

}
