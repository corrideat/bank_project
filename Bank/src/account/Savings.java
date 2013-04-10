package account;

import date.DateTime;
import account.Account.InternalTransaction;
import account.Account.PeriodBalance;
import backend.GlobalParameters;
import backend.RuntimeAPI.InterestRate;

public class Savings extends InterestBearingAccount {
	
	public Savings(long number, AccountHolder owner) {
		super(AccountType.SAVINGS, InterestRate.SAVINGS, number, owner, false);
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		super.onUpdate(cycle, pb);
		if (pb.average_balance < GlobalParameters.SAVINGS_MINIMUM_BALANCE.get()) {
			// TODO: If the account hasn't been 
			new InternalTransaction(GlobalParameters.SAVINGS_FEE.get(), GlobalParameters.SAVINGS_FEE.describe());
		}
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
	}	
}
