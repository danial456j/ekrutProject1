package logic;

public class Worker extends User {
	private String area;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Worker(String firstName, String lastName, String id, String role,String storeName, String userName, String email, String phoneNumber) {
		super(firstName, lastName, id, role,storeName,userName, email, phoneNumber);
		
	}
}
