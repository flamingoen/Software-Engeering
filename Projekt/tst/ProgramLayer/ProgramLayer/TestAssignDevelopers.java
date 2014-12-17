package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * 
 * @author Daniel
 * Manage Project data set B
 */
public class TestAssignDevelopers {

	/**
	 * test that you can add a developer to existing activity. (B1)
	 * @throws Exception
	 */
	@Test //daniel
	public void testAssignDeveloperToActivity() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));
		
		// add a project to the list
		Project project = new Project("project 1",1864,"1234567890");
		comApp.addProject(project);
		
		// add an activity
		Activity activity = new Activity("TestActivity");
		project.addActivity(activity);
		
		// set work period - any will do
		activity.setWorkPeriod(new GregorianCalendar(), new GregorianCalendar());
		
		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		
		// check for no developers on activity and opposite
		assertEquals(0, activity.getDevelopers().size());
		assertEquals(0, dev.getActivities().size());
		
		// assign developer to activity
		activity.addDeveloper(dev);
		
		// check the developers has been added to the activity
		assertEquals(1,activity.getDevelopers().size());
		assertEquals(dev,activity.getDevelopers().get(0));
		
		//check the activity have been added to the developer
		assertEquals(1, dev.getActivities().size());
		assertTrue(dev.getActivities().contains(dev.getActivityByName("TestActivity")));
	
		// test for non-existing activity (B2)
		try {
			new Activity("nonExist").addDeveloper(dev);
			fail("A IllegalInputException should be thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method addDeveloper", e.getMessage());
		}
		
		// test for non-existing developer (B3)
		try {
			activity.addDeveloper(new Developer("martt"));
			fail("a IllegalInputException should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method addDeveloper", e.getMessage());
		}
	}
}
