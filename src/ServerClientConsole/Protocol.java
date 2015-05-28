package ServerClientConsole;

import java.io.Serializable;
import java.util.ArrayList;

public class Protocol implements Serializable{
	private String procKind;
	private Object data;
	private String[] dataStrings;
	
	public Protocol(String procKind, Object data) {
		this.procKind = procKind;
		this.data = data;
		cutString();
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
		return dataStrings[0];
	}
	
	public String getPW() {
		return dataStrings[1];
	}
	
	public String getName() {
		return dataStrings[2];
	}
	
	public String getSubject() {
		return dataStrings[3];
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
