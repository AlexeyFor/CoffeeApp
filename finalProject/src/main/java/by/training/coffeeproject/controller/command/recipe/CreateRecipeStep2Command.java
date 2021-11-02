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
import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.CoffeeTypeCreator;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.validator.CoffeeTypeValidator;
import by.training.coffeeproject.service.validator.ValidatorCommonMethods;

/**
 * @author AlexeySupruniuk
 * 
 *         create Recipe and CoffeType in database
 * 
 */
public class CreateRecipeStep2Command implements Command {
	private static final Logger LOG = LogManager.getLogger(CreateRecipeStep2Command.class);
	
	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
//		HttpSession session = request.getSession();

		ServiceFactory fct = ServiceFactory.getInstance();
		CoffeeTypeService logCoffeeType = fct.getCoffeeTypeService();

		InfusionCreator infCreator = InfusionCreator.getInstance();
		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();
		boolean wasCreated = Boolean.valueOf(request.getParameter("wasCreated"));
		boolean isValid = false;
//		LOG.debug("wasCreated " + wasCreated);

		try {
			if (wasCreated) {
				// Validation
				boolean isValidCoffeeType = CoffeeTypeValidator.getInstance().validateCoffeeTypeRequest(request);
				boolean isValidRecipeType = validator.checkRecipeType(request.getParameter("recipeType"));
				boolean isValidInfusions = validator.checkInfusionsNumber(request.getParameter("infusionsNumber"));
				isValid = isValidCoffeeType&&isValidRecipeType&&isValidInfusions;
				if (isValid) {
					// create CoffeeType
					CoffeeType coffeeType = CoffeeTypeCreator.getInstance().createFromRequest(request);
					//check, if such coffeeType exist in DB. If exist - set found ID
					Integer foundCoffeeTypeId = logCoffeeType.isExistingInDataBase(coffeeType);
					Integer coffeeTypeId;
					if (foundCoffeeTypeId!=0) {
//						LOG.debug("set existing coffeeType");
						coffeeTypeId = foundCoffeeTypeId;
						request.setAttribute("message", "SetExistingCoffeeType");

					} else {
						// create CoffeeType in DB
						coffeeTypeId = logCoffeeType.createCoffeeTypeInDataBase(coffeeType);
						request.setAttribute("message", "SetCreatedCoffeeType");
					}
					request.setAttribute("coffeeTypeId", coffeeTypeId);
				}
			} else {
				boolean isValidRecipeType = validator.checkRecipeType(request.getParameter("recipeType"));
				boolean isValidInfusions = validator.checkInfusionsNumber(request.getParameter("infusionsNumber"));
				isValid = isValidRecipeType&&isValidInfusions;
				if (isValid) {
					String coffeeTypeId = request.getParameter("coffeeTypeID");
					request.setAttribute("coffeeTypeId", coffeeTypeId);
					request.setAttribute("message", "SetExistingCoffeeType");			
				}
			}
		} catch (ServiceException e) {
			LOG.warn("catch " + e.getMessage());
			request.setAttribute("message", e.getMessage());
			page = ("/jsp/menu.html");
			answer.setPage(page);
			answer.setRedirect(false);
			return answer;

		}
		
		if (isValid) {
			String [] [] infusionsArray = infCreator.createInfusionsArray(request.getParameter("infusionsNumber"));
			request.setAttribute("infusionsArray", infusionsArray);
			request.setAttribute("infusionsNumber", request.getParameter("infusionsNumber"));

			RecipeType recipeType = RecipeType.valueOf(request.getParameter("recipeType"));
			switch (recipeType) {
			case POUROVER:
				List<FunnelType> funnelTypes = new ArrayList<>();
				Stream.of(FunnelType.values()).forEachOrdered(funnelTypes::add);
				request.setAttribute("funnelTypes", funnelTypes);
				page = ("/jsp/recipe/createRecipeStep2Pourover.html");
				break;
			case FRENCHPRESS:
				page = ("/jsp/recipe/createFrenchpressRecipe.html");
				break;
			}
		}else {
			request.setAttribute("message", "Message.NotCreate");
			page = ("/jsp/menu.html");
			answer.setPage(page);
			answer.setRedirect(false);
			return answer;
		}
		answer.setPage(page);
		answer.setRedirect(false); 
		return answer;
	}

}
