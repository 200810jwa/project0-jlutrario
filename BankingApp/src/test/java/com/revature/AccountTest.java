package com.revature;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AccountService;

public class AccountTest {
	
	@Mock
	private IAccountDAO mockedAccDAO;
	
	private AccountService accInstance = new AccountService(mockedAccDAO);
	private User jayson;
	private User cookie;
	private Account account;
	private Account other_account;
	private Account new_account;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accInstance = new AccountService(mockedAccDAO);
		
		jayson = new User(1, "jlutrario", "password", "Jayson", "Lutrario", Role.Admin);
		cookie = new User(2, "cmonster", "password", "Cookie", "Moster", Role.Customer);
		account = new Account(1, 100, jayson);
		other_account = new Account(2, 0, cookie);
		new_account = new Account(0, 0, cookie);
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		
		when(mockedAccDAO.findById(1)).thenReturn(account);
		when(mockedAccDAO.findById(2)).thenReturn(other_account);
		when(mockedAccDAO.update(account)).thenReturn(true);
		when(mockedAccDAO.insert(account)).thenReturn(1);
		when(mockedAccDAO.insert(new_account)).thenReturn(2);
		when(mockedAccDAO.delete(new_account.getId())).thenReturn(true);
		when(mockedAccDAO.findAll()).thenReturn(accounts);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWithdrawSuccessful() {
		assertTrue(accInstance.withdraw(account, 20));
	}
	
	@Test
	public void testWithdrawFailure() {
		assertFalse(accInstance.withdraw(account, -20));
	}
	
	@Test
	public void testDepositSuccessful() {
		assertTrue(accInstance.deposit(account, 20));
	}
	
	@Test
	public void testDepositFailure() {
		assertFalse(accInstance.deposit(account, -20));
	}
	
	@Test
	public void testTransferSuccessful() {
		assertTrue(accInstance.transfer(account, new_account, 20));
	}
	
	@Test
	public void testTransferInsufficientFundsFailure() {
		assertFalse(accInstance.transfer(new_account, account, 20));
	}
	
	@Test
	public void testTransferNegativeAmountFailure() {
		assertFalse(accInstance.transfer(account, new_account, -20));
	}
	
	@Test
	public void testOpenAccount() {
		assertEquals(accInstance.openAccount(cookie), other_account);
	}
	
	@Test
	public void testCloseAccountSuccess() {
		assertTrue(accInstance.closeAccount(new_account));
	}
	
	@Test
	public void testCloseAccountFailure() {
		assertFalse(accInstance.closeAccount(account));
	}
	
	@Test
	public void testFindAccount() {
		assertEquals(accInstance.findAccount(jayson), account);
	}

}
