package backend;

import java.io.*;
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
		//save();
		
		JFrame loginFrame = new MainFrame();
		loginFrame.setVisible(true);
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

	public static void timeShiftNotification() {
		System.out.println(Core.m_dtCurrentTime);
		for (Account a:Core.m_aaAccounts.values()) {
			a.update();
		}
	}

}
