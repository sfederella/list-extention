package exceptions;

public class NotFlattenizableListException extends RuntimeException {

	private static final long serialVersionUID = -5813289774601742447L;
	
	public NotFlattenizableListException(String message){
		super(message);
	}
	
}
