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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountManagerFrame extends JFrame {

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 294, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(64, 13, 139, 20);
		contentPane.add(textField);
		
		JLabel label = new JLabel("Customer:");
		label.setBounds(10, 16, 50, 14);
		contentPane.add(label);
		
		JButton button = new JButton("Access");
		button.setBounds(213, 12, 65, 23);
		contentPane.add(button);
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 77, 106, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Account:");
		label_2.setBounds(10, 50, 46, 14);
		contentPane.add(label_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(64, 47, 139, 20);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 96, 262, 64);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("New");
		btnNewButton.setBounds(213, 46, 65, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 203, 297, 2);
		contentPane.add(separator);
		
		JButton btnNewButton_1 = new JButton("New Customer");
		btnNewButton_1.setBounds(10, 216, 106, 23);
		contentPane.add(btnNewButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(192, 217, 79, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblLoanLimit = new JLabel("Loan Cap:");
		lblLoanLimit.setBounds(131, 220, 56, 14);
		contentPane.add(lblLoanLimit);
		
		textField_2 = new JTextField();
		textField_2.setBounds(76, 172, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnChange = new JButton("Change");
		btnChange.setBounds(172, 171, 89, 23);
		contentPane.add(btnChange);
		
		JLabel lblCreditLimit = new JLabel("Credit Limit:");
		lblCreditLimit.setBounds(10, 175, 65, 14);
		contentPane.add(lblCreditLimit);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 255, 297, 2);
		contentPane.add(separator_1);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(99, 268, 89, 23);
		contentPane.add(btnLogout);
	}
}
