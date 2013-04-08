package account;

import date.DateTime;
import backend.RuntimeAPI.InterestRate;

public class Savings extends InterestBearingAccount {
	
	public Savings(long number, AccountHolder owner) {
		super(AccountType.SAVINGS, InterestRate.SAVINGS, number, owner, false);
	}
	
	protected void onUpdate(DateTime cycle) {
		// TODO: Do stuff, charge service fees
	}
	
}
