package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class TestFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
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
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 172, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Customer");
		btnNewButton.setBounds(12, 43, 131, 23);
		contentPane.add(btnNewButton);
		
		JButton btnTeller = new JButton("Teller");
		btnTeller.setBounds(12, 76, 131, 23);
		contentPane.add(btnTeller);
		
		JButton btnAccountManager = new JButton("Account Manager");
		btnAccountManager.setBounds(12, 109, 131, 23);
		contentPane.add(btnAccountManager);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(12, 10, 131, 23);
		contentPane.add(btnLogin);
		
		JButton btnAccountant = new JButton("Accountant");
		btnAccountant.setBounds(12, 142, 131, 23);
		contentPane.add(btnAccountant);
		
		JButton btnAuditor = new JButton("Auditor");
		btnAuditor.setBounds(12, 175, 131, 23);
		contentPane.add(btnAuditor);
		
		JButton btnOperationManager = new JButton("Operation Manager");
		btnOperationManager.setBounds(12, 208, 131, 23);
		contentPane.add(btnOperationManager);
	}

}
