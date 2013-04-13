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
	public static void adjustCap(double d) throws InsufficientCreditAvailableException {
		// TODO Auto-generated method stub		
	}

	public static double getCap() {
		return 0D;
	}

	public static Account getAccount(long m_lTargetAccount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
