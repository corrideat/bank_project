package user;

import date.DateTime;

public class Auditor extends Employee {

	public Auditor(String firstName, String lastName, DateTime birthday, int ssn, String username, String password) {
		super(firstName, lastName, birthday, ssn, Privileges.AUDITOR, username, password);
	}

}
