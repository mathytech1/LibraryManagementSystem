module LibraryManagementSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.junit.jupiter.api;

	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
