package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class LibraryManagementSystem extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("userLogin.fxml"));
			Scene scene = new Scene(root, 650, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			stage.setOnCloseRequest(event -> {
				event.consume();
				logout(stage);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Are you sure you want to exit?");

		if (alert.showAndWait().get() == ButtonType.OK) {

			System.out.println("Successfuly logout");
			stage.close();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
