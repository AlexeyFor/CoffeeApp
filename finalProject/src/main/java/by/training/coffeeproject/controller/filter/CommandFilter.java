package by.training.coffeeproject.controller.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.*;
import by.training.coffeeproject.controller.command.recipe.*;
import by.training.coffeeproject.controller.command.user.*;
import by.training.coffeeproject.controller.command.menu.*;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         put command in every request to ControllerServlet
 *
 */
public class CommandFilter implements Filter {
	private final static Map<String, Command> commandMap = new HashMap<>();
	private static final Logger LOG = LogManager.getLogger(CommandFilter.class);

	static {
		commandMap.put("/jsp/authorization", new AuthorizationPageCommand());
		commandMap.put("/", new StartCommand());
		commandMap.put("/jsp/startPage", new StartCommand());
		commandMap.put("/jsp/guest", new GuestCommand());
		commandMap.put("/jsp/result", new AuthorisationCommand());
		commandMap.put("/jsp/nothing", new NothingCommand());

		commandMap.put("/jsp/recipe/showRecipe", new ShowRecipeCommand());
		commandMap.put("/jsp/recipe/showAllRecipes", new ShowAllRecipesCommand());
		commandMap.put("/jsp/recipe/showAllCoffeeType", new ShowAllCoffeeTypeCommand());
		commandMap.put("/jsp/recipe/deleteNotCommonRecipe", new DeleteNotCommonRecipeCommand());
		commandMap.put("/jsp/recipe/saveCommonRecipe", new SaveCommonRecipeCommand());
		commandMap.put("/jsp/recipe/showAllCoffeeTypeEdit", new ShowAllCoffeeTypeEditCommand());

		commandMap.put("/jsp/recipe/createRecipeStep1Coffee", new CreateRecipeStep1CoffeeCommand());
		commandMap.put("/jsp/recipe/createRecipeStep2", new CreateRecipeStep2Command());
		commandMap.put("/jsp/recipe/createSaveRecipePourover", new CreateSaveRecipePouroverCommand());

		commandMap.put("/jsp/recipe/editRecipeStep1Coffee", new EditRecipeStep1CoffeeCommand());
		commandMap.put("/jsp/recipe/editRecipeStep2", new EditRecipeStep2Command());
		commandMap.put("/jsp/recipe/editSaveChangesRecipePourover", new EditSaveChangesRecipePouroverCommand());

		commandMap.put("/jsp/recipe/deleteSavedCommonRecipe", new DeleteSavedCommonRecipeCommand());

		commandMap.put("/jsp/user/showPublicUserInfo", new ShowPublicUserInfoCommand());
		commandMap.put("/jsp/locale", new SetLocaleCommand());
		commandMap.put("/jsp/menu", new MenuCommand());
		commandMap.put("/jsp/logOut", new LogOutCommand());

		commandMap.put("/jsp/user/showAllSavedRecipes", new ShowAllUserSavedRecipesCommand());

	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		LOG.debug("start doFilter");
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
			String contextPath = httpRequest.getContextPath();
			String uri = httpRequest.getRequestURI();
//			LOG.debug("getRequestURI is " + uri);
			int begin = contextPath.length();
			int end = uri.lastIndexOf('.');
			String commandName;

			// get command name
			if (end > begin) {
				commandName = uri.substring(begin, end);
			} else {
				commandName = uri.substring(begin);
			}
			LOG.debug("commandName is " + commandName);

			// get command
			if (commandMap.containsKey(commandName)) {
				Command command = commandMap.get(commandName);
				httpRequest.setAttribute("command", command);
				filterChain.doFilter(httpRequest, servletResponse);
			} else {
				LOG.error("can't take command from URI " + uri);
				httpRequest.setAttribute("error", "Error.ErrorAdresMessage");
				httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(httpRequest,
						servletResponse);
			}
		} else {
			LOG.error("impossible to use HTTP filter");
			servletRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(servletRequest,
					servletResponse);
		}
	}
}
