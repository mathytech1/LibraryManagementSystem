package application;

import java.io.IOException;
import java.time.LocalDate;

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
	private TextField addBookId;
	@FXML
	private TextField addBookTitle;
	@FXML
	private TextField addBookAuthor;
	@FXML
	private DatePicker addBookPubYear;
	@FXML
	private Label bookAddErrorLabel;
	@FXML
	private Label bookAddSuccess;

	private Stage stage;
	private Scene scene;

	public void addBookPage(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("addBookForm.fxml"));
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

	public void addBook(ActionEvent event) {
		String bookID = addBookId.getText();
		String bookTitle = addBookTitle.getText();
		String bookAuthor = addBookAuthor.getText();
		LocalDate publishedYear = addBookPubYear.getValue();

		bookAddSuccess.setText("");
		bookAddErrorLabel.setText("");

		if (bookID.equals("") || bookTitle.equals("") || bookAuthor.equals("") || publishedYear == null) {
			bookAddErrorLabel.setText("Please fill all the boxes!");
		} else if (bookID.equals("S0001")) {
			bookAddErrorLabel.setText("Book ID already exists!");
		} else {
			bookAddSuccess.setText("Book Added Successfuly!");
			System.out.println(bookID);
			System.out.println(bookTitle);
			System.out.println(bookAuthor);
			System.out.println(publishedYear);
		}
	}

	public void updateBookPage(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("updateBookForm.fxml"));
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

	public void updateBook(ActionEvent event) {
		String bookID = addBookId.getText();
		String bookTitle = addBookTitle.getText();
		String bookAuthor = addBookAuthor.getText();
		LocalDate publishedYear = addBookPubYear.getValue();

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

	public void backToAdminDashBoard(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
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
