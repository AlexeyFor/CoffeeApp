package by.training.coffeeproject.controller.command.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.CoffeeTypeCreator;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.validator.CoffeeTypeValidator;
import by.training.coffeeproject.service.validator.ValidatorCommonMethods;

/**
 * @author AlexeySupruniuk
 * 
 *         Create new CoffeType in database. Checks if there is the same coffee
 *         in database. There can be three options: 1) New coffeType was
 *         created. coffeWasEdit - true, wasCreated - true 2) CoffeType was
 *         selected from existing. coffeWasEdit - true, wasCreated - false 3)
 *         Nothing was changing - neither coffeeType nor number of infusions.
 *         coffeWasEdit - false
 * 
 */
public class EditRecipeStep2Command implements Command {

	private static final Logger LOG = LogManager.getLogger(CreateRecipeStep2Command.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		CoffeeTypeService logCoffeeType = fct.getCoffeeTypeService();
		RecipeService logRecipe = fct.getRecipeService();
		InfusionCreator infCreator = InfusionCreator.getInstance();
		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		try {
			Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
			boolean wasCreated = Boolean.valueOf(request.getParameter("wasCreated"));
			boolean coffeWasEdit = Boolean.valueOf(request.getParameter("coffeWasEdit"));
			Recipe recipe = logRecipe.findRecipeByID(recipeId);
			boolean isValid = true;
			Integer coffeeTypeId;
			// if nothing has changed, move on
			if (coffeWasEdit) {
				if (wasCreated) {
					// Validation
					boolean isValidCoffeeType = CoffeeTypeValidator.getInstance().validateCoffeeTypeRequest(request);
					boolean isValidInfusions = validator.checkInfusionsNumber(request.getParameter("infusionsNumber"));
					isValid = isValidCoffeeType && isValidInfusions;

					if (isValid) {
						CoffeeType coffeeType = CoffeeTypeCreator.getInstance().createFromRequest(request);
						// check, if such coffeeType exist in DB. If exist - set found ID
						Integer foundCoffeeTypeId = logCoffeeType.isExistingInDataBase(coffeeType);
						if (foundCoffeeTypeId != 0) {
//							LOG.debug("set existing coffeeType");
							coffeeTypeId = foundCoffeeTypeId;
							request.setAttribute("message", "SetExistingCoffeeType");
						} else {
							// create CoffeeType in DB
							coffeeTypeId = logCoffeeType.createCoffeeTypeInDataBase(coffeeType);
							request.setAttribute("message", "SetCreatedCoffeeType");
						}
						request.setAttribute("coffeeTypeId", coffeeTypeId);
					}
					// if coffeeType was selected:
				} else {
					boolean isValidInfusions = validator.checkInfusionsNumber(request.getParameter("infusionsNumber"));
					isValid = isValidInfusions;
					if (isValid) {
						coffeeTypeId = Integer.valueOf(request.getParameter("coffeeTypeId"));
						request.setAttribute("message", "SetExistingCoffeeType");
						request.setAttribute("coffeeTypeId", coffeeTypeId);
					}
				}
			} else {
				coffeeTypeId = Integer.valueOf(request.getParameter("coffeeTypeId"));
				request.setAttribute("coffeeTypeId", coffeeTypeId);
				request.setAttribute("message", "CoffeeTypeWasNotChanged");
			}

			if (isValid) {
				Integer infusionsNumber = Integer.valueOf(request.getParameter("infusionsNumber"));
				request.setAttribute("infusionsNumber", infusionsNumber);

				RecipeType recipeType = recipe.getRecipeType();
				switch (recipeType) {
				case POUROVER:
					List<FunnelType> funnelTypes = new ArrayList<>();
					Stream.of(FunnelType.values()).forEachOrdered(funnelTypes::add);
					PouroverRecipe tmp = (PouroverRecipe) recipe;
					request.setAttribute("funnelTypes", funnelTypes);
					request.setAttribute("recipe", tmp);
					List<Infusion> infusions = tmp.getInfusions();
					request.setAttribute("infusionsArray",
							infCreator.createInfusionsArrayWithValues(infusionsNumber, infusions));

					page = ("/jsp/recipe/editRecipeStep2Pourover.html");
					break;
				case FRENCHPRESS:
					page = ("/jsp/recipe/editFrenchpressRecipe.html");
					request.setAttribute("recipe", (FrenchPressRecipe) recipe);
					break;
				}
			} else {
				request.setAttribute("message", "Message.NotUpdate");
				page = ("/jsp/menu.html");
				answer.setPage(page);
				answer.setRedirect(false);
				return answer;
			}
		} catch (ServiceException e) {
			request.setAttribute("message", e.getMessage());
			page = ("/jsp/menu.html");
			answer.setPage(page);
			answer.setRedirect(false);
			return answer;

		}
		answer.setPage(page);
		answer.setRedirect(false);
		LOG.debug("all ok");
		return answer;
	}
}
