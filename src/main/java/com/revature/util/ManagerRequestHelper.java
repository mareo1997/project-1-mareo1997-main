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
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;
import com.revature.template.RequestsTemplate;
import com.revature.template.ResolveTemplate;

public class ManagerRequestHelper {

	private static Logger log = Logger.getLogger(ManagerRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processEmpls(HttpServletRequest req, HttpServletResponse res) throws IOException{ // See all empls

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<User> empl = userserv.allEmplHQL();
					if (empl != null) {
						res.setStatus(200);
						log.info("Got employees");
						System.out.println(empl);
						ps.println(om.writeValueAsString(empl));
					} else {
						res.setStatus(204);
						ps.println(om.writeValueAsString("No employeed"));
					}
				} else {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(om.writeValueAsString("The requested action is not permitted."));
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

	public static void processResolve(HttpServletRequest req, HttpServletResponse res) throws IOException { /* Manager resolves a reim */
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			BufferedReader reader = req.getReader();
			StringBuilder s = new StringBuilder();

			// we are just transferring our Reader data to our StringBuilder, line by line
			String line = null;

			while ((line = reader.readLine()) != null) {
				s.append(line);
				line = reader.readLine();
			}

			String body = s.toString();

			ResolveTemplate attempt = om.readValue(body, ResolveTemplate.class);
			String username = attempt.getUsername();
			User u = LoginService.authority(username);

			if (u != null && u.getRole().getRoleid() == 2) {
				int reimid = attempt.getReimid();
				Reimbursement reim = reimserv.findReimHQL(reimid);
				int statusid = attempt.getStatus();
				Status status = reimserv.statusHQL(statusid);
				Timestamp resolved = new Timestamp(System.currentTimeMillis());
				reim = reimserv.resolveHQL(reim, u, status, resolved);
				System.out.println(reim+""+u+""+status+""+resolved);
				if (reim != null) {
					log.info("Submitted form");
					res.setStatus(200);
					System.out.println(reim);
					ps.println(om.writeValueAsString(reim));
				} else {
					res.setStatus(204); // Connection was successful but no user found
					ps.write(om.writeValueAsString("Could not resolve."));
				}
			} else {
				log.warn("Not permitted\n");
				res.setStatus(401);
				ps.write(om.writeValueAsString("The requested action is not permitted."));
			}
		} catch (NullPointerException e) {
			res.setStatus(202);
		}
	}

	public static void processRequests(HttpServletRequest req, HttpServletResponse res) throws IOException{ /* Manager views an empl requests */
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

			RequestsTemplate attempt = om.readValue(body, RequestsTemplate.class);
			String m = attempt.getManager();
			User man = LoginService.authority(m);

			if (man != null && man.getRole().getRoleid() == 2) {
				String e = attempt.getEmployee();
				User empl = LoginService.authority(e);
				if (empl != null) {
					List<Reimbursement> reim = reimserv.requestsHQL(empl);
					if (reim != null) {
						log.info("Got requests\n");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						log.warn("No requests found\n");
						res.setStatus(204);
						ps.println(om.writeValueAsString("Has no pending requests"));
					}
				} else {
					log.warn("No requests found\n");
					res.setStatus(202);
					ps.println(om.writeValueAsString("Can not find employee"));
				}
			} else {
				log.warn("Not logged in\n");
				res.setStatus(401);
				ps.write(om.writeValueAsString("The requested action is not permitted."));
			}
		} catch (NullPointerException e) {
			res.setStatus(204);
		}
	}

	public static void processPendingRequests(HttpServletRequest req, HttpServletResponse res) throws IOException { /* Manager views all empls pendings */
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<Reimbursement> reim = reimserv.pendingrequestsHQL();
					if (reim != null) {
						log.info("Got all pending requests\n");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						log.warn("No requests found\n");
						res.setStatus(204);
					}
				} else {
					log.warn("Not permitted\n");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(om.writeValueAsString("The requested action is not permitted."));
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

	public static void processResolvedRequests(HttpServletRequest req, HttpServletResponse res) throws IOException{/* Manager views all empls resolved requets */
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<Reimbursement> reim = reimserv.resolvedrequestsHQL();
					if (reim != null) {
						log.info("Got all resolved requests\n");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						log.warn("No resolved found\n");
						res.setStatus(204);
					}
				} else {
					log.warn("Not permitted\n");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(om.writeValueAsString("The requested action is not permitted."));
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

/*	public static void processType(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int t = Integer.parseInt(req.getParameter("type"));

		Type type = reimserv.typeHQL(t);

		res.setStatus(200);
		System.out.println(type);
		ps.println(om.writeValueAsString(type));

	}

	public static void processStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int statusid = Integer.parseInt(req.getParameter("status"));

		Status status = reimserv.statusHQL(statusid);

		res.setStatus(200);
		System.out.println(status);
		ps.println(om.writeValueAsString(status));

	}

	public static void processReimID(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int Reimbursementid = Integer.parseInt(req.getParameter("reimid"));

		Reimbursement reim = reimserv.findReimHQL(Reimbursementid);

		if (reim != null) {
			res.setStatus(200);
			System.out.println(reim);
			ps.println(om.writeValueAsString(reim));
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(om.writeValueAsString("Invalid credentials"));
		}
	}
*/