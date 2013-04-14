package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backend.InsufficientCreditAvailableException;

import account.Account;
import account.AccountHolder;
import account.AccountType;

import date.DateTime;

public class Customer extends User implements AccountHolder {
	protected final List<Account> m_aAccounts;
	
	protected Customer(final String firstName, final String lastName, final DateTime birthday,
			final int ssn, final String username, final String password, final AccountType type, final Map<account.AccountParameters, Object> params) throws InsufficientCreditAvailableException {
		super(firstName, lastName, birthday, ssn, Privileges.CUSTOMER, username, password);
		m_aAccounts = new ArrayList<Account>();
		Account a = type.open(this, params);
		this.assignAccount(a);
	}
	
	protected Customer(final String firstName, final String lastName, final DateTime birthday,
			final int ssn, final String username, final String password) {
		super(firstName, lastName, birthday, ssn, Privileges.CUSTOMER, username, password);
		m_aAccounts = new ArrayList<Account>();
	}
	

	@Override
	public boolean isEmployee() {
		return false;
	}
	
	@Override
	public void assignAccount(Account a) {
		if (a.getAccountHolder() == this) {
			m_aAccounts.add(a);
		} else {
			throw new IllegalArgumentException();
		}
	}

	// We want actual reference values
	@Override
	public Account[] getAccounts() {
		return m_aAccounts.toArray(new Account[0]);
	}

	@Override
	final AccountHolder getAH() {
		return this;
	}
}
