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

import com.revature.dao.IApplicationDAO;
import com.revature.models.Application;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ApplicationService;

public class ApplicationTest {

	@Mock
	private IApplicationDAO mockedAppDAO;
	
	private ApplicationService appInstance = new ApplicationService(mockedAppDAO);
	
	private Application app;
	private Application app2;
	private User jayson;
	private User cookie;
	private List<Application> applications;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		appInstance = new ApplicationService(mockedAppDAO);
		
		jayson = new User(1, "jlutrario", "password", "Jayson", "Lutrario", Role.Admin);
		cookie = new User(2, "cmonster", "password", "Cookie", "Moster", Role.Customer);
		app = new Application(1, jayson, true);
		app2 = new Application(0, jayson, true);
		applications = new ArrayList<>();
		applications.add(app);
		
		when(mockedAppDAO.insert(app)).thenReturn(1);
		when(mockedAppDAO.insert(app2)).thenReturn(1);
		when(mockedAppDAO.findAll()).thenReturn(applications);
		when(mockedAppDAO.delete(1)).thenReturn(true);
		when(mockedAppDAO.update(app)).thenReturn(true);
		when(mockedAppDAO.findAll()).thenReturn(applications);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindApplicationSuccess() {
		assertEquals(appInstance.findApplication(jayson), app);
	}
	
	@Test
	public void testFindApplicationFailure() {
		assertEquals(appInstance.findApplication(cookie), null);
	}

	@Test
	public void testApproveApplication() {
		assertTrue(appInstance.approveApplication(app));
	}
	
	@Test
	public void testDenyApplication() {
		assertTrue(appInstance.denyApplication(app));
	}
	
	@Test
	public void testViewActiveApps() {
		assertEquals(appInstance.viewActiveApplications(), applications);
	}
	
	@Test
	public void testApply() {
		assertEquals(appInstance.apply(jayson), app);
	}
	
}
