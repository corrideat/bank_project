package user;

import date.DateTime;

public class OperationManager extends Employee {

	public OperationManager(String firstName, String lastName,
			DateTime birthday, int ssn, String username, String password) {
		super(firstName, lastName, birthday, ssn, Privileges.OPERATION_MANAGER, username, password);
	}

}
