package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import user.User;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import account.Account;
import account.AccountType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAccountFrame extends JFrame {

	private JPanel contentPane;
	private JTextField balancePrinciple;
	public static User user;
	private JTextField interestRate;
	private JTextField installments;
	private JTextField limit;
	private JTextField length;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccountFrame frame = new CreateAccountFrame();
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
	public CreateAccountFrame() {
		setAlwaysOnTop(true);
		setTitle("Account Creation");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setBounds(10, 11, 87, 14);
		contentPane.add(lblAccountType);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(141, 8, 101, 20);
		contentPane.add(comboBox);
		
		JLabel lblBalance = new JLabel("Balance/Prinicple:");
		lblBalance.setBounds(10, 42, 121, 14);
		contentPane.add(lblBalance);
		
		balancePrinciple = new JTextField();
		balancePrinciple.setText("0.00");
		balancePrinciple.setBounds(141, 39, 101, 20);
		contentPane.add(balancePrinciple);
		balancePrinciple.setColumns(10);
		
		interestRate = new JTextField();
		interestRate.setBounds(141, 70, 101, 20);
		contentPane.add(interestRate);
		interestRate.setColumns(10);
		
		installments = new JTextField();
		installments.setBounds(141, 101, 101, 20);
		contentPane.add(installments);
		installments.setColumns(10);
		
		JLabel lblInterestRate = new JLabel("Interest Rate:");
		lblInterestRate.setBounds(10, 73, 121, 14);
		contentPane.add(lblInterestRate);
		
		JLabel lblInstallments = new JLabel("Installments:");
		lblInstallments.setBounds(10, 104, 101, 14);
		contentPane.add(lblInstallments);
		
		limit = new JTextField();
		limit.setBounds(141, 132, 101, 20);
		contentPane.add(limit);
		limit.setColumns(10);
		
		length = new JTextField();
		length.setBounds(141, 163, 101, 20);
		contentPane.add(length);
		length.setColumns(10);
		
		JLabel lblLimit = new JLabel("Limit:");
		lblLimit.setBounds(10, 135, 72, 14);
		contentPane.add(lblLimit);
		
		JLabel lblLength = new JLabel("Length:");
		lblLength.setBounds(10, 166, 87, 14);
		contentPane.add(lblLength);
		
		JEditorPane dtrpnOnlyTheField = new JEditorPane();
		dtrpnOnlyTheField.setText("Only the fields pertainingto your account type will be used.");
		dtrpnOnlyTheField.setBounds(261, 8, 142, 54);
		contentPane.add(dtrpnOnlyTheField);
		
		JLabel lblloanloc = new JLabel("(Loan/LoC)");
		lblloanloc.setBounds(252, 73, 94, 14);
		contentPane.add(lblloanloc);
		
		JLabel lblloan = new JLabel("(Loan)");
		lblloan.setBounds(252, 104, 79, 14);
		contentPane.add(lblloan);
		
		JLabel lblcd = new JLabel("(CD)");
		lblcd.setBounds(252, 135, 46, 14);
		contentPane.add(lblcd);
		
		JLabel label = new JLabel("(CD)");
		label.setBounds(252, 166, 46, 14);
		contentPane.add(label);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// this is where the accounts need to be made the variables are as follows:
				// balancePrinciple, interestRate, installments, limit, length
				// use the .getText(variable) method and change it into doubles if you need to [Double.parseDouble(textField.getText())]
				
				if ((Object)comboBox.getSelectedItem() == AccountType.SAVINGS){
						//make saving account
				}else if(){
					//etc. etc.
				}
			}
		});
		btnCreateAccount.setBounds(64, 194, 135, 23);
		contentPane.add(btnCreateAccount);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(263, 194, 89, 23);
		contentPane.add(btnCancel);
	}
}
