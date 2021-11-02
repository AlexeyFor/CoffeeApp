package by.training.coffeeproject.service;

import by.training.coffeeproject.entity.UserRecipe;

public interface UserRecipeService {

	public Integer createUserRecipeInDB(UserRecipe userRecipe) throws ServiceException;

	/**
	 * first param - userID, second - recipeID
	 * 
	 * @param userId
	 * @param recipeId
	 * @return
	 * @throws ServiceException
	 */
	public boolean deleteUserRecipeInDB(Integer userId, Integer recipeId) throws ServiceException;

	/**
	 * first param - userID, second - recipeID
	 * @param userId
	 * @param recipeId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkExists(Integer userId, Integer recipeId) throws ServiceException;

}
