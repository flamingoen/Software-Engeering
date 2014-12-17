package UILayer;
/**
 * 
 */
import java.io.PrintWriter;

import ProgramLayer.IllegalInputExpection;
import ProgramLayer.Project;

/**
 * Main screen for managing a project.
 * @author Martin
 *
 */
public class ProjectMangageScreen extends Screen {

	private Project project;

	public ProjectMangageScreen(Project project) {
		super();
		this.project=project;
	}

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println(""
				+ "#Current project: "+project.getName()+" | Current user: "+currentUser().getInitials()+" #\n"
				+ "1: Create new activity\n"
				+ "2: Assign developer to an activity\n"
				+ "3: See activities\n"
				+ "4: Finish project\n"
				+ "5: Back");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws IllegalInputExpection {
		if (input.equals("1")){
			companyUI.setScreen(new ProjectAddActivityScreen(project));
		} else if (input.equals("2")){
			companyUI.setScreen(new ProjectAssignToActivityScreen(project));
		} else if (input.equals("3")){
			companyUI.setScreen(new ProjectActivityScreen(project));
		} else if (input.equals("4")){
			if (!project.hasActiveActivity()){
				project.archiveProject();
				System.out.println("Project has been archived");
				companyUI.setScreen(new UserScreen());
			} else 
			System.out.println("Can't close. Some activities have not been finished");
		} else if (input.equals("5")){
			companyUI.setScreen(new UserScreen());
		}
		return false;
	}
}
