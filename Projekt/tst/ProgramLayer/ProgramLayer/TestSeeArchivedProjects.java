package ProgramLayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.List;
import java.util.GregorianCalendar;

import org.junit.Test;
/**
 * contains test for the functionality of archived projects being visible
 * to the project manager. also adds the functionality to close an open project.
 * Therefore, it also includes test for closing project.
 * A project should not be closed, if an activity has not been ended in the project.
 * 
 * @author martin
 *
 */
public class TestSeeArchivedProjects {

	/**
	 * contains the test for closing projects with no activities
	 * and visable archived projects for the manager.
	 * @throws Exception 
	 */
	
	@Test //martin
	public void testArchiveProject() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,1,1));
		
		//initialize
		Developer dev = new Developer("frmc");
		Project p = new Project("project 1", 2014, "00001");
		Activity a = new Activity("activity");
		comApp.addProject(p);
		p.addActivity(a);
		p.setManager(dev);
		a.setWorkPeriod(new GregorianCalendar(2014,1,1), new GregorianCalendar(2014,10,10));
		comApp.addDeveloper(dev);
		comApp.addProject(p);
		a.addDeveloper(dev);
		
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2015,11,11));		
		
		//assert that the project is not archived and exists in comApp
		assertFalse(comApp.getArchivedProjects().contains(p));
		//assert that the developer is assigned to the activity in project
		assertTrue(a.getDevelopers().contains(dev));
		
		// archive project
		p.archiveProject();
		
		//assert that the project is archived and does not exists as active project
		assertTrue(comApp.getArchivedProjects().contains(p));
		//assert that the developer is unassigned to the activity in the project
		assertFalse(a.getDevelopers().contains(dev));
	
	}
	
	@Test //martin
	public void testSeeArchivedProjects() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		Developer dev = new Developer("frmc");
		Project p1 = new Project("project 1", 2014, "00001");
		Project p2 = new Project("project 2", 2014, "00002");
		Project p3 = new Project("project 3", 2014, "00003");
		comApp.addDeveloper(dev);
		comApp.addProject(p1);
		comApp.addProject(p2);
		comApp.addProject(p3);
		
		// set him as manager on the three projects
		p1.setManager(dev);
		p2.setManager(dev);
		p3.setManager(dev);
		
		// test that their is no archived projects 
		assertEquals(0,comApp.getArchivedProjects().size());
		
		// close first project
		p1.archiveProject();
		
		// test that their is an archived project
		assertEquals(1,comApp.getArchivedProjects().size());
		
		// close last projects
		p2.archiveProject();
		p3.archiveProject();
		
		// test that their is an archived project
		assertEquals(3,comApp.getArchivedProjects().size());
	}
	
	/**
	 * should test that an project can't be closed if any activity is active or not yet started.
	 * @throws Exception 
	 */
	@Test //martin
	public void testCantCloseProject() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		//set a date server
		DateServer dateServer = mock(DateServer.class);
		comApp.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(new GregorianCalendar(2014,1,1));
		
		Developer dev = new Developer("frmc");
		Project p1 = new Project("project 1", 2014, "00001");
		Project p2 = new Project("project 2", 2014, "00002");
		Project p3 = new Project("project 3", 2014, "00003");
		comApp.addDeveloper(dev);
		comApp.addProject(p1);
		comApp.addProject(p2);
		comApp.addProject(p3);
		Activity act = new Activity("TestActivity");
		
		// set him as manager on the three projects
		p1.setManager(dev);
		p2.setManager(dev);
		p3.setManager(dev);
		
		// set activity work period and add to project
		p3.addActivity(act);
		act.setWorkPeriod(new GregorianCalendar(2014,1,1), new GregorianCalendar(2015,1,2));
		// test that their is no archived projects 
		assertEquals(0,comApp.getArchivedProjects().size());
		
		// close first 2 projects
		p1.archiveProject();
		p2.archiveProject();
		
		// test that their is an archived project
		assertEquals(2,comApp.getArchivedProjects().size());
		
		// close last project - this one shouldn't work
		try {
			p3.archiveProject();
			fail("A IllegalInputExpection should be thrown");
		} catch (IllegalInputExpection e) {
			assertEquals("Invalid input in method archiveProject", e.getMessage());
		}
	}
		
	/**
	 * Test that a developer can get closed project he managed
	 * @throws Exception 
	 */
	@Test //martin
	public void TestSeeOwnArchived() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		Developer dev1 = new Developer("dev1");
		Developer dev2 = new Developer("dev2");
		Project p1 = new Project("project 1", 2014, "00001");
		Project p2 = new Project("project 2", 2014, "00002");
		Project p3 = new Project("project 3", 2014, "00003");
		comApp.addDeveloper(dev1);
		comApp.addProject(p1);
		comApp.addProject(p2);
		comApp.addProject(p3);
		
		//add dev1 as manager in the two first projects
		p1.setManager(dev1);
		p2.setManager(dev1);
		p3.setManager(dev2);
		
		
		//close all three projects
		p1.archiveProject();
		p2.archiveProject();
		p3.archiveProject();
		
		//assert that the developer can see the two first calendars without the last
		java.util.List<Project> archived = dev1.getOwnArchivedProjects();
		assertEquals(2, archived.size());
		assertTrue(archived.contains(p1));
		assertTrue(archived.contains(p2));
		assertFalse(archived.contains(p3));
	}
}
