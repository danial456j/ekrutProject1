package clientGUI;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Costumer;

//**************16.1
public  class UsersController   {
	private UsersInfoFormController sfc;	

	@FXML
	private Button btnSend = null;
	
	@FXML
	private TextField idtxt;
	
	@FXML
	public Label wrongInfo = new Label();
	
	@FXML
	private Button logOutBtn=null;
	
	@FXML
	private Button backBtn=null;
	
	/**
	 * getID method is used to return the text entered in the ID text field.
	 *
	 * @return the text entered in the ID text field.
	 */
	public String getID() {
		return idtxt.getText();
	}
	
	
	
	/**
	 * Send method is used to search for a user by his ID and load the user information if the user was found
	 *
	 * @param event The event that triggers the Send action
	 * @throws Exception If there is an error while loading the UsersInfoForm.fxml or searching for the user's ID
	 */
	
	public void Send(ActionEvent event) throws Exception {
		String id;
		FXMLLoader loader = new FXMLLoader();
		id=getID();
		if(isInputValid()) 
		{
			try {
			    // Create a new ArrayList
			ArrayList<String> searchForID = new ArrayList<>();
			searchForID.add("Search");//add to the arraylist 
			searchForID.add(id);//add the user if to the arraylist
			ClientUI.chat.accept(searchForID);//send the array list to the server
			if(ChatClient.msgRecieved.equals("UserNotExist")) {
				System.out.println("User ID not Found");
				//if the user id not fount in the DB set the wrong label to "User ID Not Found"
				wrongInfo.setText("User ID Not Found");
				wrongInfo.setVisible(true);

			}

			else {
				System.out.println("User ID Found");
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
			
			}catch(IndexOutOfBoundsException e) {
				System.out.println("User ID Found");

			}}
			else {
				return;
			}
		
	}

	
	/**
	 * start method is used to start the UsersFrame window and to close the program when the window is closed
	 *
	 * @param primaryStage the stage where the UsersFrame window will be displayed
	 * @throws Exception if there is an error while loading the UsersFrame.fxml or setting the scene
	 */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/UsersFrame.fxml"));
		Scene scene = new Scene(root);
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
	 * getBackBtn method is used to return to the previous window which is CostumerServiceWorkerPage
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the CostumerServiceWorkerPage window
	 */
	public void getBackBtn(ActionEvent event) throws Exception {
	    ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    Stage primaryStage = new Stage();
	    CostumerServiceWorkerPage Frame = new CostumerServiceWorkerPage();
	    Frame.start(primaryStage);
	}

	
	/**
	 * isInputValid method is used to check the validity of the input in the text field.
	 * it checks if the input is empty or not a number and returns true if the input is valid and false if not.
	 *
	 * @return true if the input is valid, false if not
	 */
	private boolean isInputValid() {
		try {


			if ( idtxt.getText().equals("")) {
				wrongInfo.setText("Please fill all fields.");
				wrongInfo.setVisible(true);
				return false;
			} else {
				int checkIfNumber = Integer.parseInt(idtxt.getText());
				if(checkIfNumber<0) {
					wrongInfo.setText("Invalid Input");
					wrongInfo.setVisible(true);
					return false;
				}	
				wrongInfo.setVisible(false);
				return true;
			}
		}
		catch(Exception e) {
			wrongInfo.setText("Invalid Input");
			wrongInfo.setVisible(true);
		}
		return false;
	}
	/**
	 * logOut method is used to log out the user by sending a logOut message to the server.
	 * It also hides the current window and opens the login window for the user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
		ArrayList<String> list = new ArrayList<>();
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) logOutBtn.getScene().getWindow());
	}
	
}
