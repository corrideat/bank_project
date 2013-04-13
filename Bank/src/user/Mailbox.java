package user;

import java.util.List;
import java.util.ArrayList;

public class Mailbox {	
	private final List<Message> m_amMessages;
	
	public Mailbox() {
		m_amMessages = new ArrayList<Message>(); 
	}
	
	final public void send(Message m) {
		m_amMessages.add(m);
	}
}
