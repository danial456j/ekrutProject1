package logic;

public class Threshold {
	String Location;
	String Threshold;
	
	public Threshold(String Location, String Threshold) {
		super();
		this.Location=Location;
		this.Threshold= Threshold;
	}
	
	public String getLocation()  {
		return Location;
	}


	public void seLocation(String Location) {
		this.Location= Location;
	}
	public String getThreshold()  {
		return Threshold;
	}


	public void setThreshold(String Threshold) {
		this.Threshold = Threshold;
	}
	
	public String toString() {
		return String.format("%s %s  \n", Location, Threshold);
	}

}
