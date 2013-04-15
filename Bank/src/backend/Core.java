package backend;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import date.*;
import ui.*;
import user.*;
import account.*;

public class Core {
	
	//these fields need to be public for the frames to see them since they are in a different package
	public static DateTime m_dtCurrentTime = new DateTime();
	
	public static Map<Long, Account> m_aaAccounts = new HashMap<Long, Account>();
	public static Map<String, User> m_auUsers  = new HashMap<String, User>();
	
	public static double currentCap = 0D; 

	public static void main(String[] args) {
		new AccountManager("Patricia", "Rhodes", new DateTime(1951, 1, 17), 202549277, "accountmanager", "password");
		Agent ag = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
		new Auditor("Michael", "Schneider", new DateTime(1965, 4, 23), 808960670, "auditor", "password");
		new OperationManager("Donna", "Wash", new DateTime(1947, 12, 20), 491669260, "operationmanager", "password");
		new Accountant("Brian", "Tyree", new DateTime(1984, 11, 8), 765572580, "accountant", "password");
		
		try {
			Customer c = m_auUsers.get("accountmanager").m_ePrivileges.createCustomer("George", "West", new DateTime(1961, 8, 12), "customer1", "password", 218656057, AccountType.CHECKING, null);
			c.getAccounts()[0].postTransaction(new Transaction(ag, c.getAccounts()[0], 1E6, "$1M Initial Deposit"));
		} catch (InsufficientCreditAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame loginFrame = new MainFrame();
		loginFrame.setVisible(true);
	}
	
	//the following will return the average balance of each type of account currently in existence
	public static double AllCheckingBalanceAvg() {
		double totalOfBalances=0;
		int accountCount = 0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Checking){
			totalOfBalances = totalOfBalances + CurrentAccount.getBalance();
		    accountCount = accountCount + 1;
		    }
	}
		return totalOfBalances/accountCount; 
	}
	public static double AllSavingsBalanceAvg() {
		double totalOfBalances=0;
		int accountCount = 0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Savings){
			totalOfBalances = totalOfBalances + CurrentAccount.getBalance();
		    accountCount = accountCount + 1;
		    }
	}
		return totalOfBalances/accountCount; 
	}	
	public static double AllCDBalanceAvg() {
		double totalOfBalances=0;
		int accountCount = 0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof CD){
			totalOfBalances = totalOfBalances + CurrentAccount.getBalance();
		    accountCount = accountCount + 1;
		    }
	}
		return totalOfBalances/accountCount; 
	}	
	public static double AllLoanBalanceAvg() {
		double totalOfBalances=0;
		int accountCount = 0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Loan){
			totalOfBalances = totalOfBalances + CurrentAccount.getBalance();
		    accountCount = accountCount + 1;
		    }
	}
		return totalOfBalances/accountCount; 
	}
	public static double AllLOCBalanceAvg() {
		double totalOfBalances=0;
		int accountCount = 0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof LineOfCredit){
			totalOfBalances = totalOfBalances + CurrentAccount.getBalance();
		    accountCount = accountCount + 1;
		    }
	}
		return totalOfBalances/accountCount; 
	}	

	//the following will return the total sum of balances of each type of account currently in existence
	public static double CheckingBalanceSum() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Checking){
		    sum = sum + CurrentAccount.getBalance();
		    }
	}
		return sum;
	}
	public static double SavingsBalanceSum() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Savings){
		    sum = sum + CurrentAccount.getBalance();
		    }
	}
		return sum;
	}
	public static double CDBalanceSum() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof CD){
		    sum = sum + CurrentAccount.getBalance();
		    }
	}
		return sum;
	}	
	public static double LoanBalanceSum() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof Loan){
		    sum = sum + CurrentAccount.getBalance();
		    }
	}
		return sum;
	}	
	public static double LOCBalanceSum() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof LineOfCredit){
		    sum = sum + CurrentAccount.getBalance();
		    }
	}
		return sum;
	}	
	
	//will return the sum of credit limits of all Line Of Credit accounts
	public static double SumLOCLimits() {
		double totalOfLimits=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    if (CurrentAccount instanceof LineOfCredit){
		    LineOfCredit CurrentLOC=(LineOfCredit) CurrentAccount;
			totalOfLimits = totalOfLimits + CurrentLOC.getLimit();
		    }
	}
		return totalOfLimits;
	}
	
	//will return the sum of ALL accounts' balances
	public static double TotalBankBalance() {
		double sum=0;
		for (Account CurrentAccount : m_aaAccounts.values()) {
		    sum = sum + CurrentAccount.getBalance();
	}
		return sum;
	}	
	
	public static void save() {
		
	}
	
	public static void load() {
		
	}

	public static void timeShiftNotification() {
		System.out.println(Core.m_dtCurrentTime);
		for (Account a:Core.m_aaAccounts.values()) {
			a.update();
		}
	}

}
