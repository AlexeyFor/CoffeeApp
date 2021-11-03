package by.training.coffeeproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.pool.ConnectionPool;

public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 6811873944606376780L;
	private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);

	@Override
	public void init() {
		LOG.debug("start init servl");
		final String url = "jdbc:mysql://localhost/coffeeRecipes";
		final String propertiesPath = this.getServletContext().getRealPath("/WEB-INF/classes/database.properties");
		final int startSize = 10;
		final int maxSize = 200;
		final int checkConnectionTimeout = 3;
		try {
			ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		LOG.debug("start processRequest");
		Command command = (Command) request.getAttribute("command");

		if (command != null) {
			LOG.debug("command not null " + command.getClass().toString());
			ForwardRedirect forwardRedir = command.execute(request);
			page = forwardRedir.getPage();
			if (page != null && !forwardRedir.isRedirect()) {
				String pageName = fromHtmlToJSP(page);
				setAllcookiesFromRequest(request, response);
//				forDebug(request);
				getServletContext().getRequestDispatcher(pageName).forward(request, response);
			} else if (page != null && forwardRedir.isRedirect()) {
				String redirectedUri = request.getContextPath() + page;
				setAllcookiesFromRequest(request, response);
				LOG.debug("redirect request on " + redirectedUri);
				response.sendRedirect(redirectedUri);
			} else {
				LOG.error("forward to error page");
				request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			}
		} else {
			LOG.error("command = null");
			request.setAttribute("error", "mistake");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}

	// set cookies for the first time
	private void setAllcookiesFromRequest(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		int counter = 0;

		for (Cookie cook : cookies) {
			if (cook.getName().equals("localeName")) {
				counter++;
			}
		}
		if (counter == 0) {
			Cookie tmp = new Cookie("localeName", "ru_RU");
			tmp.setMaxAge(60 * 60 * 24 * 90);
			tmp.setPath("/webCoffeeApp/jsp");
			response.addCookie(tmp);
		}
		for (Cookie cook : cookies) {
			response.addCookie(cook);
		}

	}

//	private void setPreviousPageInSession(HttpServletRequest request, String pageName) {
//		HttpSession session = request.getSession();
//		pageName = pageName.concat(".html");
//		session.setAttribute("previousPage", pageName);
//	}

	private String fromHtmlToJSP(String page) {
		int end = page.lastIndexOf('.');
		String pageName;
		pageName = page.substring(0, end);
		pageName = pageName.concat(".jsp");
		pageName = "/WEB-INF".concat(pageName);
		LOG.debug("page is " + pageName);
		return pageName;
	}

//	private void forDebug (HttpServletRequest request) {
//		Map <String, String []> fromReq = request.getParameterMap();
//		Enumeration <String> namesEnum = request.getAttributeNames();
//		List <String> names= Collections.list (namesEnum);
//			 
//		for (String name : names) {
//			String attr = request.getAttribute(name).toString();
////		 for (Map.Entry<String, String []> entry : fromReq.entrySet()) {
//			 LOG.warn (" key " + name + " //value " + attr);
//		    }
//	}
}
