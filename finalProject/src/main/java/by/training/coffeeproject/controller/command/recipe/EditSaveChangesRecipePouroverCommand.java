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
import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.creator.PouroverRecipeCreator;
import by.training.coffeeproject.service.validator.InfusionArrayValidator;
import by.training.coffeeproject.service.validator.PouroverRecipeValidator;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Update recipe, pourover recipe in database. Infusions - old will be
 *         deleted and new will be created
 *
 */
public class EditSaveChangesRecipePouroverCommand implements Command {
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

		try {
			LOG.debug("start execute ");
			boolean isValidRecipe = PouroverRecipeValidator.getInstance().validatePouroverRecipeRequest(request);
			boolean isValidInfusion = InfusionArrayValidator.getInstance().validateInfusionsArray(request);

			// Validation
			if (isValidRecipe && isValidInfusion) {
				Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
				Integer coffeeTypeId = Integer.valueOf(request.getParameter("coffeeTypeId"));
				Recipe recipe = logRecipe.findRecipeByID(recipeId);

				if (recipe.isCommon()) {
					request.setAttribute("message", "Recipe.CantEditCommon");
					page = ("/jsp/menu.html");
					answer.setPage(page);
					answer.setRedirect(false);
					LOG.debug("all ok");
					return answer;
				}

				recipe.setCoffeeType(new CoffeeType(coffeeTypeId));

				PouroverRecipe pouroverRecipe = PouroverRecipeCreator.getInstance().createFromRequest(request,
						recipeId);
				boolean result = logPouroverRecipe.editPouroverRecipenInDB(pouroverRecipe);
				boolean resultUpdate = logRecipe.editRecipenInDB(recipe);
				if (result && resultUpdate) {

					List<Infusion> infusionsOld = logInfusion.takeInfusionsByRecipeID(recipeId);
					for (Infusion tmp : infusionsOld) {
						logInfusion.deleteInfusionInDB(tmp.getID());
					}
					List<Infusion> infusions = InfusionCreator.getInstance().createFromRequest(request, recipeId);
					for (int i = 0; i < infusions.size(); i++) {
						logInfusion.createInfusionInDB(infusions.get(i));
					}
					request.setAttribute("message", "RecipeWasEdited");
				} else {
					LOG.warn("recipe wasn't edited");
					request.setAttribute("message", "RecipeWasNotEdited");
				}
			} else {
				request.setAttribute("message", "RecipeWasNotEdited");
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
