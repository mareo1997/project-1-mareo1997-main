package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.revature.services.LoginService;

public class LoginTest {

	private static Logger log = Logger.getLogger(LoginTest.class);

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		assertTrue(true);

	}

	@Test
	public void loginPass() {
		log.info("Login test \n");

		assertEquals("mareo1997", LoginService.confirm("mareo1997", "password").getUsername());
		assertEquals("mareo1997", LoginService.authority("mareo1997").getUsername());
	}

	@Test
	public void loginFail() {
		log.info("Login fail test \n");
		assertEquals(null, LoginService.confirm("usernam", "password"));
		assertEquals(null, LoginService.authority("usernam"));
	}

}
