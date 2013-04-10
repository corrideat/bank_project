package account;

import date.DateTime;
import backend.RuntimeAPI.InterestRate;

abstract public class InterestAccount extends Account {
	final InterestRate m_eRate;
	final double m_dInterestOffset;

	public InterestAccount(AccountType at, InterestRate rate, double offset, long number, AccountHolder owner, boolean fix_rate) {
		super(at, number, owner);
		if (fix_rate) {
			m_eRate = null;
			m_dInterestOffset = offset + rate.getRate();
		} else {
			m_eRate = rate;
			m_dInterestOffset = offset;
		}
	}
	
	public double getAccountRate() {
		if (m_eRate != null) {
			return m_eRate.getRate()+m_dInterestOffset;
		} else {
			return m_dInterestOffset;
		}
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		if (this.debtInstrument() && pb.average_balance < 0D) {
			new InternalTransaction(pb.average_balance * (1+this.getAccountRate()), "Interest Charge");
		} else if (!this.debtInstrument() && pb.average_balance > 0D) {
			new InternalTransaction(pb.average_balance * (1+this.getAccountRate()), "Interest Payment");
		}
	}
	
	@Override
	protected void onUpdate() {	}
}
