package clientGUI;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StoreEmployeeHomePageController {
    @FXML
    private Label msg;
    @FXML
    private Button invetoryReffiling;
    @FXML
    private Button logOut;
    @FXML
    private ImageView logo;
    Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
    
    /**
     * check if the worker has permission to make an order if the worker can then open make order page
     * @param event
     * @throws Exception 
     */
    public void MakeOrderButton(ActionEvent event) throws Exception {
    	if(ChatClient.u1.getPermissions()==null || ChatClient.u1.getPermissions().equals("Approve")==false){
    		msg.setTextFill(Color.RED);
    		msg.setText("You don`t have permission to make an order");
    		return;
    			}
    	ChatClient.u1.setRole("Subscriber");
    	 CostumerHomePageController aFrame3 = new CostumerHomePageController();
		  aFrame3.start((Stage) msg.getScene().getWindow()); 
    }
    /**
     * start the page
     * @param primaryStage
     * @throws Exception
     */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/StoreEmployeeHomePageForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);	
		primaryStage.show();
		//when press on X then terminate the procces 
		   primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}
	/**
	 * open the initiate discount page
	 * @param event
	 * @throws Exception
	 */
    public void RefilingInventory(ActionEvent event) throws Exception{
    	  InventoryRefillingController aFrame5 = new InventoryRefillingController();
		  aFrame5.start((Stage) msg.getScene().getWindow());
		
}
    /** log out and return to the login page
	 * @param event the action event
	 * @throws Exception throw exception if loading the login page failed
	 */
	public void LogOutButtonOnAction(ActionEvent event) throws Exception {
		ArrayList<String> list = new ArrayList<>(); 
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) msg.getScene().getWindow());
	}
}
