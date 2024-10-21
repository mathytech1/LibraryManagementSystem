package application;

import java.io.EOFException;
import java.io.File;
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
import javafx.scene.control.PasswordField;
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

	@FXML
	private Label errorLabel;

	@FXML
	private Label successLabel;

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

	private TreeMap<String, Book> books;
	private TreeMap<String, User> users;
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

		successLabel.setText("");
		errorLabel.setText("");

		Book book = tableView.getSelectionModel().getSelectedItem();
		if (book == null) {
			errorLabel.setText("Error! No book selected!");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove");
			alert.setHeaderText("You're about to remove a book!");
			alert.setContentText("Are you sure you want to remove '" + title + "'?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				tableView.getItems().remove(book);
				books = readBooks();
				books.remove(book.getBookID());
				writeBooks(books);
				successLabel.setText(book.getBookTitle() + " is successfuly removed!");
			}
		}
	}

	@FXML
	void searchBook(MouseEvent event) {
		String bookTitle = bookTitleTextField.getText();

		errorLabel.setText("");
		successLabel.setText("");

		books = readBooks();

		if (bookTitle.equals("")) {
			errorLabel.setText("Please Enter Book Title!");
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
				errorLabel.setText("0 matchs found for search '" + bookTitle + "'");
			}
		}
	}

//	@FXML
//	void updateBook(MouseEvent event) {
//		String bookID = 
//	}

	@FXML
	void updateBookPage(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("updateBookForm.fxml"));
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
	public void returnBookPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("returnBookForm.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
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

	@FXML
	void userRefresh(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userDashboardTest.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void borrowBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		errorLabel.setText("");
		successLabel.setText("");

		if (selectedBook == null) {
			errorLabel.setText("Please select a book first!");
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("Unavailable")) {
				errorLabel.setText(selectedBook.getBookTitle() + " is unavailable");
			} else {
				books = readBooks();

				selectedBook.setAvailable("Unavailable");
				tableView.getItems().set(selectedIndex, selectedBook);
				successLabel.setText("You have borrowed " + selectedBook.getBookTitle());

				books.remove(selectedBook.getBookID());
				books.put(selectedBook.getBookID(), selectedBook);

				writeBooks(books);
			}
		}
	}

	@FXML
	public void returnBook(MouseEvent event) {
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		errorLabel.setText("");
		successLabel.setText("");

		if (selectedBook == null) {
			errorLabel.setText("Plz select the book to be returned!");
		} else {
			if (selectedBook.getIsAvailable().equalsIgnoreCase("available")) {
				errorLabel.setText("You didn't borrow " + selectedBook.getBookTitle());
			} else {
				selectedBook.setAvailable("Available");
				tableView.getItems().set(selectedIndex, selectedBook);
				errorLabel.setText("You have returned " + selectedBook.getBookTitle());
				books = readBooks();
				books.remove(selectedBook.getBookID());
				books.put(selectedBook.getBookID(), selectedBook);
				writeBooks(books);
			}
		}
	}

	@FXML
	void updateStudentInfoPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("updateStudentInfoForm.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void backToUserDashBoard(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userDashboardTest.fxml"));
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

	@SuppressWarnings("unchecked")
	public TreeMap<String, User> readUsers() {
		users = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			if (new File(FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty user list.");
			} else {
				users = (TreeMap<String, User>) reader.readObject(); // Reading the entire map
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

	private void writeUsers(TreeMap<String, User> user2) {
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
