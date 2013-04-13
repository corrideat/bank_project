package user;

import date.DateTime;

public class AccountManager extends Employee {

	public AccountManager(String firstName, String lastName, DateTime birthday,	int ssn, String username, String password) {
		super(firstName, lastName, birthday, ssn, Privileges.ACCOUNT_MANAGER, username, password);
	}

}
