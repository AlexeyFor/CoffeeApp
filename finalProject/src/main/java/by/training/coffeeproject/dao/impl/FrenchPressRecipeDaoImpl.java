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
import by.training.coffeeproject.dao.FrenchPressRecipeDao;
import by.training.coffeeproject.entity.FrenchPressRecipe;


public class FrenchPressRecipeDaoImpl extends AbstractDao<FrenchPressRecipe> implements FrenchPressRecipeDao {

	private FrenchPressRecipeDaoImpl() {
	}

	private static FrenchPressRecipeDaoImpl instance = new FrenchPressRecipeDaoImpl();

	public static FrenchPressRecipeDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(FrenchPressRecipeDaoImpl.class);

	private static final String SQL_FRENCHPRESS_RECIPE_BY_ID = "SELECT id_recipe, recipe_name, french_press_volume,"
			+ " mass_of_coffee, grind_settings, coffee_grinder, cap_breaking_time, plunger_lowering_time, total_time, discription"
			+ " FROM french_press_recipes WHERE id_recipe=?";
	private static final String SQL_DELETE_FRENCHPRESS_RECIPE = "DELETE FROM  french_press_recipes WHERE id_recipe=?";
	private static final String SQL_CREATE_FRENCHPRESS_RECIPE = "INSERT INTO french_press_recipes (id_recipe, recipe_name,"
			+ " french_press_volume, mass_of_coffee, grind_settings, coffee_grinder, cap_breaking_time, plunger_lowering_time, "
			+ "total_time, discription) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_FRENCHPRESS_RECIPE = "UPDATE french_press_recipes SET recipe_name=?, "
			+ "french_press_volume=?, mass_of_coffee=?, grind_settings=?, coffee_grinder=?, cap_breaking_time=?, "
			+ "plunger_lowering_time=?, total_time=?, discription=?  WHERE id_recipe=?";

	@Override
	public FrenchPressRecipe findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_FRENCHPRESS_RECIPE_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			FrenchPressRecipe resultFrenchPressRecipe = null;

			if (resultSet.next()) {
				resultFrenchPressRecipe = createFrenchPressRecipeFromResultSet(resultSet);
			}
			return resultFrenchPressRecipe;
		} catch (SQLException e) {
			LOG.debug("can't find frenchPressRecipe " + e.getMessage());
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
			statement = connection.prepareStatement(SQL_DELETE_FRENCHPRESS_RECIPE);
			statement.setInt(1, id);
			statement.execute();
			LOG.debug("delete return true");
			return true;
		} catch (SQLException e) {
			LOG.debug("can't delete frenchPressRecipe " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public Integer create(FrenchPressRecipe t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_FRENCHPRESS_RECIPE, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, t.getID());
			statement.setString(2, t.getRecipeName());
			statement.setInt(3, t.getFrechPressVolume());
			statement.setFloat(4, t.getMassOfCoffee());
			statement.setFloat(5, t.getGrindSettings());
			statement.setString(6, t.getCoffeeGrinder());
			statement.setInt(7, t.getCapBreakingTime());
			statement.setInt(8, t.getPlungerLoweringTime());
			statement.setInt(9, t.getTotalTime());
			statement.setString(10, t.getDisription());

			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			LOG.error("can't create frenchPressRecipe " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	
	
	@Override
	public boolean update(FrenchPressRecipe t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_FRENCHPRESS_RECIPE);
			statement.setString(1, t.getRecipeName());
			statement.setInt(2, t.getFrechPressVolume());
			statement.setFloat(3, t.getMassOfCoffee());
			statement.setFloat(4, t.getGrindSettings());
			statement.setString(5, t.getCoffeeGrinder());
			statement.setInt(6, t.getCapBreakingTime());
			statement.setInt(7, t.getPlungerLoweringTime());
			statement.setInt(8, t.getTotalTime());
			statement.setString(9, t.getDisription());
			statement.setInt(10, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.debug("can't update frenchPressRecipe " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public List<FrenchPressRecipe> findAll() throws DaoException {
		throw new UnsupportedOperationException();
	}

	private FrenchPressRecipe createFrenchPressRecipeFromResultSet(ResultSet resultSet) throws SQLException {
		FrenchPressRecipe frenchPressRecipe = new FrenchPressRecipe();
		frenchPressRecipe.setID(resultSet.getInt("id_recipe"));
		frenchPressRecipe.setRecipeName(resultSet.getString("recipe_name"));
		frenchPressRecipe.setFrechPressVolume(resultSet.getInt("french_press_volume"));
		frenchPressRecipe.setMassOfCoffee(resultSet.getFloat("mass_of_coffee"));
		frenchPressRecipe.setGrindSettings(resultSet.getFloat("grind_settings"));
		frenchPressRecipe.setCoffeeGrinder(resultSet.getString("coffee_grinder"));
		frenchPressRecipe.setCapBreakingTime(resultSet.getInt("cap_breaking_time"));
		frenchPressRecipe.setPlungerLoweringTime(resultSet.getInt("plunger_lowering_time"));
		frenchPressRecipe.setTotalTime(resultSet.getInt("total_time"));
		frenchPressRecipe.setDisription(resultSet.getString("discription"));

		return frenchPressRecipe;
	}
}
