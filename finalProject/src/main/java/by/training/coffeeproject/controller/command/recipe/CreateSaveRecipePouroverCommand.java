package by.training.coffeeproject.controller.command.recipe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.UserRecipe;
import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserRecipeService;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.creator.PouroverRecipeCreator;
import by.training.coffeeproject.service.creator.RecipeCreator;
import by.training.coffeeproject.service.creator.UserRecipeCreator;
import by.training.coffeeproject.service.validator.InfusionArrayValidator;
import by.training.coffeeproject.service.validator.PouroverRecipeValidator;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Create new !common pourover recipe in DB
 *
 */
public class CreateSaveRecipePouroverCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CreateSaveRecipePouroverCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		InfusionService logInfusion = fct.getInfusionService();
		PouroverRecipeService logPouroverRecipe = fct.getPouroverRecipeService();
		RecipeService logRecipe = fct.getRecipeService();
		UserRecipeService logUserRecipe = fct.getUserRecipeService();
		Integer recipeId = 0;
		try {
			boolean isValidRecipe = PouroverRecipeValidator.getInstance().validatePouroverRecipeRequest(request);
			boolean isValidInfusion = InfusionArrayValidator.getInstance().validateInfusionsArray(request);

			// Validation
			if (isValidRecipe && isValidInfusion) {
				Integer coffeeTypeId = Integer.valueOf(request.getParameter("coffeeTypeId"));
				Recipe recipe = RecipeCreator.getInstance().createFromRequest(request, new CoffeeType(coffeeTypeId),
						RecipeType.POUROVER);
				recipeId = logRecipe.createRecipeInDataBase(recipe);
				// create UserRecipe in DB (save this recipe in this user recipes)
				UserRecipe userRecipe = UserRecipeCreator.getInstance().createFromRequest(request, recipeId);
				logUserRecipe.createUserRecipeInDB(userRecipe);

				PouroverRecipe pouroverRecipe = PouroverRecipeCreator.getInstance().createFromRequest(request,
						recipeId);
				Integer result = logPouroverRecipe.createPouroverRecipeInDB(pouroverRecipe);
				if (result == 1) {
					List<Infusion> infusions = InfusionCreator.getInstance().createFromRequest(request, recipeId);
					for (int i = 0; i < infusions.size(); i++) {
						logInfusion.createInfusionInDB(infusions.get(i));
					}
					request.setAttribute("message", "RecipeWasCreated");
				} else {
					LOG.warn("recipe wasn't created");
					request.setAttribute("message", "RecipeWasNotCreated");
					if (recipeId != 0) {
						logRecipe.deleteRecipeFromDataBase(recipeId);
						LOG.warn("recipe wasn deleted");
					}
				}
			} else {
				request.setAttribute("message", "RecipeWasNotCreated");
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
