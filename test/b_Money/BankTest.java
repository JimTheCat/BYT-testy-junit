package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

    /**
     * Test get name method from Bank
     */
	@Test
	public void testGetName() {
        String sweBankName = SweBank.getName();
        String nordeaName = Nordea.getName();
        String danskeBankName = DanskeBank.getName();

        assertEquals("Name of bank is not correct", "SweBank", sweBankName);
        assertEquals("Name of bank is not correct", "Nordea", nordeaName);
        assertEquals("Name of bank is not correct", "DanskeBank", danskeBankName);
	}

    /**
     * Test get currency method from Bank
     */
	@Test
	public void testGetCurrency() {
        Currency sweBankCurrency = SweBank.getCurrency();
        Currency nordeaCurrency = Nordea.getCurrency();
        Currency danskeBankCurrency = DanskeBank.getCurrency();

        assertEquals("Currency of bank is not correct", SEK, sweBankCurrency);
        assertEquals("Currency of bank is not correct", SEK, nordeaCurrency);
        assertEquals("Currency of bank is not correct", DKK, danskeBankCurrency);
	}

    /**
     * Test open account method from Bank
     * @throws AccountExistsException
     */
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        SweBank.openAccount("Alice");
        Nordea.openAccount("Alice");
        DanskeBank.openAccount("Alice");

        SweBank.deposit("Alice", new Money(1000000, SEK));
        Nordea.deposit("Alice", new Money(1000000, SEK));
        DanskeBank.deposit("Alice", new Money(1000000, DKK));

        assertEquals("Balance of account is not correct", 1000000, SweBank.getBalance("Alice"), 0);
        assertEquals("Balance of account is not correct", 1000000, Nordea.getBalance("Alice"), 0);
        assertEquals("Balance of account is not correct", 1000000, DanskeBank.getBalance("Alice"), 0);
	}

    /**
     * Test deposit method from Bank
     * @throws AccountDoesNotExistException
     */
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(1000000, SEK));
        Nordea.deposit("Bob", new Money(1000000, SEK));
        DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

        assertEquals("Balance of account is not correct", 1000000, SweBank.getBalance("Ulrika"), 0);
        assertEquals("Balance of account is not correct", 1000000, Nordea.getBalance("Bob"), 0);
        assertEquals("Balance of account is not correct", 1000000, DanskeBank.getBalance("Gertrud"), 0);
	}

    /**
     * Test withdraw method from Bank
     * @throws AccountDoesNotExistException
     */
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {

        SweBank.deposit("Ulrika", new Money(2000000, SEK));
        Nordea.deposit("Bob", new Money(2000000, SEK));
        DanskeBank.deposit("Gertrud", new Money(2000000, DKK));

        SweBank.withdraw("Ulrika", new Money(1000000, SEK));
        Nordea.withdraw("Bob", new Money(1000000, SEK));
        DanskeBank.withdraw("Gertrud", new Money(1000000, DKK));

        assertEquals("Balance of account is not correct", 1000000, SweBank.getBalance("Ulrika"), 0);
        assertEquals("Balance of account is not correct", 1000000, Nordea.getBalance("Bob"), 0);
        assertEquals("Balance of account is not correct", 1000000, DanskeBank.getBalance("Gertrud"), 0);
	}

    /**
     * Test get balance method from Bank
     * @throws AccountDoesNotExistException
     */
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(1000000, SEK));
        Nordea.deposit("Bob", new Money(1000000, SEK));
        DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

        assertEquals("Balance of account is not correct", 1000000, SweBank.getBalance("Ulrika"), 0);
        assertEquals("Balance of account is not correct", 1000000, Nordea.getBalance("Bob"), 0);
        assertEquals("Balance of account is not correct", 1000000, DanskeBank.getBalance("Gertrud"), 0);
	}

    /**
     * Test transfer method from Bank
     * @throws AccountDoesNotExistException
     */
	@Test
	public void testTransfer() throws AccountDoesNotExistException {

        SweBank.deposit("Ulrika", new Money(1000000, SEK));
        Nordea.deposit("Bob", new Money(1000000, SEK));
        DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

        SweBank.transfer("Ulrika", "Bob", new Money(1000000, SEK));
        SweBank.transfer("Bob", DanskeBank, "Gertrud", new Money(1000000, SEK));
        DanskeBank.transfer("Gertrud", Nordea,"Bob", new Money(1000000, DKK));

        assertEquals("Balance of account is not correct", 0, SweBank.getBalance("Ulrika"), 0);
        assertEquals("Balance of account is not correct", 1750000, Nordea.getBalance("Bob"), 0);
        assertEquals("Balance of account is not correct", 1333333, DanskeBank.getBalance("Gertrud"), 0);
	}

    /**
     * Test add timed payment method from Bank
     * @throws AccountDoesNotExistException
     */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(3000000, SEK));
        Nordea.deposit("Bob", new Money(1000000, SEK));
        DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

        SweBank.addTimedPayment("Ulrika", "1", 1, 1, new Money(1000000, SEK), Nordea, "Bob");
        SweBank.addTimedPayment("Ulrika", "2", 1, 1, new Money(1000000, SEK), DanskeBank, "Gertrud");
        SweBank.addTimedPayment("Ulrika", "3", 1, 1, new Money(1000000, SEK), SweBank, "Bob");

        SweBank.tick();
        SweBank.tick();
        SweBank.tick();

        assertEquals("Balance of account is not correct", 0, SweBank.getBalance("Ulrika"), 0);
        assertEquals("Balance of account is not correct", 2000000, Nordea.getBalance("Bob"), 0);
        assertEquals("Balance of account is not correct", 2333333, DanskeBank.getBalance("Gertrud"), 0);
        assertEquals("Balance of account is not correct", 1000000, SweBank.getBalance("Bob"), 0);
	}
}
