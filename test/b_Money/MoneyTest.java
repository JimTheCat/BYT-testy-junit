package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Test get amount method from Money
	 */
	@Test
	public void testGetAmount() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Integer> amountList = List.of(10000, 1000, 20000, 2000, 0, 0, -10000);

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Amount of money is not correct", amountList.get(i), moneyList.get(i).getAmount());
		}
	}

	/**
	 * Test get currency method from Money
	 */
	@Test
	public void testGetCurrency() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Currency> currencyList = List.of(SEK, EUR, SEK, EUR, SEK, EUR, SEK);

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Currency of money is not correct", currencyList.get(i), moneyList.get(i).getCurrency());
		}
	}

	/**
	 * Test to string method from Money
	 */
	@Test
	public void testToString() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<String> stringList = List.of("100.0 SEK", "10.0 EUR", "200.0 SEK", "20.0 EUR", "0.0 SEK", "0.0 EUR", "-100.0 SEK");

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("String of money is not correct", stringList.get(i), moneyList.get(i).toString());
		}
	}

	/**
	 * Test universal value method from Money
	 */
	@Test
	public void testGlobalValue() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Integer> globalValueList = List.of(66666, 666, 133333, 1333, 0, 0, -66666);

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Global value of money is not correct", globalValueList.get(i), moneyList.get(i).universalValue());
		}
	}

	/**
	 * Test equals method from Money
	 */
	@Test
	public void testEqualsMoney() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> moneyList2 = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Boolean> equalsList = List.of(true, true, true, true, true, true, true);

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Money is not equal", equalsList.get(i), moneyList.get(i).equals(moneyList2.get(i)));
		}
	}

	/**
	 * Test add method from Money
	 */
	@Test
	public void testAdd() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> moneyList2 = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> addList = List.of(new Money(20000, SEK), new Money(2000, EUR), new Money(40000, SEK), new Money(4000, EUR), new Money(0, SEK), new Money(0, EUR), new Money(-20000, SEK));

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Money is not equal", addList.get(i).getAmount(), moneyList.get(i).add(moneyList2.get(i)).getAmount());
		}
	}

	/**
	 * Test sub method from Money
	 */
	@Test
	public void testSub() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> moneyList2 = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> subList = List.of(new Money(0, SEK), new Money(0, EUR), new Money(0, SEK), new Money(0, EUR), new Money(0, SEK), new Money(0, EUR), new Money(0, SEK));

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Money is not equal", subList.get(i).getAmount(), moneyList.get(i).sub(moneyList2.get(i)).getAmount());
		}
	}

	/**
	 * Test is zero method from Money
	 */
	@Test
	public void testIsZero() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Boolean> isZeroList = List.of(false, false, false, false, true, true, false);

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Money is not equal", isZeroList.get(i), moneyList.get(i).isZero());
		}
	}

	/**
	 * Test negate method from Money
	 */
	@Test
	public void testNegate() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> negateList = List.of(new Money(-10000, SEK), new Money(-1000, EUR), new Money(-20000, SEK), new Money(-2000, EUR), new Money(0, SEK), new Money(0, EUR), new Money(10000, SEK));

		for (int i = 0; i < moneyList.size(); i++) {
			assertEquals("Money is not equal", negateList.get(i).getAmount(), moneyList.get(i).negate().getAmount());
		}
	}

	/**
	 * Test compare to method from Money
	 */
	@Test
	public void testCompareTo() {
		List<Money> moneyList = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Money> moneyList2 = List.of(SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100);
		List<Integer> compareToList = List.of(0, 0, 0, 0, 0, 0, 0);

		for (int i = 0; i < moneyList.size(); i++) {
			assertTrue("Money is not equal", moneyList.get(i).compareTo(moneyList2.get(i)) == compareToList.get(i));
		}
	}
}
