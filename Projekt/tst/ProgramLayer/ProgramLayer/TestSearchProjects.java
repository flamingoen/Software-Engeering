package ProgramLayer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test that a developer can see all the projects he is working on
 * 
 * @author martin
 * 
 */
public class TestSearchProjects {

	/**
	 * test that a developer can see all the projects he is currently working
	 * on. is not working on anything, it should return an empty list
	 * @throws Exception 
	 */
	@Test //martin
	public void testSearchProjects() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		Developer dev = new Developer("frmc");
		Project project1 = new Project("project1", 2014, "101");
		Project project2 = new Project("project2", 2014, "102");
		Project project3 = new Project("project3", 2014, "103");
		comApp.addProject(project1);
		comApp.addProject(project2);
		comApp.addProject(project3);

		comApp.addDeveloper(dev);
		
		//assert that he has no current projects
		assertTrue(dev.getCurrentProjects().isEmpty());
		
		//add a project
		project1.setManager(dev);
		assertEquals(1, dev.getCurrentProjects().size());
		assertEquals("project1", dev.getCurrentProjects().get(0).getName());

		project2.setManager(dev);
		assertEquals(2, dev.getCurrentProjects().size());
		project3.setManager(dev);
		assertEquals(3, dev.getCurrentProjects().size());
		
		project3.archiveProject();
		assertEquals(2, dev.getCurrentProjects().size());
	}
}
