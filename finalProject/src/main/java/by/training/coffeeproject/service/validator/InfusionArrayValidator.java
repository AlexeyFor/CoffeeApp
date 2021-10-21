package by.training.coffeeproject.service.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.creator.InfusionCreator;

public class InfusionArrayValidator {

	private static final Logger LOG = LogManager.getLogger(InfusionArrayValidator.class);

	private static final InfusionArrayValidator instance = new InfusionArrayValidator();

	private InfusionArrayValidator() {
	}

	public static InfusionArrayValidator getInstance() {
		return instance;
	}

	private final String EXC_SYMBOL_TIMESTART = "worng_symbols_TimeStart";
	private final String EXC_SYMBOL_WATERVOLUME = "worng_symbols_WaterVolume";
	private final String EXC_SYMBOL_TIMEEND = "worng_symbols_TimeEnd";
	private final String EXC_SYMBOL_WATERTEMPERATURE = "worng_symbols_WaterTemperature";
	private final String EXC_NULL_TIMESTART = "TimeStart_null";
	private final String EXC_NULL_WATERVOLUME = "WaterVolume_null";
	private final String EXC_NULL_TIMEEND = "TimeEnd_null";
	private final String EXC_NULL_WATERTEMPERATURE = "WaterTemperature_null";
	private final InfusionCreator infusionCreator = InfusionCreator.getInstance();

	/**
	 * Only for CreatePouroverRecipeCommand and CreateFrencPressRecipeCommand. Take
	 * all Parameters from HttpServletRequest, validate them, throw exception if
	 * there is any mistake. If parameters can be null - valid with if.
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @see CreatePouroverRecipeCommand
	 * @see CreateFrencPressRecipeCommand
	 */
	public boolean validateInfusionsArray(HttpServletRequest request) throws ServiceException {
		LOG.debug("start validateInfusionsArray");

		ValidatorCommonMethods validator = ValidatorCommonMethods.getInstance();

		String infusionsNumber = request.getParameter("infusionsNumber");
		boolean condition1 = validator.checkInfusionsNumber(infusionsNumber);

		if (condition1) {
			String[][] parameterNames = infusionCreator.createInfusionsNameArray(infusionsNumber);
			for (int i = 0; i < parameterNames.length; i++) {
				validator.checkInfusion(request.getParameter(parameterNames[i][0]), EXC_SYMBOL_TIMESTART,
						EXC_NULL_TIMESTART);
				validator.checkInfusion(request.getParameter(parameterNames[i][1]), EXC_SYMBOL_WATERVOLUME,
						EXC_NULL_WATERVOLUME);
				validator.checkInfusion(request.getParameter(parameterNames[i][2]), EXC_SYMBOL_TIMEEND,
						EXC_NULL_TIMEEND);
				validator.checkInfusion(request.getParameter(parameterNames[i][3]), EXC_SYMBOL_WATERTEMPERATURE,
						EXC_NULL_WATERTEMPERATURE);
			}
			return true;
		} else {
			return false;
		}
	}


}
