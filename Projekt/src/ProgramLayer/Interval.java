package ProgramLayer;

import java.util.Calendar;

//daniel
public class Interval {
	private Calendar startDate, endDate;

	//daniel
	public Interval(Calendar start, Calendar end) {
		startDate = start;
		endDate = end;
	}

	//daniel
	public boolean inInterval(Calendar date) {
		if (date.after(startDate) && date.before(endDate)) {
			return true;
		}
		return false;
	}

	//daniel
	public boolean inInterval(Interval interval) {
		if (isOverlapping(interval)) {
			return true;
		}
		return false;
	}

	//martin
	private boolean isOverlapping(Interval interval) {
		if ((inInterval(interval.startDate) || inInterval(interval.endDate))){
			return true;
		}
		if ((interval.inInterval(startDate) || interval.inInterval(endDate))){
			return true;
		}
		if (interval.startDate.equals(startDate) || interval.endDate.equals(endDate)){
			return true;
		}
		return false;
	}

	//martin
	public Calendar getStartDate() {
		return startDate;
	}

	//martin
	public Calendar getEndDate() {
		return endDate;
	}
}
