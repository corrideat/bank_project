package user;

import date.DateTime;
import backend.Agent;

public class Teller extends Employee implements Agent {

	public Teller(String firstName, String lastName, DateTime birthday, int ssn, String username, String password) {
		super(firstName, lastName, birthday, ssn, Privileges.TELLER, username, password);
	}

	@Override
	public boolean isInternal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}

}
