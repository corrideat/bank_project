package user;

import account.Account;
import account.AccountHolder;
import date.DateTime;

public abstract class Employee extends User {
	public class EmployeeCustomer extends Customer {

		public EmployeeCustomer(String username, String password) {
			super(Employee.this.m_sFirstName, Employee.this.m_sLastName, Employee.this.m_dtBirthday, Employee.this.m_iSSN, username+"_customer", password);
		}
		
		@Override
		final public boolean isEmployee() {
			return Employee.this.isEmployee();
		}

	}
	
	final EmployeeCustomer m_cCustomerProfile;

	public Employee(String firstName, String lastName, DateTime birthday, int ssn, Privileges p, String username, String password) {
		super(firstName, lastName, birthday, ssn, p, username, password);
		m_cCustomerProfile = new EmployeeCustomer(username, password);
	}
	
	final public boolean isEmployee() {
		return true;
	}
	
	@Override
	final public void assignAccount(Account a) {
		if (a.getAccountHolder() == this) { // TODO: This could be conflictive eventually
			m_cCustomerProfile.m_aAccounts.add(a);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	final public Account[] getAccounts() {
		return m_cCustomerProfile.getAccounts();
	}
	
	@Override
	final public AccountHolder getAH() {
		return m_cCustomerProfile;
	}
}
