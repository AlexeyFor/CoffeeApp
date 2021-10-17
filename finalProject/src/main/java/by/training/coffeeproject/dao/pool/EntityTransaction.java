package by.training.coffeeproject.dao.pool;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.Dao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.impl.AbstractDao;

/**
 * 
 * @author AlexeySupruniuk
 *
 *         Class for transaction query to DB, allows to combine many requests
 */
public class EntityTransaction {

	private static final Logger LOG = LogManager.getLogger(EntityTransaction.class);

	private PooledConnection connection;

	/**
	 * 
	 * @param daos
	 * @throws DaoException
	 */
	public void initTransaction(AbstractDao... daos) throws DaoException {
		LOG.info("start initTransaction");
		if (connection == null) {
			connection = (PooledConnection) ConnectionPool.getInstance().getConnection();
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			LOG.debug("exception in initTransaction");
			throw new DaoException();
		}
		for (AbstractDao daoElement : daos) {
			daoElement.setConnection(connection);
		}
	}
	
	/**
	 * 
	 * @param daos
	 * @throws DaoException
	 */
	public  void initTransactionInterface(Dao... daos) throws DaoException {
		LOG.info("start initTransaction");
		if (connection == null) {
			connection = (PooledConnection) ConnectionPool.getInstance().getConnection();
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			LOG.debug("exception in initTransaction");
			throw new DaoException();
		}
		for (Dao daoElement : daos) {
			if (daoElement instanceof AbstractDao) {
				((AbstractDao)daoElement).setConnection(connection);
			} else {
				throw new DaoException (daoElement.toString() + " doesn't instanceof AbstractDao");
			}
		}
	}

	/**
	 * 
	 * @throws DaoException
	 */
	public void endTransaction() throws DaoException {
		LOG.info("start endTransaction");
		if (connection == null) {
			LOG.debug("exception in endTransaction, is null");
			throw new DaoException("Connection is null");
		}

		try {
			// connection check code for commit
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			LOG.debug("exception in endTransaction " + e);
			throw new DaoException();
		}
		// connection return code to the pool or closing
		ConnectionPool.getInstance().freeConnection(connection);
//		connection = null;
	}

	/**
	 * do all requests
	 * 
	 * @throws DaoException
	 */
	public void commit() throws DaoException {
		LOG.info("start commit");
		try {
			connection.commit();
		} catch (SQLException e) {
			LOG.debug("exception in commit " + e);
			throw new DaoException();
		}
	}

	/**
	 * reverts the database to its previous state before the request
	 * 
	 * @throws DaoException
	 */
	public void rollback() throws DaoException {
		LOG.info("start rollback");
		try {
			connection.rollback();
		} catch (SQLException e) {
			LOG.debug("exception in rollback " + e);
			throw new DaoException();
		}
	}
	
}
