package Assignment;

public class Notice {

	private String from;
	private String to;
	private String message;
	
	public Notice() {
	}
	
	public Notice(String from, String to, String message) {
		setFrom(from);
		setTo(to);
		setMessage(message);
	}

	
	/*
	 * Getters and Setters below
	 */
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
