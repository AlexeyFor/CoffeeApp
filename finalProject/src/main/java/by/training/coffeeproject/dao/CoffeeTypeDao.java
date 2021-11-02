package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.SortType;

public interface CoffeeTypeDao extends Dao<CoffeeType> {

	public List<String> findAllRoasters() throws DaoException;

	/**
	 * Try to find such a CoffeeType in database, if such recipe exists - return it
	 * ID
	 * 
	 * @param coffeeType
	 * @return
	 * @throws DaoException
	 */
	public Integer isExisting(CoffeeType coffeeType) throws DaoException;

	/**
	 * Return number of CoffeeType from start point. If NONE - order by id; SortType
	 * - for ORDER BY command
	 * 
	 * @param start
	 * @param number
	 * @param type
	 * @return
	 */
	public List<CoffeeType> findSortedCoffeeTypePagination(int start, int number, SortType type) throws DaoException;

	/**
	 * Count all rows in coffee_type table;
	 * @return
	 * @throws DaoException
	 */
	public Integer count() throws DaoException;



}
