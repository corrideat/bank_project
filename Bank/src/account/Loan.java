package account;

import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

public class Loan extends InterestChargingAccount {
	final double minimumMonthlyPayment;

	public Loan(double principal, short installments, double offset, long number, AccountHolder owner) throws InsufficientCreditAvailableException {
		super(RuntimeAPI.InterestRate.LOAN, offset, number, owner, true);
		this.getAccountRate();
		if (installments<1) throw new IllegalArgumentException();
		if (principal<0) throw new IllegalArgumentException();
		RuntimeAPI.adjustCap(-principal);
		new InternalTransaction(-principal, "Principal");
		// TODO: Calculate the minimum monthly payment appropriately
		minimumMonthlyPayment = 1L;
	}


	// TODO: Each payment should probably increase the cap by at least the principal
	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		// Safety check
		// Allow withdrawals of positive balances on closed accounts. (However, such a situation should never arise)		
		if (t.m_dAmount<0 || (this.isClosed() && (t.m_dAmount+this.getBalance())<0D)) {
			throw new IllegalOperationException();
		} else if ((t.m_dAmount+this.getBalance())>0D) { // Too large of a payment. This should avoid most situations that the second part of the if above handles.
			throw new IllegalOperationException(); // TODO: Maybe create a specialized Exception for this case?
		}
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		if (this.getBalance() >= 0) { // Greater than is a safety check
			this.close();			
		}
		// TODO: Charge penalty whenever a monthly payment was missed or did not meet the minimum 
	}
}
