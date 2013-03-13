package account;

import backend.RuntimeAPI.InterestRate;

abstract public class InterestAccount extends Account {
	final InterestRate m_eRate;
	final double m_dInterestOffset;

	public InterestAccount(InterestRate rate, double offset, long number, AccountHolder owner, boolean fix_rate) {
		super(number, owner);
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
}
