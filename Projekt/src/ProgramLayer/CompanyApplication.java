package ProgramLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is the main layer for the application, 
 * which should put the components for the application together
 * 
 * @author martin
 *
 */

public class CompanyApplication {

	private List<Developer> developers = new ArrayList<Developer>();
	private List<Project> projects = new ArrayList<Project>();
	private List<Project> archivedProjects = new ArrayList<Project>();
	private Developer currentUser;
	private DateServer dateServer = new DateServer();

	//martin
	public List<Developer> getDevelopers() {
		return developers;
	}

	//daniel
	public void addDeveloper(Developer developer) throws Exception {
		if (getDeveloperByInitials(developer.getInitials())!=null){
			throw new IllegalInputExpection("addDeveloper");
		}
		developers.add(developer);
	}
	
	//martin
	public List<Project> getProjects() {
		return projects ;
	}

	//martin
	public void addProject(Project project) {
		project.setCompanyApplication(this);
		projects.add(project);
	}

	//martin
	public void setCurrentUser(String string) {
		currentUser = getDeveloperByInitials(string);
	}
	
	//daniel
	public Developer getDeveloperByInitials(String initials) {
		for(Developer dev: developers){
			if(dev.getInitials().equals(initials))
				return dev;		
		}
		return null;
	}
	
	//daniel
	public Project getProjectBySerial(String input) {
		for(Project project : projects){
			if(project.getserialNumber().equals(input))
				return project;		
		}
		return null;
	}

	//martin
	public Developer getCurrentUser() {
		return currentUser;
	}
	
	//daniel
	public void setArchivedProject(Project project){
		archivedProjects.add(project);
	}
	
	//daniel
	public List<Project> getArchivedProjects(){
		return archivedProjects;
	}
	
	//martin
	public void requestAssistance(Activity activity, Developer dev) throws Exception {
		if (activity.getProject()==null || !getDevelopers().contains(dev)){
			throw new IllegalInputExpection("requestAssistance");
		} else if (currentUser==null){
			throw new InsufficientRightsException("request assistance");
		} else if (!dev.isAvailable(activity.getWorkPeriod())){
			throw new OverBookedException("ask developer for assistance");
		}
		dev.addRequest(activity);
		dev.addRequester(currentUser);
	}
	
	//martin
	public void setDateServer(DateServer dateServer){
		this.dateServer = dateServer;
	}

	//martin
	public Calendar getDate() {
		return dateServer.getDate();
	}
}
