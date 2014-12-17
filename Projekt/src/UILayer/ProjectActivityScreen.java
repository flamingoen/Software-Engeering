package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.*;

/**
 * should show all the activities in a project and give the option to manage
 * them.
 * 
 * @author martin
 */
public class ProjectActivityScreen extends ActivitySelectScreen {

	private Project project;

	public ProjectActivityScreen(Project project) {
		super();
		this.project = project;
	}

	@Override
	public void printMenu(PrintWriter out) {
		for (Activity activity : project.getActivities()) {
			System.out.println("# " + activity.getName() + " #");
			printTimeSpend(activity);
			printWorkPeriod(activity);
			System.out.println();
		}
		System.out.println("choose an acctivity or go back by typing back");
	}
	
	@Override
	protected void printTimeSpend(Activity activity) {
		System.out.println("Time spend:");
		if (activity.getDevelopers().isEmpty()) {
			System.out.println("No developers");
		} else {
			for (Developer dev : activity.getDevelopers()) {
				System.out.println(dev.getInitials() + ": "
						+ activity.GetUserTimeSpend(dev) + " hours");
			}
		}
		System.out.println("total: " + activity.GetTotalTimeSpend() + " hours");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if (input.equals("back")) {
			companyUI.setScreen(new ProjectMangageScreen(project));
		} else if (project.getActivityByName(input) != null) {
			companyUI.setScreen(new ActivityManageScreen(project.getActivityByName(input),true));
		}
		return false;
	}

}
