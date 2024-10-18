package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BookController implements Initializable {

	@FXML
	private Button adminAddBookButton;

	@FXML
	private TableColumn<Book, String> authorCol;

	@FXML
	private TableColumn<Book, String> availableCOl;

	@FXML
	private TableColumn<Book, String> bookIDCol;

	@FXML
	private Label bookNotFoundLabel;

	@FXML
	private TextField bookTitleTextField;

	@FXML
	private Button logoutButton;

	@FXML
	private TableColumn<Book, LocalDate> pubYearCol;

	@FXML
	private Button removeBookButton;

	@FXML
	private AnchorPane scenePane;

	@FXML
	private Button searchButton;

	@FXML
	private TableView<Book> tableView;

	@FXML
	private TableColumn<Book, String> titleCol;

	@FXML
	private Button updateBookButton;

	private TreeMap<String, Book> books;
	private final String FILE_NAME = "src\\application\\files\\books.dat";

	@FXML
	private Stage stage;

	@FXML
	private Scene scene;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookIDCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		pubYearCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publichedYear"));
		availableCOl.setCellValueFactory(new PropertyValueFactory<Book, String>("isAvailable"));

		tableView.getItems().clear();
		TreeMap<String, Book> sortedBooks = readBooks();
		ObservableList<Book> books = tableView.getItems();
		for (Book book : sortedBooks.values()) {
			books.add(book);
		}

		tableView.setItems(books);
	}

	@FXML
	void addBookPage(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("addBookForm.fxml"));
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
	}

	@FXML
	void logout(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Are you sure you want to exit?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			System.out.println("Successfuly logout");
			stage.close();
		}
	}

	@FXML
	void removeBook(MouseEvent event) {
		String title = tableView.getSelectionModel().getSelectedItem().getBookTitle();
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Remove");
//		alert.setHeaderText("You're about to remove a book!");
//		alert.setContentText("Are you sure you want to remove " + title + "?");

		int index = tableView.getSelectionModel().getSelectedIndex();
		tableView.getItems().remove(index);

		books = readBooks();
		books.remove(tableView.getSelectionModel().getSelectedItem().getBookID());
//		ArrayList<Book> list = new ArrayList<>();
//		for (Book book : books.values()) {
//			list.add(book);
//		}
//		list.remove(index);
//
//		TreeMap<String, Book> updatedBook = new TreeMap<>();
//		for (Book book : list) {
//			updatedBook.put(book.getBookID(), book);
//		}

		writeBooks(books);
	}

	@FXML
	void searchBook(MouseEvent event) {
		String bookTitle = bookTitleTextField.getText();

		bookNotFoundLabel.setText("");

		books = readBooks();

		if (bookTitle.equals("")) {
			bookNotFoundLabel.setText("Please Enter Book Title!");
		} else {
			int counter = 0;
			tableView.getItems().clear();
			for (Book book : books.values()) {
				if (book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
					ObservableList<Book> books = tableView.getItems();
					books.add(book);

					System.out.println(book);
					counter++;
				}
			}
			if (counter == 0) {
				bookNotFoundLabel.setText("Could not Find " + bookTitle);
			}
		}
	}

	@FXML
	void updateBook(MouseEvent event) {

	}

	@FXML
	void updateBookPage(ActionEvent event) {

	}

	@FXML
	void adminRefresh(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("adminDashboardTest.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, Book> readBooks() {
		books = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			books = (TreeMap<String, Book>) reader.readObject(); // Reading the entire map
		} catch (FileNotFoundException e) {
			// If the file doesn't exist, return an empty map
			System.out.println("File not found, initializing empty book list.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return books;
	}

	private void writeBooks(TreeMap<String, Book> books2) {
		// TODO Auto-generated method stub
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			writer.writeObject(books2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
