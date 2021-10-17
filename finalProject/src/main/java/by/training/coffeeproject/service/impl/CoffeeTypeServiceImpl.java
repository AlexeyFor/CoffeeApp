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
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.ServiceException;

public class CoffeeTypeServiceImpl implements CoffeeTypeService {

	private static final Logger LOG = LogManager.getLogger(CoffeeTypeServiceImpl.class);

	private CoffeeTypeServiceImpl() {
	}

	private static CoffeeTypeServiceImpl instance = new CoffeeTypeServiceImpl();

	public static CoffeeTypeServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public List<String> findAllRoasters() throws ServiceException {
		LOG.debug("start findAllRoasters");

		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(coffeeTypeDao);
		List<String> roasters = null;

		try {
			roasters = coffeeTypeDao.findAllRoasters();
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}
		return roasters;
	}
	
	public List <CoffeeType> findAllCoffeeTypes() throws ServiceException{
		
		LOG.debug("start findAllCommonRecipes");

		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(coffeeTypeDao, countryDao);
		List<CoffeeType> coffeeTypes = null;

		try {
			coffeeTypes = coffeeTypeDao.findAll();
			if (coffeeTypes != null) {
				for (int i = 0; i < coffeeTypes.size(); i++) {
					CoffeeType tmpCoffeeType = coffeeTypes.get(i);
					
					tmpCoffeeType.setCountry(countryDao.findEntityById(tmpCoffeeType.getCountry().getID()));
					}
				}
			
//			LOG.debug(coffeeTypes.toString());

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
		return coffeeTypes;
	}


	@Override
	public Integer createCoffeeTypeInDataBase(CoffeeType coffeeType) throws ServiceException {
		LOG.debug("start createCoffeeTypeInDataBase");

		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(coffeeTypeDao);
		Integer result = 0;

		try {
			result = coffeeTypeDao.create(coffeeType);
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}
		return result;
	}

}
