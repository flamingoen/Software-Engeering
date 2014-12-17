package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;
import java.util.List;
import ProgramLayer.Project;

/**
 * screen for selecting one of the current users projects. the user have to be manager to select it.
 * @author martin
 *
 */
public class ProjectSelectScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		List<Project> currentProjects = companyUI.getComApp().getCurrentUser().getCurrentProjects();
		for(Project project : currentProjects){
			System.out.println("# "+project.getName()+" #\n"+"Serial Number: "+project.getserialNumber()+"\n");
		}
		System.out.println("Enter Serial number: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		if (getProject(input) != null){
			companyUI.setScreen(new ProjectMangageScreen(getProject(input)));
		} else {
			wrongInput();
			companyUI.setScreen(new UserScreen());
		}
		return false;
	}

	private Project getProject(String input) {
		return companyUI.getComApp().getProjectBySerial(input);
	}

}
