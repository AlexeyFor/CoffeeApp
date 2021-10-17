package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.Recipe;

public interface RecipeService {

	public List<Recipe> findAllCommonRecipes() throws ServiceException;

	public Recipe findRecipeByID(Integer ID) throws ServiceException;

	public List<Recipe> findAllUserCommonRecipes(Integer userID) throws ServiceException;

	public List<Recipe> findAllUserSavedRecipes(Integer userID) throws ServiceException;

	public Integer createRecipeInDataBase(Recipe recipe) throws ServiceException;

}
