package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.CoffeeType;

public interface CoffeeTypeService {

	public List<String> findAllRoasters() throws ServiceException;

	public List<CoffeeType> findAllCoffeeTypes() throws ServiceException;

	public Integer createCoffeeTypeInDataBase(CoffeeType offeeType) throws ServiceException;

	/**
	 * check if such CoffeeType exists in DB. If true - return it's ID, else - 0
	 * 
	 * @param coffeeType
	 * @return
	 * @throws ServiceException
	 */
	public Integer isExistingInDataBase(CoffeeType coffeeType) throws ServiceException;

	/**
	 * Start - start position, number - number of taken CoffeeTypes, type - type of
	 * sorting (value from Enum). It's all look like ORDER BY type, LIMIT start,
	 * number
	 * 
	 * @param start
	 * @param number
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	public List<CoffeeType> findSortedCoffeeTypePagination(int start, int number, String type) throws ServiceException;

	/**
	 * Count all CoffeeTypes in DB
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Integer countAllCoffeeTypes() throws ServiceException;
}
