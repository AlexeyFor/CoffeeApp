package by.training.coffeeproject.dao;

import by.training.coffeeproject.entity.UserRecipe;

public interface UserRecipeDao extends Dao<UserRecipe> {

	/**
	 * Delete UserRecipe in DB 
	 * @param userId
	 * @param recipeId
	 * @return
	 * @throws DaoException
	 */
	boolean deleteWithTwoInt(Integer userId, Integer recipeId) throws DaoException;


	/**
	 * 
	 * @param userId
	 * @param recipeId
	 * @return
	 * @throws DaoException
	 */
	boolean checkExists(Integer userId, Integer recipeId) throws DaoException;

}
