package by.training.service;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.InfusionService;
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
public class PouroverServiceTest {

	private static final Logger LOG = LogManager.getLogger(PouroverServiceTest.class);

	private ServiceFactory srvFactory = ServiceFactory.getInstance();
	private PouroverRecipeService service = srvFactory.getPouroverRecipeService();

	@BeforeTest
	private void intiPool() throws DaoException {
		String url = "jdbc:mysql://localhost/coffeeRecipes";
		String propertiesPath = "resources\\database.properties";
		int startSize = 6;
		int maxSize = 6;
		int checkConnectionTimeout = 3;
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
	}
	
	@Test(description = "check pouroverRecipe  with id=1, all fields (including description) must be not null")
	public void findPouroverRecipeByIDTest1() throws ServiceException {

		PouroverRecipe pouroverRecipe = null;
		pouroverRecipe = service.takePouroverRecipeByID(1);
			boolean answer = false;
			if (pouroverRecipe!= null) {
				LOG.debug(pouroverRecipe.toString());
				answer = checkFieldsForNull (pouroverRecipe);
			}
		assertTrue(answer);
	}

	private boolean checkFieldsForNull(PouroverRecipe pouroverRecipe) {

		boolean answer = true;
		boolean condition1 = pouroverRecipe.getID() == null;
		boolean condition2 = pouroverRecipe.getFunnelType().getName() == null;
		boolean condition3 = pouroverRecipe.getRecipeName() == null;
		boolean condition4 = pouroverRecipe.getMassOfCoffee() == 0;
		boolean condition5 = pouroverRecipe.getGrindSettings() == 0;
		boolean condition6 = pouroverRecipe.getCoffeeGrinder() == null;
		boolean condition7 = pouroverRecipe.getTotalTime() == 0;
		boolean condition8 = pouroverRecipe.getDisription() == null;

		if (condition1 | condition2 | condition3 | condition4 | condition5 | condition6 | condition7 | condition8) {
			answer = false;
		}

		return answer;
	}
}