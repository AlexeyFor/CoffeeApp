package by.training.coffeeproject.controller.command.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;

public class LogOutCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(LogOutCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");
		ForwardRedirect answer = new ForwardRedirect();

		HttpSession session = request.getSession();

		session.setAttribute("role", null);
		session.setAttribute("ID", null);

		String page = ("/jsp/startPage.html");
		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
