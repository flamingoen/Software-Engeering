package UILayer;
/**
 * martin
 */
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import ProgramLayer.Activity;
import ProgramLayer.DateServer;
import ProgramLayer.Project;

public class DataTestSamples1 extends DataTestSamples{

	public DataTestSamples1(CompanyUI companyUI) throws Exception {
		
		super(companyUI);
		
		//make a project
		Project p = new Project("test project", 2014, "0");
		p.setManager(companyUI.getComApp().getDeveloperByInitials("mot"));
		companyUI.getComApp().addProject(p);
		
		//make an activity
		Activity a = new Activity("test activity");
		p.addActivity(a);
		p.addActivity(new Activity("no workperiod"));
		a.setWorkPeriod(new GregorianCalendar(2014,7,7) , new GregorianCalendar(2014,10,10));
		a.addDeveloper(companyUI.getComApp().getDeveloperByInitials("mot"));
		a.addDeveloper(companyUI.getComApp().getDeveloperByInitials("dev1"));
		a.addDeveloper(companyUI.getComApp().getDeveloperByInitials("dev2"));
	}
}
