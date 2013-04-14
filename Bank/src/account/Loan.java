package account;

import date.DateTime;
import backend.GlobalParameters;
import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

final public class Loan extends InterestChargingAccount {
	final public double minimumMonthlyPayment;
	final public double principal;
	private boolean hasGraceElapsed;
	private boolean hasBeenRepaid;

	public Loan(final double principal, final short installments, final double offset, final long number, final AccountHolder owner) throws InsufficientCreditAvailableException {
		super(AccountType.LOAN, RuntimeAPI.InterestRate.LOAN, offset, number, owner, true);
		this.getAccountRate();
		if (installments<1) throw new IllegalArgumentException();
		if (principal<0) throw new IllegalArgumentException();
		RuntimeAPI.adjustCap(-principal);
		new InternalTransaction(-principal, "Principal");
		// Formula from: http://ncalculators.com/loan/installment-loan-payoff-calculator.htm
		minimumMonthlyPayment = Math.ceil(principal * (this.getAccountRate()/12D) * Math.pow(1D - Math.pow(1D+this.getAccountRate()/12D, -1D*installments), -1D) * 100D)/100D;
		this.principal = principal;
		this.hasGraceElapsed = false;
		this.hasBeenRepaid = false;
	}


	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		// Safety check
		// Allow withdrawals of positive balances on closed accounts. (However, such a situation should never arise)
		if (t.m_dAmount<0 && (!this.hasBeenRepaid || (t.m_dAmount+this.getBalance())<0D)) {
			throw new IllegalOperationException();
		} else if ((t.m_dAmount+this.getBalance())>0D) { // Too large of a payment. This should avoid most situations that the second part of the if above handles.
			throw new IllegalOperationException(); // TODO: Maybe create a specialized Exception for this case?
		}
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		if (this.getBalance() >= 0) { // Greater than is a safety check
			hasBeenRepaid=true;
			this.close();
		} 
	}
	
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		if (hasGraceElapsed || (cycle.getYearMonth() - this.m_dtOpened.getYearMonth() > 1)) { // Give one month grace period
			if (pb.credits<Math.min(this.minimumMonthlyPayment, -pb.starting_balance)) {
				new InternalTransaction(GlobalParameters.LOAN_LATE_PENALTY.get(), GlobalParameters.LOAN_LATE_PENALTY.describe());
			}
			// TODO: Each payment should probably increase the cap by at least the principal repaid. Right now we are increasing it for the entire repayment. (Not just principal)
			RuntimeAPI.forcefulAdjustCap(pb.credits);
			super.onUpdate(cycle, pb);
			hasGraceElapsed = true;
		}
	}
}
