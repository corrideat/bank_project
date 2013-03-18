package backend;

public enum Parameters {
	ACCOUNT("Account", Types.LONG);
	
	final Types m_tType;
	final String m_sDescription;

	Parameters(final String description, final Types t) {
		m_sDescription = description;
		m_tType = t;
	}

}
