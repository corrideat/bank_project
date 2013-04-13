package account;

import java.util.Map;
import java.util.Random;

import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;
import account.CD.CD_type;

public enum AccountType {
	CHECKING, SAVINGS, CD, LOC, LOAN;
	
	private static Random random = new Random();
	
	protected long generateAccountNumber() {
		long temp;
		do {
			temp = Math.abs(random.nextLong());
			if (RuntimeAPI.getAccount(temp) == null) {
				return temp;
			}
		} while (true);
	}
	
	public Account open(AccountHolder ah, Map<AccountParameters, Object> params) throws InsufficientCreditAvailableException {
		Account a = null;
		switch(this) {
		case CHECKING:
			a = new Checking(generateAccountNumber(), ah);
			break;
		case SAVINGS:
			a = new Savings(generateAccountNumber(), ah);
			break;
		case CD:
			a = new CD((CD_type) params.get(AccountParameters.CD_TYPE), generateAccountNumber(), ah);
			break;
		case LOC:
			a = new LineOfCredit((Double) params.get(AccountParameters.OFFSET), (Double) params.get(AccountParameters.LIMIT), generateAccountNumber(), ah);
			break;
		case LOAN:
			a = new Loan((Double) params.get(AccountParameters.PRINCIPAL), (Short) params.get(AccountParameters.INSTALLMENTS), (Double) params.get(AccountParameters.OFFSET), generateAccountNumber(), ah);
			break;
		}		

		if (a == null) throw new IllegalArgumentException();
		if (!RuntimeAPI.registerAccount(a)) {
			throw new RuntimeException();
		} else {
			return a;
		}
	}
}
