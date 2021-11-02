package by.training.coffeeproject.controller;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.ShowRequestListenerService;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Writes information about all viewed recipes in the file
 *
 */
@WebListener
public class ShowRequestListener implements ServletRequestListener {
	private static final Logger LOG = LogManager.getLogger(ShowRequestListener.class);
	private final String PAGE = "/jsp/recipe/showRecipe";
	private final String FILEADDRESS = "/WEB-INF/recipesViews/views.txt";

	public void requestDestroyed(ServletRequestEvent event) {
		LOG.debug("start requestDestroyed");

		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if (uri != null && !uri.equals("")) {
			int begin = contextPath.length();
			int end = uri.lastIndexOf('.');
			String commandName;

			// get command name
			if (end > begin) {
				commandName = uri.substring(begin, end);
			} else {
				commandName = uri.substring(begin);
			}
//			LOG.debug("commandName " + commandName);

			if (commandName.equals(PAGE)) {
//				LOG.debug("write data from request");
				ShowRequestListenerService.getInstance().writeDataFromRequestInFile(request, FILEADDRESS);
			}
		}
	}
}
