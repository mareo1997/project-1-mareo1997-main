package com.revature.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.HibernateUtil;

public class LoginService { // Applied logs and HQL

	private static Logger log = Logger.getLogger(LoginService.class);
	public static String sql;
	public static PreparedStatement ps;
	public static ResultSet rs;

	public static boolean login(String username, String password) {
		String u = null, p = null;
		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			sql = "select username, erspassword from ersuser where username=? and erspassword=?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				u = rs.getString(1);
				p = rs.getString(2);
			}
			if (u != null && p != null) {
				return true;
				// return (username.equalsIgnoreCase("Aretha") && password.equals("awesome"));
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static User confirm(String username, String password) {
		log.info("Attempting to confirm username: " + username + " \n");

		Session ses = HibernateUtil.getSession();
		
		Query q = ses.createQuery("From User where username = :username");
		q.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<User> u = q.getResultList();
		
		if(u.size()>0) {
			log.info("Confirmed username\n");
			if(BCrypt.checkpw(password, u.get(0).getPassword())) {
				log.info("Confirmed password. Returning credentials\n");
				return u.get(0);
			}else {
				log.warn("Could not confirm password\n");
				return null;
			}
		}else {
			log.warn("Could not confirm username\n");
			return null;
		}
	}

	public static User authority(String username) {
		log.info("Attempting to validate " + username + " \n");

		Session ses = HibernateUtil.getSession(); // capture the session
		
		Query q = ses.createQuery("From User where username = :username");
		q.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<User> u = q.getResultList();

		//List<User> u = ses.createQuery("from User where username = '" + username + "' ", User.class).list();
		if (u.size() > 0) {
			log.info("Returning " + username + " credentials\n");
			return u.get(0);
		} else {
			log.warn("Could not validate " + username + " \n");
			return null;
		}
	}
}
