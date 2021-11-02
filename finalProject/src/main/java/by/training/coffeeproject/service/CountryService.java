package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.Country;

public interface CountryService {

	public List<Country> findAllCountries() throws ServiceException;

	/**
	 * Check, if country with such ID exist in DB
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public boolean CountryExist(Integer id) throws ServiceException;

	public Country findCountryByID(Integer id) throws ServiceException;

}
