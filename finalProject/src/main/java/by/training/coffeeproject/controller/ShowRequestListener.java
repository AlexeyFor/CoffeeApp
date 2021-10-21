package by.training.coffeeproject.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.ShowRequestListenerService;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Writes information about all viewed recipes in the file
 *
 */
@WebListener
public class ShowRequestListener implements ServletRequestListener {
	private static final Logger LOG = LogManager.getLogger(ShowRequestListener.class);
	private final String PAGE = "/jsp/recipe/showRecipe";
	private final String FILEADDRESS = "/WEB-INF/recipesViews/views.txt";


	public void requestDestroyed(ServletRequestEvent event) {
		LOG.debug("start requestDestroyed");

		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		LOG.debug("request");

		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if (uri!=null && uri!="") {
			int begin = contextPath.length();
			int end = uri.lastIndexOf('.');
			String commandName;
			LOG.debug("her1d");

			// get command name
			if (end > begin) {
				commandName = uri.substring(begin, end);
			} else {
				commandName = uri.substring(begin);
			}
			LOG.debug("commandName " + commandName);

			if (commandName.equals(PAGE)) {
				LOG.debug("write data from request");
				ShowRequestListenerService.getInstance().writeDataFromRequestInFile(request, FILEADDRESS);
//				Recipe recipe = (Recipe) request.getAttribute("recipe");
//				if (recipe!=null) {
//				Integer recipeId = recipe.getID();
//				RecipeType recipeType = recipe.getRecipeType();
//				HttpSession session = request.getSession();
//				Integer authorUserId = (Integer) session.getAttribute("ID");
//				
//				String path = request.getServletContext().getRealPath(FILEADDRESS);
//				
//				writeInFile (recipeId, recipeType, authorUserId, path);
				}
			}
		}
	

	}

//	private void writeInFile(Integer recipeId,RecipeType recipeType,Integer authorUserId, String path) {
//		
//		final String BETWEEN = "___";
//		File file = new File(path);
//		String savingStr =recipeId + BETWEEN + recipeType + BETWEEN + authorUserId + System.lineSeparator();
//		FileWriter writer = null;
//		try {
//			writer = new FileWriter(file, true);
////			writer.append(savingStr);
//			writer.write(savingStr);
//			writer.close();
//		} catch (IOException ex) {
//			LOG.warn("can't write data in file");
//		}
//	}
//}
