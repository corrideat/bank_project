package test;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.SavingsTest.Benefactor;
import user.AccountManager;
import user.Customer;
import user.User;
import account.Account;
import account.AccountParameters;
import account.AccountType;
import account.AutomatedTransaction;
import account.CD.CD_type;
import account.Transaction;
import backend.Agent;
import backend.GlobalParameters;
import backend.RuntimeAPI;
import date.DateTime;

public class CDTest {
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
		u = new Customer[2];
		
		u[0] = accountManager.m_ePrivileges.createCustomer("George", "West", new DateTime(1961, 8, 12), "Lasuchis1961", "fahg8VeeG5ai", 218656057, AccountType.CHECKING, null);
		
		Map<AccountParameters, Object> params = new EnumMap<AccountParameters, Object>(AccountParameters.class);
		
		params.put(AccountParameters.CD_TYPE, CD_type.CD_6M);
		
		u[1] = accountManager.m_ePrivileges.createCustomer("Deborah", "Sweeny", new DateTime(1973, 10, 11), "Begadd", "eiZaegh4ATh", 775442720, AccountType.SAVINGS, null);
		
		GlobalParameters.CD_MINIMUM_BALANCE.set(5000);
		
		Account[] u0a = u[0].getAccounts();
		Account[] u1a = u[1].getAccounts();
		
		Transaction t1 = new Transaction(benefactor, u0a[0], 1000000.00, "$1M Gift");
		
		AutomatedTransaction at1 = new AutomatedTransaction(1000, u1a[0].getAccountNumber(), "Some payment");
		
		u0a[0].setupAutomatedTransaction(at1);
		
		if (t1 != null) {
			u[0].getAccounts()[0].postTransaction(t1);
		}
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		u[0].getAccounts()[0].close();
		u[1].getAccounts()[0].close();
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
		// TODO: Verify interest being paid properly
		//assertEquals(1000 * expectedCycles, u1a[0].getBalance(), 0);
	}
	
	@Test
	public void automatedTransactionWithVariableInterest() {
		Account[] u0a = u[0].getAccounts();
		Account[] u1a = u[1].getAccounts();
		
		DateTime dt_orig = RuntimeAPI.now();		
		RuntimeAPI.shiftTime(22594900L);
		
		GlobalParameters.RATE_SAVINGS.set(.01);
		RuntimeAPI.shiftTime(30000000L);
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1000000.00 - 1000 * expectedCycles, u0a[0].getBalance(), 0);
		// TODO: Verify interest being paid properly
		assertEquals(1000 * expectedCycles, u1a[0].getBalance(), 0);
	}

}
