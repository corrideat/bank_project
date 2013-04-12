package user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.RuntimeAPI;

import account.AccountHolder;

import date.DateTime;
import date.Time;

public abstract class User implements AccountHolder {
	final String m_sFirstName;
	final String m_sLastName;
	final DateTime m_dtBirthday;
	final DateTime m_dtRecordCreationDate;
	final int m_iSSN;
	final public Privileges m_ePrivileges;
	final Mailbox m_mbMailbox;
	
	private boolean validateSSN(final int ssn) {
		String sSSN = Integer.toString(ssn);
		 // Based on http://ssa-custhelp.ssa.gov/app/answers/detail/a_id/425 and http://stackoverflow.com/questions/4087468/ssn-regex-for-123-45-6789-or-xxx-xx-xxxx
		 Pattern pSSN = Pattern.compile("^(?!(000|666))[0-8]\\d{2}(?!00)\\d{2}(?!0000)\\d{4}$");
		 Matcher m = pSSN.matcher(sSSN);
		 boolean b = m.matches();
		 return (b && sSSN.substring(0, 3).compareTo("000")!=0 && sSSN.substring(3, 5).compareTo("00")!=0 && sSSN.substring(5, 9).compareTo("0000")!=0);
	}
	
	// Important: customers must have at least one account opened.
	// It is not possible to ensure this at creation time, but this must be enforced externally (i.e.,
	// a new customer may be created only during account creation time) 
	public User(final String firstName, final String lastName, final DateTime birthday, final int ssn, final Privileges p) {
		// TODO: Validate birthday as well (impose a 18 age)
		if (firstName != null && lastName != null && ssn > 10010001 && validateSSN(ssn)) {
			m_sFirstName = firstName;
			m_sLastName = lastName;
			m_dtBirthday = birthday;
			m_iSSN = ssn;
			m_dtRecordCreationDate = RuntimeAPI.now();
			m_ePrivileges = p;
			m_mbMailbox = new Mailbox();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	final public String getFirstName() {
		return m_sFirstName;
	}

	@Override
	final public String getLastName() {
		// TODO Auto-generated method stub
		return m_sLastName;
	}

	@Override
	final public int getAge() {
		Time t = RuntimeAPI.now().subtract(m_dtBirthday);
		return (int) Math.floor(((double)t.getDays())/365.25);
	}

	@Override
	final public int getSSN() {
		return m_iSSN;
	}	


	@Override
	public boolean isEmployee() {
		return false;
	}

}
