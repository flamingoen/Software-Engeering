package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.Activity;

public class ActivityAddTimeScreen extends Screen {

	private Activity activity;

	public ActivityAddTimeScreen(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Input amount of hours (ex 1.5): ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		double in;
		try {
			in = Double.parseDouble(input);
		} catch (Exception e) {
			System.out.println("1");
			wrongInput();
			return false;
		}
		if (in > 0. && in % 0.5 == 0  && UserOnActivity()) {
			activity.addTimeSpend(in);
			companyUI.setScreen(new ActivityManageScreen(activity,false));
		} else {
			System.out.println("2");
			wrongInput();
			return false;
		}
		return false;
	}

	private boolean UserOnActivity() {
		return activity.getDevelopers().contains(currentUser());
	}

}
