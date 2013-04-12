package user;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
	static Random random = new SecureRandom();
	private String m_sUsername;
	private byte[] m_baSaltedPassword;
	private byte[] m_baPasswordSalt;
	
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
	
	public void setUsername(final String username) {
		// Important: this does not guard against overlaps.
		m_sUsername = username;
	}
	
	public String getUsername(final String username) {
		return m_sUsername;
	}

	
	public void changePassword(final String password) {
		byte[] salt = new byte[8];
		User.random.nextBytes(salt);
		
		try {
			m_baSaltedPassword = hmac(password, salt);
			m_baPasswordSalt = salt;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}
	}
	
	public boolean checkPassword(final String password) {
		try {
			byte[] saltedPassword = hmac(password, m_baPasswordSalt);
			
			if (saltedPassword.length != m_baSaltedPassword.length) return false;
			
			for(int i=0;i<saltedPassword.length;i++) {
				if (m_baSaltedPassword[i] != saltedPassword[i]) return false;
			}
			
			return true;			
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}
	}
	
	private static byte[] byteHash(final byte[] message) throws NoSuchAlgorithmException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");			
		} catch (NoSuchAlgorithmException e) {
			md = MessageDigest.getInstance("RIPEMD160");
		}
		md.update(message);
		
		return md.digest();
	}
	
	private static byte[] hmac(final String message, byte[] bKey) throws NoSuchAlgorithmException {
		if (bKey.length > 64) {
			bKey = User.byteHash(bKey);
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
		
		partA = User.byteHash(partA);
		
		temp = new byte[partA.length+o_key_pad.length];
		
		for(int i=0;i<o_key_pad.length;i++) {
			temp[i] = o_key_pad[i];
		}
		
		for(int i=0;i<partA.length;i++) {
			temp[o_key_pad.length+i] = partA[i];
		}
		
		return User.byteHash(temp);
	}

}

