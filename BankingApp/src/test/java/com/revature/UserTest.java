package com.revature;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.IAccountDAO;
import com.revature.dao.IUserDAO;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class UserTest {

	@Mock
	private IUserDAO mockedDAO;
	
	@Mock
	private IAccountDAO mockedAccDAO;
	
	private UserService testInstance = new UserService(mockedDAO);
	private AccountService accInstance = new AccountService(mockedAccDAO);
	private User jayson;
	private User new_jayson;
	private User cookie;
	private Account account;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testInstance = new UserService(mockedDAO);
		accInstance = new AccountService(mockedAccDAO);
		
		jayson = new User(1, "jlutrario", "password", "Jayson", "Lutrario", Role.Admin);
		new_jayson = new User(0, "jlutrario", "password", "Jayson", "Lutrario", Role.Admin);
		cookie = new User(2, "cmonster", "password", "Cookie", "Moster", Role.Customer);
		account = new Account(0, 0, cookie);
		
		when(mockedDAO.findByUsername("jlutrario")).thenReturn(jayson);
//		when(mockedDAO.findByUsername(anyString())).thenReturn(null);
		when(mockedDAO.findByID(1)).thenReturn(jayson);
//		when(mockedDAO.findByID(anyInt())).thenReturn(null);
		when(mockedDAO.insert(jayson)).thenReturn(1);
		when(mockedDAO.insert(new_jayson)).thenReturn(1);
		when(mockedDAO.update(jayson)).thenReturn(true);
		when(mockedDAO.delete(1)).thenReturn(true);
		when(mockedAccDAO.insert(account)).thenReturn(2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoginSuccessful() {
		assertEquals(testInstance.login("jlutrario", "password"), jayson);
//		verify(mockedDAO, times(1)).findByUsername("jlutrario");
	}
	
	@Test
	public void testLoginFailure() {
		assertEquals(testInstance.login("jlutrario", "wrongpw"), null);
	}
	
	@Test
	public void testRegisterSuccessful() {
		assertEquals(testInstance.register("jlutrario", "password", "Jayson", "Lutrario", Role.Admin), jayson);
	}
	
	public void testRegisterFailure() {
		testInstance.register("jlutrario", "password", "Jayson", "Lutrario", Role.Admin);
		assertEquals(testInstance.register("jlutrario", "password", "Jayson", "Lutrario", Role.Admin), 0);
	}
	
	@Test
	public void testChangePasswordSuccessful() {
		assertTrue(testInstance.changePassword(1, "otherpw"));
	}
	
	@Test
	public void testChangeRoleSuccessful() {
		testInstance.changeRole(1, Role.Employee);
		assertEquals(jayson.getRole(), Role.Employee);
	}
	
	@Test
	public void testDeleteUserSuccessful() {
		assertTrue(testInstance.deleteUser(jayson));
	}
	
	@Test
	public void testDeleteUserFailure() {
		accInstance.openAccount(cookie);
		assertFalse(testInstance.deleteUser(cookie));
	}
	
}
