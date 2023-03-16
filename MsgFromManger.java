package logic;

public class MsgFromManger {
	private String msg;

	public MsgFromManger(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "MsgFromManger [msg=" + msg + "]";
	}
	
}
