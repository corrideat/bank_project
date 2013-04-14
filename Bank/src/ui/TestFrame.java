package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame extends JFrame {

	private JPanel contentPane;
	public static User user;

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
		setTitle("Test");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 191, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Teller");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new TellerFrame();
				userFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 76, 154, 23);
		contentPane.add(btnNewButton);
		
		JButton btnTeller = new JButton("Customer");
		btnTeller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new CustomerFrame();
				userFrame.setVisible(true);
			}
		});
		btnTeller.setBounds(10, 43, 154, 23);
		contentPane.add(btnTeller);
		
		JButton btnAccountManager = new JButton("Account Manager");
		btnAccountManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new AccountManagerFrame();
				userFrame.setVisible(true);
			}
		});
		btnAccountManager.setBounds(10, 142, 154, 23);
		contentPane.add(btnAccountManager);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
			}
		});
		btnLogin.setBounds(10, 10, 154, 23);
		contentPane.add(btnLogin);
		
		JButton btnAccountant = new JButton("Accountant");
		btnAccountant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new AccountantFrame();
				userFrame.setVisible(true);
			}
		});
		btnAccountant.setBounds(10, 175, 154, 23);
		contentPane.add(btnAccountant);
		
		JButton btnAuditor = new JButton("Auditor");
		btnAuditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new AuditorFrame();
				userFrame.setVisible(true);
			}
		});
		btnAuditor.setBounds(10, 208, 154, 23);
		contentPane.add(btnAuditor);
		
		JButton btnOperationManager = new JButton("Operation Manager");
		btnOperationManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame userFrame = new OperationManagerFrame();
				//userFrame.setVisible(true);
			}
		});
		btnOperationManager.setBounds(10, 241, 154, 23);
		contentPane.add(btnOperationManager);
		
		JButton btnNewButton_1 = new JButton("Employee & Customer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new AccountantFrame();
				userFrame = new EmployeeCustomerFrame();
				//EmployeeCustomerFrame.user = user;
				userFrame.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(10, 109, 154, 23);
		contentPane.add(btnNewButton_1);
	}
}
