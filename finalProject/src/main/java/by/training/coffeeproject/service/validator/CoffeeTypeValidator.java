package by.training.coffeeproject.service.validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.recipe.CreateRecipeTypeCommand;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class CoffeeTypeValidator {

	private static final Logger LOG = LogManager.getLogger(CoffeeTypeValidator.class);

	private static final CoffeeTypeValidator instance = new CoffeeTypeValidator();

	private CoffeeTypeValidator() {
	}

	public static CoffeeTypeValidator getInstance() {
		return instance;
	}

	/**
	 * Only for CreateRecipeTypeCommand. Take all Parameters from
	 * HttpServletRequest, validate them, throw exception if there is any mistake.
	 * If parapmetec can be null - valid with if.
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @see CreateRecipeTypeCommand
	 */
	public boolean validateForCreateRecipeType(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreateRecipeType ");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		String infusions = request.getParameter("infusions");
		String roaster = request.getParameter("roaster");
		String countryID = request.getParameter("countries");
		String coffeeName = request.getParameter("CoffeeName");
		String roastDegree = request.getParameter("roastDegree");
		String processingMethod = request.getParameter("processingMethod");
		String arabicPercent = request.getParameter("ArabicPercent");
		String robustaPercent = request.getParameter("RobustaPercent");
		String information = request.getParameter("information");
		String recipeType = request.getParameter("recipeType");

		boolean condition1 = validator.checkInfusions(infusions);
		boolean condition2 = validator.checkRoaster(roaster);
		boolean condition3 = validator.checkCountryByID(countryID);
		boolean condition4 = validator.checkName(coffeeName);
		boolean condition7 = validator.checkPercents(arabicPercent, robustaPercent);
		boolean condition9 = validator.checkRecipeType(recipeType);

		boolean condition5;
		if (roastDegree != null) {
			condition5 = validator.checkRoastDegree(roastDegree);
		} else {
			condition5 = true;
		}

		boolean condition6;
		if (roastDegree != null) {
			condition6 = validator.checkProcessingMethod(processingMethod);
		} else {
			condition6 = true;
		}

		boolean condition8;
		if (information != null && information != "") {
			condition8 = validator.checkInformation(information);
		} else {
			condition8 = true;
		}

		return (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7
				&& condition8 && condition9);
	}

	public boolean validateForCreateRecipeTypeSelected(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreateRecipeType ");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();
		String infusions = request.getParameter("infusions");
		boolean condition1 = validator.checkInfusions(infusions);

		return condition1;
	}
}