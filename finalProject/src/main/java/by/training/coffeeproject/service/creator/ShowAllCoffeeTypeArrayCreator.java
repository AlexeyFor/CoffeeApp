package by.training.coffeeproject.service.creator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.CoffeeTypeService;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;

public class ShowAllCoffeeTypeArrayCreator {
	private static final Logger LOG = LogManager.getLogger(ShowAllCoffeeTypeArrayCreator.class);

	private static final ShowAllCoffeeTypeArrayCreator instance = new ShowAllCoffeeTypeArrayCreator();

	private ShowAllCoffeeTypeArrayCreator() {
	}

	public static ShowAllCoffeeTypeArrayCreator getInstance() {
		return instance;
	}

	/**
	 * Create array with parameters names. infusionsNumStr must be valid!
	 * 
	 * @param infusionsNumStr
	 * @return
	 * @throws ServiceException
	 */
	public int[][] createPagesArray(int numberOnPage) throws ServiceException {
		LOG.debug("start createPagesArray");
		CoffeeTypeService serv = ServiceFactory.getInstance().getCoffeeTypeService();
		double amountInDB = serv.countAllCoffeeTypes();
		double numberOfPagesDouble = amountInDB / numberOnPage;
		int numberOfPages = (int) Math.ceil(numberOfPagesDouble);
		int[][] result = new int[numberOfPages][2];
		for (int i = 0; i < numberOfPages; i++) {
			result[i][0] = i + 1;// page number
			result[i][1] = i * numberOnPage; // start position

		}

		return result;
	}

}
