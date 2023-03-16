package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import client.ChatClient;
import clientTest.IClientUIChatAccept;
import clientTest.RegularClientUI;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author qamar
 * @author shokry
 *
 */
public class MainPageReportsController implements Initializable {
	
	private IClientUIChatAccept clientUIChatAccept = new RegularClientUI();
	
	ObservableList<String> year = FXCollections.observableArrayList("2022", "2021", "2020");
	ObservableList<String> month = FXCollections.observableArrayList("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");

	ObservableList<String> machineLocation;

	ObservableList<String> type = FXCollections.observableArrayList("Stock report", "Orders report", "Costumer report");

	@FXML
	private Button showReport;
	@FXML
	private Button backBtn;
	@FXML
	private Button homeBtn;
	@FXML
	private static ComboBox<String> yearBox;
	@FXML
	private static ComboBox<String> monthBox;
	@FXML
	private static ComboBox<String> machineLocationBox;
	
	@FXML
	public static String yearString;
	@FXML
	public static String monthString;
	@FXML
	public static String machineLocationString;
	@FXML
	private static ComboBox<String> typeBox;
	@FXML
	public Label wrongInfo;
	@FXML
	private ImageView logo;
	@FXML
	private ImageView report;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image reportImage = new Image(getClass().getResourceAsStream("report.png"));
	
	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 resources for the location of the root object
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (ChatClient.u1.getRole().equals("CEO"))
			machineLocation = FXCollections.observableArrayList(CEOController.staticStore);
		else
			machineLocation = FXCollections.observableArrayList(ManagerPageController.staticStore);
		yearBox = new ComboBox<>();
		monthBox = new ComboBox<>();
		machineLocationBox = new ComboBox<>();
		typeBox = new ComboBox<>();
		yearBox.setItems(year);
		monthBox.setItems(month);
		machineLocationBox.setItems(machineLocation);
		typeBox.setItems(type);
		logo.setImage(logoImage);
		report.setImage(reportImage);
	}

	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/MainPageReports.fxml"));
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
	 * showReportBtn method is used to open the OrderReportController window
	 * for the manager so he can see the order report
	 * This method is used to show a report based on user input
	 * It retrieves report data from the server and displays it in a new window.
	 * If the input is invalid, an error message is displayed.
	 *the method insert into an arrayList called report and insert to it the values that we get from the ComboBox
	 *the method sent the arrayList to the server and check if there's a report 
	 *if there's a report the method open the page OrderPageReport
	 *else the method shows a label with "No report found"
	 * @param event The action event that triggers this method.
	 * @throws Exception
	 */
	public void showReportBtn(ActionEvent event) throws Exception {

		if (isInputValid(yearBox.getValue(), monthBox.getValue(), machineLocationBox.getValue(), typeBox.getValue())) {
			ArrayList<String> report = new ArrayList<>();
			String reportType = typeBox.getValue();
			if (reportType.equals("Orders report")) {
				report = addInfoReport("Orders_report", machineLocationBox.getValue(),
						monthBox.getValue().toLowerCase(), yearBox.getValue());
			} else if (reportType.equals("Stock report")) {
				report = addInfoReport("Stock reports", machineLocationBox.getValue(),
						monthBox.getValue().toLowerCase(), yearBox.getValue());
			} else if (reportType.equals("Costumer report")) {
				report = addInfoReport("Costumer reports", machineLocationBox.getValue(),
						monthBox.getValue().toLowerCase(), yearBox.getValue());
			}
			
			yearString = yearBox.getValue();
			monthString = monthBox.getValue();
			machineLocationString = machineLocationBox.getValue();
			
			boolean reportExists = checkReportExists(report);
			if (reportExists) {
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Parent pane = null;
				if (reportType.equals("Orders report")) {
					OrderReportController orderReportController = new OrderReportController();
					orderReportController.start(new Stage());
				} else if (reportType.equals("Stock report")) {
					StockReport stockReport = new StockReport();
					stockReport.start(new Stage());
				} else if (reportType.equals("Costumer report")) {
					CostumerReport costumerReport = new CostumerReport();
					costumerReport.start(new Stage());
				}
				wrongInfo.setVisible(false);
				Scene scene = new Scene(pane);
				Stage primaryStage = new Stage();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			if (!reportExists) {
				wrongInfo.setText("No Report Found");
				wrongInfo.setVisible(true);
			}

		} else {
			wrongInfo.setText("Please fill all fields.");
			wrongInfo.setVisible(true);
		}
	}
	
	/**
	 * checks if the there are existing report with the same relevant data
	 * @param report
	 * @return true if exists report, otherwise false
	 */
	private boolean checkReportExists(ArrayList<String> report) {
		clientUIChatAccept.ClientUIChatAcceptMethod(report);
		if (clientUIChatAccept.getMsgRecived().equals("Report")) 
			return  true;
		else 
			return false;
	}

	/**
	 * 	This method is used to create an arrayList report containing information on report type, location, month, and year
	 * 	This method is used to create a report containing information on report type, location, month, and year
	 * 	@param reportType - type of report
	 * 	@param location - location of report
	 * 	@param month - month of report
	 * 	@param year-  year of report	
	 * @return ArrayList of report details
	*/
	private ArrayList<String> addInfoReport(String reportType, String location, String month, String year) {
		ArrayList<String> report = new ArrayList<>();
		report.add(reportType);
		report.add(location);
		report.add(month);
		report.add(year);
		return report;
	}

	/**
	 * Checking if the user selected all the comboBox so the controller will display
	 * the reports on the tableView.
	 * 
	 * @return true or false, based on the user input.
	 */
	private boolean isInputValid(String year, String month, String location, String type) {
		if (year == null || month == null || location == null || type == null)
			return false;
		else
			return true;
	}

	/**
	 * getBackBtn method is used to return to the previous window which is ManagerPageController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the ManagerPageController window
	 */
	public void back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		ManagerPageController managerPageController = new ManagerPageController();
		managerPageController.start(stage);
	}

	/**
	 * This method is responsible for redirecting the user to the home page of the
	 * application. It hides the current window and opens a new stage for the home
	 * page.
	 * 
	 * @param event - ActionEvent object that is triggered when the 'Home' button is
	 *              clicked
	 * @throws Exception - in case of any error while loading the home page
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
	
	public IClientUIChatAccept getClientUIChatAccept() {
		return clientUIChatAccept;
	}

	public void setClientUIChatAccept(IClientUIChatAccept clientUIChatAccept) {
		this.clientUIChatAccept = clientUIChatAccept;
	}
}
