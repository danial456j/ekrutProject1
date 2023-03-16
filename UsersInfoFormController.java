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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Costumer;

public class UsersInfoFormController implements Initializable {
	private Costumer s;

	@FXML
	private Label lblID;
	@FXML
	private Label lblFirstName;
	@FXML
	private Label lblLastName;
	@FXML
	private Label phoneNumber;
	@FXML
	private Label emailAddress;
	@FXML
	private Label creditCardNumber;
	@FXML
	private Label lblRole;

	@FXML
	private TextField txtID;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtPhoneNumber;
	@FXML
	private TextField txtemailAddress;
	
    @FXML private TextField txtcreditCardNumber;
	 
	@FXML
	private Label userIsSubscriber = new Label(); 
	@FXML
	private TextField txtRole;

	@FXML
	private Button btnBack = null;
	@FXML
	private Button btnSend = null;

	
	ObservableList<String> list;
	
	/**
	 * loadSubscriber method is used to populate the fields in the UsersInfoForm with the information of a specific user
	 *
	 * @param s1 the user whose information will be loaded
	 */
	public void loadSubscriber(Costumer s1) {
		this.s = s1;
		System.out.println(s);
		this.txtID.setText(s.getId());
		this.txtFirstName.setText(s.getFirstName());
		this.txtLastName.setText(s.getLastName());
		this.txtPhoneNumber.setText(s.getPhoneNumber());
		this.txtemailAddress.setText(s.getEmailAddress());
	    this.txtcreditCardNumber.setText(s.getCreditCardNumber());
		this.txtRole.setText(s.getRole());

	}
	
	/**
	 * getBackBtn method is used to return to the previous window which is CostumerServiceWorkerPage
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the CostumerServiceWorkerPage window
	 */
	public void getBackBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UsersController Frame = new UsersController();
	    Frame.start(primaryStage);
	}

	/**
	 * getRole method is used to return the text entered in the Role text field.
	 *
	 * @return the text entered in the Role text field.
	 */
	private String getRole() {
		return txtRole.getText();
	}

	//************ 15.1
	
	/**
	 * getSaveBtn method is used to handle the save button click event. 
	 * the method check the user role, is the user role's role is null then the method open the page Costumer_Register in order register the user as a costumer
	 * if the user's role is a costumer the method open the page SubscriberRegistering to register the costumer as a subscriber
	 * if the user's role is a subscriber the method shows message that the user is a costumer 
	 * if the user's role is  waitForApproval the method shows message that the user wait for manager approval
	 * if the user's role is non of the above it change the permissions of the user to Approve
	 * it check if the user is a subscriber or not and redirect to the appropriate page
	 *
	 * @param event the event of clicking the save button
	 * @throws Exception if there is an error while loading the fxml files or setting the scene
	 */
	public void getSaveBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		ArrayList<String> updateSubscriberInof = new ArrayList<>();
		updateSubscriberInof.add("checkSubscriber");
		updateSubscriberInof.add(s.getId());
		ClientUI.chat.accept(updateSubscriberInof);
		if (getRole().equals("NULL")) {
			System.out.println("User Role is Null");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/clientGUI/Costumer_Register.fxml").openStream());
			CostumerRegisterController costumerRegisterController = loader.getController();
			costumerRegisterController.loadSubscriber(ChatClient.s1);
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
		} else if (getRole().equals("Costumer") && (ChatClient.data.equals("NotExist"))) {
			System.out.println("The costumer is not a subscriber");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/clientGUI/SubscriberRegistering.fxml").openStream());
			SubscriberRegisteringController subscriberRegisteringController = loader.getController();
			subscriberRegisteringController.loadSubscriber(ChatClient.s1);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Subscriber Registering Tool");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			     @Override
			     public void handle(WindowEvent e) {
			      Platform.exit();
			      System.exit(0);
			     }
			   });
		} else  if( getRole().equals("Subscriber") ){
			userIsSubscriber.setText("The User is subscriber you can't change his role! ");
			userIsSubscriber.setVisible(true);
			
		}
	 else  if( getRole().equals("WaitForApproval") ){
		userIsSubscriber.setText("The User is not approval yet, waitiong for manager approval. ");
		userIsSubscriber.setVisible(true);
		
	}
	 else {
		 ArrayList<String> updatePermision = new ArrayList<>();
		      updatePermision.add("updatePermissions");
		      updatePermision.add(s.getId());
			  ClientUI.chat.accept(updatePermision);
				userIsSubscriber.setText("Costumer registered succssefuly ");
				userIsSubscriber.setVisible(true);
			  
	 }
	}

	/**
	 * The initialize method is used to set the text fields to be non-editable when the form is loaded.
	 *
	 * @param arg0 the URL location of the FXML file associated with this controller
	 * @param arg1 the resources used to localize the root object, or null if the root object was not localized
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtID.setEditable(false);
		txtFirstName.setEditable(false);
		txtLastName.setEditable(false);
		txtPhoneNumber.setEditable(false);
		txtemailAddress.setEditable(false);
		txtcreditCardNumber.setEditable(false);
		txtRole.setEditable(false);
	}

}
