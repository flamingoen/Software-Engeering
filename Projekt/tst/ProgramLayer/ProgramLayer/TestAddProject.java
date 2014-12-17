package ProgramLayer;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;


/**
 * should test the functionality of adding a new project to the application.
 * The project is defined by a name, the year it was made and a serial number
 * 
 * @author martin
 *
 */


public class TestAddProject {
	
	@Test //martin
	public void testAddProject(){
		CompanyApplication comApp = new CompanyApplication();
		
		List<Project> projects = comApp.getProjects();
		assertEquals(0, projects.size());
		
		// add a project to the list
		Project project = new Project("project 1",1864,"1234");
		comApp.addProject(project);
		assertEquals(1, projects.size());
		assertTrue(comApp.getProjectBySerial("1234").equals(project));
		
		// assert that the info is correct
		assertEquals("project 1",project.getName());
		assertEquals(1864,project.getyear());
		assertTrue(project.getserialNumber().equals("1234"));	
	}
}
