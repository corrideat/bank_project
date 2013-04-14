package account;

import date.DateTime;
import backend.GlobalParameters;
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
				break;
			case CD_1Y:
				return 12;
				break;
			case CD_2Y:
				return 24;
				break;
			case CD_3Y:
				return 36;
				break;
			case CD_4Y:
				return 48;
				break;
			case CD_5Y:
				return 60;
				break;
			default:
				return 0;
			}
		}
	}
	
	private final CD_type m_eType;
	private boolean m_bHasMatured;
	private short m_dDuration;

	public CD(CD_type type, long number, AccountHolder owner) {
		super(AccountType.CD, type.m_eRate, number, owner, true);
		m_eType = type;
		m_bHasMatured = false;
		m_dDuration = type.getDuration();
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
	protected void onUpdate(DateTime dt, PeriodBalance pb) {
		if (!m_bHasMatured) {
			if (m_dDuration-- < 0) {
				m_bHasMatured = true;
			} else {
				super.onUpdate(dt, pb); // Interest operations
			}
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
