package by.training.coffeeproject.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.CoffeeTypeDao;
import by.training.coffeeproject.dao.CountryDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.RecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.ServiceException;

public class CountryServiceImpl implements CountryService {

	private static final Logger LOG = LogManager.getLogger(CountryServiceImpl.class);

	private CountryServiceImpl() {
	}

	private static CountryServiceImpl instance = new CountryServiceImpl();

	public static CountryServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public List<Country> findAllCountries() throws ServiceException {
		LOG.debug("start findAllCountries");

		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(countryDao);
		List<Country> countries = null;

		try {
			countries = countryDao.findAll();
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}
		return countries;
	}

	@Override
	public boolean CountryExist(Integer id) throws ServiceException {
		LOG.debug("start CountryExist");

		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(countryDao);
		Country country = null;

		try {
			country = countryDao.findEntityById(id);
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}
		if (country != null) {
			return true;
		} else {
			return false;

		}
	}

	@Override
	public Country findCountryByID(Integer id) throws ServiceException {
		LOG.debug("start findCountryByID");

		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(countryDao);
		Country country = null;

		try {
			country = countryDao.findEntityById(id);
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}

		return country;
	}

}
