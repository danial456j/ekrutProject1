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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ManagerPageController implements Initializable {
	public static ArrayList<String> staticStore = new ArrayList<>();

	@FXML
	private Button logOutBtn = null;

	@FXML
	private Button reportsButton = null;

	@FXML
	private Button notificationButton = null;

	@FXML
	private Button thresholdLevelButton = null;
	@FXML
	private Button makeOrderBtn = null;

	@FXML
	private Label info = new Label();

	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView Manager;
	ObservableList<String> list;

	Image logo = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image Manager1 = new Image(getClass().getResourceAsStream("manager.png"));
	FXMLLoader loader = new FXMLLoader();

	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/Managerpage15.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Searching for Ekrut Users Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
		// Set an event handler for when the primary stage is closed
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
		ArrayList<String> list = new ArrayList<>();
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) logOutBtn.getScene().getWindow());

	}

	/**
	 * getReportsButton method is used to open the MainPageReportsController window
	 * for the manager so he can choose and see the reports
	 *
	 * @param event The event that triggers the getReportsButton action
	 * @throws Exception If there is an error while loading the MainPageReports.fxml
	 *                   or opening the MainPageReportsController window
	 */
	public void getReportsButton(ActionEvent event) throws Exception {
		// ((Node)event.getSource()).getScene().getWindow().hide();
		Parent pane = FXMLLoader.load(getClass().getResource("MainPageReports.fxml"));
		((Node) event.getSource()).getScene().getWindow().hide();

		Scene scene = new Scene(pane);
		MainPageReportsController mainPageReportsController = new MainPageReportsController();
		// thresholdLevelController.load();
		Stage primaryStage = new Stage();
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
	 * getThresholdLevelButton method is used to open the ThresholdLevelController
	 * window for the manager so he can insert threshold level for a chosen branch
	 * to the DB
	 *
	 * @param event The event that triggers the getThresholdLevelButton action
	 * @throws Exception If there is an error while loading the ThresholdLevel.fxml
	 *                   or opening the ThresholdLevelController window
	 */
	public void getThresholdLevelButton(ActionEvent event) throws Exception {
		Parent pane = FXMLLoader.load(getClass().getResource("ThresholdLevel.fxml"));
		((Node) event.getSource()).getScene().getWindow().hide();

		Scene scene = new Scene(pane);
		ThresholdLevelController thresholdLevelController = new ThresholdLevelController();
		// thresholdLevelController.load(ChatClient.t1);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Searching for Ekrut Users Tool");
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
	 * userManagmentBtn method is used to open the ManagerCostumerApproval window
	 * for the manager so he can approve costumers
	 *
	 * @param event The event that triggers the userManagmentBtn action
	 * @throws Exception If there is an error while loading the
	 *                   ManagerCostumerApproval.fxml or opening the
	 *                   userManagmentBtn window
	 */
	public void userManagmentBtn(ActionEvent event) throws Exception {
		Parent pane = FXMLLoader.load(getClass().getResource("ManagerCostumerApproval.fxml"));
		((Node) event.getSource()).getScene().getWindow().hide();
		ManagerCostumerApproval managerCostumerApproval = new ManagerCostumerApproval();
		Stage primaryStage = new Stage();
		managerCostumerApproval.start(primaryStage);
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
	 * makeOrdertBtn method is used to check if the manager is registered as
	 * costumer (that's mean he can make order). If the manager is a registered
	 * costumer, it opens the CostumerHomePageForn window , otherwise, it will show
	 * an error message.
	 *
	 * @param event The event that triggers the makeOrdert action
	 * @throws Exception If there is an error while checking the manager's
	 *                   permission or opening the ManagerCostumerApproval window
	 */
	public void makeOrdertBtn(ActionEvent event) throws Exception {
		if (ChatClient.u1.getPermissions() == null) {
			info.setText("The worker is not registered as a costumer!");
			return;
		}
		if (ChatClient.u1.getPermissions() != null || ChatClient.u1.getPermissions().equals("Approve")) {
			ChatClient.u1.setRole("Subscriber");
			CostumerHomePageController aFrame3 = new CostumerHomePageController();
			aFrame3.start((Stage) logOutBtn.getScene().getWindow());
		} else {
			info.setText("The worker is not registered as a costumer!");

		}
	}

	public void notificationBtn(ActionEvent event) throws Exception {
		MangerNotificationsController aFrame = new MangerNotificationsController();
		aFrame.start((Stage) logOutBtn.getScene().getWindow());
	}

	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override

	public void initialize(URL location, ResourceBundle resources) {

		try {
			staticStore.clear();
			if (ChatClient.u1.getStoreName().equals("haifa") || ChatClient.u1.getStoreName().equals("karmiel")) {
				staticStore.add("haifa");
				staticStore.add("karmiel");
			}

			if (ChatClient.u1.getStoreName().equals("eilat") || ChatClient.u1.getStoreName().equals("beersheva")) {
				staticStore.add("eilat");
				staticStore.add("beersheva");

			}

			if (ChatClient.u1.getStoreName().equals("abudhabi") || ChatClient.u1.getStoreName().equals("dubai")) {
				staticStore.add("abudhabi");
				staticStore.add("dubai");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
