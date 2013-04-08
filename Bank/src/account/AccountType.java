package account;

import java.util.Map;
import backend.InsufficientCreditAvailableException;
import account.CD.CD_type;

public enum AccountType {
	CHECKING, SAVINGS, CD, LOC, LOAN;
	
	protected long generateAccountNumber() {
		return 0;
	}
	
	public Account open(AccountHolder ah, Map<AccountParameters, Object> params) throws InsufficientCreditAvailableException {
		switch(this) {
		case CHECKING:
			return new Checking(generateAccountNumber(), ah);
		case SAVINGS:
			return new Savings(generateAccountNumber(), ah);
		case CD:
			return new CD((CD_type) params.get(AccountParameters.CD_TYPE), generateAccountNumber(), ah);
		case LOC:
			return new LineOfCredit((Double) params.get(AccountParameters.OFFSET), (Double) params.get(AccountParameters.LIMIT), generateAccountNumber(), ah);
		case LOAN:
			return new Loan((Double) params.get(AccountParameters.PRINCIPAL), (Short) params.get(AccountParameters.INSTALLMENTS), (Double) params.get(AccountParameters.OFFSET), generateAccountNumber(), ah);
		default:
			throw new IllegalArgumentException();
		}
	}
}
