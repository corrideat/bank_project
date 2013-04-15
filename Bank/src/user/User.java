package user;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import backend.Core;
import backend.RuntimeAPI;

import account.Account;
import account.AccountHolder;

import date.DateTime;
import date.Time;

public abstract class User implements AccountHolder, Comparable<User> {
	final String m_sFirstName;
	final String m_sLastName;
	final DateTime m_dtBirthday;
	final DateTime m_dtRecordCreationDate;
	final int m_iSSN;
	final public Privileges m_ePrivileges;
	final Mailbox m_mbMailbox;
	static Random random = new SecureRandom();
	final private Authentication m_oAuthObject;
	
	final private class Authentication implements Comparable<Authentication> {
	
		private String m_sUsername;
		private byte[] m_baSaltedPassword;
		private byte[] m_baPasswordSalt;
		
		protected Authentication(final String username, final String password) {
			this.setUsername(username, password);
		}
		
		final public void setUsername(final String username, final String password) {
			if (username != null && password != null) {
				// Important: this does not guard against overlaps.
				String old = m_sUsername;
				try {
					m_sUsername = username;
					setPassword(password);
				} catch(IllegalStateException e) {
					m_sUsername = old;
					throw e;
				}
			}
		}
		
		final public void setPassword(final String password) {
			byte[] salt = new byte[64];
			User.random.nextBytes(salt);
			
			try {
				byte[] temp =  hmac(m_sUsername, salt);
				
				// Make computation more expensive, and hence, harder to attack
				for(int i=0;i<2000;i++) {
					if (i%2==0) {
						temp =  hmac(m_sUsername, temp);
					}
					else {
						temp =  hmac(password, temp);
					}
				}
				
				m_baSaltedPassword = hmac(password, temp);
				m_baPasswordSalt = salt;
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException();
			}
		}
		
		final public boolean authenticate(final String username, final String password) {
			if (m_sUsername.compareTo(username) != 0) return false;
			try {
				byte[] temp =  this.hmac(m_sUsername, m_baPasswordSalt);
				
				// Make computation more expensive, and hence, harder to attack
				for(int i=0;i<2000;i++) {
					if (i%2==0) {
						temp =  hmac(m_sUsername, temp);
					}
					else {
						temp =  hmac(password, temp);
					}
				}

				
				byte[] saltedPassword = this.hmac(password, temp);
				
				if (saltedPassword.length != m_baSaltedPassword.length) return false;
				
				for(int i=0;i<saltedPassword.length;i++) {
					if (m_baSaltedPassword[i] != saltedPassword[i]) return false;
				}
				
				return true;			
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException();
			}
		}
		
		final private byte[] byteHash(final byte[] message) throws NoSuchAlgorithmException {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");			
			} catch (NoSuchAlgorithmException e) {
				md = MessageDigest.getInstance("RIPEMD160");
			}
			md.update(message);
			
			return md.digest();
		}
		
		final private byte[] hmac(final String message, byte[] bKey) throws NoSuchAlgorithmException {
			if (bKey.length > 64) {
				bKey = this.byteHash(bKey);
			} else if (bKey.length < 64) {
				byte[] temp = new byte[64];
				int i=0;
				for (;i<bKey.length;i++) {
					temp[i] = bKey[i];
				}			
				for (;i<64;i++) {
					temp[i] = 0;
				}
				bKey = temp;
			}
			
			byte[] o_key_pad = new byte[64];
			byte[] i_key_pad = new byte[64];
			
			for(int i=0;i<64;i++) {
				o_key_pad[i] = (byte) (0x5c ^ bKey[i]);
				i_key_pad[i] = (byte) (0x36 ^ bKey[i]);
			}

			byte[] temp = message.getBytes();
			byte[] partA = new byte[i_key_pad.length + temp.length];

			
			for(int i=0;i<i_key_pad.length;i++) {
				partA[i] = i_key_pad[i];
			}
			
			for(int i=0;i<temp.length;i++) {
				partA[i_key_pad.length+i] = temp[i];
			}
			
			partA = this.byteHash(partA);
			
			temp = new byte[partA.length+o_key_pad.length];
			
			for(int i=0;i<o_key_pad.length;i++) {
				temp[i] = o_key_pad[i];
			}
			
			for(int i=0;i<partA.length;i++) {
				temp[o_key_pad.length+i] = partA[i];
			}
			
			return this.byteHash(temp);
		}

		
		@Override
		final public int compareTo(Authentication o) {
			return this.m_sUsername.compareTo(o.m_sUsername);
		}
	}
		
	final private boolean validateSSN(final int ssn) {
		String sSSN = Integer.toString(ssn);
		 // Based on http://ssa-custhelp.ssa.gov/app/answers/detail/a_id/425 and http://stackoverflow.com/questions/4087468/ssn-regex-for-123-45-6789-or-xxx-xx-xxxx
		 Pattern pSSN = Pattern.compile("^(?!(000|666))[0-8]\\d{2}(?!00)\\d{2}(?!0000)\\d{4}$");
		 Matcher m = pSSN.matcher(sSSN);
		 return m.matches();
	}
	
	// Important: customers must have at least one account opened.
	// It is not possible to ensure this at creation time, but this must be enforced externally (i.e.,
	// a new customer may be created only during account creation time) 
	public User(final String firstName, final String lastName, final DateTime birthday, final int ssn, final Privileges p, final String username, final String password) {
		// TODO: Validate birthday as well (impose a 18 age)
		if (firstName != null && lastName != null && validateSSN(ssn)) {
			m_sFirstName = firstName;
			m_sLastName = lastName;
			m_dtBirthday = birthday;
			m_iSSN = ssn;
			m_dtRecordCreationDate = RuntimeAPI.now();
			m_ePrivileges = p;
			m_mbMailbox = new Mailbox();
			this.m_oAuthObject = new Authentication(username, password);
			Core.m_auUsers.put(username, this);
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

	@Override
	public int compareTo(User o) {
		return this.m_oAuthObject.compareTo(o.m_oAuthObject);
	}
	
	@Override
	public void sendNotification(Account a, String message) {
		Message m = new Message(message, a);
		this.m_mbMailbox.send(m);
	}
	
	public void setUsername(final String username, final String password) {
		this.m_oAuthObject.setUsername(username, password);
	}
	
	public void setPassword(final String password) {
		this.m_oAuthObject.setPassword(password);
	}
	
	public boolean authenticate(final String username, final String password) {
		return this.m_oAuthObject.authenticate(username, password);
	}
	
	final public String toString() {
		String pos = "Unknown";
		if (this instanceof Customer) {
			pos = "Customer";
		} else if (this instanceof Accountant) {
			pos = "Accountant";
		} else if (this instanceof OperationManager) {
			pos = "Operation Manager";
		} else if (this instanceof AccountManager) {
			pos = "Account Manager";
		} else if (this instanceof Teller) {
			pos = "Teller";
		} else if (this instanceof Auditor) {
			pos = "Auditor";
		}
		return this.m_sLastName+", "+this.m_sFirstName+" - "+this.m_dtBirthday+" : "+pos;
	}
	
	abstract AccountHolder getAH();
	
}

