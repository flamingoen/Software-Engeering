package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.*;

/**
 * screen chooses an activity to add an developer to then goes to
 * AssignToActivitySetDeveloperScreen
 * 
 * @author martin
 * 
 */
public class ProjectAssignToActivityScreen extends Screen {

	private Project project;

	public ProjectAssignToActivityScreen(Project project) {
		super();
		this.project = project;
	}

	@Override
	public void printMenu(PrintWriter out) {
		if (project.getActivities().isEmpty()){
			System.out.println("no current activities in this project");
			companyUI.setScreen(new ProjectMangageScreen(project));
		}
		System.out.println("Activities in this project:");
		for (Activity activity : project.getActivities()) {
			System.out.println(activity.getName());
		}
		System.out.println("\nChoose an activity by name: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		if (project.getActivityByName(input) != null){
			if (project.getActivityByName(input).getWorkPeriod()==null){
				System.out.println("can't assign developer to an activity without a work period");
				companyUI.setScreen(new ProjectMangageScreen(project));
			} else {
			companyUI.setScreen(new ProjectAssignToActivitySetDeveloperScreen(project.getActivityByName(input)));
			}
		} else {
			wrongInput();
		}
		return false;
	}
}
