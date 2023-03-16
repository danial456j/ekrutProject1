package logic;

import java.io.IOException;

public class Costumer {

	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String creditCardNumber;
	private String subscriberNumber;
	private String Role;


	/**
	 * @param id
	 * @param name
	 * @param name2
	 * @param fc
	 */
	public Costumer(String id, String firstName, String lastName, String phoneNumber, String emailAddress,
			String creditCardNumber, String Role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.creditCardNumber = creditCardNumber;
		this.subscriberNumber = subscriberNumber;
		this.Role = Role;

	}

	/**
	 * @return the id
	 */
	public String getId()  {
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

	public String getsubscriberNumber() {
		return subscriberNumber;
	}

	public void setsubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String Role) {
		this.Role = Role;
	}

	public String toString() {
		return String.format("%s %s %s %s %s %s %s %s \n", id, firstName, lastName, phoneNumber, emailAddress,
				creditCardNumber, Role, subscriberNumber);
	}

}
