package com.revature.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.User;
import com.revature.util.HibernateUtil;

public class ManagerReimbursementDaoImpl implements ManagerReimburmentDao {

	private static Logger log = Logger.getLogger(ManagerReimbursementDaoImpl.class);

	@Override
	public Reimbursement resolveHQL(Reimbursement reim, User resolver, Status status, Timestamp resolved) {
		try {
			log.info("Attempting to resolve a request\n");

			Session ses = HibernateUtil.getSession();
			Transaction tx = ses.beginTransaction(); // perform an operation on DB
			ses.evict(reim);

			reim.setResolve(resolver);
			reim.setResolved(resolved);
			reim.setStatus(status);

			ses.update(reim);
			tx.commit(); //commit the transaction by utilizing the methods from the Transaction

			return reim;
		} catch (IllegalStateException e) {
			log.warn(e + "\n");
			return null;
		}
	}

	@Override
	public List<Reimbursement> pendingrequestsHQL() {
		log.info("Attempting to get all pending requests\n");
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> reim = ses.createQuery("FROM Reimbursement where status_statusid = 1", Reimbursement.class).list();
		
		if (reim.size() > 0) {
			log.info("Returning all pending requests\n");
			return reim;
		} else {
			log.warn("No pending requests\n");
			return null;
		}
	}

	@Override
	public List<Reimbursement> resolvedrequestsHQL() {
		log.info("Attempting to get all resolved requests\n");
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> reim = ses
				.createQuery("FROM Reimbursement where not status_statusid = 1", Reimbursement.class).list();
		
		if (reim.size() > 0) {
			log.info("Returning all resolved requests\n");
			return reim;
		} else {
			log.warn("No resolved requests\n");
			return null;
		}
	}

	@Override
	public List<Reimbursement> requestsHQL(User u) {
		log.info("Attempting to get list of an employees requests\n");
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

}
