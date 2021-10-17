package by.training.coffeeproject.controller.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class ShowAllUserSavedRecipesCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ShowAllUserSavedRecipesCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		HttpSession session = request.getSession();
		Integer userID = (Integer) session.getAttribute("ID");

		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService logic = fct.getRecipeService();
		try {
			List<Recipe> recipes = logic.findAllUserSavedRecipes(userID);
			request.setAttribute("recipes", recipes);
		} catch (ServiceException e) {
			LOG.debug("error in  ShowAllRecipesCommand " + e.getMessage());
			request.setAttribute("error", "message.loginerror");
		}

		page = ("/jsp/recipe/showAllRecipes.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
