package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;

import date.DateTime;

import backend.RuntimeAPI;

import account.Account;
import account.CD;
import account.Transaction;
import account.TransactionValidationException;

import user.Teller;
import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField withdrawalAmount;
	private JTextField transferAmount;
	public static User user;
	public static Account currentAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerFrame frame = new CustomerFrame();
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
	public CustomerFrame() {
		setTitle("Customer");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 301, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Account Information");
		label.setBounds(10, 45, 154, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Account:");
		label_1.setBounds(10, 15, 85, 14);
		contentPane.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 262, 64);
		contentPane.add(scrollPane);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 187, 262, 64);
		contentPane.add(scrollPane_1);
		
		final JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		scrollPane_1.setViewportView(textPane_1);
		
		JComboBox<Long> comboBox = new JComboBox<Long>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<Account> cb = (JComboBox)e.getSource();
				currentAccount = RuntimeAPI.getAccount((long)cb.getSelectedItem());
				textPane.setText(currentAccount.toString2());
				String history = "";
				for (int i =0; i < currentAccount.getTransactions().length; i++){
					history += currentAccount.getTransactions()[i].toString() + "\n";
				}
				textPane_1.setText(history);
			}
		});
		comboBox.setBounds(75, 12, 139, 20);
		contentPane.add(comboBox);
		for (int i = 0; i < user.getAccounts().length; i++){
			comboBox.addItem(user.getAccounts()[i].getAccountNumber());
		}
		
		final JComboBox<Transaction> comboBox_3 = new JComboBox<Transaction>();
		comboBox_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
					comboBox_3.addItem(currentAccount.getTransactions()[i]);
				}
			}
		});
		comboBox_3.setBounds(10, 372, 262, 20);
		contentPane.add(comboBox_3);
		for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
			comboBox_3.addItem(currentAccount.getTransactions()[i]);
		}
	
		JButton btnMark = new JButton("Mark");
		btnMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Transaction)comboBox_3.getSelectedItem()).flagAsFraudulent();
			}
		});
		btnMark.setBounds(39, 403, 89, 23);
		contentPane.add(btnMark);
		
		withdrawalAmount = new JTextField();
		withdrawalAmount.setText("0.00");
		withdrawalAmount.setColumns(10);
		withdrawalAmount.setBounds(10, 137, 91, 20);
		contentPane.add(withdrawalAmount);
		
		JButton button = new JButton("Withdraw");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Teller agent = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
					agent.m_ePrivileges.withdraw(agent,currentAccount,Double.parseDouble(withdrawalAmount.getText()));
					
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful. Please make sure your input is correct.", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
					
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
					
				} catch (TransactionValidationException e1) {
					JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful. The transacation was unable to be completed.", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
					
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
					
				}
				
			}
		});
		button.setBounds(111, 136, 89, 23);
		contentPane.add(button);
		
		JLabel lblAccountHistory = new JLabel("Account History");
		lblAccountHistory.setBounds(10, 168, 106, 14);
		contentPane.add(lblAccountHistory);
		
		JLabel lblTransferFunds = new JLabel("Transfer Funds");
		lblTransferFunds.setBounds(10, 262, 117, 14);
		contentPane.add(lblTransferFunds);
		
		final JComboBox<Account> comboBox_2 = new JComboBox<Account>();
		comboBox_2.setBounds(49, 287, 223, 20);
		contentPane.add(comboBox_2);
		for (int i =0; i < backend.Core.m_aaAccounts.values().toArray().length; i++){
			comboBox_2.addItem((Account)backend.Core.m_aaAccounts.values().toArray()[i]);
		}
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(10, 290, 23, 14);
		contentPane.add(lblTo);
		
		JLabel lblAmount = new JLabel("Amount: ");
		lblAmount.setBounds(10, 321, 71, 14);
		contentPane.add(lblAmount);
		
		transferAmount = new JTextField();
		transferAmount.setText("0.00");
		transferAmount.setBounds(64, 318, 79, 20);
		contentPane.add(transferAmount);
		transferAmount.setColumns(10);
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Teller agent = new Teller("Alice", "Colby", new DateTime(1963, 5, 10), 256880460, "teller", "password");
				
				try {
					agent.m_ePrivileges.transfer(agent,currentAccount,((Account)comboBox_2.getSelectedItem()).getAccountNumber(),Double.parseDouble(transferAmount.getText()));
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Transfer unsuccessful. Please make sure your input is correct.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
					
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
					e1.printStackTrace();
				} catch (TransactionValidationException e1) {
					JOptionPane.showMessageDialog(null, "Transfer unsuccessful. Please make sure this transfer is valid.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
					textPane.setText(currentAccount.toString2());
					String history = "";
					for (int i =0; i < currentAccount.getTransactions().length; i++){
						history += currentAccount.getTransactions()[i].toString() + "\n";
					}
					textPane_1.setText(history);
					for (int i = comboBox_3.getItemCount(); i < currentAccount.getTransactions().length; i++){
						comboBox_3.addItem(currentAccount.getTransactions()[i]);
					}
					e1.printStackTrace();
				}
				
			}
		});
		btnTransfer.setBounds(164, 317, 108, 23);
		contentPane.add(btnTransfer);
		
		JLabel lblMarkFradulentTransaction = new JLabel("Mark Fradulent Transaction");
		lblMarkFradulentTransaction.setBounds(10, 349, 190, 14);
		contentPane.add(lblMarkFradulentTransaction);
		
		
		
		JButton btnNewButton = new JButton();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailboxFrame mail = new MailboxFrame();
				MailboxFrame.user = user;
				mail.setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon(CustomerFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		btnNewButton.setBounds(236, 11, 36, 23);
		contentPane.add(btnNewButton);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		btnLogout.setBounds(167, 403, 89, 23);
		contentPane.add(btnLogout);
	}
}
