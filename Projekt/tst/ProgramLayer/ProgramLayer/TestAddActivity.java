package ProgramLayer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


/**
 * Plan project Use Case data set A
 * @author daniel
 *
 */

public class TestAddActivity {
	/**
	 * add activity (A1)
	 */	
	
	@Test //daniel
	public void testAddActivity() throws Exception {		
		CompanyApplication comApp = new CompanyApplication();
				
		// add a project to the list
		Project project = new Project("project 1",1864,"1234567890");
		comApp.addProject(project);
		
		// add an activity
		Activity activity = new Activity("TestActivity");
		
		// Assert empty
		List<Activity> activities = project.getActivities();
		assertEquals(0, activities.size());
		assertFalse(project.hasActiveActivity());
		
		// Add activity to project
		project.addActivity(activity);
		
		// Assert activity exists
		assertTrue(project.hasActivity(activity));
		assertEquals(1, activities.size());
		assertEquals(activity,project.getActivities().get(0));
		}

	/**
	 * Test that you can't add an activity with the same name as another activity
	 * 
	 */
	@Test //martin
	public void testAddActivityNameExists() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		// adding a project
		Project project = new Project("project 1",1864,"1234567890");
		comApp.addProject(project);
		
		// adding a activity
		Activity activity1 = new Activity("activity");
		project.addActivity(activity1);
		
		//try to add a new activity with the same name as before (A2)
		try {
			Activity activity2 = new Activity("activity");
			project.addActivity(activity2);
			fail("A IllegalInputExpection should have been thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method setActivity", e.getMessage());
		}
	}

}
