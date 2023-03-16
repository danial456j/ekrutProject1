package Server;

import java.net.InetAddress;

import ocsf.server.ConnectionToClient;

public class ClienClass {
	String msg;
	String IP;
	ConnectionToClient client;
	String status,userName;
	public ClienClass( ConnectionToClient client,String msg, String status,String IP) {
		this.msg = msg;
		this.client = client;
		this.status = status;
		this.IP=IP;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ConnectionToClient getClient() {
		return client;
	}
	public void setClient(ConnectionToClient client) {
		this.client = client;
	}
	

}
