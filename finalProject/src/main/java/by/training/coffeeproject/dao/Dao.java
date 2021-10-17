package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.Entity;

public interface Dao<T extends Entity> {
	List<T> findAll() throws DaoException;

	T findEntityById(Integer id) throws DaoException;

	boolean delete(Integer id) throws DaoException;

	Integer create(T t) throws DaoException;

	boolean update(T t) throws DaoException;

}
