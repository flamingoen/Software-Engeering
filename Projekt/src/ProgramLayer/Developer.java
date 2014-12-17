package ProgramLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//martin
public class Developer {

	private String initials;
	private List<Project> projects = new ArrayList<Project>();
	private List<Interval> unavailableTime = new ArrayList<Interval>();
	private List<Activity> activities = new ArrayList<Activity>();
	private HashMap<Interval,String> unavailableTimeMap = new HashMap<Interval,String>();
	private Queue<Activity> requests = new LinkedList<Activity>();
	private Queue<Developer> requesters = new LinkedList<Developer>();
	private List<Project> ownArchivedProjects = new ArrayList<Project>();
	
	//martin
	public Developer(String initials) {
		this.initials=initials;
		projects = new ArrayList<Project>();
	}

	//martin
	public String getInitials() {
		return initials;
	}

	//martin
	public void setManager(Project project){
		projects.add(project);
	}
	
	//daniel
	public void setUnavailableTime(Interval workPeriod, String name)
			throws Exception {
		if (workPeriod == null) {
			throw new NullPointerException("no workperiod given");
		}
		unavailableTimeMap.put(workPeriod, name);
		unavailableTime.add(workPeriod);
	}

	//daniel
	public boolean isAvailable(Interval workPeriod) {
		for(Interval interval: unavailableTime)
			if(workPeriod.inInterval(interval)){
				return false;
			}
			return true;
	}

	//martin
	public void registerVacation(Interval interval) throws Exception {
		if (!isAvailable(interval)){
			throw new OverBookedException("register vacation");
		} else if(interval.getStartDate().after(interval.getEndDate())){
			throw new DateExpriedException("Start date was before end date");
		}
		setUnavailableTime(interval, "Vacation");
	}

	//martin
	public Queue<Activity> getRequest() {
		return requests;
	}

	//martin
	public Queue<Developer> getRequester() {
		return requesters;
	}

	//martin
	public void acceptRequest() throws Exception {
		if (!isAvailable(requests.peek().getWorkPeriod())){
			throw new OverBookedException("accept assistance request");
		} else if (requests.peek().getWorkPeriod().getStartDate().before(requests.peek().getProject().getCompanyApplication().getDate())){
			throw new DateExpriedException("date outdated in acceptRequest");
		}
		requests.poll().addDeveloper(this);
	}

	//martin
	public void addRequest(Activity activity) {
		requests.add(activity);
		requesters.poll();
	}

	//martin
	public void addRequester(Developer currentUser) {
		requesters.add(currentUser);
	}

	//martin
	public void DeclineRequest() {
		requests.poll();
		requesters.poll();
	}
	
	//daniel
	public List<Project> getOwnArchivedProjects(){
		return ownArchivedProjects;
	}

	//daniel
	public List<Project> getCurrentProjects() {
		return projects;
	}

	//martin
	public void archive(Project project) {
		projects.remove(project);
		ownArchivedProjects.add(project);
	}
	
	//daniel
	public List<Activity> getActivities() {
		return activities;
	}
	
	//daniel
	public Activity getActivityByName(String name) {
		for(Activity activity: activities){
			if(activity.getName().equals(name))
				return activity;		
		}
		return null;
	}

	//daniel
	public void setActivity(Activity activity) {
		activities.add(activity);
	}

	public List<Interval> getUnavailableTime() {
		return unavailableTime;
	}

	public String getScheduleIn(Interval period) {
		return unavailableTimeMap.get(period);
	}

	public void removeActivity(Activity activity) {
		activities.remove(activity);
	}
}
