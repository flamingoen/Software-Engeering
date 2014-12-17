package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.Activity;

public class ActivitySelectScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("# Activities #");
		if (currentUser().getActivities().isEmpty()) {
			System.out.println("no current activities\npress enter to return");
			companyUI.setScreen(new UserScreen());
		} else {
			for (Activity activity : currentUser().getActivities()) {
				printName(activity);
				printProject(activity);
				printTimeSpend(activity);
				printWorkPeriod(activity);
				System.out.println("\n");
			}
			System.out.println("Select an activity by name, type back to go back to the menu: ");
		}
	}

	private void printName(Activity activity) {
		System.out.println("# "+activity.getName()+" #"); 
	}

	protected void printWorkPeriod(Activity activity) {
		System.out.println("Work period:");
		if (activity.getWorkPeriod() != null) {
			System.out.println("Started: "
					+ activity.getWorkPeriod().getStartDate().get(5)
					+ "/" + activity.getWorkPeriod().getStartDate().get(2) + "/"
					+ activity.getWorkPeriod().getStartDate().get(1));
			System.out.println("Expected end date: "
					+ activity.getWorkPeriod().getEndDate().get(5)
					+ "/" + activity.getWorkPeriod().getEndDate().get(2) + "/"
					+ activity.getWorkPeriod().getEndDate().get(1));
		} else {
			System.out.println("work period not set yet");
		}
	}

	protected void printProject(Activity activity) {
		if (activity.getProject() != null) {
			System.out.println("Project: " + activity.getProject().getName());
		} else {
			System.out.println("");
		}
	}

	protected void printTimeSpend(Activity activity) {
		System.out.println("Time spend: "
				+ activity.GetUserTimeSpend(currentUser()));
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if (input.equals("back")) {
			companyUI.setScreen(new UserScreen());
		} else if (companyUI.getComApp().getCurrentUser().getActivityByName(input) != null) {
			companyUI.setScreen(new ActivityManageScreen(companyUI.getComApp().getCurrentUser().getActivityByName(input),false));
		}
		return false;
	}
}
