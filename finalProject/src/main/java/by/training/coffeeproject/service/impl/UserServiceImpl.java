package by.training.coffeeproject.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.CountryDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.DaoFabric;
import by.training.coffeeproject.dao.UserDao;
import by.training.coffeeproject.dao.UserInfoDao;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.Role;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.entity.UserInfo;
import by.training.coffeeproject.service.EntityTransactionLogic;
import by.training.coffeeproject.service.PasswordEncryptionService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.UserService;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         All methods for taking User objects
 *
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

	private UserServiceImpl() {
	}

	private static UserServiceImpl instance = new UserServiceImpl();

	public static UserServiceImpl getInstance() {
		return instance;
	}

	private final DaoFabric daoFabric = DaoFabric.getInstance();
	private final PasswordEncryptionService encryptionLogic = PasswordEncryptionService.getInstance();
	private EntityTransactionLogic transactionLogic = EntityTransactionLogic.getInstance();

	/**
	 * take three Dao classes, accesses four tables and return List <User>
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public List<User> takeAllUsers() throws ServiceException {
		LOG.debug("start takeAllUsers");

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao, countryDao);
		List<User> users = null;

		try {
			users = userDao.findAllUsers();
			for (int i = 0; i < users.size(); i++) {
				User tmpUser = users.get(i);
				tmpUser.setUserInfo(userInfoDao.findEntityById(tmpUser.getID()));
				if (tmpUser.getUserInfo() != null) {
					tmpUser.getUserInfo()
							.setCountry(countryDao.findEntityById(tmpUser.getUserInfo().getCountry().getID()));
				}
			}
//			LOG.debug(users.toString());
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
		return users;
	}

	/**
	 * take country name, accesses four tables and return List <User>? where all
	 * users from one country
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public List<User> takeAllUsersByCountry(String name) throws ServiceException {
		LOG.debug("start takeAllUsers");

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao, countryDao);
		List<User> users = null;

		try {
			users = userDao.findAllUsersByCountryName(name);
			for (int i = 0; i < users.size(); i++) {
				User tmpUser = users.get(i);
				tmpUser.setUserInfo(userInfoDao.findEntityById(tmpUser.getID()));
				if (tmpUser.getUserInfo() != null) {
					tmpUser.getUserInfo()
							.setCountry(countryDao.findEntityById(tmpUser.getUserInfo().getCountry().getID()));
				}
			}
//			LOG.debug(users.toString());
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
		return users;
	}

	/**
	 * check login and password of User, if 'true' - return user. Also encrypt
	 * password.
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public User authorization(String login, String password) throws ServiceException {
		LOG.debug("start takeUserByLogin");

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao, countryDao);
		User user = null;

		try {
			String encryptedPassword = encryptionLogic.getEncryptedPassword(password);
			user = userDao.authorization(login, encryptedPassword);
			if (user != null) {
				user.setUserInfo(userInfoDao.findEntityById(user.getID()));
			} else {
				LOG.debug("user_is_not_found");
				throw new ServiceException("user_is_not_found");
			}
			if (user.getUserInfo() != null) {
				user.getUserInfo().setCountry(countryDao.findEntityById(user.getUserInfo().getCountry().getID()));

			}
			LOG.debug(user.toString());
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
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
		return user;
	}

	/**
	 * take two Dao classes, accesses two tables and create User and UserInfo.
	 * Encrypt password.
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public boolean createUserInDB(String login, String password, String name, String email, String information,
			Country country, String storagePath) throws ServiceException {
		LOG.debug("start createUser");

		if (!checkUserInformation(login, password, name, email, country, storagePath)) {
			throw new ServiceException("wrong_data_null");
		}
		String encryptedPassword = encryptionLogic.getEncryptedPassword(password);
		User user = createUser(login, encryptedPassword);
		UserInfo userInfo = createUserInfo(name, email, information, country, storagePath);
		boolean answer = false;

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao);
		// userInfo will not be created without User
		try {
			Integer id = userDao.create(user);
			if (id != 0) {
				LOG.debug("get id = " + id);
				userInfo.setID(id);
				if (userInfoDao.create(userInfo) != 0) {
					answer = true;
				} else {
					answer = false;
					transaction.rollback();
				}
			} else {
				answer = false;
				transaction.rollback();
			}
			if (answer) {
				transaction.commit();
			}
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				return false;
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

	/**
	 * take two Dao classes, accesses two tables and create User and UserInfo.
	 * Encrypt password.
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public boolean updateUserInDB(Integer ID, String login, String password, String name, String email,
			String information, Country country, String storagePath) throws ServiceException {
		LOG.debug("start updateUserInDB");

		if (!checkUserInformation(login, password, name, email, country, storagePath)) {
			throw new ServiceException("wrong_data_null");
		}
		UserInfo userInfo = new UserInfo(ID, name, email, information, country, storagePath, null);
		String encryptedPassword = encryptionLogic.getEncryptedPassword(password);
		User user = new User(ID, login, encryptedPassword, Role.USER, userInfo);
		boolean answer = false;

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao);
		// if the user or userInfo are not updated, a rollback will occur
		try {
			User tmp = userDao.findEntityById(ID);
			if (tmp != null) {
				boolean answer1 = userDao.update(user);
				boolean answer2 = false;
				if (answer1) {
					answer2 = userInfoDao.update(userInfo);
				}
				answer = answer1 && answer2;
				if (!answer) {
					transaction.rollback();
				}
			} else {
				answer = false;
			}
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
				return false;
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

	/**
	 * Delete User and UserInfo (userInfo - in database)
	 * 
	 * @param ID
	 * @return
	 * @throws ServiceException
	 */
	public boolean deleteUserByID(Integer ID) throws ServiceException {
		LOG.debug("start deleteUserByID with ID " + ID);

		UserDao userDao = daoFabric.getUserDao();

		boolean answer = false;

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao);

		try {
			User tmp = userDao.findEntityById(ID);
			if (tmp != null) {
				answer = userDao.delete(ID);
			} else {
				LOG.debug("user with ID " + ID + " doesn't exists");
				answer = false;
			}
			transaction.commit();
			transaction.endTransaction();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
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

	/**
	 * take three Dao classes, accesses four tables and return List <User>
	 * 
	 * @return List <User>
	 * @throws ServiceException
	 */
	public User takeUserByID(Integer ID) throws ServiceException {
		LOG.debug("start takeAllUsers");

		UserDao userDao = daoFabric.getUserDao();
		UserInfoDao userInfoDao = daoFabric.getUserInfoDao();
		CountryDao countryDao = daoFabric.getCountryDao();

		EntityTransaction transaction = transactionLogic.initTransactionInterface(userDao, userInfoDao, countryDao);
		User user = null;

		try {
			user = userDao.findEntityById(ID);
			if (user != null) {
				user.setUserInfo(userInfoDao.findEntityById(user.getID()));
				if (user.getUserInfo() != null) {
					user.getUserInfo().setCountry(countryDao.findEntityById(user.getUserInfo().getCountry().getID()));
				}
			}
//			LOG.debug(users.toString());
			transaction.commit();
		} catch (DaoException e) {
			try {
				transaction.rollback();
			} catch (DaoException e1) {
				LOG.debug("rollback, transaction wasn't commited");
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
		return user;
	}

	/**
	 * check all parameters for being not null
	 * 
	 * @return
	 */
	private boolean checkUserInformation(String login, String password, String name, String email, Country country,
			String storagePath) {
		boolean condition1 = login != null;
		boolean condition2 = name != null;
		boolean condition3 = email != null;
		boolean condition4 = country != null;
		boolean condition5 = storagePath != null;
		boolean condition6 = password != null;

		return condition1 && condition2 && condition3 && condition4 && condition5 && condition6;
	}

	private UserInfo createUserInfo(String name, String email, String information, Country country,
			String storagePath) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setEmail(email);
		userInfo.setCountry(country);
		userInfo.setStoragePath(storagePath);
		userInfo.setInformation(information);

		return userInfo;
	}

	private User createUser(String login, String password) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setRole(Role.USER);
		return user;
	}
}
