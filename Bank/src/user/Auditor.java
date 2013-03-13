package user;

import date.DateTime;

public class Auditor extends Employee {

	public Auditor(String firstName, String lastName, DateTime birthday, int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.AUDITOR);
	}

}
