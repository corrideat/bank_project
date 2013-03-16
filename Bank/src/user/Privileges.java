package user;

import java.util.Map;

import date.DateTime;
import backend.Agent;
import backend.GlobalParameters;
import backend.InsufficientCreditAvailableException;
import account.Account;
import account.AccountHolder;
import account.AccountType;
import account.Transaction;
import account.TransactionValidationException;

public enum Privileges {
	CUSTOMER(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
	TELLER(true, false, true, true, false, false, false, false, false, false, false, false, true, false, false, false),
	AUDITOR(true, true, false, false, false, false, false, true, false, false, true, true, false, false, false, false),
	ACCOUNT_MANAGER(false, false, false, false, true, true, false, false, false, false, false, false, false, true, true, false),
	OPERATION_MANAGER(false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false),
	ACCOUNTANT(false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true);
	
	
	// TODO: Not all possible transactions are covered
	
	final public boolean canCheckBalance;
	final public boolean canListTransactions;
	final public boolean canDeposit;
	final public boolean canWithdraw;
	final public boolean canCreateCustomer;
	final public boolean canCreateAccount;
	final public boolean canTriggerTimeShift;
	final public boolean canVisualizeStatistics;
	final public boolean canSendNotices;
	final public boolean canChangeGlobalParameters;
	final public boolean canSeeFlags; // Employee, fraudulent
	final public boolean canOverturnTransactions;
	final public boolean canSetupAutomaticTransactions;
	final public boolean canSeeSSN;
	final public boolean canSeeCap;
	final public boolean canSetCap;
	
	
	Privileges(final boolean checkBalance, final boolean listTransactions, final boolean deposit, final boolean withdraw, final boolean createCustomer, final boolean createAccount, final boolean triggerTimeShift, final boolean visualizeStatistics, final boolean sendNotices, final boolean changeGlobalParameters, final boolean seeFlags, final boolean overturnTransactions, final boolean setupAutomaticTransactions, final boolean seeSSN, final boolean seeCap, final boolean setCap) {
		canCheckBalance = checkBalance;
		canListTransactions = listTransactions;
		canDeposit = deposit;
		canWithdraw = withdraw;
		canCreateCustomer = createCustomer;
		canCreateAccount = createAccount;
		canTriggerTimeShift = triggerTimeShift; 
		canVisualizeStatistics = visualizeStatistics;
		canSendNotices = sendNotices;
		canChangeGlobalParameters = changeGlobalParameters;
		canSeeFlags = seeFlags;
		canOverturnTransactions = overturnTransactions;
		canSetupAutomaticTransactions = setupAutomaticTransactions;
		canSeeSSN = seeSSN;
		canSeeCap = seeCap;
		canSetCap = setCap;
	}
	
	public double checkBalance(Account a) {
		if (canCheckBalance) {
			return a.getBalance();
		} else throw new SecurityException();
	}
	
	public void deposit(Agent ag, Account a, double amount) throws TransactionValidationException {
		if (canDeposit) {
			if (amount < 0) throw new IllegalArgumentException();
			Transaction t = new Transaction(ag, a, amount, "Deposit");
			a.postTransaction(t);
		} else throw new SecurityException();
	}
	
	public void withdraw(Agent ag, Account a, double amount) throws TransactionValidationException {
		if (canWithdraw) {
			if (amount < 0) throw new IllegalArgumentException();
			Transaction t = new Transaction(ag, a, -amount, "Withdrawal");
			a.postTransaction(t);
		} else throw new SecurityException();
	}

	public Customer canCreateCustomer(String firstName, String lastName, DateTime birthday,	int ssn, AccountType type , Map<String, Object> params) throws InsufficientCreditAvailableException {
		if (canListTransactions) {
			Customer c = new Customer(lastName, lastName, birthday, ssn);
			Account a = type.open(c, params);
			c.assignAccount(a);
			return c;
		} else throw new SecurityException();
	}
	
	public Account canCreateAccount(AccountHolder ah, AccountType type, Map<String, Object> params) throws InsufficientCreditAvailableException {
		if (canListTransactions) {
			return type.open(ah, params);
		} else throw new SecurityException();
	}
	
	public void triggerTimeShift() {
		if (canTriggerTimeShift) {
			// TODO: Do stuff
		} else throw new SecurityException();
	}
	
	// TODO: Create a Statistics class for statistics?
	
	public void sendNotice(Message m) {
		// TODO: Do stuff
	}
	
	public void adjustGlobalParameter(GlobalParameters gp, double value) {
		// TODO: Do stuff
	}
	
	public DateTime reportedFraudulent(Transaction t) {
		return null; // TODO: Do stuff
	}
	
	public boolean employeeFlag(Account t) {
		return false; // TODO: Do stuff
	}
	
	public void overturnTransaction(Transaction t) {
		// TODO: Do stuff
	}
	
	// TODO: Implement all transactions
	
}
