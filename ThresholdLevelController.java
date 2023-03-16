package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Threshold;

public class ThresholdLevelController implements Initializable {

	ObservableList<String> Location = FXCollections.observableArrayList(ManagerPageController.staticStore);
	@FXML
	private ComboBox<String> machineLocationBox;
	@FXML
	private TextField thresholdtxt = new TextField();
	@FXML
	private Label successMsg;
	@FXML
	private Button savebtn;
	@FXML
	private Button backBtn;

	@FXML
	private Label wrongInfo = new Label();
	private Threshold t;

	private String location;
	ObservableList<String> list;

	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		successMsg.setVisible(false);
		machineLocationBox.setItems(Location);

	}
	/**
	 * load method is used to populate the fields with the information of a specific user
	 *
	 * @param t1 the threshold of the machine location
	 */
	public void load(Threshold t1) {
		this.t = t1;
		this.location = t.getLocation();
		this.thresholdtxt.setText(t.getThreshold());

	}

	/**
	 * This method is used to update or insert the threshold level of specific machine by request to the server.
	 * If the request sent successfully, an label show to the user with a message that their change successfully saved
	 * @param event - The event of clicking on the Yes button
	 * @throws Exception
	 */
	public void getSaveBtn(ActionEvent event) throws Exception {
		successMsg.setVisible(true);
		FXMLLoader loader = new FXMLLoader();
		if (isInputValid()) {
			ArrayList<String> updateMachineLocation = new ArrayList<>();
			updateMachineLocation.add("AddMachineThreshold");
			wrongInfo.setText("");
			updateMachineLocation.add(machineLocationBox.getValue());
			updateMachineLocation.add(thresholdtxt.getText());
			ClientUI.chat.accept(updateMachineLocation);
			successMsg.setVisible(true);
			successMsg.setText("Threshold saved succsessfully");
		} else {
			return;
		}

	}

	/**
	 * isInputValid method is used to check the validity of the input in the text field.
	 * the method check that the entered value in thresholdtxt is a positive number only and returns true if the input is valid and false if not
	 * it checks if the input is empty or not a number and returns true if the input is valid and false if not.
	 *
	 * @return true if the input is valid, false if not
	 */
	private boolean isInputValid() {
		//successMsg.setVisible(false);
		//test.setVisible(false);
		//test.setText("");
		try {
			System.out.println("shreshold text: " + thresholdtxt.getText());
			System.out.println(machineLocationBox.getValue());

			if (machineLocationBox.getValue() == null || thresholdtxt.getText().equals("")) {
				System.out.println("shreshold text: if " + thresholdtxt.getText());
				wrongInfo.setText("Please fill all fields.");
				wrongInfo.setVisible(true);
				successMsg.setVisible(false);

				return false;
			} else {
				System.out.println("shreshold text: " + thresholdtxt.getText() );
				int checkIfNumber = Integer.parseInt(thresholdtxt.getText());
				if(checkIfNumber<0) {
					wrongInfo.setText("Invalid Input");
					wrongInfo.setVisible(true);
					successMsg.setVisible(false);

					return false;
				}
					
				wrongInfo.setVisible(false);
				return true;
			}
		}
		catch(Exception e) {
			wrongInfo.setText("Invalid Input");
			wrongInfo.setVisible(true);
			successMsg.setVisible(false);
		}
		return false;
	}
	/**
	 * The start method is used to launch the application and set the primary stage of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/ThresholdLevel.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Searching for Ekrut Subscriber Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * getBackBtn method is used to return to the previous window which is ManagerPagerController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the ManagerPagerController window
	 */
	public void back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		ManagerPageController managerPageController = new ManagerPageController();
		managerPageController.start(stage);
	}
}