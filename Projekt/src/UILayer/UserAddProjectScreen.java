package UILayer;

import java.io.PrintWriter;
import java.util.Calendar;

import ProgramLayer.Project;

/**
 * screen for adding a new project
 * the current user is also set as manager of that project.
 * @author martin
 *
 */
public class UserAddProjectScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Input name of the new project: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		if (input.equals("back")){
			companyUI.setScreen(new UserScreen());
		} else {
			companyUI.getComApp().getDate();
			Project project = new Project(input,Calendar.YEAR,companyUI.NewId());
			companyUI.getComApp().addProject(project);
			System.out.println("\nnew project added\n"
					+ "Name:\t"+input+"\n"
					+ "SerialNumber:\t"+project.getserialNumber());
			project.setManager(companyUI.getComApp().getCurrentUser());
			companyUI.setScreen(new ProjectMangageScreen(project));
		}
		return false;
	}

}
