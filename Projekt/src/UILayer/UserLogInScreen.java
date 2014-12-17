package UILayer;

import java.io.PrintWriter;

/**
 * Screen for login. should go to the main menu if user exist else return to the
 * start screen
 * 
 * @author martin
 */
public class UserLogInScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.print("user: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out){
		// finds developer and sets current user to that developer
		if (companyUI.getComApp().getDeveloperByInitials(input) != null) {
			companyUI.getComApp().setCurrentUser(input);
			if (currentUser().getRequest().isEmpty()){
				companyUI.setScreen(new UserScreen());	
			} else {
				companyUI.setScreen(new UserRequestScreen());
			}
		// error message, back to start screen
		} else {
			wrongInput();
			companyUI.setScreen(new StartScreen());
		}
		return false;
	}

}
