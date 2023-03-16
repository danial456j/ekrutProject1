package logic;


public class User {
	private String id;
	private String firstName;
	private String lastName,role,storeName;
	private String userName, email, phoneNumber,permissions;


	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/**
	 * @param id
	 * @param name
	 * @param name2
	 * @param fc
	 */
	public User(String firstName, String lastName,String id, String role,String storeName, String userName, String email, String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role=role;
		this.storeName=storeName;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
		// System.out.println("ID set to "+id);
	}

	/**
	 * @return the lName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param name the lName to set
	 */
	public void setFirstName(String name) {
		firstName = name;
		// System.out.println("Last name set to "+name);
	}

	/**
	 * @return the pName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param name the pName to set
	 */
	public void setLastName(String name) {
		lastName = name;
		// System.out.println("Private name set to "+name);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
