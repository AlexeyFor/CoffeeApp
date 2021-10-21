package by.training.coffeeproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.CoffeeTypeDao;
import by.training.coffeeproject.dao.CountryDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.FrenchPressRecipeDao;
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.dao.PouroverRecipeDao;
import by.training.coffeeproject.dao.RecipeDao;
import by.training.coffeeproject.dao.UserDao;
import by.training.coffeeproject.dao.UserInfoDao;
import by.training.coffeeproject.dao.UserRecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.service.*;

public class RecipeServiceImpl implements RecipeService {

	private static final Logger LOG = LogManager.getLogger(RecipeServiceImpl.class);

	private RecipeServiceImpl() {
	}

	private static RecipeServiceImpl instance = new RecipeServiceImpl();

	public static RecipeServiceImpl getInstance() {
		return instance;
	}

	ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private final FrenchPressRecipeService frenchPressRecipeService = serviceFactory.getFrenchPressRecipeService();
	private final PouroverRecipeService pouroverRecipeService = serviceFactory.getPouroverRecipeService();

	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public List<Recipe> findAllCommonRecipes() throws ServiceException {
		LOG.debug("start findAllCommonRecipes");

		RecipeDao recipeDao = daoFabric.getRecipeDao();
		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao, coffeeTypeDao, countryDao);
		List<Recipe> recipes = null;

		try {
			recipes = recipeDao.findAllCommonRecipes();
			if (recipes != null) {
				for (int i = 0; i < recipes.size(); i++) {
					Recipe tmpRecipe = recipes.get(i);
					CoffeeType coffeeType = coffeeTypeDao.findEntityById(tmpRecipe.getCoffeeType().getID());
					if (coffeeType != null) {
						coffeeType.setCountry(countryDao.findEntityById(coffeeType.getCountry().getID()));
					}
					tmpRecipe.setCoffeeType(coffeeType);
				}
			}
//			LOG.debug(recipes.toString());

			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return recipes;
	}

	@Override
	public Recipe findRecipeByID(Integer ID) throws ServiceException {
		LOG.debug("start findAllCommonRecipes");

		RecipeDao recipeDao = daoFabric.getRecipeDao();
		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao, coffeeTypeDao, countryDao);
		Recipe recipe = null;

		try {
			recipe = recipeDao.findEntityById(ID);
			if (recipe != null) {
				CoffeeType coffeeType = coffeeTypeDao.findEntityById(recipe.getCoffeeType().getID());
				if (coffeeType != null) {
					coffeeType.setCountry(countryDao.findEntityById(coffeeType.getCountry().getID()));
				}
				recipe.setCoffeeType(coffeeType);

			}
//			LOG.debug(recipes.toString());

			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		
		recipe = setDetailedInformation (recipe);
		LOG.debug (recipe.toString());
		return recipe;
	}

	
	@Override
	public List<Recipe> findAllUserSavedRecipes(Integer userID) throws ServiceException {
		LOG.debug("start findAllUserSavedRecipes");

		RecipeDao recipeDao = daoFabric.getRecipeDao();
		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao, coffeeTypeDao, countryDao);
		List<Recipe> recipes = null;

		try {
			recipes = recipeDao.findAllUserSavedRecipes(userID);
			if (recipes != null) {
				for (int i = 0; i < recipes.size(); i++) {
					Recipe tmpRecipe = recipes.get(i);
					CoffeeType coffeeType = coffeeTypeDao.findEntityById(tmpRecipe.getCoffeeType().getID());
					if (coffeeType != null) {
						coffeeType.setCountry(countryDao.findEntityById(coffeeType.getCountry().getID()));
					}
					tmpRecipe.setCoffeeType(coffeeType);
				}
			}
//			LOG.debug(recipes.toString());

			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return recipes;
	}

	@Override
	public List<Recipe> findAllUserCommonRecipes(Integer userID) throws ServiceException {
		LOG.debug("start findAllUserCommonRecipes");

		RecipeDao recipeDao = daoFabric.getRecipeDao();
		CoffeeTypeDao coffeeTypeDao = daoFabric.getCoffeeTypeDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao, coffeeTypeDao, countryDao);
		List<Recipe> recipes = null;

		try {
			// 0 - all user's private recipes
			// 1 - all user's public recipes
			recipes = recipeDao.findByUserId(userID, 1);
			if (recipes != null) {
				for (int i = 0; i < recipes.size(); i++) {
					Recipe tmpRecipe = recipes.get(i);
					CoffeeType coffeeType = coffeeTypeDao.findEntityById(tmpRecipe.getCoffeeType().getID());
					if (coffeeType != null) {
						coffeeType.setCountry(countryDao.findEntityById(coffeeType.getCountry().getID()));
					}
					tmpRecipe.setCoffeeType(coffeeType);
				}
			}
//			LOG.debug(recipes.toString());

			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return recipes;
	}

	@Override
	public Integer createRecipeInDataBase(Recipe recipe) throws ServiceException {
		LOG.debug("start createCoffeeTypeInDataBase");

		RecipeDao recipeDao = daoFabric.getRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao);
		Integer result = 0;

		try {
			result = recipeDao.create(recipe);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return result;
	}
	
	public boolean deleteRecipeFromDataBase(Integer ID) throws ServiceException{
		LOG.debug("start createUserRecipeInDB");

		RecipeDao recipeDao = daoFabric.getRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(recipeDao);
		boolean result = false;

		try {
			result = recipeDao.delete(ID);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited " + e.getMessage());
				throw new ServiceException("rollback, transaction wasn't commited " + e.getMessage());
			}
		}finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.debug("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return result;
	}
	

	
	
	private Recipe setDetailedInformation(Recipe recipe) throws ServiceException {
		int recipeID = recipe.getID();
		RecipeType type = recipe.getRecipeType();
		try {
			switch (type) {
			case POUROVER:
				recipe = pouroverRecipeService.uniteTwoRecipes(recipe,
						pouroverRecipeService.takePouroverRecipeByID(recipeID));
				break;
			case FRENCHPRESS:
				recipe = frenchPressRecipeService.uniteTwoRecipes(recipe,
						frenchPressRecipeService.takeFrenchPressRecipeByID(recipeID));
				break;

			default:
				throw new ServiceException("wrong_type");
			}
		} catch (ServiceException e) {
			LOG.debug("can't set DetailedInformation " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return recipe;
	}
}
