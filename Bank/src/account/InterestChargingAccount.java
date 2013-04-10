package account;

import backend.RuntimeAPI.InterestRate;

abstract public class InterestChargingAccount extends InterestAccount {

	public InterestChargingAccount(AccountType at, InterestRate rate, double offset,
		long number, AccountHolder owner, boolean fix_rate) {
		super(at, rate, offset, number, owner, fix_rate);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean debtInstrument() {
		return true;
	}
}
