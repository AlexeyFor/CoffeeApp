package by.training.coffeeproject.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.ControllerServlet;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CountryService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

/**
 * 
 * AlexeySupruniuk
 *
 * All validating methods. Only validating and nothing else. All methods take
 * String and return boolean
 *
 */
public class ValidatorCommonMethods {
	
	private static final Logger LOG = LogManager.getLogger(ValidatorCommonMethods.class);

	private static final ValidatorCommonMethods instance = new ValidatorCommonMethods();

	private ValidatorCommonMethods() {
	}

	public static ValidatorCommonMethods getInstance() {
		return instance;
	}

	public boolean checkInfusions(String infusions) throws ServiceException {
		if (infusions != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,2}$");
			Matcher match = pat.matcher(infusions);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worng_symbols_infusions");
			}
		} else {
			throw new ServiceException("infusions_null");
		}
	}

	public boolean checkName(String name) throws ServiceException {
		if (name != null) {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-Я\s\\.\\-]{1,100}$");
			Matcher match = pat.matcher(name);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worng_symbols_name");
			}
		} else {
			throw new ServiceException("name_null");
		}
	}

	public boolean checkRoaster(String roaster) throws ServiceException {
		if (roaster != null) {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-Я\s\\.\\-]{1,100}$");
			Matcher match = pat.matcher(roaster);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worng_symbols_roaster");
			}
		} else {
			throw new ServiceException("roaster_null");
		}
	}

	/**
	 * First check with pattern, then in database
	 * 
	 * @param countryId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkCountryByID(String countryId) throws ServiceException {
		Integer countryIdInt;
		if (countryId != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,3}$");
			Matcher match = pat.matcher(countryId);
			if (match.find()) {
				countryIdInt = Integer.valueOf(countryId);
				CountryService serv = ServiceFactory.getInstance().getCountryService();
				return serv.CountryExist(countryIdInt);
			} else {
				throw new ServiceException("worng_symbols_country");
			}
		} else {
			throw new ServiceException("country_null");
		}
	}

	/**
	 * Check, if such constant exists in RoastDegree enum
	 * 
	 * @param roastDegree
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkRoastDegree(String roastDegree) throws ServiceException {
		if (roastDegree != null) {
			try {
				RoastDegree.valueOf(roastDegree);
			} catch (IllegalArgumentException e) {
				throw new ServiceException("RoastDegree_worng");
			}
			return true;
		} else {
			throw new ServiceException("RoastDegree_null");
		}
	}

	/**
	 * Check, if such constant exists in ProcessingMethod enum
	 * 
	 * @param roastDegree
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkProcessingMethod(String processingMethod) throws ServiceException {
		if (processingMethod != null) {
			try {
				ProcessingMethod.valueOf(processingMethod);
			} catch (IllegalArgumentException e) {
				throw new ServiceException("processingMethod_worng");
			}
			return true;
		} else {
			throw new ServiceException("processingMethod_null");
		}
	}

	/**
	 * check if percents are numbers and their summ
	 * 
	 * @param arabicPercentStr
	 * @param robustaPercentStr
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkPercents(String arabicPercentStr, String robustaPercentStr) throws ServiceException {
		Integer arabicPercent;
		Integer robustaPercent;

		if ((arabicPercentStr != null) && (robustaPercentStr != null)) {
			Pattern pat = Pattern.compile("^[0-9]{1,3}$");
			Matcher match = pat.matcher(arabicPercentStr);
			Matcher match2 = pat.matcher(robustaPercentStr);
			if (match.find() && match2.find()) {
				arabicPercent = Integer.valueOf(arabicPercentStr);
				robustaPercent = Integer.valueOf(robustaPercentStr);
				boolean cond1 = (arabicPercent >= 0) && (robustaPercent >= 0);
				boolean cond2 = (arabicPercent + robustaPercent) == 100;
				if (cond1 && cond2) {
					return true;
				} else {
					throw new ServiceException("wrong_percent");
				}
			} else {
				throw new ServiceException("wrong_percent");
			}
		} else {
			throw new ServiceException("percent_null");
		}

	}

	public boolean checkInformation(String information) throws ServiceException {
		if (information != null && information != "") {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-Я\s,\\.\\:\\!\\-\\?\\;]{1,1000}$");
			Matcher match = pat.matcher(information);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worng_symbols_information");
			}
		} else {
			throw new ServiceException("information_null");
		}
	}

	/**
	 * Check, if such constant exists in RecipeType enum
	 * 
	 * @param roastDegree
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkRecipeType(String recipeType) throws ServiceException {
		LOG.debug("start checkRecipeType with " + recipeType);
		if (recipeType != null && recipeType != "") {
			try {
				RecipeType.valueOf(recipeType);
			} catch (IllegalArgumentException e) {
				throw new ServiceException("recipeType_worng " + recipeType + "end");
			}
			return true;
		} else {
			throw new ServiceException("recipeType_null");
		}
	}
}
