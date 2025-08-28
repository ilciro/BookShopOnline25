package laptop.exception;

import java.io.Serial;

public class LogoutException extends Exception {
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public LogoutException(String message)
	{
		super(message);
	}

}
