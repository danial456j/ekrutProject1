
package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Costumer;

public class SubscriberRegisteringController implements Initializable {
	private Costumer s;

	@FXML
	private Label noBtnLabel;
	@FXML
	private Button btnNo = null;
	@FXML
	private Button btnYes = null;

	private String id;
	
	@FXML
	private Button backBtn = null;
	
	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView CostumerRegister;
	@FXML
	private Button btnHome = null;
	ObservableList<String> list;

	
	Image logo= new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image CostumerRegister1= new Image(getClass().getResourceAsStream("Costumer_Register.png"));

	public void loadSubscriber(Costumer s1) {
		this.s = s1;
		this.id = s.getId();
	}

	/**
	 * This method is used to handle the event when the user clicks the "No" button in the UI.
	 * It sets the text of the "noBtnLabel" label to indicate that the customer did not register as a subscriber and makes the label visible.
	 * @param event The event object representing the button click event.
	 * @throws Exception

	*/
	public void getNoButton(ActionEvent event) throws Exception {
		noBtnLabel.setText("The costumer doesn't register as a subscriber");
		noBtnLabel.setVisible(true);
	}
	
	
	/**
	 * 	This method is responsible for redirecting the user to the home page of the application.
	 * 	It hides the current window and opens a new stage for the home page.
	 * 	@param event - ActionEvent object that is triggered when the 'Home' button is clicked
	 * 	@throws Exception - in case of any error while loading the home page
	*/
	public void getHomeBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UsersController Frame = new UsersController();
	    Frame.start(primaryStage);
	}
	
	/**
	 * getBackBtn method is used to return to the previous window which is UsersInfoFormController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the UsersInfoFormController window
	 */
	public void getBackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGUI/UsersInfoForm.fxml").openStream());
		UsersInfoFormController usersInfoFormController = loader.getController();	
		usersInfoFormController.loadSubscriber(ChatClient.s1);
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Users Info Tool");	
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	

	/**
	* This method is used to get the Yes button, it will update the role of the costumer to "subscriber" in DB.
	* and will open the subscriber number window after calculate the subscriber number and enter in to the DB.
	* @param event - the event of clicking on the Yes button
	* @throws Exception - throws an exception in case of any error in the process
	*/
	public void getYesBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		ArrayList<String> updateSubscriberInof = new ArrayList<>();
		updateSubscriberInof.add("getSubscriberNum");
		updateSubscriberInof.add(id);
		ClientUI.chat.accept(updateSubscriberInof);
		ArrayList<String> updateCostumreRole = new ArrayList<>();
		updateCostumreRole.add("UpdatingRoleOfUserInDB");
		updateCostumreRole.add(id);
		updateCostumreRole.add("Subscriber");
		ClientUI.chat.accept(updateCostumreRole);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGUI/subscribernumber.fxml").openStream());
		SubscribernumberController subscribernumberController = loader.getController();
		subscribernumberController.loadSubscriber(ChatClient.s1);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Costumer Registering Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}

	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logoImage.setImage(logo);
		CostumerRegister.setImage(CostumerRegister1);

	}

}
