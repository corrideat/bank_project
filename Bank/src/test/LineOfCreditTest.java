package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import backend.GlobalParameters;
import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

import account.Account;
import account.AccountParameters;
import account.AccountType;
import account.InterestAccount;
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.User;
import date.DateTime;

@RunWith(Parameterized.class)
public class LineOfCreditTest {
	static User accountManager;	
	private Account a;
	
	private double offset;
	private double limit;
	
	final private int onemonth = 2629740;
	
	@Parameters
	public static Collection<Object[]> configs() {
		Collection<Object[]> configs = new ArrayList<Object[]>();
			
		configs.add(new Object[]{1000D, 0D});
		configs.add(new Object[]{1000D, 0.12D});
		configs.add(new Object[]{74238D, 0D});
		configs.add(new Object[]{74238D, -0.06D});
		configs.add(new Object[]{74238D, -1D});
		
		return configs;
	}
	
	public LineOfCreditTest(double limit, double offset) {
		this.limit = limit;
		this.offset = offset;
	}
	
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
		Map<AccountParameters, Object> params = new EnumMap<AccountParameters, Object>(AccountParameters.class);
		
		params.put(AccountParameters.OFFSET, offset);
		params.put(AccountParameters.LIMIT, limit);
		
		GlobalParameters.MASTER_RATE_LOAN.set(.20D);
		GlobalParameters.LOC_LATE_PENALTY.set(-5);
		GlobalParameters.OFFSET_RATE_LOAN.set(.02);
		GlobalParameters.OFFSET_RATE_LOC.set(.02);
		GlobalParameters.LOC_MAXIMUM_BALANCE.set(0D);
		GlobalParameters.LOC_MINIMUM_PAYMENT.set(20D); 
		GlobalParameters.LOC_MINIMUM_PAYMENT_FRACTION.set(0.2D);
		
		RuntimeAPI.adjustCap(+1E6);
		
		a = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.LOC, params);
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
		assertEquals(0, a.getBalance(), 0.01);
	}
	
	@Test
	public void rightRate() {
		assertEquals(Math.max(0, .24+offset), ((InterestAccount)a).getAccountRate(), 0.1);
	}
	
	@Test
	public void rightRateChange() {
		GlobalParameters.MASTER_RATE_LOAN.set(1);
		assertEquals(Math.max(0, 1.04+offset), ((InterestAccount)a).getAccountRate(), 0.1);
	}
	
	@Test
	public void feesNotAssessed() {
		RuntimeAPI.shiftTime(onemonth*12);

		assertEquals(0, a.getBalance(), 0.01);
	}

	@Test
	public void feesAssessedInsufficientPayment() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, -limit/2, "Withdrawal");

		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to transfer.");
		}		
		
		DateTime dt_orig = RuntimeAPI.now();
		RuntimeAPI.shiftTime(onemonth*12);
		DateTime dt_final = RuntimeAPI.now();

		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		if (1.02+offset/12D > 1) {
			assertEquals(t.m_dAmount*Math.pow(1.02+offset/12D, expectedCycles)-5D*(1-Math.pow(1.02+offset/12D, expectedCycles-1))/(1.0D-1.02D-offset/12D), a.getBalance(), 0.1D);
		} else {
			assertEquals(t.m_dAmount-5D*(expectedCycles-1), a.getBalance(), 0.1D);
		}
	}
	
	@Test
	public void overpay() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 0.01, "Overpayment");

		try {
			a.postTransaction(t);
			fail("Able to transfer.");
		} catch (TransactionValidationException e) {
			GlobalParameters.LOC_MAXIMUM_BALANCE.set(10D);
			try {
				a.postTransaction(t);
				t = new Transaction(LocalAgent.agent, a, 10.01, "Overpayment");
				try {
					a.postTransaction(t);
					fail("Aable to transfer.");
				} catch (TransactionValidationException g) {}
			} catch (TransactionValidationException f) {
				fail("Unable to transfer.");
			}
		}
	}
	
	@Test(timeout=200)
	public void payRegularly() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, -limit, "Withdrawal");

		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to transfer.");
		}		
		
		while(a.getBalance()<-1E-4) {
			t = new Transaction(LocalAgent.agent, a, Math.min(Math.max((-a.getBalance())*.2D, 20D), -a.getBalance()), "Payment");
			try {
				a.postTransaction(t);
			} catch (TransactionValidationException e) {
				e.printStackTrace();
				fail("Unable to pay.");
			}
			DateTime dt_orig = RuntimeAPI.now();
			DateTime dt_new = new DateTime(dt_orig.getYear(),dt_orig.getMonth()+1,dt_orig.getDay()+1);
			RuntimeAPI.shiftTime((dt_new.subtract(dt_orig).getRawCountInMillis())/1000L);
		}
	}
}