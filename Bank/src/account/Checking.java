package account;

import date.DateTime;
import backend.GlobalParameters;

public class Checking extends Account {

	protected Checking(long number, AccountHolder owner) {
		super(AccountType.CHECKING, number, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		// Do not allow balance to go below 0
		if (t.m_dAmount<0D && (this.getBalance()+t.m_dAmount) < GlobalParameters.CHECKING_MINIMUM_BALANCE.get()) {
			new InternalTransaction(Math.min(0D, GlobalParameters.CHECKING_OVERDRAFT_FEE.get()), GlobalParameters.CHECKING_OVERDRAFT_FEE.describe());
			throw new InsufficientFundsException();
		}		
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		if (pb.average_balance < GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE.get()) {
			// TODO: If the account hasn't been 
			new InternalTransaction(Math.min(0D, GlobalParameters.CHECKING_FEE.get()), GlobalParameters.CHECKING_FEE.describe());
		}
	}
	
	@Override
	protected void onUpdate() {
	}

}