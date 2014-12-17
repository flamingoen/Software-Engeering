package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * this should test that a developer can ask another developer for help on an
 * activity.
 * <ol>
 * <li>A developer should be able to ask for help on an activity
 * <li>A developer should not be able to ask for help if the developer is
 * working an another project in that period
 * <li>A developer should be able to accept an activity
 * <li>A developer should not be able to accept an request if they work on a
 * project in that period.
 * <ol>
 * 
 * @author martin
 * 
 */
public class TestRequestAssistance {

	/**
	 * test that a developer can send an request to another developer.
	 * 
	 * Data set A
	 * 
	 * @throws Exception
	 */
	@Test //martin
	public void testSendHelpRequest() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// adds developers, a project and an activity
		Developer dev1 = new Developer("dev1");
		Developer dev2 = new Developer("dev2");
		Activity activity1 = new Activity("activity1");
		Activity activity2 = new Activity("activity2");
		Project project = new Project("project", 2014, "1234");
		comApp.addDeveloper(dev1);
		comApp.addDeveloper(dev2);
		comApp.setCurrentUser("dev1");
		project.addActivity(activity1);
		project.addActivity(activity2);
		comApp.addProject(project);
		activity1.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		activity2.setWorkPeriod(new GregorianCalendar(2014, 0, 3),
				new GregorianCalendar(2014, 0, 5));

		// add developer to the activity
		activity1.addDeveloper(dev1);

		assertEquals(0, dev2.getRequest().size());
		assertFalse(activity1.getDevelopers().contains(dev2));

		// ask the other developer to join project
		// and assert that it have been send (A1)
		comApp.requestAssistance(activity1, dev2);
		assertEquals(1, dev2.getRequest().size());
		assertEquals("activity1", dev2.getRequest().peek().getName());
		assertEquals("dev1", dev2.getRequester().peek().getInitials());
		comApp.requestAssistance(activity2, dev2);
		assertEquals(2, dev2.getRequest().size());
		assertTrue(dev2.getRequest().contains(activity2));
		assertTrue(dev2.getRequester().contains(dev1));

		// accept the first request from the developer
		dev2.acceptRequest();
		assertTrue(activity1.getDevelopers().contains(dev2));
		assertEquals(1, dev2.getRequest().size());

		// decline the second request
		assertEquals("activity2", dev2.getRequest().peek().getName());
		assertEquals("dev1", dev2.getRequester().peek().getInitials());
		dev2.DeclineRequest();
		assertFalse(activity2.getDevelopers().contains(dev2));
		assertEquals(0, dev2.getRequest().size());

		// try to add ask for a non existing activity (A2)
		Activity activity3 = new Activity("activity3");
		try {
			comApp.requestAssistance(activity3, dev2);
			fail("A illegalInputExeption should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method requestAssistance",
					e.getMessage());
		}

		// try to add ask for a non existing developer (A3)
		Developer dev3 = new Developer("dev3");
		try {
			comApp.requestAssistance(activity1, dev3);
			fail("A IllegalInputExeption should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method requestAssistance",
					e.getMessage());
		}
	}

	/**
	 * test that a developer can't ask for help if the other developer is
	 * working on an activity in that period. 
	 * 
	 * Test set B
	 * 
	 * @throws Exception
	 */
	@Test //martin
	public void testCantSendRequest() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// adds developers, a project and an activity
		Developer dev1 = new Developer("dev1");
		Developer dev2 = new Developer("dev2");
		comApp.addDeveloper(dev1);
		comApp.addDeveloper(dev2);
		comApp.setCurrentUser("dev1");
		Activity activity1 = new Activity("activity1");
		Activity activity2 = new Activity("activity2");
		Project project = new Project("project", 2014, "1234");
		project.addActivity(activity1);
		project.addActivity(activity2);
		comApp.addProject(project);
		activity1.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		activity2.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));

		// add developer to the activity
		activity1.addDeveloper(dev1);
		activity2.addDeveloper(dev2);

		// test that a developer can't be assigned if already working on another activity (B)
		try {
			comApp.requestAssistance(activity1, dev2);
			fail("A OverBookedException should have been thrown");
		} catch (OverBookedException e) {
			assertEquals(
					"can't ask developer for assistance, developer overbooked",
					e.getMessage());
		}
	}

	/**
	 * test that the program works for multiple requests. The developer should
	 * accept all of them, the last one should fail.
	 * 
	 * @throws Exception
	 */
	@Test //martin
	public void testMultipleRequests() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// adds developers, a project and an activity
		Developer dev = new Developer("dev");
		Developer manager = new Developer("manager");
		Project project = new Project("project", 2014, "1234");
		Activity activity1 = new Activity("activity1");
		Activity activity2 = new Activity("activity2");
		Activity activity3 = new Activity("activity3");
		comApp.addDeveloper(dev);
		comApp.addDeveloper(manager);
		comApp.setCurrentUser("manager");
		comApp.addProject(project);
		project.setManager(manager);
		project.addActivity(activity1);
		project.addActivity(activity2);
		project.addActivity(activity3);
		activity1.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
						new GregorianCalendar(2014, 0, 2));
		activity2.setWorkPeriod(new GregorianCalendar(2014, 0, 3),
						new GregorianCalendar(2014, 0, 5));
		activity3.setWorkPeriod(new GregorianCalendar(2014, 0, 1),
						new GregorianCalendar(2014, 0, 12));

		comApp.requestAssistance(activity1,dev);
		assertEquals(1, dev.getRequest().size());
		comApp.requestAssistance(activity2, dev);
		assertEquals(2, dev.getRequest().size());
		comApp.requestAssistance(activity3, dev);
		assertEquals(3, dev.getRequest().size());

		// accept the two first
		dev.acceptRequest();
		assertTrue(activity1.getDevelopers().contains(dev));
		assertEquals(2, dev.getRequest().size());
		
		dev.acceptRequest();
		assertTrue(activity2.getDevelopers().contains(dev));
		assertEquals(1, dev.getRequest().size());

		// accept the last request - see that it fails
		try {
			dev.acceptRequest();
			fail("A OverBookedException should have been thrown");
		} catch (OverBookedException e) {
			assertEquals(
					"can't accept assistance request, developer overbooked",
					e.getMessage());
		}
	}

	/**
	 * test that there have to be a current user to ask another user for help.
	 * 
	 * @throws Exception
	 */
	@Test //martin
	public void testNotCurrentUser() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// adds developers, a project and an activity
		Developer dev1 = new Developer("dev1");
		Developer dev2 = new Developer("dev");
		Activity activity = new Activity("activity");
		Project project = new Project("project", 2014, "1234");
		project.addActivity(activity);
		comApp.addProject(project);
		comApp.addDeveloper(dev1);
		comApp.addDeveloper(dev2);

		// try to add a request with no current user in the program
		try {
			comApp.requestAssistance(activity, dev2);
			fail("A InsufficientRightsException should have been thrown");
		} catch (InsufficientRightsException e) {
			assertEquals(
					"request assistance not allowed if user is not project manager",
					e.getMessage());
		}
	}

	/**
	 * should test that an outdated request can't be accepted
	 * 
	 * @throws Exception
	 */
	@Test //martin
	public void testOutdatedRequest() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));

		// adds developers, a project and an activity
		Developer dev1 = new Developer("dev1");
		Developer dev2 = new Developer("dev2");
		Activity activity = new Activity("activity1");
		Project project = new Project("project", 2014, "1234");
		comApp.addDeveloper(dev1);
		comApp.addDeveloper(dev2);
		comApp.setCurrentUser("dev1");
		project.addActivity(activity);
		comApp.addProject(project);
		activity.setWorkPeriod(new GregorianCalendar(2014, 1, 1),
				new GregorianCalendar(2014, 10, 1));

		// send request to dev2
		comApp.requestAssistance(activity, dev2);

		// set date to after the work period of the activity
		Calendar newCal = new GregorianCalendar(2014, 11, 1);
		when(dateServer.getDate()).thenReturn(newCal);

		try {
			dev2.acceptRequest();
			fail("A DateExpiredException should have been thrown");
		} catch (DateExpriedException e) {
			assertEquals("date outdated in acceptRequest", e.getMessage());
		}
	}

}
