package com.revature.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.HibernateUtil;

/*
 * Session methods:
 * 
 * save() and persist() ---- result in a SQL insert
 * update() and merge() ---- result in a SQL update
 * saveOrUpdate() ---------- result in a SQL insert OR update (depends)
 * get() and load()	-------- result in a SQL select 
 * 
 * 
 * There are 3 different ways to create complex queries in Hibernate:
 * 
 * 1. HQL - Hibernate Query Language
 * 2. Criteria API
 * 3. Native SQL 
 * 
 */
public class UserDaoImpl implements UserDao { // Applied log

	private static Logger log = Logger.getLogger(UserDaoImpl.class);
	Session ses;

	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User insert(User u) {
		log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction(); // perform an operation on DB

		ses.save(u); // use the save() session method to perform an insert operation
		tx.commit(); /*commit the transaction by utilizing the methods from the Transaction interface*/
		
		User user = u;
		return user;
	}

	@Override
	public void insert(Role r) {
		log.info("Attempting to insert role "+r+"\n");
		Session ses = HibernateUtil.getSession(); 
		Transaction tx = ses.beginTransaction();

		ses.save(r);
		tx.commit(); 

	}
	
	@Override
	public Role roleHQL() {
		log.info("Attemting to get roleid");
		Session ses = HibernateUtil.getSession(); // capture the session

		Role r = ses.get(Role.class, 1);

		//HibernateUtil.closeSes();

		return r;

	}

	@Override
	public User profileHQL(User u) {
		log.info("Attempting to get " + u.getUsername() + " profile\n");
		ses = HibernateUtil.getSession();
		
		Query q = ses.createQuery("from User where userid = :userid");
		q.setParameter("userid", u.getUserid());
		@SuppressWarnings("unchecked")
		List<User> u1 = q.getResultList();
		
		if (u1.size() > 0) {
			log.info("Returning profile\n");
			return u1.get(0);
		} else {
			log.warn(u.getUsername() + " has no profile\n");
			return null;
		}
	}

	@Override
	public List<User> allEmplHQL() { // - A Manager can view all Employees **DONE**
		log.info("Attempting to get all employees\n");
		ses = HibernateUtil.getSession();
		// This is an example of HQL --> HQL will check for the class name (of our java
		// models)

		List<User> empl = ses.createQuery("from User where role_roleid = 1", User.class).list();
		// HQL is returning all instances of the User class
		// no need for transaction object here We are just querying Data, NOT committing
		// any changes to our database, hence it's not a transaction
		
		if (empl.size() > 0) {
			log.info("Returning employees\n");
			return empl;
		} else {
			log.warn("Could not find employees\n");
			return null;
		}
	}

	@Override
	public User updateHQL(User user, String firstname, String lastname, String email, String username, String password,
			String repassword) {

		log.info("Attempting to change " + user.getUsername() + " profile\n");
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction(); // perform an operation on DB
		ses.evict(user);

		if (!(firstname.isEmpty() || firstname.isBlank() || firstname.equals(user.getFirstname()))) {
			user.setFirstname(firstname);
		}
		if (!(lastname.isEmpty() || lastname.isBlank() || lastname.equals(user.getFirstname()))) {
			user.setLastname(lastname);
		}
		if (!(email.isEmpty() || email.isBlank() || email.equals(user.getFirstname()))) {
			user.setEmail(email);
		}
		if (!(username.isEmpty() || username.isBlank() || username.equals(user.getFirstname()))) {
			user.setUsername(username);
		}
		if (!(password.isEmpty() || password.isBlank()) && !(repassword.isEmpty() || repassword.isBlank())
				&& (password.equals(repassword))) {
			user.setPassword(password);
		}

		ses.update(user);
		tx.commit();

		return user;
	}
	
	@Override
	public User useridHQL(int i) {
		Session ses = HibernateUtil.getSession();

		User u = ses.get(User.class, i);

		//HibernateUtil.closeSes();
		if(u!=null) {
		return u;
		}else {
			return null;
		}
	}
}
/*	public String sql, call;
	public PreparedStatement ps;
	public ResultSet rs;

	@Override
	public void profile(User u) { // - An Employee can view their information **DONE**

		User user = null;
		Role role;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			log.info("Attempting to access profile\n");

			sql = "select * from ersuser u " + "inner join roles r on r.userid=u.userid " + "where u.userid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUserid());
			rs = ps.executeQuery();

			if (rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), role);
			}

			if (user != null) {
				System.out.println(user);
			} else {
				log.warn("Not found\n");
				System.out.println("Not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void allEmpl() {
		ArrayList<User> empl = new ArrayList<>();
		Role role;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {

			log.info("Attempting to get all employees\n");

			sql = "select * FROM ersuser e " + "inner join roles r on r.userid =e.userid "
					+ "WHERE r.ersroles ='EMPLOYEE'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				empl.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), role));
			}

			for (User u : empl) {
				System.out.println(u);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/
