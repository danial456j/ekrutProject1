package clientTest;

import java.util.ArrayList;

import logic.User;

public interface IClientUIChatAccept {
	public void ClientUIChatAcceptMethod(ArrayList<String> fromClient);
	public void setMsgRecieved(String msgRecieved);
	public String getMsgRecieved();
	public void setUser(User u1);
	public User getUser();
	public void setMsgRecived(String msgRecived);
	public String getMsgRecived();	
}
