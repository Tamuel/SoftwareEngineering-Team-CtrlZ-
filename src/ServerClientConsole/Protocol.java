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
	
	private String topic;
	private String content;
	private String year;
	private String month;
	private String day;
	private String hour;
	
	/**
	 * Make Protocol for communicate between client and server
	 * @param procKind
	 * @param data Must not be null, if you want input void you should input ""
	 */
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
			
		case MAKE_ASSIGNMENT:
			ID = dataStrings[0];
			topic = dataStrings[1];
			content = dataStrings[2];
			year = dataStrings[3];
			month = dataStrings[4];
			day = dataStrings[5];
			hour = dataStrings[6];
			break;

		case SET_REFRESH:
			subject = dataStrings[0];
			break;
			
		case QUIT:
			ID = dataStrings[0];
			break;
			
		case SUBMIT_ASSIGNMENT:
			break;
			
		case APPRAISAL_ASSIGNMENT:
			break;
			
		case MAKE_QUESTION:
			break;
			
		case MAKE_ANSWER:
			break;
			
		case NEED_REFRESH:
			ID = dataStrings[0];
			break;
			
		case REFRESH:
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

	public String getTopic() {
		return topic;
	}

	public String getContent() {
		return content;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public String getHour() {
		return hour;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
