package serverTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.EchoServer;

import jdbc.DBController;

class StockReportTests {

	class StubIDateAndTime implements IDateAndTime {

		@Override
		public int getYear() {
			return 2024;
		}

		@Override
		public int getMonth() {
			return 1;
		}

		@Override
		public int getDay() {
			return 31;
		}

		@Override
		public int getHour() {
			return 23;
		}

		@Override
		public int getMinutes() {
			return 59;
		}

		@Override
		public int getSeconds() {
			return 59;
		}
	}

	private static DBController myDB;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		myDB = DBController.getInstance();
		myDB.connectToDB();
	}

//	@BeforeEach
//	void setUp() throws Exception {
//		myDB = DBController.getInstance();
//		myDB.connectToDB();
//	}

	////////////////// tests for getStockReportInfo()//////////////

	// functionality: checks that with existing report info
	// getStockReportInfo() returns arrayList containing the correct data
	// input: [Stock reports, dubai, january, 2022]
	// expected result: checkAndSetProperLabelForLogin() returns
	// [dubai, 63, 26, 10, hot dog, 25, january, 2022]
	@Test
	public void getStockReportInfo_ReportFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("dubai");
		report.add("january");
		report.add("2022");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add(report.get(1));
		expectedResult.add("63");
		expectedResult.add("26");
		expectedResult.add("10");
		expectedResult.add("hot dog");
		expectedResult.add("25");
		expectedResult.add(report.get(2));
		expectedResult.add(report.get(3));
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
		assertEquals(actualResult.get(1), expectedResult.get(1));
		assertEquals(actualResult.get(2), expectedResult.get(2));
		assertEquals(actualResult.get(3), expectedResult.get(3));
		assertEquals(actualResult.get(4), expectedResult.get(4));
		assertEquals(actualResult.get(5), expectedResult.get(5));
		assertEquals(actualResult.get(6), expectedResult.get(6));
		assertEquals(actualResult.get(7), expectedResult.get(7));
	}

	// functionality: checks that with branch not found
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, haifa, august, 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_BranchNotFoundAndMonthYearFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("haifa");
		report.add("august");
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with month not found
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, karmiel, august, 2020]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_BranchYearFoundAndMonthNotFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("karmiel");
		report.add("august");
		report.add("2020");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with year not found
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, abudhabi, may, 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_BranchMonthFoundAndYearNotFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("abudhabi");
		report.add("may");
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with null branch
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, null, may, 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_NullBranchMonthAndYearFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add(null);
		report.add("may");
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with null month
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, karmiel, null, 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_NullMonthAndBranchYearFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("karmiel");
		report.add(null);
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with null year
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, karmiel, september, null]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_NullYearAndBranchMonthFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("karmiel");
		report.add("september");
		report.add(null);
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with empty branch
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, "", may, 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_EmptyBranchMonthAndYearFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("");
		report.add("may");
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with empty month
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, karmiel, "", 2021]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_EmptyMonthAndBranchYearFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("karmiel");
		report.add("");
		report.add("2021");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	// functionality: checks that with empty year
	// getStockReportInfo() returns [NoReport]
	// input: [Stock reports, karmiel, september, ""]
	// expected result: checkAndSetProperLabelForLogin() returns [NoReport]
	@Test
	public void getStockReportInfo_EmptyYearAndBranchMonthFound() {
		ArrayList<String> report = new ArrayList<>();
		report.add("Stock reports");
		report.add("karmiel");
		report.add("september");
		report.add("");
		ArrayList<String> expectedResult = new ArrayList<>();
		expectedResult.add("NoReport");
		ArrayList<String> actualResult = myDB.getStockReportInfo(report);
		assertEquals(actualResult.get(0), expectedResult.get(0));
	}

	////////////////// tests for getNumberOfRowsInTable()//////////////

//	@Test
//	public void getNumberOfRowsInTableTest_FoundTableWithCorrectNumberOfRows() {
//		String tableName = "stockreport";
//		int numberOfRows = myDB.getNumberOfRowsInTable(tableName);
//		assertEquals(numberOfRows, 19);
//	}

	// functionality: checks that with not found table
	// getStockReportInfo() returns -1
	// input: tableName = notTableName
	// expected result: checkAndSetProperLabelForLogin() returns -1
	@Test
	public void getNumberOfRowsInTable_NotFoundTable() {
		String tableName = "notFoundTableName";
		int numberOfRows = myDB.getNumberOfRowsInTable(tableName);
		assertEquals(numberOfRows, -1);
	}

	////////////////// tests for checkIfReportExists()//////////////

	// functionality: checks that with already existing report
	// checkIfReportExists() returns true
	// input: tableName = stockreport, branch = beersheva, year = 2022, month =
	// january
	// expected result: true
	@Test
	public void checkIfReportExistsTest_ReportExists()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("checkIfReportExists", String.class, String.class,
				String.class, String.class);
		method.setAccessible(true);

		String tableName = "stockreport";
		String branch = "beersheva";
		String year = "2022";
		String month = "january";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), tableName, branch, year, month);
		assertTrue(actualResult);
	}

	// functionality: checks that with not found tableName
	// checkIfReportExists() returns false
	// input: tableName = noSuchTable, branch = beersheva, year = 2022, month =
	// january
	// expected result: false
	@Test
	public void checkIfReportExistsTest_noSuchTable()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("checkIfReportExists", String.class, String.class,
				String.class, String.class);
		method.setAccessible(true);

		String tableName = "noSuchTable";
		String branch = "beersheva";
		String year = "2022";
		String month = "january";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), tableName, branch, year, month);
		assertFalse(actualResult);
	}

	// functionality: checks that with not found branch
	// checkIfReportExists() returns false
	// input: tableName = stockreport, branch = noBranch, year = 2022, month =
	// january
	// expected result: false
	@Test
	public void checkIfReportExistsTest_noSuchBranch()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("checkIfReportExists", String.class, String.class,
				String.class, String.class);
		method.setAccessible(true);

		String tableName = "stockreport";
		String branch = "noBranch";
		String year = "2022";
		String month = "january";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), tableName, branch, year, month);
		assertFalse(actualResult);
	}

	// functionality: checks that with not found year
	// checkIfReportExists() returns false
	// input: tableName = stockreport, branch = beersheva, year = noSuchYear, month
	// = january
	// expected result: false
	@Test
	public void checkIfReportExistsTest_noSuchYear()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("checkIfReportExists", String.class, String.class,
				String.class, String.class);
		method.setAccessible(true);

		String tableName = "stockreport";
		String branch = "beersheva";
		String year = "noSuchYear";
		String month = "january";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), tableName, branch, year, month);
		assertFalse(actualResult);
	}

	// functionality: checks that with not found month
	// checkIfReportExists() returns false
	// input: tableName = stockreport, branch = beersheva, year = noSuchYear, month
	// = noSuchMonth
	// expected result: false
	@Test
	public void checkIfReportExistsTest_noSuchMonth()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("checkIfReportExists", String.class, String.class,
				String.class, String.class);
		method.setAccessible(true);

		String tableName = "stockreport";
		String branch = "beersheva";
		String year = "2022";
		String month = "noSuchMonth";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), tableName, branch, year, month);
		assertFalse(actualResult);
	}

	////////////////// tests for getIndexOfMaximumAmount()//////////////

	// functionality: checks that with given arrayList
	// getIndexOfMaximumAmount() returns the index of the maximum number
	// input: amounts = [5, 0, 7, 5]
	// expected result: 2
	@Test
	public void getIndexOfMaximumAmount_Successfully()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getIndexOfMaximumAmount", ArrayList.class);
		method.setAccessible(true);

		ArrayList<Integer> amounts = new ArrayList<>();
		amounts.add(5);
		amounts.add(0);
		amounts.add(7);
		amounts.add(5);
		int actualResult = (int) method.invoke(DBController.getInstance(), amounts);
		assertEquals(actualResult, 2);
	}

	// functionality: checks that with null given arrayList
	// getIndexOfMaximumAmount() returns -1
	// input: amounts = null
	// expected result: -1
	@Test
	public void getIndexOfMaximumAmount_NullArray()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getIndexOfMaximumAmount", ArrayList.class);
		method.setAccessible(true);

		ArrayList<Integer> amounts = null;
		int actualResult = (int) method.invoke(DBController.getInstance(), amounts);
		assertEquals(actualResult, -1);
	}

	////////////////// tests for getMostAvailableSnack()//////////////

	// functionality: checks that with given tableName
	// getMostAvailableSnack() returns the most available item
	// input: tableName = eilat
	// expected result: getMostAvailableSnack() returns jelly belly
	@Test
	public void getMostAvailableSnack_Successfully()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		Method method = DBController.class.getDeclaredMethod("getMostAvailableSnack", String.class);
		method.setAccessible(true);

		String tableName = "eilat";
		String actualResult = (String) method.invoke(DBController.getInstance(), tableName);
		assertEquals(actualResult, "jelly belly");
	}

	// functionality: checks that with given null tableName
	// getMostAvailableSnack() returns null
	// input: tableName = null
	// expected result: getMostAvailableSnack() returns null
	@Test
	public void getMostAvailableSnack_NullTableName()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		Method method = DBController.class.getDeclaredMethod("getMostAvailableSnack", String.class);
		method.setAccessible(true);

		String tableName = null;
		String actualResult = (String) method.invoke(DBController.getInstance(), tableName);
		assertEquals(actualResult, null);
	}

	// functionality: checks that with given unknown tableName
	// getMostAvailableSnack() returns null
	// input: tableName = unknown
	// expected result: getMostAvailableSnack() returns null
	@Test
	public void getMostAvailableSnack_UnknownTableName()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		Method method = DBController.class.getDeclaredMethod("getMostAvailableSnack", String.class);
		method.setAccessible(true);

		String tableName = "unknown";
		String actualResult = (String) method.invoke(DBController.getInstance(), tableName);
		assertEquals(actualResult, null);
	}

	////////////////// tests for getSum()//////////////

	// functionality: checks that with given tableName and column
	// getSum() returns the sum of the values in the specific column
	// input: tableName = eilat, column = times_that_lowed_to_zero
	// expected result: getSum() returns 8
	@Test
	public void getSumTest_SumUpSuccessfully()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getSum", String.class, String.class);
		method.setAccessible(true);

		String tableName = "eilat";
		String column = "times_that_lowed_to_zero";
		int actualResult = (int) method.invoke(DBController.getInstance(), tableName, column);
		assertEquals(actualResult, 8);
	}

	// functionality: checks that with given null tableName and not null column
	// getSum() returns -1
	// input: tableName = null, column = times_that_lowed_to_zero
	// expected result: getSum() returns -1
	@Test
	public void getSumTest_TableNameNull()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getSum", String.class, String.class);
		method.setAccessible(true);

		String tableName = null;
		String column = "times_that_lowed_to_zero";
		int actualResult = (int) method.invoke(DBController.getInstance(), tableName, column);
		assertEquals(actualResult, -1);
	}

	// functionality: checks that with given not null tableName and null column
	// getSum() returns -1
	// input: tableName = eilat, column = null
	// expected result: getSum() returns -1
	@Test
	public void getSumTest_ColumnNull()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getSum", String.class, String.class);
		method.setAccessible(true);

		String tableName = "eilat";
		String column = null;
		int actualResult = (int) method.invoke(DBController.getInstance(), tableName, column);
		assertEquals(actualResult, -1);
	}

	// functionality: checks that with given unknown tableName and known column
	// getSum() returns -1
	// input: tableName = unknown, column = times_that_lowed_to_zero
	// expected result: getSum() returns -1
	@Test
	public void getSumTest_UnknownTableName()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getSum", String.class, String.class);
		method.setAccessible(true);

		String tableName = "unknown";
		String column = "times_that_lowed_to_zero";
		int actualResult = (int) method.invoke(DBController.getInstance(), tableName, column);
		assertEquals(actualResult, -1);
	}

	// functionality: checks that with given known tableName and unknown column
	// getSum() returns -1
	// input: tableName = eilat, column = unknown
	// expected result: getSum() returns -1
	@Test
	public void getSumTest_UnknownColumn()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("getSum", String.class, String.class);
		method.setAccessible(true);

		String tableName = "eilat";
		String column = "unknown";
		int actualResult = (int) method.invoke(DBController.getInstance(), tableName, column);
		assertEquals(actualResult, -1);
	}

	////////////////// tests for insertStockReport()//////////////

	// functionality: checks that with valid given data
	// insertStockReport() inserts new line in stockReport table with the given data
	// and returns true
	// input: reportID = 26, branch = eilat, year = 2022, month = december
	// expected result: insertStockReport() returns true

//	@Test
//	public void insertStockReport_InsertSuccessfully()
//			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
//				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
//		method.setAccessible(true);
//
//		String reportID = "26";
//		String branch = "eilat";
//		String year = "2022";
//		String month = "december";
//		int numberOfRows = myDB.getNumberOfRowsInTable("stockreport"); 
//		assertEquals(numberOfRows, 19);
//		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
//				0, "Item", 0);
//		assertTrue(actualResult);
//
//		numberOfRows = myDB.getNumberOfRowsInTable("stockreport"); 
//		assertEquals(numberOfRows, 20);
//	}

	// functionality: checks that with null reportID
	// insertStockReport() returns false
	// input: reportID = null, branch = eilat, year = 2022, month = december
	// expected result: insertStockReport() returns false
	@Test
	public void insertStockReport_NullReportID()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
		method.setAccessible(true);

		String reportID = null;
		String branch = "eilat";
		String year = "2022";
		String month = "december";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
				0, "Item", 0);
		assertFalse(actualResult);
	}

	// functionality: checks that with null branch
	// insertStockReport() returns false
	// input: reportID = 26, branch = null, year = 2022, month = december
	// expected result: insertStockReport() returns false
	@Test
	public void insertStockReport_NullBranch()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
		method.setAccessible(true);

		String reportID = "26";
		String branch = null;
		String year = "2022";
		String month = "december";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
				0, "Item", 0);
		assertFalse(actualResult);
	}

	// functionality: checks that with null year
	// insertStockReport() returns false
	// input: reportID = 26, branch = eilat, year = null, month = december
	// expected result: insertStockReport() returns false
	@Test
	public void insertStockReport_NullYear()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
		method.setAccessible(true);

		String reportID = "26";
		String branch = "eilat";
		String year = null;
		String month = "december";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
				0, "Item", 0);
		assertFalse(actualResult);
	}

	// functionality: checks that with null month
	// insertStockReport() returns false
	// input: reportID = 26, branch = eilat, year = 2022, month = null
	// expected result: insertStockReport() returns false
	@Test
	public void insertStockReport_NullMonth()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
		method.setAccessible(true);

		String reportID = "26";
		String branch = "eilat";
		String year = "2022";
		String month = null;
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
				0, "Item", 0);
		assertFalse(actualResult);
	}

	// functionality: checks that with already existing report info
	// insertStockReport() returns false
	// input: reportID = 26, branch = haifa, year = 2022, month = august
	// expected result: insertStockReport() returns false
	@Test
	public void insertStockReport_insertStockReportAlreadyExists()
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = DBController.class.getDeclaredMethod("insertStockReport", String.class, String.class,
				String.class, String.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class);
		method.setAccessible(true);

		String reportID = "1";
		String branch = "haifa";
		String year = "2022";
		String month = "august";
		boolean actualResult = (boolean) method.invoke(DBController.getInstance(), reportID, branch, year, month, 0, 0,
				0, "Item", 0);
		assertFalse(actualResult);
	}

	////////////////// tests for createStockReportInDB()//////////////

	// functionality: checks that with valid given report data
	// createStockReportInDB() creates new report with the given data
	// and returns true
	// input: data = [eilat, 2022, december]
	// expected result: createStockReportInDB() returns true
//	@Test
//	public void createStockReportInDB_CreateSuccessfully() {
//		ArrayList<String> data = new ArrayList<>();
//		data.add("eilat");
//		data.add("2022");
//		data.add("december");
//		int numberOfRows = myDB.getNumberOfRowsInTable("stockreport"); 
//		assertEquals(numberOfRows, 19);
//		boolean actualResult = myDB.createStockReportInDB(data);
//		assertTrue(actualResult);
//		numberOfRows = myDB.getNumberOfRowsInTable("stockreport"); 
//		assertEquals(numberOfRows, 20);
//	}

	// functionality: checks that with already existing given report data
	// createStockReportInDB() returns false
	// and returns true
	// input: data = [eilat, 2020, may]
	// expected result: createStockReportInDB() returns false
	@Test
	public void createStockReportInDB_ReportAlreadyExists() {
		ArrayList<String> data = new ArrayList<>();
		data.add("eilat");
		data.add("2020");
		data.add("may");
		boolean actualResult = myDB.createStockReportInDB(data);
		assertFalse(actualResult);
	}

	// functionality: checks that with null branch
	// createStockReportInDB() returns false
	// and returns true
	// input: data = [null, 2022, december]
	// expected result: createStockReportInDB() returns false
	@Test
	public void createStockReportInDB_NullBranch() {
		ArrayList<String> data = new ArrayList<>();
		data.add(null);
		data.add("2022");
		data.add("december");
		boolean actualResult = myDB.createStockReportInDB(data);
		assertFalse(actualResult);
	}

	// functionality: checks that with null year
	// createStockReportInDB() returns false
	// and returns true
	// input: data = [eilat, null, december]
	// expected result: createStockReportInDB() returns false
	@Test
	public void createStockReportInDB_NullYear() {
		ArrayList<String> data = new ArrayList<>();
		data.add("eilat");
		data.add(null);
		data.add("december");
		boolean actualResult = myDB.createStockReportInDB(data);
		assertFalse(actualResult);
	}

	// functionality: checks that with null month
	// createStockReportInDB() returns false
	// and returns true
	// input: data = [eilat, 2022, null]
	// expected result: createStockReportInDB() returns false
	@Test
	public void createStockReportInDB_NullMonth() {
		ArrayList<String> data = new ArrayList<>();
		data.add("eilat");
		data.add("2022");
		data.add(null);
		boolean actualResult = myDB.createStockReportInDB(data);
		assertFalse(actualResult);
	}

	////////////////// tests for serverStarted()//////////////

	// functionality: checks that when the server launches when the last day in
	// the month arrives the reports automatically created serverStarted() creates
	// new report with the given data
	// input: echoServer
	// expected result: true
	@Test
	public void createReportsAutomatically() {
		EchoServer echoServer = new EchoServer(5555);
		echoServer.setRegularDateAndTime(new StubIDateAndTime());
		int numberOfRows = myDB.getNumberOfRowsInTable("stockreport");
		assertEquals(numberOfRows, 19);
		echoServer.serverStarted();
		numberOfRows = myDB.getNumberOfRowsInTable("stockreport");
		assertEquals(numberOfRows, 25);
	}
}
