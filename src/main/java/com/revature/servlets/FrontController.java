package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.EmplRequestHelper;
import com.revature.util.ManagerRequestHelper;
import com.revature.util.UserRequestHelper;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		final String URI = req.getRequestURI();// .replace("/project-1/", "");
		System.out.println("begin " + URI);

		switch (URI) {
		case "/project-1/profile": // DONE
			System.out.println("case " + URI);
			UserRequestHelper.processProfile(req, res);
			break;

		case "/project-1/pending": // DONE
			System.out.println("case " + URI);
			EmplRequestHelper.processPending(req, res);
			break;
		case "/project-1/resolved": // DONE
			System.out.println("case " + URI);
			EmplRequestHelper.processResolved(req, res);
			break;

		case "/project-1/allempls": // DONE
			System.out.println("case " + URI);
			ManagerRequestHelper.processEmpls(req, res);
			break;
		case "/project-1/pendingrequests": // DONE
			System.out.println("case " + URI);
			ManagerRequestHelper.processPendingRequests(req, res);
			break;
		case "/project-1/resolvedrequests": // DONE
			System.out.println("case " + URI);
			ManagerRequestHelper.processResolvedRequests(req, res);
			break;
		default:
			doPost(req, res);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		final String URI = req.getRequestURI();
		System.out.println("begin " + URI);

		switch (URI) {
		case "/project-1/login": // DONE
			System.out.println("case " + URI);
			UserRequestHelper.processLogin(req, res);
			break;
		case "/project-1/logout": // DONE
			System.out.println("case " + URI);
			UserRequestHelper.processLogout(req, res);
			break;
		case "/project-1/update": // DONE
			System.out.println("case " + URI);
			UserRequestHelper.processUpdate(req, res);
			break;
		case "/project-1/signup":
			System.out.println("case " + URI);
			UserRequestHelper.processSignup(req, res);
			break;

		case "/project-1/reim": // DONE
			System.out.println("case " + URI);
			EmplRequestHelper.processReim(req, res);
			break;

		case "/project-1/requests": // DONE
			System.out.println("case " + URI);
			ManagerRequestHelper.processRequests(req, res);
			break;
		case "/project-1/resolve": // DONE
			System.out.println("case " + URI);
			ManagerRequestHelper.processResolve(req, res);
			break;
		default:
			System.out.println(URI);
			break;
		}

	}
}
