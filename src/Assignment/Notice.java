package Assignment;

public class Notice {

	private String destination; // destined ID
	private String message;
	
	public Notice() {
	}
	
	public Notice(String message) {
		setMessage(message);
	}
	
	public Notice(String destination, String message) {
		setDestination(destination);
		setMessage(message);
	}

	
	/*
	 * Getters and Setters below
	 */
	public String getDestination() {
		return this.destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
