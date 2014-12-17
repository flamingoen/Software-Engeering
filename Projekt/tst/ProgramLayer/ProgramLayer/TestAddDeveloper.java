package ProgramLayer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


/**
 * should test the functionality of adding a developer to the company. 
 * A developer should be created with his name, but also with some kind of id
 * that is unique for that developer. typically a company would have some sort of id
 * or initials. if not, they will have to have one specially for this application
 * 
 * @author martin
 *
 */
public class TestAddDeveloper{

	
	/**
	 * this should test the functionality of adding a new developer to the company, 
	 * when everything is typed right
	 * @throws Exception 
	 */
	@Test //martin
	public void testAddDeveloperNoInputError() throws Exception{
		CompanyApplication comApp = new CompanyApplication();
		
		List<Developer> developers = comApp.getDevelopers();
		assertEquals(0, developers.size());
		
		// add a developer to the list
		Developer dev = new Developer("frmc");
		comApp.addDeveloper(dev);
		assertEquals(1, developers.size());
		
		// assert that the info is correct
		assertEquals("frmc",dev.getInitials());
		
		// add multiple developers
		comApp.addDeveloper(new Developer("mort"));
		assertEquals(2, developers.size());
		
		// test that you can't add a developer with an existing initial
		try {
			comApp.addDeveloper(new Developer("frmc"));
			fail("A IllegalInputExpection should have been thrown");
		} catch (Exception e) {
			assertEquals("Invalid input in method addDeveloper", e.getMessage());
		}
	}
}
