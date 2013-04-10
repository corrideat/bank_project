package account;

import date.DateTime;
import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

public class Loan extends InterestChargingAccount {
	final double minimumMonthlyPayment;

	public Loan(double principal, short installments, double offset, long number, AccountHolder owner) throws InsufficientCreditAvailableException {
		super(AccountType.LOAN, RuntimeAPI.InterestRate.LOAN, offset, number, owner, true);
		this.getAccountRate();
		if (installments<1) throw new IllegalArgumentException();
		if (principal<0) throw new IllegalArgumentException();
		RuntimeAPI.adjustCap(-principal);
		new InternalTransaction(-principal, "Principal");
		// Formula from: http://ncalculators.com/loan/installment-loan-payoff-calculator.htm
		minimumMonthlyPayment = Math.ceil(principal * (this.getAccountRate()/12D) * Math.pow(1D - Math.pow(1D+this.getAccountRate()/12D, -1D), -1D) * 100D)/100D;
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
	}
	
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		// TODO: Charge penalty whenever a monthly payment was missed or did not meet the minimum
		super.onUpdate(cycle, pb);
	}
}
