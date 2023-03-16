package serverTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jdbc.DBController;

class LoginTests {

	private static DBController myDB;
	private String username;
	private String password;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		myDB = DBController.getInstance();
		myDB.connectToDB();
	}

	/*
	 * @BeforeEach void setUp() throws Exception { myDB =
	 * DBController.getInstance(); myDB.connectToDB(); }
	 */

	////////////////// tests for checkValidUsernameAndPassword()//////////////

	// functionality: checks that with valid username and password
	// checkValidUsernameAndPassword() returns the correct data
	// input: username = costumer, password = 123456
	// expected result: checkAndSetProperLabelForLogin() returns arrayList with the
	// correct data
	@Test
	public void checkValidUsernameAndPasswordTest_validUsernameAndPassword() {
		username = "costumer";
		password = "123456";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("Yossi");
		expectedResult.add("Levi");
		expectedResult.add("2");
		expectedResult.add("Subscriber");
		expectedResult.add("karmiel");
		expectedResult.add("costumer");
		expectedResult.add("ekrutbraude@gmail.com");
		expectedResult.add("0523215682");
		expectedResult.add(null);
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult.get(0), expectedResult.get(0));
		assertEquals(actualResult.get(1), expectedResult.get(1));
		assertEquals(actualResult.get(2), expectedResult.get(2));
		assertEquals(actualResult.get(3), expectedResult.get(3));
		assertEquals(actualResult.get(4), expectedResult.get(4));
		assertEquals(actualResult.get(5), expectedResult.get(5));
		assertEquals(actualResult.get(6), expectedResult.get(6));
		assertEquals(actualResult.get(7), expectedResult.get(7));
		assertEquals(actualResult.get(8), expectedResult.get(8));
		assertEquals(actualResult.get(9), expectedResult.get(9));
	}

	// functionality: checks that with valid username and invalid password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = costumer, password = 12345
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_validUsernameInvalidPassword() {
		username = "costumer";
		password = "12345";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult.get(0), expectedResult.get(0));
		assertEquals(actualResult.get(1), expectedResult.get(1));
	}

	// functionality: checks that with invalid username and valid password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = costumer8, password = 123456
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_InvalidUsernameValidPassword() {
		username = "costumer8";
		password = "123456";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult, expectedResult);
	}

	// functionality: checks that with null username and valid password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = null, password = 123456
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_NullUsernameValidPassword() {
		username = null;
		password = "123456";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult, expectedResult);
	}

	// functionality: checks that with valid username and null password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = costumer, password = null
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_ValidUsernameNullPassword() {
		username = "costumer";
		password = null;
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult, expectedResult);
	}

	// functionality: checks that with valid username and empty password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = costumer, password = ""
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_ValidUsernameEmptyPassword() {
		username = "costumer";
		password = "";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult, expectedResult);
	}

	// functionality: checks that with empty username and valid password
	// checkValidUsernameAndPassword() returns [login, NotFound]
	// input: username = "", password = 123456
	// expected result: checkAndSetProperLabelForLogin() returns [login, NotFound]
	@Test
	public void checkValidUsernameAndPasswordTest_EmptyValidUsernamePassword() {
		username = "";
		password = "123456";
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("login");
		expectedResult.add("NotFound");
		ArrayList<String> actualResult = myDB.checkValidUsernameAndPassword(username, password);
		assertEquals(actualResult, expectedResult);
	}

}
