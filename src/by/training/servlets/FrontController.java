package by.training.servlets;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.commands.ActionFactory;
import by.training.commands.Command;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected ResourceBundle bundle = ResourceBundle.getBundle("path");

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		executeRequest(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		executeRequest(req, resp);
	}

	private void executeRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String page = null;
		ActionFactory client = new ActionFactory();
		Command command = client.detectCommand(req);
		page = command.execute(req);

		if (page != null && !page.isEmpty()) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
			rd.forward(req, resp);
		} else {
			RequestDispatcher rd = getServletContext()
					.getRequestDispatcher(bundle.getString("path.page.showall"));
			rd.forward(req, resp);
		}
	}
}
