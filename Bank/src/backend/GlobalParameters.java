package backend;

public enum GlobalParameters {
	RATE_SAVINGS(0.01D, "Interest for Savings Accounts"),  // Marginal rage. 0.1 means 10% interest.
	RATE_CD_6M(0.02D, "Interest for 6-Month CD"), 
	RATE_CD_1Y(0.025D, "Interest for 1-Year CD"),
	RATE_CD_2Y(0.028D, "Interest for 2-Year CD"),
	RATE_CD_3Y(0.03D, "Interest for 3-Year CD"),
	RATE_CD_4Y(0.03D, "Interest for 4-Year CD"),
	RATE_CD_5Y(0.03D, "Interest for 5-Year CD"),
	RATE_LOAN(0.07D, "Interest for Loan"),
	RATE_LOC(0.25D, "Interest for LOC"), // 
	SAVINGS_FEE(-5D, "Savings Account Fee"), // Fees are negative
	SAVINGS_MINIMUM_GRATIS_BALANCE(100D, "Free Minimum Balance for Savings Accounts"),
	CHECKING_MINIMUM_BALANCE(-20D, "Minimum Allowable Balance for Checking Accounts"),
	CHECKING_MINIMUM_GRATIS_BALANCE(100D, "Free Minimum Balance for Checking Accounts"), ///
	CHECKING_FEE(-5D, "Checking Account Fee"), // Fees are negative
	CHECKING_OVERDRAFT_FEE(-35D, "Checking Overdraft Fee"), // Fees are negative
	CD_MINIMUM_BALANCE(1000D, "Minimum Balance for CD"),
	LOC_MAXIMUM_BALANCE(0D, "LOC Maximum Balance"),
	LOC_MINIMUM_PAYMENT(20D, "LOC Minimum Payment per Month"), // The bank asks you to monthly pay 10% of what you have spent 
	LOC_MINIMUM_PAYMENT_FRACTION(0.2D, "LOC Minimum Payment Fraction per Month"),
	LOC_LATE_PENALTY(-20D, "LOC Account Late Penalty Fee per Month"), // Fees are negative
	LOAN_LATE_PENALTY(-25D, "Loan Account Late Penalty Fee per Month"); // Fees are negative
	
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
