package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 
 * @author suhel
 * @author amne
 *
 */
public class UserHomePageController implements Initializable {

	@FXML
	private ImageView register;
	@FXML
	private Button logOut;
	@FXML
	private ImageView logoImage;
	Image logo= new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image register1= new Image(getClass().getResourceAsStream("register.png"));

	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		register.setImage(register1);
		logoImage.setImage(logo);
	}
	
	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/UserHomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				// Exit the application when the primary stage is closed
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	/**
	 * logOut method is used to log out the user by sending a logOut message to the
	 * server. It also hides the current window and opens the login window for the
	 * user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or
	 *                   opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
		// Create a new ArrayList to store the logOut message
		ArrayList<String> list = new ArrayList<>();
		ChatClient.msgRecieved = "";
		list.add("logOut"); // Add logOut message to the list
		ClientUI.chat.accept(list); // Send the logOut message to the server
		((Node) event.getSource()).getScene().getWindow().hide(); // Hiding primary window
		logInController aFrame = new logInController(); // Create EkrutFrame
		aFrame.start((Stage) logOut.getScene().getWindow()); // open login window
	}
}
