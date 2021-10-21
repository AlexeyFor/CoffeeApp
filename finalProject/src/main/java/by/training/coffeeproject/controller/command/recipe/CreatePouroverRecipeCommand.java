package by.training.coffeeproject.controller.command.recipe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;

import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;

import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.InfusionCreator;
import by.training.coffeeproject.service.creator.PouroverRecipeCreator;
import by.training.coffeeproject.service.validator.InfusionArrayValidator;
import by.training.coffeeproject.service.validator.PouroverRecipeValidator;

public class CreatePouroverRecipeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CreatePouroverRecipeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");

		String page = null;
		ForwardRedirect answer = new ForwardRedirect();
		ServiceFactory fct = ServiceFactory.getInstance();
		InfusionService logInfusion = fct.getInfusionService();
		PouroverRecipeService logRecipe = fct.getPouroverRecipeService();
		

		try {
			LOG.debug("start execute ");
			boolean isValidRecipe = PouroverRecipeValidator.getInstance().validateForCreatePouroverRecipe(request);
			LOG.debug("1");
			boolean isValidInfusion = InfusionArrayValidator.getInstance().validateInfusionsArray(request);

			// Validation
			if (isValidRecipe && isValidInfusion) {
				// create PouroverRecipe
				LOG.debug("2");
				PouroverRecipe pouroverRecipe = PouroverRecipeCreator.getInstance().createFromRequest(request);
				LOG.debug("3");
				// create CoffeeType in DB
				Integer result = logRecipe.createPouroverRecipeInDB(pouroverRecipe);
				LOG.debug("4");
				if (result == 1) {
					LOG.debug("5");
					List <Infusion> infusions = InfusionCreator.getInstance().createFromRequest(request);
					LOG.debug("6");
					for (int i = 0; i < infusions.size(); i++) {
						LOG.debug("7");
						logInfusion.createInfusionInDB(infusions.get(i));
						LOG.debug("8");
					}
				} else {
					LOG.debug( "recipe wasn't created");
					request.setAttribute("message", "recipe wasn't created");
				}
				request.setAttribute("message", "DAAAAAAABL");
			}

		} catch (ServiceException e) {
			LOG.error(e.getMessage());
			request.setAttribute("message", e.getMessage());
		}
		page = ("/jsp/menu.html");
		LOG.debug("9");

		
		answer.setPage(page);
		answer.setRedirect(false);
		LOG.debug("all ok");
		return answer;
	}
}
