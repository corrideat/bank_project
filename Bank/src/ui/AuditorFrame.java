package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import account.Account;
import account.Transaction;

import user.Customer;
import user.User;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AuditorFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public static User user;
	public static Account currentAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuditorFrame frame = new AuditorFrame();
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
	public AuditorFrame() {
		setTitle("Auditor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 297, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Account:");
		label.setBounds(10, 41, 85, 14);
		contentPane.add(label);	
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 74, 154, 14);
		contentPane.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 94, 260, 100);
		contentPane.add(scrollPane);
		
		final JTextPane accountPane = new JTextPane();
		scrollPane.setViewportView(accountPane);
		accountPane.setEditable(false);
		
		JLabel label_2 = new JLabel("Account History");
		label_2.setBounds(10, 205, 106, 14);
		contentPane.add(label_2);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(10, 14, 85, 14);
		contentPane.add(lblCustomer);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(157, 364, 89, 23);
		contentPane.add(btnLogout);
		
		table = new JTable();
		table.setBackground(Color.GRAY);
		table.setBounds(10, 318, 260, -87);
		contentPane.add(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 230, 260, 95);
		contentPane.add(scrollPane_1);
		
		final JTextPane historyPane = new JTextPane();
		historyPane.setEditable(false);
		scrollPane_1.setViewportView(historyPane);
		
		final JComboBox<User> customer = new JComboBox<User>();
		customer.setBounds(91, 11, 179, 20);
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
				accountPane.setText(user.toString() + "\n" + currentAccount.toString2());
				String history = "";
				for (int i =0; i < currentAccount.getTransactions().length; i++){
					if (!currentAccount.getTransactions()[i].isFlagged()){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}else{
						history += "FLAGGED - " + currentAccount.getTransactions()[i].toString() + "\n";
					}
				}
				historyPane.setText(history);
			}
			@Override
			public void focusLost(FocusEvent e) {
				
				currentAccount = (Account)accounts.getSelectedItem();
				accountPane.setText(user.toString() + "\n" + currentAccount.toString2());
				String history = "";
				for (int i =0; i < currentAccount.getTransactions().length; i++){
					if (!currentAccount.getTransactions()[i].isFlagged()){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}else{
						history += "FLAGGED - " + currentAccount.getTransactions()[i].toString() + "\n";
					}
				}
				historyPane.setText(history);
			}
		});
		accounts.setBounds(91, 38, 179, 20);
		contentPane.add(accounts);
		
		final JComboBox<Transaction> transactions = new JComboBox<Transaction>();
		transactions.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				transactions.removeAllItems();
				for (int i = 0; i < currentAccount.getTransactions().length; i++){
					transactions.addItem(currentAccount.getTransactions()[i]);
				}
			}
		});
		transactions.setBounds(10, 336, 260, 20);
		contentPane.add(transactions);
		if (currentAccount != null){
			for (int i = transactions.getItemCount(); i < currentAccount.getTransactions().length; i++){
				transactions.addItem(currentAccount.getTransactions()[i]);
			}
		}
		
		
		JButton btnReverse = new JButton("Reverse");
		btnReverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					user.m_ePrivileges.overturnTransaction((Transaction)transactions.getSelectedItem());
					
					currentAccount = (Account)accounts.getSelectedItem();
					accountPane.setText(user.toString() + "\n" + currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						if (!currentAccount.getTransactions()[i].isFlagged()){
							history += currentAccount.getTransactions()[i].toString() + "\n";
						}else{
							history += "FLAGGED - " + currentAccount.getTransactions()[i].toString() + "\n";
						}
					}
					historyPane.setText(history);
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Reverse unsuccessful. Please make sure your input is correct.", "Reversal Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnReverse.setBounds(34, 364, 89, 23);
		contentPane.add(btnReverse);
	}
}
