package backend;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import account.Account;

public enum Actions {
	checkBalance("Check Balance", Types.DOUBLE, Parameters.ACCOUNT);
	
	final Set<Parameters> m_sParams;
	public final String m_sDescription;
	public final Types m_tReturnType;
	
	Actions(final String description, Types returnType, Parameters ... params) {
		m_sDescription = description;
		m_tReturnType = returnType;
		m_sParams = new TreeSet<Parameters>();
		for(Parameters p : params) {
			m_sParams.add(p);
		}
	}
	
	final public Object perform(Map<Parameters, Object> args) {
		switch(this) {
			case checkBalance:
				return new Double(((Account) args.get(Parameters.ACCOUNT)).getBalance());
			default:
				break;
		}
		return null;
	}
	
	final public Parameters[] parameters() {
		return 	m_sParams.toArray(new Parameters[0]);	
	}
}
