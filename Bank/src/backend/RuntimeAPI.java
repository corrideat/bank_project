package backend;

import user.User;
import account.Account;
import date.DateTime;
import date.Time;

public final class RuntimeAPI {
	private static DateTime m_dtCurrentTime = new DateTime();
	
	public static DateTime now() {
		return m_dtCurrentTime;
	}
	
	public static void shiftTime(final long millis) {
		Time diff = new Time(Math.abs(millis));
		DateTime finalTime = m_dtCurrentTime.add(diff);
		
		int start = m_dtCurrentTime.getYear()*12+m_dtCurrentTime.getMonth();
		int end = finalTime.getYear()*12+finalTime.getMonth();
		
		// Shift time ensuring that the first of that month is always reached
		for(int i=start;i<(end-1);i++) {
			DateTime temp = new DateTime(i/12, (i%12)+1, 1, 0, 0, 0);
			m_dtCurrentTime = temp;
			Core.timeShiftNotification();
		}
		
		m_dtCurrentTime = finalTime;
		Core.timeShiftNotification();
	}
	
	public static enum InterestRate {
		SAVINGS, CD_6M, CD_1Y, CD_2Y, CD_3Y, CD_4Y, CD_5Y, LOAN, LOC;

		public double getRate() {
			switch(this) {
			case SAVINGS:
				return GlobalParameters.RATE_SAVINGS.get();
			case CD_6M:
				return GlobalParameters.RATE_CD_6M.get();
			case CD_1Y:
				return GlobalParameters.RATE_CD_1Y.get();
			case CD_2Y:
				return GlobalParameters.RATE_CD_2Y.get();
			case CD_3Y:
				return GlobalParameters.RATE_CD_3Y.get();
			case CD_4Y:
				return GlobalParameters.RATE_CD_4Y.get();
			case CD_5Y:
				return GlobalParameters.RATE_CD_5Y.get();
			case LOAN:
				return GlobalParameters.RATE_LOAN.get();
			case LOC:
				return GlobalParameters.RATE_LOC.get();
			default:
				return 0D;
			}
		}		
	}
	
	// Negative: reduce from current cap (e.g., new loan), positive: increase from current cap
	public static void forcefulAdjustCap(double d) {
		Core.currentCap += d;
	}
	
	// Negative: reduce from current cap (e.g., new loan), positive: increase from current cap
	public static void adjustCap(double d) throws InsufficientCreditAvailableException {
		if (d >= 0D || (Core.currentCap + d) > 0) {
			Core.currentCap += d;
		} else {
			throw new InsufficientCreditAvailableException();
		}
	}

	public static double getCap() {
		return Core.currentCap;
	}

	public static Account getAccount(final long account_number) {
		return Core.m_aaAccounts.get(account_number);
	}
	
	public static User getUser(final String username) {
		return Core.m_auUsers.get(username);
	}
	
	public static boolean registerAccount(final Account a) {
		if (a != null && !Core.m_aaAccounts.containsKey(a.getAccountNumber()) && !Core.m_aaAccounts.containsValue(a)) {
			Core.m_aaAccounts.put(a.getAccountNumber(), a);
			return Core.m_aaAccounts.containsKey(a.getAccountNumber());
		} else {
			return false;
		}
	}
	
	public static boolean registerUser(final String username, final User u) {
		if (u != null && !Core.m_auUsers.containsKey(username) && !Core.m_auUsers.containsValue(u)) {
			Core.m_auUsers.put(username, u);
			return Core.m_auUsers.containsKey(username);
		} else {
			return false;
		}
	}
	
}
