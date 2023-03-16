package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.Item;
import logic.ItemInBill;
import logic.TimerTwoMinutes;

/**
 * 
 * @author Suhel
 * @author Amne
 *
 */

public class BillController implements Initializable {
	@FXML
	private Button confirm=null;
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	@FXML
	private Button back=null;
	@FXML
	private Button cancel=null;
	@FXML
	private Label total = new Label();
	@FXML
	private TableView<ItemInBill> cartTable = new TableView<>();
	@FXML
	private TableColumn<ItemInBill,ImageView> imageColumnCart;
	@FXML
	private TableColumn<ItemInBill,Integer> totalPriceColumnCart;
	@FXML
	private TableColumn<ItemInBill,String> idColumnCart;
	@FXML
	private TableColumn<ItemInBill,String> QuantityColumnCart;
	@FXML
	ImageView backImageView;
	//Image backImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));

	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));

	
	ObservableList<ItemInBill> listCart;
	
	ArrayList<Item> chosenItems;
	ArrayList<Item> catalogItems;

	public static ArrayList<ItemInBill> itemsInCartWithoutDuplicates = new ArrayList<>();
	
	String OrderType;
	
	/**
	 * initializing all the required data for this class
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logo.setImage(logoImage);
		back = new Button();
		
		imageColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		idColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		totalPriceColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		QuantityColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		total.setStyle("-fx-alignment: BASELINE_CENTER");
		LoadColumns();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}
	
	/**
	 * loading the columns of the table
	 */
	public void LoadColumns() {
		imageColumnCart.setCellValueFactory(new PropertyValueFactory<ItemInBill,ImageView>("image"));
		idColumnCart.setCellValueFactory(new PropertyValueFactory<ItemInBill,String>("id"));
		totalPriceColumnCart.setCellValueFactory(new PropertyValueFactory<ItemInBill,Integer>("totalPrice"));
		QuantityColumnCart.setCellValueFactory(new PropertyValueFactory<ItemInBill,String>("quantityInOrderString"));
	}
	
	/**
	 * confirming the bill, and changing the page according to the chosen order type
	 * Remote Order or Immediate Order
	 * @param event
	 * @throws Exception
	 */
	public void confirmBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.thread.stop();
		Stage primaryStage = new Stage();
		ChatClient.primaryStage = primaryStage;
		if(ChooseOrderTypeController.ordertype.equals("Remote Order")) {
			ChooseMethodController method = new ChooseMethodController();
			method.start(primaryStage);
		}
		if(ChooseOrderTypeController.ordertype.equals("Immediate Order")) {
			FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Pane root = loader.load(getClass().getResource("/clientGUI/Payment.fxml").openStream());
			PaymentController paymentController = loader.getController();
			paymentController.loadCustomerInfo(ChatClient.costumerInfo);
			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			     @Override
			     public void handle(WindowEvent e) {
			      Platform.exit();
			      System.exit(0);
			     }
			   });
			primaryStage.show();
		}
		
	}
	
	/**
	 * canceling the bill and returning to the create new order page
	 * @param event
	 * @throws Exception
	 */
	public void cancelBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		Stage primaryStage = new Stage();
		ChatClient.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Pane root = loader.load(getClass().getResource("/clientGUI/CreateNewOrder.fxml").openStream());
		CreateNewOrderController createOrderController = loader.getController();
		createOrderController.loadCart(chosenItems, itemsInCartWithoutDuplicates);
		createOrderController.loadTotal(Integer.toString(CreateNewOrderController.newTotal));
		createOrderController.loadGivenCatalogItems(catalogItems);
		Scene scene = new Scene(root);		
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
		primaryStage.show();
	}
	
	/**
	 * loading the cart and the bill
	 * @param chosenItems
	 * @param itemsInCartNoDuplicates
	 */
	public void loadTable(ArrayList<Item> chosenItems, ArrayList<ItemInBill> itemsInCartNoDuplicates) {
		this.chosenItems = chosenItems;
		itemsInCartWithoutDuplicates = itemsInCartNoDuplicates;
		listCart = FXCollections.observableArrayList(itemsInCartWithoutDuplicates);
		cartTable.setItems(listCart);
	}
	
	/**
	 * loading the total price of the bill
	 * @param total
	 */
	public void loadTotal(String total) {
		this.total.setText(total);
		ChooseOrderTypeController.orderDetails.add(total);
	}
	
	/**
	 * loading the catalog items in case of returning to create new order page
	 * @param catalogItems
	 */
	public void loadGivenCatalogItems(ArrayList<Item> catalogItems) {
		this.catalogItems = catalogItems;
	}
	
	/**
	 * logOut method is used to log out the user by sending a logOut message to the server.
	 * It also hides the current window and opens the login window for the user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}
	
	/**
	 * changing the page to the home page
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
}
