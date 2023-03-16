package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import clientTest.IClientUIChatAccept;
import clientTest.RegularClientUI;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Raeed Ataria
 *
 */

public class logInController implements Initializable {
	IClientUIChatAccept clientUIChatAccept = new RegularClientUI();

	@FXML
	private Button Button_Login;

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label loginMsg;
	@FXML
	private ImageView QR;
	Image QRImage = new Image(getClass().getResourceAsStream("qr_code.png"));

	/**
	 * get the username amd the password and call the function that do the login
	 * 
	 * @param event the event that triggers this�method.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void loginButtonOnAction(Event event) throws IOException, InterruptedException {
		if(!isValid(username.getText(), password.getText()))
			loginMsg.setText("Please enter the password and the username!");
		else {
			ArrayList<String> inputUsernameAndPasswor = new ArrayList<>();
			inputUsernameAndPasswor.add("login");
			inputUsernameAndPasswor.add(username.getText());
			inputUsernameAndPasswor.add(password.getText());
			login(inputUsernameAndPasswor);
		}
	}
	 /**
	  * function that checks if the username and the password are valid
	  * @param username
	  * @param password
	  * @return false if one of them is empty, otherwise true
	  */
	private boolean isValid(String username, String password) {
		if(username.equals("") || password.equals(""))
			return false;
		return true;
	}
	
	/**
	 * check the permeations of the user and return the name of relevant page name
	 * 
	 * @throws IOException
	 */
	private void Permation() throws IOException {
		String role = ChatClient.u1.getRole();
		try {
			switch (role) {
			case "Costumer":
				CostumerHomePageController aFrame2 = new CostumerHomePageController();
				aFrame2.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "CEO":
				CEOController aFrame10 = new CEOController();
				aFrame10.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Subscriber":
				CostumerHomePageController aFrame3 = new CostumerHomePageController();
				aFrame3.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Marketing_Manager":
				MarketingManagerController aFrame = new MarketingManagerController();
				aFrame.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Marketing_Employee":
				MarketingEmployeeController aFrame8 = new MarketingEmployeeController();
				aFrame8.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "ShippingOperator":
				RegionalShippingHomePageController aFrame7 = new RegionalShippingHomePageController();
				aFrame7.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Store_Employee":
				StoreEmployeeHomePageController aFrame5 = new StoreEmployeeHomePageController();
				aFrame5.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Costumer_Service_Employee":
				CostumerServiceWorkerPage aFrame1 = new CostumerServiceWorkerPage();
				aFrame1.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "Regional_Manager":
				ManagerPageController aFrame6 = new ManagerPageController();
				aFrame6.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "WaitForAprroval":
				UserHomePageController aFrame15 = new UserHomePageController();
				aFrame15.start((Stage) loginMsg.getScene().getWindow());
				break;
			case "NULL":
				UserHomePageController aFrame16 = new UserHomePageController();
				aFrame16.start((Stage) loginMsg.getScene().getWindow());
				break;
			default:
				// code block
			}
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param inputUsernameAndPasswor array list that contains the user name and
	 *                                password
	 * @throws IOException
	 */
	public void login(ArrayList<String> inputUsernameAndPasswor) throws IOException {
		String properLabel = checkAndSetProperLabelForLogin(inputUsernameAndPasswor);
		if(properLabel.equals("login succeeded")) {
			Button_Login.getScene().getWindow().hide(); // hiding primary window
			Permation();
		}
		else if(properLabel.equals("Not a subscriber")) {
			loginMsg.setTextFill(Color.RED);
			loginMsg.setText(properLabel);
		}
		else {
			loginMsg.setText(properLabel);
		}
	}
	
	/**
	 * function that returns the proper text that represents the input data
	 * @param inputUsernameAndPasswor
	 * @return String - proper text that represents the input data
	 */
	private String checkAndSetProperLabelForLogin(ArrayList<String> inputUsernameAndPasswor) {
		clientUIChatAccept.ClientUIChatAcceptMethod(inputUsernameAndPasswor);
		if (clientUIChatAccept.getMsgRecieved().equals("username or password are not correct try again!")) {
			return "username or password are not correct";
		} 
		else if (clientUIChatAccept.getMsgRecieved().equals("user Already logged in")) {
			return "user Already logged in";
		} 
		else if (!clientUIChatAccept.getUser().getRole().equals("Subscriber")
				&& clientUIChatAccept.getUser().getUserName().equals("costumer123")) {
			ArrayList<String> list = new ArrayList<>();
			clientUIChatAccept.setMsgRecieved("");
			list.add("logOut");
			clientUIChatAccept.ClientUIChatAcceptMethod(list);
			return "Not a subscriber";
		} 
		else {
			return "login succeeded";
		}
	}

	/**
	 * login for subscriber using the qr code
	 * 
	 * @throws IOException
	 */
	public void LoginButtonForSubscriber() throws IOException {
		ArrayList<String> inputUsernameAndPasswor = new ArrayList<>();
		inputUsernameAndPasswor.add("login");
		inputUsernameAndPasswor.add("costumer123");
		inputUsernameAndPasswor.add("123456");
		login(inputUsernameAndPasswor);
	}

	/**
	 * start login page
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/loginFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);

		primaryStage.show();
		// image.setPickOnBounds(true);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	/**
	 * Initialise the login page
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		QR.setImage(QRImage);
		QR.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					LoginButtonForSubscriber();
				} catch (IOException e) {
					e.printStackTrace();
				}
				event.consume();
			}
		});
	}

	public IClientUIChatAccept getClientUIChatAccept() {
		return clientUIChatAccept;
	}

	public void setClientUIChatAccept(IClientUIChatAccept clientUIChatAccept) {
		this.clientUIChatAccept = clientUIChatAccept;
	}
}
