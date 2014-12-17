package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ProgramLayer.Activity;

public class ActivityWorkPeriodScreen extends Screen {

	private Activity activity;

	public ActivityWorkPeriodScreen(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("input date:\n(format: dd/mm/yyyy-dd/mm/yyyy)\n");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		String[] info = input.split("-");
		if (info.length == 2) {
			Calendar[] dates = new Calendar[2];
			for (int i = 0; i < info.length; i++) {
				String[] date = info[i].split("/");
				for (String j : date) {
					try {
						Integer.parseInt(j);
					} catch (Exception e) {
						wrongInput();
						return false;
					}
				}
				dates[i] = new GregorianCalendar(Integer.parseInt(date[2]),
						Integer.parseInt(date[1]), Integer.parseInt(date[0]));
			}
			if (dates[1].before(dates[0]) || dates[0].before(companyUI.getComApp().getDate())){
				wrongInput();
				return false;
			}
			activity.setWorkPeriod(dates[0], dates[1]);
			companyUI.setScreen(new ActivityManageScreen(activity,false));
		} else {
			wrongInput();
		}
		return false;
	}
}
