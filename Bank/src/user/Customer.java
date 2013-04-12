package user;

import java.util.ArrayList;
import java.util.List;

import account.Account;

import date.DateTime;

public class Customer extends User {
	protected final List<Account> m_aAccounts;
	
	public Customer(final String firstName, final String lastName, final DateTime birthday,
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
		return null;
	}
}
