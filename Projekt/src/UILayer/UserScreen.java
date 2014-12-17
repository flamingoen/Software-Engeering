package UILayer;

import java.io.PrintWriter;
import java.util.Calendar;

import ProgramLayer.Interval;

/**
 * should show all possible functions of the application
 * 
 * @author martin
 *
 */

public class UserScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("# Current user: "+currentUser().getInitials()+" #\n"
				+ "1: Manage project\n" // see all current affiliated projects
				+ "2: Manage activity\n"   // see all affiliated activities
				+ "3: Add project\n"    // create new project
				+ "4: See Archived Projects\n" // see old affiliated projects
				+ "5: Register vacation\n" // add vacation period
				+ "6: See schedule\n"
				+ "7: Change User\n"
				+ "8: Exit"
		);
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		if(input.equals("1")){
			if (currentUser().getCurrentProjects().isEmpty()){
				System.out.println("no current projects at the moment");
				return false;
			}
			companyUI.setScreen(new ProjectSelectScreen()); // obel
		} else if(input.equals("2")){
			companyUI.setScreen(new ActivitySelectScreen()); // daniel
		} else if(input.equals("3")){
			companyUI.setScreen(new UserAddProjectScreen()); // obel
		} else if(input.equals("4")){
			companyUI.setScreen(new ProjectSeeArchiveScreen()); // daniel
		}else if(input.equals("5")){
			companyUI.setScreen(new UserVacationScreen()); // daniel
		}else if(input.equals("8")){
			return exit();
		} else if(input.equals("7")){
			companyUI.setScreen(new UserLogInScreen());
		} else if (input.equals("6")){
			printSchedule();
		} else {
			wrongInput();
		}
		return false;
	}

	private void printSchedule() {
		for(Interval period : currentUser().getUnavailableTime()){
			System.out.println(currentUser().getScheduleIn(period) + " from "+
					printDate(period.getStartDate())+" to "+printDate(period.getEndDate()));
		}
		
	}

	private String printDate(Calendar date) {
		return date.get(5)+"/"+date.get(2)+"/"+date.get(1);
	}

}
