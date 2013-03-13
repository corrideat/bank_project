package account;

public class AutomatedTransaction {
	final double m_dAmount;
	final long m_lTargetAccount;
	
	public AutomatedTransaction(final double amount, final long target) {
		if (amount<0) throw new IllegalArgumentException();
		// if () TODO: Check whether target account exists
		m_dAmount = amount;
		m_lTargetAccount = target;
	}
}
