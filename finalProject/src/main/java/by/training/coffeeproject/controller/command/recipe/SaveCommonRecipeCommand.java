package by.training.coffeeproject.controller.command.recipe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.UserRecipe;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserRecipeService;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Save recipe for user - create new row in userRecipes
 *
 */
public class SaveCommonRecipeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(DeleteNotCommonRecipeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		UserRecipeService logUserRecipe = fct.getUserRecipeService();
//		LOG.debug(request.getParameter("recipeId"));
//		LOG.debug(request.getParameter("recipeCommon"));

		Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
//		LOG.debug(recipeId);
		boolean common = Boolean.parseBoolean(request.getParameter("recipeCommon"));
//		LOG.debug(common);
		HttpSession session = request.getSession();
//		LOG.debug("we have all data ");

		try {
			if (common) {
				Integer result;
				LOG.debug("common ");
				Integer userID = (Integer) session.getAttribute("ID");
				boolean exist = logUserRecipe.checkExists(userID, recipeId);

				if (!exist) {
					UserRecipe tmp = new UserRecipe(userID, recipeId);
					result = logUserRecipe.createUserRecipeInDB(tmp);
					if (result == 1) {
						request.setAttribute("message", "RecipeWasAdded");
					} else {
						request.setAttribute("errorMessage", "RecipeWasNotAdded");
					}
				} else {
					request.setAttribute("errorMessage", "RecipeWasAlreadyAdded");
				}

			} else {
				LOG.debug("recipe is not common ");
				request.setAttribute("errorMessage", "recipe is not common, can't add ");
			}
		} catch (ServiceException e) {
			LOG.error(e.getMessage());
			request.setAttribute("errorMessage", e.getMessage());

		}

		page = ("/jsp/menu.html");
		answer.setPage(page);
		answer.setRedirect(false);
		return answer;

	}

}
