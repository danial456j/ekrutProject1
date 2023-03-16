package logic;

public class Price {
	private String originalPrice;
	private String priceAfterDiscount;
	
	public Price(String originalPrice, String priceAfterDiscount) {
		super();
		this.originalPrice = originalPrice;
		this.priceAfterDiscount = priceAfterDiscount;
	}
	
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getPriceAfterDiscount() {
		return priceAfterDiscount;
	}
	public void setPriceAfterDiscount(String newPrice) {
		this.priceAfterDiscount = newPrice;
	}

	@Override
	public String toString() {
		if(!priceAfterDiscount.equals(""))
			return "Price: " + originalPrice + "NIS \n" + "After discount: " + priceAfterDiscount + "NIS";
		else
			return "Price: " + originalPrice + "NIS";
		
	}

}
