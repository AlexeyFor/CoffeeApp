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

import by.training.coffeeproject.dao.CoffeeTypeDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.entity.SortType;

public class CoffeeTypeDaoImpl extends AbstractDao<CoffeeType> implements CoffeeTypeDao {

	private CoffeeTypeDaoImpl() {
	}

	private static CoffeeTypeDaoImpl instance = new CoffeeTypeDaoImpl();

	public static CoffeeTypeDaoImpl getInstance() {
		return instance;
	}

	private static final Logger LOG = LogManager.getLogger(CoffeeTypeDaoImpl.class);

	private static final String SQL_SELECT_COFFETYPE_BY_ID = "SELECT  id_coffee_type, name,"
			+ " country_of_origin_id, processing_method, roaster, roast_degree, information,"
			+ "arabic_percent, robusta_percent FROM coffee_type WHERE id_coffee_type=?";
	private static final String SQL_SELECT_ALL_COFFETYPE = "SELECT  id_coffee_type, name, "
			+ "country_of_origin_id, processing_method, roaster, roast_degree, "
			+ "information, arabic_percent, robusta_percent FROM  coffee_type";
	private static final String SQL_FIND_COFFETYPE = "SELECT  id_coffee_type FROM coffee_type WHERE name=? AND"
			+ " country_of_origin_id=? AND processing_method=? AND roaster=? AND roast_degree=? AND information=? AND "
			+ "arabic_percent=? AND robusta_percent=?";
	private static final String SQL_SELECT_ALL_COFFE_ROASTER = "SELECT DISTINCT roaster FROM  coffee_type";
	private static final String SQL_DELETE_COFFETYPE = "DELETE FROM coffee_type WHERE id_coffee_type=?";
	private static final String SQL_CREATE_COFFETYPE = "INSERT INTO coffee_type (name, "
			+ "country_of_origin_id, processing_method, roaster, roast_degree, information,"
			+ " arabic_percent, robusta_percent) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_COFFETYPE = "UPDATE coffee_type SET name=?, "
			+ "country_of_origin_id=?, processing_method=?, roaster=?, roast_degree=?, "
			+ "information=?, arabic_percent=?, robusta_percent=? WHERE id_user=?";
	private static final String SQL_SELECT_SORTED_COFFETYPE_PAGINATION = "SELECT id_coffee_type, name, "
			+ "country_of_origin_id, processing_method, roaster, roast_degree, "
			+ "information, arabic_percent, robusta_percent FROM coffee_type ORDER BY ";
	private static final String SQL_COUNT_ALL_COFFE_TYPE = "SELECT count(*) FROM coffee_type";

	@Override
	public List<CoffeeType> findAll() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_COFFETYPE);
			ResultSet resultSet = statement.executeQuery();
			List<CoffeeType> resultList = new ArrayList<>();

			while (resultSet.next()) {
				CoffeeType resultCoffeeType = createCoffeeTypeFromResultSet(resultSet);
				resultList.add(resultCoffeeType);
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
	public List<String> findAllRoasters() throws DaoException {
		LOG.debug("start findAll");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_COFFE_ROASTER);
			ResultSet resultSet = statement.executeQuery();
			List<String> resultList = new ArrayList<>();

			while (resultSet.next()) {
				String roaster = (resultSet.getString("roaster"));
				resultList.add(roaster);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.debug("from findAllRoasters " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public CoffeeType findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_COFFETYPE_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			CoffeeType resultCoffeeType = null;

			if (resultSet.next()) {
				resultCoffeeType = createCoffeeTypeFromResultSet(resultSet);
			}
			return resultCoffeeType;
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
			statement = connection.prepareStatement(SQL_DELETE_COFFETYPE);
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

	@Override
	public Integer create(CoffeeType t) throws DaoException {
		LOG.debug("start create with " + t.toString());

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_CREATE_COFFETYPE, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, t.getName());
			statement.setInt(2, t.getCountry().getID());
			statement.setString(3, t.getProcessingMethod().getName());
			statement.setString(4, t.getRoaster());
			statement.setString(5, t.getRoastDegree().getName());
			statement.setString(6, t.getInformation());
			statement.setInt(7, t.getArabicPercent());
			statement.setInt(8, t.getRobustaPercent());

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

	@Override
	public boolean update(CoffeeType t) throws DaoException {
		LOG.debug("start update");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_COFFETYPE);
			statement.setString(1, t.getName());
			statement.setInt(2, t.getCountry().getID());
			statement.setInt(3, t.getArabicPercent());
			statement.setInt(4, t.getRobustaPercent());
			statement.setString(5, t.getProcessingMethod().getName());
			statement.setString(6, t.getRoaster());
			statement.setString(7, t.getRoastDegree().getName());
			statement.setString(8, t.getInformation());
			statement.setInt(9, t.getID());

			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			LOG.warn("can't update coffeeType " + e.getMessage());
			return false;
		} finally {
			close(statement);
		}
	}

	String SQL_FIND_COFFETYPEa = "SELECT  id_coffee_type FROM coffee_type WHERE  AND"
			+ "    AND roast_degree=? AND information=? AND" + "arabic_percent=? AND robusta_percent=?";

	@Override
	public Integer isExisting(CoffeeType coffeeType) throws DaoException {
		LOG.debug("start isExisting");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_FIND_COFFETYPE);
			statement.setString(1, coffeeType.getName());
			statement.setInt(2, coffeeType.getCountry().getID());
			statement.setString(3, coffeeType.getProcessingMethod().getName());
			statement.setString(4, coffeeType.getRoaster());
			statement.setString(5, coffeeType.getRoastDegree().getName());
			statement.setString(6, coffeeType.getInformation());
			statement.setInt(7, coffeeType.getArabicPercent());
			statement.setInt(8, coffeeType.getRobustaPercent());

			ResultSet resultSet = statement.executeQuery();
			Integer id = 0;

			if (resultSet.next()) {
				id = resultSet.getInt("id_coffee_type");
			}
			return id;
		} catch (SQLException e) {
			LOG.warn("can't find if coffeeType  is Existing" + e.getMessage());
			return 0;
		} finally {
			close(statement);
		}
	}

	@Override
	public List<CoffeeType> findSortedCoffeeTypePagination(int start, int number, SortType type) throws DaoException {
		LOG.debug("start findSortedCoffeeTypePagination");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		String createdStatement = createStatementPagination(start, number, type);
		try {
			statement = connection.prepareStatement(createdStatement);
			List<CoffeeType> resultList = new ArrayList<>();
//			statement.setString(1, type.getName());
//			statement.setInt(1, start);
//			statement.setInt(2, number);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				CoffeeType resultCoffeeType = createCoffeeTypeFromResultSet(resultSet);
				resultList.add(resultCoffeeType);
			}
			return resultList;
		} catch (SQLException e) {
			LOG.warn("from findSortedCoffeeTypePagination " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}
	}

	@Override
	public Integer count() throws DaoException {
		LOG.debug("start count");

		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();

		try {
			statement = connection.prepareStatement(SQL_COUNT_ALL_COFFE_TYPE);
			ResultSet resultSet = statement.executeQuery();
			Integer result = 0;

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
			return result;
		} catch (SQLException e) {
			LOG.warn("can't count CoffeeTypes" + e.getMessage());
			throw new DaoException("can't count CoffeeTypes" + e);
		} finally {
			close(statement);
		}
	}

	private CoffeeType createCoffeeTypeFromResultSet(ResultSet resultSet) throws SQLException {
		CoffeeType coffeeType = new CoffeeType();
		coffeeType.setID(resultSet.getInt("id_coffee_type"));
		coffeeType.setName(resultSet.getString("name"));
		coffeeType.setCountry(new Country(resultSet.getInt("country_of_origin_id")));
		coffeeType.setArabicPercent(resultSet.getInt("arabic_percent"));
		coffeeType.setRobustaPercent(resultSet.getInt("robusta_percent"));
		coffeeType
				.setProcessingMethod(ProcessingMethod.valueOf(resultSet.getString("processing_method").toUpperCase()));
		coffeeType.setRoaster(resultSet.getString("roaster"));
		coffeeType.setRoastDegree(RoastDegree.valueOf(resultSet.getString("roast_degree").toUpperCase()));
		coffeeType.setInformation(resultSet.getString("information"));

		return coffeeType;
	}
	
	/**
	 * Concat - because it is impossible to pass the column name in statement
	 * @param start
	 * @param number
	 * @param type
	 * @return
	 */
	private String createStatementPagination(int start, int number, SortType type) {
		String result;
		String oldStatement = SQL_SELECT_SORTED_COFFETYPE_PAGINATION;
		result = oldStatement.concat(type.getName());
		result = result.concat(" LIMIT ");
		result = result.concat(start + " ,");
		result = result.concat(" " + number);
		return result;
	}

}
