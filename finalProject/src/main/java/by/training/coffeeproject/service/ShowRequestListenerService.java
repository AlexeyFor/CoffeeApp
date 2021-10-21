package by.training.coffeeproject.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.WriteInFileDao;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;

public class ShowRequestListenerService {

	private static final Logger LOG = LogManager.getLogger(ShowRequestListenerService.class);
	private final String BETWEEN = "___";

	private ShowRequestListenerService() {
	}

	private static ShowRequestListenerService instance = new ShowRequestListenerService();

	public static ShowRequestListenerService getInstance() {
		return instance;
	}
	
	public void writeDataFromRequestInFile(HttpServletRequest request, String fileAddress) {
		LOG.debug("write data from request");

		Recipe recipe = (Recipe) request.getAttribute("recipe");
		if (recipe != null) {
			Integer recipeId = recipe.getID();
			RecipeType recipeType = recipe.getRecipeType();
			HttpSession session = request.getSession();
			Integer userId = (Integer)session.getAttribute("ID");

			String path = request.getServletContext().getRealPath(fileAddress);
			String result = concatStr (recipeId, recipeType, userId);
			WriteInFileDao.getInstance().writeInFile(result, path);
		}
	}
	
	private String concatStr (Integer recipeId, RecipeType recipeType,Integer userId) {
		String savingStr =recipeId + BETWEEN + recipeType + BETWEEN + userId + System.lineSeparator();
		return savingStr;
	}
}