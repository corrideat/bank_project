package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import user.User;

public class CreateAccountFrame extends JFrame {

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
					CreateAccountFrame frame = new CreateAccountFrame();
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
	public CreateAccountFrame() {
		setAlwaysOnTop(true);
		setTitle("Account Creation");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setBounds(10, 11, 87, 14);
		contentPane.add(lblAccountType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(107, 8, 101, 20);
		contentPane.add(comboBox);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(10, 42, 68, 14);
		contentPane.add(lblBalance);
		
		textField = new JTextField();
		textField.setText("0.00");
		textField.setBounds(107, 39, 101, 20);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
