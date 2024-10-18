package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ListViewController {

	@FXML
	private Button addButton;

	@FXML
	private ListView<String> listView;

	@FXML
	private Button removeButton;

	@FXML
	private Label selectedLabel;

	@FXML
	private TextField textField;

	@FXML
	void addItem(MouseEvent event) {
		listView.getItems().add(textField.getText());
	}

	@FXML
	void removeItem(MouseEvent event) {
		int itemID = listView.getSelectionModel().getSelectedIndex();
		listView.getItems().remove(itemID);
	}

}
