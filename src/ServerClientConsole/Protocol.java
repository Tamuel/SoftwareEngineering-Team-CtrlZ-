package ServerClientConsole;

import java.io.Serializable;
import java.util.ArrayList;

public class Protocol implements Serializable{
	private String procKind;
	private Object data;
	private String[] dataStrings;
	private String ID;
	private String PW;
	private String name;
	private String subject;
	
	public Protocol(String procKind, Object data) {
		this.procKind = procKind;
		this.data = data;
		cutString();
		
		if(isRequestLogin()) {
			ID = dataStrings[0];
			PW = dataStrings[1];
		}
		else if(isRequestJoin()) {
			ID = dataStrings[0];
			PW = dataStrings[1];
			name = dataStrings[2];
			subject = dataStrings[3];
		}
	}
	
	public void cutString() {
		if(data instanceof String)
			dataStrings = ((String)data).split(":");
	}
	
	public boolean isIdExist() {
		if(getProcKind().equals("[ID_EXIST]"))
			return true;
		
		return false;
	}
	
	public boolean isRequestLogin() {
		if(getProcKind().equals("[LOGIN]"))
			return true;
		
		return false;
	}
	
	public boolean isLoginAccept() {
		if(getProcKind().equals("[LOGIN_ACCEPT]"))
			return true;
		
		return false;
	}
	
	public boolean isRequestJoin() {
		if(getProcKind().equals("[JOIN]"))
			return true;
		
		return false;
	}

	public boolean isJoinAccept() {
		if(getProcKind().equals("[JOIN_ACCEPT]"))
			return true;
		
		return false;
	}
	
	public boolean isRequestAccountList() {
		if(getProcKind().equals("[REQUEST_ACCOUNT_LIST]"))
			return true;
		
		return false;
	}
	
	public boolean isAccountListReceive() {
		if(getProcKind().equals("[ACCOUNT_LIST]"))
			return true;
		
		return false;
	}
			
	public boolean isChangeAccount() {
		if(getProcKind().equals("[CHANGE_ACCOUNT]"))
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

	public String getProcKind() {
		return procKind;
	}

	public void setProcKind(String procKind) {
		this.procKind = procKind;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
