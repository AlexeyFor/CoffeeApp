package by.training.coffeeproject.service.creator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         only for creating CoffeeRecipe object, validating in another class.
 *
 */
public class CoffeeRecipeCreator {

	private static final Logger LOG = LogManager.getLogger(CoffeeRecipeCreator.class);

	private static final CoffeeRecipeCreator instance = new CoffeeRecipeCreator();

	private CoffeeRecipeCreator() {
	}

	public static CoffeeRecipeCreator getInstance() {
		return instance;
	}

	/**
	 * Take all parameters from request (except CoffeeTypeID). For command
	 * CreateRecipeCoffeeCommand.
	 * 
	 * SET COMMON AS FALSE!
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public Recipe createFromRequest(HttpServletRequest request, CoffeeType coffeeType) throws ServiceException {
		LOG.debug("start createFromRequest ");
		HttpSession session = request.getSession();
		RecipeType recipeType = RecipeType.valueOf(request.getParameter("recipeType"));
		Integer authorUserId = (Integer) session.getAttribute("ID");

		boolean common = false;
		Recipe result;

		switch (recipeType) {
		case POUROVER:
			result = new PouroverRecipe(coffeeType, authorUserId, common, recipeType);
			return result;

		case FRENCHPRESS:
			result = new FrenchPressRecipe(coffeeType, authorUserId, common, recipeType);
			return result;

		default:
			throw new ServiceException("wrong_type");
		}

	}

}
