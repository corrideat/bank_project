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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EnumMap;

import javax.swing.JComboBox;

import account.AccountParameters;
import account.AccountType;
import account.CD.CD_type;
import javax.swing.JEditorPane;

public class CreateCustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JButton btnCreate;
	private JButton btnCancel;
	private JLabel label;
	private JTextField textField_6;
	private JLabel label_1;
	private JTextField textField_7;
	private JLabel label_2;
	private JComboBox<AccountType> comboBox;
	private JLabel label_3;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JLabel label_4;
	private JLabel label_5;
	private JTextField textField_11;
	private JComboBox<CD_type> lengthBox;
	private JLabel label_6;
	private JLabel label_7;
	private JEditorPane editorPane;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;

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
		
		textField = new JTextField();
		textField.setBounds(107, 10, 105, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 13, 105, 14);
		contentPane.add(lblFirstName);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(107, 40, 105, 20);
		contentPane.add(textField_1);
		
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
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(107, 100, 105, 20);
		contentPane.add(textField_3);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 133, 105, 14);
		contentPane.add(lblUsername);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(107, 130, 105, 20);
		contentPane.add(textField_4);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 163, 105, 14);
		contentPane.add(lblPassword);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(107, 160, 105, 20);
		contentPane.add(textField_5);
		
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//create account 
				EnumMap<AccountParameters, Object> params = new EnumMap<AccountParameters, Object>(AccountParameters.class);
				if(!installments.getText().equals("")){params.put(AccountParameters.INSTALLMENTS, Double.parseDouble(installments.getText()));}
				if(!offset.getText().equals("")){params.put(AccountParameters.OFFSET, Double.parseDouble(offset.getText()));}
				if(!principal.getText().equals("")){params.put(AccountParameters.PRINCIPAL, Double.parseDouble(principal.getText()));}
				params.put(AccountParameters.CD_TYPE, (account.CD.CD_type)lengthBox.getSelectedItem());
				
				try {
					user.m_ePrivileges.createAccount(user, (AccountType)accountTypeBox.getSelectedItem(), params);
					JOptionPane.showMessageDialog(null, "Creation successful.", "Account Creation", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (InsufficientCreditAvailableException e1) {
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure their is sufficient credit.", "Account Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1){
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure your inputs are correct.", "Account Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
								
				//Customer newC = new Customer(textField.getText(), textField_1.getText(), new DateTime(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_6.getText()), Integer.parseInt(textField_7.getText())), Integer.parseInt(textField_3.getText()), textField_4.getText(), textField_5.getText());
				if(RuntimeAPI.registerUser(textField_4.getText(), newC)){
					
					
					
				}else{
					JOptionPane.showMessageDialog(null, "Unable to create account. Username is taken or not all information is correct.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCreate.setBounds(211, 196, 89, 23);
		contentPane.add(btnCreate);
		
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
		
		comboBox = new JComboBox<AccountType>();
		comboBox.setBounds(369, 10, 101, 20);
		contentPane.add(comboBox);
		
		label_3 = new JLabel("Principal:");
		label_3.setBounds(238, 44, 121, 14);
		contentPane.add(label_3);
		
		textField_8 = new JTextField();
		textField_8.setText("0.00");
		textField_8.setColumns(10);
		textField_8.setBounds(369, 41, 101, 20);
		contentPane.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(369, 72, 101, 20);
		contentPane.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(369, 103, 101, 20);
		contentPane.add(textField_10);
		
		label_4 = new JLabel("Interest Rate Offset:");
		label_4.setBounds(238, 75, 121, 14);
		contentPane.add(label_4);
		
		label_5 = new JLabel("Installments:");
		label_5.setBounds(238, 106, 101, 14);
		contentPane.add(label_5);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(369, 134, 101, 20);
		contentPane.add(textField_11);
		
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
	}
}
