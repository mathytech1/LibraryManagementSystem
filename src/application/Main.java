package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("userLogin.fxml"));
			Scene scene = new Scene(root, 600, 350);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			Image icon = new Image(getClass().getResourceAsStream("/application/images/libraryIcon.png"));
			stage.setTitle("Digital Library");
			stage.getIcons().add(icon);
			stage.setResizable(false);
			stage.show();

			stage.setOnCloseRequest(event -> {
				event.consume();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Closing program");
				alert.setHeaderText("You're about to Exit!");
				alert.setContentText("Are you sure you want to close this program?");

				if (alert.showAndWait().get() == ButtonType.OK) {
					System.out.println("Successfuly logout");
					stage.close();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
