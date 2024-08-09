package com.revature.dao;

import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.User;

public interface EmplReimbursementDao {

	public Reimbursement submitHQL(Reimbursement reim);

	public List<Reimbursement> pendingHQL(User profile);

	public List<Reimbursement> resolvedHQL(User profile);

}
