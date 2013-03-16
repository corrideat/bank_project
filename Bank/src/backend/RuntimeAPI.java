package backend;

import date.DateTime;

public final class RuntimeAPI {
	static double savings_rate = 0.1;
	
	
	static double savings_fee = 1;
	static double savings_minimum = 1;
	

	public RuntimeAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public static double CDMinimumBalance() {
		return 1D;
	}
	
	public static double CheckingMinimumBalance() {
		return -1D;
	}
	
	// Fees are negative
	public static double CheckingOverdraftFee() {
		return -1D;
	}

	public static DateTime now() {
		// TODO Auto-generated method stub
		return null;
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
	
	public static enum ServiceCharge {
		CHECKING, SAVINGS;
		
		public double getServiceFee(final double accountBalance) {
			switch(this) {
			case SAVINGS:
				if (accountBalance < savings_minimum) {
					return 0D;
				} else {
					return savings_fee;
				}
			case CHECKING:
				if (accountBalance < savings_minimum) {
					return 0D;
				} else {
					return savings_fee;
				}
			default:
				return 0D;
			}
		}
	}

	public static double LOCMaximumBalance() {
		return 0D;
	}

	// Negative: reduce from current cap (e.g., new loan), positive: increase from current cap
	public static void adjustCap(double d) throws InsufficientCreditAvailableException {
		// TODO Auto-generated method stub		
	}

	public static double getCap() {
		return 0D;
	}
}
