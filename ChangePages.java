package logic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangePages {
	
	public void changeScene(ActionEvent event, String fxml) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(pane);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
