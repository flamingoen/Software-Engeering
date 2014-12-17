package ProgramLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author daniel
 *
 */

public class Activity {
	private String name;
	private List<Developer> developers = new ArrayList<Developer>();
	private HashMap<Developer,Double> developersMap = new HashMap<Developer,Double>();
	private Interval workPeriod;
	private int Scheduledtime;
	private Project project;
	
	//daniel
	public Activity(String name){
		this.name = name;
	}
	
	//daniel
	public List<Developer> getDevelopers() {
		return developers;
	}
	
	//daniel
	public void addDeveloper(Developer dev) throws Exception{
		if (project==null || !project.getCompanyApplication().getDevelopers().contains(dev)){
			throw new IllegalInputExpection("addDeveloper");
		} else if(!dev.isAvailable(getWorkPeriod())){
			throw new OverBookedException("add developer to activity");
		}
		developers.add(dev);
		developersMap.put(dev, 0.);
		dev.setUnavailableTime(this.workPeriod, this.name);
		dev.setActivity(this);
		
	}
	
	//daniel
	public int getScheduledTime() {
		return Scheduledtime;
	}
	
	//daniel
	public void setTime(int time) {
		this.Scheduledtime = time;
	}
	
	//martin
	public Double GetUserTimeSpend(Developer dev) {
		return developersMap.get(dev);
	}
	
	//martin
	public Double GetTotalTimeSpend() {
		Double total=0.;
		for (Developer dev : developers){
			total += developersMap.get(dev);
		}
		return total;
	}
	
	//martin
	public void addTimeSpend(double d) {
		developersMap.put(getCurrentUser(), GetUserTimeSpend(getCurrentUser())+d);
	}

	//martin
	private Developer getCurrentUser() {
		return project.getCompanyApplication().getCurrentUser();
	}

	//martin
	public void setProject(Project project) {
		this.project = project;
	}
	
	//daniel
	public void setWorkPeriod(Calendar start, Calendar end) throws Exception{
		if (start.after(end)){
			throw new DateExpriedException("end date can't be before start date");
		} else if (start.before(project.getCompanyApplication().getDate())){
			throw new DateExpriedException("cant set a start date before today");
		}
		workPeriod = new Interval(start, end);
	}
	
	//martin
	public String getName(){
		return this.name;
	}
	
	//daniel
	public Interval getWorkPeriod(){
		return workPeriod;
	}

	//martin
	public Project getProject() {
		return project;
	}

	public void unassignDevelopers() {
		for (Developer dev : developers){
			dev.removeActivity(this);
		}
		developers = new ArrayList<Developer>();
	}
}
