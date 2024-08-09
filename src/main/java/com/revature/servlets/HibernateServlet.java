package com.revature.servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.model.Reimbursement;
import com.revature.model.Role;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;
import com.revature.util.HibernateUtil;

public class HibernateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public HibernateServlet() {
		// Here we will invoke the initialValues() method
		initialValues();

	}

	private void initialValues() {
		Role role1 = new Role(1, "EMPLOYEE");
		userserv.insert(role1);
		Role role2 = new Role(2, "MANAGER");
		userserv.insert(role2);

		// Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com", role1);
		User mareo = user;
		userserv.insert(user);

		user = new User("marwil", "william", "Marcia", "Williamson", "mother@gmail.com", role1);
		userserv.insert(user);

		user = new User("king", "george", "Kingsley", "Yapp", "father@gmail.com", role2);
		userserv.insert(user);

		Status status = new Status(1, "PENDING");
		reimserv.insert(status);
		status = new Status(2, "APPROVED");
		Status approved = status;
		reimserv.insert(status);
		status = new Status(3, "DENIED");
		reimserv.insert(status);

		Type type = new Type(1, "LODGE");
		Type lodge = type;
		reimserv.insert(type);
		type = new Type(2, "TRAVEL");
		reimserv.insert(type);
		type = new Type(3, "FOOD");
		reimserv.insert(type);
		type = new Type(4, "OTHER");
		reimserv.insert(type);

		System.out.println("done saving user to db");

		Reimbursement reim = new Reimbursement(mareo, 1897, "HILTON INN", timestamp, approved, lodge);
		reimserv.insert(reim);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(userserv.allEmplHQL());
		HibernateUtil.closeSes();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
