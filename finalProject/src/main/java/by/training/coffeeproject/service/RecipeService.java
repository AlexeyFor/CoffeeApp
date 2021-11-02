package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.Recipe;

public interface RecipeService {

	public List<Recipe> findAllCommonRecipes() throws ServiceException;

	public Recipe findRecipeByID(Integer ID) throws ServiceException;

	/**
	 * Find only Common recipes (for page showPublicUserINfo)
	 * 
	 * @param userID
	 * @return
	 * @throws ServiceException
	 */
	public List<Recipe> findAllUserCommonRecipes(Integer userID) throws ServiceException;

	/**
	 * Find both common and !common recipes
	 * 
	 * @param userID
	 * @return
	 * @throws ServiceException
	 */
	public List<Recipe> findAllUserSavedRecipes(Integer userID) throws ServiceException;

	public Integer createRecipeInDataBase(Recipe recipe) throws ServiceException;

	public boolean deleteRecipeFromDataBase(Integer ID) throws ServiceException;

	public boolean editRecipenInDB(Recipe recipe) throws ServiceException;

}
