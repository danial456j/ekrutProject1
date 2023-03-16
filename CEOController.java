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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CEOController implements Initializable {
	@FXML
	private Label WelcomeLabel;

	public static ArrayList<String> staticStore = new ArrayList<>();

	@FXML
	private Button reportsButton = null;
	@FXML
	private Label info = new Label(); 
	@FXML
	private Button makeOrderBtn = null;
	@FXML
	private Button logOutBtn = null;

	FXMLLoader loader = new FXMLLoader();
	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView CEO;
	@FXML
	private ImageView report;
	ObservableList<String> list;


	Image logo= new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image ceo1= new Image(getClass().getResourceAsStream("ceo.png"));
	Image report1= new Image(getClass().getResourceAsStream("report.png"));

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
	/**
	 * The start method is used to launch the application and set the primary stage of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CEOPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Searching for Ekrut Users Tool");
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
	public void initialize(URL location, ResourceBundle resources) {
		logoImage.setImage(logo);
		CEO.setImage(ceo1);
		report.setImage(report1);

		try {
			staticStore.clear();
			staticStore.add("haifa");
			staticStore.add("karmiel");
			staticStore.add("eilat");
			staticStore.add("beersheva");
			staticStore.add("abudhabi");
			staticStore.add("dubai");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * makeOrdertBtn method is used to check if the manager is registered as costumer (that's mean he can make order).
	 * If the manager is a registered costumer, it opens the CostumerHomePageForn window , otherwise, it will show an error message.
	 *
	 * @param event The event that triggers the makeOrdert action
	 * @throws Exception If there is an error while checking the manager's permission or opening the ManagerCostumerApproval window
	 */
	public void makeOrdertBtn(ActionEvent event) throws Exception {
		 if(ChatClient.u1.getPermissions()==null) {
			 info.setText("The CEO is not registered as a costumer!");
			 return;
		 }
			if (ChatClient.u1.getPermissions()!=null || ChatClient.u1.getPermissions().equals("Approve")) {
				ChatClient.u1.setRole("Subscriber");
				CostumerHomePageController aFrame3 = new CostumerHomePageController();
				  aFrame3.start((Stage) makeOrderBtn.getScene().getWindow());
		  }
		  else {
			  info.setText("The CEO is not registered as a costumer!");
			  
		  }
	}
	
	/**
	 * getReportsButton method is used to open the MainPageReportsController window for the manager so he can choose and  see the reports
	 *
	 * @param event The event that triggers the getReportsButton action
	 * @throws Exception If there is an error while loading the MainPageReports.fxml or opening the MainPageReportsController window
	 */
	public void getReportsButton(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		MainPageReportsController mainPageReportsController = new MainPageReportsController();
		Stage primaryStage = new Stage();
		mainPageReportsController.start(primaryStage);
	}

}
