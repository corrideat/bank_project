package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

import user.User;
import backend.*;

public class AccountantFrame extends JFrame {

	private JPanel contentPane;
	private JTextField loanCap;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountantFrame frame = new AccountantFrame(null);
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
	public AccountantFrame(final User user) {
		this.user = user;
		setTitle("Accountant");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 303, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setBounds(13, 14, 112, 14);
		contentPane.add(lblAccountType);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(13, 66, 258, 67);
		contentPane.add(scrollPane);
		
		final JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		final JComboBox<String> comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String output = "";
				if (comboBox.getSelectedItem().equals("All")){
					output += "Total: " + backend.Core.TotalBankBalance() + "\n";
				}else if (comboBox.getSelectedItem().equals("Saving")){
					output += "Total: " + backend.Core.SavingsBalanceSum() + "\n";
					output += "Average: " + backend.Core.AllSavingsBalanceAvg() + "\n";
				}else if (comboBox.getSelectedItem().equals("CD")){
					output += "Total: " + backend.Core.CDBalanceSum() + "\n";
					output += "Average: " + backend.Core.AllCDBalanceAvg() + "\n";
				}else if (comboBox.getSelectedItem().equals("Loan")){
					output += "Total: " + backend.Core.LoanBalanceSum() + "\n";
					output += "Average: " + backend.Core.AllLoanBalanceAvg() + "\n";
				}else if (comboBox.getSelectedItem().equals("Checking")){
					output += "Total: " + backend.Core.CheckingBalanceSum() + "\n";
					output += "Average: " + backend.Core.AllCheckingBalanceAvg() + "\n";
				}else if (comboBox.getSelectedItem().equals("LOC")){
					output += "Total Balances: " + backend.Core.LOCBalanceSum() + "\n";
					output += "Total Limits: " + backend.Core.SumLOCLimits() + "\n";
					output += "Average: " + backend.Core.AllLOCBalanceAvg() + "\n";
				}
				
				textPane.setText(output);	
			}
		});
		comboBox.setBounds(132, 11, 139, 20);
		contentPane.add(comboBox);
		comboBox.addItem("All");
		comboBox.addItem("Saving");
		comboBox.addItem("CD");
		comboBox.addItem("Loan");
		comboBox.addItem("Checking");
		comboBox.addItem("LOC");
		
		
		JLabel lblAccountStatistics = new JLabel("Account Statistics");
		lblAccountStatistics.setBounds(13, 41, 106, 14);
		contentPane.add(lblAccountStatistics);
		
		JButton button = new JButton("Logout");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		button.setBounds(102, 193, 89, 23);
		contentPane.add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(-14, 144, 316, 2);
		contentPane.add(separator);
		
		JLabel lblLoanCap = new JLabel("Loan Cap:");
		lblLoanCap.setBounds(24, 157, 74, 14);
		contentPane.add(lblLoanCap);
		
		loanCap = new JTextField();
		loanCap.setBounds(90, 154, 101, 20);
		contentPane.add(loanCap);
		loanCap.setColumns(10);
		loanCap.setText(String.valueOf(user.m_ePrivileges.seeCap()));
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					user.m_ePrivileges.setCap(Double.parseDouble(loanCap.getText()));
					loanCap.setText(String.valueOf(user.m_ePrivileges.seeCap()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSet.setBounds(203, 153, 71, 23);
		contentPane.add(btnSet);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(-14, 182, 316, 2);
		contentPane.add(separator_1);
		
		
	}
}
