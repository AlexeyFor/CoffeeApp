package by.training.coffeeproject.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuestCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(GuestCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		ForwardRedirect answer = new ForwardRedirect();
		
		HttpSession session = request.getSession();
		session.setAttribute("ID", 0);
		session.setAttribute("role", "guest");

		String page = ("/jsp/menu.html");
		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
