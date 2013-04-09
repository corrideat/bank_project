package account;

import date.DateTime;
import backend.RuntimeAPI.InterestRate;

abstract public class InterestChargingAccount extends InterestAccount {

	public InterestChargingAccount(AccountType at, InterestRate rate, double offset,
		long number, AccountHolder owner, boolean fix_rate) {
		super(at, rate, offset, number, owner, fix_rate);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onUpdate(DateTime cycle) {
		super.onUpdate(cycle);
		// TODO: Charge interest
	}
	
	@Override
	public boolean debtInstrument() {
		return true;
	}
}
