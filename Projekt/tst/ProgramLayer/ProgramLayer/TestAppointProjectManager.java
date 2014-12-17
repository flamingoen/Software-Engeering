package ProgramLayer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Daniel
 *
 */

public class TestAppointProjectManager {

	@Test //daniel
	public void testAppointManagerStandard() throws Exception {
		CompanyApplication comApp = new CompanyApplication();
		
		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
				
		// add a project to the list
		Project project = new Project("project 1",1864,"1234567890");
		comApp.addProject(project);
		
		//assert that the manager is not manager before:
		assertFalse(dev.getCurrentProjects().contains(project));
			
		// Appoint project manager
		project.setManager(dev);
		assertTrue(dev.getCurrentProjects().contains(project));
		assertEquals(dev,project.getManager());
		
	}
}
