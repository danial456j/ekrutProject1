package clientTest;

import java.util.ArrayList;

import logic.User;

public class StubClientUIChatAccept implements IClientUIChatAccept {

	public User u1;
	public String msgRecieved;
	public String msgRecived;
	
	@Override
	public void ClientUIChatAcceptMethod(ArrayList<String> fromClient) {
		
	}

	@Override
	public void setMsgRecieved(String msgRecieved) {
		this.msgRecieved = msgRecieved;
		
	}

	@Override
	public String getMsgRecieved() {
		return msgRecieved;
	}

	@Override
	public void setUser(User u1) {
		this.u1 = u1;
		
	}

	@Override
	public User getUser() {
		return u1;
	}

	@Override
	public void setMsgRecived(String msgRecived) {
		this.msgRecived = msgRecived;
		
	}

	@Override
	public String getMsgRecived() {
		return msgRecived;
	}

}
