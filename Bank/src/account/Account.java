package account;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import backend.Agent;
import backend.RuntimeAPI;
import date.DateTime;

public abstract class Account implements Comparable<Account> {
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
	private final List<String> m_asPendingStatements;
	
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
		final String m_sDescription;
		
		protected InternalAgent(final String description) {
			m_sDescription = description;
		}
		
		@Override
		public boolean isInternal() {
			return true;
		}

		@Override
		public String describe() {
			return this.m_sDescription;
		}		
	}
	
	private static Agent ms_agent=new InternalAgent("Internal Agent");
	private static Agent ms_atAgent=new InternalAgent("Automated Transactions Agent");
	
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
		this.m_dtLastUpdated = this.m_dtOpened =  RuntimeAPI.now();
		this.m_lAccountNumber = number;
		this.m_aatAutomatedTransactions = new ArrayList<AutomatedTransaction>();
		this.m_dLastUpdatedBalance = 0.0D;
		this.m_asPendingStatements = new ArrayList<String>();
		this.m_dtClosed = null;
	}
	
	public final void close() {
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
		return (this.m_dtClosed != null);
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
		if (!this.isClosed() && this.m_atHistory.add(t)) {
			this.m_dBalance += t.m_dAmount;
			if (t.m_dAmount > 0D) {
				System.out.println(String.format("%d/%d/%d\t%s\t%.03f\t", t.m_dtTime.getMonth(), t.m_dtTime.getDay(), t.m_dtTime.getYear(), t.m_sDescription, Math.abs(t.m_dAmount)));
			} else {
				System.out.println(String.format("%d/%d/%d\t%s\t\t%.03f", t.m_dtTime.getMonth(), t.m_dtTime.getDay(), t.m_dtTime.getYear(), t.m_sDescription, Math.abs(t.m_dAmount)));
			}
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
		//System.out.println("\t\tSTV: "+this.m_dtClosed+"\t"+t.m_aTarget.hashCode()+"\t"+this.hashCode());
		if (this.m_dtClosed != null || t.m_aTarget != this) {
				throw new SecurityException();
		}
	}
	
	protected abstract void transactionValidator(final Transaction t) throws TransactionValidationException;
	
	public final void update() {	
		DateTime current = RuntimeAPI.now();
		
		if (this.isClosed()) return;
		
		if (current.getYearMonth() != this.m_dtLastUpdated.getYearMonth()) {
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
					if (!transferred) {
						new InternalTransaction(this.m_dBalance, String.format("Account %d Close Check Mailed for %.02d", this.m_lAccountNumber, this.m_dBalance));
					}
				}
				this.m_dBalance = 0L;
				this.m_dtClosed = current;
			}
			
			
			DateTime cutoff = new DateTime(current.getYear(), current.getMonth(), 1);
			PeriodBalance pb = getMonthlyBalance(cutoff);
			System.out.println(this.m_lAccountNumber+" "+m_dBalance+"\t"+pb.starting_balance+"\t"+pb.average_balance+"\t"+pb.ending_balance);
			m_dLastUpdatedBalance = pb.ending_balance;
			this.m_dtLastUpdated=cutoff;
			onUpdate(cutoff, pb);
			
			if (!this.isClosed()) {				
				for(AutomatedTransaction trans:this.m_aatAutomatedTransactions) {					
					try {
						transfer(Account.ms_atAgent, trans.m_lTargetAccount, trans.m_dAmount, String.format("Automated Transaction: %s", trans.m_sDescription));
					} catch (TransactionValidationException | SecurityException e) {
						// TODO: Should we charge customer a fee?
						new InternalTransaction(0D, String.format("Failed Automated Transaction for %.02f: %s", -Math.abs(trans.m_dAmount), trans.m_sDescription));
						e.printStackTrace();
					}
				}
			}
		}
		
		onUpdate();
		
	}
	
	final public void transfer(final Agent agent, final long destination, final double amount, final String description) throws TransactionValidationException {
		final Account target = RuntimeAPI.getAccount(destination);
		if (target != null && !target.isClosed()) {
			final Transaction local = new Transaction(agent, this, -Math.abs(amount), description);
			final Transaction outgoing = new Transaction(agent, target, Math.abs(amount), String.format("Payment from %s, %s", this.m_ahOwner.getLastName(), this.m_ahOwner.getFirstName()));
			this.simulateTransaction(local);
			target.simulateTransaction(outgoing);
			this.forcePostTransaction(local);
			target.forcePostTransaction(outgoing);
		}
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
	
	public void cancelAutomatedTransaction(int at_index) {
		m_aatAutomatedTransactions.remove(at_index);
	}
	
	public boolean debtInstrument() {
		return false;
	}
	
	final public void prepareStatement() {
		Iterator<String> i= this.m_asPendingStatements.listIterator();
		while(i.hasNext()) {
			this.m_ahOwner.sendNotification(this, i.next());
			i.remove();			
		}
	}
	
	// Assume that m_dtLastUpdated to be accurate
	final private PeriodBalance getMonthlyBalance(DateTime cutoff) {
		if (m_atHistory.size() > 0) {
			if (m_iLastUpdatedTransactionIndex==null) m_iLastUpdatedTransactionIndex = 0;
			Iterator<Transaction> i = m_atHistory.listIterator(m_iLastUpdatedTransactionIndex);
			Transaction temp;
			double temp_avg = 0D;
			double temp_cred = 0D;
			double temp_deb = 0D;
			
			DateTime last = m_dtLastUpdated;
			
			ArrayList<String> transactions = new ArrayList<String>();
			
			transactions.add(String.format("ACCOUNT STATEMENT"));
			transactions.add(String.format("Customer: %s, %s", this.m_ahOwner.getLastName(), this.m_ahOwner.getFirstName()));
			transactions.add(String.format("Account: %d", this.m_lAccountNumber));
			transactions.add(String.format("Period: %d/%d/%d - %d/%d/%d", this.m_dtLastUpdated.getMonth(), this.m_dtLastUpdated.getDay(), this.m_dtLastUpdated.getYear(), cutoff.getMonth(), cutoff.getDay(), cutoff.getYear()));
			if (this.debtInstrument()) {
				transactions.add(String.format("Starting balance: %c%f", (this.m_dLastUpdatedBalance>0)?'+':' ', Math.round(Math.abs(this.m_dLastUpdatedBalance)*1E3)/1E3));
			} else {
				transactions.add(String.format("Starting balance: %c%f", (this.m_dLastUpdatedBalance>0)?' ':'-', Math.round(Math.abs(this.m_dLastUpdatedBalance)*1E3)/1E3));				
			}
			
			transactions.add("----------------");
			if (this.debtInstrument()) {
				transactions.add("Date\tDescription\tAmount\tRunning Balance");
			} else {
				transactions.add("Date\tDescription\tCredits\tDebits");
			}			
			
			
			while(i.hasNext()) {
				temp = i.next();
				if (cutoff.subtract(temp.m_dtTime).getRawCountInMillis()>0) {					
					if (temp.m_dAmount > 0D) {
						temp_cred += temp.m_dAmount;
					} else {
						temp_deb -= temp.m_dAmount;
					}

					double runningBalance = this.m_dLastUpdatedBalance + temp_cred - temp_deb;
					
					temp_avg += (m_dLastUpdatedBalance + temp_cred - temp_deb) * temp.m_dtTime.subtract(last).getRawCountInMillis();
					
					last = temp.m_dtTime;
					m_iLastUpdatedTransactionIndex++;

					
					if (this.debtInstrument()) {						
						transactions.add(String.format("%d/%d/%d\t%s\t%c%.03f\t%c%.03f", temp.m_dtTime.getMonth(), temp.m_dtTime.getDay(), temp.m_dtTime.getYear(), temp.m_sDescription, (temp.m_dAmount>0)?'+':' ', Math.abs(temp.m_dAmount), (runningBalance>0)?'+':' ', Math.abs(runningBalance)));
					} else {
						if (temp.m_dAmount > 0D) {
							transactions.add(String.format("%d/%d/%d\t%s\t%.03f\t", temp.m_dtTime.getMonth(), temp.m_dtTime.getDay(), temp.m_dtTime.getYear(), temp.m_sDescription, Math.abs(temp.m_dAmount)));
						} else {
							transactions.add(String.format("%d/%d/%d\t%s\t\t%.03f", temp.m_dtTime.getMonth(), temp.m_dtTime.getDay(), temp.m_dtTime.getYear(), temp.m_sDescription, Math.abs(temp.m_dAmount)));
						}
					}					
				} else {
					break;
				}
			}
			
			temp_avg += (m_dLastUpdatedBalance + temp_cred - temp_deb) * cutoff.subtract(last).getRawCountInMillis();
			
			temp_avg /= cutoff.subtract(m_dtLastUpdated).getRawCountInMillis();
			
			PeriodBalance pd = new PeriodBalance(m_dLastUpdatedBalance, temp_avg, temp_cred, temp_deb);
			
			transactions.add("----------------");			
			
			if (this.debtInstrument()) {
				transactions.add(String.format("Average balance: %c%.03f", (this.m_dLastUpdatedBalance>0)?'+':' ', Math.abs(pd.average_balance)));
				transactions.add(String.format("Ending balance: %c%.03f", (this.m_dLastUpdatedBalance>0)?'+':' ', Math.abs(this.m_dLastUpdatedBalance)));
			} else {
				transactions.add(String.format("Average balance: %c%.03f", (this.m_dLastUpdatedBalance>0)?'+':' ', Math.abs(pd.average_balance)));
				transactions.add(String.format("Ending balance: %c%.03f", (this.m_dLastUpdatedBalance>0)?'+':' ', Math.abs(this.m_dLastUpdatedBalance)));			
			}
			
			String statement = "";
			for(String t:transactions) {
				statement += t+"\n";
			}
			m_asPendingStatements.add(statement);
			
			return pd;
		}
		return new PeriodBalance();
	}

	@Override
	public int compareTo(Account o) {
		long diff = this.m_lAccountNumber-o.m_lAccountNumber;
		return ((diff<0)?(-1):((diff>0)?1:0));
	}
}
