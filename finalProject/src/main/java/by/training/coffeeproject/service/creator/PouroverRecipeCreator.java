package by.training.coffeeproject.service.creator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         only for creating PouroverRecipe object, validating in another class.
 *
 */
public class PouroverRecipeCreator {

	private static final Logger LOG = LogManager.getLogger(PouroverRecipeCreator.class);

	private static final PouroverRecipeCreator instance = new PouroverRecipeCreator();

	private PouroverRecipeCreator() {
	}

	public static PouroverRecipeCreator getInstance() {
		return instance;
	}

	/**
	 * Take all parameters from request. All data must be validated with
	 * PouroverRecipeValidator
	 * 
	 * @see CreateSaveRecipePouroverCommand
	 * @see PouroverRecipeValidator
	 * @param request
	 * @return CoffeeType
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public PouroverRecipe createFromRequest(HttpServletRequest request) throws ServiceException {
		LOG.debug("start createFromRequest ");

		try {
			Integer recipeId = Integer.valueOf(request.getParameter("recipeId"));
			String recipeName = request.getParameter("recipeName");
			FunnelType funnelType = FunnelType.valueOf(request.getParameter("funnelType"));
			Float massOfCoffee = Float.valueOf(request.getParameter("massOfCoffee"));
			Float grindSettings = Float.valueOf(request.getParameter("grindSettings"));
			String coffeeGrinder = request.getParameter("coffeeGrinder");
			Integer totalTime = Integer.valueOf(request.getParameter("totalTime"));
			String disription = request.getParameter("description");

			PouroverRecipe result = new PouroverRecipe(recipeId, recipeName, funnelType, massOfCoffee, grindSettings,
					coffeeGrinder, totalTime, disription);

			return result;
		} catch (NumberFormatException e) {
			LOG.warn("wrong number data");
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @param recipeId
	 * @return
	 * @throws ServiceException
	 */
	public PouroverRecipe createFromRequest(HttpServletRequest request, Integer recipeId) throws ServiceException {
		LOG.debug("start createFromRequest ");

		try {
			String recipeName = request.getParameter("recipeName");
			FunnelType funnelType = FunnelType.valueOf(request.getParameter("funnelType"));
			Float massOfCoffee = Float.valueOf(request.getParameter("massOfCoffee"));
			Float grindSettings = Float.valueOf(request.getParameter("grindSettings"));
			String coffeeGrinder = request.getParameter("coffeeGrinder");
			Integer totalTime = Integer.valueOf(request.getParameter("totalTime"));
			String disription = request.getParameter("description");

			PouroverRecipe result = new PouroverRecipe(recipeId, recipeName, funnelType, massOfCoffee, grindSettings,
					coffeeGrinder, totalTime, disription);

			return result;
		} catch (NumberFormatException e) {
			LOG.warn("wrong number data");
			throw new ServiceException(e.getMessage());
		}
	}

}
