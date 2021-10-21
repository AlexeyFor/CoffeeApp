package by.training.coffeeproject.service.creator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import by.training.coffeeproject.entity.UserRecipe;
import by.training.coffeeproject.service.ServiceException;

public class UserRecipeCreator {

	private static final Logger LOG = LogManager.getLogger(UserRecipeCreator.class);

	private static final UserRecipeCreator instance = new UserRecipeCreator();

	private UserRecipeCreator() {
	}

	public static UserRecipeCreator getInstance() {
		return instance;
	}

	public UserRecipe createFromRequest(HttpServletRequest request, Integer recipeId) throws ServiceException {
		LOG.debug("start createFromRequest ");

		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("ID");

		UserRecipe result = new UserRecipe(userId, recipeId);

		return result;

	}
}
