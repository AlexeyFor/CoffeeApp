package by.training.coffeeproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.UserRecipeDao;
import by.training.coffeeproject.entity.UserRecipe;

public class UserRecipeDaoImpl extends AbstractDao<UserRecipe> implements UserRecipeDao {

	private UserRecipeDaoImpl() {
	}

	private static UserRecipeDaoImpl instance = new UserRecipeDaoImpl();

	public static UserRecipeDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(UserRecipeDaoImpl.class);

	private static final String SQL_DELETE_USERRECIPE = "DELETE FROM user_recipes WHERE user_id=? AND recipe_id=?";
	private static final String SQL_CREATE_USERRECIPE = "INSERT INTO user_recipes (user_id, recipe_id) VALUES ( ?, ?)";


	/**
	 * return Integer = 1 if success, or 0 if not, because UserRecipe doesn't have
	 * generated key
	 * 
	 * @param t
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Integer create(UserRecipe t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_USERRECIPE);
			statement.setInt(1, t.getUserId());
			statement.setInt(2, t.getRecipeId());
			statement.executeUpdate();
			return 1;

		} catch (SQLException e) {
			LOG.error("can't create UserRecipe " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	public boolean deleteWithTwoInt(Integer userId, Integer recipeId) throws DaoException {

		LOG.debug("start delete");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_USERRECIPE);
			statement.setInt(1, userId);
			statement.setInt(1, recipeId);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.debug("can't delete Infusion " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public List<UserRecipe> findAll() throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserRecipe findEntityById(Integer id) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Integer id) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(UserRecipe t) throws DaoException {
		throw new UnsupportedOperationException();
	}

}