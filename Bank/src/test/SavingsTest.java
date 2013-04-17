/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import backend.GlobalParameters;
import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

import account.Account;
import account.AccountType;
import account.AutomatedTransaction;
import account.InterestAccount;
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.User;
import date.DateTime;

public class SavingsTest {
	static User accountManager;	
	Account a;
	
	@BeforeClass
	static public void setUpClass() {
		accountManager = new AccountManager("Joe", "Smith", new DateTime(1940, 4, 29), 123456789, "account_mgr", "password");
	}

	/**
	 * @throws InsufficientCreditAvailableException 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws InsufficientCreditAvailableException {		
		GlobalParameters.SAVINGS_FEE.set(0);
		GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.set(0);
		GlobalParameters.MASTER_RATE_SAVINGS.set(.2);
		GlobalParameters.OFFSET_RATE_SAVINGS.set(.04);
		
		a = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.SAVINGS, null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		a.immediateClose();
	}
	
	@Test
	public void initialBalance() {
		assertEquals(0D, a.getBalance(), 0);
	}
	
	@Test
	public void rightRate() {
		assertEquals(.24, ((InterestAccount)a).getAccountRate(), 0.001);
	}

	
	@Test
	public void feesNotAssessed() {
		GlobalParameters.SAVINGS_FEE.set(0);
		GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.set(0);
		
		RuntimeAPI.shiftTime(31556900); // One Year
		
		assertEquals(0D, a.getBalance(), 0);
	}

	@Test
	public void feesAssessed() {
		GlobalParameters.SAVINGS_FEE.set(-3);
		GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.set(0.01);
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(31556900); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(-3*expectedCycles, a.getBalance(), 0);
	}

	@Test(expected=TransactionValidationException.class)
	public void nonNegativeBalance() throws TransactionValidationException {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, -0.0011313, "Withdrawal");
		a.postTransaction(t);
	}
	
	@Test
	public void interest_simple_test() {
		GlobalParameters.SAVINGS_FEE.set(-3);
		GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.set(0.01);
		
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(31556900); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1313 * Math.pow(1.02, expectedCycles), a.getBalance(), .1);
	}
	
	@Test
	public void interest_many_transactions() {
		GlobalParameters.SAVINGS_FEE.set(-1);
		GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.set(0.01);
		
		Account checking = null;
		
		try {
			checking = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.CHECKING, null);
			Transaction t;
			
			t = new Transaction(LocalAgent.agent, checking, 1E6, "Deposit");
			checking.postTransaction(t);
			
			AutomatedTransaction at1 = new AutomatedTransaction(1000, a.getAccountNumber(), "Some payment");		
			checking.setupAutomatedTransaction(at1);
		} catch (InsufficientCreditAvailableException e1) {
			e1.printStackTrace();
			fail("Unable to create checking.");
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to create checking.");
		}		
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(31556900); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1000 * (1-Math.pow(1.02, expectedCycles))/(1-1.02), a.getBalance(), 5);
		
		checking.immediateClose();
	}
}
