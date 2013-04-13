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
 
	public Transaction(final Agent agent, final Account target, final double amount, final String description) {
		 this.m_agAgent = agent;
		 this.m_aTarget = target;
		 this.m_dAmount = amount;
		 this.m_sDescription = description;
		 this.m_dtTime = RuntimeAPI.now();
		 this.m_dtReportedFraudulent=null;
	}
	
	public boolean flaggable() {
		return true;
	}
	
	public final void flagAsFraudulent() {
		if (this.flaggable()) {
			m_dtReportedFraudulent = RuntimeAPI.now();
		}
	}
	
	public final String toString() {
		return m_sDescription;
	}

}
