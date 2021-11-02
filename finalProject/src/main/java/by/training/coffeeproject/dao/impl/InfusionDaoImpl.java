package by.training.coffeeproject.dao.impl;

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
import by.training.coffeeproject.dao.InfusionDao;
import by.training.coffeeproject.entity.Infusion;

/**
 * 
 * @author AlexeySupruniuk
 *
 */
public class InfusionDaoImpl extends AbstractDao<Infusion> implements InfusionDao {

	private InfusionDaoImpl() {
	}

	private static InfusionDaoImpl instance = new InfusionDaoImpl();

	public static InfusionDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(InfusionDaoImpl.class);

	private static final String SQL_SELECT_INFUSION_BY_ID = "SELECT id_infusion, recipe_id, time_start,"
			+ " water_volume, time_end, water_temperature FROM infusions WHERE id_infusion=?";
	private static final String SQL_SELECT_ALL_INFUSIONS = "SELECT id_infusion, recipe_id, time_start,"
			+ " water_volume, time_end, water_temperature FROM infusions";
	private static final String SQL_DELETE_INFUSION = "DELETE FROM infusions WHERE id_infusion=?";
	private static final String SQL_CREATE_INFUSION = "INSERT INTO infusions "
			+ "(recipe_id, time_start, water_volume, time_end, water_temperature) VALUES ( ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_INFUSION = "UPDATE infusions SET recipe_id=?, time_start=?,"
			+ " water_volume=?, time_end=?, water_temperature=? WHERE id_infusion=?";
	private static final String SQL_SELECT_INFUSION_BY_RECIPE_ID = "SELECT id_infusion, recipe_id, time_start,"
			+ " water_volume, time_end, water_temperature FROM infusions WHERE recipe_id=?";

	@Override
	public List<Infusion> findAll() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_INFUSIONS);
			ResultSet resultSet = statement.executeQuery();
			List<Infusion> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Infusion resultInfusion = createInfusionFromResultSet(resultSet);
				resultList.add(resultInfusion);
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
	public List<Infusion> findByRecipeId(Integer RecipeId) throws DaoException {
		LOG.debug("start findByRecipeId");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_INFUSION_BY_RECIPE_ID);
			statement.setInt(1, RecipeId);
			ResultSet resultSet = statement.executeQuery();
			List<Infusion> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Infusion resultInfusion = createInfusionFromResultSet(resultSet);
				resultList.add(resultInfusion);
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
	public Infusion findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_INFUSION_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			Infusion resultInfusion = null;

			if (resultSet.next()) {
				resultInfusion = createInfusionFromResultSet(resultSet);
			}
			return resultInfusion;
		} catch (SQLException e) {
			LOG.debug("can't find CoffeeType " + e.getMessage());
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
			statement = connection.prepareStatement(SQL_DELETE_INFUSION);
			statement.setInt(1, id);
			statement.execute();
			return true;
		} catch (SQLException e) {
			LOG.warn("can't delete Infusion " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	@Override
	public Integer create(Infusion t) throws DaoException {
		LOG.debug("start create");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_INFUSION, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, t.getRecipeId());
			statement.setInt(2, t.getTimeStart());
			statement.setInt(3, t.getWaterVolume());
			statement.setInt(4, t.getTimeEnd());
			statement.setInt(5, t.getWaterTemperature());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			LOG.warn("can't create infusion " + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	@Override
	public boolean update(Infusion t) throws DaoException {
		LOG.debug("start update");
		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_INFUSION);
			statement.setInt(1, t.getRecipeId());
			statement.setInt(2, t.getTimeStart());
			statement.setInt(3, t.getWaterVolume());
			statement.setInt(4, t.getTimeEnd());
			statement.setInt(5, t.getWaterTemperature());
			statement.setInt(6, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.warn("can't update Infusion " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	private Infusion createInfusionFromResultSet(ResultSet resultSet) throws SQLException {
		Infusion infusion = new Infusion();
		infusion.setID(resultSet.getInt("id_infusion"));
		infusion.setRecipeId(resultSet.getInt("recipe_id"));
		infusion.setTimeStart(resultSet.getInt("time_start"));
		infusion.setWaterVolume(resultSet.getInt("water_volume"));
		infusion.setTimeEnd(resultSet.getInt("time_end"));
		infusion.setWaterTemperature(resultSet.getInt("water_temperature"));

		return infusion;
	}

}
