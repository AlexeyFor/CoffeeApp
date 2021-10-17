package by.training.dao;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import by.training.coffeeproject.dao.impl.UserDaoImpl;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.entity.UserInfo;
import by.training.coffeeproject.entity.Role;

public class UserDaoTest {
	private static final Logger LOG = LogManager.getLogger(UserInfoDaoTest.class);
	private UserDao userDao = DaoFabric.getInstance().getUserDao();
	private EntityTransaction transaction;

	@BeforeTest
	private void intiPool() throws DaoException {
		String url = "jdbc:mysql://localhost/coffeeRecipes";
		String propertiesPath = "resources\\database.properties";
		int startSize = 6;
		int maxSize = 6;
		int checkConnectionTimeout = 3;
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
	}

	@BeforeMethod
	private void initTransaction() {
		transaction = new EntityTransaction();
		try {
			transaction.initTransactionInterface(userDao);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
	}

	@AfterMethod
	private void closeTransaction() {
		try {
			transaction.endTransaction();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "take all User from DB")
	public void findAllPositiveTest() throws DaoException {

		List<User> users = null;
		try {
			users = userDao.findAll();
			LOG.debug(users.toString());
			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
		}
		boolean answer = checkListForNull(users);
		assertTrue(answer);
	}

	@Test(description = "take User from DB by ID")
	public void findByIdPositive1Test() throws DaoException {

		List<User> users = new ArrayList<>();
		try {
			users.add(userDao.findEntityById(1));

			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
		}
		boolean answer = checkListForNull(users);
		assertTrue(answer);
	}

	@Test(description = "create new User in DB ")
	public void createPositiveTest() throws DaoException {
		Integer answer = 0;
		User user = new User();
		user.setLogin("tmpName");
		user.setPassword("pass");
		user.setRole(Role.valueOf("USER"));
		try {
			answer = userDao.create(user);

			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
		}

		
		assertTrue(answer!=0);
	}

	private boolean checkListForNull(List<User> list) {

		boolean answer = true;
		boolean condition1;
		boolean condition2;
		boolean condition3;
		boolean condition4;

		for (int i = 0; i < list.size(); i++) {
			condition1 = (list.get(i).getID() == null);
			condition2 = (list.get(i).getLogin() == null);
			condition3 = (list.get(i).getPassword() == null);
			condition4 = (list.get(i).getRole() == null);

			if (condition1 | condition2 | condition3 | condition4) {
				answer = false;
				break;
			}
		}
		return answer;
	}
}
