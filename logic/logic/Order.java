package logic;

	import java.io.Serializable;
	import java.sql.Timestamp;

	/**
	 *  class description:
	 *  order class contains all the order information
	 *  
	 * 
	 */
	public class Order implements Serializable {


		private static final long serialVersionUID = 1L;

		/**
		 * the order number
		 */
		public String OrderNumber;
		/**
		 * customer id
		 */
		public String CustomerID;
		/**
		 * order branch
		 */
		public String branch;
		/**
		 *  order status(pending, approval...)
		 */
		public String orderStatus;
		/**
		 * order date
		 */
		public String orderDate;
		/**
		 * the estimated time
		 */
		public String estimatedDate;
		/**
		 *  the actual date of the order
		 */
		public String actualDate;
		/**
		 * order supply type
		 */
		public String supplyType;
		/**
		 *  price of the order
		 */
		public String totalPrice;

		/**
		 * the customer name that made the order
		 */
		public String customerName;
		/**
		
		/**
		 *  delivery address of the customer
		 */
		public String deliveryAddress;
		/**
		 * the items that the order contains
		 */
		public String components;
		/**
		 * the phone number of the customer that made the order
		 */
		public String phoneNumber;
		private String orderReceived;
	

	
		public Order(String orderNumber, String customerID, String branch, String orderstatus, String orderDate,
				String estimatedDate, String actualDate, String supplyType, String totalPrice,
				 String customerName, String deliveryAddress, String Items, String phoneNumber, String orderReceived) {

			super();
			this.OrderNumber = orderNumber;
			this.CustomerID = customerID;
			this.branch = branch;
			this.orderStatus = orderstatus;
			this.orderDate = orderDate;
			this.estimatedDate = estimatedDate;
			this.actualDate = actualDate;
			this.supplyType = supplyType;
			this.totalPrice = totalPrice;
			this.customerName = customerName;
			this.deliveryAddress = deliveryAddress;
			this.components = Items;
			this.phoneNumber = phoneNumber;
			this.orderReceived = orderReceived;
		}

		/*
		public Order(String OrderNumber, String CustomerName, String DeliveryAddress) {

			this.OrderNumber = OrderNumber;
			this.customerName = CustomerName;
			this.deliveryAddress = DeliveryAddress;
		}

		
	
		public Order(String orderNumber2, String customerID2, String branch2, String orderStatus2,
				Timestamp orderDate2, Timestamp estimatedDate2, Timestamp actualDate2, String supplyType,
				double totalPrice2, double deliveryCost2, String customerName2, String deliveryAddress2, String Item,
				String GreetingCard) {

			this.OrderNumber = orderNumber2;
			this.CustomerID = customerID2;
			this.branch = branch2;
			this.orderStatus = orderStatus2;
			this.orderDate = orderDate2;
			this.estimatedDate = estimatedDate2;
			this.actualDate = actualDate2;
			this.supplyType = supplyType;
			this.totalPrice = totalPrice2;
			this.customerName = customerName2;
			this.deliveryAddress = deliveryAddress2;
			this.components = Item;
			

		}   

		
		public Order(Order order) {
			this.OrderNumber = order.getOrderNumber();
			this.CustomerID = order.getCustomerID();
			this.branch = order.getBranch();
			this.orderStatus = order.getOrderstatus();
			this.orderDate = order.getOrderDate();
			this.estimatedDate = order.getEstimatedDate();
			this.actualDate = order.getActualDate();
			this.supplyType = order.getSupplyType();
			this.totalPrice = order.getTotalPrice();
			this.customerName = order.getCustomerName();
			this.deliveryAddress = order.getDeliveryAddress();
			this.components = order.getAllItems();
		}
		*/
		
		
		
		/**
		 * setters and getters
		 */
		

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			phoneNumber = phoneNumber;
		}

		

		public String getAllItems() {
			return components;
		}

		public void setAllItems(String allItems) {
			components = allItems;
		}


		public String getOrderNumber() {
			return OrderNumber;
		}

		public void setOrderNumber(String orderNumber) {
			OrderNumber = orderNumber;
		}

		public String getCustomerID() {
			return CustomerID;
		}

		public void setCustomerID(String customerID) {
			CustomerID = customerID;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public String getOrderstatus() {
			return orderStatus;
		}

		public void setOrderstatus(String orderstatus) {
			this.orderStatus = orderstatus;
		}

		public String getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Timestamp orderDate) {
			orderDate = orderDate;
		}

		public String getEstimatedDate() {
			return estimatedDate;
		}

		public void setEstimatedDate(Timestamp estimatedDate) {
			estimatedDate = estimatedDate;
		}

		public String getActualDate() {
			return actualDate;
		}

		public void setActualDate(Timestamp actualDate) {
			actualDate = actualDate;

		}

		public String getSupplyType() {
			return supplyType;
		}

		public void setSupplyType(String supplyType) {
			supplyType = supplyType;
		}

		public String getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			totalPrice = totalPrice;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			customerName = customerName;
		}

		public String getDeliveryAddress() {
			return deliveryAddress;
		}

		public void setDeliveryAddress(String deliveryAddress) {
			deliveryAddress = deliveryAddress;
		}
		

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getComponents() {
			return components;
		}

		public void setComponents(String components) {
			this.components = components;
		}

		public String getOrderReceived() {
			return orderReceived;
		}

		public void setOrderReceived(String orderReceived) {
			this.orderReceived = orderReceived;
		}
		

		@Override
		public String toString() {
			return "Order [OrderNumber=" + OrderNumber + ", CustomerID=" + CustomerID + ", branch=" + branch
					+ ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", estimatedDate=" + estimatedDate
					+ ", actualDate=" + actualDate + ", supplyType=" + supplyType + ", totalPrice=" + totalPrice
					+ ", customerName=" + customerName + ", deliveryAddress=" + deliveryAddress + ", components="
					+ components + ", phoneNumber=" + phoneNumber + ", orderReceived=" + orderReceived + "]";
		}
		
		

	


}
