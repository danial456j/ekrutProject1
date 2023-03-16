package logic;

import java.util.Objects;

public class ItemInBill extends Item {
	private Integer quantityInOrder;
	private String quantityInOrderString;
	private Integer totalPrice;
	
	public ItemInBill(String id, String imageName, String originalPrice, String priceAfterDiscount, String amount, String name, Integer quantityInOrder) {
		super(id, imageName, originalPrice, priceAfterDiscount, amount, name);
		this.quantityInOrder = quantityInOrder;
		this.quantityInOrderString = "X" + quantityInOrder;
		if(priceAfterDiscount.equals(""))
			this.totalPrice = Integer.parseInt(originalPrice);
		else
			this.totalPrice = Integer.parseInt(priceAfterDiscount);
	}
	
	public Integer getQuantityInOrder() {
		return quantityInOrder;
	}
	
	public void setQuantityInOrder(Integer quantityInOrder) {
		this.quantityInOrder = quantityInOrder;
		this.quantityInOrderString = "X" + quantityInOrder;
	}

	public String getQuantityInOrderString() {
		return quantityInOrderString;
	}

	public void setQuantityInOrderString(String quantityInOrderString) {
		this.quantityInOrderString = quantityInOrderString;
	}
	
	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %s", id,quantityInOrderString,totalPrice,name);
	}
	
	
	
}
