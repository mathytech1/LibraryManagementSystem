package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookManagementController {
	@FXML
	private TextField bookIdTextField;
	@FXML
	private TextField bookTitleTextField;
	@FXML
	private TextField bookAuthorTextField;
	@FXML
	private DatePicker bookPubYear;
	@FXML
	private Label bookAddErrorLabel;
	@FXML
	private Label bookAddSuccess;
	@FXML
	private Label bookNotFoundLabel;
	@FXML
	private Label bookFoundLabel;

	private Stage stage;
	private Scene scene;
	private HashMap<String, Book> books;
	private final String FILE_NAME = "src\\application\\files\\books.dat";

	public void addBookPage(ActionEvent event) {
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

	public void addBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();
		String bookTitle = bookTitleTextField.getText();
		String bookAuthor = bookAuthorTextField.getText();
		LocalDate publishedYear = bookPubYear.getValue();

		bookAddSuccess.setText("");
		bookAddErrorLabel.setText("");

		if (!bookID.equals("") && !bookTitle.equals("") && !bookAuthor.equals("") && publishedYear != null) {
			books = readBooks();
			Book newBook = new Book(bookID, bookTitle, bookAuthor, publishedYear);

			if (books.containsKey(newBook.getBookID())) {
				bookAddErrorLabel.setText("Book ID already exists!");
			} else {
				books.put(newBook.getBookID(), newBook);
				writeBooks(books);
				bookAddSuccess.setText("Book Added Successfuly!");

				books = readBooks();
				for (Book book : books.values()) {
					System.out.println(book);
				}

//				System.out.println(bookID);
//				System.out.println(bookTitle);
//				System.out.println(bookAuthor);
//				System.out.println(publishedYear);
			}

		} else {
			bookAddErrorLabel.setText("Please fill all the boxes!");
		}
	}

	public void updateBookPage(ActionEvent event) {
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

	public void updateBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();
		String bookTitle = bookTitleTextField.getText();
		String bookAuthor = bookAuthorTextField.getText();
		LocalDate publishedYear = bookPubYear.getValue();

		bookAddSuccess.setText("");
		bookAddErrorLabel.setText("");

		if (bookID.equals("S0001")) {
			bookAddSuccess.setText("Book Updated Successfuly!");
			bookAddSuccess.setText("Book Added Successfuly!");
			System.out.println(bookID);
			System.out.println(bookTitle);
			System.out.println(bookAuthor);
			System.out.println(publishedYear);
		} else {
			bookAddErrorLabel.setText("Could not find book ID!");
		}
	}

	public void searchBookPage(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("searchBookForm.fxml"));
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

	public void searchBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();

		bookFoundLabel.setText("");
		bookNotFoundLabel.setText("");

		books = readBooks();

		if (bookID.equals("")) {
			bookNotFoundLabel.setText("Please Enter Book ID!");
		} else if (books.containsKey(bookID)) {
			bookFoundLabel.setText("Book Found!.");
			Book book = books.get(bookID);
			System.out.println(book);
		} else {
			bookNotFoundLabel.setText("Could not Find " + bookID);
		}
	}

	public void returnBookPage(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("returnBookForm.fxml"));
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

	public void returnBook(ActionEvent event) {
		String bookID = bookIdTextField.getText();
		System.out.println("Book returned: " + bookID);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooks() {
		books = new HashMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			books = (HashMap<String, Book>) reader.readObject(); // Reading the entire map
		} catch (FileNotFoundException e) {
			// If the file doesn't exist, return an empty map
			System.out.println("File not found, initializing empty book list.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return books;
	}

	private void writeBooks(HashMap<String, Book> books2) {
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

	public void backToAdminDashBoard(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void backToUserDashBoard(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userDashboard.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
}
