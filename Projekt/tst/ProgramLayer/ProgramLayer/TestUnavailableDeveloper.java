package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Test;

//daniel
public class TestUnavailableDeveloper {
//TODO: set date servers
	@Test
	public void testAssignBusyDeveloperToActivity() throws Exception {
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
		
		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		
		// check that you have to give a workperiod to set unavailable time
		try {
		 dev.setUnavailableTime(null,"test period");
		 fail("A nullPointerException should have been thrown");
		} catch (NullPointerException e) {
			assertEquals("no workperiod given", e.getMessage());;
		}
		
		// set work period for activity
		activity.setWorkPeriod(new GregorianCalendar(), new GregorianCalendar(2015,11,5,23,2));

		// set work period for developer
		dev.setUnavailableTime(new Interval(new GregorianCalendar(2014,9,9,9,9),new GregorianCalendar(2014,9,9,9,9)),"test period");
		
		// check for no developers on activity
		assertEquals(0, activity.getDevelopers().size());
		
		// check that an developer can't be assigned more than one activity at the time
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
	@Test
	public void testAssignNotBusyDeveloperToActivity() throws Exception {
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
		
		// set work period for activity
		activity.setWorkPeriod(new GregorianCalendar(), new GregorianCalendar(2015,11,5,23,2));
		
		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);

		// set work period for developer
		dev.setUnavailableTime(new Interval(new GregorianCalendar(2016,12,9,9,9),new GregorianCalendar(2016,12,9,9,9)), activity.getName());
		
		// check for no developers on activity
		assertEquals(0, activity.getDevelopers().size());
		
		// assign developer to activity
		activity.addDeveloper(dev);
		
		// check the developers has not been added to the activity
		assertEquals(1,activity.getDevelopers().size());
	
	}
	
	/**
	 * test the interval object for overlapping intervals with different start and end time.
	 * 
	 * @author Martin
	 */
	@Test
	public void testOverlappingIntervals(){
		//two intervals in length of each other
		Interval interval1 = new Interval(new GregorianCalendar(2014,0,5), new GregorianCalendar(2014,0,10));
		Interval interval2 = new Interval(new GregorianCalendar(2014,0,11), new GregorianCalendar(2014,0,20));
		//interval that goes into interval 1
		Interval interval3 = new Interval(new GregorianCalendar(2014,0,0), new GregorianCalendar(2014,0,7));
		//interval that goes out of interval 2
		Interval interval4 = new Interval(new GregorianCalendar(2014,0,15), new GregorianCalendar(2014,0,22));
		// interval that contains both intervals
		Interval interval5 = new Interval(new GregorianCalendar(2014,0,0), new GregorianCalendar(2014,0,22));
		// interval that is the same period as 1
		Interval interval6 = new Interval(new GregorianCalendar(2014,0,5), new GregorianCalendar(2014,0,10));
		
		//1 and 2 should not be in same interval
		assertFalse(interval1.inInterval(interval2));
		//1 and 3 should be in the same interval
		assertTrue(interval1.inInterval(interval3));
		//2 and 4 should be in the same interval
		assertTrue(interval2.inInterval(interval4));
		// 5 should be in same interval as both 1 and 2
		assertTrue(interval5.inInterval(interval1));
		assertTrue(interval5.inInterval(interval2));
		// should also work in opposite direction
		assertTrue(interval1.inInterval(interval5));
		assertTrue(interval2.inInterval(interval5));
		// 1 and 6 should be in same interval
		assertTrue(interval1.inInterval(interval6));
		assertTrue(interval6.inInterval(interval1));
	}
}
