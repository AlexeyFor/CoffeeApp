package by.training.coffeeproject.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Redirect for all pages thats are under construction
 *
 */

public class NothingCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(NothingCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");
		ForwardRedirect answer = new ForwardRedirect();

		String page = ("/jsp/nothing.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
