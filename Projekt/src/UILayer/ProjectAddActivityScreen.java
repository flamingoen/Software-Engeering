package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.Activity;
import ProgramLayer.IllegalInputExpection;
import ProgramLayer.Project;

public class ProjectAddActivityScreen extends Screen {

	private Project project;

	public ProjectAddActivityScreen(Project project) {
		super();
		this.project = project;
	}

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Input activity name");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws IllegalInputExpection {
		 if (project.getActivityByName(input) == null) {
			Activity activity = new Activity(input);
			project.addActivity(activity);
			System.out.println("Activity: " + activity.getName() + " has been added");
			companyUI.setScreen(new ProjectMangageScreen(project));
		} else {
			System.out.println("An activity with that name exist in the project");
		}

		return false;
	}

}
