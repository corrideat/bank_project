package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import user.*;
import javax.swing.JSeparator;


public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3684777489091088347L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	public User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("Banking");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 232, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(78, 11, 132, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 14, 94, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 45, 94, 14);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = new String(textField.getText());
				String pass = new String(passwordField.getPassword());
				
				user = backend.RuntimeAPI.getUser(username);
				
				if (user == null) {
					JOptionPane.showMessageDialog(null, "Login unsuccessful. User not found.", "Login Error", JOptionPane.ERROR_MESSAGE);
				} else if (!user.authenticate(username, pass)) {
					JOptionPane.showMessageDialog(null, "Login unsuccessful. Wrong credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
				} else {
				
					JFrame userFrame = new CustomerFrame();
					
					if (user instanceof Teller){					// Teller
						userFrame = new TellerFrame();
					}else if (user instanceof Customer){			// Customer
						userFrame = new CustomerFrame();
	//				}else if (user instanceof EmployeeCustomer){	// Customer Employee
	//					userFrame = new EmployeeCustomerFrame();
					}else if (user instanceof AccountManager){		// Account Manager
						userFrame = new AccountManagerFrame();
					}else if (user instanceof Accountant){			// Accountant
						userFrame = new AccountantFrame();
					}else if (user instanceof Auditor){				// Auditor
						userFrame = new AuditorFrame();
	//				}else if (user instanceof OperationManager){	// Operation Manager
	//					userFrame = new OperationManagerFrame();
					}
					
					userFrame.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setBounds(68, 73, 89, 23);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(78, 42, 132, 20);
		contentPane.add(passwordField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 106, 226, 2);
		contentPane.add(separator);
		
		JButton btnTestFrames = new JButton("Test Frames");
		btnTestFrames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame testFrame = new TestFrame();
				testFrame.setVisible(true);
			}
		});
		btnTestFrames.setBounds(47, 119, 132, 23);
		contentPane.add(btnTestFrames);
	}
}
