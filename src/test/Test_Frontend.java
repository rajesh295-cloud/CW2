package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.util.TestUtils;

import backend.AdminOperations;
import frontend.Register;
import model.Registration;

public class Test_Frontend {
	Registration reg;
	@Before
	public void setUp() {  //Creating required object before starting test
		reg = new Registration("Test", "User", "For","SetterGetter","Testing");
		
	}
	
	@Test
	public void test_getFname() { //Testing on setter and gettor method
		reg.setFname("Test"); //An string is set on setter method
		String actual = reg.getFname(); //gets the setted text from getter method
		assertEquals("Test", actual); //Validates that our expected data matches with returned data
	}
	
	@Test
	public void test_getLname() {
		reg.setLname("User"); //An string is set on setter method
		String actual = reg.getLname(); //gets the setted text from getter method
		assertEquals("User", actual); //Validates that our expected data matches with returned data
	}
	
	@Test
	public void test_getUname() {
		reg.setUname("For"); //An string is set on setter method
		String actual = reg.getUname(); //gets the setted text from getter method
		assertEquals("For", actual);//Validates that our expected data matches with returned data
	}
}
