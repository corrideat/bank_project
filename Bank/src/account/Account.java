package account;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import backend.Agent;
import backend.RuntimeAPI;
import date.DateTime;
import date.Time;

public abstract class Account {
	public final DateTime m_dtOpened;
	public final AccountType m_atType;
	private double m_dBalance;
	private DateTime m_dtClosed;
	private boolean m_bToBeClosed;
	private final List<Transaction> m_atHistory;
	private final AccountHolder m_ahOwner;
	private final long m_lAccountNumber;
	private DateTime m_dtLastUpdated;
	private Integer m_iLastUpdatedTransactionIndex;
	private double m_dLastUpdatedBalance;
	private final List<AutomatedTransaction> m_aatAutomatedTransactions;
	
	protected class PeriodBalance {
		final double starting_balance;
		final double ending_balance;
		final double average_balance;
		final double credits;
		final double debits;
		
		public PeriodBalance(final double starting, final double average, final double credits, final double debits) {
			this.starting_balance = starting;
			this.ending_balance = starting + credits - debits;
			this.average_balance = average;
			this.credits = credits;
			this.debits = debits;			
		}
		
		public PeriodBalance() {
			starting_balance = 0D;
			ending_balance = 0D;
			average_balance = 0D;
			this.credits = 0D;
			this.debits = 0D;
		}
	}
	
	private static class InternalAgent implements Agent {

		@Override
		public boolean isInternal() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private static Agent ms_agent=new InternalAgent();
	
	protected class InternalTransaction extends Transaction {
		public InternalTransaction(final double amount, final String description) {
			super(ms_agent, Account.this, amount, description);
			Account.this.forcePostTransaction(this);
		}

		public InternalTransaction(final Account target, final double amount, final String description) {
			super(ms_agent, Account.this, amount, description);
			try {
				target.postTransaction(this);
			} catch (TransactionValidationException e) {
			}
		}
		
		@Override
		public boolean flaggable() {
			return false;
		}
	}
	
	

	public Account(final AccountType at, final long number, final AccountHolder owner) {
		this.m_atType = at;
		this.m_dBalance = 0.0D;
		this.m_bToBeClosed = false;
		this.m_atHistory = new ArrayList<Transaction>();
		this.m_ahOwner =  owner;
		this.m_dtOpened =  RuntimeAPI.now();
		this.m_lAccountNumber = number;
		this.m_aatAutomatedTransactions = new ArrayList<AutomatedTransaction>();
		this.m_dLastUpdatedBalance = 0.0D;
	}
	
	protected final void close() {
		this.m_bToBeClosed = true;
		// TODO: Zero balance?, it should still function for a the remaining of a month
	}	
	
	/**
	 * @return the m_dBalance
	 */
	public final double getBalance() {
		return this.m_dBalance;
	}
	
	/**
	 * @return the m_dtClosed
	 */
	public final DateTime closedDate() {
		return this.m_dtClosed;
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
		return (this.m_dtClosed == null);
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
		this.simulateTransaction(t);
		this.forcePostTransaction(t);
	}
	
	private final void forcePostTransaction(final Transaction t)  {
		if (this.m_atHistory.add(t)) {
			this.m_dBalance += t.m_dAmount;
			this.update();
		} else {
			throw new IllegalStateException();
		}
	}

	
	protected final void simulateTransaction(final Transaction t) throws TransactionValidationException {
		securityTransactionValidator(t);
		if (t.m_agAgent!=ms_agent) transactionValidator(t);
	}
	
	private final void securityTransactionValidator(Transaction t) {
		// TODO: What if the account is closed but has a positive balance?
		if (this.m_dtClosed != null || t.m_aTarget != this)
				throw new SecurityException();
		//if (t.m_agAgent != this) throw new SecurityException();
	}
	
	protected abstract void transactionValidator(final Transaction t) throws TransactionValidationException;
	
	protected final void update() {
		DateTime current = RuntimeAPI.now();
		Time difference = current.subtract(this.m_dtLastUpdated);
		
		if (current.getDay() == 1 || difference.getDays() >= 31) {
			if (this.m_bToBeClosed) {
				Account[] accounts = this.m_ahOwner.getAccounts();
				boolean transferred = false;
				if (accounts.length>1) {
					for(Account a:accounts) {
						if (a != this && a.m_atType==AccountType.CHECKING) {
							new InternalTransaction(this.m_dBalance, String.format("Account %d Close Transfer %.02d", this.m_lAccountNumber, this.m_dBalance));
							transferred = true;
							break;
						}
					}
					if (!transferred) {
						for(Account a:accounts) {
							if (a != this && a.m_atType==AccountType.SAVINGS) {
								new InternalTransaction(this.m_dBalance, String.format("Account %d Close Transfer %.02d", this.m_lAccountNumber, this.m_dBalance));
								transferred = true;
								break;
							}
						}
					}
					if (!transferred) {
						for(Account a:accounts) {
							if (a != this && a.m_atType==AccountType.LOC) {
								new InternalTransaction(this.m_dBalance, String.format("Account %d Close Transfer %.02d", this.m_lAccountNumber, this.m_dBalance));
								transferred = true;
								break;
							}
						}
					}
					if (!transferred) {
						for(Account a:accounts) {
							if (a != this && a.m_atType==AccountType.LOAN) {
								new InternalTransaction(this.m_dBalance, String.format("Account %d Close Transfer %.02d", this.m_lAccountNumber, this.m_dBalance));
								transferred = true;
								break;
							}
						}
					}
				}
				// TODO: What is !transferred?
				this.m_dBalance = 0L;
				this.m_dtClosed = current;
			}
			this.prepareStatement();
			for(AutomatedTransaction trans:this.m_aatAutomatedTransactions) {
				Account target = null;
				if ((target = RuntimeAPI.getAccount(trans.m_lTargetAccount)) != null) {
					try {
						Transaction local = new Transaction(ms_agent, this, -Math.abs(trans.m_dAmount), String.format("Automated Transaction: %s", trans.m_sDescription));
						Transaction outgoing =new Transaction(ms_agent, this, -Math.abs(trans.m_dAmount), String.format("Automated Transaction from %s, %s", this.m_ahOwner.getLastName(), this.m_ahOwner.getFirstName()));
						target.simulateTransaction(local);
						target.simulateTransaction(outgoing);
						this.forcePostTransaction(local);
						target.forcePostTransaction(outgoing);
					} catch (TransactionValidationException e) {
						// TODO: Should we charge customer a fee?
						new InternalTransaction(0D, String.format("Failed Automated Transaction for %.02d: %s", -Math.abs(trans.m_dAmount), trans.m_sDescription));
					}
				}
			}
			onUpdate(RuntimeAPI.now(), getMonthlyBalance(RuntimeAPI.now()));
			this.m_dtLastUpdated=RuntimeAPI.now();
		}
		
		onUpdate();
		
	}
	
	abstract protected void onUpdate(DateTime cycle, PeriodBalance pb);
	abstract protected void onUpdate();
	
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
	
	final public void prepareStatement() {
		// TODO: Do stuff
	}
	
	// Assume that m_dtLastUpdated to be accurate
	final private PeriodBalance getMonthlyBalance(DateTime cutoff) {
		if (m_atHistory.size() > 0) {
			if (m_iLastUpdatedTransactionIndex==null) m_iLastUpdatedTransactionIndex = 0;
			Iterator<Transaction> i = m_atHistory.listIterator(m_iLastUpdatedTransactionIndex);
			Transaction temp;
			double temp_amount = 0D;
			double temp_cred = 0D;
			double temp_deb = 0D;
			
			DateTime last = m_dtLastUpdated;		
			while(i.hasNext()) {
				temp = i.next();
				if (cutoff.subtract(temp.m_dtTime).getRawCountInMillis()>0) {
					temp_amount = temp.m_dAmount * temp.m_dtTime.subtract(last).getRawCountInMillis();
					if (temp.m_dAmount > 0D) {
						temp_cred += temp.m_dAmount;
					} else {
						temp_deb -= temp.m_dAmount;
					}
					last = temp.m_dtTime;
					m_iLastUpdatedTransactionIndex++;
				} else {
					break;
				}
			}
			
			temp_amount /= cutoff.subtract(m_dtLastUpdated).getRawCountInMillis();
			
			PeriodBalance pd = new PeriodBalance(m_dLastUpdatedBalance, m_dLastUpdatedBalance+temp_amount, temp_cred, temp_deb);
			
			m_dLastUpdatedBalance = pd.ending_balance;
			
			return pd;
		}
		return new PeriodBalance();
	}
}
