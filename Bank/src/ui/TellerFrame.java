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

import account.Account;

import user.Customer;
import user.User;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TellerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public static User user;
	public static Account currentAccount;

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
	 */
	public TellerFrame() {
		setTitle("Teller");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 248, 412);
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
		
		textField_1 = new JTextField();
		textField_1.setText("0.00");
		textField_1.setBounds(10, 162, 91, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("0.00");
		textField_2.setColumns(10);
		textField_2.setBounds(10, 194, 91, 20);
		contentPane.add(textField_2);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWithdraw.setBounds(111, 161, 89, 23);
		contentPane.add(btnWithdraw);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(111, 193, 89, 23);
		contentPane.add(btnDeposit);
		
		JRadioButton rdbtnWavieServiceCharge = new JRadioButton("Waive Service Charge?");
		rdbtnWavieServiceCharge.setBounds(10, 317, 186, 23);
		contentPane.add(rdbtnWavieServiceCharge);
		
		JLabel lblAutomaticTransaction = new JLabel("Automatic Transaction");
		lblAutomaticTransaction.setBounds(10, 225, 156, 14);
		contentPane.add(lblAutomaticTransaction);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(14, 250, 91, 20);
		comboBox_1.addItem("Deposit");
		comboBox_1.addItem("Withdraw");
		contentPane.add(comboBox_1);
		
		JComboBox autoTime = new JComboBox();
		autoTime.setBounds(119, 250, 107, 20);
		contentPane.add(autoTime);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(144, 281, 56, 23);
		contentPane.add(btnAdd);
		
		textField_3 = new JTextField();
		textField_3.setText("0.00");
		textField_3.setColumns(10);
		textField_3.setBounds(14, 281, 91, 20);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		btnNewButton.setBounds(71, 347, 89, 23);
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
		
		final JComboBox accounts = new JComboBox();
		accounts.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				accounts.removeAllItems();
				
				for (int i = 0; i < ((Customer)customer.getSelectedItem()).getAccounts().length; i++){
					accounts.addItem(((Customer)customer.getSelectedItem()).getAccounts()[i]);
				}
				
				currentAccount = (Account)accounts.getSelectedItem();
				accountPane.setText(user.toString() + "\n" + currentAccount.toString2());
			}
		});
		accounts.setBounds(79, 39, 139, 20);
		contentPane.add(accounts);
	}
}
