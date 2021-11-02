package by.training.coffeeproject.service.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.ServiceException;

public class PouroverRecipeValidator {

	private static final Logger LOG = LogManager.getLogger(PouroverRecipeValidator.class);

	private static final PouroverRecipeValidator instance = new PouroverRecipeValidator();

	private PouroverRecipeValidator() {
	}

	public static PouroverRecipeValidator getInstance() {
		return instance;
	}

	/**
	 * Take all Parameters from HttpServletRequest, validate them, throw exception
	 * if there is any mistake. If parameter can be null (as in database)- valid
	 * with if.
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @see CreateSaveRecipePouroverCommand
	 */
	public boolean validatePouroverRecipeRequest(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateForCreatePouroverRecipe");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		String recipeName = request.getParameter("recipeName");
		String funnelType = request.getParameter("funnelType");
		String massOfCoffee = request.getParameter("massOfCoffee");
		String grindSettings = request.getParameter("grindSettings");
		String coffeeGrinder = request.getParameter("coffeeGrinder");
		String totalTime = request.getParameter("totalTime");
		String description = request.getParameter("description");

		boolean condition1 = validator.checkName(recipeName);
		boolean condition2 = validator.checkFunnelType(funnelType);
		boolean condition3 = validator.checkMassOfCoffee(massOfCoffee);
		boolean condition4 = validator.checkGrindSettings(grindSettings);
		boolean condition5 = validator.checkCoffeeGrinder(coffeeGrinder);
		boolean condition6 = validator.checkTotalTime(totalTime);

		boolean condition7;
		if (description != null && description != "") {
			condition7 = validator.checkInformation(description);
		} else {
			condition7 = true;
		}

		return (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7);
	}

}
