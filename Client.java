package logic;

public class Client {
	String Host;
	String IP;
	String Status;
	
	public Client(String ip,String host) {
		
		this.Host = host;
		this.IP = ip;
		Status = "Connected";
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		this.Host = host;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		this.IP = ip;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public String toString() {
		return IP + " " + Host + " " + Status;
	}
	
	
	
	

}
