package account;

import date.DateTime;
import backend.GlobalParameters;
import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

public class LineOfCredit extends InterestChargingAccount {
	double m_dCreditLimit;

	public LineOfCredit(double offset, double creditLimit, long number, AccountHolder owner) throws InsufficientCreditAvailableException {
		super(AccountType.LOC, RuntimeAPI.InterestRate.LOC, offset, number, owner, false);
		m_dCreditLimit = 0D;
		this.setLimit(creditLimit);
		m_dCreditLimit = creditLimit;
	}

	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		double potential = t.m_dAmount+this.getBalance();
		if ((t.m_dAmount>0D && potential>GlobalParameters.LOC_MAXIMUM_BALANCE.get()) || (t.m_dAmount<0D && potential<m_dCreditLimit)) {
			throw new IllegalOperationException(); // TODO: Maybe create a specialized Exception for this case?
		}
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		super.onUpdate(cycle, pb); // Charge Interest, etc.
		if (pb.starting_balance <0D && pb.credits<(Math.max(Math.min(-pb.starting_balance, GlobalParameters.LOC_MINIMUM_PAYMENT.get()), -pb.starting_balance*GlobalParameters.LOC_MINIMUM_PAYMENT_FRACTION.get()))) {
			new InternalTransaction(GlobalParameters.LOC_LATE_PENALTY.get(), GlobalParameters.LOC_LATE_PENALTY.describe());
		}
	}
	
	protected void setLimit(double newCreditLimit) throws InsufficientCreditAvailableException {
		if (newCreditLimit < 0D) throw new IllegalArgumentException();
		RuntimeAPI.adjustCap(this.m_dCreditLimit - newCreditLimit);
		this.m_dCreditLimit = newCreditLimit;
	}
	
	protected double getLimit() {
		return this.m_dCreditLimit;
	
	}
}
