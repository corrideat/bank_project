package user;

import java.util.EnumSet;
import java.util.Map;

import date.DateTime;
import backend.Agent;
import backend.Core;
import backend.GlobalParameters;
import backend.RuntimeAPI;
import backend.InsufficientCreditAvailableException;
import account.Account;
import account.AccountHolder;
import account.AccountType;
import account.AutomatedTransaction;
import account.LineOfCredit;
import account.Transaction;
import account.TransactionValidationException;

public enum Privileges {
	CUSTOMER(),
	TELLER(acos.seeBasicInformation, acos.checkBalance, acos.deposit, acos.withdraw, acos.setupAutomaticTransactions),
	AUDITOR(acos.seeBasicInformation, acos.listTransactions, acos.seeEmployeeFlags, acos.seeFraudulentFlags, acos.overturnTransactions),
	ACCOUNT_MANAGER(acos.seeSSN, acos.seeBasicInformation, acos.createAccount, acos.createCustomer, acos.seeCap, acos.adjustLOCLimit),
	OPERATION_MANAGER(acos.sendNotices, acos.triggerTimeShift, acos.setGlobalParameters),
	ACCOUNTANT(acos.seeStatistics, acos.seeCap, acos.setCap);
	
	private enum acos { // Access Control Objects
		checkBalance,
		listTransactions,
		deposit,
		withdraw,
		createCustomer,
		createAccount,
		triggerTimeShift,
		seeStatistics,
		sendNotices,
		setGlobalParameters,
		seeEmployeeFlags,
		seeFraudulentFlags,
		overturnTransactions,
		setupAutomaticTransactions,
		seeSSN,
		seeCap,
		setCap,
		seeBasicInformation,
		adjustLOCLimit;
	}
	
	final private EnumSet<acos> m_esPermissions;
	
	Privileges(acos ... permissions) {
		m_esPermissions = EnumSet.noneOf(acos.class);
		for(acos p:permissions) {
			m_esPermissions.add(p);
		}
	}
	
	public double checkBalance(Account a) {
		if (m_esPermissions.contains(acos.checkBalance)) {
			return a.getBalance();
		} else throw new SecurityException();
	}
	
	public void deposit(Agent ag, Account a, double amount) throws TransactionValidationException {
		if (m_esPermissions.contains(acos.deposit)) {
			if (amount < 0) throw new IllegalArgumentException();
			Transaction t = new Transaction(ag, a, amount, "Deposit");		
			a.postTransaction(t);
		} else throw new SecurityException();
	}
	
	public void withdraw(Agent ag, Account a, double amount) throws TransactionValidationException {
		if (m_esPermissions.contains(acos.withdraw)) {
			if (amount < 0) throw new IllegalArgumentException();	
			Transaction t = new Transaction(ag, a, -amount, "Withdrawal");		
			a.postTransaction(t);
		} else throw new SecurityException();
	}
	
	public void transfer(Agent ag, Account origin, long destination, double amount) throws TransactionValidationException {
		if (m_esPermissions.contains(acos.deposit) && m_esPermissions.contains(acos.withdraw)) {
			if (amount < 0) throw new IllegalArgumentException();
			origin.transfer(ag, destination, amount, "Money Transfer to "+destination);
		} else throw new SecurityException();
	}

	public Customer createCustomer(String firstName, String lastName, DateTime birthday, String username, String password, int ssn, AccountType type , Map<account.AccountParameters, Object> params) throws InsufficientCreditAvailableException {
		if (m_esPermissions.contains(acos.createCustomer)) {
				if (RuntimeAPI.getUser(username) == null) {
				return new Customer(firstName, lastName, birthday, ssn, username, password, type, params);				
			} else {
				throw new IllegalArgumentException();
			}
		} else throw new SecurityException();
	}
	
	public Account createAccount(AccountHolder ah, AccountType type, Map<account.AccountParameters, Object> params) throws InsufficientCreditAvailableException {
		if (m_esPermissions.contains(acos.createAccount)) {
			Account a = type.open(ah, params);
			ah.assignAccount(a);
			return a;
		} else throw new SecurityException();
	}
	
	public void triggerTimeShift(final long seconds) {
		if (m_esPermissions.contains(acos.triggerTimeShift)) {
			RuntimeAPI.shiftTime(seconds);
		} else throw new SecurityException();
	}
	
	// TODO: Create a Statistics class for statistics?
	
	public void sendNotice(Account a, String m) {
		if (m_esPermissions.contains(acos.sendNotices)) {
			a.getAccountHolder().sendNotification(a, m);
		} else throw new SecurityException();
	}
	
	public void setGlobalParameter(GlobalParameters gp, double value) {
		if (m_esPermissions.contains(acos.setGlobalParameters)) {
			gp.set(value);
		} else throw new SecurityException();
	}
	
	public DateTime reportedFraudulent(Transaction t) {
		if (m_esPermissions.contains(acos.seeFraudulentFlags)) {
			return t.m_dtReportedFraudulent;
		} else throw new SecurityException();
	}
	
	public boolean employeeFlag(Account a) {
		if (m_esPermissions.contains(acos.seeEmployeeFlags)) {
			return a.getAccountHolder().isEmployee();
		} else throw new SecurityException();
	}
	
	public void overturnTransaction(Transaction t) {
		if (m_esPermissions.contains(acos.overturnTransactions)) {
			t.m_aTarget.overturnTransaction(t);
		} else throw new SecurityException();
	}

	public void overturnTransaction(Account a, int transaction_index) {
		if (m_esPermissions.contains(acos.overturnTransactions)) {
			a.overturnTransaction(transaction_index);
		} else throw new SecurityException();
	}
	
	public void setupAutomaticTransaction(Account origin, long destination, String description, double amount) {
		if (m_esPermissions.contains(acos.setupAutomaticTransactions)) {
			AutomatedTransaction at = new AutomatedTransaction(amount, destination, description); 
			origin.setupAutomatedTransaction(at);
		} else throw new SecurityException();
	}
	
	public void cancelAutomaticTransaction(Account origin, AutomatedTransaction at) {
		if (m_esPermissions.contains(acos.setupAutomaticTransactions)) {
			origin.cancelAutomatedTransaction(at);
		} else throw new SecurityException();
	}
	
	public void cancelAutomaticTransaction(Account origin, int at_index) {
		if (m_esPermissions.contains(acos.setupAutomaticTransactions)) {
			origin.cancelAutomatedTransaction(at_index);
		} else throw new SecurityException();
	}
	
	public int seeSSN(AccountHolder ah) {
		if (m_esPermissions.contains(acos.seeSSN)) {
			return ah.getSSN();
		} else throw new SecurityException();
	}
	
	public double seeCap() {
		if (m_esPermissions.contains(acos.seeCap)) {
			return RuntimeAPI.getCap();
		} else throw new SecurityException();
	}
	
	public void adjustCap(double delta) {
		if (m_esPermissions.contains(acos.setCap)) {
			RuntimeAPI.forcefulAdjustCap(delta);
		} else throw new SecurityException();
	}
	
	public void setCap(double exactAmount) {
		if (m_esPermissions.contains(acos.setCap)) {
			RuntimeAPI.forcefulAdjustCap(exactAmount-RuntimeAPI.getCap());
		} else throw new SecurityException();
	}
	
	public AccountHolder getAccountHolder(final String username) {
		if (m_esPermissions.contains(acos.seeBasicInformation)) {
			return Core.m_auUsers.get(username).getAH();
		} else throw new SecurityException();
	}
	
	public String getAccountHolderFirstName(AccountHolder ah) {
		if (m_esPermissions.contains(acos.seeBasicInformation)) {
			return ah.getFirstName();
		} else throw new SecurityException();
	}

	public String getAccountHolderLastName(AccountHolder ah) {
		if (m_esPermissions.contains(acos.seeBasicInformation)) {
			return ah.getLastName();
		} else throw new SecurityException();
	}
	
	public int getAccountHolderAge(AccountHolder ah) {
		if (m_esPermissions.contains(acos.seeBasicInformation)) {
			return ah.getAge();
		} else throw new SecurityException();
	}
	
	public Account[] getAccountHolderAccounts(AccountHolder ah) {
		if (m_esPermissions.contains(acos.seeBasicInformation)) {
			return ah.getAccounts();
		} else throw new SecurityException();
	}
	
	public double seeLOCLimit(LineOfCredit a, double limit) {
		if (m_esPermissions.contains(acos.adjustLOCLimit)) {
			return a.getLimit();
		} else throw new SecurityException();
	}
	
	public void adjustLOCLimit(LineOfCredit a, double limit) throws InsufficientCreditAvailableException {
		if (m_esPermissions.contains(acos.adjustLOCLimit)) {
			a.setLimit(limit);
		} else throw new SecurityException();
	}
	
	public Transaction[] listTransactions(Account a) throws InsufficientCreditAvailableException {
		if (m_esPermissions.contains(acos.listTransactions)) {
			return a.getTransactions();
		} else throw new SecurityException();
	}	

}
