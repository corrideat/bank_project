package backend;

import java.util.HashMap;
import java.util.Map;

import date.DateTime;

import user.User;

import account.Account;

public class Core {
	protected static DateTime m_dtCurrentTime = new DateTime();
	
	protected static Map<Long, Account> m_aaAccounts = new HashMap<Long, Account>();
	protected static Map<String, User> m_auUsers  = new HashMap<String, User>();
	
	protected static double currentCap = 0D; 

	public static void main(String[] args) {
		
	}
	
	public static void save() {
		
	}
	
	public static void load() {
		
	}

	public static void timeShiftNotification() {
		System.out.println(Core.m_dtCurrentTime);
		for (Account a:Core.m_aaAccounts.values()) {
			a.update();
		}
	}

}
