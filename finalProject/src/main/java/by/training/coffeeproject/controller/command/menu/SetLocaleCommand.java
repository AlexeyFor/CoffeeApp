package by.training.coffeeproject.controller.command.menu;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;

public class SetLocaleCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(SetLocaleCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		ForwardRedirect answer = new ForwardRedirect();

		String currentPage = request.getParameter("currentPage");
		String contextPath = request.getContextPath();
		int counter = 0;

		String localeName = request.getParameter("localeName");
		LOG.debug("localeName is " + localeName);
		Cookie[] cookies = request.getCookies();
		for (Cookie cook : cookies) {
			if (cook.getName().equals("localeName")) {
				cook.setValue(localeName);
				LOG.debug(cook.getName() + "  " + cook.getValue());

				counter++;
			}
		}

		LOG.debug("counter " + counter);
		String page = getPageFromUri(currentPage, contextPath);

		answer.setPage(page);
		answer.setRedirect(true);

		return answer;
	}

	private String getPageFromUri(String uri, String contextPath) {
//		LOG.debug("get URI " + uri);
		int begin = contextPath.length() + "/WEB-INF".length();
		int end = uri.lastIndexOf('.') + 1;
		String page;

		// get command name
		if (end > begin) {
			page = uri.substring(begin, end);
		} else {
			page = uri.substring(begin);
		}
		page = page.concat("html");
		LOG.debug("page is " + page);
		return page;
	}

}
