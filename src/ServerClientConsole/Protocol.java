package ServerClientConsole;

import java.io.Serializable;
import java.util.ArrayList;

import common.ProtocolType;

public class Protocol implements Serializable{
	private ProtocolType procKind;
	private Object data;
	private String[] dataStrings;
	private String ID;
	private String PW;
	private String name;
	private String subject;
	
	public Protocol(ProtocolType procKind, Object data) {
		this.procKind = procKind;
		this.data = data;
		cutString();
		
		switch(getProcKind()) {
		case LOGIN:
			ID = dataStrings[0];
			PW = dataStrings[1];
			break;
		case LOGIN_ACCEPT:
			break;
		case JOIN:
			ID = dataStrings[0];
			PW = dataStrings[1];
			name = dataStrings[2];
			subject = dataStrings[3];
			break;
		case JOIN_ACCEPT:
			break;
		case ADD_SUBJECT:
			ID = dataStrings[0];
			subject = dataStrings[1];
			name = dataStrings[2];
			break;
		case ID_EXIST:
			break;
		case REQUEST_ACCOUNT_LIST:
			break;
		case ACCOUNT_LIST:
			break;
		default:
			break;
		}
	}
	
	public void cutString() {
		if(data instanceof String)
			dataStrings = ((String)data).split(":");
	}
	
	public boolean isProcType(ProtocolType procType) {
		if(getProcKind() == procType)
			return true;
		
		return false;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getPW() {
		return PW;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSubject() {
		return subject;
	}

	public ProtocolType getProcKind() {
		return procKind;
	}

	public void setProcKind(ProtocolType procKind) {
		this.procKind = procKind;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
