package com.revature;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.EmplReimbursementDaoImpl;
import com.revature.dao.ManagerReimbursementDaoImpl;
import com.revature.dao.ReimbursmentDaoImpl;
import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;

public class ReimTest {

	private static Logger log = Logger.getLogger(ReimTest.class);
	Timestamp submit = new Timestamp(System.currentTimeMillis());

	@InjectMocks
	private ReimbursementServicesImpl reimserv;

	@Mock
	private ReimbursmentDaoImpl reimdao;

	@Mock
	private EmplReimbursementDaoImpl empldao;
	
	@Mock
	private ManagerReimbursementDaoImpl mandao;

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
		reimserv = new ReimbursementServicesImpl();
		MockitoAnnotations.initMocks(this);
		System.out.println("Set up Mockito\n");		
	}
	
	@Test
	public void pendingPass() {
		log.info("Pending test\n");
		User u = LoginService.authority("mareo1997");
		List <Reimbursement> r = reimserv.pendingHQL(u);
		when(empldao.pendingHQL(u)).thenReturn(r);
		verify(empldao, times(1)).pendingHQL(u);		
	}
	
	@Test
	public void pendingFail() {
		log.info("Pending fail test\n");
		User u = LoginService.authority("mareo199");
		List <Reimbursement> r = reimserv.pendingHQL(u);
		when(empldao.pendingHQL(u)).thenReturn(r);
		verify(empldao, times(1)).pendingHQL(u);		
	}
	
	@Test
	public void resolvedPass() {
		log.info("Resolved test\n");
		User u = LoginService.authority("mareo1997");
		List <Reimbursement> r = reimserv.resolvedHQL(u);
		when(empldao.resolvedHQL(u)).thenReturn(r);
		verify(empldao, times(1)).resolvedHQL(u);		
	}
	
	@Test
	public void resolvedFail() {
		log.info("Resolved fail test\n");
		User u = LoginService.authority("mareo199");
		List <Reimbursement> r = reimserv.resolvedHQL(u);
		when(empldao.resolvedHQL(u)).thenReturn(r);
		verify(empldao, times(1)).resolvedHQL(u);		
	}
	
	@Test
	public void resolve() {
		User u = LoginService.authority("king");
		Reimbursement reim = reimserv.findReimHQL(4);
		Status status = reimserv.statusHQL(3);
		reimserv.resolveHQL(reim, u, status, submit);
	}
	
	//@Ignore
	@Test
	public void submitPass() {
		log.info("Submit test\n");
		
		Reimbursement r = mock(Reimbursement.class);
		when(reimserv.submitHQL(r)).thenReturn(r);
		assertEquals(reimserv.submitHQL(r), r);
		verify(empldao, times(1)).submitHQL(r);
		
		/*User u = LoginService.authority("username");
		Status status = reimserv.statusHQL(3);
		Type type = reimserv.typeHQL(4);
		Reimbursement r = new Reimbursement(u, 1, "description", submit, status, type);
		
		when(reimserv.submitHQL(r)).thenReturn(r);/
		verify(empldao, times(1)).submitHQL(r);*/	
	}
	
	@Test
	public void submitFail() {
		log.info("Submit fail test\n");
		User u = LoginService.authority("usernam");
		Timestamp submit = new Timestamp(System.currentTimeMillis());
		Status status = reimserv.statusHQL(0);
		Type type = reimserv.typeHQL(0);
		Reimbursement r = new Reimbursement(u, 1, "description", submit, status, type);
		
		r = reimserv.submitHQL(r);
		when(empldao.submitHQL(r)).thenThrow(NullPointerException.class);
		verify(empldao, times(0)).submitHQL(r);		
	}
	
	@Test
	public void reimidFail() {
		log.info("Find reim id test\n");
		when(reimserv.findReimHQL(0)).thenThrow(new NullPointerException());
		//verify(reimdao, times(1)).findReimHQL(0);
	}
	
	@Test
	public void reimidPass() {
		log.info("Find reim id fail test\n");
		Reimbursement reim = reimserv.findReimHQL(4);
		when(reimserv.findReimHQL(4)).thenReturn(reim);
		//verify(reimdao, times(1)).findReimHQL(4);
	}
	
	@Test
	public void statusFail() {
		log.info("Find status fail test\n");
		when(reimserv.statusHQL(0)).thenReturn(null);
		verify(reimdao, never()).statusHQL(0);
	}
	
	@Test
	public void statusPass() {
		log.info("Find status test\n");		
		Status s = reimserv.statusHQL(1);
		when(reimserv.statusHQL(1)).thenReturn(s);
		verify(reimdao, times(1)).statusHQL(1);
	}
	
	@Test
	public void test() {
		assertTrue(true);
	}
}
