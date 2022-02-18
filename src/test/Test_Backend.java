package test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import backend.AdminOperations;
import frontend.Register;

public class Test_Backend {
	/*
	 * This class is used to perform testing on backend package
	 * 
	 */
	AdminOperations obj;
	@Before 
	public void setUp() { //Creating required object before starting test
		obj = new AdminOperations();
		
	}
	@Test
	public void test_getDoctors() { //Get doctors method is tested 
		ResultSet actual = obj.getDoctors(); // getDoctors method is accesed from the method
		assertNotNull(actual); // Assert that the resultset is not null and verify its returning data
	}
	
	@Test
	public void test_getNurses() {
		ResultSet actual = obj.getNurses();// getNurses method is accesed from the method
		assertNotNull(actual);// Assert that the resultset is not null and verify its returning data
	}
	
	@Test
	public void test_getPatients() {
		ResultSet actual = obj.getPatients();// getPatients method is accesed from the method
		assertNotNull(actual);// Assert that the resultset is not null and verify its returning data
	}
	
	@Test
	public void test_register() { // Metod to register user is tested
		boolean actual = obj.register("Chirag", "Simkhada", "chirag101", "Doctor", "Abcd"); //An object for the method is created with all required arguments
		assertTrue(actual); //Assert that it's returning true i.e. data is inserted successfully
	}
	
	@Test
	public void test_addBed() {
		boolean actual = obj.addBed(1,"Test");//An object for the method is created with all required arguments
		assertTrue(actual); //Assert that it's returning true i.e. data is inserted successfully
	}
	

}
