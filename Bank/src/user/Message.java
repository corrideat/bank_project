package user;

import account.Account;

public class Message {
	final String m_sContent;
	final Account m_aAccountConcerned;
	boolean m_bRead;
	
	Message(final String content, final Account account) {
		if (content == null) throw new IllegalArgumentException(); 
		m_sContent = content;
		m_aAccountConcerned = account; // May be left blank
	}
	
	public String toString() {
		return m_sContent;
	}
}
