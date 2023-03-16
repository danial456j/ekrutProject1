

package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Costumer;

public class SubscribernumberController implements Initializable {
	private Costumer s;
	ObservableList<String> list;

	@FXML
	private TextField subscriber_number;
	private String id;
	private String subsnum;
	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView SubscriberNumber;
	@FXML
	private Button btnHome = null;
	
	Image logo= new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image SubscriberNumber1= new Image(getClass().getResourceAsStream("SubscriberNumber.png"));

	
	public void loadSubscriber(Costumer s1)  {
		this.s = s1;
		System.out.println(s);
		this.id= s.getId();
		this.subscriber_number.setText(ChatClient.subscriberNumber);


		}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/UsersFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Searching for Ekrut Subscriber Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public void homeBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UsersController uFrame = new UsersController();
		uFrame.start(primaryStage);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logoImage.setImage(logo);
		SubscriberNumber.setImage(SubscriberNumber1);
		
	}

}


