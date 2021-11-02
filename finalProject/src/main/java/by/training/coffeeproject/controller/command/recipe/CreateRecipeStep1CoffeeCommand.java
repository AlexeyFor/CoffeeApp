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
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Only send data on page
 *
 */
public class CreateRecipeStep1CoffeeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CreateRecipeStep1CoffeeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		String page = null;
		ForwardRedirect answer = new ForwardRedirect();

		ServiceFactory fct = ServiceFactory.getInstance();
		CountryService logCountry = fct.getCountryService();
		CoffeeTypeService logCoffeeType = fct.getCoffeeTypeService();

		try {
			List<Country> countries = logCountry.findAllCountries();
			List<String> roasters = logCoffeeType.findAllRoasters();
			List<RoastDegree> roastDegrees = new ArrayList<>();
			Stream.of(RoastDegree.values()).forEachOrdered(roastDegrees::add);
			List<ProcessingMethod> processingMethods = new ArrayList<>();
			Stream.of(ProcessingMethod.values()).forEachOrdered(processingMethods::add);
			String recipeType = request.getParameter("recipeType");
//			LOG.debug("get recipeType " + recipeType);

			request.setAttribute("countries", countries);
			request.setAttribute("roasters", roasters);
			request.setAttribute("roastDegrees", roastDegrees);
			request.setAttribute("processingMethods", processingMethods);
			request.setAttribute("recipeType", recipeType);

		} catch (ServiceException e) {
			LOG.debug("error in  CreateRecipeCoffeeCommand ");
			request.setAttribute("error", "message.loginerror");
		}

		page = ("/jsp/recipe/createRecipeStep1Coffee.html");

		answer.setPage(page);
		answer.setRedirect(false);
		LOG.debug("all ok");
		return answer;
	}
}
