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
