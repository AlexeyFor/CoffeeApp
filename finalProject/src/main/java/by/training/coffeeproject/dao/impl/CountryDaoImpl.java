package by.training.coffeeproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.CountryDao;
import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.entity.Country;

/**
 * 
 * @author AlexeySupruniuk
 *
 *         Contain only two methods - findEntityById and findAll, all others
 *         throws UnsupportedOperationException
 */
public class CountryDaoImpl extends AbstractDao<Country> implements CountryDao {

	private CountryDaoImpl() {
	}

	private static CountryDaoImpl instance = new CountryDaoImpl();

	public static CountryDaoImpl getInstance() {
		return instance;
	}

	private static final String SQL_SELECT_ALL_COUNTRIES = "SELECT id_country,country_name FROM countries;";
	private static final String SQL_SELECT_COUNTRY_BY_ID = "SELECT id_country,country_name FROM countries WHERE id_country=?";

	private static final Logger LOG = LogManager.getLogger(CountryDaoImpl.class);

	@Override
	public Country findEntityById(Integer id) throws DaoException {
		LOG.debug("start findEntityById");
		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_COUNTRY_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			Country resultCountry = null;

			if (resultSet.next()) {
				resultCountry = new Country();
				resultCountry.setID(resultSet.getInt("id_country"));
				resultCountry.setCountryName(resultSet.getString("country_name"));
			}
			return resultCountry;
		} catch (SQLException e) {
			LOG.debug("from findEntityById " + e.getMessage());
			throw new DaoException(e);
		} finally {
			close(statement);
		}

	}

	@Override
	public List<Country> findAll() throws DaoException {
		LOG.debug("start findAll");
		PreparedStatement statement = null;
		Connection connection = pooledConnection.getConnection();
		try {
			statement = connection.prepareStatement(SQL_SELECT_ALL_COUNTRIES);
			ResultSet resultSet = statement.executeQuery();
			List<Country> resultList = new ArrayList<>();

			while (resultSet.next()) {
				Country country = new Country();
				country.setID(resultSet.getInt("id_country"));
				country.setCountryName(resultSet.getString("country_name"));
				resultList.add(country);
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
		throw new UnsupportedOperationException();

	}

	@Override
	public Integer create(Country t) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(Country t) throws DaoException {
		throw new UnsupportedOperationException();

	}

}
