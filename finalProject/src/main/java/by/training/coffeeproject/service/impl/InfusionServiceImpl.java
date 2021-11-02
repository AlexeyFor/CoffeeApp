package by.training.coffeeproject.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.InfusionService;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 *
 */
public class InfusionServiceImpl implements InfusionService {

	private static final Logger LOG = LogManager.getLogger(InfusionServiceImpl.class);

	private InfusionServiceImpl() {
	}

	private static InfusionServiceImpl instance = new InfusionServiceImpl();

	public static InfusionServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	@Override
	public List<Infusion> takeInfusionsByRecipeID(Integer ID) throws ServiceException {

		LOG.debug("start takeInfusionsByRecipeID");

		InfusionDao infusionDao = daoFabric.getInfusionDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(infusionDao);
		List<Infusion> infusions = null;

		try {
			infusions = infusionDao.findByRecipeId(ID);
//			LOG.debug(infusions.toString());
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
		return infusions;
	}

	@Override
	public Integer createInfusionInDB(Infusion infusion) throws ServiceException {

		LOG.debug("start createInfusionInDB");

		InfusionDao infusionDao = daoFabric.getInfusionDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(infusionDao);
		Integer result = 0;

		try {
			result = infusionDao.create(infusion);
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

	@Override
	public boolean editInfusionInDB(Infusion infusion) throws ServiceException {
		LOG.debug("start createInfusionInDB");

		InfusionDao infusionDao = daoFabric.getInfusionDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(infusionDao);
		boolean result = false;

		try {
			result = infusionDao.update(infusion);
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

	@Override
	public boolean deleteInfusionInDB(Integer ID) throws ServiceException {
		LOG.debug("start deleteInfusionInDB with ID " + ID);

		InfusionDao infusionDao = daoFabric.getInfusionDao();

		boolean answer = false;

		EntityTransaction transaction = transactionLogic.initTransactionInterface(infusionDao);

		try {
			answer = infusionDao.delete(ID);
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.error("rollback, transaction wasn't commited");
				answer = false;
			}
		} finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.error("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return answer;
	}

}
