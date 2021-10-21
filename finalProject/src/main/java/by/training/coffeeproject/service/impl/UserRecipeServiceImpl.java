package by.training.coffeeproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.UserRecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.UserRecipe;
import by.training.coffeeproject.service.EntityTransactionLogic;

import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserRecipeService;

public class UserRecipeServiceImpl implements UserRecipeService {

private static final Logger LOG = LogManager.getLogger(UserRecipeServiceImpl.class);
	

	private UserRecipeServiceImpl() {
	}

	private static UserRecipeServiceImpl instance = new UserRecipeServiceImpl();

	public static UserRecipeServiceImpl getInstance() {
		return instance;
	}

	ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	
	@Override
	public Integer createUserRecipeInDB(UserRecipe userRecipe) throws ServiceException {
		LOG.debug("start createUserRecipeInDB");

		UserRecipeDao userRecipeDao = daoFabric.getUserRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userRecipeDao);
		Integer result = 0;

		try {
			result = userRecipeDao.create(userRecipe);
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

	@Override
	public boolean deleteUserRecipeInDB(Integer userId, Integer recipeId) throws ServiceException {
		LOG.debug("start createUserRecipeInDB");

		UserRecipeDao recipeDao = daoFabric.getUserRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao);
		boolean result = false;

		try {
			result = recipeDao.deleteWithTwoInt(userId, recipeId);
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
