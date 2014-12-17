package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.mockito.*;

/**
 * should test that an activity can have its workperiod set. 
 * 
 * Plan Project data set C
 */

//daniel
public class TestActivitySetWorkPeriod {

	@Test
	public void testSetWorkPeriod() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,0,0));	
		
		//add a project with an activity
		comApp.addProject(new Project("project", 2014, "1234"));
		comApp.getProjectBySerial("1234").addActivity(new Activity("activity"));
		
		//add a time period to the activity
		GregorianCalendar start = new GregorianCalendar(2014, 1, 1);
		GregorianCalendar end = new GregorianCalendar(2014, 10, 10);
		comApp.getProjectBySerial("1234").getActivityByName("activity").setWorkPeriod(start, end);
		
		//assert that the time period have been added to the activity (C1)
		assertTrue(comApp.getProjectBySerial("1234").getActivityByName("activity").getWorkPeriod().getStartDate().equals(start));
		assertTrue(comApp.getProjectBySerial("1234").getActivityByName("activity").getWorkPeriod().getEndDate().equals(end));
		
		// test that you cant add a end date that is before the start date (C2)
		try {
			comApp.getProjectBySerial("1234").getActivityByName("activity").setWorkPeriod(end,start);
			fail("A DateExpiredException should have been thrown");
		} catch (DateExpriedException e) {
			assertEquals("end date can't be before start date", e.getMessage());
		}
		
		//test that you can't start a project in the past (C3)
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,5,5));
		try {
			comApp.getProjectBySerial("1234").getActivityByName("activity").setWorkPeriod(start,end);
			fail("A DateExpiredException should have been thrown");
		} catch (DateExpriedException e) {
			assertEquals("cant set a start date before today", e.getMessage());
		}
	}
}
