package clientTest;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clientGUI.MainPageReportsController;
import clientGUI.logInController;
import logic.User;

class StockReportTest {

	public User u1;
	private IClientUIChatAccept stubClientUIChatAccept;
	private String year;
	private String month;
	private String location;
	private String type = null;
	private MainPageReportsController mainPageReportsController;
	private Method method;
	
	@BeforeEach
	void setUp() throws Exception {
		mainPageReportsController = new MainPageReportsController();
		stubClientUIChatAccept = new StubClientUIChatAccept();
		u1 = new User(null, null, null, null, null, null, null, null);
		year = "2023";
		month = "january";
		location = "haifa";
		type = "stockReport";
		method = MainPageReportsController.class.getDeclaredMethod("isInputValid", String.class, String.class, String.class, String.class);
		method.setAccessible(true);
	}
	
	////////////////// tests for  isInputValid()//////////////
	
	//functionality: checks that with non-null year, month, location and type
	//isInputValid() returns true
	//input: year = 2023, month = january, location = haifa, type = stockReport
	//expected result: true
	@Test
	void isInputValidTest_NotNullInput() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean actualResult = (boolean)method.invoke(mainPageReportsController, year, month, location, type);
		assertTrue(actualResult);
	}
	
	//functionality: checks that with null year
	//isInputValid() returns false
	//input: year = null, month = january, location = haifa, type = stockReport
	//expected result: false
	@Test
	void isInputValidTest_NullYearAndValidMonthLocationType() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		year = null;
		boolean actualResult = (boolean)method.invoke(mainPageReportsController, year, month, location, type);
		assertFalse(actualResult);
	}
	
	//functionality: checks that with null month
	//isInputValid() returns false
	//input: year = 2023, month = null, location = haifa, type = stockReport
	//expected result: false
	@Test
	void isInputValidTest_NullMonthAndValidYearLocationType() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		month = null;
		boolean actualResult = (boolean)method.invoke(mainPageReportsController, year, month, location, type);
		assertFalse(actualResult);
	}
	
	//functionality: checks that with null location
	//isInputValid() returns false
	//input: year = 2023, month = january, location = null, type = stockReport
	//expected result: false
	@Test
	void isInputValidTest_NullLocationAndValidYearMonthType() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		location = null;
		boolean actualResult = (boolean)method.invoke(mainPageReportsController, year, month, location, type);
		assertFalse(actualResult);
	}
	
	//functionality: checks that with null type
	//isInputValid() returns false
	//input: year = 2023, month = january, location = haifa, type = null
	//expected result: false
	@Test
	void isInputValidTest_NullTypeAndValidYearMonthLocation() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		type = null;
		boolean actualResult = (boolean)method.invoke(mainPageReportsController, year, month, location, type);
		assertFalse(actualResult);
	}
	
	////////////////// tests for  addInfoReport()//////////////
	
	//functionality: checks that isInputValid() truly adds the entered data
	//input: year = 2023, month = january, location = haifa, type = stockReport
	//expected result: the data in the returned ArrayList contains the entered data
	@Test
	void addInfoReport_SuccessfullyAdded() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		method = MainPageReportsController.class.getDeclaredMethod("addInfoReport", String.class, String.class, String.class, String.class);
		method.setAccessible(true);
		year = "2023";
		month = "january";
		location = "haifa";
		type = "stockReport";
		ArrayList<String> actualResult = (ArrayList<String>)method.invoke(mainPageReportsController, type, location, month, year);
		assertEquals(actualResult.get(0), type);
		assertEquals(actualResult.get(1), location);
		assertEquals(actualResult.get(2), month);
		assertEquals(actualResult.get(3), year);
	}
	
	////////////////// tests for  checkReportExists()//////////////

	//functionality: checks that with msgRecived = report 
	//checkReportExists() returns true
	//input: msgRecived = Report
	//expected result: true
	@Test
	void checkReportExists_Exists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		method = MainPageReportsController.class.getDeclaredMethod("checkReportExists", ArrayList.class);
		method.setAccessible(true);
		stubClientUIChatAccept.setMsgRecived("Report");
		mainPageReportsController.setClientUIChatAccept(stubClientUIChatAccept);
		boolean actualResult = (boolean) method.invoke(mainPageReportsController, new ArrayList<String>());
		assertTrue(actualResult);
	}
	
	//functionality: checks that with msgRecived = NotReport 
	//checkReportExists() returns false
	//input: msgRecived = NotReport
	//expected result: false
	@Test
	void checkReportExists_DoesNotExist() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		method = MainPageReportsController.class.getDeclaredMethod("checkReportExists", ArrayList.class);
		method.setAccessible(true);
		stubClientUIChatAccept.setMsgRecived("NotReport");
		mainPageReportsController.setClientUIChatAccept(stubClientUIChatAccept);
		boolean actualResult = (boolean) method.invoke(mainPageReportsController, new ArrayList<String>());
		assertFalse(actualResult);
	}
}
