package com.revature.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.util.HibernateUtil;

public class EmplReimbursementDaoImpl implements EmplReimbursementDao {// Applied log

	private static Logger log = Logger.getLogger(EmplReimbursementDaoImpl.class);

	@Override
	public Reimbursement submitHQL(Reimbursement r) {
		try {
			log.info("Attempting to submit " + r + "\n");
			Session ses = HibernateUtil.getSession(); // capture the session

			Transaction tx = ses.beginTransaction();
			ses.save(r);
			tx.commit(); // commit the transaction by utilizing the methods from the Transaction
			return r;
		} catch (IllegalStateException e) {
			log.warn(e + "\n");
			return null;
		}
	}

	@Override
	public List<Reimbursement> pendingHQL(User u) { // - An Employee can view their pending reimbursement requests ****
		log.info("Attempting to get pending reim for " + u.getUsername() + "\n");
		Session ses = HibernateUtil.getSession();

		Query q = ses.createQuery("FROM Reimbursement where authorfk = :authorfk and status_statusid = 1");
		q.setParameter("authorfk", u.getUserid());
		@SuppressWarnings("unchecked")
		List<Reimbursement> reim = q.getResultList();

		if (reim.size() > 0) {
			log.info("Returning pending requests\n");
			return reim;
		} else {
			log.warn(u.getUsername() + " has no pending requests\n");
			return null;
		}

	}

	@Override
	public List<Reimbursement> resolvedHQL(User u) { // - An Employee can view their pending reimbursement requests ****

		log.info("Attempting to get resolved requests for " + u.getUsername() + "\n");
		Session ses = HibernateUtil.getSession();
		
		Query q = ses.createQuery("FROM Reimbursement where authorfk = :authorfk and not status_statusid = 1");
		q.setParameter("authorfk", u.getUserid());
		@SuppressWarnings("unchecked")
		List<Reimbursement> reim = q.getResultList();

		if (reim.size() > 0) {
			log.info("Returning resolved requests\n");
			return reim;
		} else {
			log.warn(u.getUsername() + " has no resolved requests\n");
			return null;
		}
	}

}
