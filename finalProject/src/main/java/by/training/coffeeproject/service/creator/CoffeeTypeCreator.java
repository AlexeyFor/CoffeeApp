package by.training.coffeeproject.service.creator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.recipe.CreateRecipeStep1CoffeeCommand;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.validator.CoffeeTypeValidator;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         only for creating CoffeeType object, validating in another class.
 *
 */
public class CoffeeTypeCreator {

	private static final Logger LOG = LogManager.getLogger(CoffeeTypeCreator.class);

	private static final CoffeeTypeCreator instance = new CoffeeTypeCreator();

	private CoffeeTypeCreator() {
	}

	public static CoffeeTypeCreator getInstance() {
		return instance;
	}

	/**
	 * Take all parameters from request. All data must be validated with
	 * CoffeeTypeValidator
	 * 
	 * @see CreateRecipeStep1CoffeeCommand
	 * @see CoffeeTypeValidator
	 * @param request
	 * @return CoffeeType
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public CoffeeType createFromRequest(HttpServletRequest request) throws ServiceException {
		LOG.debug("start createFromRequest ");

		try {
			String roaster = request.getParameter("roaster");
			Country country = takeCountryByID(Integer.valueOf(request.getParameter("countries")));
			String name = request.getParameter("CoffeeName");
			RoastDegree roastDegree = RoastDegree.valueOf(request.getParameter("roastDegree"));
			ProcessingMethod processingMethod = ProcessingMethod.valueOf(request.getParameter("processingMethod"));
			Integer arabicPercent = Integer.valueOf(request.getParameter("ArabicPercent"));
			Integer robustaPercent = Integer.valueOf(request.getParameter("RobustaPercent"));
			String information = request.getParameter("information");
			CoffeeType result = new CoffeeType(name, country, arabicPercent, robustaPercent, processingMethod, roaster,
					roastDegree, information);

			return result;
		} catch (NumberFormatException e) {
			LOG.error("wrong number data");
			throw new ServiceException(e.getMessage());
		}
	}

	private Country takeCountryByID(Integer id) throws ServiceException {
		ServiceFactory fct = ServiceFactory.getInstance();
		CountryService logCountry = fct.getCountryService();
		Country country = logCountry.findCountryByID(id);
		return country;
	}
}
