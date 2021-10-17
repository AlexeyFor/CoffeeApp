package by.training.coffeeproject.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.Dao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.impl.InfusionDaoImpl;
import by.training.coffeeproject.dao.impl.UserDaoImpl;
import by.training.coffeeproject.dao.pool.EntityTransaction;

/**
 * All Logic for EntityTransaction
 * @author AlexeySupruniuk
 *
 */
public class EntityTransactionLogic {
	
	private static final Logger LOG = LogManager.getLogger(EntityTransactionLogic.class);

	
	private EntityTransactionLogic() {
	}

	private static EntityTransactionLogic instance = new EntityTransactionLogic();

	public static EntityTransactionLogic getInstance() {
		return instance;
	}

	/**
	 * initiate EntityTransaction with Dao interface implementations
	 * @param abstractDaos
	 * @return
	 */
	public EntityTransaction initTransactionInterface(Dao... abstractDaos) {
		LOG.debug ("start initTransactionInterface ");
		EntityTransaction transaction = new EntityTransaction();
		try {
			transaction.initTransactionInterface(abstractDaos);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		return transaction;
	}
	
}
