package backend;

public interface Agent {
	public boolean isInternal(); // Done by either workers or automatic system such as overdraft fee
	public String describe();
}
