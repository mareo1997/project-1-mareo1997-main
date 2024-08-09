package com.revature.dao;

import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;

public interface ReimburmentDao {

	/*public void submit(User u, Reimbursement r);

	public List<Reimbursement> pending(User u);

	public List<Reimbursement> resolved(User u);

	public void resolve(int id, String status, int resolver);

	public void requests(int userid);

	public void resolvedrequests();

	public void pendingrequests();*/

	public void insert(Reimbursement e);

	public void insert(Status s);

	public void insert(Type t);

	public Reimbursement findReimHQL(int reimbursementid);

	public Type typeHQL(int t);

	public Status statusHQL(int s);

}
