package by.training.coffeeproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.PouroverRecipeDao;
import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.RecipeType;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         All methods for taking, updating and delete PouroverRecipe objects
 *
 */
public class PouroverRecipeDaoImpl extends AbstractDao<PouroverRecipe> implements PouroverRecipeDao {

	private PouroverRecipeDaoImpl() {
	}

	private static PouroverRecipeDaoImpl instance = new PouroverRecipeDaoImpl();

	public static PouroverRecipeDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(PouroverRecipeDaoImpl.class);
	
	private static final String SQL_POUROVER_RECIPE_BY_ID = "SELECT  id_recipe, funnel_type, recipe_name, "
			+ "mass_of_coffee, grind_settings, coffee_grinder, total_time, discription"
			+ " FROM pourover_recipes WHERE id_recipe=?";
	private static final String SQL_DELETE_POUROVER_RECIPE = "DELETE FROM  pourover_recipes WHERE id_recipe=?";
	private static final String SQL_CREATE_POUROVER_RECIPE = "INSERT INTO pourover_recipes (id_recipe, funnel_type,"
			+ " recipe_name, mass_of_coffee, grind_settings, coffee_grinder, total_time, discription) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_POUROVER_RECIPE = "UPDATE pourover_recipes SET funnel_type=?,"
			+ " recipe_name=?, mass_of_coffee=?, grind_settings=?, coffee_grinder=?, total_time=?, discription=?"
			+ " WHERE id_recipe=?";

	@Override
	public PouroverRecipe findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_POUROVER_RECIPE_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			PouroverRecipe resultPouroverRecipe = null;

			if (resultSet.next()) {
				resultPouroverRecipe = createPouroverRecipeFromResultSet(resultSet);
			}
			return resultPouroverRecipe;
		} catch (SQLException e) {
			LOG.debug("can't find pouroverRecipe " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}
	
	@Override
	public Integer create(PouroverRecipe t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_POUROVER_RECIPE, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, t.getID());
			statement.setString(2, t.getFunnelType().getName());
			statement.setString(3, t.getRecipeName());
			statement.setFloat(4, t.getMassOfCoffee());
			statement.setFloat(5, t.getGrindSettings());
			statement.setString(6, t.getCoffeeGrinder());
			statement.setInt(7, t.getTotalTime());
			statement.setString(8, t.getDisription());

			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			LOG.error("can't create pouroverRecipe " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	@Override
	public boolean update(PouroverRecipe t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_POUROVER_RECIPE);
			statement.setString(1, t.getFunnelType().getName());
			statement.setString(2, t.getRecipeName());
			statement.setFloat(3, t.getMassOfCoffee());
			statement.setFloat(4, t.getGrindSettings());
			statement.setString(5, t.getCoffeeGrinder());
			statement.setInt(6, t.getTotalTime());
			statement.setString(7, t.getDisription());
			statement.setInt(8, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.debug("can't update pouroverRecipe " + e.getMessage());
			return false;
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
			statement = connection.prepareStatement(SQL_DELETE_POUROVER_RECIPE);
			statement.setInt(1, id);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.debug("can't delete PouroverRecipe " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public List<PouroverRecipe> findAll() throws DaoException {
		throw new UnsupportedOperationException();
	}
	
	private PouroverRecipe createPouroverRecipeFromResultSet(ResultSet resultSet) throws SQLException {
		PouroverRecipe pouroverRecipe = new PouroverRecipe();
		pouroverRecipe.setID(resultSet.getInt("id_recipe"));
		pouroverRecipe.setFunnelType(FunnelType.valueOf(deleteAllSpaces(resultSet.getString("funnel_type").toUpperCase())));
		pouroverRecipe.setRecipeName(resultSet.getString("recipe_name"));
		pouroverRecipe.setMassOfCoffee(resultSet.getFloat("mass_of_coffee"));
		pouroverRecipe.setGrindSettings(resultSet.getFloat("grind_settings"));
		pouroverRecipe.setCoffeeGrinder(resultSet.getString("coffee_grinder"));
		pouroverRecipe.setTotalTime(resultSet.getInt("total_time"));
		pouroverRecipe.setDisription(resultSet.getString("discription"));
		
		return pouroverRecipe;
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
