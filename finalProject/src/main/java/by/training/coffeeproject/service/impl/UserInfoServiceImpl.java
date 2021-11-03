package by.training.coffeeproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.UserInfoDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService {

	private static final Logger LOG = LogManager.getLogger(UserInfoServiceImpl.class);

	private UserInfoServiceImpl() {
	}

	private static UserInfoServiceImpl instance = new UserInfoServiceImpl();

	public static UserInfoServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	/**
	 * take three Dao classes, accesses four tables and return List <User>
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public String takeUserNameByID(Integer ID) throws ServiceException {
		LOG.debug("start takeUserNameByID");

		if (ID == null) {
			LOG.error("can't take UserName, null");
			throw new ServiceException("can't take UserName, null");
		}

		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userInfoDao);
		String name = "";

		try {
			name = userInfoDao.takeUserNameByID(ID);
//			LOG.debug(name);
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.warn("rollback, transaction wasn't commited");
				throw new ServiceException();
			}
		} finally {
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				LOG.error("can't endTransaction " + e.getMessage());
				throw new ServiceException(e.getMessage());

			}
		}
		return name;
	}
}
