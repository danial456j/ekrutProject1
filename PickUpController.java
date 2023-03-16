package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.TimerTwoMinutes;

public class PickUpController implements Initializable{
	
	@FXML
	private Button logOutBtn;
	@FXML
	private Button home;
	@FXML 
	private TextField orderCode;
	@FXML 
	private Button getOrder;
	@FXML 
	private Label msgToDisplay;
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView pickUpCode;
	Image pickUpCodeImage = new Image(getClass().getResourceAsStream("pickUpCode.png"));
	
	private TimerTwoMinutes timer = new TimerTwoMinutes();
	private ArrayList<String> stores;
	private String branch;

	
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/PickUp.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		//TimerTwoMinutes.startNewTimer(ChatClient.thread);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
		ChatClient.primaryStage = primaryStage;
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logo.setImage(logoImage);
		pickUpCode.setImage(pickUpCodeImage);
		stores = new ArrayList<>();
		stores.add("haifa");
		stores.add("karmiel");
		stores.add("eilat");
		stores.add("beersheva");
		stores.add("abudhabi");
		stores.add("dubai");
	}
	
	public void getOrderBtn(ActionEvent event) throws Exception {
		//TimerTwoMinutes.stopTimer(ChatClient.thread);
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();

		if(orderCode.getText().trim().isEmpty())
			msgToDisplay.setText("You must Enter an Order Code");
		else {
			Random rand = new Random(); 
			int storeIndex = rand.nextInt(6);
			branch = stores.get(storeIndex);
			ArrayList<String> checkOrderCode = new ArrayList<>();
			checkOrderCode.add("checkOrderCode");
			checkOrderCode.add(branch);
			checkOrderCode.add(orderCode.getText());
			ClientUI.chat.accept(checkOrderCode);
			
			if(ChatClient.orderCodeMsg.equals("No Such Code")) 
				msgToDisplay.setText("No Such Code is found! in " + branch + " store");
			else if(ChatClient.orderCodeMsg.equals("Was Picked Up"))
				msgToDisplay.setText("This order Was Picked Up");
			else {
				msgToDisplay.setText("");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("The Robot is making up your order, please wait");
				Optional <ButtonType> result = alert.showAndWait();
				result = alert.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK) {
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					ChatClient.thread.stop();
					Stage primaryStage = new Stage();
					ChatClient.primaryStage = primaryStage;
					CostumerHomePageController costumerHomePageController = new CostumerHomePageController();
					costumerHomePageController.start(primaryStage);
				}
			}
		}
	}
	
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
	
	public void logOutBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}
}
