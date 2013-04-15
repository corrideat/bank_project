package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import backend.InsufficientCreditAvailableException;
import backend.RuntimeAPI;

import date.DateTime;

import user.Customer;
import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JComboBox;

import account.Account;
import account.AccountParameters;
import account.AccountType;
import account.CD.CD_type;
import javax.swing.JEditorPane;

public class CreateCustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField first;
	private JTextField last;
	private JTextField textField_2;
	private JTextField ssn;
	private JTextField username;
	private JTextField pass;
	private JButton btnCreate;
	private JButton btnCancel;
	private JLabel label;
	private JTextField textField_6;
	private JLabel label_1;
	private JTextField textField_7;
	private JLabel label_2;
	private JComboBox<AccountType> accountTypeBox;
	private JLabel lblBalanceprinciple;
	private JTextField principle;
	private JTextField offset;
	private JTextField installments;
	private JLabel label_4;
	private JLabel label_5;
	private JTextField limit;
	private JComboBox<CD_type> lengthBox;
	private JLabel label_6;
	private JLabel label_7;
	private JEditorPane editorPane;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	public static User user;
	public static Account newAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateCustomerFrame frame = new CreateCustomerFrame();
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
	public CreateCustomerFrame() {
		setTitle("Create Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 682, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		first = new JTextField();
		first.setBounds(107, 10, 105, 20);
		contentPane.add(first);
		first.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 13, 105, 14);
		contentPane.add(lblFirstName);
		
		last = new JTextField();
		last.setColumns(10);
		last.setBounds(107, 40, 105, 20);
		contentPane.add(last);
		
		JLabel lblLastname = new JLabel("Last Name:");
		lblLastname.setBounds(10, 43, 105, 14);
		contentPane.add(lblLastname);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(107, 70, 23, 20);
		contentPane.add(textField_2);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setBounds(10, 73, 105, 14);
		contentPane.add(lblBirthday);
		
		JLabel lblSsn = new JLabel("SSN:");
		lblSsn.setBounds(10, 103, 105, 14);
		contentPane.add(lblSsn);
		
		ssn = new JTextField();
		ssn.setColumns(10);
		ssn.setBounds(107, 100, 105, 20);
		contentPane.add(ssn);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 133, 105, 14);
		contentPane.add(lblUsername);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(107, 130, 105, 20);
		contentPane.add(username);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 163, 105, 14);
		contentPane.add(lblPassword);
		
		pass = new JTextField();
		pass.setColumns(10);
		pass.setBounds(107, 160, 105, 20);
		contentPane.add(pass);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(310, 196, 89, 23);
		contentPane.add(btnCancel);
		
		label = new JLabel("/");
		label.setBounds(138, 73, 11, 14);
		contentPane.add(label);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(151, 71, 23, 20);
		contentPane.add(textField_6);
		
		label_1 = new JLabel("/");
		label_1.setBounds(179, 73, 11, 14);
		contentPane.add(label_1);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(189, 71, 23, 20);
		contentPane.add(textField_7);
		
		label_2 = new JLabel("Account Type:");
		label_2.setBounds(238, 13, 87, 14);
		contentPane.add(label_2);
		
		accountTypeBox = new JComboBox<AccountType>();
		accountTypeBox.setBounds(369, 10, 101, 20);
		contentPane.add(accountTypeBox);
		accountTypeBox.addItem(account.AccountType.CHECKING);
		accountTypeBox.addItem(account.AccountType.SAVINGS);
		accountTypeBox.addItem(account.AccountType.LOAN);
		accountTypeBox.addItem(account.AccountType.CD);
		accountTypeBox.addItem(account.AccountType.LOC);
		
		lblBalanceprinciple = new JLabel("Balance/Principle:");
		lblBalanceprinciple.setBounds(238, 44, 121, 14);
		contentPane.add(lblBalanceprinciple);
		
		principle = new JTextField();
		principle.setText("0.00");
		principle.setColumns(10);
		principle.setBounds(369, 41, 101, 20);
		contentPane.add(principle);
		
		offset = new JTextField();
		offset.setColumns(10);
		offset.setBounds(369, 72, 101, 20);
		contentPane.add(offset);
		
		installments = new JTextField();
		installments.setColumns(10);
		installments.setBounds(369, 103, 101, 20);
		contentPane.add(installments);
		
		label_4 = new JLabel("Interest Rate Offset:");
		label_4.setBounds(238, 75, 121, 14);
		contentPane.add(label_4);
		
		label_5 = new JLabel("Installments:");
		label_5.setBounds(238, 106, 101, 14);
		contentPane.add(label_5);
		
		limit = new JTextField();
		limit.setColumns(10);
		limit.setBounds(369, 134, 101, 20);
		contentPane.add(limit);
		
		lengthBox = new JComboBox<CD_type>();
		lengthBox.setBounds(369, 165, 101, 20);
		contentPane.add(lengthBox);
		lengthBox.addItem(account.CD.CD_type.CD_6M);
		lengthBox.addItem(account.CD.CD_type.CD_1Y);
		lengthBox.addItem(account.CD.CD_type.CD_2Y);
		lengthBox.addItem(account.CD.CD_type.CD_3Y);
		lengthBox.addItem(account.CD.CD_type.CD_4Y);
		lengthBox.addItem(account.CD.CD_type.CD_5Y);
		
		label_6 = new JLabel("Limit:");
		label_6.setBounds(238, 137, 72, 14);
		contentPane.add(label_6);
		
		label_7 = new JLabel("Length:");
		label_7.setBounds(238, 168, 87, 14);
		contentPane.add(label_7);
		
		editorPane = new JEditorPane();
		editorPane.setText("Only the fields pertaining to your account type will be used.");
		editorPane.setBounds(489, 10, 142, 54);
		contentPane.add(editorPane);
		
		label_8 = new JLabel("(Loan/LoC)");
		label_8.setBounds(480, 75, 94, 14);
		contentPane.add(label_8);
		
		label_9 = new JLabel("(Loan)");
		label_9.setBounds(480, 106, 79, 14);
		contentPane.add(label_9);
		
		label_10 = new JLabel("(CD)");
		label_10.setBounds(480, 137, 46, 14);
		contentPane.add(label_10);
		
		label_11 = new JLabel("(CD)");
		label_11.setBounds(480, 168, 46, 14);
		contentPane.add(label_11);
		
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//create account 
				EnumMap<AccountParameters, Object> params = new EnumMap<AccountParameters, Object>(AccountParameters.class);
				if(!installments.getText().equals("")){params.put(AccountParameters.INSTALLMENTS, Double.parseDouble(installments.getText()));}
				if(!offset.getText().equals("")){params.put(AccountParameters.OFFSET, Double.parseDouble(offset.getText()));}
				if(!principle.getText().equals("")){params.put(AccountParameters.PRINCIPAL, Double.parseDouble(principle.getText()));}
				params.put(AccountParameters.CD_TYPE, (account.CD.CD_type)lengthBox.getSelectedItem());
				
				try {
					backend.Core.m_auUsers.put(username.getText(), user.m_ePrivileges.createCustomer(first.getText(), last.getText(), new DateTime(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_6.getText()), Integer.parseInt(textField_7.getText())), username.getText(), pass.getText(), Integer.parseInt(ssn.getText()), (AccountType)accountTypeBox.getSelectedItem(), (Map<AccountParameters, Object>)params));
					JOptionPane.showMessageDialog(null, "Creation successful.", "Account Creation", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (InsufficientCreditAvailableException e1) {
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure there is sufficient credit.", "Account Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1){
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure your inputs are correct.", "Account Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
					
				//Customer newC = new Customer( Integer.parseInt(textField_3.getText()), textField_4.getText(), textField_5.getText());					
					
				
				//JOptionPane.showMessageDialog(null, "Unable to create account. Username is taken or not all information is correct.", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		btnCreate.setBounds(211, 196, 89, 23);
		contentPane.add(btnCreate);
	}
}
