package test;

import static org.junit.Assert.*;

import org.junit.Test;

import date.DateTime;

import user.Customer;
import user.User;

public class CustomerTest {
	User u = new Customer("First", "Last", new DateTime(1993, 12, 01), 456788888, "user", "pass");
	
	@Test
	public void login() {
		assertTrue(u.authenticate("user", "pass"));
	}
	
	@Test
	public void wrongLogin() {
		assertFalse(u.authenticate("user", "password"));
	}
	
	@Test
	public void age() {
		assertEquals(u.getAge(), 19);
	}
	
	
}
