package ProgramLayer;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Test that the date server is working. Code borrowed from hubert in libApp
 * @author Martin
 *
 */
public class TestDateServer{

	/***
	 * Tests that getDate function of the DateServer returns the current date.
	 */
	@Test //martin
	public void testDateServer() {
		Calendar expected = GregorianCalendar.getInstance();
		Calendar result = new DateServer().getDate();
		assertEquals(expected.get(Calendar.YEAR), result.get(Calendar.YEAR));
		assertEquals(expected.get(Calendar.MONTH), result.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), result
				.get(Calendar.DAY_OF_MONTH));
	}

	/***
	 * Tests that the getDate function of the library application returns the current date.
	 */
	@Test //martin
	public void testLibAppGetDate() {
		CompanyApplication comApp = new CompanyApplication();
		Calendar expected = GregorianCalendar.getInstance();
		Calendar result = comApp.getDate();
		assertEquals(expected.get(Calendar.YEAR), result.get(Calendar.YEAR));
		assertEquals(expected.get(Calendar.MONTH), result.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), result
				.get(Calendar.DAY_OF_MONTH));
	}
}