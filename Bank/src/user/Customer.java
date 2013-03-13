package user;

import java.util.ArrayList;
import java.util.List;

import account.Account;

import date.DateTime;

public class Customer extends User {
	protected final List<Account> m_aAccounts;
	
	public Customer(String firstName, String lastName, DateTime birthday,
			int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.CUSTOMER);
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
