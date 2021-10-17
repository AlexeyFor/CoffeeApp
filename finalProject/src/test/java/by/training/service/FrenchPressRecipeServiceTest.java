package by.training.service;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.service.FrenchPressRecipeService;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 * All tests are using standart data from sql/5_fill_tables_myProject.sql
 * @see file 5_fill_tables_myProject.sql
 *
 */
public class FrenchPressRecipeServiceTest {

	private static final Logger LOG = LogManager.getLogger(FrenchPressRecipeServiceTest.class);

	private ServiceFactory srvFactory = ServiceFactory.getInstance();
	private FrenchPressRecipeService service = srvFactory.getFrenchPressRecipeService();

	@BeforeTest
	private void intiPool() throws DaoException {
		String url = "jdbc:mysql://localhost/coffeeRecipes";
		String propertiesPath = "resources\\database.properties";
		int startSize = 6;
		int maxSize = 6;
		int checkConnectionTimeout = 3;
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
	}
	
	@Test(description = "check frenchPressRecipe  with id=2, all fields (including description) must be not null")
	public void findFrenchPressRecipeByIDTest1() throws ServiceException {

		FrenchPressRecipe frenchPressRecipe = null;
		frenchPressRecipe = service.takeFrenchPressRecipeByID(2);
			boolean answer = false;
			if (frenchPressRecipe!= null) {
				LOG.debug(frenchPressRecipe.toString());
				answer = checkFieldsForNull (frenchPressRecipe);
			}
		assertTrue(answer);
	}
	
	private boolean checkFieldsForNull(FrenchPressRecipe frenchPressRecipe) {

		boolean answer = true;
		boolean condition1 = frenchPressRecipe.getID() == null;
		boolean condition2 = frenchPressRecipe.getRecipeName() == null;
		boolean condition3 = frenchPressRecipe.getFrechPressVolume() == 0;
		boolean condition4 = frenchPressRecipe.getMassOfCoffee() == 0;
		boolean condition5 = frenchPressRecipe.getGrindSettings() == 0;
		boolean condition6 = frenchPressRecipe.getCoffeeGrinder() == null;
		boolean condition7 = frenchPressRecipe.getCapBreakingTime() == 0;
		boolean condition8 = frenchPressRecipe.getPlungerLoweringTime() == 0;
		boolean condition9 = frenchPressRecipe.getTotalTime() == 0;
		boolean condition10 = frenchPressRecipe.getDisription() == null;

		if (condition1 | condition2 | condition3 | condition4 | condition5 
				|condition6 | condition7|condition8 | condition9 | condition10) {
			answer = false;
		}

		return answer;
	}
}
