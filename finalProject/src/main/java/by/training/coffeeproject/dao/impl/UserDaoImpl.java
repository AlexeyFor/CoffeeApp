package by.training.coffeeproject.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.UserDao;
import by.training.coffeeproject.entity.User;
import by.training.coffeeproject.entity.UserInfo;
import by.training.coffeeproject.entity.Role;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	private UserDaoImpl() {
	}

	private static UserDaoImpl instance = new UserDaoImpl();

	public static UserDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

	private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user, login, password, role FROM users WHERE id_user=?";
	private static final String SQL_SELECT_ALL_USER = "SELECT id_user, login, password, role FROM users;";
	private static final String SQL_SELECT_ALL_USERROLE = "SELECT id_user, login, password,  role FROM users WHERE role='user';";
	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_user=?";
	private static final String SQL_CREATE_USER = "INSERT INTO users (login,password, role) VALUES ( ?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE users SET login=?,password =?,  role=? WHERE id_user=?";
	private static final String SQL_FIND_USER_COUNTRY = "{call findUsersFromCountry (?)}";
	private static final String SQL_SELECT_IDUSER_BY_LOGIN = "SELECT id_user FROM users WHERE login=?";
	private static final String SQL_AUTHORIZATION = "SELECT id_user, login,password, role FROM users WHERE login=? AND password=?";

	@Override
	public List<User> findAll() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_USER);
			ResultSet resultSet = statement.executeQuery();
			List<User> resultList = new ArrayList<>();

			while (resultSet.next()) {
				User resultUser = createUserFromResultSet(resultSet);
				resultList.add(resultUser);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("from findAll " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	public List<User> findAllUsersByCountryName(String name) throws DaoException {
		LOG.debug("findAllUsersByCountryName");

		CallableStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareCall(SQL_FIND_USER_COUNTRY);
			statement.setString(1, name);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			List<User> resultList = new ArrayList<>();

			while (resultSet.next()) {
//				User user = findEntityById(resultSet.getInt(1));
				User resultUser = createUserFromResultSet(resultSet);
				resultList.add(resultUser);
			}
			LOG.debug("return resultList " + resultList.toString());
			return resultList;
		} catch (SQLException e) {
			LOG.debug("from findAll " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public List<User> findAllUsers() throws DaoException {
		LOG.debug("start findAllUsers");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_USERROLE);
			ResultSet resultSet = statement.executeQuery();
			List<User> resultList = new ArrayList<>();

			while (resultSet.next()) {
				User resultUser = createUserFromResultSet(resultSet);
				resultList.add(resultUser);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("from findAll " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public User findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			User resultUser = null;

			if (resultSet.next()) {
				resultUser = createUserFromResultSet(resultSet);
			}
			return resultUser;
		} catch (SQLException e) {
			LOG.debug("can't find user " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public Integer findUserIDByLogin(String login) throws DaoException {
		LOG.debug("start findUserByLogin");

		PreparedStatement statement = null;

		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_SELECT_IDUSER_BY_LOGIN);
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			Integer id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt("id_user");
			}
			return id;
		} catch (SQLException e) {
			LOG.debug("can't find user " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}

	}

	@Override
	public Integer create(User t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, t.getLogin());
			statement.setString(2, t.getPassword());
			statement.setString(3, t.getRole().getName());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			LOG.warn("can't create user " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	@Override
	public boolean update(User t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_USER);
			statement.setString(1, t.getLogin());
			statement.setString(2, t.getPassword());
			statement.setString(3, t.getRole().getName());
			statement.setInt(4, t.getID());
			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.warn("can't update user " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	/**
	 * also delete user_info with the same id
	 */
	@Override
	public boolean delete(Integer id) throws DaoException {
		LOG.debug("start delete");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_USER);
			statement.setInt(1, id);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.warn("can't delete user " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public User authorization(String login, String password) throws DaoException {
		LOG.debug("start authorization");

		PreparedStatement statement = null;

		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_AUTHORIZATION);
			statement.setString(1, login);
			statement.setString(2, password);
//			LOG.debug("set pwd from dao " + password);
			ResultSet resultSet = statement.executeQuery();
			User resultUser = null;

			if (resultSet.next()) {
				resultUser = createUserFromResultSet(resultSet);
			}
			return resultUser;
		} catch (SQLException e) {
			LOG.debug("can't find user " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setID(resultSet.getInt("id_user"));
		user.setPassword(resultSet.getString("password"));
		user.setLogin(resultSet.getString("login"));
		user.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
		user.setUserInfo(new UserInfo(user.getID()));
		return user;
	}

}
