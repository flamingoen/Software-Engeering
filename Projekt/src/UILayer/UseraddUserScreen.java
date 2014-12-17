package UILayer;

import java.io.PrintWriter;

import ProgramLayer.Developer;
/**
 * daniel
 */
public class UseraddUserScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("input initials for new user: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if (companyUI.getComApp().getDeveloperByInitials(input)!=null){
			System.out.println("User with initials exists in the program");
		} else {
			companyUI.getComApp().addDeveloper(new Developer(input));
			companyUI.setScreen(new StartScreen());
		}
		return false;
	}

}
