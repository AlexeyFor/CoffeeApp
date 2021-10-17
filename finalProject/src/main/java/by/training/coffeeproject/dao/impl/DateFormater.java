package by.training.coffeeproject.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;

public class DateFormater {
	/**
	 * convert String into right-formated date
	 * 
	 * @param str
	 * @return
	 * @throws ServiceException
	 */

	private static final Logger LOG = LogManager.getLogger(DateFormater.class);

	public static Date fromStrToDate(String str) throws DaoException {
		LOG.debug("start fromStrToDate with  " + str);

		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date result = formater.parse(str);
			LOG.debug(result.toString());
			return result;
		} catch (ParseException e) {
			LOG.debug("wrong date in xml");
			throw new DaoException("wrong_xml_date");
		}
	}

}
