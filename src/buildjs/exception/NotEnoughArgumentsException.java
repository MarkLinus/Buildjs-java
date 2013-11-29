package buildjs.exception;

public class NotEnoughArgumentsException extends Exception {
	private static final long serialVersionUID = 4366794511360596356L;

	public NotEnoughArgumentsException() {
		super();
	}
	
	public NotEnoughArgumentsException(String message) {
		super(message);
	}
	
	public NotEnoughArgumentsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotEnoughArgumentsException(Throwable cause) {
		super(cause);
	}
}
