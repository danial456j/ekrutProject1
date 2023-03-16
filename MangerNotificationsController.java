package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.MsgFromManger;
import logic.Order;

/**
 * 
 * @author danial
 *
 */
public class MangerNotificationsController implements Initializable {
	@FXML
	private TableView<MsgFromManger> mailTable = new TableView<>();
	@FXML
	private TableColumn<MsgFromManger, String> mailCol;
	@FXML
	private Button sendToWorkerBtn=null;
	@FXML
	private Button refreshBtn=null;
	@FXML
	private Button backBtn=null;
	@FXML
	private Label noOrderSelected;
	ObservableList<MsgFromManger> msgList;
	
	/**
	 * Method to initialize the components of the scene.
	 * 
	 * @param url - The arg0 used to resolve relative paths for the root object, or null if the location is not known
	 * @param rb - The arg1 used to localize the root object, or null if the root object was not localized
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mailCol.setStyle("-fx-alignment: BASELINE_CENTER");
		LoadColumns();
		loadTableFromDB();
		loadDataToTableView();
	}
	/**
	 * Method to handle the back button press.
	 * 
	 * @param event - The action event that triggered the method call.
	 * @throws Exception - If there is any exception while creating the new stage.
	 */
	public void back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		ManagerPageController managerPageController = new ManagerPageController();
		managerPageController.start(stage);
    }
	/**
	 * The start method is responsible for displaying the Manger Notifications scene on the primary stage.
	 *
	 * @param primaryStage the primary stage that the scene will be displayed on.
	 * @throws Exception 
	 */
	public void start(Stage primaryStage) throws Exception {	
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/clientGUI/MangerNotificationsPage.fxml").openStream());
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
	 * Method to load the columns for the table view.
	 */
	public void LoadColumns() {
		mailTable.setEditable(true);
		mailCol.setCellValueFactory(new PropertyValueFactory<MsgFromManger,String>("msg"));
	}
	/**
	 * this method is used to load the data from the data base  
	 * to the tableView
	 */
	public void loadTableFromDB() {
		ArrayList<String> store = new ArrayList<>();
		System.out.println("MangerMotifications staticstore"+ ManagerPageController.staticStore);
		store.add("storeNeedsReffiling");
		for(int i=0;i<2;i++) {
			store.add(ManagerPageController.staticStore.get(i));
		}
		System.out.println("MangerMotifications "+ store);
		try {
			ClientUI.chat.accept(store);
			System.out.println("manger chatClient: " + ChatClient.item);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * this method is used to load the data from item arrayList in ChatClient 
	 * to the tableView
	 */
	public void loadDataToTableView() {
		ArrayList<MsgFromManger> storesList = new ArrayList<>();
		MsgFromManger temp;
		for(int i=0;i<ChatClient.item.size();i++) {
			temp= new MsgFromManger(ChatClient.item.get(i)+"'s stock needs to be refilled");
			storesList.add(temp);
		}
		msgList = FXCollections.observableArrayList(storesList);
		mailTable.setItems(msgList);
	}
	/**
	 * This method is used to send a message to a worker.
	 * When the sendToWorker button is clicked, the selected message from the mailTable 
	 * is sent to the worker, if any message is selected, otherwise an error message will be displayed
	 * 
	 * @param event an ActionEvent that is triggered when the sendToWorker button is clicked
	 * @throws Exception
	 */
	public void sendToWorker(ActionEvent event) throws Exception {
	    // Create an ArrayList to store the approved store location
	    ArrayList<String> storeApproved = new ArrayList<>();
	    // Get the selected index of the mailTable
	    int selected = mailTable.getSelectionModel().getSelectedIndex();
	    if(selected == -1)
	    {
	        // If no message is selected, show an error message
	        noOrderSelected.setVisible(true);
	        noOrderSelected.setText("you must select an order");
	    }
	    else {
	        // Hide the error message
	        noOrderSelected.setVisible(false);
	        // Show a confirmation alert
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Confirmation Alert");
	        alert.setContentText("Do you want to SEND this message to worker?");
	        Optional<ButtonType> result = alert.showAndWait();
	        if(result.get() == ButtonType.OK) {
	            // Get the selected message
	            MsgFromManger msg = mailTable.getItems().get(selected);
	            // Split the message to get the store location
	            String[] parts = msg.getMsg().split("'s stock needs to be refilled");
	            String storeLocation = parts[0];
	            // Add the store location and the "StoreApproved" string to the storeApproved ArrayList
	            storeApproved.add("StoreApproved");
	            storeApproved.add(storeLocation);
	            // Send the storeApproved ArrayList to the worker
	            ClientUI.chat.accept(storeApproved);
	        }
	    }
	}

	/**
	 * This method is used to refresh the contents of the mailTable. 
	 * @param event an ActionEvent that is triggered when the refresh button is clicked
	 * @throws Exception 
	 */
	public void refresh(ActionEvent event) throws Exception {
	    // Create an ArrayList to store the items from ChatClient.item
	    ArrayList<MsgFromManger> storesList = new ArrayList<>();
	    MsgFromManger temp;
	    // Load the table from the database
	    loadTableFromDB();
	    // Iterate through the items in ChatClient.item
	    for(int i=0;i<ChatClient.item.size();i++) {
	        // Create a new MsgFromManger object with the current item
	        temp= new MsgFromManger(ChatClient.item.get(i)+"'s stock needs to be refilled");
	        // Add the object to the storesList ArrayList
	        storesList.add(temp);
	    }
	    // Convert the storesList ArrayList to an observable list
	    msgList = FXCollections.observableArrayList(storesList);
	    // Set the items of the mailTable to the msgList observable list
	    mailTable.setItems(msgList);
	}

	
}
