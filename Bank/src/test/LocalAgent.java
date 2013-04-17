package test;

import backend.Agent;

public class LocalAgent implements Agent {
	protected static Agent agent = new LocalAgent(); 

	@Override
	public boolean isInternal() {
		return false;
	}

	@Override
	public String describe() {
		return "Local";
	}

	private LocalAgent() {
		
	}
}
