package by.training.coffeeproject.controller.command.recipe;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.CoffeeRecipeCreator;
import by.training.coffeeproject.service.creator.CoffeeTypeCreator;
import by.training.coffeeproject.service.validator.CoffeeTypeValidator;

public class CreateRecipeTypeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CreateRecipeTypeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		CoffeeTypeService logCoffeeType = fct.getCoffeeTypeService();
		RecipeService logRecipe = fct.getRecipeService();
		
		boolean wasCreated = Boolean.getBoolean(request.getParameter("wasCreated"));
		LOG.debug("wasCreated " + wasCreated);

		try {
			if (wasCreated) {
				// Validation
				boolean isValid = CoffeeTypeValidator.getInstance().validateForCreateRecipeType(request);
				if (isValid) {
					// create CoffeeType
					CoffeeType coffeeType = CoffeeTypeCreator.getInstance().createFromRequest(request);
					// create CoffeeType in DB
					Integer coffeeTypeId = logCoffeeType.createCoffeeTypeInDataBase(coffeeType);
					coffeeType.setID(coffeeTypeId);
					// create Recipe
					Recipe recipe = CoffeeRecipeCreator.getInstance().createFromRequest(request, coffeeType);
					// create Recipe in DB
					Integer recipeId = logRecipe.createRecipeInDataBase(recipe);
					request.setAttribute("recipeId", recipeId);
					request.setAttribute("infusions", request.getParameter("infusions"));
				}
			} else {
				boolean isValid = CoffeeTypeValidator.getInstance().validateForCreateRecipeTypeSelected(request);
				if (isValid) {
				Integer coffeeTypeId = Integer.valueOf(request.getParameter("coffeeTypeID"));
				Recipe recipe = CoffeeRecipeCreator.getInstance().createFromRequest(request,
						new CoffeeType(coffeeTypeId));
				// create Recipe in DB
				Integer recipeId = logRecipe.createRecipeInDataBase(recipe);
				request.setAttribute("recipeId", recipeId);
				request.setAttribute("infusions", request.getParameter("infusions"));
				}
			}
		} catch (ServiceException e) {
			request.setAttribute("message", e.getMessage());
		}

		RecipeType recipeType = RecipeType.valueOf(request.getParameter("recipeType"));
		switch (recipeType) {
		case POUROVER:
			page = ("/jsp/recipe/createPouroverRecipe.html");
			break;
		case FRENCHPRESS:
			page = ("/jsp/recipe/createFrenchpressRecipe.html");
			break;
		}

		answer.setPage(page);
		answer.setRedirect(false);
		LOG.debug("all ok");
		return answer;
	}
}