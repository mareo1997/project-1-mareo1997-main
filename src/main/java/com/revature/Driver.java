
package com.revature;

import java.sql.Timestamp;

import com.revature.model.Reimbursement;
import com.revature.model.Role;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;

public class Driver {

	// Instantiate a Hibernate Dao
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void main(String[] args) {

		//initialValues();
		//insert();
		reim();
		allEmpl();
		login();
	}
	
	public static void login() {
		User login = LoginService.confirm("mareo1997","password");
		userserv.profileHQL(login);
		System.out.println("login " + login);
	}
	
	public static void reim() {
		reimserv.findReimHQL(4);
		reimserv.pendingrequestsHQL();
		reimserv.resolvedrequestsHQL();
	}

	public static void insert() {
		Role role = userserv.roleHQL();
		
		User user = new User("15", "16", "17", "18", "19", role);
		userserv.insert(user);
		System.out.println(user.getPassword());

	}
	
	public static void allEmpl() {
		System.out.println(userserv.allEmplHQL());
	}

	public static void initialValues() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Role role1 = new Role(1, "EMPLOYEE");
		userserv.insert(role1);
		
		Role role2 = new Role(2, "MANAGER");
		userserv.insert(role2);

		System.out.println("done saving user to db");

		// Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com", role1);
		User mareo = user;
		userserv.insert(user);

		user = new User("marwil", "william", "Marcia", "Williamson", "mother@gmail.com", role1);
		User marcia = user;
		userserv.insert(user);

		user = new User("king", "george", "Kingsley", "Yapp", "father@gmail.com", role2);
		userserv.insert(user);

		System.out.println("done saving user to db");

		Status status = new Status(1, "PENDING");
		Status pending = status;
		reimserv.insert(status);
		
		status = new Status(2, "APPROVED");
		reimserv.insert(status);
		
		status = new Status(3, "DENIED");
		reimserv.insert(status);

		System.out.println("done saving user to db");

		Type type = new Type(1, "LODGE");
		Type lodge = type;
		reimserv.insert(type);
		
		type = new Type(2, "TRAVEL");
		Type travel = type;
		reimserv.insert(type);
		
		type = new Type(3, "FOOD");
		Type food = type;
		reimserv.insert(type);
		
		type = new Type(4, "OTHER");
		Type other = type;
		reimserv.insert(type);

		System.out.println("done saving user to db");

		Reimbursement reim = new Reimbursement(mareo, 100, "Hiton Inn", timestamp, pending, lodge);
		reimserv.insert(reim);
		reim = new Reimbursement(marcia, 100, "Jet Blue", timestamp, pending, travel);
		reimserv.insert(reim);
		reim = new Reimbursement(mareo, 200, "American Airlines", timestamp, pending, travel);
		reimserv.insert(reim);
		reim = new Reimbursement(marcia, 200, "4 Seasons", timestamp, pending, lodge);
		reimserv.insert(reim);
		reim = new Reimbursement(mareo, 300, "Wendy's", timestamp, pending, food);
		reimserv.insert(reim);
		reim = new Reimbursement(marcia, 300, "Misc", timestamp, pending, other);
		reimserv.insert(reim);
		reim = new Reimbursement(mareo, 400, "Misc", timestamp, pending, other);
		reimserv.insert(reim);

		System.out.println("done saving user to db");
	}
}
