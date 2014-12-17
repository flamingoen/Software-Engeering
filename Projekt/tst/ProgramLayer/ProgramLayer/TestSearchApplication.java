package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Test;

public class TestSearchApplication {

	/**
	 * test the functionality for the program to be able to get an developer by
	 * initial, an activity by name and a project by its serial number
	 * 
	 * @author martin
	 * @throws Exception
	 */
	
	@Test //martin
	public void testSearcchApplication() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));
		
		Developer dev = new Developer("dev");
		Project pro = new Project("project", 2014, "01");
		Activity act = new Activity("activity");
		comApp.setCurrentUser("dev");
		
		//assert that dev is not searchable
		assertNull(comApp.getDeveloperByInitials("dev"));
		
		//add the developer and assert that he is now searchable
		comApp.addDeveloper(dev);
		assertTrue(comApp.getDeveloperByInitials("dev").equals(dev));
		
		pro.setManager(dev);
		
		//assert that the project is not searchable
		assertNull(comApp.getProjectBySerial("01"));
		
		//add a project and assert that it is searchable
		comApp.addProject(pro);
		assertTrue(comApp.getProjectBySerial("01").equals(pro));
		
		//assert that activity is not searchable in project and developer
		assertNull(pro.getActivityByName("activity"));
		assertNull(dev.getActivityByName("activity"));
		
		//add the activity an assert that it is searchable
		pro.addActivity(act);
		act.setWorkPeriod(comApp.getDate(), comApp.getDate());
		assertTrue(pro.getActivityByName("activity").equals(act));
		act.addDeveloper(dev);
		assertTrue(dev.getActivityByName("activity").equals(act));

	}
}
