package clientTest;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clientGUI.logInController;
import logic.User;

class LoginTest {
	
	public User u1;
	private logInController logInController;
	private IClientUIChatAccept stubClientUIChatAccept;
	private Method method;
	
	@BeforeEach
	void setUp() throws Exception {
		logInController = new logInController();
		stubClientUIChatAccept = new StubClientUIChatAccept();
		u1 = new User(null, null, null, null, null, null, null, null);
		method = logInController.class.getDeclaredMethod("checkAndSetProperLabelForLogin", ArrayList.class);
		method.setAccessible(true);
	}
	
	////////////////// tests for  checkAndSetProperLabelForLogin()//////////////
	
	//functionality: checks that with valid username and password 
	//checkAndSetProperLabelForLogin() returns login succeeded
	//input: u1 with Role = Costumer and username = costumer, msgRecieved = login succeeded
	//expected result: checkAndSetProperLabelForLogin() returns login succeeded
	@Test
	void checkAndSetProperLabelForLoginTest_LoginSucceeded() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String expectedResult = "login succeeded";
		u1.setRole("Costumer");
		u1.setUserName("costumer");
		stubClientUIChatAccept.setUser(u1);
		stubClientUIChatAccept.setMsgRecieved("login succeeded");
		logInController.setClientUIChatAccept(stubClientUIChatAccept);
		String actualResult = (String)method.invoke(logInController, new ArrayList<String>());
		assertEquals(expectedResult, actualResult);
	}
	
	//functionality: checks that with invalid username and password 
	//checkAndSetProperLabelForLogin() returns username or password are not correct
	//input: msgRecieved = username or password are not correct try again!
	//expected result: checkAndSetProperLabelForLogin() returns username or password are not correct
	@Test
	void checkAndSetProperLabelForLoginTest_usernameAndPasswordsNotCorrect() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String expectedResult = "username or password are not correct";
		stubClientUIChatAccept.setMsgRecieved("username or password are not correct try again!");
		logInController.setClientUIChatAccept(stubClientUIChatAccept);
		String actualResult = (String)method.invoke(logInController, new ArrayList<String>());
		assertEquals(expectedResult, actualResult);
	}
	
	//functionality: checks that with username and password that already were logged in
	//checkAndSetProperLabelForLogin() returns user Already logged in
	//input: msgRecieved = user Already logged in
	//expected result: checkAndSetProperLabelForLogin() returns user Already logged in
	@Test
	void checkAndSetProperLabelForLoginTest_UserAlreadyLoggedIn() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String expectedResult = "user Already logged in";
		stubClientUIChatAccept.setMsgRecieved("user Already logged in");
		logInController.setClientUIChatAccept(stubClientUIChatAccept);
		String actualResult = (String)method.invoke(logInController, new ArrayList<String>());
		assertEquals(expectedResult, actualResult);
	}
	
	//functionality: checks that with username costumer123 and role Customer 
	//checkAndSetProperLabelForLogin() returns Not a subscriber
	//input: u1 with Role = Costumer and username = costumer123, msgRecieved = ""
	//expected result: checkAndSetProperLabelForLogin() returns Not a subscriber
	@Test
	void checkAndSetProperLabelForLoginTest_NotASubscriberWithEKTMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String expectedResult = "Not a subscriber";
		u1.setRole("Costumer");
		u1.setUserName("costumer123");
		stubClientUIChatAccept.setUser(u1);
		stubClientUIChatAccept.setMsgRecieved("");
		logInController.setClientUIChatAccept(stubClientUIChatAccept);
		String actualResult = (String)method.invoke(logInController, new ArrayList<String>());
		assertEquals(expectedResult, actualResult);
	}
	
	////////////////// tests for  isValid()//////////////
	
	//functionality: checks that with non-empty username and password
	//isValid() returns true
	//input: username = notEmptyUsername, password = notEmptyPassword
	//expected result: true
	@Test
	void isValidTest_notEmptyUsernameAndPassword() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method = logInController.class.getDeclaredMethod("isValid", String.class, String.class);
		method.setAccessible(true);
		String username = "notEmptyUsername";
		String password = "notEmptyPassword";
		boolean actualResult = (boolean)method.invoke(logInController, username, password);
		assertTrue(actualResult);
	}
	
	//functionality: checks that with empty username and non-empty password
	//isValid() returns false
	//input: username = "", password = notEmptyPassword
	//expected result: false
	@Test
	void isValidTest_EmptyUsernameAndNotEmptyPassword() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method = logInController.class.getDeclaredMethod("isValid", String.class, String.class);
		method.setAccessible(true);
		String username = "";
		String password = "notEmptyPassword";
		boolean actualResult = (boolean)method.invoke(logInController, username, password);
		assertFalse(actualResult);
	}
	
	//functionality: checks that with non-empty username and empty password
	//isValid() returns false
	//input: username = notEmptyUsername, password = ""
	//expected result: false
	@Test
	void isValidTest_notEmptyUsernameAndEmptyPassword() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method = logInController.class.getDeclaredMethod("isValid", String.class, String.class);
		method.setAccessible(true);
		String username = "notEmptyUsername";
		String password = "";
		boolean actualResult = (boolean)method.invoke(logInController, username, password);
		assertFalse(actualResult);
	}
}
