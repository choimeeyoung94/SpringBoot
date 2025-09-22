package org.shark.boot07.user.exception;

// uncheckedException
public class UserNotFoundException extends RuntimeException  {
	
	
	private static final long serialVersionUID = -1717225831662653657L;

	private int errorCode;
	
	
	public UserNotFoundException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}


	public int getErrorCode() {
		return errorCode;
	}
	
	
	
	
	
	
	
}
