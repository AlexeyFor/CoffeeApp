package by.training.coffeeproject.service.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.recipe.CreateRecipeStep2Command;

import by.training.coffeeproject.service.ServiceException;

public class CoffeeTypeValidator {

	private static final Logger LOG = LogManager.getLogger(CoffeeTypeValidator.class);

	private static final CoffeeTypeValidator instance = new CoffeeTypeValidator();

	private CoffeeTypeValidator() {
	}

	public static CoffeeTypeValidator getInstance() {
		return instance;
	}

	/**
	 * Take all Parameters from HttpServletRequest, validate them, throw exception
	 * if there is any mistake. If parameter can be null (as in database) - valid
	 * with if. Valid all fields of CoffeeType
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @see CreateRecipeStep2Command
	 */
	public boolean validateCoffeeTypeRequest(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreateRecipeType");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		String roaster = request.getParameter("roaster");
		String countryID = request.getParameter("countries");
		String coffeeName = request.getParameter("CoffeeName");
		String roastDegree = request.getParameter("roastDegree");
		String processingMethod = request.getParameter("processingMethod");
		String arabicPercent = request.getParameter("ArabicPercent");
		String robustaPercent = request.getParameter("RobustaPercent");
		String information = request.getParameter("information");

		boolean condition2 = validator.checkRoaster(roaster);
		boolean condition3 = validator.checkCountryByID(countryID);
		boolean condition4 = validator.checkName(coffeeName);
		boolean condition7 = validator.checkPercents(arabicPercent, robustaPercent);

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

		return (condition2 && condition3 && condition4 && condition5 && condition6 && condition7
				&& condition8 );
	}

	/**
	 * valid infusionsNumber
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public boolean validateForCreateRecipeTypeSelected(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreateRecipeType ");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();
		String infusions = request.getParameter("infusionsNumber");
		boolean condition1 = validator.checkInfusionsNumber(infusions);

		return condition1;
	}
}