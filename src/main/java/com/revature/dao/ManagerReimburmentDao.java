package com.revature.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.User;

public interface ManagerReimburmentDao {

	public Reimbursement resolveHQL(Reimbursement reim, User resolver, Status status, Timestamp resolved);

	public List<Reimbursement> resolvedrequestsHQL();

	public List<Reimbursement> pendingrequestsHQL();

	public List<Reimbursement> requestsHQL(User u);
}
