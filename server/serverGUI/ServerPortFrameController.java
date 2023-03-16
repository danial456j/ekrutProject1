package serverGUI;

import java.awt.TextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdbc.DBController;
import logic.Client;
import logic.Subscriber;
import Server.ClienClass;
import Server.EchoServer;
import Server.ServerUI;

public class ServerPortFrameController  implements Initializable {
	
	
	String temp="";
	
	@FXML
	private TableView<ClienClass> tableView;
	@FXML
	private TableColumn<ClienClass,String> columnIP;
	@FXML
	private TableColumn<ClienClass,String> columnHost;
	@FXML
	private TableColumn<ClienClass,String> columnStatus;
	
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnStart= null;
	@FXML
	private Button btnRefreshTable = null;
	@FXML
	private Button importUserBTN;
	@FXML
	private Label lbllist;
	@FXML
	private Label msgFromImport;
	@FXML
	private TextField portxt;
	@FXML
	private PasswordField password;
	@FXML
	private Label consolemsg;
	
	ObservableList<ClienClass> list;
	
	private String getport() {
		return portxt.getText();			
	}
	public void importData() {
		DBController.getInstance().importUsers();	
		 importUserBTN.setVisible(false);
		 msgFromImport.setTextFill(Color.ORANGE);
		 msgFromImport.setText("Data was imported");
	}
	public void StartBtn(ActionEvent event) throws Exception {
		String p;
		if(btnStart.getText().equals("Disconect")) {
		        btnStart.setText("Connect");
		        disconect();		   
		        return;
		        }
		btnStart.setText("Disconect");
		DBController.getInstance().setDBPassword(password.getText());
		DBController.getInstance().connectToDB();
		p=getport();
		if(p.trim().isEmpty()) {
			System.out.println("You must enter a port number");					
		}
		else
		{
			getRefreshTable();			
			ServerUI.runServer(p);
			consolemsg.setText(DBController.msgFromDb);		    
			//msg.setText(DBController.msgFromDb);
		}
	}
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/serverGUI/ServerPort.fxml"));				
		Scene scene = new Scene(root);
		//Image EkrutIcon = new Image("logo.png");
		scene.getStylesheets().add(getClass().getResource("/serverGUI/ServerPort.css").toExternalForm());
		//primaryStage.getIcons().add(EkrutIcon);
		primaryStage.setTitle("Client");
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
	public void getRefreshTableBtn(ActionEvent event) throws Exception {
		getRefreshTable();
		//System.out.println("mine"+EchoServer.rClients.get(0).getIP());
	}	
	public void getRefreshTable() {
		tableView.getItems().clear();
		LoadColumns();
		for(int i=0;i<EchoServer.rClients.size();i++) {
			System.out.println("s " +EchoServer.rClients.get(i).getClient().isAlive()); 
		  if(EchoServer.rClients.get(i).getClient().isAlive()==false)
			EchoServer.rClients.get(i).setStatus("Disconected");	
		}
		list = FXCollections.observableArrayList(EchoServer.rClients);
		tableView.setItems(list);
	}
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);			
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoadColumns();
		
	}	
	public void LoadColumns() {
		columnIP.setCellValueFactory(new PropertyValueFactory<>("IP"));
		columnHost.setCellValueFactory(new PropertyValueFactory<>("msg"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
	}
	public void disconect() {
		try {
			ServerUI.sv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 DBController.msgFromDb="server stopped listening to clients";
		 consolemsg.setText(DBController.msgFromDb);
	     btnStart.setText("Connect");
	}
}