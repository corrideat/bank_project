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

public class AccountManagerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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
		setBounds(100, 100, 303, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(64, 12, 139, 20);
		contentPane.add(textField);
		
		JLabel label = new JLabel("Customer:");
		label.setBounds(10, 15, 50, 14);
		contentPane.add(label);
		
		JButton button = new JButton("Access");
		button.setBounds(213, 11, 65, 23);
		contentPane.add(button);
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 77, 106, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Account:");
		label_2.setBounds(10, 46, 46, 14);
		contentPane.add(label_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(64, 43, 139, 20);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 96, 262, 64);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
	}

}
