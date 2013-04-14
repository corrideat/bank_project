package account;

import date.DateTime;
import backend.GlobalParameters;
import backend.RuntimeAPI.InterestRate;

public class Savings extends InterestBearingAccount {
	
	protected Savings(long number, AccountHolder owner) {
		super(AccountType.SAVINGS, InterestRate.SAVINGS, number, owner, false);
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		super.onUpdate(cycle, pb);
		if (pb.average_balance < GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE.get()) {
			new InternalTransaction(Math.min(0D, GlobalParameters.SAVINGS_FEE.get()), GlobalParameters.SAVINGS_FEE.describe());
		}
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
	}	
}
