package by.training.coffeeproject.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.FunnelType;
import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.entity.RecipeType;
import by.training.coffeeproject.entity.RoastDegree;
import by.training.coffeeproject.service.CoffeeTypeService;
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

	public boolean checkInfusionsNumber(String infusions) throws ServiceException {
		if (infusions != null) {
			Pattern pat = Pattern.compile("^[1-9]{1}[0-9]{0,1}$");
			Matcher match = pat.matcher(infusions);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsInfusions");
			}
		} else {
			throw new ServiceException("infusionsNull");
		}
	}

	public boolean checkName(String name) throws ServiceException {
		if (name != null) {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$");
			Matcher match = pat.matcher(name);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsName");
			}
		} else {
			throw new ServiceException("nameNull");
		}
	}

	public boolean checkRoaster(String roaster) throws ServiceException {
		if (roaster != null) {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$");
			Matcher match = pat.matcher(roaster);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsRoaster");
			}
		} else {
			throw new ServiceException("roasterNull");
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
				throw new ServiceException("worngSymbolsCountry");
			}
		} else {
			throw new ServiceException("countryNull");
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
				throw new ServiceException("RoastDegreeWorng");
			}
			return true;
		} else {
			throw new ServiceException("RoastDegreeNull");
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
				throw new ServiceException("processingMethodWorng");
			}
			return true;
		} else {
			throw new ServiceException("processingMethodNull");
		}
	}

	/**
	 * Check, if such constant exists in RoastDegree enum
	 * 
	 * @param roastDegree
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkFunnelType(String funnelType) throws ServiceException {
		if (funnelType != null) {
			try {
				FunnelType.valueOf(funnelType);
			} catch (IllegalArgumentException e) {
				throw new ServiceException("FunnelTypeWorng");
			}
			return true;
		} else {
			throw new ServiceException("FunnelTypeNull");
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
			throw new ServiceException("percentNull");
		}

	}

	public boolean checkInformation(String information) throws ServiceException {
		if (information != null && information != "") {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-ЯёЁ\s,\\.\\:\\!\\-\\?\\;]{1,1000}$");
			Matcher match = pat.matcher(information);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbols_information");
			}
		} else {
			throw new ServiceException("informationNull");
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
				throw new ServiceException("recipeTypeWorng " + recipeType + "end");
			}
			return true;
		} else {
			throw new ServiceException("recipeTypeNull");
		}
	}

	public boolean checkMassOfCoffee(String massOfCoffee) throws ServiceException {
		if (massOfCoffee != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,3}[\\.]{0,1}[0-9]{0,2}$");
			Matcher match = pat.matcher(massOfCoffee);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsMassOfCoffee");
			}
		} else {
			throw new ServiceException("massOfCoffeeNull");
		}
	}

	public boolean checkGrindSettings(String grindSettings) throws ServiceException {
		if (grindSettings != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,3}[\\.]{0,1}[0-9]{0,2}$");
			Matcher match = pat.matcher(grindSettings);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsGrindSettings");
			}
		} else {
			throw new ServiceException("grindSettingsNull");
		}
	}

	public boolean checkCoffeeGrinder(String coffeeGrinder) throws ServiceException {
		if (coffeeGrinder != null) {
			Pattern pat = Pattern.compile("^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$");
			Matcher match = pat.matcher(coffeeGrinder);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsCoffeeGrinder");
			}
		} else {
			throw new ServiceException("coffeeGrinderNull");
		}
	}

	public boolean checkTotalTime(String totalTime) throws ServiceException {
		if (totalTime != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,4}$");
			Matcher match = pat.matcher(totalTime);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsTotalTime");
			}
		} else {
			throw new ServiceException("totalTimeNull");
		}
	}

	/**
	 * infusionParameter - checked parameter excSymbol - message for
	 * ServiceException, when wrong symbols was found excNull - message for
	 * ServiceException, when infusionParameter is null
	 * 
	 * @param infusionParameter
	 * @param excSymbol
	 * @param excNull
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkInfusion(String infusionParameter, String excSymbol, String excNull) throws ServiceException {
		if (infusionParameter != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,4}$");
			Matcher match = pat.matcher(infusionParameter);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException(excSymbol);
			}
		} else {
			throw new ServiceException(excNull);
		}
	}

	/**
	 * First check with pattern, then in database
	 * 
	 * @param countryId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkNumberPagination(String number) throws ServiceException {
		if (number != null) {
			Pattern pat = Pattern.compile("^[0-9]{1,6}$");
			Matcher match = pat.matcher(number);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsPaginationNum");
			}
		} else {
			throw new ServiceException("paginationNumNull");
		}
	}

	/**
	 * First check with pattern, then in database
	 * 
	 * @param countryId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkEnum(String enumMember) throws ServiceException {
		if (enumMember != null) {
			Pattern pat = Pattern.compile("^[0-9A-Z]{1,100}$");
			Matcher match = pat.matcher(enumMember);
			if (match.find()) {
				return true;
			} else {
				throw new ServiceException("worngSymbolsEnumMember");
			}
		} else {
			throw new ServiceException("enumMemberNull");
		}
	}

	/**
	 * Check, if start position is not bigger than coffeetypes amount
	 * 
	 * @param countryId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkStartPaginationCoffeeType(String start) throws ServiceException {
		if (start != null) {
			int startInt;
			startInt = Integer.valueOf(start);
			CoffeeTypeService serv = ServiceFactory.getInstance().getCoffeeTypeService();
			int amountInDB = serv.countAllCoffeeTypes();
			if (startInt < amountInDB) {
				return true;
			} else {
				throw new ServiceException("startPaginationCoffeeType");
			}
		} else {
			throw new ServiceException("paginationStartNull");
		}

	}
}
