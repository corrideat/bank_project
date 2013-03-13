package user;

import date.DateTime;

public class AccountManager extends Employee {

	public AccountManager(String firstName, String lastName, DateTime birthday,	int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.ACCOUNT_MANAGER);
	}

}
