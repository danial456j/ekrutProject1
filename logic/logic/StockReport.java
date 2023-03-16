package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class StockReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reportId;
	private String branch;
	private String totalInventory;
	private String totalTimesLowedToThreshold;
	private String totalTimesLowedToZero;
	private String mostAvailableItem;
	private String month;
	private String year;
	
	public StockReport(String reportId, String branch, String totalInventory, String totalTimesLowedToThreshold,
			String totalTimesLowedToZero, String mostAvailableItem, String month, String year) {
		super();
		this.reportId = reportId;
		this.branch = branch;
		this.totalInventory = totalInventory;
		this.totalTimesLowedToThreshold = totalTimesLowedToThreshold;
		this.totalTimesLowedToZero = totalTimesLowedToZero;
		this.mostAvailableItem = mostAvailableItem;
		this.month = month;
		this.year = year;
	}
	
	public StockReport() {
		
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTotalInventory() {
		return totalInventory;
	}

	public void setTotalInventory(String totalInventory) {
		this.totalInventory = totalInventory;
	}

	public String getTotalTimesLowedToThreshold() {
		return totalTimesLowedToThreshold;
	}

	public void setTotalTimesLowedToThreshold(String totalTimesLowedToThreshold) {
		this.totalTimesLowedToThreshold = totalTimesLowedToThreshold;
	}

	public String getTotalTimesLowedToZero() {
		return totalTimesLowedToZero;
	}

	public void setTotalTimesLowedToZero(String totalTimesLowedToZero) {
		this.totalTimesLowedToZero = totalTimesLowedToZero;
	}
	

	public String getMostAvailableItem() {
		return mostAvailableItem;
	}

	public void setMostAvailableItem(String mostAvailableItem) {
		this.mostAvailableItem = mostAvailableItem;
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

	@Override
	public String toString() {
		return "StockReport [reportId=" + reportId + ", branch=" + branch + ", totalInventory=" + totalInventory
				+ ", totalTimesLowedToThreshold=" + totalTimesLowedToThreshold + ", totalTimesLowedToZero="
				+ totalTimesLowedToZero + ", mostAvailableItem=" + mostAvailableItem + ", month=" + month + ", year="
				+ year + "]";
	}
}
