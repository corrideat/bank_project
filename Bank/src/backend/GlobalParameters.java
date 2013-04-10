package backend;

public enum GlobalParameters {
	RATE_SAVINGS(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_6M(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_1Y(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_2Y(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_3Y(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_4Y(0D, "Interest Rate for Savings Accounts"),
	RATE_CD_5Y(0D, "Interest Rate for Savings Accounts"),
	RATE_LOAN(0D, "Interest Rate for Savings Accounts"),
	RATE_LOC(0D, "Interest Rate for Savings Accounts"),
	SAVINGS_FEE(0D, "Interest Rate for Savings Accounts"),
	CHECKING_FEE(0D, ""),
	SAVINGS_MINIMUM_BALANCE(0D, ""),
	CHECKING_MINIMUM_BALANCE(0D, ""),
	CHECKING_MINIMUM_FREE_BALANCE(0D, ""),
	CD_MINIMUM_BALANCE(0D, ""),
	CHECKING_OVERDRAFT_FEE(-1D, ""), // Fees are negative
	LOC_MAXIMUM_BALANCE(1D, ""),
	LOC_MINIMUM_PAYMENT(0D, ""),
	LOC_MINIMUM_PAYMENT_FRACTION(0D, ""),
	LOC_LATE_PENALTY(0D, "");
	
	private double m_dValue;
	private final String m_sDescription;	
	
	GlobalParameters(final double v, final String description) {
		this.m_sDescription = description;
		this.set(v);
	}
	
	public double get() {
		return m_dValue;
	}

	public String describe() {
		return m_sDescription;
	}
	
	public void set(final double v) {
		m_dValue = v;
	}
	
	public void set(final Double v) {
		m_dValue = v.doubleValue();
	}
}