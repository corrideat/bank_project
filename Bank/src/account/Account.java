package account;
import java.util.ArrayList;
import java.util.List;
import backend.Agent;
import backend.RuntimeAPI;
import date.DateTime;

public abstract class Account {	
	private double m_dBalance;
	private boolean m_bClosed;
	private final List<Transaction> m_atHistory;
	private final AccountHolder m_ahOwner;
	private final long m_lAccountNumber;
	private DateTime m_dtLastUpdated;
	private final List<AutomatedTransaction> m_aatAutomatedTransactions;
	
	private static class InternalAgent implements Agent {
		
	}
	
	private static Agent ms_agent=new InternalAgent();
	
	protected class InternalTransaction extends Transaction {
		public InternalTransaction(final double amount, final String description) {
			super(ms_agent, Account.this, amount, description);
			try {
				Account.this.postTransaction(this);
			} catch (TransactionValidationException e) {
			}
		}
		
		@Override
		public boolean flaggable() {
			return false;
		}
	}
	

	public Account(final long number, final AccountHolder owner) {
		this.m_dBalance = 0.0D;
		this.m_bClosed = false;
		this.m_atHistory = new ArrayList<Transaction>();
		this.m_ahOwner =  owner;
		this.m_lAccountNumber = number;
		m_aatAutomatedTransactions = new ArrayList<AutomatedTransaction>();
	}
	
	protected final void close() {
		this.m_bClosed = true;
		// TODO: Zero balance?, it should still function for a the remaining of a month
	}	
	
	/**
	 * @return the m_dBalance
	 */
	public final double getBalance() {
		return this.m_dBalance;
	}
	
	/**
	 * @return the m_atHistory
	 */
	public final Transaction[] getTransactions() {
		return this.m_atHistory.toArray(new Transaction[0]);
	}
	
	/**
	 * @return the m_bClosed
	 */
	public final boolean isClosed() {
		return this.m_bClosed;
	}
	
	/**
	 * @return the m_ahOwner
	 */
	public final AccountHolder getAccountHolder() {
		return this.m_ahOwner;
	}
	
	/**
	 * @return the m_lAccountNumber
	 */
	public final long getAccountNumber() {
		return this.m_lAccountNumber;
	}
	
	/**
	 * @return the m_dtLastUpdated
	 */
	public final DateTime getLastUpdated() {
		return this.m_dtLastUpdated;
	}
	
	public final void postTransaction(final Transaction t) throws TransactionValidationException {
		securityTransactionValidator(t);
		if (t.m_agAgent!=ms_agent) transactionValidator(t);
		this.m_atHistory.add(t);
		this.m_dBalance += t.m_dAmount;
		this.update();
	}
	
	private final void securityTransactionValidator(Transaction t) {
		// TODO: What if the account is closed but has a positive balance?
		if (this.m_bClosed || t.m_aTarget != this)
				throw new SecurityException();
		//if (t.m_agAgent != this) throw new SecurityException();
	}
	
	protected abstract void transactionValidator(final Transaction t) throws TransactionValidationException;
	
	protected final void update() {
		this.m_dtLastUpdated=RuntimeAPI.now();
		// Prepare statements, etc?
		// Effect Automated Transactions
		onUpdate();
	}
	
	protected void onUpdate() {
	}
	
	public void overturnTransaction(int transaction_index) {
		Transaction t = m_atHistory.get(transaction_index);
		if (t!=null) {
			new InternalTransaction(-t.m_dAmount, String.format("Transaction %d overturned. [%s]", transaction_index, t.m_sDescription));
		} else throw new IllegalArgumentException();
	}

	public AutomatedTransaction[] getAutomatedTransactions() {
		return m_aatAutomatedTransactions.toArray(new AutomatedTransaction[0]);
	}
	
	public void setupAutomatedTransaction(AutomatedTransaction at) {
		m_aatAutomatedTransactions.add(at);
	}
	
	public boolean debtInstrument() {
		return false;
	}
}
