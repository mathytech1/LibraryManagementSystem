package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
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
	private Label errorLabel;
	@FXML
	private Label successLabel;

	@FXML
	private AnchorPane scenePane;
	@FXML
	private RadioButton yesRadioButton;
	@FXML
	private RadioButton noRadioButton;
	@FXML
	private ToggleGroup group;
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

	private Stage stage;
	private Scene scene;
	private TreeMap<String, Book> books;
	private TreeMap<String, User> users;
	private RegistrationController registrationController;
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

		errorLabel.setText("");
		successLabel.setText("");

		if (!bookID.equals("") && !bookTitle.equals("") && !bookAuthor.equals("") && publishedYear != null) {
			books = readBooks();
			Book newBook = new Book(bookID, bookTitle, bookAuthor, publishedYear);

			if (books.containsKey(newBook.getBookID())) {
				errorLabel.setText("Book ID already exists!");
			} else {
				books.put(newBook.getBookID(), newBook);
				writeBooks(books);
				successLabel.setText("Book Added Successfuly!");

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
			errorLabel.setText("Please fill all the boxes!");
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
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		String available;

		errorLabel.setText("");
		successLabel.setText("");

		books = readBooks();

		if (books.containsKey(bookID)) {
			if (bookTitle.equals("")) {
				bookTitle = books.get(bookID).getBookTitle();
			}
			if (bookAuthor.equals("")) {
				bookAuthor = books.get(bookID).getAuthor();
			}
			if (publishedYear == null) {
				publishedYear = books.get(bookID).getPublichedYear();
			}
			if (selected == null) {
				available = books.get(bookID).getIsAvailable();
			} else {
				available = selected.getText();
				if (available.equals("Yes")) {
					available = "Available";
				} else {
					available = "Unavailable";
				}
			}

			Book book = new Book(bookID, bookTitle, bookAuthor, publishedYear);
			book.setAvailable(available);

			books.remove(bookID);
			books.put(book.getBookID(), book);
			writeBooks(books);

			System.out.println(bookID);
			System.out.println(bookTitle);
			System.out.println(bookAuthor);
			System.out.println(publishedYear);

			successLabel.setText("Book Updated Successfuly!");
		} else {
			errorLabel.setText("Could not find '" + bookID + "!");
		}
	}

	@FXML
	void updateStudentInfo(ActionEvent event) {
		String oldUsername = oldUsernameTextField.getText();
		String newUsername = usernameTextField.getText();
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		errorLabel.setText("");
		successLabel.setText("");

		if (oldUsername.equals("") || newUsername.equals("") || firstName.equals("") || lastName.equals("")
				|| password.equals("") || confirmPassword.equals("")) {
			errorLabel.setText("Please fill all fields!");
		} else if (!password.equals(confirmPassword)) {
			errorLabel.setText("confirm password didn't match");
		} else {
			registrationController = new RegistrationController();
			users = registrationController.readUsers();

			if (users == null) {
				errorLabel.setText("Sorry, No data found!");
			}
			if (users.containsKey(oldUsername)) {
				User user = new User(newUsername, firstName, lastName, password, "User");
				users.remove(oldUsername);
				users.put(user.getUsername(), user);

				successLabel.setText("Info updated successfuly!");

				registrationController.writeUsers(users);

				users = registrationController.readUsers();
				for (User user2 : users.values()) {
					System.out.println(user2);
				}

			} else {
				errorLabel.setText("Username '" + oldUsername + "' doesn't exist!");
			}
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
		String bookTitle = bookTitleTextField.getText();

		successLabel.setText("");
		errorLabel.setText("");

		books = readBooks();

		if (bookTitle.equals("")) {
			errorLabel.setText("Please Enter Book Title!");
		} else {
			int counter = 0;
			for (Book book : books.values()) {
				if (book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
					System.out.println(book);
					counter++;
				}
			}
			if (counter > 0) {
				successLabel.setText("Book Found!.");
			} else {
				errorLabel.setText("Could not Find " + bookTitle);
			}
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

	public void backToAdminDashBoard(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("adminDashboardTest.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void backToUserDashBoard(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("userDashboardTest.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void logout(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Are you sure you want to exit?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			System.out.println("Successfuly logout");
			stage.close();
		}

//		Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
//		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		stage.setResizable(false);
//		stage.setScene(scene);
//		stage.show();
	}

	public void viewListPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("viewTest.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

	}
}
