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

public class TellerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 304, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(69, 11, 139, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(15, 14, 50, 14);
		contentPane.add(lblCustomer);
		
		JButton btnAccess = new JButton("Access");
		btnAccess.setBounds(218, 10, 65, 23);
		contentPane.add(btnAccess);
		
		JLabel lblAccountInformation = new JLabel("Account Information");
		lblAccountInformation.setBounds(15, 76, 106, 14);
		contentPane.add(lblAccountInformation);
		
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setBounds(15, 45, 46, 14);
		contentPane.add(lblAccount);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(69, 42, 139, 20);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 95, 262, 64);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		textField_1 = new JTextField();
		textField_1.setText("0.00");
		textField_1.setBounds(15, 168, 91, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("0.00");
		textField_2.setColumns(10);
		textField_2.setBounds(15, 200, 91, 20);
		contentPane.add(textField_2);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWithdraw.setBounds(116, 167, 89, 23);
		contentPane.add(btnWithdraw);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(116, 199, 89, 23);
		contentPane.add(btnDeposit);
		
		JRadioButton rdbtnWavieServiceCharge = new JRadioButton("Waive Service Charge?");
		rdbtnWavieServiceCharge.setBounds(15, 283, 139, 23);
		contentPane.add(rdbtnWavieServiceCharge);
		
		JLabel lblAutomaticTransaction = new JLabel("Automatic Transaction");
		lblAutomaticTransaction.setBounds(15, 231, 116, 14);
		contentPane.add(lblAutomaticTransaction);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(15, 256, 91, 20);
		comboBox_1.addItem("Deposit");
		comboBox_1.addItem("Withdraw");
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(116, 256, 101, 20);
		contentPane.add(comboBox_2);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(227, 255, 56, 23);
		contentPane.add(btnAdd);
	}
}
