package account;

import backend.RuntimeAPI.InterestRate;

public class Savings extends InterestBearingAccount {
	
	public Savings(long number, AccountHolder owner) {
		super(AccountType.SAVINGS, InterestRate.SAVINGS, number, owner, false);
	}
	
	protected void onUpdate() {
		// TODO: Do stuff, charge service fees
	}
	
}
