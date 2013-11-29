package buildjs.exception;

public class UndeclaredDirectiveException extends Exception {
	private static final long serialVersionUID = 5034503497976026779L;

	public UndeclaredDirectiveException() {
		super();
	}
	
	public UndeclaredDirectiveException(String message) {
		super(message);
	}
	
	public UndeclaredDirectiveException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UndeclaredDirectiveException(Throwable cause) {
		super(cause);
	}
}
