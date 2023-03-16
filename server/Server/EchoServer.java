// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.DBController;
import logic.Client;
import logic.Item;
import logic.MyFile;
import ocsf.server.*;
import serverTest.IDateAndTime;
import serverTest.RegularDateAndTime;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author suhel
 * @author amne
 * @author qamar
 * @author daniel
 * @author raed
 * @author shokry
 */

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	public static ArrayList<Client> clients = new ArrayList<>();

	// raed
	public static ArrayList<ClienClass> rClients = new ArrayList<>();

	public static ArrayList<Item> item = new ArrayList<>();
	ObservableList<Item> list = FXCollections.observableArrayList();
	IDateAndTime iDateAndTime = new RegularDateAndTime();

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	// public static Subscriber [] subscribers=new Subscriber[4];
	private DBController myDB;

	public EchoServer(int port) {
		super(port);
		myDB = DBController.getInstance();
		myDB.connectToDB();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("c " + this.getNumberOfClients());
		System.out.println("state " + client.getState());
		System.out.println("client" + client);
		ArrayList<String> msgFromClient = new ArrayList<>();
		ArrayList<String> msgToClient = new ArrayList<>();
		if (msg instanceof String) { //// this case cannot be possible because accept method is arrayList
			if (msg.equals("dis"))
				System.out.println("disconected");
		}
		if (msg instanceof ArrayList) {
			msgFromClient = (ArrayList<String>) msg;
			String store = msgFromClient.get(0).substring(msgFromClient.get(0).length() - 5,
					msgFromClient.get(0).length());
			System.out.println("Message received: " + msgFromClient.toString() + " from " + client);
			//////////// raed////////
			if (msgFromClient.get(0).equals("newClient")) {
				String[] temp = client.toString().split(" ");

				System.out.println("temp" + temp[0]);
				clients.add(new Client(temp[1], msgFromClient.get(1)));
				System.out.println("check:" + temp[1] + " " + msgFromClient.get(1));

				ClienClass newClient = new ClienClass(client, temp[1], "Connected", temp[0]);
				rClients.add(newClient);

				try {
					client.sendToClient((Object) "Connected");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// Raed
			// logout
			if (msgFromClient.get(0).equals("logOut")) {
				for (int i = 0; i < rClients.size(); i++) {
					if (rClients.get(i).getClient().equals(client)) {
						rClients.get(i).setUserName(null);
					}
				}
				msgToClient.add("logout");
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("login")) {
				// firstly check if the user is already loged in
				try {
					msgToClient = myDB.checkValidUsernameAndPassword(msgFromClient.get(1), msgFromClient.get(2));
					if (msgToClient.get(1) != "NoFound") {

						for (int j = 0; j < rClients.size(); j++) {
							if (rClients.get(j).userName != null) {
								if (rClients.get(j).userName.equals(msgFromClient.get(1))
										&& rClients.get(j).getClient().isAlive()) {
									ArrayList<String> loginMsg = new ArrayList<>();
									loginMsg.add("login");
									loginMsg.add("user Already logged in");
									msgToClient = loginMsg;
								}
							}

						}
						if (msgToClient.get(1) != ("user Already logged in")) {
							for (int j = 0; j < rClients.size(); j++) {
								if (rClients.get(j).client.equals(client)) {
									rClients.get(j).setUserName(msgFromClient.get(1));
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					System.out.println("Couldn`t send to client");
					e.printStackTrace();
				}
			}
			/*
			 * initiating a discount send to data base
			 */
			// Raed
			if (msgFromClient.get(0).equals("discount")) {
				msgToClient.add("discount");
				try {
					boolean x = myDB.initiatingDiscount(this, msgFromClient);
					if (x == true)
						msgToClient.add("updated");
					else {
						msgToClient.add("Order not found");
						System.out.println("no");
					}
				} catch (Exception e) {
					e.getMessage();
					msgToClient.add("Order not found");
				}
				try {
					client.sendToClient(msgToClient);
					return;
				} catch (IOException e) {
					System.out.println("Couldn`t send to client");
					e.printStackTrace();
				}
			}
			/*
			 * get the Item name, the item id is given
			 */
			if (msgFromClient.get(0).equals("discountEnterID")) {
				try {
					System.out.println("Echo server Discount info have been recieved" + msgFromClient);
					msgToClient = myDB.discountEnterID(this, msgFromClient);
				} catch (Exception e) {
					e.getMessage();
				}
				try {
					client.sendToClient(msgToClient);
					return;
				} catch (IOException e) {
					System.out.println("Couldn`t send to client");
					e.printStackTrace();
				}
			}
			/*
			 * raed search for discounts
			 */
			if (msgFromClient.get(0).equals("search for discounts")) {
				msgToClient = myDB.discountsForItem(msgFromClient.get(1), msgFromClient.get(2));
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			 * activate the discount
			 * 
			 */
			if (msgFromClient.get(0).equals("activateDiscount")) {
				if (myDB.activateDiscount(msgFromClient.get(1), msgFromClient.get(2), msgFromClient.get(3)) == true) {
					msgToClient.add("activateDiscount");
					msgToClient.add("OK");
				} else {
					msgToClient.add("activateDiscount");
					msgToClient.add("Error");
				}
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//////////// suhel////////////
			if (msgFromClient.get(0).equals("getAllItems")) {
				try {
					ArrayList<String> itemsFromDB = new ArrayList<>();
					ArrayList<String> photosPath = new ArrayList<>();
					itemsFromDB.add("getAllItems");
					itemsFromDB.addAll(myDB.getItems(msgFromClient.get(1), msgFromClient.get(2), msgFromClient.get(3)));
					for (int i = 7; i < itemsFromDB.size(); i += 7) {
						photosPath.add(itemsFromDB.get(i));
					}
					ArrayList<String> itemsFromDBWithoutPath = new ArrayList<>();
					itemsFromDBWithoutPath.add(itemsFromDB.get(0));
					for (int i = 1; i < itemsFromDB.size(); i++) {
						if (i % 7 != 0)
							itemsFromDBWithoutPath.add(itemsFromDB.get(i));
					}
					if (itemsFromDB.size() > 1) {
						transmitPhotosToClient(client, photosPath);
						client.sendToClient((Object) itemsFromDBWithoutPath);
					} else {
						client.sendToClient("");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("costumerInfo")) {
				try {
					ArrayList<String> costumerInfoFromDB = new ArrayList<>();
					costumerInfoFromDB.add("costumerInfo");
					costumerInfoFromDB.addAll(myDB.getCostumerInfo(this, msgFromClient.get(1)));
					client.sendToClient((Object) costumerInfoFromDB);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			if (msgFromClient.get(0).equals("InsertNewOrder")) {
				try {
					int numberOfOrders = myDB.getNumberOfRowsInTable("orders");
					msgFromClient.add(1, Integer.toString(numberOfOrders + 1));
					int orderCode = 0;
					if (msgFromClient.get(msgFromClient.size() - 4).equals("Pick up later")) {
						orderCode = (int) ((Math.random() * (100000 - 1000)) + 1000);
						orderCode += numberOfOrders;
						msgFromClient.remove(msgFromClient.size() - 4);
						msgFromClient.add(Integer.toString(orderCode));
						myDB.insertOrder(this, msgFromClient);
						client.sendToClient(new Integer(Integer.toString(orderCode)));
					} else {
						msgFromClient.remove(msgFromClient.size() - 4);
						msgFromClient.add(null);
						myDB.insertOrder(this, msgFromClient);
						client.sendToClient("");
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			if (msgFromClient.get(0).equals("UpdateAmountItemsAndCheckThreshold")) {
				try {
					int threshold = myDB.checkAndUpdateStateOfThreshold(msgFromClient.get(1), msgFromClient);
					myDB.updateAmountItems(msgFromClient, threshold);
					client.sendToClient("");
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			if (msgFromClient.get(0).equals("checkIfFirstPayment")) {
				try {
					String firstPayment = myDB.checkIfFirstPayment("'" + msgFromClient.get(1) + "'");
					client.sendToClient(firstPayment);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			if (msgFromClient.get(0).equals("updateFirstPayment")) {
				try {
					myDB.updateFirstPayment("'" + msgFromClient.get(1) + "'");
					client.sendToClient("");
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			if (msgFromClient.get(0).equals("checkOrderCode")) {
				try {
					String ifOrderCodeIsFound = myDB.checkOrderCodeInMachine(msgFromClient);
					client.sendToClient(ifOrderCodeIsFound);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			/////////////// Suhel + Qamar////////////////

			if (msgFromClient.get(0).equals("Stock reports")) {
				msgToClient.add("Stock reports");
				msgToClient.addAll(myDB.getStockReportInfo(msgFromClient));
				try {
					client.sendToClient(msgToClient);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (msgFromClient.get(0).equals("Costumer reports")) {
				msgToClient.add("Costumer reports");
				msgToClient.addAll(myDB.getCostumerReport(msgFromClient));
				try {
					client.sendToClient(msgToClient);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			////////// Qamar////////////
			if (msgFromClient.get(0).equals("updatePermissions")) {
				myDB.updatePermissionsInDB(msgFromClient.get(1));
				try {
					client.sendToClient("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("Get")) {
				msgToClient = myDB.getSubscriberNum(msgFromClient.get(1));
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("UpdatingRoleOfUserInDB")) {
				myDB.updatingRoleOfUserInDB(msgFromClient.get(1), msgFromClient.get(2));
				try {
					client.sendToClient("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// *****************16.1
			if (msgFromClient.get(0).equals("UpdatingRoleAndFirstPaymentOfUserInDB")) {
				myDB.UpdatingRoleAndFirstPaymentOfUserInDB(msgFromClient.get(1));
				try {
					client.sendToClient("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// ***************16.1
			if (msgFromClient.get(0).equals("Search")) {
				msgToClient.add("UserInfo");
				msgToClient.addAll(myDB.showUserInfo(msgFromClient.get(1)));

				try {
					client.sendToClient(msgToClient);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// if (msgFromClient.get(0).equals("Add")) myDB.AddTheData(this, msg);

			if (msgFromClient.get(0).equals("AddMachineThreshold")) {
				msgToClient.add("Thresholdnum");
				msgToClient.add(myDB.checkiflocationinDB(msg));
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			/////////////////
//					if (msgFromClient.get(0).equals("AddMachineThreshold")) {
//						myDB.checkiflocationinDB(this, msg);
//						try {
//							client.sendToClient(5);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
			//
//					}
			if (msgFromClient.get(0).equals("newClient")) {
				String[] temp = client.toString().split(" ");
				clients.add(new Client(temp[1], msgFromClient.get(1)));
				System.out.println(temp[1] + "  " + msgFromClient.get(1));
				try {
					client.sendToClient((Object) "Connected");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// get subscriber number
			if (msgFromClient.get(0).equals("getSubscriberNum")) {
				msgToClient.add("subscriberNumber");
				String subscriberNumber = myDB.AddSubscriberNumberOfUserInDB(msg);
				msgToClient.add(subscriberNumber);
				try {
					client.sendToClient(msgToClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (msgFromClient.get(0).equals("checkSubscriber")) {
				String data = myDB.checkIfSubscriberExistInDB(msg);
				try {
					client.sendToClient(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (msgFromClient.get(0).equals("Orders_report")) {
				msgToClient.add("Orders_report");
				msgToClient.addAll(myDB.getOrdersReportInfo(msgFromClient));
				try {
					client.sendToClient(msgToClient);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (msgFromClient.get(0).equals("WaitingForApproval")) {
				msgToClient.add("WaitForApproval");
				msgToClient.addAll(myDB.getWaitingForApprovalUsers(msgFromClient.get(1)));
				msgToClient.addAll(myDB.getWaitingForApprovalUsers(msgFromClient.get(2)));
				try {
					client.sendToClient(msgToClient);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			////////////////////////////////// danial//////////
			if (store.equals("items"))
				try {
					msgToClient.add("items");
					msgToClient.addAll(myDB.getDataItem(this, msgFromClient.get(0)));
					client.sendToClient(msgToClient);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			if (msgFromClient.get(0).equals("updateOrderReceived")) {
				try {
					myDB.updateOrderReceived(this, "Received", msgFromClient.get(2));
					client.sendToClient("");

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			if (msgFromClient.get(0).equals("updateOrderConfirmation")) {
				try {
					if (msgFromClient.get(1).equals("CONFIRM")) {
						myDB.updateOrderConfirmation(this, "CONFIRMED", msgFromClient.get(2), msgFromClient.get(3));
					} else {
						myDB.updateOrderConfirmation(this, msgFromClient.get(1), msgFromClient.get(2),
								msgFromClient.get(3));
					}
					client.sendToClient("");

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("ordersNumber")) {
				try {
					msgToClient.add("ordersNumber");
					msgToClient.addAll(myDB.getOrdersNumbers(this, msgFromClient.get(1)));
					client.sendToClient(msgToClient);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("ApprovingOrders")) {
				try {
					msgToClient.add("ApprovingOrders");
					msgToClient.addAll(myDB.getOrders(this, msgFromClient.get(1)));
					msgToClient.addAll(myDB.getOrders(this, msgFromClient.get(2)));
					client.sendToClient(msgToClient);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("UpdateInventory")) {
				try {
					myDB.updateInventory(this, msgFromClient.get(1), msg);
					client.sendToClient("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//////////// updated//////////
			if (msgFromClient.get(0).equals("StoreApproved")) {
				try {
					myDB.changeStoreToApproved(this, msgFromClient.get(1));
					client.sendToClient("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("thresholdUpdate")) {
				try {
					msgToClient.add("thresholdUpdate");
					msgToClient.addAll(myDB.thresholdUpdate(this, msgFromClient.get(1)));
					client.sendToClient(msgToClient);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("stockReffiled")) {
				try {
					String storeName = msgFromClient.get(1);
					String[] parts = storeName.split("items");
					String storeLocation = parts[0];
					myDB.updateNeedsReffilling(this, storeLocation);
					client.sendToClient("");

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("storeNeedsReffilingWorker")) {
				try {
					msgToClient.add("storeNeedsReffilingWorker");
					msgToClient.addAll(myDB.getWhichStoresNeedsRefillingWorker(this));
					client.sendToClient(msgToClient);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (msgFromClient.get(0).equals("storeNeedsReffiling")) {
				try {
					msgToClient.add("storeNeedsReffiling");
					////////////////
					for (int i = 1; i <= 2; i++) {
						msgToClient.addAll(myDB.getWhichStoresNeedsRefilling(this, msgFromClient.get(i)));
					}
					///////////////
					client.sendToClient(msgToClient);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			//////////////// updated////////////
		}
	}

	private void transmitPhotosToClient(ConnectionToClient client, ArrayList<String> photosPath) {

		try {
			ArrayList<File> newFiles = new ArrayList<>();
			ArrayList<MyFile> messages = new ArrayList<>();
			ArrayList<byte[]> mybytearrays = new ArrayList<>();
			ArrayList<FileInputStream> fis = new ArrayList<>();
			ArrayList<BufferedInputStream> bis = new ArrayList<>();
			for (int i = 0; i < photosPath.size(); i++) {
				newFiles.add(new File(photosPath.get(i)));
				messages.add(new MyFile(newFiles.get(i).getName()));
				mybytearrays.add(new byte[(int) newFiles.get(i).length()]);
				fis.add(new FileInputStream(newFiles.get(i)));
				bis.add(new BufferedInputStream(fis.get(i)));
				messages.get(i).initArray(mybytearrays.get(i).length);
				messages.get(i).setSize(mybytearrays.get(i).length);
				bis.get(i).read(messages.get(i).getMybytearray(), 0, mybytearrays.get(i).length);
			}
			client.sendToClient(messages);
		} catch (Exception e) {
			System.out.println("Error send Files to Client");
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	public void serverStarted() {
		DBController.msgFromDb += "Server listening for connections on port " + getPort();
		createMonthlyReportsAutomatically();
	}
	
	private void createMonthlyReportsAutomatically() {
		ArrayList<String> lcationMachines = new ArrayList<>();
		lcationMachines.add("haifa");
		lcationMachines.add("karmiel");
		lcationMachines.add("beersheva");
		lcationMachines.add("eilat");
		lcationMachines.add("abudhabi");
		lcationMachines.add("dubai");

		//Thread thread = new Thread(new Runnable() {

			//@Override
			//public void run() {

				//while (true) { // instead of the loop must use the technique in the lessons

					Calendar calendar = Calendar.getInstance();
					
					calendar.set(iDateAndTime.getYear(), iDateAndTime.getMonth()-1,
							iDateAndTime.getDay());
					
					String lastMonth = Month.of(iDateAndTime.getMonth()).name();
					String lastYear = Integer.toString(iDateAndTime.getYear());
					if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE)) {
						while (iDateAndTime.getHour() != 23 || iDateAndTime.getMinutes() != 59
								|| iDateAndTime.getSeconds() != 59) {
						}
						createTheThreeReports(lcationMachines, lastYear, lastMonth.toLowerCase());
					}
//					try {
//						Thread.sleep(86400000);// sleep for a day
//					} catch (InterruptedException e) {
//					}
				//}
			//}
		//});
		//thread.start();
	}

	// create reports for the last month and year and zeroing the tables of the
	// machines
	private void createTheThreeReports(ArrayList<String> lcationMachines, String lastYear, String lastMonth) {
		for (int i = 0; i < lcationMachines.size(); i++) {
			ArrayList<String> msg = new ArrayList<>();
			msg.add(lcationMachines.get(i));
			msg.add(lastYear);
			msg.add(lastMonth.toLowerCase());
			myDB.createStockReportInDB(msg);
			/*myDB.createOrderReportInDB(msg);
			myDB.createCostumerReportInDB(msg)*/
			msg.clear();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	public IDateAndTime getRegularDateAndTime() {
		return iDateAndTime;
	}

	public void setRegularDateAndTime(IDateAndTime regularDateAndTime) {
		this.iDateAndTime = regularDateAndTime;
	}
}
//End of EchoServer class
