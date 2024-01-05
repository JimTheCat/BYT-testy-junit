package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

    /**
     * Test get names method from Currency
     */
	@Test
	public void testGetName() {
        String sekName = SEK.getName();
        String dkkName = DKK.getName();
        String eurName = EUR.getName();

        assertEquals("Name of currency is not correct", "SEK", sekName);
        assertEquals("Name of currency is not correct", "DKK", dkkName);
        assertEquals("Name of currency is not correct", "EUR", eurName);
	}

	/**
	 * Test get rate method from Currency
	 */
	@Test
	public void testGetRate() {
		double sekName = SEK.getRate();
		double dkkName = DKK.getRate();
		double eurName = EUR.getRate();

        assertEquals("Rate of currency is not correct", 0, Double.compare(sekName, 0.15));
        assertEquals("Rate of currency is not correct", 0, Double.compare(dkkName, 0.20));
        assertEquals("Rate of currency is not correct", 0, Double.compare(eurName, 1.5));
	}

	/**
	 * Test set rate method from Currency
	 */
	@Test
	public void testSetRate() {
		double newSEKRate = .20;
		double newDKKRate = .15;
		double newEURRate = 1.1;

		SEK.setRate(newSEKRate);
		DKK.setRate(newDKKRate);
		EUR.setRate(newEURRate);

        assertEquals("Rate of currency is not correct", 0, Double.compare(newSEKRate, SEK.getRate()));
        assertEquals("Rate of currency is not correct", 0, Double.compare(newDKKRate, DKK.getRate()));
        assertEquals("Rate of currency is not correct", 0, Double.compare(newEURRate, EUR.getRate()));
	}

	/**
	 * Test universal value method from Currency
	 */
	@Test
	public void testGlobalValue() {
		int amountToConvert = 40;

		int eurUniversalValue = EUR.universalValue(amountToConvert);
		int eurExpectedValue = (int) (amountToConvert / EUR.getRate());
		int dkkUniversalValue = DKK.universalValue(amountToConvert);
		int dkkExpectedValue = (int) (amountToConvert / DKK.getRate());
		int sekUniversalValue = SEK.universalValue(amountToConvert);
		int sekExpectedValue = (int) (amountToConvert / SEK.getRate());

		assertEquals("Universal value is wrongly calculated", eurExpectedValue, eurUniversalValue);
		assertEquals("Universal value is wrongly calculated", dkkExpectedValue, dkkUniversalValue);
		assertEquals("Universal value is wrongly calculated", sekExpectedValue, sekUniversalValue);
	}

	/**
	 * Test valueInThisCurrency method in Currency
	 */
	@Test
	public void testValueInThisCurrency() {
		//Convert EUR to DKK
		int amountToConvert = 40;

		int convertedDKKValue = DKK.valueInThisCurrency(amountToConvert, EUR);
		int expectedDKKValue = (int) (EUR.universalValue(amountToConvert) * DKK.getRate());

		assertEquals("Method seems to be wrong", expectedDKKValue, convertedDKKValue);
	}

}
