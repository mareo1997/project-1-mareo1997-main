package com.revature.services;

import java.sql.Timestamp;
import java.util.List;

import com.revature.dao.EmplReimbursementDaoImpl;
import com.revature.dao.ManagerReimbursementDaoImpl;
import com.revature.dao.ReimbursmentDaoImpl;
import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;

public class ReimbursementServicesImpl implements ReimbursementServices {

	ReimbursmentDaoImpl reim = new ReimbursmentDaoImpl();
	EmplReimbursementDaoImpl emp = new EmplReimbursementDaoImpl();
	ManagerReimbursementDaoImpl man  = new ManagerReimbursementDaoImpl();

	@Override
	public void insert(Reimbursement e) {
		reim.insert(e);
	}

	@Override
	public void insert(Status s) {
		reim.insert(s);
	}

	@Override
	public void insert(Type t) {
		reim.insert(t);
	}


	@Override
	public List<Reimbursement> pendingHQL(User u) {
		return emp.pendingHQL(u);
	}

	@Override
	public List<Reimbursement> resolvedHQL(User profile) {
		return emp.resolvedHQL(profile);
	}

	@Override
	public Reimbursement submitHQL(Reimbursement reim) {
		return emp.submitHQL(reim);
	}

	@Override
	public Type typeHQL(int t) {
		return reim.typeHQL(t);
	}

	@Override
	public Reimbursement findReimHQL(int reimbursementid) {
		return reim.findReimHQL(reimbursementid);
	}

	@Override
	public Reimbursement resolveHQL(Reimbursement reim, User resolver, Status status, Timestamp resolved) {
		return man.resolveHQL(reim, resolver, status, resolved);
	}

	public Status statusHQL(int s) {
		return reim.statusHQL(s);
	}

	@Override
	public List<Reimbursement> pendingrequestsHQL() {
		return man.pendingrequestsHQL();
	}
	
	@Override
	public List<Reimbursement> resolvedrequestsHQL() {
		return man.resolvedrequestsHQL();
	}

	@Override
	public List<Reimbursement> requestsHQL(User u) {
		return man.requestsHQL(u);
	}
}
	
	/*@Override
	public void submit(User u, Reimbursement r) {
		reim.submit(u, r);
	}

	@Override
	public List<Reimbursement> pending(User u) {
		return reim.pending(u);
	}

	@Override
	public List<Reimbursement> resolved(User u) {
		return reim.resolved(u);
	}

	@Override
	public void resolve(int id, String status, int resolver) {
		reim.resolve(id, status, resolver);
	}

	@Override
	public void requests(int userid) {
		reim.requests(userid);
	}

	@Override
	public void resolvedrequests() {
		reim.resolvedrequests();
	}

	@Override
	public void pendingrequests() {
		reim.pendingrequests();
	}
}*/
