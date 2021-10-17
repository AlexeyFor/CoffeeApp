package by.training.coffeeproject.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserService;

public class NothingCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(NothingCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		ForwardRedirect answer = new ForwardRedirect();

		String page = null;

		User user;
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		request.setAttribute("login", user.getLogin());
		page = ("/jsp/Nothing.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
