package logic;

import java.io.Serializable;

public class CustomerReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reportId;
	private String machineLocation;
	private String numberOfOrders0To3;
	private String numberOfOrders3To5;
	private String numberOfOrders5To10;
	private String numberOfOrders10Plus;
	private String month;
	private String year;
	
	
	public CustomerReport(String reportId, String machineLocation, String numberOfOrders0To3, String numberOfOrders3To5,
			String numberOfOrders5To10, String numberOfOrders10Plus, String month, String year) {
		super();
		this.reportId = reportId;
		this.machineLocation = machineLocation;
		this.numberOfOrders0To3 = numberOfOrders0To3;
		this.numberOfOrders3To5 = numberOfOrders3To5;
		this.numberOfOrders5To10 = numberOfOrders5To10;
		this.numberOfOrders10Plus = numberOfOrders10Plus;
		this.month = month;
		this.year = year;
	}
	
	public CustomerReport() {
		
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getMachineLocation() {
		return machineLocation;
	}
	public void setMachineLocation(String machineLocation) {
		this.machineLocation = machineLocation;
	}
	public String getNumberOfOrders0To3() {
		return numberOfOrders0To3;
	}
	public void setNumberOfOrders0To3(String numberOfOrders0To3) {
		this.numberOfOrders0To3 = numberOfOrders0To3;
	}
	public String getNumberOfOrders3To5() {
		return numberOfOrders3To5;
	}
	public void setNumberOfOrders3To5(String numberOfOrders3To5) {
		this.numberOfOrders3To5 = numberOfOrders3To5;
	}
	public String getNumberOfOrders5To10() {
		return numberOfOrders5To10;
	}
	public void setNumberOfOrders5To10(String numberOfOrders5To10) {
		this.numberOfOrders5To10 = numberOfOrders5To10;
	}
	public String getNumberOfOrders10Plus() {
		return numberOfOrders10Plus;
	}
	public void setNumberOfOrders10Plus(String numberOfOrders10Plus) {
		this.numberOfOrders10Plus = numberOfOrders10Plus;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CustomerReport [reportId=" + reportId + ", machineLocation=" + machineLocation + ", numberOfOrders0To3="
				+ numberOfOrders0To3 + ", numberOfOrders3To5=" + numberOfOrders3To5 + ", numberOfOrders5To10="
				+ numberOfOrders5To10 + ", numberOfOrders10Plus=" + numberOfOrders10Plus + ", month=" + month
				+ ", year=" + year + "]";
	}
	
	

}
