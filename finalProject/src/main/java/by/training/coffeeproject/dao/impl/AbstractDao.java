package by.training.coffeeproject.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.pool.PooledConnection;
import by.training.coffeeproject.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author AlexeySupruniuk
 */
public abstract class AbstractDao<T extends Entity> {
	
	private static final Logger LOG = LogManager.getLogger(AbstractDao.class);

	protected PooledConnection pooledConnection;

	public abstract List<T> findAll() throws DaoException;

	public abstract T findEntityById(Integer id) throws DaoException;

	public abstract boolean delete(Integer id) throws DaoException;

	public abstract Integer create(T t) throws DaoException;

	public abstract boolean update(T t) throws DaoException;

	/**
	 * 
	 * @param statement
	 */
	public void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.debug("can't close statement");
		}
	}

	/**
	 * 
	 * @param connection
	 */
	public void setConnection(PooledConnection connection) {
		this.pooledConnection = connection;
	}

}
