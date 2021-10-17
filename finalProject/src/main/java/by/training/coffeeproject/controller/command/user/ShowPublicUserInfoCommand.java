package by.training.coffeeproject.controller.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.UserService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class ShowPublicUserInfoCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ShowPublicUserInfoCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");
		ForwardRedirect answer = new ForwardRedirect();

		Integer ID = Integer.valueOf(request.getParameter("userID"));
		ServiceFactory fct = ServiceFactory.getInstance();
		UserService log = fct.getUserService();
		RecipeService logRecipe = fct.getRecipeService();

		try {
			User user = log.takeUserByID(ID);
			List<Recipe> recipes = logRecipe.findAllUserCommonRecipes(ID);
			user.getUserInfo().setRecipes(recipes);
			LOG.debug("get user + " + user.toString());

			request.setAttribute("user", user);
			request.setAttribute("recipes", recipes);
		} catch (ServiceException e) {
			LOG.debug("error in  ShowPublicUserInfoCommand ");
			request.setAttribute("error", "message.loginerror");
		}
		String page = ("/jsp/user/showPublicUserInfo.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
