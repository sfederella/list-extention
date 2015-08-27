package exceptions;

public class EmptyListException extends RuntimeException {

	private static final long serialVersionUID = -5813289774601742447L;
	
	public EmptyListException(String message){
		super(message);
	}
	
}
