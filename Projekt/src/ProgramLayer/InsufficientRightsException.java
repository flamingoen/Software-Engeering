package ProgramLayer;

//martin
public class InsufficientRightsException extends Exception {
	
	public InsufficientRightsException(String op) {
		super(op + " not allowed if user is not project manager");
	}
}
