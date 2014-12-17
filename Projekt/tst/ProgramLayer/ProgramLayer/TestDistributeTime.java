package ProgramLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * test for adding time to an activity. A developer must be the manager of the
 * project with the activity to register time.
 * 
 * @author martin
 * 
 */
public class TestDistributeTime {

	@Test //martin
	public void testBudgetTimeToActivity() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// add a project to the list
		Project project = new Project("project 1", 1864, "1234567890");
		comApp.addProject(project);

		// add an activity
		Activity activity = new Activity("TestActivity");

		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);

		// set as current user and manager
		comApp.setCurrentUser("frmc");
		project.setManager(dev);

		// add activity to project
		project.addActivity(activity);

		// add time to activity
		project.addBudgetTimeToActivity(activity, 1);

		// assert time in activity to be 1
		assertEquals(1, activity.getScheduledTime());
	}

	/**
	 * test that a developer can't budget time to an activity if not project manager
	 */
	@Test //martin
	public void testBudgetTimeToActivityNotManager() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// add a project to the list
		Project project = new Project("project 1", 1864, "1234567890");
		comApp.addProject(project);

		// add an activity
		Activity activity = new Activity("TestActivity");

		// add developers
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		comApp.setCurrentUser("frmc");

		// add activity to project
		project.addActivity(activity);

		// budget time
		try {
			project.addBudgetTimeToActivity(activity, 1);
			fail("A InsufficientRightsException should have been thrown");
		} catch (InsufficientRightsException e) {
			// Step 4
			assertEquals(
					"add time to activity not allowed if user is not project manager",
					e.getMessage());
		}

	}

	/**
	 * test that a developer can add time spent on an activity.
	 * Test set A
	 * @throws Exception
	 */
	@Test //martin
	public void testUserAddTimeToActivity() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// add a project to the list
		Project project = new Project("project 1", 1864, "1234567890");
		comApp.addProject(project);

		// add an activity
		Activity activity = new Activity("TestActivity");
		project.addActivity(activity);
		activity.setWorkPeriod(new GregorianCalendar(), new GregorianCalendar());

		// add developers
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		comApp.setCurrentUser("frmc");
		activity.addDeveloper(dev);

		assertTrue(activity.GetUserTimeSpend(dev).equals(0.));
		assertTrue(activity.GetTotalTimeSpend().equals(0.));

		
		// Test for positive hours (A1)
		project.addTimeToActivity(activity, 4);

		// test if time have been added
		assertTrue(activity.GetUserTimeSpend(dev).equals(4.));
		assertTrue(activity.GetTotalTimeSpend().equals(4.));
		
		// test for negativ hours (A2)
		try {
			project.addTimeToActivity(activity, -4);
			fail("A IlligalInputExpection should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method addTimeToActivity", e.getMessage());
		}
		
		// test for floats (not halves) (A3)
		try {
			project.addTimeToActivity(activity, 4.3);
			fail("A IlligalInputExpection should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method addTimeToActivity", e.getMessage());
		}
	}
	
	/**
	 * Test that a user can add time to an activity multiple times, and also add half hours.
	 * Test set B
	 * @throws Exception 
	 */
	@Test //martin
	public void testAddMutipleHoursToActivity() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// add a project to the list
		Project project = new Project("project 1", 1864, "1234567890");
		comApp.addProject(project);

		// add an activity
		Activity activity = new Activity("TestActivity");
		project.addActivity(activity);
		activity.setWorkPeriod(new GregorianCalendar(), new GregorianCalendar());

		// add developers
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		comApp.setCurrentUser("frmc");
		activity.addDeveloper(dev);
		
		// test for adding time multiple times (B1)
		activity.addTimeSpend(4);
		activity.addTimeSpend(2);
		assertTrue(activity.GetUserTimeSpend(dev).equals(6.));
		
		// Test for halves hours (B2)
		activity.addTimeSpend(1.5);
		assertTrue(activity.GetUserTimeSpend(dev).equals(7.5));
	}
	

	/**
	 * test that mulitple users can add time to an activity
	 * @throws Exception
	 */
	@Test //martin
	public void testUserAddTimeToActivityMultipleUsers() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// add a project to the list
		Project project = new Project("project 1", 1864, "1234567890");
		comApp.addProject(project);

		// add an activity
		Activity activity = new Activity("TestActivity");
		project.addActivity(activity);
		activity.setWorkPeriod(new GregorianCalendar(2014,1,1), new GregorianCalendar(2014,5,5));

		// add developers
		Developer dev1 = new Developer("frmc");
		Developer dev2 = new Developer("martt");
		Developer dev3 = new Developer("jcwh");
		comApp.addDeveloper(dev1);
		comApp.addDeveloper(dev2);
		comApp.addDeveloper(dev3);
		activity.addDeveloper(dev1);
		activity.addDeveloper(dev2);
		activity.addDeveloper(dev3);

		assertTrue(activity.GetUserTimeSpend(dev1).equals(0.));
		assertTrue(activity.GetUserTimeSpend(dev2).equals(0.));
		assertTrue(activity.GetUserTimeSpend(dev3).equals(0.));
		assertTrue(activity.GetTotalTimeSpend().equals(0.));

		// set current user
		comApp.setCurrentUser("frmc");

		// add time developer spend on activity
		project.addTimeToActivity(activity, 1);

		// test if time have been added
		assertTrue(activity.GetUserTimeSpend(dev1).equals(1.));
		assertTrue(activity.GetTotalTimeSpend().equals(1.));

		// set current user
		comApp.setCurrentUser("martt");

		// add time developer spend on activity
		project.addTimeToActivity(activity, 2);

		// test if time have been added
		assertTrue(activity.GetUserTimeSpend(dev2).equals(2.));
		assertTrue(activity.GetTotalTimeSpend().equals(3.));

		// set current user
		comApp.setCurrentUser("jcwh");

		// add time developer spend on activity
		project.addTimeToActivity(activity, 3);

		// test if time have been added
		assertTrue(activity.GetUserTimeSpend(dev3).equals(3.));
		assertTrue(activity.GetTotalTimeSpend().equals(6.));
	}
}
