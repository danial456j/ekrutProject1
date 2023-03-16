package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import logic.CustomerReport;
import logic.ItemInBill;
import logic.StockReport;
import ocsf.server.AbstractServer;

/**
 * 
 * @author suhel
 * @author raed
 * @author amne
 * @author qamar
 * @author daniel
 * @author shokry
 *
 */
public class DBController {
	private static DBController onlyInstance;
	public static String msgFromDb = "";
	private static Connection conn;
	private String password;

	private DBController() {

	}

	/**
	 * getting instance of the Singelton DB
	 * 
	 * @return
	 */
	public static DBController getInstance() {
		if(onlyInstance == null)
			onlyInstance = new DBController();
		return onlyInstance;
	}

	/**
	 * setting the password of the DB
	 * 
	 * @param password password of DB
	 */
	public void setDBPassword(String password) {
		this.password = password;
	}

	/**
	 * getting the password of the DB
	 * 
	 * @return
	 */
	public String getDBPassword() {
		return this.password;
	}

	/**
	 * connecting to DB
	 */
	public void connectToDB() {
		try {
			msgFromDb = "";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
			msgFromDb += "Driver definition succeed";
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			msgFromDb += "Driver definition failed";
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ekrut?serverTimezone=IST", "root", "@Suhel123");
			System.out.println("SQL connection succeed");
			msgFromDb += "\n SQL connection succeed";
			// printCourses(conn);
		} catch (SQLException ex) {/* handle any errors */
			System.out.println(password);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

/////////////// raed/////////////////
/////////////// raed/////////////////
	/**
	 * imporitng the user from the external DB to ekrut DB
	 * 
	 * @author Raeed
	 * 
	 */
	public void importUsers() {
		ArrayList<String> usersList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//get the the all the user information from the external DB
		try {
			pstmt = conn.prepareStatement("SELECT * FROM usersexternal");
			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			int count = 0;
			while (rs.next()) {
				count++;
				usersList.add(rs.getString("username"));
				usersList.add(rs.getString("password"));
				usersList.add(rs.getString("firstName"));
				usersList.add(rs.getString("lastName"));
				usersList.add(rs.getString("role"));
				usersList.add(rs.getString("email"));
				usersList.add(rs.getString("phoneNumber"));
				usersList.add(rs.getString("creditCard"));
				usersList.add(rs.getString("isLoggedIn"));
				usersList.add(rs.getString("id"));
				usersList.add(rs.getString("permissions"));
				usersList.add(rs.getString("storeName"));
				usersList.add(rs.getString("expirationDate"));
				usersList.add(rs.getString("cvv"));
				usersList.add(rs.getString("city"));
				usersList.add(rs.getString("street"));
				usersList.add(rs.getString("house_number"));
				usersList.add(rs.getString("receiver_name"));
				usersList.add(rs.getString("receiver_phone"));
				usersList.add(rs.getString("firstPayment"));
			}
			pstmt.close();
			rs.close();
			conn.setAutoCommit(false);
			PreparedStatement ps1 = null;
			System.out.println(usersList);
			// insert the the user information from the external DB table to Ekrut users
			// table
			String UPDATE_QUERY = "INSERT INTO users (username, password,firstName,lastName,role,email,phoneNumber,creditCard,isLoggedIn,id,permissions,storeName,expirationDate,cvv,city,street,house_number,receiver_name,receiver_phone,firstPayment) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = conn.prepareStatement(UPDATE_QUERY);
			for (int i = 0; i < count * 20; i = i + 20) {
				System.out.println("hi");
				ps1.setString(1, usersList.get(i));
				ps1.setString(2, usersList.get(i + 1));
				ps1.setString(3, usersList.get(i + 2));
				ps1.setString(4, usersList.get(i + 3));
				ps1.setString(5, usersList.get(i + 4));
				ps1.setString(6, usersList.get(i + 5));
				ps1.setString(7, usersList.get(i + 6));
				ps1.setString(8, usersList.get(i + 7));
				ps1.setString(9, usersList.get(i + 8));
				ps1.setString(10, usersList.get(i + 9));
				ps1.setString(11, usersList.get(i + 10));
				ps1.setString(12, usersList.get(i + 11));
				ps1.setString(13, usersList.get(i + 12));
				ps1.setString(14, usersList.get(i + 13));
				ps1.setString(15, usersList.get(i + 14));
				ps1.setString(16, usersList.get(i + 15));
				ps1.setString(17, usersList.get(i + 16));
				ps1.setString(18, usersList.get(i + 17));
				ps1.setString(19, usersList.get(i + 18));
				ps1.setString(20, usersList.get(i + 19));
				ps1.addBatch();
			}
			ps1.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.getStackTrace();
		}
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * check the the username and the password that were given by the user exists in
	 * DB
	 * 
	 * @author Raeed Ataria
	 * @param server   the server
	 * @param userName the input user name
	 * @param password the password that the user put
	 * @return array list the information of the client if the username and passowrd
	 *         are correct, else return array list that contains that the username
	 *         with password are not in DB
	 */
	public ArrayList<String> checkValidUsernameAndPassword(String userName, String password) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> info = new ArrayList<>();
		if(userName == null || password == null) {
			info.add("login");
			info.add("NotFound");
			return info;
		}
		if(userName.equals("") || password.equals("")) {
			info.add("login");
			info.add("NotFound");
			return info;
		}

		try {
			// get the info of the user with the given username and password
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password=? ");
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next() == false) {
				info.add("login");
				info.add("NotFound");
				return info;
			} else {
				info.add("login");
				info.add(rs.getString("firstName"));
				info.add(rs.getString("lastName"));
				info.add(rs.getString("id"));
				info.add(rs.getString("role"));
				info.add(rs.getString("storeName"));
				info.add(userName);
				info.add(rs.getString("email"));
				info.add(rs.getString("phoneNumber"));
				info.add(rs.getString("permissions"));
				rs.close();
				pstmt.close();
				return info;
			}
		} catch (SQLException e) {
			
		}
		return null;

	}

	/**
	 * Activate the Discount the function
	 * 
	 * @param itemID       the item id
	 * @param discountName the discount name
	 * @param StoreName    the store branch
	 * @return false when the discount name isn`t in the DB, otherwise true
	 */
	public boolean activateDiscount(String itemID, String discountName, String StoreName) {
// get the info about the discount
		ArrayList<String> info = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//get the information of the discount from the discounts table
		try {
			pstmt = conn
					.prepareStatement("SELECT * FROM discounttable WHERE itemID = ? AND DiscountName=? AND branch=?");
			pstmt.setString(1, itemID);
			pstmt.setString(2, discountName);
			pstmt.setString(3, StoreName);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				info.add(rs.getString("DiscountName"));
				info.add(rs.getString("salePrice"));
				info.add(rs.getString("EndDate"));
				info.add(rs.getString("EndTime"));
				info.add(rs.getString("StartDate"));
				info.add(rs.getString("StartTime"));
			}
			if (i == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		try {
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// set the discount information for the item in the branch items table
		String sql = "UPDATE ";
		sql += StoreName;
		sql += " SET DiscountName=?, salePrice=?, EndDate=?,EndTime=?,StartDate=?,StartTime=? WHERE id=?";
		try {
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			pstmt1.setString(1, info.get(0));
			pstmt1.setString(2, info.get(1));
			pstmt1.setString(3, info.get(2));
			pstmt1.setString(4, info.get(3));
			pstmt1.setString(5, info.get(4));
			pstmt1.setString(6, info.get(5));
			pstmt1.setString(7, itemID);
			if (pstmt1.executeUpdate() == 0) {
				pstmt1.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * initiating a discount get the the discount info set them in discount table
	 * 
	 * @param server the server
	 * @param list   list of the discount information
	 * @return true if the discount was added else false;
	 */
	public boolean initiatingDiscount(AbstractServer server, ArrayList<String> list) {
		String sql = "INSERT INTO ";
		sql += "discounttable";
		sql += "(DiscountName, salePrice,EndDate,EndTime,itemID,StartDate,StartTime,branch) VALUES (?, ?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
			pstmt1.setString(1, list.get(1));
			pstmt1.setString(2, list.get(2));
			pstmt1.setString(3, list.get(3));
			pstmt1.setString(4, list.get(4));
			pstmt1.setString(5, list.get(5));
			pstmt1.setString(6, list.get(6));
			pstmt1.setString(7, list.get(7));
			pstmt1.setString(8, list.get(8));
			pstmt1.executeUpdate();
			pstmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * get the name and price of the item in the branch
	 * 
	 * @param echoServer    the server
	 * @param msgFromClient list that have the item id
	 * @return list that have name and price of the item if the item in DB, else a
	 *         list that says that there is no discount for this item
	 */

	public ArrayList<String> discountEnterID(AbstractServer echoServer, ArrayList<String> msgFromClient) {
		ArrayList<String> info = new ArrayList<String>();
		info.add("discountEnterID");
		ResultSet rs = null;
		String sql = "SELECT * FROM ";
		sql += msgFromClient.get(2);
		sql += " WHERE id = ?";
		System.out.println(sql);
		try {
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			pstmt1.setString(1, msgFromClient.get(1));
			rs = pstmt1.executeQuery();
			if (rs.next() == false) {
				info.add("false");
				return info;
			}
			info.add("true");
			info.add(msgFromClient.get(1));
			info.add(rs.getString("name"));
			info.add(rs.getString("price"));
			rs.close();
			pstmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * get the discounts for an item
	 * 
	 * @param itemID the item id
	 * @param branch the branch
	 * @return list for all the discount for the item
	 */
	public ArrayList<String> discountsForItem(String itemID, String branch) {
// check if the the itemID in DB, and set all the discounts in rs
		ArrayList<String> discounts = new ArrayList<>();
		discounts.add("search for discounts");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM discounttable WHERE itemID = ? AND  branch=?");
			pstmt.setString(1, itemID);
			pstmt.setString(2, branch);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				discounts.add(rs.getString("DiscountName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return discounts;

	}

	/**
	 * @author Suhel
	 * @author Amne getting the catalog of the items while considering the sales of
	 *         items and availability of item
	 * @param tableName            table name
	 * @param costumerOrSubscriber costumer or subscriber
	 * @param registeredMachine    location of the machine
	 * @return
	 */
	public ArrayList<String> getItems(String tableName, String costumerOrSubscriber, String registeredMachine) {
		ArrayList<String> items = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT* FROM " + tableName + "items" + " WHERE NOT (amount_in_stock = '0')";
			ResultSet rs = stmt.executeQuery(query);
			String id, imageName, imagePath, OriginalPrice, priceAfterDiscount, amount, name;

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		    
			LocalTime currentTime = LocalTime.now();
			LocalTime startTime;
			LocalTime endTime;

			Date currentDate = date.parse(LocalDate.now().toString());
			Date startDate;
			Date endDate;

			while (rs.next()) {
				id = rs.getString("id");
				items.add(id);
				imageName = rs.getString("image_name");
				items.add(imageName);

				String discountName = rs.getString("DiscountName");

				OriginalPrice = rs.getString("price");

				if ((costumerOrSubscriber.equals("Subscriber") || costumerOrSubscriber.equals("Worker"))
						&& discountName != null && !discountName.equals("") && tableName.equals(registeredMachine)) {
					priceAfterDiscount = rs.getString("salePrice");
					startTime = LocalTime.parse(rs.getString("StartTime"));
					endTime = LocalTime.parse(rs.getString("EndTime"));
					startDate = date.parse(rs.getString("StartDate"));
					endDate = date.parse(rs.getString("EndDate"));
					if(returnDateAndTimeInRange(startDate, currentDate, endDate, startTime, currentTime, endTime)) {
						items.add(OriginalPrice);
						items.add(priceAfterDiscount);
					} else {
						items.add(OriginalPrice);
						items.add("");
					}
				} else {
					items.add(OriginalPrice);
					items.add("");
				}

				amount = rs.getString("amount_in_stock");
				items.add(amount);
				name = rs.getString("name");
				items.add(name);
				imagePath = rs.getString("image_path");
				items.add(imagePath);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(items);
		return items;
	}
	
	private boolean returnDateAndTimeInRange(Date startDate, Date currentDate, Date endDate, LocalTime startTime, LocalTime currentTime, LocalTime endTime) {
		if(currentDate.compareTo(startDate)>0 && endDate.compareTo(currentDate)>0) {
			return true;
		}
		if(currentDate.compareTo(startDate)==0) {
			if(currentTime.compareTo(startTime)<0)
				return false;
			if(endTime.compareTo(currentTime)>=0)
				return true;
		}
		if(endDate.compareTo(currentDate)==0) {
			if(endTime.compareTo(currentTime)<0)
				return false;
			if(currentTime.compareTo(startTime)>=0)
				return true;
		}
		return false;
	}

	/**
	 * getting the informations of entered username
	 * 
	 * @param server   server
	 * @param username username
	 * @return arrayList that contains all the inforamtions
	 */
	public ArrayList<String> getCostumerInfo(AbstractServer server, String username) {
		ArrayList<String> info = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT* FROM users WHERE username = " + username;
			ResultSet rs = stmt.executeQuery(query);
			String city, street, houseNumber, receiverName, receiverPhone;
			String creditCard, expirationDate, CVV;

			rs.next();
			city = rs.getString("city");
			info.add(city);
			street = rs.getString("street");
			info.add(street);
			houseNumber = rs.getString("house_number");
			info.add(houseNumber);
			receiverName = rs.getString("receiver_name");
			info.add(receiverName);
			receiverPhone = rs.getString("receiver_phone");
			info.add(receiverPhone);
			creditCard = rs.getString("creditCard");
			info.add(creditCard);
			expirationDate = rs.getString("expirationDate");
			info.add(expirationDate);
			CVV = rs.getString("cvv");
			info.add(CVV);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * insert the order into the orders
	 * 
	 * @param server       server
	 * @param orderDetails the details of the order
	 */
	public void insertOrder(AbstractServer server, ArrayList<String> orderDetails) {
		System.out.println(orderDetails);
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO orders (orderNumber, CustomerId, CustomerName,phoneNumber,Branch,Components,TotalPrice,DeliveryAddress,SupplyType,OrderStatus,OrderDate,EstimatedOrderDate,OrderReceived,CustomerConfirmation,was_pickedUp, Month, Year,OrderCode) VALUES (?, ?, ?,?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, orderDetails.get(1));
			pstmt.setString(2, orderDetails.get(2));
			pstmt.setString(3, orderDetails.get(3));
			pstmt.setString(4, orderDetails.get(4));
			pstmt.setString(5, orderDetails.get(5));
			pstmt.setString(6, orderDetails.get(6));
			pstmt.setString(7, orderDetails.get(7));
			pstmt.setString(8, orderDetails.get(8));
			pstmt.setString(9, orderDetails.get(9));
			pstmt.setString(10, orderDetails.get(10));
			pstmt.setString(11, orderDetails.get(11));
			pstmt.setString(12, orderDetails.get(12));
			pstmt.setString(13, orderDetails.get(13));
			pstmt.setString(14, orderDetails.get(14));
			pstmt.setString(15, orderDetails.get(15));
			pstmt.setString(16, orderDetails.get(16));
			pstmt.setString(17, orderDetails.get(17));
			pstmt.setString(18, orderDetails.get(18));

			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * updating the quantity of each item and updating data about the threshold
	 * 
	 * @param orderInfo arrayList containing items and their new quantity
	 * @param threshold the threshold of the machine
	 */
	public void updateAmountItems(ArrayList<String> orderInfo, int threshold) {
		String tableName = orderInfo.get(1) + "items";
		for (int i = 2; i < orderInfo.size(); i += 2) {
			updateAmountForItem(tableName, orderInfo.get(i), orderInfo.get(i + 1), threshold);
		}
	}

	/**
	 * updating amount of item
	 * 
	 * @param tableName table name
	 * @param id        id of item
	 * @param Amount    new amount to update
	 * @param threshold the threshold of the machine
	 */
	private void updateAmountForItem(String tableName, String id, String Amount, int threshold) {
		Statement stmt;
		String query;
		try {
			if (Integer.parseInt(Amount) <= threshold) {
				updateTimesLowedToThreshold(tableName, id);
				if (Integer.parseInt(Amount) == 0)
					updateTimesLowedToZero(tableName, id);
			}

			query = "UPDATE " + tableName + " Set amount_in_stock = " + Amount + " WHERE id=" + id;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * updating the times that the Amount of item drops to zero
	 * 
	 * @param tableName
	 * @param id
	 */
	private void updateTimesLowedToZero(String tableName, String id) {
		Statement stmt;
		try {
			String query = "UPDATE " + tableName + " Set times_that_lowed_to_zero = times_that_lowed_to_zero +1 "
					+ " WHERE id=" + id;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * updating the times that the Amount of item drops to the threshold
	 * 
	 * @param tableName table name
	 * @param id        id of item
	 */
	private void updateTimesLowedToThreshold(String tableName, String id) {
		Statement stmt;
		try {
			String query = "UPDATE " + tableName
					+ " Set times_that_lowed_to_threshold = times_that_lowed_to_threshold +1 " + " WHERE id=" + id;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * checking the amount of item and updates the message to refill the stock of
	 * the machine
	 * 
	 * @param branch    branch
	 * @param orderInfo data about item including the amount
	 * @return the threshold
	 */
	public int checkAndUpdateStateOfThreshold(String branch, ArrayList<String> orderInfo) {
		int thresholdOfBranch = getThreshold(branch);
		for (int i = 3; i < orderInfo.size(); i += 2) {
			if (Integer.parseInt(orderInfo.get(i)) <= thresholdOfBranch) {
				updateMsgtoRefill("'" + branch + "'");
				break;
			}
		}
		return thresholdOfBranch;
	}

	/**
	 * updating the message to refill the machine stock
	 * 
	 * @param branch branch
	 */
	private void updateMsgtoRefill(String branch) {
		Statement stmt;
		try {
			String query = "UPDATE threshold Set needsrefilling = 'stock needs refilling' WHERE Location = " + branch;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * get the threshold of branch
	 * 
	 * @param branch
	 * @return threshold of the entered branch
	 */
	private int getThreshold(String branch) {
		Statement stmt;
		String branchh = "'" + branch + "'";
		try {
			String query = "SELECT thresholdnum FROM threshold WHERE Location = " + branchh;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			return Integer.parseInt(rs.getString(1));

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * get the number of rows in a table
	 * 
	 * @param tableName table name
	 * @return the number of rows
	 */
	public int getNumberOfRowsInTable(String tableName) {
		Statement stmt;
		try {
			String query = "SELECT COUNT(*) FROM " + tableName;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int temp = rs.getInt(1);
			
			rs.close();
			stmt.close();
			return temp;

		} catch (SQLException e) {
		}
		return -1;
	}

	/**
	 * checking if this is the first payment of username
	 * 
	 * @param username username
	 * @return true or false
	 */
	public String checkIfFirstPayment(String username) {
		Statement stmt;
		try {
			String query = "SELECT firstPayment FROM users WHERE username = " + username;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			return rs.getString(1);

		} catch (SQLException e) {

		}
		return "false";
	}

	/**
	 * updating the first payment of username
	 * 
	 * @param username username
	 */
	public void updateFirstPayment(String username) {
		Statement stmt;
		try {
			String query = "UPDATE users Set firstPayment = 'false' WHERE username = " + username;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {

		}
	}

	/**
	 * checks if the order code exists in the machine
	 * 
	 * @param OrderCodeData data about the code number and machine
	 * @return
	 */
	public String checkOrderCodeInMachine(ArrayList<String> OrderCodeData) {
		PreparedStatement stmt;
		ResultSet rs = null;
		String branch = OrderCodeData.get(1);
		String orderCode = OrderCodeData.get(2);

		try {

			stmt = conn.prepareStatement("SELECT was_pickedUp FROM orders WHERE Branch = ? AND OrderCode = ?");
			stmt.setString(1, branch);
			stmt.setString(2, orderCode);
			rs = stmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				return "No Such Code";
			}
			rs.next();
			if (rs.getString("was_pickedUp").equals("true"))
				return "Was Picked Up";

		} catch (SQLException e) {

		}
		updateWasPickedUpOrder(orderCode);
		return "Code is found";

	}

	/**
	 * updating was picked up order for pickUp orders
	 * 
	 * @param orderCode
	 */
	private void updateWasPickedUpOrder(String orderCode) {
		PreparedStatement stmt;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("UPDATE orders Set was_pickedUp = ? WHERE OrderCode = ?");
			stmt.setString(1, "true");
			stmt.setString(2, orderCode);
			stmt.executeUpdate();
		} catch (SQLException e) {

		}
	}

	// ***********************Qamar***************

	// ***********************Qamar***************
	// ************16/1

	/**
	 * AddSubscriberNumberOfUserInDB method check if the ID exist in the DB the
	 * method call the function getNumberOfRowsInTable that calculate the rows in
	 * the subscriber table in data base after calling the function
	 * getNumberOfRowsInTable the method create subscriber number if the ID is not
	 * exist on the DB the method insert into the subscriber table the ID and the
	 * subscriber number
	 * 
	 * @param The ID of the costumer
	 * @return The subscriber number that the method calculate  
	 */
	public String AddSubscriberNumberOfUserInDB(Object msg) {
		PreparedStatement stmt;
		ResultSet rs = null;
		ArrayList<String> temp = (ArrayList<String>) msg;
		String ID = temp.get(1);
		int m = getNumberOfRowsInTable("subscriber") + 1000;
		String newSubscriberNumber = Integer.toString(m);
		System.out.println(newSubscriberNumber + "first");

		try {

			stmt = conn.prepareStatement("SELECT * FROM subscriber WHERE ID = ?");
			stmt.setString(1, ID);
			rs = stmt.executeQuery();
			// rs.next();

			if (rs.next() == false) {
				System.out.println("The Costumer is not in DB(not a subscriber)");
				PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement("insert into subscriber (ID,subscriber_number)values(?,?)");
					pstmt.setString(1, ID);
					pstmt.setString(2, newSubscriberNumber);
					pstmt.executeUpdate();
				} catch (SQLException e) {
				}
			} else {
				System.out.println("The Costumer in DB(not a subscriber)");
			}

		} catch (SQLException e) {
		}
		System.out.println("new subs" + newSubscriberNumber);
		return newSubscriberNumber;

	}

	/**
	 * The method check if the costumer with the entered ID is a subscriber (is in
	 * the subscriber table in data base) if the costumer exist in the data base the
	 * method insert into "info" "NotExist" if the costumer doesn't exist in the
	 * data base the method insert into "info" "Exist"
	 * 
	 * @param The costumer ID
	 * @return string info   
	 */
	public String checkIfSubscriberExistInDB(Object msg) {
		PreparedStatement stmt;
		ResultSet rs = null;
		ArrayList<String> temp = (ArrayList<String>) msg;
		String ID = temp.get(1);
		String info = "";
		try {

			stmt = conn.prepareStatement("SELECT * FROM subscriber WHERE ID = ?");
			stmt.setString(1, ID);
			rs = stmt.executeQuery();

			if (rs.next() == false) {
				System.out.println("The Costumer is not in DB(not a subscriber)");
				info = "NotExist";
			} else {
				System.out.println("The Costumer in DB(a subscriber)");
				info = "Exist";
			}

		} catch (SQLException e) {
		}
		return info;
	}

	/**
	 * This method is used to update the permissions of a user in the database. It
	 * sets the firstPayment field to "true" and the permissions field to "Approve"
	 * for the user with the specified ID.
	 * 
	 * @param ID The ID of the user whose permissions are being updated.
	 */
	public void updatePermissionsInDB(String ID) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("UPDATE users SET firstPayment=?,permissions= ? WHERE id=?");
			stmt.setString(1, "true");
			stmt.setString(2, "Approve");
			stmt.setString(3, ID);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * updatingRoleOfUserInDB method update the role of the costumer with the
	 * entered id in the data base with the entered role
	 * 
	 * @param ID
	 * @param newRole  
	 */
	public void updatingRoleOfUserInDB(String ID, String newRole) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("UPDATE users SET role= ? WHERE id=?");
			stmt.setString(1, newRole);
			stmt.setString(2, ID);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * UpdatingRoleAndThreSholdOfUserInDB update the role to "Costumer" and the
	 * firstPayment to "true" with the entered id  @param ID  
	 */
	public void UpdatingRoleAndFirstPaymentOfUserInDB(String ID) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("UPDATE users SET role= ?,firstPayment= ? WHERE id=?");
			stmt.setString(1, "Costumer");
			stmt.setString(2, "true");
			stmt.setString(3, ID);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * showUserInfo check if the user with the entered id exist in the data base if
	 * the user's id doesn't exist in the data base the method add into the
	 * arrayList info "UserNotExist" and return it if the user's id exist in the
	 * data base the method add into the arrayList info "UserExist" and get the data
	 * from the users table in the data base and insert it to info
	 * 
	 * @param ID @return info   
	 */
	public ArrayList<String> showUserInfo(String ID) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> info = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			// rs.next();

			if (rs.next() == false) {
				System.out.println("The exntered ID was Not Found");
				info.add("UserNotExist");
			} else {
				System.out.println("The exntered ID was Found");
				info.add("UserExist");
				info.add(rs.getString("firstName"));
				info.add(rs.getString("lastName"));
				info.add(rs.getString("id"));
				info.add(rs.getString("phoneNumber"));
				info.add(rs.getString("email"));
				info.add(rs.getString("creditCard"));
				info.add(rs.getString("role"));
				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
		}
		return info;
	}

	/**
	 * checkiflocationinDB method check if the machine location exist in the data
	 * base (in threshold table) if the machine location exist in the data base the
	 * method change the thresholdnum that exist with the new one that we get in
	 * third place in msg if the machine location doesn't exist in the data base we
	 * insert the machine location into Location in data base and thresholdnum into
	 * thresholdnum in db
	 * 
	 * @param msg
	 * @return info  
	 */
	public String checkiflocationinDB(Object msg) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> temp = (ArrayList<String>) msg;
		String info = null;
		String MachineLocation = temp.get(1);
		String thresholdnum = temp.get(2);
		PreparedStatement stmt;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM threshold WHERE Location = ?");
			pstmt.setString(1, MachineLocation);
			rs = pstmt.executeQuery();
			// rs.next();

			if (rs.next() == false) {
				System.out.println("The Location is not in DataBase");
				try {
					stmt = conn.prepareStatement("insert into threshold (Location,thresholdnum)values(?,?)");
					stmt.setString(1, MachineLocation);
					stmt.setString(2, thresholdnum);
					stmt.executeUpdate();
					info = thresholdnum;
				} catch (SQLException e) {
				}
			} else {
				System.out.println("The Location is in DataBase");
				try {
					stmt = conn.prepareStatement("UPDATE threshold SET thresholdnum=? WHERE Location=?");
					stmt.setString(1, thresholdnum);
					stmt.setString(2, MachineLocation);
					stmt.executeUpdate();
					info = thresholdnum;
				} catch (SQLException e) {
				}

			}
		} catch (SQLException e) {

		}
		return info;
	}

	/**
	 * getSubscriberNum method get the subscriber number from the subscriber table
	 * with the entered ID if the id doesn't exist in the data base we insert into
	 * the arrayList "NotFound" if the if exist we insert into the arrayList the
	 * subscriber_number from the db
	 * 
	 * @param ID
	 * @return info  
	 */
	public ArrayList<String> getSubscriberNum(String ID) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> info = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement("SELECT * FROM subscriber WHERE ID = ?");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			// rs.next();

			if (rs.next() == false) {
				info.add("NotFound");
				return info;
			} else {
				System.out.println("The exntered ID was Found");
				info.add(rs.getString("subscriber_number"));
				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
		}
		return info;
	}

	/**
	 * getWaitingForApprovalUsers method check and return the info of the users that
	 * there role is "waitForApproval" and their storeName is the enter storeName
	 * 
	 * @param storeName
	 * @return the info of the users
	 */
	public ArrayList<String> getWaitingForApprovalUsers(String storeName) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> info = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement("SELECT* FROM users WHERE role = ? AND storeName = ? ");
			pstmt.setString(1, "WaitForApproval");
			pstmt.setString(2, storeName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				info.add(rs.getString("firstName"));
				info.add(rs.getString("lastName"));
				info.add(rs.getString("id"));
				info.add(rs.getString("phoneNumber"));
				info.add(rs.getString("email"));
				info.add(rs.getString("creditCard"));
				info.add(rs.getString("role"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
		}
		return info;
	}

	/**
	 * Retrieves the stock report information for a specific branch, month, and year from the data base.
	 * the method create arrayList stockReport 
	 *if the report doesn't exist the method add to the arralList stockReport "noReport"
	 *if the report exist in the DB the method insert to the arrayList the information of the report
	 * @param msg a list containing the branch name, month, and year for which to retrieve the stock report information
	 * @return a list containing the stock report information for the specified branch, month, and year
	 */
	public ArrayList<String> getStockReportInfo(ArrayList<String> msg) {/* HERE I ADDED THIS */
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> stockReport = new ArrayList<>();
		for(int i=0;i<msg.size();i++) {
			if(msg.get(i) == null) {
				stockReport.add("NoReport");
				return stockReport;
			}
			if(msg.get(i).equals("")) {
				stockReport.add("NoReport");
				return stockReport;
			}
		}
		try {
			pstmt = conn.prepareStatement("SELECT * FROM stockreport WHERE Branch = ? AND Month = ? AND Year = ?");
			pstmt.setString(1, msg.get(1));
			pstmt.setString(2, msg.get(2));
			pstmt.setString(3, msg.get(3));
			rs = pstmt.executeQuery();

			if (!rs.isBeforeFirst()) {
				stockReport.add("NoReport");
				return stockReport;
			} else {
				rs.next();
				stockReport.add(rs.getString("Branch"));
				stockReport.add(rs.getString("Total_inventory"));
				stockReport.add(rs.getString("Total_times_that_lowed_to_threshold"));
				stockReport.add(rs.getString("Total_times_that_lowed_to_zero"));
				stockReport.add(rs.getString("most_available_item"));
				stockReport.add(rs.getString("times_that_loaded"));
				stockReport.add(rs.getString("Month"));
				stockReport.add(rs.getString("Year"));

			}
			rs.close();
		} catch (SQLException e) {
		}
		return stockReport;
	}

	/**
	 * Creates a new stock report in the database for a specific branch, month, and year.
	 *The method call functions that calculate the information of the stock report 
	 *the method call the function updateStockReport in order to insert the report info to tha data base
	 * @param msg a list containing the branch name, month, and year for which to create the stock report
	 */
	public boolean createStockReportInDB(ArrayList<String> msg) {
		int totalTimesLowedToThreshold = 0;
		int numberOfRows = getNumberOfRowsInTable("stockreport");
		String numberOfRowsString = Integer.toString(numberOfRows + 1);
		int totalInventory = 0;
		int totalTimesLowedToZero = 0;
		String mostAvailableItem;
		int timesThatLoaded = 0;
		if (checkIfReportExists("stockreport", msg.get(0), msg.get(1), msg.get(2)))
			return false;
		totalInventory = getSumOfSnacksInStock(msg.get(0));
		totalTimesLowedToThreshold = getTimesLowedToThreshold(msg.get(0));
		mostAvailableItem = getMostAvailableSnack(msg.get(0));
		timesThatLoaded = getTimesLoaded(msg.get(0));
		totalTimesLowedToZero = getTimesLowedToZero(msg.get(0));
		return insertStockReport(numberOfRowsString, msg.get(0), msg.get(1), msg.get(2), totalInventory,
				totalTimesLowedToThreshold, totalTimesLowedToZero, mostAvailableItem, timesThatLoaded);
	}

	/**
	* Inserts the stock report information into the 'stockreport' table in the database.
	*
	* @param numberOfRows the number of rows in the stockreport table
	* @param branch the branch name of the stock report
	* @param year the year of the stock report
	* @param month the month of the stock report
	* @param totalInventory the total inventory for the stock report
	* @param totalTimesLowedToThreshold the total times that the inventory of the branch has lowered below the threshold
	* @param totalTimesLowedToZero the total times that the inventory of the branch has lowered to zero
	* @param mostAvailableItem the most available item in the branch
	* @param timesThatLoaded the times that the branch was loaded
	*/
	private boolean insertStockReport(String numberOfRows, String branch, String year, String month, Integer totalInventory,
			Integer totalTimesLowedToThreshold, Integer totalTimesLowedToZero, String mostAvailableItem, Integer timesThatLoaded) {
		if(numberOfRows == null || branch == null || year == null || month == null)
			return false;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"insert into stockreport (reportID,Branch,Year,Month,Total_inventory,Total_times_that_lowed_to_threshold,Total_times_that_lowed_to_zero,most_available_item,times_that_loaded)values(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, numberOfRows);
			pstmt.setString(2, branch);
			pstmt.setString(3, year);
			pstmt.setString(4, month);
			pstmt.setString(5, Integer.toString(totalInventory));
			pstmt.setString(6, Integer.toString(totalTimesLowedToThreshold));
			pstmt.setString(7, Integer.toString(totalTimesLowedToZero));
			pstmt.setString(8, mostAvailableItem);
			pstmt.setString(9, Integer.toString(timesThatLoaded));
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			
		}
		return false;
	}

	/**
	 * Retrieves the name of the most available snack in a specific branch.
	 * the method call the function getIndexOfMaximumAmount to get the maximum number
	 * @param info list containing the information needed to retrieve the most available snack 
	 * @param tableName the name of the table in the database where the snack information is stored
	 * @return the name of the most available snack in the specified branch
	 */
	private String getMostAvailableSnack(String tableName) {
		String maxItemName = null;
		if(tableName == null)
			return null;
		Statement stmt;
		try {
			ArrayList<String> itemsName = new ArrayList<>();
			ArrayList<Integer> itemsAmount = new ArrayList<>();
			stmt = conn.createStatement();
			String query = "SELECT name,amount_in_stock FROM " + tableName + "items";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				itemsName.add(rs.getString(1));
				itemsAmount.add(Integer.parseInt(rs.getString(2)));
			}
			int indexMax = getIndexOfMaximumAmount(itemsAmount);
			maxItemName = itemsName.get(indexMax);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
		}
		return maxItemName;
	}

	/**
	 * Retrieves the index of the maximum amount in a list of integers.
	 * @param itemsAmount arrayList with the istems from the data base
	 * @return the maximum index
	 */
	private int getIndexOfMaximumAmount(ArrayList<Integer> itemsAmount) {
		if(itemsAmount == null)
			return -1;
		int index = 0;
		int max = 0;
		for (int i = 0; i < itemsAmount.size(); i++) {
			if (itemsAmount.get(i) > max) {
				max = itemsAmount.get(i);
				index = i;
			}
		}
		return index;
	}

	/**
	 * get the sum of all the amounts in the stock
	 * @param info data about the item
	 * @param tableName table name
	 * @return number of inventory amount
	 */
	private int getSumOfSnacksInStock(String tableName) {
		return getSum(tableName, "amount_in_stock");
	}

	/**
	 * get number of times the item dropped to threshold
	 * @param info data about the item
	 * @param tableName table name
	 * @return number of times the item dropped to threshold
	 */
	private int getTimesLowedToThreshold(String tableName) {
		return getSum(tableName, "times_that_lowed_to_threshold");
	}

	/**
	 * get number of times the item dropped to zero
	 * @param info data about the item
	 * @param tableName table name
	 * @return number of times the item dropped to zero
	 */
	private int getTimesLowedToZero(String tableName) {
		return getSum(tableName, "times_that_lowed_to_zero");
	}

	/**
	 *  get number of times the item was loaded
	 * @param info data about the item
	 * @param tableName table name
	 * @return number of times the item was loaded
	 */
	private int getTimesLoaded(String tableName) {
		return getSum(tableName, "times_that_loaded");
	}

	/**
	 * Retrieves the sum of a specific column in a table
	 *
	 * @param info list containing the information needed to retrieve the sum
	 * @param tableName the name of the table in the database where the information is stored
	 * @param column the name of the column for which to retrieve the sum
	 * @return the sum of the specified column in the specified table
	 */
	private int getSum(String tableName, String column) {
		Statement stmt;
		if(tableName == null || column == null)
			return -1;
		int sum = 0;
		try {
			stmt = conn.createStatement();
			String query = "SELECT " + column + " FROM " + tableName + "items";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sum += Integer.parseInt(rs.getString(1));
			}
			rs.close();
			stmt.close();
			return sum;
		} catch (SQLException e) {
		}
		return -1;
	}

	/**
	 * Retrieves the customer report information for a specific branch, month, and year from the data base
	 * if the report doesn't exist in the DB the method return arrayList with "no report" 
	 * if the report exist the method take the order information insert it into arrayList and return it
	 * @param msg a list containing the branch name, month, and year for which to retrieve the customer report information
	 * @return a list containing the customer report information for the specified branch, month, and year
	 */
	public ArrayList<String> getCostumerReport(ArrayList<String> msg) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> stockReport = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM costumersreport WHERE Branch = ? AND Month = ? AND Year = ?");
			pstmt.setString(1, msg.get(1));
			pstmt.setString(2, msg.get(2));
			pstmt.setString(3, msg.get(3));
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				stockReport.add("No report");
			} else {
				rs.next();
				stockReport.add(rs.getString("reportID"));
				stockReport.add(rs.getString("Branch"));
				stockReport.add(rs.getString("0To3"));
				stockReport.add(rs.getString("3To5"));
				stockReport.add(rs.getString("5To10"));
				stockReport.add(rs.getString("10Plus"));
				stockReport.add(rs.getString("Month"));
				stockReport.add(rs.getString("Year"));
			}
		} catch (SQLException e) {
		}
		return stockReport;
	}

	/**
	 * Creates a customer report in the database for a specific branch, month, and year.
	 * If a report for the specified branch, month, and year already exists, the method will return without creating a new report.
	 *
	 * @param msg a list containing the branch name, month, and year for which to create the customer report
	 */
	public void createCostumerReportInDB(ArrayList<String> msg) {
		if (checkIfReportExists("costumersreport", "'" + msg.get(0) + "'", "'" + msg.get(1) + "'",
				"'" + msg.get(2) + "'"))
			return;
		HashSet<String> zeroToThreeOrders = new HashSet<>();
		HashSet<String> threeToFiveOrders = new HashSet<>();
		HashSet<String> fiveToTenOrders = new HashSet<>();
		HashSet<String> tenPlusOrders = new HashSet<>();
		HashMap<String, Integer> numberOfOrdersForEachCostumer = new HashMap<>();

		int numberOfRows = getNumberOfRowsInTable("costumersreport");
		String numberOfRowsString = Integer.toString(numberOfRows + 1);
		PreparedStatement pstmt;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM orders WHERE Branch = ? AND Year = ? AND Month = ?");
			pstmt.setString(1, msg.get(0));
			pstmt.setString(2, msg.get(1));
			pstmt.setString(3, msg.get(2));
			rs = pstmt.executeQuery();

			if (rs.isBeforeFirst()) {
				String customer;
				int numberOfOrders;
				while (rs.next()) {
					customer = rs.getString("CustomerName");
					if (numberOfOrdersForEachCostumer.containsKey(customer)) {
						numberOfOrders = numberOfOrdersForEachCostumer.get(customer);
						numberOfOrdersForEachCostumer.replace(customer, numberOfOrders + 1);
					} else
						numberOfOrdersForEachCostumer.put(customer, 1);
				}

				for (Map.Entry<String, Integer> entry : numberOfOrdersForEachCostumer.entrySet()) {
					customer = entry.getKey();
					numberOfOrders = entry.getValue();
					if (0 <= numberOfOrders && numberOfOrders < 3)
						zeroToThreeOrders.add(customer);
					else if (3 <= numberOfOrders && numberOfOrders < 5)
						threeToFiveOrders.add(customer);
					else if (5 <= numberOfOrders && numberOfOrders < 10)
						fiveToTenOrders.add(customer);
					else
						tenPlusOrders.add(customer);
				}
			}

			updateCostumerReport(numberOfRowsString, msg.get(0), msg.get(1), msg.get(2), zeroToThreeOrders.size(),
					threeToFiveOrders.size(), fiveToTenOrders.size(), tenPlusOrders.size());

		} catch (SQLException e) {
		}
	}

	/**
	 * This method retrieves a stock report from the database based on the provided branch, month and year
	 * @param msg an ArrayList containing the branch, month and year in the order of [branch, month, year]
	 * @return an ArrayList containing the stock report information in the order of [Branch, Total_inventory, Total_times_that_lowed_to_threshold, Total_times_that_lowed_to_zero, most_available_item, times_that_loaded, Month, Year]
	 *         if no report exists with the provided branch, month and year, the ArrayList will contain only one element "NoReport"
	 */
	private void updateCostumerReport(String numberOfRows, String branch, String year, String month,
			int zeroToThreeOrders, int threeToFiveOrders, int fiveToTenOrders, int tenPlusOrders) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"insert into costumersreport (reportID,Branch,Year,Month,0To3,3To5,5To10,10Plus)values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, numberOfRows);
			pstmt.setString(2, branch);
			pstmt.setString(3, year);
			pstmt.setString(4, month);
			pstmt.setString(5, Integer.toString(zeroToThreeOrders));
			pstmt.setString(6, Integer.toString(threeToFiveOrders));
			pstmt.setString(7, Integer.toString(fiveToTenOrders));
			pstmt.setString(8, Integer.toString(tenPlusOrders));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * check if the report with the specific tableName,branch,year,month exist in the data base 
	 * if the report exist the method return true else false
	 * @param tableName the table name in DB
	 * @param branch the branch in the table 
	 * @param year the year in the table
	 * @param month the month in the table
	 * @return
	 */
	private boolean checkIfReportExists(String tableName, String branch, String year, String month) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT reportID FROM " + tableName + " WHERE Branch = '" + branch + "' AND Year = '" + year
					+ "' AND Month = '" + month + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.isBeforeFirst())
				return true;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * method that get the information of the order report from the DB on the specific branch,month,year
	 * if there's no report the method add "NoReport" to the arrayList and return it
	 * @param msg with the branch,year,month
	 * @return arrayList info with the information of the report order
	 */
	public ArrayList<String> getOrdersReportInfo(ArrayList<String> msg) {
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> info = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement("SELECT * FROM orders_reports WHERE Branch = ? AND Month = ? AND Year = ?");
			pstmt.setString(1, msg.get(1));
			pstmt.setString(2, msg.get(2));
			pstmt.setString(3, msg.get(3));
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				info.add("NoReport");
				return info;

			} else {
				rs.next();
				info.add(rs.getString("Orders_amount"));
				info.add(rs.getString("Income"));
				info.add(rs.getString("pickUp_method"));
				info.add(rs.getString("Drone_method"));
				info.add(rs.getString("Immediatly"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
		}
		return info;
	}

	/**
	* This function creates a report of orders for a specific branch, month and year and stores it in the database.
	* @param msg An ArrayList containing the branch name, year and month of the report
	*/
	public void createOrderReportInDB(ArrayList<String> msg) {

		if (checkIfReportExists("orders_reports", "'" + msg.get(0) + "'", "'" + msg.get(1) + "'",
				"'" + msg.get(2) + "'"))
			return;

		int numberOfRows = getNumberOfRowsInTable("orders_reports");
		int ordersAmount = calulateOrdersAmount(msg);
		int income = calulateIncome(msg);
		String numberOfRowsString = Integer.toString(numberOfRows + 1);
		ArrayList<String> amountOrdersForEachSupplyMethod = new ArrayList<>();
		amountOrdersForEachSupplyMethod = checkSupplyType(msg);

		updateOrderReport(numberOfRowsString, msg.get(0), msg.get(1), msg.get(2), ordersAmount, income,
				amountOrdersForEachSupplyMethod);

	}

	/**
	 * This method updates the Orders report table in the database with the given parameters.
	 * It takes the report number, branch, year, month, total amount of orders, total income, and the amount of orders for each supply method.
	 * The method then uses prepared statement to insert the values into the table.
	 *
	 * @param numberOfRows                 the report number
	 * @param branch                       the branch name
	 * @param year                         the year of the report
	 * @param month                        the month of the report
	 * @param ordersAmount                 the total amount of orders
	 * @param income                       the total income
	 * @param amountOrdersForEachSupplyMethod the amount of orders for each supply method
	 */
	private void updateOrderReport(String numberOfRows, String branch, String year, String month, int ordersAmount,
			int income, ArrayList<String> amountOrdersForEachSupplyMethod) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"insert into orders_reports (reportID,Branch,Year,Month,Orders_amount,Income,pickUp_method,Drone_method,Immediatly)values(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, numberOfRows);
			pstmt.setString(2, branch);
			pstmt.setString(3, year);
			pstmt.setString(4, month);
			pstmt.setString(5, Integer.toString(ordersAmount));
			pstmt.setString(6, Integer.toString(income));
			pstmt.setString(7, amountOrdersForEachSupplyMethod.get(0));
			pstmt.setString(8, amountOrdersForEachSupplyMethod.get(1));
			pstmt.setString(9, amountOrdersForEachSupplyMethod.get(2));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * This method is used to calculate the amount of orders for a specific month, year and branch.
	 * @param msg - a list that contains the branch name, month, and year.
	 * @return an integer representing the amount of orders for the given month, year, and branch.
	 */
	public int calulateOrdersAmount(ArrayList<String> msg) {// Qamar
		PreparedStatement pstmt;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE Branch = ? AND Year = ? AND Month = ?");
			pstmt.setString(1, msg.get(0));
			pstmt.setString(2, msg.get(1));
			pstmt.setString(3, msg.get(2));
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
		}
		return 0;
	}

	 /**
     * This method calculates the income of a specific branch in a specific month and year
     * by fetching all the TotalPrice values from the orders table and adding them together
     *
     * @param msg the ArrayList containing the branch, year, and month for which the income is calculated
     * @return the calculated income
     */
	public int calulateIncome(ArrayList<String> msg) {// Qamar
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<String> income = new ArrayList<String>();
		int finalincome = 0;
		try {
			pstmt = conn.prepareStatement("SELECT TotalPrice FROM orders WHERE Branch = ? AND Year = ? AND Month = ?");
			pstmt.setString(1, msg.get(0));
			pstmt.setString(2, msg.get(1));
			pstmt.setString(3, msg.get(2));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				income.add(rs.getString("TotalPrice"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
		}
		for (int i = 0; i < income.size(); i++)
			finalincome += Integer.parseInt(income.get(i));
		return finalincome;
	}

	/**
	 * This method retrieves the number of orders for each supply method, immediately pick up later, drone delivery, and immediate.
	 * @param msg - an ArrayList containing branch, year, and month for which the report is being generated
	 * @return an ArrayList containing the number of orders for each supply method in the same order as the supply method names.
	 */
	public ArrayList<String> checkSupplyType(ArrayList<String> msg) {// Qamar
		PreparedStatement pstmt;
		ResultSet rs = null;
		int pickUpCounter = 0;
		int droneDeliveryCounter = 0;
		int Immediate = 0;
		ArrayList<String> amountOrdersForEachSupplyMethod = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("SELECT SupplyType FROM orders WHERE Branch = ? AND Year = ? AND Month = ?");
			pstmt.setString(1, msg.get(0));
			pstmt.setString(2, msg.get(1));
			pstmt.setString(3, msg.get(2));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("SupplyType").equals("Pick up later"))
					pickUpCounter += 1;
				else if (rs.getString("SupplyType").equals("Drone Delivery"))
					droneDeliveryCounter += 1;
				else if (rs.getString("SupplyType").equals("Immediate"))
					Immediate += 1;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
		}
		amountOrdersForEachSupplyMethod.add(Integer.toString(pickUpCounter));
		amountOrdersForEachSupplyMethod.add(Integer.toString(droneDeliveryCounter));
		amountOrdersForEachSupplyMethod.add(Integer.toString(Immediate));
		return amountOrdersForEachSupplyMethod;
	}

	/**
	* Method for zeroing the data needed to stock report.
	* @param tableName the name of the table to update
	*/
	public boolean zeroingTheDataNeededToStockReport(String tableName) {
		Statement stmt;
		String query;
		if(tableName == null)
			return false;
		try {
			query = "UPDATE " + tableName
					+ "items Set times_that_lowed_to_threshold = '0' AND times_that_lowed_to_zero = '0' AND times_that_loaded = '0'";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
		}
		return false;
	}

//////////////////////////////daniel/////////////////

	/**
	 * Updates the inventory of a store in the database.
	 * 
	 * @param server    the server
	 * @param storeName The name of the store whose inventory is being updated
	 * @param msg       The message containing the updated inventory information
	 */
	public static void updateInventory(AbstractServer server, String storeName, Object msg) {
		ArrayList<String> msgFromServer = (ArrayList<String>) msg;
		for (int i = 2; i < msgFromServer.size(); i += 2) {
			updateInventoryInDB(storeName, msgFromServer.get(i), msgFromServer.get(i + 1));
		}
	}

	/**
	 * Updates the inventory of a specific item in a store in the database.
	 *
	 * @param storeName The name of the store where the item is located
	 * @param ID        The ID of the item whose inventory is being updated
	 * @param amount    The new amount of the item in stock
	 */
	public static void updateInventoryInDB(String storeName, String ID, String amount) {
		PreparedStatement stmt;
		try {
			String sql = "UPDATE " + storeName + " SET amount_in_stock= " + amount + " WHERE id=" + ID;
			System.out.println("the sql is: " + sql);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();

			String counter = "UPDATE " + storeName + " SET times_that_loaded = times_that_loaded + 1 " + " WHERE id="
					+ ID;
			System.out.println("the sql is: " + sql);
			stmt = conn.prepareStatement(counter);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}

	}

	/**
	 * Retrieves data of all items in a store from the database.
	 *
	 * @param server The server
	 * @param store  The name of the store whose data is being retrieved
	 * @return A list containing the ID and amount in stock of all items in the
	 *         store
	 */
	public static ArrayList<String> getDataItem(AbstractServer server, String store) {
		Statement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT * FROM " + store;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
// rs.next();
			while (rs.next()) {
				list.add(rs.getString("id"));
				list.add(rs.getString("amount_in_stock"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e1) {
		}

		return list;
	}

	/**
	 * Retrieves all approved drone delivery orders that have not been received for
	 * a specific store from the database.
	 *
	 * @param server    The server
	 * @param storeName The name of the store whose orders are being retrieved
	 * @return A list containing the order number, estimated date, branch, status,
	 *         and received status of all orders
	 */
	public static ArrayList<String> getOrders(AbstractServer server, String storeName) {
		PreparedStatement stmt = null;
		ArrayList<String> ordersData = new ArrayList<>();
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
					"SELECT * FROM orders WHERE SupplyType=? AND OrderStatus=? AND OrderReceived=? AND Branch=?");
			stmt.setString(1, "Drone Delivery");
			stmt.setString(2, "Approved");
			stmt.setString(3, "Not Received");
			stmt.setString(4, storeName);

			rs = stmt.executeQuery();
			while (rs.next()) {
				ordersData.add(rs.getString("orderNumber"));
				ordersData.add(rs.getString("EstimatedOrderDate"));
				ordersData.add(rs.getString("Branch"));
				ordersData.add(rs.getString("OrderStatus"));
				ordersData.add(rs.getString("OrderReceived"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e1) {
		}
		return ordersData;
	}

	/**
	 * Retrieves the order numbers and confirmation status of all drone delivery
	 * orders that have been received for a specific customer from the database.
	 *
	 * @param server     The server where the customer is located
	 * @param customerID The ID of the customer whose orders are being retrieved
	 * @return A list containing the order numbers and confirmation status of all
	 *         orders
	 */
	public ArrayList<String> getOrdersNumbers(AbstractServer server, String customerID) {
		PreparedStatement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
					"SELECT orderNumber,CustomerConfirmation FROM orders WHERE CustomerId=? AND CustomerConfirmation=? AND OrderReceived = ? AND SupplyType = ?");
			stmt.setString(1, customerID);
			stmt.setString(2, "PENDING");
			stmt.setString(3, "Received");
			stmt.setString(4, "Drone Delivery");
			rs = stmt.executeQuery();
// rs.next();
			while (rs.next()) {
				list.add(rs.getString("orderNumber"));
				list.add(rs.getString("CustomerConfirmation"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e1) {
		}
		return list;
	}

	/**
	 * Updates the confirmation status of a specific order for a specific customer
	 * in the database.
	 *
	 * @param server               The server
	 * @param CustomerConfirmation The new confirmation status of the order
	 * @param CustomerID           The ID of the customer whose order is being
	 *                             updated
	 * @param OrderID              The ID of the order that is being updated
	 */
	public void updateOrderConfirmation(AbstractServer server, String CustomerConfirmation, String CustomerID,
			String OrderID) {
		PreparedStatement stmt;

		try {
			String sql = "UPDATE orders SET CustomerConfirmation='" + CustomerConfirmation + "' WHERE CustomerId="
					+ CustomerID + " AND orderNumber=" + OrderID;
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * Updates the received status of a specific order in the database.
	 *
	 * @param server   The server
	 * @param received The new received status of the order
	 * @param OrderID  The ID of the order that is being updated
	 */
	public static void updateOrderReceived(AbstractServer server, String received, String OrderID) {
		PreparedStatement stmt;
		try {
			String sql = "UPDATE orders SET OrderReceived= '" + received + "' WHERE orderNumber=" + OrderID;
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * Changes the manager approval status of a specific store in the database to
	 * 'approved'.
	 *
	 * @param server        The server
	 * @param storeLocation The location of the store whose manager approval status
	 *                      is being updated
	 */
	public static void changeStoreToApproved(AbstractServer server, String storeLocation) {
		PreparedStatement stmt;

		try {
			String queryForUpdatingmangerapproval = "UPDATE threshold SET mangerapproval= 'approved' WHERE Location= '"
					+ storeLocation + "'";
			stmt = conn.prepareStatement(queryForUpdatingmangerapproval);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the threshold number for a specific store from the database.
	 *
	 * @param server    The server
	 * @param storeName The name of the store whose threshold number is being
	 *                  retrieved
	 * @return A list containing the threshold number of the store
	 */
	public static ArrayList<String> thresholdUpdate(AbstractServer server, String storeName) {
		PreparedStatement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {

			stmt = conn.prepareStatement("SELECT thresholdnum FROM threshold WHERE Location=?");
			stmt.setString(1, storeName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("thresholdnum"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return list;
	}

	/**
	 * This method updates the 'needsrefilling' and 'managerapproval' fields in the
	 * 'threshold' table for a specific store location.
	 *
	 * @param server        The server
	 * @param storeLocation The location of the store whose
	 */
	public static void updateNeedsReffilling(AbstractServer server, String storeLocation) {
		PreparedStatement stmt;

		try {
			String queryForUpdatingNeedsRefilling = "UPDATE threshold SET needsrefilling= 'stock refilled' WHERE Location= '"
					+ storeLocation + "'";
			stmt = conn.prepareStatement(queryForUpdatingNeedsRefilling);
			stmt.executeUpdate();
			String queryForUpdatingmangerapproval = "UPDATE threshold SET mangerapproval= 'not approved' WHERE Location= '"
					+ storeLocation + "'";
			stmt = conn.prepareStatement(queryForUpdatingmangerapproval);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * This method returns a list of store locations that needs refilling and are
	 * not approved by the manager.
	 * 
	 * @param server The server
	 * @param store  The specific store location that is being queried
	 * @return A list of store locations that needs refilling and are not approved
	 *         by the manager.
	 */
	public static ArrayList<String> getWhichStoresNeedsRefilling(AbstractServer server, String store) {
		Statement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			String sql = "SELECT Location FROM threshold WHERE Location= '" + store
					+ "' AND needsrefilling='stock needs refilling' AND mangerapproval='not approved'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("Location"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Retrieves the list of store locations that need refilling work.
	 * 
	 * @param server the server being used to connect to the database
	 * @return a list of store locations that need refilling work
	 */
	public static ArrayList<String> getWhichStoresNeedsRefillingWorker(AbstractServer server) {
		Statement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			String sql = "SELECT Location FROM threshold WHERE mangerapproval='approved' AND needsrefilling='stock needs refilling'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("Location"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
