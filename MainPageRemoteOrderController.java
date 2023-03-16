package clientGUI;

import client.ChatClient;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.TimerTwoMinutes;

/**
 * 
 * @author suhel
 * @author amne
 *
 */
public class MainPageRemoteOrderController {
	ObservableList<String> store = FXCollections.observableArrayList("Haifa", "Karmiel", "Beersheva", "Eilat", "Abu Dhabi", "Dubai");

	@FXML
	private Label chooseStore;
	@FXML
	private Label errorMsgToProceed;
	@FXML
	private ComboBox<String> storeBox = new ComboBox<>();
	@FXML 
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	
	public static String storeLocation;
	
	/**
	*
	*The initialize method is used to initialize the components of the UI when the scene is loaded.
	*It sets the items of the storeBox combobox with the store list and sets the image of the logo.
	*/
	@FXML
	private void initialize() {
		storeBox.setItems(store);
		logo.setImage(logoImage);
	}
	
	/**
	*
	*The start method is used to start the GUI for the remote order application.
	*It loads the MainPageRemoteOrder.fxml file to set the layout of the GUI, sets the scene to the primary stage,
	*and shows the stage to the user. It also starts a new thread for the TimerTwoMinutes class and sets an event
	*handler for the primary stage's close request to close the application.
	*@param primaryStage the main stage on which the scene is set
	*@throws Exception if the FXML file cannot be loaded
	*/
	public void start(Stage primaryStage) throws Exception {
		ChatClient.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/MainPageRemoteOrder.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}
	
	/**
	*
	*The ProceedToOrderBtn method is used to proceed to the next stage of the remote order process
	*after the user has chosen a store from the storeBox combobox. It stops the thread and checks if the user
	*has chosen a store. If not, it displays an error message and restarts the thread. If a store has been chosen,
	*it loads the CreateNewOrder.fxml file to set the layout of the next stage of the GUI, passing the store location
	*to the loadCatalogItemsFromDB method in the CreateNewOrderController and sets the scene to the new primary stage.
	*It also sets an event handler for the primary stage's close request to close the application.
	*@param event the event that triggers the method call
	*@throws Exception if the FXML file cannot be loaded
	*/
	public void ProceedToOrderBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		if(storeBox.getValue() == null) {
			errorMsgToProceed.setText("You must choose a store first!");
			ChatClient.thread = new Thread(new TimerTwoMinutes());
			ChatClient.thread.start();
		}
		else {
			FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			ChatClient.primaryStage = primaryStage;
			Pane root = loader.load(getClass().getResource("/clientGUI/CreateNewOrder.fxml").openStream());
			CreateNewOrderController createNewOrderController = loader.getController();
			storeLocation = storeBox.getValue().replaceAll("\\s", "");
			storeLocation = storeLocation.toLowerCase();
			ChooseOrderTypeController.orderDetails.add(storeLocation);
			/*if(ChooseOrderTypeController.orderDetails.size() > 5) {
				int lastIndex = ChooseOrderTypeController.orderDetails.size() - 1;
				ChooseOrderTypeController.orderDetails.remove(lastIndex);
			}*/
			createNewOrderController.loadCatalogItemsFromDB(storeLocation);
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
	 * logOut method is used to log out the current user from the chat application.
	 *It stops the thread running the chat client and redirects the user to the logout page.
	 * @param event
	 * @throws Exception
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}
	
	/**
	 * homeBtn method is used to redirect the user to the home page of the chat application.
     *  It stops the thread running the chat client and redirects the user to the home page.
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
}
