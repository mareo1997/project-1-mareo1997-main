package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.UserServicesImpl;
import com.revature.template.LoginTemplate;
import com.revature.template.SignupTemplate;
import com.revature.template.UpdateTemplate;

public class UserRequestHelper {
	private static Logger log = Logger.getLogger(UserRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException{ //Worked
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;
		
		while((line=reader.readLine()) != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		
		LoginTemplate attempt = om.readValue(body, LoginTemplate.class);
		String username = attempt.getUsername();
		String password = attempt.getPassword();

		log.info("Username attempted " + username);
		System.out.println(username+" "+password);

		User login = LoginService.confirm(username, password);
		
		PrintWriter ps = res.getWriter();
		res.setContentType("application/json");

		if (login != null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);			
			
			if (login.getRole().getRoleid() == 1) {
				System.out.println(login);
				ps.println(om.writeValueAsString(login));
				log.info(username + " successfully logged in\n");
				res.setStatus(200);
			} else if (login.getRole().getRoleid() == 2) {
				System.out.println(login);
				ps.println(om.writeValueAsString(login));
				log.info(username + " successfully logged in\n");
				res.setStatus(201);
			}
		}else {
			res.setContentType("application/json");
			res.setStatus(204); // Connection was successful but no user found
		}
	}

	public static void processLogout(HttpServletRequest req, HttpServletResponse res) { //Worked
		HttpSession session = req.getSession(false);

		if (session != null) {
			String username = (String) session.getAttribute("username");
			log.info(username + " logged out\n");
			session.invalidate();
		}
		res.setStatus(200);
	}

	public static void processProfile(HttpServletRequest req, HttpServletResponse res) throws IOException   {  //Worked, displayed, needs to be refined

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			
			if (session != null) {//System.out.println("logged in");
				String username = (String) session.getAttribute("username");
				System.out.println(username);
				User u = LoginService.authority(username);
				System.out.println(u);
				u = userserv.profileHQL(u);
				
				if (u != null) {//System.out.println("user found");
					log.info("Got profile\n");
					res.setStatus(200);
					System.out.println(u);
					ps.println(om.writeValueAsString(u));
				
				} else {
					log.warn("Couldn't get profile\n");
					res.setStatus(204);
					ps.write(om.writeValueAsString("Does not exist."));
				}
				
			} else {
				log.warn("Not logged in\n");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				System.out.println("The requested action is not permitted.1");
			}

		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			System.out.println("The requested action is not permitted.2");
		}
	}
	
	public static void processUpdate(HttpServletRequest req, HttpServletResponse res) throws IOException {

		try {
			BufferedReader reader = req.getReader();
			System.out.println("reader " + reader);
			StringBuilder s = new StringBuilder();

			// we are just transferring our Reader data to our StringBuilder, line by line
			String line = null;

			while ((line = reader.readLine()) != null) {
				System.out.println("line " + line);
				s.append(line);
				System.out.println("s " + s);
				line = reader.readLine();
			}

			String body = s.toString();
			System.out.println("body " + body);

			UpdateTemplate attempt = om.readValue(body, UpdateTemplate.class);
			String updater = attempt.getUpdater();
			String firstname = attempt.getFirstname();
			String lastname = attempt.getLastname();
			String email = attempt.getEmail();
			String username = attempt.getUsername();
			String password = attempt.getPassword();
			String repassword = attempt.getRepassword();

			User update = LoginService.authority(updater);
			res.setContentType("application/json");
			PrintWriter ps = res.getWriter();

			if (update != null) {
				update = userserv.updateHQL(update, firstname, lastname, email, username, password, repassword);
				if (update != null) {
					log.info("Updated " + update.getUsername() + " profile");
					res.setStatus(200);
					System.out.println(update);
					ps.println(om.writeValueAsString(update));
				} else {
					res.setStatus(204);
					ps.write(om.writeValueAsString("Does not exist."));
				}
			} else {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				ps.write(om.writeValueAsString("Does not exist."));
			}
		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(204);
		}
	}

	public static void processSignup(HttpServletRequest req, HttpServletResponse res) throws IOException  {
		res.setContentType("application/json");

		try {
			BufferedReader reader = req.getReader();
			System.out.println("reader " + reader);
			StringBuilder s = new StringBuilder();

			// we are just transferring our Reader data to our StringBuilder, line by line
			String line = null;

			while ((line = reader.readLine()) != null) {
				System.out.println("line " + line);
				s.append(line);
				System.out.println("s " + s);
				line = reader.readLine();
			}

			String body = s.toString();
			System.out.println("body " + body);

			SignupTemplate attempt = om.readValue(body, SignupTemplate.class);
			String firstname = attempt.getFirstname();
			String lastname = attempt.getLastname();
			String email = attempt.getEmail();
			String username = attempt.getUsername();
			String password = attempt.getPassword();
			String repassword = attempt.getRepassword();

			if (!(password.equals(repassword))) {
				res.setStatus(202);
			} else {
				Role role = userserv.roleHQL();
				User user = new User(username, password, firstname, lastname, email, role);
				User u = userserv.insert(user);

				if (u != null) {
					res.setStatus(200);
				} else {
					res.setStatus(204);
				}
			}
		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(204);
		}	
	}
}