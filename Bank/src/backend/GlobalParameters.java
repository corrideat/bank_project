package backend;

public enum GlobalParameters {
	RATE_SAVINGS(0D, "Interest for Savings Accounts"),
	RATE_CD_6M(0D, "Interest for 6M CD"),
	RATE_CD_1Y(0D, "Interest for 1Y CD"),
	RATE_CD_2Y(0D, "Interest for 2Y CD"),
	RATE_CD_3Y(0D, "Interest for 3Y CD"),
	RATE_CD_4Y(0D, "Interest for 4Y CD"),
	RATE_CD_5Y(0D, "Interest for 5Y CD"),
	RATE_LOAN(0D, "Interest for Loan"),
	RATE_LOC(0D, "Interest for LoC"),
	SAVINGS_FEE(0D, "Savings Account Fee"), // Fees are negative
	SAVINGS_MINIMUM_GRATIS_BALANCE(0D, "Free Minimum Balance for Savings Accounts"),
	CHECKING_MINIMUM_BALANCE(0D, "Minimum Allowable Balance for Checking Accounts"),
	CHECKING_MINIMUM_GRATIS_BALANCE(0D, "Free Minimum Balance for Checking Accounts"),
	CHECKING_FEE(0D, "Checking Account Fee"), // Fees are negative
	CHECKING_OVERDRAFT_FEE(-1D, ""), // Fees are negative
	CD_MINIMUM_BALANCE(0D, ""),
	LOC_MAXIMUM_BALANCE(1D, ""),
	LOC_MINIMUM_PAYMENT(0D, ""),
	LOC_MINIMUM_PAYMENT_FRACTION(0D, ""),
	LOC_LATE_PENALTY(0D, ""), // Fees are negative
	LOAN_LATE_PENALTY(0D, ""); // Fees are negative
	
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