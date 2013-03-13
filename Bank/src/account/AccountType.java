package account;

import java.util.Map;

import backend.InsufficientCreditAvailableException;

import account.CD.CD_type;

public enum AccountType {
	CHECKING, SAVINGS, CD, LOC, LOAN;
	
	protected long generateAccountNumber() {
		return 0;
	}
	
	public Account open(AccountHolder ah, Map<String, Object> params) throws InsufficientCreditAvailableException {
		switch(this) {
		case CHECKING:
			return new Checking(generateAccountNumber(), ah);
		case SAVINGS:
			return new Savings(generateAccountNumber(), ah);
		case CD:
			return new CD((CD_type) params.get("cd_type"), generateAccountNumber(), ah);
		case LOC:
			return new LineOfCredit((Double) params.get("offset"), (Double) params.get("limit"), generateAccountNumber(), ah);
		case LOAN:
			return new Loan((Double) params.get("principal"), (Short) params.get("installments"), (Double) params.get("offset"), generateAccountNumber(), ah);
		default:
			throw new IllegalArgumentException();
		}
	}
}
