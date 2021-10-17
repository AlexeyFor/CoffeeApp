package by.training.coffeeproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.UserInfoDao;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.UserInfo;

public class UserInfoDaoImpl extends AbstractDao<UserInfo> implements UserInfoDao {

	private UserInfoDaoImpl() {
	}

	private static UserInfoDaoImpl instance = new UserInfoDaoImpl();

	public static UserInfoDaoImpl getInstance() {
		return instance;
	}

	private static final String SQL_SELECT_USERINFO_BY_ID = "SELECT id_user, name, email, information, country_id, storagePath FROM user_info WHERE id_user=?";
	private static final String SQL_SELECT_ALL_USERINFO = "SELECT id_user, name, email, information, country_id, storagePath FROM user_info;";
	private static final String SQL_DELETE_USERINFO = "DELETE FROM user_info WHERE id_user=?";
	private static final String SQL_CREATE_USERINFO = "INSERT INTO user_info (id_user,name, email, information, country_id, storagePath) VALUES ( ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_USERINFO = "UPDATE user_info SET name=?, email=?, information=?, country_id=?, storagePath=? WHERE id_user=?";
	private static final String SQL_SELECT_USER_NAME_BY_ID = "SELECT name FROM user_info WHERE id_user=?";

	private static final Logger LOG = LogManager.getLogger(UserInfoDaoImpl.class);

	@Override
	public UserInfo findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_USERINFO_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			UserInfo resultUserInfo = null;

			if (resultSet.next()) {
				resultUserInfo = createUserInfoFromResultSet(resultSet);
			}
			return resultUserInfo;
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public List<UserInfo> findAll() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_USERINFO);
			ResultSet resultSet = statement.executeQuery();
			List<UserInfo> resultList = new ArrayList<>();

			while (resultSet.next()) {
				UserInfo resultUserInfo = createUserInfoFromResultSet(resultSet);
				resultList.add(resultUserInfo);
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
	public boolean delete(Integer id) throws DaoException {
		LOG.debug("start delete");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_USERINFO);
			statement.setInt(1, id);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.error("can't delete user_info");
			return false;
		} finally {
			close(statement);
		}

	}

	@Override
	public Integer create(UserInfo t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_USERINFO);
			statement.setInt(1, t.getID());
			statement.setString(2, t.getName());
			statement.setString(3, t.getEmail());
			statement.setString(4, t.getInformation());
			statement.setInt(5, t.getCountry().getID());
			statement.setString(6, t.getStoragePath());
			statement.executeUpdate();

			return t.getID();
		} catch (SQLException e) {
			LOG.error("can't create user_info " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	@Override
	public boolean update(UserInfo t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_USERINFO);
			statement.setString(1, t.getName());
			statement.setString(2, t.getEmail());
			statement.setString(3, t.getInformation());
			statement.setInt(4, t.getCountry().getID());
			statement.setString(5, t.getStoragePath());
			statement.setInt(6, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.error("can't update user_info");
			return false;
		} finally {
			close(statement);
		}
	}

	public String takeUserNameByID(Integer ID) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_USER_NAME_BY_ID);
			statement.setInt(1, ID);
			ResultSet resultSet = statement.executeQuery();
			String result = null;

			if (resultSet.next()) {
				result = resultSet.getString("name");
			}
			return result;
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement);
		}

	}

	private UserInfo createUserInfoFromResultSet(ResultSet resultSet) throws SQLException {
		UserInfo userInfo = new UserInfo();
		userInfo.setID(resultSet.getInt("id_user"));
		userInfo.setName(resultSet.getString("name"));
		userInfo.setEmail(resultSet.getString("email"));
		userInfo.setInformation(resultSet.getString("information"));
		userInfo.setCountry(new Country(resultSet.getInt("country_id")));
		userInfo.setStoragePath(resultSet.getString("storagePath"));
		return userInfo;
	}

}