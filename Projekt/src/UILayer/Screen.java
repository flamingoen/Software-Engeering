package UILayer;
import java.io.PrintWriter;

import ProgramLayer.Developer;

/**
 * abstract class for the Screens
 * @author martin
 */

public abstract class Screen {
	CompanyUI companyUI;

	public void setCompanyUI(CompanyUI companyUI) {
		this.companyUI = companyUI;
	}
	
	abstract public void printMenu(PrintWriter out);
	abstract public boolean processInput(String input, PrintWriter out) throws Exception;
	
	//Methods often used in menues:
	
	//print statement when exiting
	public boolean exit(){
		System.out.println("exited");
		return true;
	}
	
	//print statement for wrong input
	public void wrongInput(){
		System.out.println("invalid input, please try again");
	}
	
	// returns the current user
	public Developer currentUser() {
		return companyUI.getComApp().getCurrentUser();
	}
}
