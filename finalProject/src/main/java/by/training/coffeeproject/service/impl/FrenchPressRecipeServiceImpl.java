package by.training.coffeeproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.FrenchPressRecipeDao;
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.dao.PouroverRecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.FrenchPressRecipeService;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 *
 */
public class FrenchPressRecipeServiceImpl implements FrenchPressRecipeService {

	private static final Logger LOG = LogManager.getLogger(FrenchPressRecipeServiceImpl.class);

	private FrenchPressRecipeServiceImpl() {
	}

	private static FrenchPressRecipeServiceImpl instance = new FrenchPressRecipeServiceImpl();

	public static FrenchPressRecipeServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public FrenchPressRecipe takeFrenchPressRecipeByID(Integer ID) throws ServiceException {
		LOG.debug("start takeFrenchPressRecipeByID");

		FrenchPressRecipeDao frenchPressRecipeDao = daoFabric.getFrenchPressRecipeDao();
		InfusionDao infusionDao = daoFabric.getInfusionDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(frenchPressRecipeDao, infusionDao);
		FrenchPressRecipe frenchPressRecipe = null;

		try {
			frenchPressRecipe = frenchPressRecipeDao.findEntityById(ID);
			frenchPressRecipe.setInfusions(infusionDao.findByRecipeId(ID));

//			LOG.debug(frenchPressRecipe.toString());
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
		return frenchPressRecipe;
	}

	@Override
	public FrenchPressRecipe uniteTwoRecipes(Recipe recipe1, FrenchPressRecipe recipe2) throws ServiceException {
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
			LOG.debug("can't unite two recipes");
			throw new ServiceException("can't unite two recipes");
		}
		return recipe2;
	}
}
