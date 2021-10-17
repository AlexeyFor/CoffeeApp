package by.training.service;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.UserDao;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.dao.UserInfoDaoTest;

/**
 * 
 * @author AlexeySupruniuk
 * 
 * All tests are using standart data from sql/5_fill_tables_myProject.sql
 * @see file 5_fill_tables_myProject.sql
 *
 */
public class InfusionServiceTest {

	private static final Logger LOG = LogManager.getLogger(InfusionServiceTest.class);

	private ServiceFactory srvFactory = ServiceFactory.getInstance();
	private InfusionService service = srvFactory.getInfusionService();
	
	@BeforeTest
	private void intiPool() throws DaoException {
		String url = "jdbc:mysql://localhost/coffeeRecipes";
		String propertiesPath = "resources\\database.properties";
		int startSize = 6;
		int maxSize = 6;
		int checkConnectionTimeout = 3;
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
	}


	
	@Test(description = "check infusions if recipe with id=1, musqt return 3")
	public void findAllInfusionsTest1() throws ServiceException {

		List<Infusion> infusions = null;
			infusions = service.takeInfusionsByRecipeID(1);
			LOG.debug(infusions.toString());
	
		boolean answer = infusions.size() == 3;
		assertTrue(answer);
	}


	@Test(description = "check infusions if recipe with id=2, musqt return 1")
	public void findAllInfusionsTest2() throws ServiceException {

		List<Infusion> infusions = null;
			infusions = service.takeInfusionsByRecipeID(2);
//			LOG.debug(infusions.toString());
	
		boolean answer = infusions.size() == 1;
		assertTrue(answer);
	}

}
