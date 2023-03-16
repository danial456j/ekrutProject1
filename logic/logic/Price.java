package logic;

public class Price {
	private String oldPrice;
	private String newPrice;
	
	public Price(String oldPrice, String newPrice) {
		super();
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
	}
	
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	@Override
	public String toString() {
		return "OldPrice: " + oldPrice + " \n"
				+ "NewPrice: " + newPrice;
	}

}
