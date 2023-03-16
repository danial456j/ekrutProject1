package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Qamar
 * @author shokry
 */
public class StockReport implements Initializable {
	@FXML
	private Button back;
	@FXML
	private Button home;
	@FXML
	private Label Machine;
	@FXML
	private Label year = new Label();
	@FXML
	private Label month = new Label();
	
	@FXML
	private Label totalInventory;
	@FXML
	private Label lowedToThreshold;
	@FXML
	private Label lowedTozero;
	@FXML
	private Label mostAvailable;
	@FXML
	private Label loaded;
	@FXML
	private ImageView inventory = new ImageView();
	Image inventoryImage = new Image(getClass().getResourceAsStream("inventory.png"));
	
	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 resources for the location of the root object
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> costumerReport = ChatClient.report;
		inventory.setImage(inventoryImage);
		Machine.setText(costumerReport.get(0));
		totalInventory.setText(costumerReport.get(1));
		lowedToThreshold.setText(costumerReport.get(2));
		lowedTozero.setText(costumerReport.get(3));
		mostAvailable.setText(costumerReport.get(4));
		loaded.setText(costumerReport.get(5));
		month.setText(costumerReport.get(6));
		year.setText(costumerReport.get(7));
	}
	
	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/StockReports.fxml"));
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
	 * getBackBtn method is used to return to the previous window which is MainPageReportsController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the MainPageReportsController window
	 */
	public void back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		MainPageReportsController reportsFrame = new MainPageReportsController();
		reportsFrame.start(primaryStage);
	}

	/**
	 * This method is responsible for redirecting the user to the home page of the
	 * application. It hides the current window and opens a new stage for the home
	 * page.
	 * @param event - ActionEvent object that is triggered when the 'Home' button is
	 * clicked
	 * @throws Exception - in case of any error while loading the home page
	 */
	public void home(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		if (ChatClient.u1.getRole().equals("CEO")) {
			CEOController cEOCntroller = new CEOController();
			cEOCntroller.start(stage);
		} else {
			ManagerPageController managerPageController = new ManagerPageController();
			managerPageController.start(stage);
		}
	}
}