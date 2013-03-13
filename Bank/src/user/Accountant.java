package user;

import date.DateTime;

public class Accountant extends Employee {

	public Accountant(String firstName, String lastName, DateTime birthday,
			int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.ACCOUNTANT);
	}

}
