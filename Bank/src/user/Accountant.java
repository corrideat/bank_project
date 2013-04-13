package user;

import date.DateTime;

public class Accountant extends Employee {

	public Accountant(String firstName, String lastName, DateTime birthday,
			int ssn, String username, String password) {
		super(firstName, lastName, birthday, ssn, Privileges.ACCOUNTANT, username, password);
	}

}
