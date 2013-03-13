package account;

public class IllegalOperationException extends TransactionValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 829912748991488110L;

	public IllegalOperationException() {
		// TODO Auto-generated constructor stub
	}

	public IllegalOperationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IllegalOperationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
