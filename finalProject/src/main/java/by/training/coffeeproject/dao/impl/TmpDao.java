package by.training.coffeeproject.dao.impl;

import java.util.List;

import by.training.coffeeproject.dao.DaoException;
import by.training.coffeeproject.dao.RecipeDao;
import by.training.coffeeproject.dao.UserDao;
import by.training.coffeeproject.dao.pool.ConnectionPool;
import by.training.coffeeproject.dao.pool.EntityTransaction;
import by.training.coffeeproject.entity.CoffeeType;
import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.Recipe;

public class TmpDao {
	public static void main(String[] args) throws DaoException {

		String url = "jdbc:mysql://localhost/coffeeRecipes";
		String propertiesPath = "resources\\database.properties";
		int startSize = 6;
		int maxSize = 6;
		int checkConnectionTimeout = 3;
		String user = "coffeeRecipes_app";
		String password = "123password123";

		// init Pool
		ConnectionPool.getInstance().init(url, propertiesPath, startSize, maxSize, checkConnectionTimeout);
		// 1. init DAO
//		CountryDaoImpl countryDao = new CountryDaoImpl();
		CoffeeTypeDaoImpl coffeeTypeDaoImpl = CoffeeTypeDaoImpl.getInstance();
		UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

		// 2. transaction initialization for DAO objects
		EntityTransaction transaction = new EntityTransaction();
		try {
			transaction.initTransactionInterface(coffeeTypeDaoImpl, userDaoImpl);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		// 3. query execution
		List<CoffeeType> recipes = null;
		try {
//			recipes = coffeeTypeDaoImpl.findAll();
			boolean answer = userDaoImpl.delete(30);
			transaction.commit();
		} catch (DaoException e) {
			transaction.rollback();
			System.out.println("mistake");
		} finally {
			// 4. transaction closing
			try {
				transaction.endTransaction();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

System.out.println("answer");
//		for (int i = 0; i < recipes.size(); i++) {
//			System.out.println(recipes.get(i).getID());
//			System.out.println(recipes.get(i).toString());
//
//		}
//		System.out.println(recipes.get(i).getID());
//		System.out.println(recipes.get(i).getCoffeeType().getID());
//		System.out.println(recipes.get(i).getAuthorUserId());
//		System.out.println(recipes.get(i).isCommon());
//		System.out.println(recipes.get(i).getDateOfCreating());
//		System.out.println(recipes.get(i).getRecipeType());
//		System.out.println("    ");
	}
}
