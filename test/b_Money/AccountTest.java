package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	/**
	 * Test add and remove timed payment method from Account
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("2", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("3", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("4", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("5", 2, 2, new Money(100, SEK), SweBank, "Alice");

		assertTrue("Timed payment does not exist", testAccount.timedPaymentExists("1"));
		assertTrue("Timed payment does not exist", testAccount.timedPaymentExists("2"));
		assertTrue("Timed payment does not exist", testAccount.timedPaymentExists("3"));
		assertTrue("Timed payment does not exist", testAccount.timedPaymentExists("4"));
		assertTrue("Timed payment does not exist", testAccount.timedPaymentExists("5"));

		testAccount.removeTimedPayment("1");
		testAccount.removeTimedPayment("2");
		testAccount.removeTimedPayment("3");
		testAccount.removeTimedPayment("4");
		testAccount.removeTimedPayment("5");

		assertFalse("Timed payment exists", testAccount.timedPaymentExists("1"));
		assertFalse("Timed payment exists", testAccount.timedPaymentExists("2"));
		assertFalse("Timed payment exists", testAccount.timedPaymentExists("3"));
		assertFalse("Timed payment exists", testAccount.timedPaymentExists("4"));
		assertFalse("Timed payment exists", testAccount.timedPaymentExists("5"));
	}

	/**
	 * Test timed payment method from Account
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("2", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("3", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("4", 2, 2, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("5", 2, 2, new Money(100, SEK), SweBank, "Alice");

		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals("Balance of account is not correct", 9999500, testAccount.getBalance().getAmount(), 0);
		assertEquals("Balance of account is not correct", 1000500, SweBank.getBalance("Alice"), 0);

		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals("Balance of account is not correct", 9999000, testAccount.getBalance().getAmount(), 0);
		assertEquals("Balance of account is not correct", 1001000, SweBank.getBalance("Alice"), 0);
	}

	/**
	 * Test deposit method from Account
	 */
	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(100, SEK));
		assertEquals("Balance of account is not correct", 10000100, testAccount.getBalance().getAmount(), 0);

		testAccount.withdraw(new Money(100, SEK));
		assertEquals("Balance of account is not correct", 10000000, testAccount.getBalance().getAmount(), 0);
	}

	/**
	 * Test get balance method from Account
	 */
	@Test
	public void testGetBalance() {
		assertEquals("Balance of account is not correct", 10000000, testAccount.getBalance().getAmount(), 0);
	}
}
