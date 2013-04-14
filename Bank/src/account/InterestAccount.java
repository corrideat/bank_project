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
			m_dInterestOffset = Math.max(offset + rate.getRate(), 0);
		} else {
			m_eRate = rate;
			m_dInterestOffset = offset;
		}
	}
	
	final public double getAccountRate() {
		if (m_eRate != null) {
			return Math.max(m_eRate.getRate()+m_dInterestOffset, 0);
		} else {
			return m_dInterestOffset;
		}
	}
	
	@Override
	protected void onUpdate(DateTime cycle, PeriodBalance pb) {
		if (this.getAccountRate()>0 && !this.isClosed()) {
			if (this.debtInstrument() && pb.average_balance < 0D) {
				new InternalTransaction(pb.average_balance * (this.getAccountRate()/12), "Interest Charge");
			} else if (!this.debtInstrument() && pb.average_balance > 0D) {
				new InternalTransaction(pb.average_balance * (this.getAccountRate()/12), "Interest Payment");
			}
		}
	}
	
	@Override
	protected void onUpdate() {	}
}
