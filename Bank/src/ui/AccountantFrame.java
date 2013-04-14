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

public class AccountantFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public static User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountantFrame frame = new AccountantFrame();
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
	public AccountantFrame() {
		setTitle("Accountant");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 303, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setBounds(13, 14, 112, 14);
		contentPane.add(lblAccountType);
		
		JComboBox<String> comboBox = new JComboBox();
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(13, 61, 260, 154);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JButton button = new JButton("Logout");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				MainFrame.user = user;
				dispose();
			}
		});
		button.setBounds(99, 275, 89, 23);
		contentPane.add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(-17, 226, 316, 2);
		contentPane.add(separator);
		
		JLabel lblLoanCap = new JLabel("Loan Cap:");
		lblLoanCap.setBounds(21, 239, 74, 14);
		contentPane.add(lblLoanCap);
		
		textField = new JTextField();
		textField.setBounds(87, 236, 101, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSet = new JButton("Set");
		btnSet.setBounds(200, 235, 71, 23);
		contentPane.add(btnSet);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(-17, 264, 316, 2);
		contentPane.add(separator_1);
	}
}
