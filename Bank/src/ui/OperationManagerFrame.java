package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.User;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import account.Account;
import backend.GlobalParameters;
import backend.RuntimeAPI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class OperationManagerFrame extends JFrame {

	private JPanel contentPane;
	public static User user;
	private JTextField globalValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationManagerFrame frame = new OperationManagerFrame();
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
	public OperationManagerFrame() {
		setTitle("Operation Manager");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 306, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIncreaseTimeBy = new JButton("Increase Time By One Month");
		btnIncreaseTimeBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuntimeAPI.shiftTime((long)2.62974E6);
			}
		});
		btnIncreaseTimeBy.setBounds(10, 11, 261, 23);
		contentPane.add(btnIncreaseTimeBy);
		
		globalValue = new JTextField();
		globalValue.setBounds(10, 94, 156, 20);
		contentPane.add(globalValue);
		globalValue.setColumns(10);
		
		final JComboBox<GlobalParameters> globals = new JComboBox<GlobalParameters>();
		globals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				globalValue.setText((((GlobalParameters)globals.getSelectedItem()).get())+"");
			}
		});
		globals.setBounds(10, 63, 261, 20);
		contentPane.add(globals);
		globals.addItem(backend.GlobalParameters.MASTER_RATE_SAVINGS);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_SAVINGS);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD6M);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD1Y);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD2Y);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD3Y);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD4Y);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_CD5Y);
		globals.addItem(backend.GlobalParameters.MASTER_RATE_LOAN);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_LOAN);
		globals.addItem(backend.GlobalParameters.OFFSET_RATE_LOC);
		globals.addItem(backend.GlobalParameters.SAVINGS_FEE);
		globals.addItem(backend.GlobalParameters.SAVINGS_MINIMUM_GRATIS_BALANCE);
		globals.addItem(backend.GlobalParameters.CHECKING_MINIMUM_BALANCE);
		globals.addItem(backend.GlobalParameters.CHECKING_MINIMUM_GRATIS_BALANCE);
		globals.addItem(backend.GlobalParameters.CHECKING_FEE);
		globals.addItem(backend.GlobalParameters.CHECKING_OVERDRAFT_FEE);
		globals.addItem(backend.GlobalParameters.CD_MINIMUM_BALANCE);
		globals.addItem(backend.GlobalParameters.LOC_MAXIMUM_BALANCE);
		globals.addItem(backend.GlobalParameters.LOC_MINIMUM_PAYMENT);
		globals.addItem(backend.GlobalParameters.LOC_MINIMUM_PAYMENT_FRACTION);
		globals.addItem(backend.GlobalParameters.LOC_LATE_PENALTY);
		globals.addItem(backend.GlobalParameters.LOAN_LATE_PENALTY);
		globals.addItem(backend.GlobalParameters.TELLER_INTERACTION_FEE);
		
		
		
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((GlobalParameters)globals.getSelectedItem()).set(Double.parseDouble(globalValue.getText()));
			}
		});
		btnChange.setBounds(182, 93, 89, 23);
		contentPane.add(btnChange);
		
		JButton btnSendStatement = new JButton("Send Statements to Accounts");
		btnSendStatement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.m_ePrivileges.sendStatements();
			}
		});
		btnSendStatement.setBounds(14, 141, 261, 23);
		contentPane.add(btnSendStatement);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 128, 290, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 45, 290, 2);
		contentPane.add(separator_1);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		btnLogout.setBounds(14, 175, 261, 23);
		contentPane.add(btnLogout);
	}
}
