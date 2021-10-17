package by.training.coffeeproject.controller.command.menu;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;

public class MenuCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(MenuCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");
		ForwardRedirect answer = new ForwardRedirect();

		String page = ("/jsp/menu.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
