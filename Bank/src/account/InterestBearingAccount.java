package account;

import backend.RuntimeAPI.InterestRate;

public abstract class InterestBearingAccount extends InterestAccount {

	public InterestBearingAccount(AccountType at, InterestRate rate, long number, AccountHolder owner, boolean fix_rate) {
		super(at, rate, 0, number, owner, fix_rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		// Do not allow balance to go below 0
		if (t.m_dAmount<0D && (this.getBalance()+t.m_dAmount) < 0D) {
			throw new InsufficientFundsException();
		}
	}
	
}
