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
	
	public static String FormatedAllCheckingBalanceAvg(){
		return "The average of all checking account balances is " + Core.AllCheckingBalanceAvg();
	}
	public static String FormatedAllSavingsBalanceAvg(){
		return "The average of all savings account balances is " + Core.AllSavingsBalanceAvg();
	}
	public static String FormatedAllCDBalanceAvg(){
		return "The average of all CD account balances is " + Core.AllCDBalanceAvg();
	}
	public static String FormatedAllCheckingLoanAvg(){
		return "The average of all loan account balances is " + Core.AllLoanBalanceAvg();
	}
	public static String FormatedAllLOCBalanceAvg(){

		return "The average of all line of credit account balances is " + Core.AllLOCBalanceAvg();
	}

	public static String FormatedCheckingBalanceSum(){
		return "The sum of all checking account balances is " + Core.CheckingBalanceSum();
	}
	public static String FormatedSavingsBalanceSum(){
		return "The sum of all savings account balances is " + Core.SavingsBalanceSum();
	}
	public static String FormatedCDBalanceSum(){
		return "The sum of all CD account balances is " + Core.CDBalanceSum();
	}	
	public static String FormatedLoanBalanceSum(){
		return "The sum of all loan account balances is " + Core.LoanBalanceSum();
	}	
	public static String FormatedLOCBalanceSum(){
		return "The sum of all line of credit account balances is " + Core.LOCBalanceSum();
	}	

	public static String FormatedLOCLimitBalanceSum(){
		return "The sum of all line of credit account limits is " + Core.SumLOCLimits();
	}
	public static String FormatedEntireBankBalance(){
		return "The sum of all the bank's account balances is " + Core.TotalBankBalance();
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
	

//	public static void save() {
//		try{
//			FileOutputStream foStream = new FileOutputStream("database");			//create file output stream
//			
//			Long[] keys1 = new Long[m_aaAccounts.size()];
//			Account[] values1 = new Account[m_aaAccounts.size()];
//			int index1 = 0;
//			for (Map.Entry<Long, Account> mapEntry : m_aaAccounts.entrySet()) {
//			    keys1[index1] = mapEntry.getKey();
//			    values1[index1] = mapEntry.getValue();
//			    index1++;
//			}
//			
//			
//			ObjectOutputStream ooStream1 = new ObjectOutputStream(foStream);			//direct object to file
//			ooStream1.writeObject(keys1);											//write file
//			ObjectOutputStream ooStream2 = new ObjectOutputStream(foStream);			//direct object to file
//			ooStream2.writeObject(values1);											//write file
//			
//			String[] keys2 = new String[m_auUsers.size()];
//			User[] values2 = new User[m_auUsers.size()];
//			int index2 = 0;
//			for (Map.Entry<String, User> mapEntry : m_auUsers.entrySet()) {
//			    keys1[index2] = mapEntry.getKey();
//			    values1[index2] = mapEntry.getValue();
//			    index2++;
//			}
//			
//			ObjectOutputStream ooStream3 = new ObjectOutputStream(foStream);			//direct object to file
//			ooStream3.writeObject(m_auUsers);											//write file
//			ObjectOutputStream ooStream4 = new ObjectOutputStream(foStream);			//direct object to file
//			ooStream4.writeObject(m_auUsers);											//write file
//			foStream.flush();														//clear file output stream
//			foStream.close();														//close file output stream
//		}catch (IOException e){	
//			System.out.println("Error during output of files: " + e.toString());	//print any exception
//		}
//	}
//	
//	public static void load() {
//		try{
//			FileInputStream fiStream = new FileInputStream("database");				//create file input stream
//			ObjectInputStream oiStream1 = new ObjectInputStream(fiStream);			//create object input stream
//			m_aaAccounts = (HashMap<Long, Account>) oiStream1.readObject();						//write file to program
//			ObjectInputStream oiStream2 = new ObjectInputStream(fiStream);			//create object input stream
//			m_auUsers = (HashMap<String, User>) oiStream2.readObject();						//write file to program
//			fiStream.close();														//close file input stream
//		}catch (Exception e){
//			System.out.println("Error during input of files: " + e.toString());		//print any exception
//		}	
//	}
