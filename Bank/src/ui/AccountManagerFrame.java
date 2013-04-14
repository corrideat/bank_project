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

import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountManagerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public static User user;

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
		
		textField = new JTextField();
		textField.setBounds(86, 12, 139, 20);
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel lblCustomer = new JLabel("Customer #:");
		lblCustomer.setBounds(10, 15, 85, 14);
		contentPane.add(lblCustomer);
		
		JButton button = new JButton("Access");
		button.setBounds(235, 11, 85, 23);
		contentPane.add(button);
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 77, 152, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Account:");
		label_2.setBounds(10, 49, 66, 14);
		contentPane.add(label_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(86, 46, 139, 20);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("New");
		btnNewButton.setBounds(235, 45, 85, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 203, 352, 2);
		contentPane.add(separator);
		
		JButton btnNewButton_1 = new JButton("New Customer");
		btnNewButton_1.setBounds(10, 218, 131, 23);
		contentPane.add(btnNewButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(241, 219, 79, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblLoanLimit = new JLabel("Loan Cap:");
		lblLoanLimit.setBounds(163, 222, 56, 14);
		contentPane.add(lblLoanLimit);
		
		textField_2 = new JTextField();
		textField_2.setBounds(108, 173, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnChange = new JButton("Change");
		btnChange.setBounds(214, 172, 89, 23);
		contentPane.add(btnChange);
		
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
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
	}
}
