package ProgramLayer;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

//martin
public class Project {

	private String name;
	private int year;
	private String serialNumber;
	private Developer manager;
	private CompanyApplication comApp;
	private List<Activity> activities = new ArrayList<Activity>();

	//martin
	public Project(String name, int year, String info) {
		this.name = name;
		this.year = year;
		this.serialNumber = info;
	}

	//martin
	public void setCompanyApplication(CompanyApplication comApp) {
		this.comApp = comApp;
	}

	//martin
	public String getName() {
		return name;
	}

	//martin
	public int getyear() {
		return year;
	}

	//martin
	public String getserialNumber() {
		return serialNumber;
	}

	//daniel
	public Developer getManager() {
		return manager;
	}

	//daniel
	public void setManager(Developer dev) {
		this.manager = dev;
		dev.setManager(this);
	}

	//daniel
	public List<Activity> getActivities() {
		return activities;
	}

	//daniel
	public void addActivity(Activity activity) throws IllegalInputExpection {
		if (getActivityByName(activity.getName())!=null){
			throw new IllegalInputExpection("setActivity");
		}
		activities.add(activity);
		activity.setProject(this);
	}

	//daniel
	public boolean hasActivity(Activity activity) {
		for (Activity i : activities) {
			if (activity.equals(i)) {
				return true;
			}
		}
		return false;
	}

	//daniel
	private boolean currentUserIsManager() {
		return comApp.getCurrentUser().equals(manager);
	}

	//daniel
	public void addBudgetTimeToActivity(Activity activity, int time)
			throws Exception {
		if (!currentUserIsManager() || !hasActivity(activity)) {
			throw new InsufficientRightsException("add time to activity");
		}
		activity.setTime(time);
	}

	//daniel
	public void addTimeToActivity(Activity activity, double d) throws Exception {
		if (d < 0 || d % 1 != 0) {
			throw new IllegalInputExpection("addTimeToActivity");
		}
		activity.addTimeSpend(d);
	}

	//daniel
	public CompanyApplication getCompanyApplication() {
		return comApp;
	}

	//martin
	public void archiveProject() throws IllegalInputExpection {
		if (!this.hasActiveActivity()) {
			comApp.setArchivedProject(this);
			removeDevelopersFromActivities();
			manager.archive(this);
		} else {
			throw new IllegalInputExpection("archiveProject");
		}

	}

	private void removeDevelopersFromActivities() {
		for (Activity activity : activities){
			activity.unassignDevelopers();
		}
	}

	//daniel
	public boolean hasActiveActivity() {
		for (Activity activity : activities) {
			if (activity.getWorkPeriod() == null || activity.getWorkPeriod().getEndDate().after(comApp.getDate())) {
				return true;
			}
		}
		return false;
	}

	//daniel
	public Activity getActivityByName(String name) {
		for (Activity activity : activities) {
			if (activity.getName().equals(name))
				return activity;
		}
		return null;
	}
}