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
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.User;
import date.DateTime;

public class CheckingTest {
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
		GlobalParameters.CHECKING_FEE.set(0);
		GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.set(0);
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-1);
		
		a = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.CHECKING, null);
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
	public void feesNotAssessed() {
		GlobalParameters.CHECKING_FEE.set(0);
		GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.set(0);
		
		RuntimeAPI.shiftTime(31556900); // One Year
		
		assertEquals(0D, a.getBalance(), 0);
	}
	
	@Test
	public void feesAssessedWithNegativeBalance() {
		GlobalParameters.CHECKING_FEE.set(-3);
		GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.set(0);
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-1);
		
		Transaction t;		
		t = new Transaction(LocalAgent.agent, a, -0.001, "Withdrawal");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to withdraw");
		}

		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(31556900); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(-3*expectedCycles, a.getBalance(), 0.01);
	}


	@Test
	public void feesAssessed() {
		GlobalParameters.CHECKING_FEE.set(-3);
		GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.set(0.01);
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(31556900); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(-3*expectedCycles, a.getBalance(), 0);
	}

	@Test
	public void overdraftFee() {
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-3);
		GlobalParameters.CHECKING_OVERDRAFT_FEE.set(-5);

		Transaction t;
		
		try {
			t = new Transaction(LocalAgent.agent, a, -2.99, "Withdrawal");
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to withdraw");			
		}
		
		try {
			t = new Transaction(LocalAgent.agent, a, -1, "Withdrawal");
			a.postTransaction(t);
			fail("Capable of withdrawing");
		} catch (TransactionValidationException e) {
			assertEquals(-7.99, a.getBalance(), .1);
		}
	}
	
	@Test
	public void overdraftFeeWithChangingRequirements() {
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-9);
		GlobalParameters.CHECKING_OVERDRAFT_FEE.set(-5);

		Transaction t;
		
		try {
			t = new Transaction(LocalAgent.agent, a, -2.99, "Withdrawal");
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to withdraw");			
		}
		
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-1.89);
		
		try {
			t = new Transaction(LocalAgent.agent, a, -0.01, "Withdrawal");
			a.postTransaction(t);
			fail("Capable of withdrawing");
		} catch (TransactionValidationException e) {
			assertEquals(-7.99, a.getBalance(), .1);
		}
	}
	
	@Test
	public void noOverdraftFeeWithChangingRequirements() {
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-2.99);
		GlobalParameters.CHECKING_OVERDRAFT_FEE.set(-5);

		Transaction t;
		
		try {
			t = new Transaction(LocalAgent.agent, a, -2.99, "Withdrawal");
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to withdraw");			
		}
		
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-3);
		
		try {
			t = new Transaction(LocalAgent.agent, a, -0.01, "Withdrawal");
			a.postTransaction(t);
			assertEquals(-3, a.getBalance(), 0);			
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Incapable of withdrawing");
		}
	}
}
