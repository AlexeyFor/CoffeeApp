package by.training.coffeeproject.service.creator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.entity.Infusion;
import by.training.coffeeproject.service.ServiceException;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         Contains methods for creating Infusion object and List <Infusion>,
 *         validating in another class. Also contains methods for create arrays
 *         with parameters names (for requests)
 */
public class InfusionCreator {

	private static final Logger LOG = LogManager.getLogger(InfusionCreator.class);

	private static final InfusionCreator instance = new InfusionCreator();

	private InfusionCreator() {
	}

	public static InfusionCreator getInstance() {
		return instance;
	}

	/**
	 * Take all parameters from request. All data must be validated with
	 * InfusionValidator
	 * 
	 * @see InfusionValidator
	 * @param request
	 * @return List<Infusion>
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public List<Infusion> createFromRequest(HttpServletRequest request, Integer recipeId) throws ServiceException {
		LOG.debug("start createFromRequest ");

		String infusionsNumber = request.getParameter("infusionsNumber");
		String[][] parameterNames = createInfusionsNameArray(infusionsNumber);
		List<Infusion> result = new ArrayList<>();
		Infusion tmp;

		for (int i = 0; i < parameterNames.length; i++) {
			int timeStart = Integer.valueOf(request.getParameter(parameterNames[i][0]));
			int waterVolume = Integer.valueOf(request.getParameter(parameterNames[i][1]));
			int timeEnd = Integer.valueOf(request.getParameter(parameterNames[i][2]));
			int waterTemperature = Integer.valueOf(request.getParameter(parameterNames[i][3]));
			tmp = new Infusion(recipeId, timeStart, waterVolume, timeEnd, waterTemperature);
			result.add(tmp);
		}
		return result;
	}

	/**
	 * Create array with parameters names. infusionsNumStr must be valid!
	 * 
	 * @param infusionsNumStr
	 * @return
	 */
	public String[][] createInfusionsNameArray(String infusionsNumStr) {
		int infusionsNum = Integer.valueOf(infusionsNumStr);
		final String TIMESTART = "timeStart";
		final String WATERVOLUME = "waterVolume";
		final String TIMEEND = "timeEnd";
		final String WATERTEMPERATURE = "waterTemperature";
		final int FIELDNUMBERS = 4;

		String[][] result = new String[infusionsNum][FIELDNUMBERS];

		for (int i = 0; i < infusionsNum; i++) {
			result[i][0] = TIMESTART + i;
			result[i][1] = WATERVOLUME + i;
			result[i][2] = TIMEEND + i;
			result[i][3] = WATERTEMPERATURE + i;
		}
		return result;
	}

	/**
	 * Create array with numeration and parameters names (for
	 * CreateRecipeTypeCommand) infusionsNumStr must be valid!
	 * 
	 * @param infusionsNumStr
	 * @return
	 */
	public String[][] createInfusionsArray(String infusionsNumStr) {
		int infusionsNum = Integer.valueOf(infusionsNumStr);
		final String NUMBER = "№ ";
		final String TIMESTART = "timeStart";
		final String WATERVOLUME = "waterVolume";
		final String TIMEEND = "timeEnd";
		final String WATERTEMPERATURE = "waterTemperature";
		final int FIELDNUMBERS = 5;

		String[][] result = new String[infusionsNum][FIELDNUMBERS];

		for (int i = 0; i < infusionsNum; i++) {
			result[i][0] = NUMBER + (i + 1);
			result[i][1] = TIMESTART + i;
			result[i][2] = WATERVOLUME + i;
			result[i][3] = TIMEEND + i;
			result[i][4] = WATERTEMPERATURE + i;
		}
		return result;
	}

	/**
	 * Create array with numeration and parameters names (for
	 * CreateRecipeTypeCommand) infusionsNumStr must be valid!
	 * 
	 * @param infusionsNumStr
	 * @return
	 */
	public String[][] createInfusionsArrayWithValues(int infusionsNum, List<Infusion> infusions) {
		final String NUMBER = "№ ";
		final String TIMESTART = "timeStart";
		final String WATERVOLUME = "waterVolume";
		final String TIMEEND = "timeEnd";
		final String WATERTEMPERATURE = "waterTemperature";
		final int FIELDNUMBERS = 9;// 5 - columns names +4 -values

		String[][] result = new String[infusionsNum][FIELDNUMBERS];

		for (int i = 0; i < infusionsNum; i++) {
			result[i][0] = NUMBER + (i + 1);
			result[i][1] = TIMESTART + i;
			result[i][2] = WATERVOLUME + i;
			result[i][3] = TIMEEND + i;
			result[i][4] = WATERTEMPERATURE + i;
			if (i < infusions.size()) {
				result[i][5] = String.valueOf(infusions.get(i).getTimeStart());
				result[i][6] = String.valueOf(infusions.get(i).getWaterVolume());
				result[i][7] = String.valueOf(infusions.get(i).getTimeEnd());
				result[i][8] = String.valueOf(infusions.get(i).getWaterTemperature());
			}

		}
		return result;
	}
}
