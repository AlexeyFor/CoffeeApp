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
import by.training.coffeeproject.dao.RecipeDao;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.FrenchPressRecipe;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         WARNING - create and update without dates
 *
 */
public class RecipeDaoImpl extends AbstractDao<Recipe> implements RecipeDao {

	private RecipeDaoImpl() {
	}

	private static RecipeDaoImpl instance = new RecipeDaoImpl();

	public static RecipeDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(RecipeDaoImpl.class);

	private static final String SQL_SELECT_RECIPE_BY_ID = "SELECT id_recipe, author_user_id, common, date_of_creating,"
			+ " recipe_type, coffee_type_id  FROM recipes WHERE id_recipe=?";
	private static final String SQL_SELECT_ALL_COMMON_RECIPES = "SELECT id_recipe, author_user_id, common, date_of_creating,"
			+ " recipe_type, coffee_type_id  FROM recipes  WHERE common=1;";
	private static final String SQL_DELETE_RECIPE = "DELETE FROM recipes WHERE id_recipe=?";
	private static final String SQL_CREATE_RECIPE = "INSERT INTO recipes ( author_user_id, common,"
			+ " recipe_type, coffee_type_id) VALUES ( ?, ?, ?, ?)";
	private static final String SQL_UPDATE_RECIPE = "UPDATE recipes SET author_user_id=?, common=?, "
			+ " recipe_type=?, coffee_type_id=? WHERE id_recipe=?";
	private static final String SQL_SELECT_RECIPE_BY_AUTHOR_USER_ID = "SELECT id_recipe, author_user_id, common, "
			+ "date_of_creating, recipe_type, coffee_type_id  FROM recipes WHERE author_user_id=? AND common=?";
	private static final String SQL_SELECT_RECIPE_BY_RECIPE_TYPE = "SELECT id_recipe, author_user_id, common, "
			+ "date_of_creating, recipe_type, coffee_type_id  FROM recipes WHERE recipe_type=?";
	private static final String SQL_SELECT_RECIPE_BY_COFFEE_TYPE_ID = "SELECT id_recipe, author_user_id, common, "
			+ "date_of_creating, recipe_type, coffee_type_id  FROM recipes WHERE coffee_type_id=?";
	private static final String SQL_SELECT_ALL_USER_SAVED_RECIPES = "{call findAllUserSavedRecipes (?)}";

	/**
	 * return all common recipes
	 */
	@Override
	public List<Recipe> findAllCommonRecipes() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_COMMON_RECIPES);
			ResultSet resultSet = statement.executeQuery();
			List<Recipe> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Recipe resultRecipe = createRecipeFromResultSet(resultSet);
				resultList.add(resultRecipe);
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
	public List<Recipe> findAllUserSavedRecipes(Integer userId) throws DaoException {
		LOG.debug("start findAllUserSavedRecipes");

		CallableStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareCall(SQL_SELECT_ALL_USER_SAVED_RECIPES);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			List<Recipe> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Recipe resultRecipe = createRecipeFromResultSet(resultSet);
				resultList.add(resultRecipe);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.warn("can't find Recipes " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public Recipe findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_RECIPE_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			Recipe resultRecipe = null;

			if (resultSet.next()) {
				resultRecipe = createRecipeFromResultSet(resultSet);
			}
			return resultRecipe;
		} catch (SQLException e) {
			LOG.debug("can't find recipe " + e.getMessage());
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
			statement = connection.prepareStatement(SQL_DELETE_RECIPE);
			statement.setInt(1, id);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.warn("can't delete CoffeeType " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	/**
	 * * WARNING - without date
	 */
	@Override
	public Integer create(Recipe t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_RECIPE, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, t.getAuthorUserId());
			statement.setBoolean(2, t.isCommon());
			statement.setString(3, t.getRecipeType().getName());
			statement.setInt(4, t.getCoffeeType().getID());

			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			LOG.warn("can't create coffeeType " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	/**
	 * * WARNING - without date
	 */
	@Override
	public boolean update(Recipe t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_RECIPE);
			statement.setInt(1, t.getAuthorUserId());
			statement.setBoolean(2, t.isCommon());
			statement.setString(3, t.getRecipeType().getName());
			statement.setInt(4, t.getCoffeeType().getID());
			statement.setInt(5, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.debug("can't update recipe" + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	// common = 0 (private), common = 1 (common)
	@Override
	public List<Recipe> findByUserId(Integer userId, Integer common) throws DaoException {
		LOG.debug("start findByUserId");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_RECIPE_BY_AUTHOR_USER_ID);
			statement.setInt(1, userId);
			statement.setInt(2, common);

			ResultSet resultSet = statement.executeQuery();
			List<Recipe> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Recipe resultRecipe = createRecipeFromResultSet(resultSet);
				resultList.add(resultRecipe);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("can't find Infusion" + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public List<Recipe> findByRecipeType(RecipeType recipeType) throws DaoException {
		LOG.debug("start findByRecipeId");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_RECIPE_BY_RECIPE_TYPE);
			statement.setString(1, recipeType.getName());
			ResultSet resultSet = statement.executeQuery();
			List<Recipe> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Recipe resultRecipe = createRecipeFromResultSet(resultSet);
				resultList.add(resultRecipe);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("can't find Infusion" + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public List<Recipe> findByCoffeeID(Integer coffeeId) throws DaoException {
		LOG.debug("start findByRecipeId");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_RECIPE_BY_COFFEE_TYPE_ID);
			statement.setInt(1, coffeeId);
			ResultSet resultSet = statement.executeQuery();
			List<Recipe> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Recipe resultRecipe = createRecipeFromResultSet(resultSet);
				resultList.add(resultRecipe);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("can't find Infusion" + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public List<Recipe> findAll() throws DaoException {
		throw new UnsupportedOperationException();
	}

	private Recipe createRecipeFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
		Recipe resultRecipe;
		RecipeType type = RecipeType.valueOf(deleteAllSpaces(resultSet.getString("recipe_type").toUpperCase()));
		switch (type) {
		case POUROVER:
			resultRecipe = new PouroverRecipe();
			break;
		case FRENCHPRESS:
			resultRecipe = new FrenchPressRecipe();
			break;
		default:
			throw new DaoException("wrong_type");
		}
		resultRecipe.setID(resultSet.getInt("id_recipe"));
		resultRecipe.setCoffeeType(new CoffeeType(resultSet.getInt("coffee_type_id")));
		resultRecipe.setAuthorUserId(resultSet.getInt("author_user_id"));
		resultRecipe.setCommon(resultSet.getBoolean("common"));
		resultRecipe.setDateOfCreating(DateFormater.fromStrToDate(resultSet.getDate("date_of_creating").toString()));
		resultRecipe.setRecipeType(type);

		return resultRecipe;
	}

	private String deleteAllSpaces(String str) {
		char[] startStr = str.toCharArray();
		StringBuilder resultStr = new StringBuilder();
		for (int i = 0; i < startStr.length; i++) {
			if (startStr[i] != ' ') {
				resultStr.append(startStr[i]);
			}
		}
		return new String(resultStr);
	}

}
