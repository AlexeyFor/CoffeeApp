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
		LOG.debug("after execute");

		Integer ID = Integer.valueOf(request.getParameter("recipeID"));
		LOG.debug("after getingID");

		ServiceFactory fct = ServiceFactory.getInstance();
		RecipeService logic = fct.getRecipeService();
		try {
			Recipe recipe = logic.findRecipeByID(ID);
			LOG.debug("get recipe + " + recipe.toString());
			request.setAttribute("recipe", recipe);
		} catch (ServiceException e) {
			LOG.debug("error in  ShowRecipeCommand ");
			request.setAttribute("error", "message.loginerror");
		}
		String page = ("/jsp/recipe/showRecipe.html");
		LOG.debug("////////////page is " + page);
		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}
}
