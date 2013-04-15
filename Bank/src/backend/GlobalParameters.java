package backend;

public enum GlobalParameters {
	MASTER_RATE_SAVINGS(0.01D, "Master Savings Interest Rate"),  // Marginal rage. 0.1 means 10% interest.
	OFFSET_RATE_SAVINGS(0D, "Savings offset from Master Savings Rate"),
	OFFSET_RATE_CD6M(.02D, "6M CD offset from Savings Rate"),
	OFFSET_RATE_CD1Y(.02D, "1Y CD offset from 6M CD Rate"),
	OFFSET_RATE_CD2Y(.02D, "2Y CD offset from 1Y CD Rate"),
	OFFSET_RATE_CD3Y(.015D, "3Y CD offset from 2Y CD Rate"),
	OFFSET_RATE_CD4Y(.01D, "4Y CD offset from 3Y CD Rate"),
	OFFSET_RATE_CD5Y(.01D, "5Y CD offset from 4Y CD Rate"),
	MASTER_RATE_LOAN(0.07D, "Master Loan Interest"),
	OFFSET_RATE_LOAN(0D, "Loan offset from Master Loan Interest"),
	OFFSET_RATE_LOC(.15D, "Line of Credit Offset from Loan"),
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
	LOAN_LATE_PENALTY(-25D, "Loan Account Late Penalty Fee per Month"),  // Fees are negative
	TELLER_INTERACTION_FEE(-25D, "Teller Interaction Fee"); // Fees are negative

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