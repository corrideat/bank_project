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

public class TellerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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
		setBounds(100, 100, 248, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(84, 11, 139, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(15, 14, 91, 14);
		contentPane.add(lblCustomer);
		
		JButton btnAccess = new JButton("Access");
		btnAccess.setBounds(108, 39, 91, 23);
		contentPane.add(btnAccess);
		
		JLabel lblAccountInformation = new JLabel("Account Information");
		lblAccountInformation.setBounds(15, 101, 156, 14);
		contentPane.add(lblAccountInformation);
		
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setBounds(15, 73, 91, 14);
		contentPane.add(lblAccount);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(84, 70, 139, 20);
		contentPane.add(comboBox);
		
		textField_1 = new JTextField();
		textField_1.setText("0.00");
		textField_1.setBounds(15, 193, 91, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("0.00");
		textField_2.setColumns(10);
		textField_2.setBounds(15, 225, 91, 20);
		contentPane.add(textField_2);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWithdraw.setBounds(116, 192, 89, 23);
		contentPane.add(btnWithdraw);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(116, 224, 89, 23);
		contentPane.add(btnDeposit);
		
		JRadioButton rdbtnWavieServiceCharge = new JRadioButton("Waive Service Charge?");
		rdbtnWavieServiceCharge.setBounds(15, 348, 186, 23);
		contentPane.add(rdbtnWavieServiceCharge);
		
		JLabel lblAutomaticTransaction = new JLabel("Automatic Transaction");
		lblAutomaticTransaction.setBounds(15, 256, 156, 14);
		contentPane.add(lblAutomaticTransaction);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(15, 281, 91, 20);
		comboBox_1.addItem("Deposit");
		comboBox_1.addItem("Withdraw");
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(116, 281, 107, 20);
		contentPane.add(comboBox_2);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(141, 312, 56, 23);
		contentPane.add(btnAdd);
		
		textField_3 = new JTextField();
		textField_3.setText("0.00");
		textField_3.setColumns(10);
		textField_3.setBounds(15, 312, 91, 20);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(76, 378, 89, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(105, 378, -113, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 120, 208, 62);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
	}
}
