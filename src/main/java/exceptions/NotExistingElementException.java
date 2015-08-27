package exceptions;

public class NotExistingElementException extends RuntimeException {

	private static final long serialVersionUID = -5813289774601742447L;
	
	public NotExistingElementException(String message){
		super(message);
	}
	
}
