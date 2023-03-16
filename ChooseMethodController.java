package clientGUI;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.TimerTwoMinutes;

/**
 * 
 * @author Suhel
 * @author Amne
 *
 */

public class ChooseMethodController {
	ObservableList<String> method = FXCollections.observableArrayList("Pick up later", "Drone Delivery");

	@FXML
	private ComboBox<String> methodBox;
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView drone;
	Image droneImage = new Image(getClass().getResourceAsStream("droneDelivery-removebg-preview (1).png"));
	@FXML
	private ImageView immediate;
	Image immediateImage = new Image(getClass().getResourceAsStream("pickmethod.png"));
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	
	ArrayList<String> requestCostomerInfo = new ArrayList<>();
	String id = ChatClient.u1.getId();
	String username = ChatClient.u1.getUserName();
	public static String remoteMethod;
	
	/**
	 * initializing all the required data for this class
	 */
	@FXML
	private void initialize() {
		methodBox.setItems(method);
		logo.setImage(logoImage);
		drone.setImage(droneImage);
		immediate.setImage(immediateImage);
	}
	
	/**
	 * start method is used to start the UsersFrame window and to close 
	 * the program when the window is closed it also starts the timer
	 *
	 * @param primaryStage the stage where the UsersFrame window will be displayed
	 * @throws Exception if there is an error while loading the UsersFrame.fxml or setting the scene
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/ChooseMethod.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
		primaryStage.show();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}
	
	/**
	 * changes the page in accordance to the chosen method or supply
	 * @param event
	 * @throws Exception
	 */
	public void chosenMethod(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.thread.stop();
		Stage primaryStage = new Stage();
		ChatClient.primaryStage = primaryStage;
		remoteMethod = methodBox.getValue();
		if(remoteMethod.equals("Drone Delivery")) {
			DroneDeliveryController delivery = new DroneDeliveryController();
			delivery.start(primaryStage);
		}
		if(remoteMethod.equals("Pick up later")) {
			FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Pane root = loader.load(getClass().getResource("/clientGUI/Payment.fxml").openStream());
			PaymentController paymentController = loader.getController();
			requestCostomerInfo.add("costumerInfo");
			username = "'" + username + "'";
			requestCostomerInfo.add(username);
			ClientUI.chat.accept(requestCostomerInfo);
			paymentController.loadCustomerInfo(ChatClient.costumerInfo);
			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			     @Override
			     public void handle(WindowEvent e) {
			      Platform.exit();
			      System.exit(0);
			     }
			   });
			primaryStage.show();
		}
	}
	
	/**
	 * logOut method is used to log out the user by sending a logOut message to the server.
	 * It also hides the current window and opens the login window for the user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}

	/**
	 * changing the page to the home page
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
}
