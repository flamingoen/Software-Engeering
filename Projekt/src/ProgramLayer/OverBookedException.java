package ProgramLayer;

//martin
public class OverBookedException extends Exception {
	
	public OverBookedException(String op) {
		super("can't "+op + ", developer overbooked");
	}
}
