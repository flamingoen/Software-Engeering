package ProgramLayer;

//martin
public class IllegalInputExpection extends Exception {
	
	public IllegalInputExpection(String op) {
		super("Invalid input in method "+op);
	}
}
