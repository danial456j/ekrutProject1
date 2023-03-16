package clientGUI;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Qamar
 *
 */
public class OrderReportController implements Initializable {
	@FXML
	private Button backBtn;
	@FXML
	private Button homeBtn;
	@FXML
	private Label ordersAmount = new Label();
	@FXML
	private Label income = new Label();
	@FXML
	private Label pickUpType = new Label();
	@FXML
	private Label immediateOrderType = new Label();
	@FXML
	private Label droneDeliveryType = new Label();
	@FXML
	private Label branchName = new Label();
	@FXML
	private Label yearReport = new Label();
	@FXML
	private Label monthReport = new Label();
	@FXML
	private PieChart pieChart;

	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/OrderReportPage.fxml"));
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
	
	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 resources for the location of the root object
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(ChatClient.report);
		branchName.setText(MainPageReportsController.machineLocationString);
		yearReport.setText(MainPageReportsController.yearString);
		monthReport.setText(MainPageReportsController.monthString);
		
		ordersAmount.setText(ChatClient.report.get(0));
		income.setText(ChatClient.report.get(1));
		pickUpType.setText(ChatClient.report.get(2));
		droneDeliveryType.setText(ChatClient.report.get(3));
		immediateOrderType.setText(ChatClient.report.get(4));
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Pick Up", Integer.parseInt(pickUpType.getText())),
				new PieChart.Data("Drone Delivery", Integer.parseInt(droneDeliveryType.getText())),
				new PieChart.Data("Immediate", Integer.parseInt(immediateOrderType.getText())));

		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), "amount: ", data.pieValueProperty())));
		pieChart.getData().addAll(pieChartData);

	}

}
