package by.training.coffeeproject.controller.command.recipe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.UserRecipe;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserRecipeService;
import by.training.coffeeproject.service.creator.CoffeeRecipeCreator;
import by.training.coffeeproject.service.creator.CoffeeTypeCreator;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.creator.UserRecipeCreator;
import by.training.coffeeproject.service.validator.CoffeeTypeValidator;

public class DeleteNotCommonRecipeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(DeleteNotCommonRecipeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request)  {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService logRecipe = fct.getRecipeService();
		UserRecipeService logUserRecipe = fct.getUserRecipeService();
		LOG.debug("before");
		LOG.debug(request.getParameter("recipeId"));
		LOG.debug(request.getParameter("recipeCommon"));

		Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
		LOG.debug(recipeId);
		boolean common = Boolean.parseBoolean(request.getParameter("recipeCommon"));
		LOG.debug(common);
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("ID");
		LOG.debug("we have all data ");

		try {
		if (!common) {
			boolean result; 
			LOG.debug("!common ");

				result = logRecipe.deleteRecipeFromDataBase(recipeId);
			
//			logUserRecipe.deleteUserRecipeInDB(recipeId, userId);
			if (result ) {
				LOG.debug(result);
				request.setAttribute("message", "recipe was deleted");
			} else {
				LOG.debug(result);
				request.setAttribute("message", "recipe wasn't deleted");
			}
		} else {
			LOG.debug("recipe is not common ");
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
