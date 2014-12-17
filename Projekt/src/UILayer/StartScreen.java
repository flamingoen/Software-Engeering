package UILayer;

import java.io.PrintWriter;

/**
 * Root screen for all other menus, from here a developer can log in or manage
 * the application
 * 
 * @author martin
 */

public class StartScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("" + "1: Log in\n" + "2: Create User\n" + "3: Exit");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		// lead to login
		if ("1".equals(input)) {
			companyUI.setScreen(new UserLogInScreen());
		// lead to add user 
		} else if ("2".equals(input)) {
			companyUI.setScreen(new UseraddUserScreen());
		// exit program 
		} else if ("3".equals(input)) {
			return exit();
		// initialize data-set
		} else if ("initTestData".equals(input)){
			new DataTestSamples1(companyUI);
			System.out.println("initialized developers");
		// error message
		} else {
			wrongInput();
		}
		return false;
	}

}
