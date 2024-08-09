package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;
import com.revature.template.ReimTemplate;

public class EmplRequestHelper { // Applied log, exceptions

	private static Logger log = Logger.getLogger(EmplRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processReim(HttpServletRequest req, HttpServletResponse res) throws IOException { /* Worked, displayed, needs to be refined */

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

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

			ReimTemplate attempt = om.readValue(body, ReimTemplate.class);
			String username = attempt.getUsername();
			User u = LoginService.authority(username);

			if (u != null && u.getRole().getRoleid() == 1) {

				double amount = attempt.getAmount();
				String description = attempt.getDescription();
				Timestamp submit = new Timestamp(System.currentTimeMillis());
				Status status = reimserv.statusHQL(1);
				int t = attempt.getType();
				Type type = reimserv.typeHQL(t);

				Reimbursement reim = new Reimbursement(u, amount, description, submit, status, type);
				reim = reimserv.submitHQL(reim);

				if (reim != null) {
					log.info("Submitted form");
					res.setStatus(200);
					System.out.println(reim);
					ps.println(om.writeValueAsString(reim));
				} else {
					res.setStatus(204); // Connection was successful but no user found
				}
			} else {
				log.warn("Not permitted\n");
				res.setStatus(401);
				ps.write(om.writeValueAsString("The requested action is not permitted."));
			}
		} catch (NullPointerException e) {
			res.setStatus(204); // Connection was successful but no user found
		}
	}

	public static void processPending(HttpServletRequest req, HttpServletResponse res)
			throws IOException { /* Worked, displayed, needs to be refined */

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 1) {
					List<Reimbursement> reim = reimserv.pendingHQL(u);
					if (reim != null) {
						log.info("Got pending reims");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						res.setStatus(204);
						ps.write(om.writeValueAsString("No pending reims\n"));
					}
				} else {
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(om.writeValueAsString("Does not exist."));
				}
			} else {
				log.warn("Not logged in\n");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				ps.write(om.writeValueAsString("The requested action is not permitted."));
			}
		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(204);
			ps.write(om.writeValueAsString("The requested action is not permitted."));
		}
	}

	public static void processResolved(HttpServletRequest req, HttpServletResponse res)
			throws IOException { /* Empl gets his resolved reim */

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 1) {
					List<Reimbursement> reim = reimserv.resolvedHQL(u);
					if (reim != null) {
						log.info("Got resolved reims");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						res.setStatus(204);
						ps.write(om.writeValueAsString("No resolved reims\n"));
					}
				} else {
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(om.writeValueAsString("Does not exist."));
				}
			} else {
				log.warn("Not logged in\n");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				ps.write(om.writeValueAsString("The requested action is not permitted."));
			}
		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(204);
			ps.write(om.writeValueAsString("The requested action is not permitted."));
		}
	}
}
