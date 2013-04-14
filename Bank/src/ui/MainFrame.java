package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JPasswordField;

import account.*;
import backend.*;
import date.*;
import user.*;
import user.Employee.EmployeeCustomer;

import javax.swing.JSeparator;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	public User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Banking");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 232, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(78, 11, 132, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 14, 94, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 45, 94, 14);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Map<String, User> userMap = backend.Core.m_auUsers;			// get Map of users
				Object[] userArray = userMap.values().toArray(); 	// convert Map to array
				
				String pass = new String(passwordField.getPassword());
				
				
				for (int i = 0; i < userArray.length; i++){
					if(((User)userArray[i]).authenticate(textField.getText(),pass)){	//find
						user = (User)userArray[i];
					}
				}
				
				if (user == null){
					JOptionPane.showMessageDialog(null, "Login unsuccessful. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
				}else{
				
					JFrame userFrame = new CustomerFrame();
					
					if (user instanceof Teller){					// Teller
						userFrame = new TellerFrame();
					}else if (user instanceof Customer){			// Customer
						userFrame = new CustomerFrame();
	//				}else if (user instanceof EmployeeCustomer){	// Customer Employee
	//					userFrame = new EmployeeCustomerFrame();
					}else if (user instanceof AccountManager){		// Account Manager
						userFrame = new AccountManagerFrame();
					}else if (user instanceof Accountant){			// Accountant
						userFrame = new AccountantFrame();
					}else if (user instanceof Auditor){				// Auditor
						userFrame = new AuditorFrame();
	//				}else if (user instanceof OperationManager){	// Operation Manager
	//					userFrame = new OperationManagerFrame();
					}
					
					userFrame.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setBounds(68, 73, 89, 23);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(78, 42, 132, 20);
		contentPane.add(passwordField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 106, 226, 2);
		contentPane.add(separator);
		
		JButton btnTestFrames = new JButton("Test Frames");
		btnTestFrames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame testFrame = new TestFrame();
				testFrame.setVisible(true);
			}
		});
		btnTestFrames.setBounds(47, 119, 132, 23);
		contentPane.add(btnTestFrames);
	}
}
