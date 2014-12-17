package UILayer;
/**
 * daniel
 */
import java.io.PrintWriter;
import ProgramLayer.*;

public class ActivityManageScreen extends Screen {

	private Activity activity;
	private boolean inProject;

	public ActivityManageScreen(Activity activity,boolean inProject) {
		super();
		this.activity = activity;
		this.inProject = inProject;
	}

	@Override
	public void printMenu(PrintWriter out) {
		printTitle();
		System.out.println("1: Set work period \n" + "2: Add time spent \n" + "3: Request assistance");
		printBackMenu();
	}

	private void printBackMenu() {
		if (inProject){
			System.out.println("4: back to project");
		} else {
			System.out.println("4: back to menu");
		}
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {

		if (input.equals("1")) {
			companyUI.setScreen(new ActivityWorkPeriodScreen(activity));
		} else if (input.equals("2")) {
			companyUI.setScreen(new ActivityAddTimeScreen(activity));
		} else if (input.equals("3")) {
			companyUI.setScreen(new ActivityRequestAssistanceScreen(activity));
		} else if (input.equals("4")) {
			if (inProject){
			companyUI.setScreen(new ProjectMangageScreen(activity.getProject()));
			} else {
				companyUI.setScreen(new UserScreen());
			}
		}
		return false;
	}

	private void printTitle() {
		System.out.print("# Activity: "
				+ activity.getName()
				+ " | Projecct: "+activity.getProject().getName()
				+ " | Current user: "+currentUser().getInitials() +"#\n");
	}
}
