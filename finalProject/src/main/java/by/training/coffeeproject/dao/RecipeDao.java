package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.Recipe;
import by.training.coffeeproject.entity.RecipeType;

/**
 * 
 * @author AlexeySupruniuk
 *
 */
 
public interface RecipeDao extends Dao<Recipe> {

	/**
	 * Find either common or private recipes;
	 * common = 0 (private), common = 1 (common)
	 * @param userId
	 * @param common
	 * @return
	 * @throws DaoException
	 */
	List<Recipe> findByUserId(Integer userId, Integer common) throws DaoException;

	List<Recipe> findByRecipeType(RecipeType recipeType) throws DaoException;

	List<Recipe> findByCoffeeID(Integer coffeeId) throws DaoException;
	
	List<Recipe> findAllUserSavedRecipes(Integer userId) throws DaoException;
	
	List<Recipe> findAllCommonRecipes () throws DaoException;

//	List<Recipe> findByCoffeeCountry(Integer coffeeCountryId) throws DaoException;

//	List<Recipe> findByRoastDegree(String roastDegree) throws DaoException;
//
//	List<Recipe> findByRoaster(String roaster) throws DaoException;
//
//	List<Recipe> findByProcessingMethod(String processingMethod) throws DaoException;
//
//	List<Recipe> findByArabicPercent(int arabicPercent) throws DaoException;
//
//	List<Recipe> findByRobustaPercent(int robustaPercent) throws DaoException;

}
