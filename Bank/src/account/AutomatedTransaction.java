package account;

import backend.RuntimeAPI;

public class AutomatedTransaction {
	final double m_dAmount;
	final long m_lTargetAccount;
	final String m_sDescription;
	
	public AutomatedTransaction(final double amount, final long target, final String description) {
		if (amount<0) throw new IllegalArgumentException();
		if (RuntimeAPI.getAccount(target) == null) throw new IllegalArgumentException();
		m_dAmount = amount;
		m_lTargetAccount = target;
		m_sDescription = description;
	}
}
