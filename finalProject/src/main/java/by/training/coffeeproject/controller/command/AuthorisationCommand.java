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
		// извлечение из запроса логина и пароля
		String login = request.getParameter("username");
		String pass = request.getParameter("pwd");
		// проверка логина и пароля
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
			request.setAttribute("error", "message.loginerror");
		}

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
