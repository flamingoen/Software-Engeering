package UILayer;

import java.io.PrintWriter;

import ProgramLayer.*;
/**
 * martin 
 */
public class UserRequestScreen extends Screen {

	private Activity request;
	private Developer requestor;

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("you have "+currentUser().getRequest().size()+" requests left");
		request = currentUser().getRequest().poll();
		requestor = currentUser().getRequester().poll();
		printRequest(request, requestor);
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if (input.equals("y")){
			request.addDeveloper(currentUser());
		} else if (!input.equals("n")){
			wrongInput();
			return false;
		} 
		if (!currentUser().getRequest().isEmpty()){
			companyUI.setScreen(new UserRequestScreen());
		} else {
			companyUI.setScreen(new UserScreen());
		}
		return false;
	}

	private void printRequest(Activity request, Developer requestor) {
		System.out.println("requestor: "+requestor.getInitials()+"\nactivity: "+request.getName()+"\naccept request y/n?");
	}
}
