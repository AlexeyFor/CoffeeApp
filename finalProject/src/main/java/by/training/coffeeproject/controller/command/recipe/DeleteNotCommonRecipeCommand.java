package by.training.coffeeproject.controller.command.recipe;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;

import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Delete Recipe from database
 *
 */
public class DeleteNotCommonRecipeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(DeleteNotCommonRecipeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService logRecipe = fct.getRecipeService();
		LOG.debug("before");
		LOG.debug(request.getParameter("recipeId"));
		LOG.debug(request.getParameter("recipeCommon"));

		Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
		LOG.debug(recipeId);
		boolean common = Boolean.parseBoolean(request.getParameter("recipeCommon"));
//		LOG.debug(common);

		try {
			if (!common) {
				boolean result;
//				LOG.debug("!common ");

				result = logRecipe.deleteRecipeFromDataBase(recipeId);

//			logUserRecipe.deleteUserRecipeInDB(recipeId, userId);
				if (result) {
//					LOG.debug(result);
					request.setAttribute("message", "RecipeWasDeleted");
				} else {
//					LOG.debug(result);
					request.setAttribute("message", "RecipeWasNotDeleted");
				}
			} else {
				LOG.warn("recipe is not common ");
				request.setAttribute("message", "recipe is not common ");
			}
		} catch (ServiceException e) {
			LOG.error(e.getMessage());
			request.setAttribute("message", e.getMessage());
		}

		page = ("/jsp/menu.html");
		answer.setPage(page);
		answer.setRedirect(false);
		return answer;

	}

}
