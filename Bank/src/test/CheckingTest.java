/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.Agent;
import backend.GlobalParameters;
import backend.RuntimeAPI;

import account.Account;
import account.AccountType;
import account.AutomatedTransaction;
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.Customer;
import user.User;
import date.DateTime;

/**
 * @author ricardoivieitez
 *
 */
public class CheckingTest {
	class Benefactor implements Agent {

		@Override
		public boolean isInternal() {
			return true;
		}

		@Override
		public String describe() {
			return "Benefactor";
		}
		
	}
	
	final Benefactor benefactor = new Benefactor();
	
	User accountManager = new AccountManager("Joe", "Smith", new DateTime(1940, 4, 29), 123456789, "account_mgr", "password");
	User[] u;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		GlobalParameters.CHECKING_FEE.set(-7);
		GlobalParameters.CHECKING_OVERDRAFT_FEE.set(-35);
		GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.set(1500);
		GlobalParameters.CHECKING_MINIMUM_BALANCE.set(-10);
		
		u = new Customer[2];
		
		u[0] = accountManager.m_ePrivileges.createCustomer("George", "West", new DateTime(1961, 8, 12), "Lasuchis1961", "fahg8VeeG5ai", 218656057, AccountType.CHECKING, null);
		u[1] = accountManager.m_ePrivileges.createCustomer("Deborah", "Sweeny", new DateTime(1973, 10, 11), "Begadd", "eiZaegh4ATh", 775442720, AccountType.CHECKING, null);
		
		Account[] u0a = u[0].getAccounts();
		Account[] u1a = u[1].getAccounts();
		
		Transaction t1 = new Transaction(benefactor, u0a[0], 1000000.00, "$1M Gift");
		u0a[0].postTransaction(t1);
		
		AutomatedTransaction at1 = new AutomatedTransaction(1000, u1a[0].getAccountNumber(), "Some payment");		
		u0a[0].setupAutomatedTransaction(at1);		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Account[] u0a = u[0].getAccounts();
		Account[] u1a = u[1].getAccounts();
		
		u0a[0].close();
		u1a[0].close();
	}
	
	@Test
	public void getAccountByNumber() {
		Account[] u0a = u[0].getAccounts();
		
		assertEquals(u0a[0], RuntimeAPI.getAccount(u0a[0].getAccountNumber()));
	}

	@Test
	public void overdraft() {
		Account[] u1a = u[1].getAccounts();
		
		Transaction t1 = new Transaction(benefactor, u1a[0], -1000000.00, "$1M Charge");
		try {
			u1a[0].postTransaction(t1);
			fail("No exception");
		} catch (TransactionValidationException e) {
			
		}
		
		assertEquals(-35, u1a[0].getBalance(), 0);
	}
	
	@Test
	public void initialBalance() {
		Account[] u0a = u[0].getAccounts();
		
		assertEquals(1000000.00, u0a[0].getBalance(), 0);
	}


	@Test
	public void automatedTransaction() {
		Account[] u0a = u[0].getAccounts();
		Account[] u1a = u[1].getAccounts();
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(52594900L);
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1000000.00 - 1000 * expectedCycles, u0a[0].getBalance(), 0);
		assertEquals(1000 * expectedCycles - 7*3, u1a[0].getBalance(), 0);
	}

}
