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
			// Load the FXML file for the user login screen
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("userLogin.fxml"));
			// Create a new scene with the loaded FXML and set its dimensions
			Scene scene = new Scene(root, 600, 350);
			// Add the application's stylesheet
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// Set the scene on the primary stage
			stage.setScene(scene);
			// Load the application icon
			Image icon = new Image(getClass().getResourceAsStream("/application/images/libraryIcon.png"));
			// Set the title of the stage
			stage.setTitle("Digital Library");
			// Set the stage icon
			stage.getIcons().add(icon);
			// Disable resizing of the stage
			stage.setResizable(false);
			// Show the stage
			stage.show();

			// Set an event handler for the close request of the stage
			stage.setOnCloseRequest(event -> {
				// Consume the event to prevent the default closing behavior
				event.consume();
				// Create a confirmation alert before closing
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Closing program");
				alert.setHeaderText("You're about to Exit!");
				alert.setContentText("Are you sure you want to close this program?");

				// If the user confirms, close the application
				if (alert.showAndWait().get() == ButtonType.OK) {
					System.out.println("Successfully logout");
					stage.close();
				}
			});
		} catch (Exception e) {
			// Print the stack trace if an exception occurs during startup
			e.printStackTrace();
		}
	}

	// Main entry point for the application
	public static void main(String[] args) {
		launch(args); // Launch the JavaFX application
	}
}
