package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.*;

/**
 * 
 * @author daniel
 *
 */
public class ProjectSeeArchiveScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("# Archived Projects # \n"
				+ "1: Show old managed projects \n"
				+ "2: Show all old projects \n"
				+ "3: Back to menu");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if (input.equals("1")) {
			for (Project project : companyUI.getComApp().getCurrentUser().getOwnArchivedProjects()) {
				printTitle(project);
				printActivities(project);
				printStartYear(project);
			}
		} else if (input.equals("2")){
			for (Project project : companyUI.getComApp().getArchivedProjects()){
				printTitle(project);
				printManager(project);
				printActivities(project);
			}
		} else if (input.equals("3")){
			companyUI.setScreen(new UserScreen());
		}
		return false;
	}

	private void printManager(Project project) {
		System.out.println("Manager: "+project.getManager().getInitials());
	}

	private void printStartYear(Project project) {
		System.out.println("Start year: "+project.getyear());
	}

	private void printActivities(Project project) {
		System.out.println("Activities:");
		for (Activity activity : project.getActivities()){
			System.out.println("Name: "+activity.getName());
		}
	}

	private void printTitle(Project project) {
		System.out.println("# "+project.getName()+" #");
	}

}
