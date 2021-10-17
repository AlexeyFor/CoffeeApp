package by.training.coffeeproject.controller.command.recipe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.command.Command;
import by.training.coffeeproject.controller.command.ForwardRedirect;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class ShowAllCoffeeTypeCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ShowAllCoffeeTypeCommand.class);

	@Override
	public ForwardRedirect execute(HttpServletRequest request) {
		LOG.debug("start execute ");
		String page = null;
		ForwardRedirect answer = new ForwardRedirect();

		ServiceFactory fct = ServiceFactory.getInstance();
		CoffeeTypeService log = fct.getCoffeeTypeService();
		try {
			List<CoffeeType> coffeeTypes = log.findAllCoffeeTypes();
			request.setAttribute("coffeeTypes", coffeeTypes);
			String recipeType = request.getParameter("recipeType");
			LOG.debug("get recipeType " + recipeType);
			request.setAttribute("recipeType", recipeType);

		} catch (ServiceException e) {
			LOG.debug("error in  ShowAllCoffeeTypeCommand ");
			request.setAttribute("error", "message.loginerror");
		}

		page = ("/jsp/recipe/showAllCoffeeType.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
