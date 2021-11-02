package by.training.coffeeproject.controller.command.recipe;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class ShowRecipeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ShowRecipeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute");
		ForwardRedirect answer = new ForwardRedirect();
		String page;

		Integer ID = Integer.valueOf(request.getParameter("recipeID"));
		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService logic = fct.getRecipeService();
		Recipe recipe;
		try {
			recipe = logic.findRecipeByID(ID);
			LOG.debug("get recipe + " + recipe.toString());
			// this attribute is adding for displaying button "delete", for saved common
			// methods. Because in this case, it will be another command for delete recipe
			request.setAttribute("allSaved", request.getParameter("allSaved"));
			request.setAttribute("recipe", recipe);
			request.setAttribute("recipeID", ID);
//			LOG.debug(recipe.toString());
		} catch (ServiceException e) {
			LOG.error("error in  ShowRecipeCommand ");
			request.setAttribute("recipeID", ID);
			request.setAttribute("message", e.getMessage());
		}
		page = ("/jsp/recipe/showRecipe.html");

//		LOG.debug("page is " + page);
		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
