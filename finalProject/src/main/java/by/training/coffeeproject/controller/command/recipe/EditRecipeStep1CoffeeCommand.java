package by.training.coffeeproject.controller.command.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Only send data on page
 */
public class EditRecipeStep1CoffeeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CreateRecipeStep2Command.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		String page = null;
		ForwardRedirect answer = new ForwardRedirect();

		ServiceFactory fct = ServiceFactory.getInstance();
		CountryService logCountry = fct.getCountryService();
		CoffeeTypeService logCoffeeType = fct.getCoffeeTypeService();
		RecipeService logRecipe = fct.getRecipeService();

		try {
			List<Country> countries = logCountry.findAllCountries();
			List<String> roasters = logCoffeeType.findAllRoasters();
			List<RoastDegree> roastDegrees = new ArrayList<>();
			Stream.of(RoastDegree.values()).forEachOrdered(roastDegrees::add);
			List<ProcessingMethod> processingMethods = new ArrayList<>();
			Stream.of(ProcessingMethod.values()).forEachOrdered(processingMethods::add);
			Recipe recipe = logRecipe.findRecipeByID(Integer.valueOf(request.getParameter("recipeId")));
			String[] infusionsArray = (request.getParameterValues("infusionsNumber"));
			Integer infusionsNumber = Integer.valueOf(infusionsArray[0]);

//			LOG.debug("get infusions length" + infusionsNumber);
//			

//			LOG.debug("get infusions " + Arrays.toString(infusionsArr ));
//			LOG.debug("get infusions length" + infusionsArr.length);

			request.setAttribute("countries", countries);
			request.setAttribute("roasters", roasters);
			request.setAttribute("roastDegrees", roastDegrees);
			request.setAttribute("processingMethods", processingMethods);
			request.setAttribute("recipeType", recipe.getRecipeType());
			request.setAttribute("coffeeType", recipe.getCoffeeType());
			request.setAttribute("infusionsNumber", infusionsNumber);
			request.setAttribute("recipeId", recipe.getID());

		} catch (ServiceException e) {
			LOG.warn("error in  CreateRecipeCoffeeCommand ");
			request.setAttribute("error", "message.loginerror");
		}

		page = ("/jsp/recipe/editRecipeStep1Coffee.html");

		answer.setPage(page);
		answer.setRedirect(false);
		return answer;
	}
}
