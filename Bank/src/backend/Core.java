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
		
		//need to instantiate default users and add to HashMap
		DateTime bday1 = new DateTime();
		Customer cust1 = new Customer("Phillip","Riley",bday1,123456789,"PhillipR","password1");
		
		
		
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
