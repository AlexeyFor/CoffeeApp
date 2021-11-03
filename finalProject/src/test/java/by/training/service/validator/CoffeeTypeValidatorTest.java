package by.training.service.validator;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.training.coffeeproject.entity.ProcessingMethod;
import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.validator.ValidatorCommonMethods;

public class CoffeeTypeValidatorTest {

	ValidatorCommonMethods provider = ValidatorCommonMethods.getInstance();

	////////////////CheckName
	@DataProvider(name = "positiveForCheckName")
	public String[] positiveForCheckName() {
		return new String[] { "asd456asd", "asd456aфывфыв", "asdasd", "ывывавыа", "956", "1", "asd asd" };
	}

	@DataProvider(name = "negativeForCheckName")
	public String[] negativeForCheckName() {
		return new String[] { "", "asd,", "123456789101234567891012345678910123456789101234567891012345678910 123456789101234567891012345678910123456789101234567891012345678910123456789101234567891012345678910" };
	}
	
	@Test(description = "positive for CheckName", dataProvider = "positiveForCheckName")
	public void CheckNameTestPositive(String name) throws ServiceException {
		boolean actual = provider.checkRoaster(name);
	
		assertTrue(actual);
	}

	@Test(description = "negative for CheckName", dataProvider = "negativeForCheckName" ,enabled = true, expectedExceptions = ServiceException.class)
	public void CheckNameTestNegative(String name) throws ServiceException {
		boolean actual = provider.checkRoaster(name);
		assertFalse(actual);
	}
	////////////////
	
////////////////Check Information
	@DataProvider(name = "positiveForCheckInformation")
	public String[] positiveForCheckInformation() {
		return new String[] { "asd456asd", "asd456aфывфыв", "asdasd", "ывывавыа", "956", "1", "asd asd", ".!-:?" };
	}
	@Test(description = "positive for CheckName", dataProvider = "positiveForCheckInformation")
	public void CheckInformationTestPositive(String information) throws ServiceException {
		boolean actual = provider.checkInformation(information);
	
		assertTrue(actual);
	}
/////////////////

	
}
