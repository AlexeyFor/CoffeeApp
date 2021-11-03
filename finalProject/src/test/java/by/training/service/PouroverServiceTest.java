package by.training.service;

import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Comment;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.RecipeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         All tests are using standart data from test database
 *
 */
public class PouroverServiceTest {

	private static final Logger LOG = LogManager.getLogger(PouroverServiceTest.class);

	private ServiceFactory srvFactory = ServiceFactory.getInstance();
	private PouroverRecipeService service = srvFactory.getPouroverRecipeService();
	private RecipeService serviceRecipe = srvFactory.getRecipeService();

	@BeforeTest
	private void intiPool() throws DaoException {
		String url = "jdbc:mysql://localhost/coffeeRecipesTEST";
		String propertiesPath = "resources\\databaseTEST.properties";
		int startSize = 6;
		int maxSize = 26;
		int checkConnectionTimeout = 3;
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
	}

	////////////////////////////////////
	// for takePouroverRecipeByID()
	@Test(description = "check pouroverRecipe  with id=1, all fields (including description) must be not null")
	public void findPouroverRecipeByIDTest1() throws ServiceException {

		PouroverRecipe pouroverRecipe = null;
		pouroverRecipe = service.takePouroverRecipeByID(1);
		boolean answer = false;
		if (pouroverRecipe != null) {
			LOG.debug(pouroverRecipe.toString());
			answer = checkFieldsForNull(pouroverRecipe);
		}
		assertTrue(answer);
	}

	// wrong ID
	@Test(description = "negative for findPouroverRecipeByID", enabled = true, expectedExceptions = ServiceException.class)
	public void findPouroverRecipeByIDTest1Negative() throws ServiceException {
		service.takePouroverRecipeByID(0);
	}

	// wrong recipe type
	@Test(description = "negative for findPouroverRecipeByID", enabled = true, expectedExceptions = ServiceException.class)
	public void findPouroverRecipeByIDTest2Negative() throws ServiceException {
		service.takePouroverRecipeByID(2);
	}
	///////////////////////////////////

	//////////////////////////////////
	// for unite
	private final static Recipe recipeTest = new PouroverRecipe(25, new CoffeeType(1), 1, true, new Date(),
			new ArrayList<Comment>(), RecipeType.POUROVER);
	private final static PouroverRecipe pouroverRecipeTest = new PouroverRecipe(25, "tmp", FunnelType.HARIOV60,
			(float) 26.0, (float) 15.0, "grind", 240, "not");
	private final static Recipe recipeTestDiffrentID = new PouroverRecipe(100, new CoffeeType(1), 1, true, new Date(),
			new ArrayList<Comment>(), RecipeType.POUROVER);
	private final static PouroverRecipe pouroverRecipeTestNotAllFields = new PouroverRecipe(25, null,
			FunnelType.HARIOV60, (float) 26.0, (float) 15.0, "grind", 240, "not");
	private final static Recipe frenchPressTest = new FrenchPressRecipe(25, new CoffeeType(1), 1, true, new Date(),
			new ArrayList<Comment>(), RecipeType.FRENCHPRESS);

	@Test(description = "positive for uniteTwoRecipes")
	public void uniteTwoRecipesTest1() throws ServiceException {

		PouroverRecipe pouroverRecipe = null;
		pouroverRecipe = service.uniteTwoRecipes(recipeTest, pouroverRecipeTest);
		boolean answer = false;
		if (pouroverRecipe != null) {
			LOG.debug(pouroverRecipe.toString());
			answer = checkAllFieldsForNull(pouroverRecipe);
		}
		assertTrue(answer);
	}

	// one of parameters is null
	@Test(description = "negative for uniteTwoRecipes", enabled = true, expectedExceptions = ServiceException.class)
	public void uniteTwoRecipesTestNegative1() throws ServiceException {
		service.uniteTwoRecipes(null, pouroverRecipeTest);
	}

	@Test(description = "negative for uniteTwoRecipes", enabled = true, expectedExceptions = ServiceException.class)
	public void uniteTwoRecipesTestNegative2() throws ServiceException {
		service.uniteTwoRecipes(null, null);
	}

	// different types
	@Test(description = "negative for uniteTwoRecipes", enabled = true, expectedExceptions = ServiceException.class)
	public void uniteTwoRecipesTestNegative5() throws ServiceException {
		service.uniteTwoRecipes(frenchPressTest, pouroverRecipeTest);
	}

	// differen ID
	@Test(description = "negative for uniteTwoRecipes", enabled = true, expectedExceptions = ServiceException.class)
	public void uniteTwoRecipesTestNegative3() throws ServiceException {
		service.uniteTwoRecipes(recipeTestDiffrentID, pouroverRecipeTest);
	}

	// not all fields
	@Test(description = "negative for uniteTwoRecipes", enabled = true, expectedExceptions = ServiceException.class)
	public void uniteTwoRecipesTestNegative4() throws ServiceException {
		service.uniteTwoRecipes(recipeTest, pouroverRecipeTestNotAllFields);
	}

	///////////////////////////

	//////////
	// for createPouroverRecipeInDB
	private final static Recipe createdRecipeTest = new PouroverRecipe(new CoffeeType(1), 1, false,
			RecipeType.POUROVER);

	private final static PouroverRecipe createdPouroverRecipeTest = new PouroverRecipe(25, "tmp", FunnelType.HARIOV60,
			(float) 26.0, (float) 15.0, "grind", 240, "not");

	@Test(description = "positive for createPouroverRecipeInDB")
	public void createPouroverRecipe1Test() throws ServiceException {
		// create recipe in DB
		Integer recipeID = serviceRecipe.createRecipeInDataBase(createdRecipeTest);
		PouroverRecipe pouroverRecipe = createdPouroverRecipeTest;
		// set his ID
		createdPouroverRecipeTest.setID(recipeID);
		// try to create
		int result = service.createPouroverRecipeInDB(pouroverRecipe);
		boolean answer = (result == 1);
		assertTrue(answer);
	}

	@Test(description = "negative for createPouroverRecipeInDB, set wrong ID")
	public void createPouroverRecipe1TestNegative() throws ServiceException {
		Integer recipeID = 0;
		PouroverRecipe pouroverRecipe = createdPouroverRecipeTest;
		// set his ID
		createdPouroverRecipeTest.setID(recipeID);
		// try to create
		int result = service.createPouroverRecipeInDB(pouroverRecipe);
		boolean answer = (result == 0);
		assertTrue(answer);
	}

	@Test(description = "negative for createPouroverRecipeInDB, null", enabled = true, expectedExceptions = ServiceException.class)
	public void createPouroverRecipe2TestNegative() throws ServiceException {
		PouroverRecipe pouroverRecipe = null;
		// set his ID
		// try to create
		int result = service.createPouroverRecipeInDB(pouroverRecipe);

	}

	@Test(description = "negative for createPouroverRecipeInDB, the same ID")
	public void createPouroverRecipe3TestNegative() throws ServiceException {
		PouroverRecipe pouroverRecipe = new PouroverRecipe(1, "tmp", FunnelType.HARIOV60, (float) 26.0, (float) 15.0,
				"grind", 240, "not");
		// try to create
		int result = service.createPouroverRecipeInDB(pouroverRecipe);
		boolean answer = (result == 0);
		assertTrue(answer);

	}
	///////////////

	//////////
	// for editPouroverRecipenInDB

	private final static PouroverRecipe editedPouroverRecipeTest = new PouroverRecipe(3, "tmp", FunnelType.HARIOV60,
			(float) 26.0, (float) 15.0, "grind", 240, "not");

	@Test(description = "positive for editPouroverRecipeInDB")
	public void editPouroverRecipe1Test() throws ServiceException {
		PouroverRecipe pouroverRecipe = editedPouroverRecipeTest;
		boolean answer = service.editPouroverRecipenInDB(pouroverRecipe);
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

	private boolean checkAllFieldsForNull(PouroverRecipe pouroverRecipe) {

		boolean answer = true;
		boolean condition1 = pouroverRecipe.getID() == null;
		boolean condition2 = pouroverRecipe.getFunnelType().getName() == null;
		boolean condition3 = pouroverRecipe.getRecipeName() == null;
		boolean condition4 = pouroverRecipe.getMassOfCoffee() == 0;
		boolean condition5 = pouroverRecipe.getGrindSettings() == 0;
		boolean condition6 = pouroverRecipe.getCoffeeGrinder() == null;
		boolean condition7 = pouroverRecipe.getTotalTime() == 0;
		boolean condition8 = pouroverRecipe.getDisription() == null;
		boolean condition9 = pouroverRecipe.getCoffeeType() == null;
		boolean condition10 = pouroverRecipe.getAuthorUserId() == 0;
		boolean condition11 = pouroverRecipe.getDateOfCreating() == null;
		boolean condition12 = pouroverRecipe.getRecipeType() == null;

		if (condition1 | condition2 | condition3 | condition4 | condition5 | condition6 | condition7 | condition8
				| condition9 | condition10 | condition11 | condition12) {
			answer = false;
		}

		return answer;
	}
}