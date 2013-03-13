package user;

import date.DateTime;

public class OperationManager extends Employee {

	public OperationManager(String firstName, String lastName,
			DateTime birthday, int ssn) {
		super(firstName, lastName, birthday, ssn, Privileges.OPERATION_MANAGER);
	}

}
