package ProgramLayer;

import static org.junit.Assert.assertEquals;

import org.junit.*;

/**
 * 
 * @author daniel
 *
 */
public class TestLoggedIn {
	CompanyApplication comApp;
	@Before //daniel
	public void initialize() throws Exception{
		comApp = new CompanyApplication();
		
		// add developers to the list
		for(int i=0; i<10;i++){
			Developer dev = new Developer("dev"+i);
			comApp.addDeveloper(dev);
		}
	}
	@Test //daniel
	public void TestLoggedIn(){
	// check that the developer we would like to set is added to the comApp
		assertEquals("dev2", comApp.getDevelopers().get(2).getInitials());
		
	// Choose user
		comApp.setCurrentUser("dev2");
		
	// Check for correct user
		assertEquals("dev2", comApp.getCurrentUser().getInitials());
	}
}
