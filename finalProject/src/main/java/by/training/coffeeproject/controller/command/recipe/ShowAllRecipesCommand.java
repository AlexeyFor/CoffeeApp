package by.training.coffeeproject.controller.command.recipe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Show all common recipes
 *
 */
public class ShowAllRecipesCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ShowAllRecipesCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		String page = null;
		ForwardRedirect answer = new ForwardRedirect();

		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService log = fct.getRecipeService();
		try {
			List<Recipe> recipes = log.findAllCommonRecipes();
			request.setAttribute("recipes", recipes);
			request.setAttribute("allSaved", false);

		} catch (ServiceException e) {
			LOG.debug("error in  ShowAllRecipesCommand " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		}
//		LOG.debug("get recipeType " + recipeType);

		page = ("/jsp/recipe/showAllRecipes.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
