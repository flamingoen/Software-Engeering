package UILayer;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ProgramLayer.*;
/**
 * martin
 */
public class UserVacationScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Insert period for your vacation\n(format: dd/mm/yyyy-dd/mm/yyyy):\n");
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
			if (calendarsIsInOrder(dates) && userIsNotWorkingOnActivity(dates)) {
				currentUser().registerVacation(new Interval(dates[0],dates[1]));
				companyUI.setScreen(new UserScreen());
			}
		} else {
			wrongInput();
		}
		return false;
	}

	private boolean userIsNotWorkingOnActivity(Calendar[] dates) {
		return currentUser().isAvailable(new Interval(dates[0], dates[1]));
	}

	private boolean calendarsIsInOrder(Calendar[] dates) {
		return dates[0].before(dates[1]) || dates[0].before(companyUI.getComApp().getDate());
	}
}
