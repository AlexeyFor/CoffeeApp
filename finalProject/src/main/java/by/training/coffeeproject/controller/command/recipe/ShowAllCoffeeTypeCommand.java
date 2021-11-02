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
import by.training.coffeeproject.entity.SortType;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.creator.ShowAllCoffeeTypeArrayCreator;
import by.training.coffeeproject.service.validator.ShowAllCoffeeTypesValidator;

/**
 * @author AlexeySupruniuk
 * 
 *         Show All Coffee Type for create commands. With PAgination.
 * 
 */
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
			ShowAllCoffeeTypesValidator.getInstance().validateCoffeeTypeRequest(request);
		} catch (ServiceException e) {
			page = ("/jsp/recipe/showAllCoffeeType.html");
			request.setAttribute("errorMessage", e.getMessage());
			answer.setPage(page);
			answer.setRedirect(false);
			return answer;
		}

		try {
			Integer startPosition = Integer.valueOf(request.getParameter("startPosition"));
			Integer number = Integer.valueOf(request.getParameter("number"));
			String sortType = request.getParameter("sortType");
			String recipeType = request.getParameter("recipeType");
			List<SortType> sortTypesList = new ArrayList<>();
			Stream.of(SortType.values()).forEachOrdered(sortTypesList::add);
			List<CoffeeType> coffeeTypes = log.findSortedCoffeeTypePagination(startPosition, number, sortType);
			int[][] pagesArray = ShowAllCoffeeTypeArrayCreator.getInstance().createPagesArray(number);
			int startPositionSelected = (startPosition / 10) + 1;
//			LOG.debug("sortTypesList " + sortTypesList.toString());

			request.setAttribute("coffeeTypes", coffeeTypes);
			request.setAttribute("pagesArray", pagesArray);
			request.setAttribute("sortType", SortType.valueOf(sortType));
			request.setAttribute("recipeType", recipeType);
			request.setAttribute("sortTypesList", sortTypesList);
			request.setAttribute("startPositionSelected", startPositionSelected);

		} catch (ServiceException e) {
			LOG.debug("error in  ShowAllCoffeeTypeCommand " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		}

		page = ("/jsp/recipe/showAllCoffeeType.html");

		answer.setPage(page);
		answer.setRedirect(false);

		return answer;
	}

}
