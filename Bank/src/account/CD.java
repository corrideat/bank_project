package account;

import backend.RuntimeAPI;
import backend.RuntimeAPI.InterestRate;

public class CD extends InterestBearingAccount {
	public enum CD_type {
		CD_6M(InterestRate.CD_6M), CD_1Y(InterestRate.CD_1Y), CD_2Y(InterestRate.CD_2Y), CD_3Y(InterestRate.CD_3Y), CD_4Y(InterestRate.CD_4Y), CD_5Y(InterestRate.CD_5Y);
		
		final InterestRate m_eRate;
		
		CD_type(InterestRate rate) {
			m_eRate = rate;
		}
		
		public InterestRate getRate(){
			return this.m_eRate;
		}
	}
	
	final CD_type m_eType;
	boolean m_bHasMatured;

	public CD(CD_type type, long number, AccountHolder owner) {
		super(type.m_eRate, number, owner, true);
		m_eType = type;
		m_bHasMatured = false;
	}
	
	@Override
	protected void transactionValidator(Transaction t) throws TransactionValidationException {
		super.transactionValidator(t);
		if (!m_bHasMatured && t.m_dAmount<0D) { // Penalize
			// TODO: This *might* sink the balance below zero. Is this expected behaviour?
			new InternalTransaction(-this.getAccountRate()/2 * this.getBalance(), "Withdawal Penalty");
		}
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		if (this.getBalance() < RuntimeAPI.CDMinimumBalance()) {
			this.close();
		}
	}
}
