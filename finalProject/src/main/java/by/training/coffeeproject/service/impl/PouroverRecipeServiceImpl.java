package by.training.coffeeproject.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.dao.PouroverRecipeDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
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

		PouroverRecipeDao pouroverRecipeDao = daoFabric.getPouroverRecipeDao();
		InfusionDao infusionDao = daoFabric.getInfusionDao();
		EntityTransaction transaction = transactionLogic.initTransactionInterface(pouroverRecipeDao, infusionDao);
		PouroverRecipe pouroverRecipe = null;

		try {
			pouroverRecipe = pouroverRecipeDao.findEntityById(ID);
			pouroverRecipe.setInfusions(infusionDao.findByRecipeId(ID));

//			LOG.debug(pouroverRecipe.toString());
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
		return pouroverRecipe;
	}

	@Override
	public PouroverRecipe uniteTwoRecipes(Recipe recipe1, PouroverRecipe recipe2) throws ServiceException {
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
