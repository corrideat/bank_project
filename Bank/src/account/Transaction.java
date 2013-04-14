package account;

import date.DateTime;
import backend.Agent;
import backend.RuntimeAPI;

public class Transaction {
	public final Agent m_agAgent;
	public final Account m_aTarget;
	public final String m_sDescription;	
	public final double m_dAmount;
	public final DateTime m_dtTime;
	public DateTime m_dtReportedFraudulent;
	private boolean overturned;
 
	public Transaction(final Agent agent, final Account target, final double amount, final String description) {
		 this.m_agAgent = agent;
		 this.m_aTarget = target;
		 this.m_dAmount = amount;
		 this.m_sDescription = description;
		 this.m_dtTime = RuntimeAPI.now();
		 this.m_dtReportedFraudulent=null;
		 overturned = false;
	}
	
	public boolean flaggable() {
		return true;
	}
	
	final public boolean overturn() {
		if (overturned) {
			return false;
		} else {
			overturned = true;
			return true;		
		}
	}
	
	public final void flagAsFraudulent() {
		if (this.flaggable()) {
			DateTime current = RuntimeAPI.now();
			if ((current.getYearMonth() - this.m_dtTime.getYearMonth())<=2) {
				m_dtReportedFraudulent = RuntimeAPI.now();
			}
		}
	}
	
	public final String toString() {
		return m_sDescription;
	}

}
