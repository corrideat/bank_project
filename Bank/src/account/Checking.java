package account;

import backend.RuntimeAPI;

public class Checking extends Account {

	public Checking(long number, AccountHolder owner) {
		super(AccountType.CHECKING, number, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		// Do not allow balance to go below 0
		if (t.m_dAmount<0D && (this.getBalance()+t.m_dAmount) < RuntimeAPI.CheckingMinimumBalance()) {
			new InternalTransaction(RuntimeAPI.CheckingOverdraftFee(), "Overdraft Fee");
			throw new InsufficientFundsException();
		}		
	}
	
	protected void onUpdate() {
		// TODO: Do stuff, charge service fees
	}
}
