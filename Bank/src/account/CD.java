package account;

import date.DateTime;
import backend.GlobalParameters;
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
		
		public short getDuration() {
			switch(this) {
			case CD_6M:
				return 6;
			case CD_1Y:
				return 12;
			case CD_2Y:
				return 24;
			case CD_3Y:
				return 36;
			case CD_4Y:
				return 48;
			case CD_5Y:
				return 60;
			default:
				return 0;
			}
		}
	}
	
	final CD_type m_eType;
	boolean m_bHasMatured;

	public CD(CD_type type, long number, AccountHolder owner) {
		super(AccountType.CD, type.m_eRate, number, owner, true);
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
		if (this.getBalance() < GlobalParameters.CD_MINIMUM_BALANCE.get()) {
			this.close();
		}
	}
	
}
