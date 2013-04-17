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
import account.InterestAccount;
import account.Loan;
import account.Transaction;
import account.TransactionValidationException;

import user.AccountManager;
import user.User;
import date.DateTime;

@RunWith(Parameterized.class)
public class LoanTest {
	static User accountManager;	
	private Account a;
	
	private short installments;
	private double offset;
	private double principal;
	
	final private int onemonth = 2629740;
	
	@Parameters
    public static Collection<Object[]> configs() {
    	Collection<Object[]> configs = new ArrayList<Object[]>();
    	
    	configs.add(new Object[]{1000D, (short)12, 0D});
    	configs.add(new Object[]{1000D, (short)12, 0.12D});
    	configs.add(new Object[]{74238D, (short)24, 0D});
    	configs.add(new Object[]{74238D, (short)36, -0.06D});
    	configs.add(new Object[]{74238D, (short)36, -1D});
    	
    	return configs;
    }
    
    public LoanTest(double principal, short installments, double offset) {
    	this.principal = principal;
    	this.installments = installments;
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
		
		params.put(AccountParameters.INSTALLMENTS, installments);
		params.put(AccountParameters.OFFSET, offset);
		params.put(AccountParameters.PRINCIPAL, principal);
		
		GlobalParameters.MASTER_RATE_LOAN.set(.20);
		GlobalParameters.LOAN_LATE_PENALTY.set(-5);
		GlobalParameters.OFFSET_RATE_LOAN.set(.04);
		
		RuntimeAPI.adjustCap(+1E6);
		
		a = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.LOAN, params);
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
		assertEquals(-principal, a.getBalance(), 0.1);
	}
	
	@Test
	public void rightRate() {
		GlobalParameters.MASTER_RATE_LOAN.set(1);
		assertEquals(Math.max(0, .24+offset), ((InterestAccount)a).getAccountRate(), 0.1);
	}
	
	@Test
	public void feesAssessed() {
		DateTime dt_orig = RuntimeAPI.now();
		RuntimeAPI.shiftTime(onemonth*12);
		DateTime dt_final = RuntimeAPI.now();

		int expectedCycles =  dt_final.getYear()*12+dt_final.getMonth()-dt_orig.getYear()*12-dt_orig.getMonth();
		
		if (1 <= (1.02+(offset/12D))) {
			assertEquals(-(principal*Math.pow(1.02+(offset/12D), expectedCycles - 1) + 5*(1-Math.pow(1.02+(offset/12D), expectedCycles-1D))/(1-(1.02D+(offset/12D))) ), a.getBalance(), 0.5);
		} else {
			assertEquals(-(principal + 5*(expectedCycles-1)), a.getBalance(), 0.5);
		}
	}

	@Test
	public void feesAssessedInsufficientPayment() {
		Account checking = null;
		
		try {
			checking = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.CHECKING, null);
			Transaction t;
			
			t = new Transaction(LocalAgent.agent, checking, 1E6, "Deposit");
			checking.postTransaction(t);
			
			AutomatedTransaction at1 = new AutomatedTransaction(((Loan)a).m_dMinimumMonthlyPayment, a.getAccountNumber(), "Some payment");		
			checking.setupAutomatedTransaction(at1);
		} catch (InsufficientCreditAvailableException e1) {
			e1.printStackTrace();
			fail("Unable to create checking.");
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to transfer.");
		}		
		
		RuntimeAPI.shiftTime(onemonth*installments/3);
		
		double actual = a.getBalance();
		checking.cancelAutomatedTransaction(0);		
		RuntimeAPI.shiftTime(2*onemonth+150000);
		assertEquals((actual*(1.02+(offset/12D))-5D)*(1.02+(offset/12D)), a.getBalance(), -a.getBalance()*1.03);
		
		checking.immediateClose();
	}
	
	@Test
	public void payRegularly() {
		Account checking = null;
		
		try {
			checking = accountManager.m_ePrivileges.createAccount(accountManager, AccountType.CHECKING, null);
			Transaction t;
			
			t = new Transaction(LocalAgent.agent, checking, 1E6, "Deposit");
			checking.postTransaction(t);
			
			AutomatedTransaction at1 = new AutomatedTransaction(((Loan)a).m_dMinimumMonthlyPayment, a.getAccountNumber(), "Some payment");		
			checking.setupAutomatedTransaction(at1);
		} catch (InsufficientCreditAvailableException e1) {
			e1.printStackTrace();
			fail("Unable to create checking.");
		} catch (TransactionValidationException e) {
			e.printStackTrace();
			fail("Unable to transfer.");
		}		
		
		RuntimeAPI.shiftTime(onemonth*(installments+1));
		
		checking.cancelAutomatedTransaction(checking.getAutomatedTransactions()[0]);		
		
		assertEquals(0, a.getBalance(), .5);
		
		checking.immediateClose();
	}
}
