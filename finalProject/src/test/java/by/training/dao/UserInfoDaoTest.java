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
import by.training.coffeeproject.dao.UserInfoDao;
import by.training.coffeeproject.dao.impl.UserInfoDaoImpl;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.UserInfo;

public class UserInfoDaoTest {

	private static final Logger LOG = LogManager.getLogger(UserInfoDaoTest.class);
	private UserInfoDao userInfoDao = DaoFabric.getInstance().getUserInfoDao();
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
			transaction.initTransactionInterface(userInfoDao);
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

	@Test(description = "take all UserInfo from DB")
	public void findAllPositiveTest() throws DaoException {

		List<UserInfo> users = null;
		try {
			users = userInfoDao.findAll();
			LOG.debug(users.toString());
			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
		}
		boolean answer = checkListForNull(users);
		assertTrue(answer);
	}

	@Test(description = "take UserInfo from DB by ID")
	public void findByIdPositive1Test() throws DaoException {

		List<UserInfo> users = new ArrayList<>();
		try {
			users.add(userInfoDao.findEntityById(2));

			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
		}
		boolean answer = checkListForNull(users);
		assertTrue(answer);
	}

//	@Test(description = "create new UserInfo in DB ")
//	public void createPositiveTest() throws DaoException {
//		boolean answer = false;
//		UserInfo userInfo = new UserInfo();
//		userInfo.setName("tmpName");
//		userInfo.setEmail("email");	
//		userInfo.setCountry(new Country (4));
//		userInfo.setStoragePath("path");
//		try {
//			answer = userInfoDao.create (userInfo);
//
//			transaction.commit();
//		} catch (DaoException e) {
//			transaction.rollback();
//		}
//	
//		assertTrue(answer);
//	}

	private boolean checkListForNull(List<UserInfo> list) {

		boolean answer = true;
		boolean condition1;
		boolean condition2;
		boolean condition3;
		boolean condition4;
		boolean condition5;

		for (int i = 0; i < list.size(); i++) {
			condition1 = (list.get(i).getID() == null);
			condition2 = (list.get(i).getEmail() == null);
			condition3 = (list.get(i).getName() == null);
			condition4 = (list.get(i).getCountry().getID() == null);
			condition5 = (list.get(i).getStoragePath() == null);

			if (condition1 | condition2 | condition3 | condition4 | condition5) {
				answer = false;
				break;
			}
		}
		return answer;
	}
}
