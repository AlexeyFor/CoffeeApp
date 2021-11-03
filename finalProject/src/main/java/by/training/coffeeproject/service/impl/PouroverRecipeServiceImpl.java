package by.training.coffeeproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.dao.PouroverRecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.PouroverRecipeService;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 *
 */
public class PouroverRecipeServiceImpl implements PouroverRecipeService {

	private static final Logger LOG = LogManager.getLogger(PouroverRecipeServiceImpl.class);

	private PouroverRecipeServiceImpl() {
	}

	private static PouroverRecipeServiceImpl instance = new PouroverRecipeServiceImpl();

	public static PouroverRecipeServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public PouroverRecipe takePouroverRecipeByID(Integer ID) throws ServiceException {
		LOG.debug("start takePouroverRecipeByID");

		if (ID == null) {
			LOG.error("can't take recipe, null");
			throw new ServiceException("can't take recipe, null");
		}

		PouroverRecipeDao pouroverRecipeDao = daoFabric.getPouroverRecipeDao();
		InfusionDao infusionDao = daoFabric.getInfusionDao();
		EntityTransaction transaction = transactionLogic.initTransactionInterface(pouroverRecipeDao, infusionDao);
		PouroverRecipe pouroverRecipe = null;

		try {
			pouroverRecipe = pouroverRecipeDao.findEntityById(ID);
			if (pouroverRecipe == null) {
				LOG.debug("non-existentRecipe");
				throw new ServiceException("Error.non-existentRecipe");
			}
			pouroverRecipe.setInfusions(infusionDao.findByRecipeId(ID));

//			LOG.debug(pouroverRecipe.toString());
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.error("rollback, transaction wasn't commited");
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
		return pouroverRecipe;
	}

	@Override
	public PouroverRecipe uniteTwoRecipes(Recipe recipe1, PouroverRecipe recipe2) throws ServiceException {

		if (recipe1 == null || recipe2 == null) {
			LOG.error("can't unite two recipes, null");
			throw new ServiceException("can't unite two recipes, null");
		}
		if (!recipe1.getRecipeType().equals(RecipeType.POUROVER)) {
			LOG.error("can't unite two recipes, wrong type");
			throw new ServiceException("can't unite two recipes, wrong type");
		}
		// check only three fields
		if (recipe1.getID().equals(recipe2.getID()) && (recipe1.getAuthorUserId() != 0)
				&& (recipe2.getRecipeName() != null)) {
			recipe2.setAuthorUserId(recipe1.getAuthorUserId());
			recipe2.setCommon(recipe1.isCommon());
			recipe2.setDateOfCreating(recipe1.getDateOfCreating());
			recipe2.setComments(recipe1.getComments());
			recipe2.setRecipeType(recipe1.getRecipeType());
			recipe2.setCoffeeType(recipe1.getCoffeeType());
		} else {
			LOG.error("can't unite two recipes");
			throw new ServiceException("can't unite two recipes");
		}
		return recipe2;
	}

	/**
	 * return 1 if success
	 */
	@Override
	public Integer createPouroverRecipeInDB(PouroverRecipe recipe) throws ServiceException {

		if (recipe == null) {
			LOG.error("can't create recipe, null");
			throw new ServiceException("can't create recipe, null");
		}

		LOG.debug("start createPouroverRecipeInDB");

		PouroverRecipeDao pouroverRecipeDao = daoFabric.getPouroverRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(pouroverRecipeDao);
		Integer result = 0;

		try {
			result = pouroverRecipeDao.create(recipe);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
				LOG.warn("recipe wasn't created " + e.getMessage());
				throw new ServiceException("recipe wasn't created " + e.getMessage());

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
	public boolean editPouroverRecipenInDB(PouroverRecipe recipe) throws ServiceException {
		LOG.debug("start editPouroverRecipenInDB");

		if (recipe == null) {
			LOG.error("can't edit recipe, null");
			throw new ServiceException("can't edit recipe, null");
		}
		PouroverRecipeDao pouroverRecipeDao = daoFabric.getPouroverRecipeDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(pouroverRecipeDao);
		boolean result = false;

		try {
			result = pouroverRecipeDao.update(recipe);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.error("rollback, transaction wasn't commited " + e.getMessage());
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
