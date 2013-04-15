package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

import backend.Agent;
import backend.GlobalParameters;

import date.DateTime;

import account.Account;
import account.TransactionValidationException;

import user.Customer;
import user.Teller;
import user.User;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TellerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField withdrawalAmount;
	private JTextField depositAmount;
	private JTextField autoAmount;
	public static User user;
	public static Account currentAccount;
	private JTextField description;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TellerFrame frame = new TellerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param user 
	 */
	public TellerFrame() {
		setTitle("Teller");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 238, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(10, 14, 91, 14);
		contentPane.add(lblCustomer);
		
		JLabel lblAccountInformation = new JLabel("Account Information");
		lblAccountInformation.setBounds(10, 70, 156, 14);
		contentPane.add(lblAccountInformation);
		
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setBounds(10, 42, 91, 14);
		contentPane.add(lblAccount);
		
		withdrawalAmount = new JTextField();
		withdrawalAmount.setText("0.00");
		withdrawalAmount.setBounds(10, 162, 91, 20);
		contentPane.add(withdrawalAmount);
		withdrawalAmount.setColumns(10);
		
		depositAmount = new JTextField();
		depositAmount.setText("0.00");
		depositAmount.setColumns(10);
		depositAmount.setBounds(10, 194, 91, 20);
		contentPane.add(depositAmount);
		
		JLabel lblAutomaticTransaction = new JLabel("Automatic Transaction");
		lblAutomaticTransaction.setBounds(10, 225, 156, 14);
		contentPane.add(lblAutomaticTransaction);
		
		autoAmount = new JTextField();
		autoAmount.setText("0.00");
		autoAmount.setColumns(10);
		autoAmount.setBounds(10, 312, 91, 20);
		contentPane.add(autoAmount);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		btnNewButton.setBounds(128, 343, 91, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(105, 378, -113, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 208, 62);
		contentPane.add(scrollPane);
		
		final JTextPane accountPane = new JTextPane();
		scrollPane.setViewportView(accountPane);
		accountPane.setEditable(false);
		
		final JComboBox<User> customer = new JComboBox<User>();
		customer.setBounds(79, 11, 139, 20);
		contentPane.add(customer);
		for (int i =0; i < backend.Core.m_auUsers.values().toArray().length; i++){
			if (backend.Core.m_auUsers.values().toArray()[i] instanceof Customer)	
				customer.addItem((User)backend.Core.m_auUsers.values().toArray()[i]);
		}
		
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Teller agent = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
					agent.m_ePrivileges.withdraw(agent,currentAccount,Double.parseDouble(withdrawalAmount.getText()));
	
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
				} catch (NumberFormatException e) {
					
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
					e.printStackTrace();
				} catch (TransactionValidationException e) {
					
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
					e.printStackTrace();
				}
			}
		});
		btnWithdraw.setBounds(111, 161, 89, 23);
		contentPane.add(btnWithdraw);
		
		
		
		final JComboBox accounts = new JComboBox();
		accounts.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {						
				accounts.removeAllItems();
				
				for (int i = 0; i < ((Customer)customer.getSelectedItem()).getAccounts().length; i++){
					accounts.addItem(((Customer)customer.getSelectedItem()).getAccounts()[i]);
				}
				
				currentAccount = (Account)accounts.getSelectedItem();
				accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
			}
			@Override
			public void focusLost(FocusEvent e) {
				
				for (int i = 0; i < ((Customer)customer.getSelectedItem()).getAccounts().length; i++){
					accounts.addItem(((Customer)customer.getSelectedItem()).getAccounts()[i]);
				}
				
				currentAccount = (Account)accounts.getSelectedItem();
				accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
			}
		});
		accounts.setBounds(79, 39, 139, 20);
		contentPane.add(accounts);
		
		final JComboBox<Account> autoDestination = new JComboBox<Account>();
		autoDestination.setBounds(89, 250, 133, 20);
		contentPane.add(autoDestination);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Teller agent = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
					agent.m_ePrivileges.deposit(agent,currentAccount,Double.parseDouble(withdrawalAmount.getText()));
	
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
				} catch (NumberFormatException e1) {
					
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
					e1.printStackTrace();
				} catch (TransactionValidationException e1) {
					
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
					e1.printStackTrace();
				}
			}
		});
		btnDeposit.setBounds(111, 193, 89, 23);
		contentPane.add(btnDeposit);
		for (int i =0; i < backend.Core.m_aaAccounts.values().toArray().length; i++){
			autoDestination.addItem((Account)backend.Core.m_aaAccounts.values().toArray()[i]);
		}
		
		JLabel lblDestination = new JLabel("Destination:");
		lblDestination.setBounds(10, 253, 97, 14);
		contentPane.add(lblDestination);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					user.m_ePrivileges.setupAutomaticTransaction((Account)accounts.getSelectedItem(), ((Account)autoDestination.getSelectedItem()).getAccountNumber(),description.getText(), Double.parseDouble(autoAmount.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(127, 311, 91, 23);
		contentPane.add(btnAdd);
		
		description = new JTextField();
		description.setBounds(89, 281, 133, 20);
		contentPane.add(description);
		description.setColumns(10);
		
		
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(10, 284, 97, 14);
		contentPane.add(lblDescription);
		
		JButton btnNewButton_1 = new JButton("Service Fee");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double fee = GlobalParameters.TELLER_INTERACTION_FEE.get();
					Teller agent = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
					agent.m_ePrivileges.serviceFee(agent,currentAccount,-fee);
					accountPane.setText(((Customer)customer.getSelectedItem()).toString() + "\n" + currentAccount.toString2());
					
				} catch (TransactionValidationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(10, 343, 108, 23);
		contentPane.add(btnNewButton_1);
	}
}
