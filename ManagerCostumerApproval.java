package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Costumer;

public class ManagerCostumerApproval implements Initializable {
	private Costumer s;

	@FXML
	private Button saveBtn = null;
	@FXML
	private Button refreshBtn = null;
	@FXML
	private Button backBtn;

	@FXML
	private TableColumn<Costumer, String> ID = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> creditCardNumber = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> email = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> firstName = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> lastName = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> phoneNumber = new TableColumn<>();

	@FXML
	private TableColumn<Costumer, String> role = new TableColumn<>();

	@FXML
	private TableView<Costumer> table = new TableView<>();

	@FXML
	private Label noUserSelected;
	@FXML
	private Label savedSuccessfully;

	ObservableList<Costumer> subscribersList;

	ArrayList<Costumer> usersList = new ArrayList<>();

	/**
	 * start method is used to start the UsersFrame window and to close the program when the window is closed
	 *
	 * @param primaryStage the stage where the UsersFrame window will be displayed
	 * @throws Exception if there is an error while loading the UsersFrame.fxml or setting the scene
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/ManagerCostumerApproval.fxml"));
		ArrayList<String> User = new ArrayList<>();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Searching for Ekrut Users Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	* This method used to load the columns in the table view by using 
	* PropertyValueFactory to bind the data from the Costumer object to the columns.
	* @param none
	* @return void
	*/
	public void LoadColumns() {
			//binding the data from the Costumer object to the firstName column
			firstName.setCellValueFactory(new PropertyValueFactory<Costumer, String>("firstName"));
			//binding the data from the Costumer object to the lastName column
			lastName.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastName"));
			//binding the data from the Costumer object to the ID column
			ID.setCellValueFactory(new PropertyValueFactory<Costumer, String>("id"));
			//binding the data from the Costumer object to the phoneNumber column
			phoneNumber.setCellValueFactory(new PropertyValueFactory<Costumer, String>("phoneNumber"));
			//binding the data from the Costumer object to the email column
			email.setCellValueFactory(new PropertyValueFactory<Costumer, String>("emailAddress"));
			//binding the data from the Costumer object to the creditCardNumber column
			creditCardNumber.setCellValueFactory(new PropertyValueFactory<Costumer, String>("creditCardNumber"));
			//binding the data from the Costumer object to the role column
			role.setCellValueFactory(new PropertyValueFactory<Costumer, String>("Role"));
		}

	/**
	* This method is called when the class is loaded and it used to initialize the table view
	* columns by calling the LoadColumns method and it also used to load the user information from the database
	*/
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//			displayMsg.setVisible(false);
		firstName.setStyle("-fx-alignment: BASELINE_CENTER");
		lastName.setStyle("-fx-alignment: BASELINE_CENTER");
		ID.setStyle("-fx-alignment: BASELINE_CENTER");
		phoneNumber.setStyle("-fx-alignment: BASELINE_CENTER");
		email.setStyle("-fx-alignment: BASELINE_CENTER");
		creditCardNumber.setStyle("-fx-alignment: BASELINE_CENTER");
		role.setStyle("-fx-alignment: BASELINE_CENTER");
		LoadColumns();
		loadUserInfoFromDB();
	}

	
	/**
	 * 	This method is used to load user information from the database and display it in a table.
	 * 	It first clears the usersList, then creates an ArrayList of String called "users" and adds the string "WaitingForApproval" to it.
	 * 	It then sends this ArrayList to the server via the chat.accept method in order to get 
	 * 	Next, it creates a Costumer object called "user" and populates it with information from the ChatClient.waitingForApproval ArrayList.
	 *  ChatClient.waitingForApproval arrayList "WaitingForApproval" is an arrayList that we get from DB with the information of the users that their role is "WaitForApproval"
	 *  The user object is then added to the usersList.
	 *  The table is then cleared, the LoadColumns method is called, and the subscribersList is set to the observableArrayList of the usersList.
	 *  Finally, the table's items are set to the subscribersList.
	*/
	///********** 17.1
	public void loadUserInfoFromDB() {
		usersList.clear();
		ArrayList<String> users = new ArrayList<>();
		users.add("WaitingForApproval");
		if (ChatClient.u1.getStoreName().equals("haifa") || ChatClient.u1.getStoreName().equals("karmiel")) {
			users.add("haifa");
			users.add("karmiel");
		}
		if (ChatClient.u1.getStoreName().equals("eilat") || ChatClient.u1.getStoreName().equals("beersheva")) {
			users.add("eilat");
			users.add("beersheva");
		}
		if (ChatClient.u1.getStoreName().equals("abudhabi") || ChatClient.u1.getStoreName().equals("dubai")) {
			users.add("abudhabi");
			users.add("dubai");
		}
		ClientUI.chat.accept(users);
		Costumer user;
		for (int i = 0; i < ChatClient.waitingForApproval.size(); i += 7) {
			user = new Costumer(ChatClient.waitingForApproval.get(i + 2), ChatClient.waitingForApproval.get(i),
					ChatClient.waitingForApproval.get(i + 1), ChatClient.waitingForApproval.get(i + 3),
					ChatClient.waitingForApproval.get(i + 4), ChatClient.waitingForApproval.get(i + 5),
					ChatClient.waitingForApproval.get(i + 6));
			usersList.add(user);
		}
		table.getItems().clear();
		LoadColumns();
		subscribersList = FXCollections.observableArrayList(usersList);
		table.setItems(subscribersList);
	}

	// **********************16.1
	/**
	 * This method is used to approve the user's registration as a costumer.
	 * It checks whether a user is selected from the table view or not.
	 * If a user is selected, it shows an alert to confirm the approval.
	 * If the user confirms the approval, the method sends a message to the server to update the user's role in the database.
	 * @param event - ActionEvent object that represents the save button event
	 * @throws Exception - thrown if there is an error while loading the next scene
	 */
	public void saveBtn(ActionEvent event) throws Exception {
		ObservableList<Costumer> rows = table.getItems();
		ArrayList<String> tableViewList = new ArrayList<>();
		tableViewList.add("UpdatingRoleAndFirstPaymentOfUserInDB");
		tableViewList.add(ChatClient.waitingForApproval.get(2));
		int selectedID = table.getSelectionModel().getSelectedIndex();
		if (selectedID == -1) {
			savedSuccessfully.setVisible(false);
			noUserSelected.setVisible(true);
			noUserSelected.setText("you must select a user");
		} else {
			noUserSelected.setVisible(false);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("User Confirmation Alert");
			alert.setContentText("Do you want to APPROVE the user to a costumer?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				savedSuccessfully.setText("Saved Successfully");
				savedSuccessfully.setVisible(true);
				ClientUI.chat.accept(tableViewList);
			}
			else if (result.get() == ButtonType.CANCEL) {
				savedSuccessfully.setText("Saved Failed!");
				savedSuccessfully.setVisible(true);
			}
		}
	}

	/**
	 * This method is used to refresh the table of waiting for approval users.
	 * It will clear the table and call the loadUserInfoFromDB() method to 
	 * load the updated list of waiting for approval users.
	 * @param event the event that triggers the method, usually a button click
	 * @throws Exception if an error occurs while loading the table
	 */
		public void refreshBtn(ActionEvent event) throws Exception {
			//hide success and error messages
			savedSuccessfully.setVisible(false);
			noUserSelected.setVisible(false);
			//clear the table
			table.getItems().clear();
			//re-load the columns
			LoadColumns();
			//reload the list of waiting for approval users
			loadUserInfoFromDB();
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
