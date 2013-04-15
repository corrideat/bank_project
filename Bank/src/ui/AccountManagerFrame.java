package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;

import backend.InsufficientCreditAvailableException;

import account.Account;
import account.LineOfCredit;

import user.Customer;
import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AccountManagerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField loanCap;
	private JTextField limit;
	public static User user;
	public static Account currentAccount;
	private int SSN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountManagerFrame frame = new AccountManagerFrame();
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
	public AccountManagerFrame() {
		setTitle("Account Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 336, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(10, 15, 85, 14);
		contentPane.add(lblCustomer);
		
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 77, 152, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Account:");
		label_2.setBounds(10, 49, 66, 14);
		contentPane.add(label_2);
		
		JButton btnNewAccount = new JButton("New");
		btnNewAccount.setBounds(235, 45, 85, 23);
		contentPane.add(btnNewAccount);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 203, 352, 2);
		contentPane.add(separator);
		
		JButton btnNewCustomer = new JButton("New Customer");
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateCustomerFrame userFrame = new CreateCustomerFrame();
				userFrame.setVisible(true);
			}
		});
		btnNewCustomer.setBounds(10, 218, 131, 23);
		contentPane.add(btnNewCustomer);
		
		loanCap = new JTextField();
		loanCap.setEditable(false);
		loanCap.setBounds(241, 219, 79, 20);
		contentPane.add(loanCap);
		loanCap.setColumns(10);
		loanCap.setText(backend.Core.currentCap+"");
		
		JLabel lblLoanLimit = new JLabel("Loan Cap:");
		lblLoanLimit.setBounds(163, 222, 56, 14);
		contentPane.add(lblLoanLimit);
		
		limit = new JTextField();
		limit.setBounds(108, 173, 86, 20);
		contentPane.add(limit);
		limit.setColumns(10);
		
		JButton btnChangeLimit = new JButton("Change");
		btnChangeLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentAccount instanceof LineOfCredit){
					try {
						((LineOfCredit) currentAccount).setLimit(Double.parseDouble(limit.getText()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InsufficientCreditAvailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnChangeLimit.setBounds(214, 172, 89, 23);
		contentPane.add(btnChangeLimit);
		
		JLabel lblCreditLimit = new JLabel("Credit Limit:");
		lblCreditLimit.setBounds(32, 176, 89, 14);
		contentPane.add(lblCreditLimit);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 255, 352, 2);
		contentPane.add(separator_1);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(120, 268, 89, 23);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		contentPane.add(btnLogout);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 96, 310, 66);
		contentPane.add(scrollPane);
		
		final JTextPane accountPane = new JTextPane();
		accountPane.setEditable(false);
		scrollPane.setViewportView(accountPane);
		
		final JComboBox<User> customer = new JComboBox<User>();
		customer.setBounds(86, 12, 139, 20);
		contentPane.add(customer);
		for (int i =0; i < backend.Core.m_auUsers.values().toArray().length; i++){
			if (backend.Core.m_auUsers.values().toArray()[i] instanceof Customer)	
				customer.addItem((User)backend.Core.m_auUsers.values().toArray()[i]);
		}
		
		final JComboBox<Account> accounts = new JComboBox<Account>();
		accounts.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {						
				accounts.removeAllItems();
				
				for (int i = 0; i < ((Customer)customer.getSelectedItem()).getAccounts().length; i++){
					accounts.addItem(((Customer)customer.getSelectedItem()).getAccounts()[i]);
				}
				
				currentAccount = (Account)accounts.getSelectedItem();
				SSN = user.m_ePrivileges.seeSSN((Customer)customer.getSelectedItem());
				accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + "SSN: " + SSN + "\n" + currentAccount.toString2());
				if (currentAccount instanceof LineOfCredit){
					limit.setText(((LineOfCredit) currentAccount).getLimit()+"");
				}else{
					limit.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				
				for (int i = 0; i < ((Customer)customer.getSelectedItem()).getAccounts().length; i++){
					accounts.addItem(((Customer)customer.getSelectedItem()).getAccounts()[i]);
				}
				
				currentAccount = (Account)accounts.getSelectedItem();
				SSN = user.m_ePrivileges.seeSSN((Customer)customer.getSelectedItem());
				accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + "SSN: " + SSN + "\n" + currentAccount.toString2());
				if (currentAccount instanceof LineOfCredit){
					limit.setText(((LineOfCredit) currentAccount).getLimit()+"");
				}else{
					limit.setText("");
				}
			}
		});
		accounts.setBounds(86, 46, 139, 20);
		contentPane.add(accounts);
		
		
	}
}
