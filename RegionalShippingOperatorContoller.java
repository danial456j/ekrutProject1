package clientGUI;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logic.Item;
import logic.Order;



public class RegionalShippingOperatorContoller implements Initializable{
	@FXML 
	private Button saveBtn=null;
	@FXML 
	private Button backBtn=null;
	@FXML
	private TableView<Order> ordersTable = new TableView<>();
	
	@FXML
	private TableColumn<Order, String> col_Id = new TableColumn<>();

	@FXML
	private TableColumn<Order, String> col_Date = new TableColumn<>();

	@FXML
	private TableColumn<Order, String> col_Store = new TableColumn<>();
	
	@FXML
	private TableColumn<Order, String> col_OrderStatus = new TableColumn<>();
	
	@FXML
	private TableColumn<Order, String> col_Received = new TableColumn<>();
	@FXML
	private ComboBox<String> comboBox;
//	@FXML
//	private Label displayMsg;
	@FXML
	private Label noOrderSelected;
	@FXML
	private Label savedSuccessfully;
//	@FXML
//	private Label noOrderMsg;
	
	ObservableList<Order> remoteOrders;
	ArrayList<Order> ordersList = new ArrayList<>();
	
	Connection conn= null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	/**
	 * The start method is responsible for displaying the Regional Shipping Operator scene on the primary stage.
	 *
	 * @param primaryStage the primary stage that the scene will be displayed on.
	 * @throws Exception 
	 */
	public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/RegionalShippingOperator.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("RegionalShippingOperator");
			primaryStage.setScene(scene);
			primaryStage.show();	
			primaryStage.show();
			//image.setPickOnBounds(true);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			     @Override
			     public void handle(WindowEvent e) {
			      Platform.exit();
			      System.exit(0);
			     }
			   });
	}
	/**
	 * Method to handle the back button press.
	 * 
	 * @param event - The action event that triggered the method call.
	 * @throws Exception - If there is any exception while creating the new stage.
	 */
	public void back(ActionEvent event) throws Exception {
	    // hide the current window
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    // Create new stage
	    Stage stage = new Stage();
	    // Create new instance of InventoryRefillingController
	    RegionalShippingHomePageController regionalShippingHomePageController = new RegionalShippingHomePageController();
	    // Start the new stage
	    regionalShippingHomePageController.start(stage);
	}

	/**
	 * Method to initialize the components of the scene.
	 * 
	 * @param url - The url used to resolve relative paths for the root object, or null if the location is not known
	 * @param rb - The rb used to localize the root object, or null if the root object was not localized
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		displayMsg.setVisible(false);
		col_Id.setStyle("-fx-alignment: BASELINE_CENTER");
		col_Date.setStyle("-fx-alignment: BASELINE_CENTER");
		col_Store.setStyle("-fx-alignment: BASELINE_CENTER");
		col_OrderStatus.setStyle("-fx-alignment: BASELINE_CENTER");
		col_Received.setStyle("-fx-alignment: BASELINE_CENTER");
		LoadColumns();
		loadOrdersTableFromDB();
	}
	
	/**
	* This method sets up the columns of the orders table by setting it to be editable, and specifying the cell value factory for each column.
	*/
	public void LoadColumns() {
	    ordersTable.setEditable(true);
	    col_Id.setCellValueFactory(new PropertyValueFactory<Order,String>("OrderNumber"));
	    col_Date.setCellValueFactory(new PropertyValueFactory<Order,String>("estimatedDate"));
	    col_Store.setCellValueFactory(new PropertyValueFactory<Order,String>("branch"));
	    col_OrderStatus.setCellValueFactory(new PropertyValueFactory<Order,String>("orderStatus"));
	    col_Received.setCellValueFactory(new PropertyValueFactory<Order,String>("orderReceived"));
	}

	/**
	* This method loads the orders table from the database by creating an ArrayList of strings called "store".
	*
	* @throws Exception if there is an error when sending the ArrayList to the "accept" method
	*/
	public void loadOrdersTableFromDB() {
	    ArrayList<String> store = new ArrayList<>();
	    store.add("ApprovingOrders");
	    try { 
//	        if (ChatClient.u1.getStoreName().equals("northisrael")) {
//	            store.add("haifa");
//	            store.add("karmiel");
//	        }
	    	if (ChatClient.u1.getStoreName().equals("haifa") || ChatClient.u1.getStoreName().equals("karmiel")) {
	            store.add("haifa");
	            store.add("karmiel");
	        }
	        if (ChatClient.u1.getStoreName().equals("eilat")||ChatClient.u1.getStoreName().equals("beersheva")) {
	            store.add("eilat");
	            store.add("beersheva");
	        }
	        if (ChatClient.u1.getStoreName().equals("abudhabi")||ChatClient.u1.getStoreName().equals("dubai")) {
	            store.add("abudhabi");
	            store.add("dubai");
	        }
	        ClientUI.chat.accept(store);
	        Order order;
	        for(int i=0;i<ChatClient.item.size();i+=5) {
	            order = new Order(ChatClient.item.get(i),null,ChatClient.item.get(i+2),ChatClient.item.get(i+3),null,ChatClient.item.get(i+1),null,null,null,null,null,null,null,ChatClient.item.get(i+4));
	            ordersList.add(order);
	        }
	        ordersTable.getItems().clear();
	        LoadColumns();
	        remoteOrders = FXCollections.observableArrayList(ordersList);
	        ordersTable.setItems(remoteOrders);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	/**
	* This method saves changes made to the orders table by getting the selected row from the table, create an ArrayList of strings called "tableViewList", adding values to the ArrayList and then sending the ArrayList to the "accept" method of the "chat" object from the ClientUI class.
	* The method also shows a confirmation alert to the user, if the user confirms the receive of the order, it will change the status of the selected order to "Received" and sends the updated information to the server.
	* It also calls the refreshBtn method
	*
	* @param event ActionEvent that triggers the method
	* @throws Exception if there is an error when sending the ArrayList to the "accept" method
	*/
	public void saveBtn(ActionEvent event) throws Exception {
		ObservableList<Order> rows = ordersTable.getItems();
		ArrayList<String> tableViewList = new ArrayList<>();
		tableViewList.add("updateOrderReceived");
		tableViewList.add("orders");
		int selectedID = ordersTable.getSelectionModel().getSelectedIndex();
		if(selectedID == -1)
		{
			savedSuccessfully.setVisible(false);
			noOrderSelected.setVisible(true);
			noOrderSelected.setText("you must select an order");
		}
		else {
			noOrderSelected.setVisible(false);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Order Confirmation Alert");
			alert.setContentText("Do you want to CONFIRM receiving this order?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				savedSuccessfully.setText("Saved Successfully");
				savedSuccessfully.setVisible(true);
				Order addedItem = ordersTable.getItems().get(selectedID);
				tableViewList.add(addedItem.getOrderNumber());
			    tableViewList.add(addedItem.getEstimatedDate());
			    tableViewList.add(addedItem.getTotalPrice());
			    tableViewList.add(addedItem.getOrderStatus());
			    tableViewList.add("Received");
				ClientUI.chat.accept(tableViewList);
			}
		}
		refreshBtn(event);
	}
	
	/**
	* This method refreshes the orders table by clearing the items in the table, loading the columns, clearing the ordersList, and loading the orders table from the database.
	*
	* @param event ActionEvent that triggers the method
	* @throws Exception if there is an error when loading the orders table from the database
	*/
	public void refreshBtn(ActionEvent event) throws Exception {
	    ordersTable.getItems().clear();
	    LoadColumns();
	    ordersList.clear();
	    loadOrdersTableFromDB();
	}
}
