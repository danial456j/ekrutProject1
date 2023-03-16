package clientTest;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import logic.User;

public class RegularClientUI implements IClientUIChatAccept {
	public User u1 = ChatClient.u1;
	public String msgRecieved = ChatClient.msgRecieved;
	public String msgRecived = ChatClient.msgRecived;
	
	@Override
	public void ClientUIChatAcceptMethod(ArrayList<String> fromClient) {
		ClientUI.chat.accept(fromClient);
	}
	@Override
	public void setMsgRecieved(String msgRecieved) {
		this.msgRecieved = msgRecieved;
		
	}
	@Override
	public String getMsgRecieved() {
		return ChatClient.msgRecieved;
	}
	@Override
	public void setUser(User u1) {
		this.u1 = u1;
		
	}
	@Override
	public User getUser() {
		return ChatClient.u1;
	}
	@Override
	public void setMsgRecived(String msgRecived) {
		this.msgRecived = msgRecived;
		
	}
	@Override
	public String getMsgRecived() {
		return ChatClient.msgRecived;
	}
}
