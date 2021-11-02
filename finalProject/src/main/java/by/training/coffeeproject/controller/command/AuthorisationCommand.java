package by.training.coffeeproject.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserService;

public class AuthorisationCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(AuthorisationCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");

		ForwardRedirect answer = new ForwardRedirect();

		String page = null;
		String login = request.getParameter("username");
		String pass = request.getParameter("pwd");
		ServiceFactory fct = ServiceFactory.getInstance();
		UserService log = fct.getUserService();
		User user;
		HttpSession session = request.getSession();
		try {
			user = log.authorization(login, pass);
			int ID = user.getID();
			String role = user.getRole().getName();
			session.setAttribute("ID", ID);
			session.setAttribute("role", role);

			page = ("/jsp/menu.html");

		} catch (ServiceException e) {
			LOG.warn ("wrong login or password");
			request.setAttribute("message", "Message.loginerror");
			page = ("/jsp/startPage.html");
			answer.setPage(page);
			answer.setRedirect(false);

			return answer;
		}

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
