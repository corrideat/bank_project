package account;

public class InsufficientFundsException extends TransactionValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 335842465461164083L;

	public InsufficientFundsException() {
		// TODO Auto-generated constructor stub
	}

	public InsufficientFundsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InsufficientFundsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
