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
import account.AutomatedTransaction;
import account.CD;
import account.CD.CD_type;
import account.InterestAccount;
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.User;
import date.DateTime;

@RunWith(Parameterized.class)
public class CDTest {
	static User accountManager;	
	private Account a;
	
	final private int length;
	final private CD_type type;
	
	final private int onemonth = 2629740;
	
	@Parameters
    public static Collection<Object[]> configs() {
    	Collection<Object[]> configs = new ArrayList<Object[]>();
    	
    	configs.add(new Object[]{CD_type.CD_6M, 6});
    	configs.add(new Object[]{CD_type.CD_2Y, 24});
    	configs.add(new Object[]{CD_type.CD_5Y, 60});
    	
    	return configs;
    }
    
    public CDTest(CD_type type, int length) {
    	this.type = type;
    	this.length = length;
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
		
		params.put(AccountParameters.CD_TYPE, type);
		
		GlobalParameters.MASTER_RATE_SAVINGS.set(.16);
		GlobalParameters.OFFSET_RATE_SAVINGS.set(.04);
		GlobalParameters.OFFSET_RATE_CD6M.set(.04);		
		GlobalParameters.OFFSET_RATE_CD1Y.set(0);
		GlobalParameters.OFFSET_RATE_CD2Y.set(0);
		GlobalParameters.OFFSET_RATE_CD3Y.set(0);
		GlobalParameters.OFFSET_RATE_CD4Y.set(0);
		GlobalParameters.OFFSET_RATE_CD5Y.set(0);
		GlobalParameters.CD_MINIMUM_BALANCE.set(-1);
		
		a = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.CD, params);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		a.immediateClose();
	}

	@Test
	public void duration() {
		assertEquals(((CD)a).m_eType.getDuration(), length); 
	}	

	
	@Test
	public void initialBalance() {
		assertEquals(0D, a.getBalance(), 0);
	}
	
	@Test
	public void rightRate() {
		assertEquals(.24, ((InterestAccount)a).getAccountRate(), 0.1);
	}
	
	@Test
	public void feesNotAssessed() {
		RuntimeAPI.shiftTime(31556900); // One Year
		
		assertEquals(0D, a.getBalance(), 0);
	}

	@Test
	public void closedUponNotMeetingMinimumBalance() {
		GlobalParameters.CD_MINIMUM_BALANCE.set(0.01);
		
		RuntimeAPI.shiftTime(onemonth*length/2); // A little bit over two months
		
		assertEquals(0, a.getBalance(), 0);
		assertTrue(a.isClosed());
	}

	@Test(expected=TransactionValidationException.class)
	public void nonNegativeBalance() throws TransactionValidationException {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, -0.001, "Withdrawal");
		a.postTransaction(t);
	}
	
	@Test
	public void interest_simple_test_maturity() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		RuntimeAPI.shiftTime(length*onemonth*2);
		
		assertEquals(1313 * Math.pow(1.02, length), a.getBalance(), .1);
	}
	
	@Test
	public void interest_simple_test_before_maturity() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		DateTime dt_orig = RuntimeAPI.now();
		RuntimeAPI.shiftTime(onemonth*length/2);
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1313 * Math.pow(1.02, expectedCycles), a.getBalance(), .1);
	}
	
	@Test
	public void penalty_before_maturity() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		DateTime dt_orig = RuntimeAPI.now();
		RuntimeAPI.shiftTime(onemonth*length/2);
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1313 * Math.pow(1.02, expectedCycles), a.getBalance(), .1);
		
		t = new Transaction(LocalAgent.agent, a, -0.001, "Withdrawal");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		assertEquals(1313 * Math.pow(1.02, expectedCycles) * (1D - 0.02D*6D) , a.getBalance(), .1);
	}
	
	@Test
	public void no_penalty_after_maturity() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		RuntimeAPI.shiftTime(onemonth*length*2);
		RuntimeAPI.now();
		
		assertEquals(1313 * Math.pow(1.02, length), a.getBalance(), .1);
		
		t = new Transaction(LocalAgent.agent, a, -0.001, "Withdrawal");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		assertEquals(1313 * Math.pow(1.02, length) , a.getBalance(), .1);
	}


	
	@Test
	public void interest_many_transactions() {
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
		RuntimeAPI.shiftTime(onemonth*length*2); // One Year
		DateTime dt_final = RuntimeAPI.now();
		
		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		assertEquals(1000*(expectedCycles-length)+1000 * (1-Math.pow(1.02, length))/(1-1.02), a.getBalance(), 1);
		
		checking.immediateClose();
	}
	
	@Test
	public void interest_variable() {
		Transaction t;
		
		t = new Transaction(LocalAgent.agent, a, 1313, "Deposit");
		try {
			a.postTransaction(t);
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to deposit");
		}
		
		RuntimeAPI.shiftTime(onemonth*length/2); // 3.5 Months
		GlobalParameters.MASTER_RATE_SAVINGS.set(.32);
		RuntimeAPI.shiftTime(onemonth*length); // 3.5 Months
		
		assertEquals(1313 * Math.pow(1.02, length), a.getBalance(), 1);
	}


}
