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

		if (userRecipe == null) {
			LOG.error("can't create userRecipe, null");
			throw new ServiceException("can't create userRecipe, null");
		}

		UserRecipeDao userRecipeDao = daoFabric.getUserRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userRecipeDao);
		Integer result = 0;

		try {
			result = userRecipeDao.create(userRecipe);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.warn("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		} finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.error("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return result;
	}

	@Override
	public boolean deleteUserRecipeInDB(Integer userId, Integer recipeId) throws ServiceException {
		LOG.debug("start createUserRecipeInDB");

		if (userId == null || recipeId == null) {
			LOG.error("can't delete userRecipe, null");
			throw new ServiceException("can't delete userRecipe, null");
		}

		UserRecipeDao recipeDao = daoFabric.getUserRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao);
		boolean result = false;

		try {
			result = recipeDao.deleteWithTwoInt(userId, recipeId);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.warn("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		} finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.error("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return result;
	}

	@Override
	public boolean checkExists(Integer userId, Integer recipeId) throws ServiceException {
		LOG.debug("start createUserRecipeInDB");

		if (userId == null || recipeId == null) {
			LOG.error("can't check userRecipe, null");
			throw new ServiceException("can't check userRecipe, null");
		}

		UserRecipeDao recipeDao = daoFabric.getUserRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao);
		boolean result = false;

		try {
			result = recipeDao.checkExists(userId, recipeId);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.warn("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		} finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.error("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());
			}
		}
		return result;
	}

}
