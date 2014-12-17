package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * contains test for registering vacations in the application. a developer
 * should be able to register his vacation into the program. The developer
 * should not be able to register vacation, if he is working on an activity in
 * that period. The developer should not be able to be assigned to an activity
 * if he is on vacation.
 * 
 * @author martin
 * 
 */
public class TestRegisterVacation {

	/**
	 * tests that an employee can book his vacation into the system, so that he
	 * is unavailable in that period.
	 * 
	 * Test set A
	 * 
	 * @throws Exception
	 */
	@Test
	// martin
	public void testRegisterVacation() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate())
				.thenReturn(new GregorianCalendar(2014, 0, 0));

		// create an interval for the vacation and an developer
		Developer dev = new Developer("frmc");
		Interval interval = new Interval(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		Interval wrongInterval = new Interval(
				new GregorianCalendar(2014, 0, 5), new GregorianCalendar(2014,
						0, 2));

		// assert that you can't register vacation if start date is before end
		// date (A2)
		try {
			dev.registerVacation(wrongInterval);
			fail("A DateExpriedExepction should be thrown");
		} catch (DateExpriedException e) {
			assertEquals("Start date was before end date", e.getMessage());
		}

		// test that the developer is available in the interval
		assertTrue(dev.isAvailable(interval));

		// register vacation
		dev.registerVacation(interval);

		// test that the developer is unavailable after the vacation have been
		// registered (A1)
		assertFalse(dev.isAvailable(interval));
	}

	/**
	 * Test that it is possible to add one vacation day
	 * 
	 * Data set B
	 * 
	 * @throws Exception
	 */
	@Test
	// martin
	public void testOneDay() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate())
				.thenReturn(new GregorianCalendar(2014, 0, 0));

		// create an interval for the vacation and an developer
		Developer dev = new Developer("frmc");
		Interval interval = new Interval(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 0));

		// test that the developer is available in the interval
		assertTrue(dev.isAvailable(interval));

		// register vacation
		dev.registerVacation(interval);

		// test that the developer is unavailable after the vacation have been
		// added (B)
		assertFalse(dev.isAvailable(interval));
	}

	/**
	 * test that you can register vacation alongside other activities that is
	 * not overlapping
	 * 
	 * data set C
	 * 
	 * @throws Exception
	 */
	@Test
	// martin
	public void testVacationAlongActivity() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate())
				.thenReturn(new GregorianCalendar(2014, 0, 0));

		// create an interval for the vacation and an developer
		Developer dev = new Developer("frmc");
		Interval interval1 = new Interval(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 10));
		Interval interval2 = new Interval(new GregorianCalendar(2014, 0, 11),
				new GregorianCalendar(2014, 0, 20));

		// add an activity and add developer to that activity with interval 1
		Activity activity = new Activity("activity");
		Project project = new Project("test", 2014, "01");
		comApp.addProject(project);
		comApp.addDeveloper(dev);
		project.addActivity(activity);
		activity.setWorkPeriod(interval1.getStartDate(), interval1.getEndDate());
		activity.addDeveloper(dev);

		// set vacation in interval two and assert that he is unavailable in
		// both interval
		dev.registerVacation(interval2);
		assertFalse(dev.isAvailable(interval1));
		assertFalse(dev.isAvailable(interval2));
	}

	/**
	 * an developer should not be able to register his vacation, if he is
	 * already assigned to another activity.
	 * 
	 * Test set D
	 * 
	 * @throws Exception
	 */
	@Test
	// martin
	public void testRegisterVacationUserBooked() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate())
				.thenReturn(new GregorianCalendar(2014, 0, 0));

		// create an inteval for the vacation and an developer
		Developer dev = new Developer("frmc");
		Project project = new Project("test project", 2014, "1234567890");
		Activity activity = new Activity("test activity");
		Interval interval = new Interval(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		comApp.addDeveloper(dev);
		comApp.addProject(project);
		project.addActivity(activity);

		// test that the developer is available in the interval
		assertTrue(dev.isAvailable(interval));

		// set the developer to the activity in the interval
		activity.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		activity.addDeveloper(dev);

		// test that the developer is unavailable in the interval
		assertFalse(dev.isAvailable(interval));

		// test that the developer can't register vacation in this interval
		try {
			dev.registerVacation(interval);
			fail("A OverBookedException should have been thrown");
		} catch (OverBookedException e) {
			// Step 4
			assertEquals("can't register vacation, developer overbooked",
					e.getMessage());
		}
	}

	/**
	 * contains the test, that a developer can't be added to an activity in his
	 * vacation period.
	 * 
	 * Test set E
	 * 
	 * @throws Exception
	 */
	@Test
	// martin
	public void testRegisterActivityIfOnVacation() throws Exception {
		CompanyApplication comApp = new CompanyApplication();

		// set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate())
				.thenReturn(new GregorianCalendar(2014, 0, 0));

		// create an inteval for the vacation and an developer
		Developer dev = new Developer("frmc");
		Project project = new Project("test project", 2014, "1234567890");
		Activity activity = new Activity("test activity");
		Interval interval = new Interval(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		comApp.addDeveloper(dev);
		comApp.addProject(project);
		project.addActivity(activity);

		// test that the developer is available in the interval
		assertTrue(dev.isAvailable(interval));

		// set work period for the vacation and register vacation in same period
		activity.setWorkPeriod(new GregorianCalendar(2014, 0, 0),
				new GregorianCalendar(2014, 0, 2));
		dev.registerVacation(interval);

		// test that the developer can't be added to the activity.
		try {
			activity.addDeveloper(dev);
			fail("A OverBookedException should have been thrown");
		} catch (OverBookedException e) {
			// Step 4
			assertEquals(
					"can't add developer to activity, developer overbooked",
					e.getMessage());
		}
	}
}