package backend;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import account.Account;

public enum Actions {	
	checkBalance("Check Balance", "TODO", Types.DOUBLE, Parameters.ACCOUNT_HOLDER, Parameters.ACCOUNT);
	
	public class Subaction {
		protected final Set<Actions> m_aaPossibilities;
		protected final Map<Parameters, Object> m_mRestrictions;

		public Subaction(Set<Actions> possibilities, Map<Parameters, Object> restrictions) {
			m_aaPossibilities = possibilities;
			m_mRestrictions = restrictions;
		}
		
		public Actions[] getPossibilities() {
			return m_aaPossibilities.toArray(new Actions[0]);
		}
	}

	
	final Set<Parameters> m_sParams;
	public final String m_sDescription;
	public final String m_sResultString;
	public final Types m_tReturnType;
	
	Actions(final String description, final String result, Types returnType, Parameters ... params) {
		m_sDescription = description;
		m_sResultString =result;
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
