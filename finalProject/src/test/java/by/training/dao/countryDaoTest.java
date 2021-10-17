package by.training.dao;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.training.coffeeproject.dao.CountryDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.impl.CountryDaoImpl;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.Country;

public class countryDaoTest {

	private CountryDao countryDao = DaoFabric.getInstance().getCountryDao();
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
			transaction.initTransactionInterface(countryDao);
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

	@Test(description = "take all countries from DB")
	public void findAllPositiveTest() throws DaoException {

		List<Country> countries = null;
		try {
			countries = countryDao.findAll();
			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
			System.out.println("mistake");
		}
		boolean answer = checkListForNull(countries);
		assertTrue(answer);
	}

	@Test(description = "take country from DB by ID")
	public void findByIdPositive1Test() throws DaoException {

		List<Country> countries = new ArrayList<>();
		try {
			countries.add(countryDao.findEntityById(2));

			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
			System.out.println("mistake");
		}
		boolean answer = checkListForNull(countries);
		assertTrue(answer);
	}

	@Test(description = "take countries from DB by ID")
	public void findByIdPositiv2eTest() throws DaoException {

		List<Country> countries = new ArrayList<>();
		try {
			countries.add(countryDao.findEntityById(2));
			countries.add(countryDao.findEntityById(1));
			countries.add(countryDao.findEntityById(3));
			countries.add(countryDao.findEntityById(4));
			countries.add(countryDao.findEntityById(2));

			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
			System.out.println("mistake");
		}
		boolean answer = checkListForNull(countries);
		assertTrue(answer);
	}

	private boolean checkListForNull(List<Country> list) {

		boolean answer = true;
		boolean condition1;
		boolean condition2;

		for (int i = 0; i < list.size(); i++) {
			condition1 = (list.get(i).getID() == null);
			condition2 = (list.get(i).getCountryName() == null);
			if (condition1 | condition2) {
				answer = false;
				break;
			}
		}
		return answer;
	}

}
