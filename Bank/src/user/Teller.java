package user;

import date.DateTime;
import backend.Agent;

public class Teller extends Employee implements Agent {

	public Teller(String firstName, String lastName, DateTime birthday, int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.TELLER);
	}

}
