package account;

public interface AccountHolder {
	public String getFirstName();
	public String getLastName();
	public int getAge();
	public int getSSN();
	public void assignAccount(Account a);
	public boolean isEmployee();
	public Account[] getAccounts();
	// TODO: get Customer?
}
