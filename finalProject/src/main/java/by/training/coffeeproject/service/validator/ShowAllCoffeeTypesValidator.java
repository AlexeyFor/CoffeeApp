package by.training.coffeeproject.service.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.ServiceException;

public class ShowAllCoffeeTypesValidator {

	private static final Logger LOG = LogManager.getLogger(ShowAllCoffeeTypesValidator.class);

	private static final ShowAllCoffeeTypesValidator instance = new ShowAllCoffeeTypesValidator();

	private ShowAllCoffeeTypesValidator() {
	}

	public static ShowAllCoffeeTypesValidator getInstance() {
		return instance;
	}

	/**
	 * Take all Parameters from HttpServletRequest, validate them, throw exception
	 * if there is any mistake.
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public boolean validateCoffeeTypeRequest(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreateRecipeType");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		String startPosition = request.getParameter("startPosition");
		String number = request.getParameter("number");
		String sortType = request.getParameter("sortType");

		boolean condition1 = validator.checkNumberPagination(startPosition);
		boolean condition2 = validator.checkNumberPagination(number);

		boolean condition3 = validator.checkEnum(sortType);
		boolean condition4 = validator.checkStartPaginationCoffeeType(startPosition);

		return (condition1 && condition2 && condition3 && condition4);
	}
}
