package logic;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Item {
	protected String id;
	protected String amount;
	protected String originalPrice;
	protected ImageView image;
	protected String imageName;
	protected String name;
	protected String preferedPrice;
	protected Price PriceBeforeAndAfter;
	
	public Item(String id, String imageName, String originalPrice, String priceAfterDiscount, String amount,String name) {
		super();
		this.id = id;
		this.amount = amount;
		this.imageName = imageName;
		this.name = name;
		this.PriceBeforeAndAfter = new Price(originalPrice, priceAfterDiscount);
		if(originalPrice==null || priceAfterDiscount==null) {
			preferedPrice=null;
		}
		else if(priceAfterDiscount.equals(""))
			this.preferedPrice = originalPrice;
		else
			this.preferedPrice = priceAfterDiscount;
		if(imageName!=null) {
			image = new ImageView(imageName);
			image.setFitHeight(70);
			image.setFitWidth(70);
		}
	}
//	public Item(String id, String imageName, String amount) {
//		super();
//		this.id = id;
//		this.amount = amount;
//		this.imageName = imageName;
//	}
	//raeed 2
	public Item (String id,String name,String originalPrice) {
		this.id = id;
		this.name=name;
		this.originalPrice = originalPrice;
	}
	

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getId() {
		return id;
	}
	public void setId(String iD) {
		id = iD;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public ImageView getImage() {
		return image;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String price) {
		this.originalPrice = price;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getPriceBeforeAndAfter() {
		return PriceBeforeAndAfter;
	}

	public void setPriceBeforeAndAfter(Price priceBeforeAndAfter) {
		PriceBeforeAndAfter = priceBeforeAndAfter;
	}

	public String getPreferedPrice() {
		return preferedPrice;
	}

	public void setPreferedPrice(String preferedPrice) {
		this.preferedPrice = preferedPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		return this.getId().equals(other.getId());
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %s %s", id, imageName, originalPrice, amount,name);
	}

}
