package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import user.AccountManager;
import user.Accountant;
import user.Auditor;
import user.Customer;
import user.Teller;
import user.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeCustomerFrame extends JFrame {

	private JPanel contentPane;
	public static User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeCustomerFrame frame = new EmployeeCustomerFrame();
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
	public EmployeeCustomerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 142, 115);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame userFrame = new CustomerFrame();
				CustomerFrame.user = user;
				userFrame.setVisible(true);
				dispose();
			}
		});
		btnCustomer.setBounds(16, 10, 93, 23);
		contentPane.add(btnCustomer);
		
		JButton button = new JButton("Employee");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame userFrame = new AccountantFrame();
				
				if (user instanceof Teller){					// Teller
					userFrame = new TellerFrame();
					TellerFrame.user = user;
				}else if (user instanceof AccountManager){		// Account Manager
					userFrame = new AccountManagerFrame();
					AccountManagerFrame.user = user;
				}else if (user instanceof Accountant){			// Accountant
					userFrame = new AccountantFrame();
					AccountantFrame.user = user;
				}else if (user instanceof Auditor){				// Auditor
					userFrame = new AuditorFrame();
					AuditorFrame.user = user;
//				}else if (user instanceof OperationManager){	// Operation Manager
//					userFrame = new OperationManagerFrame();
//					OperationManagerFrame.user = user;
				}
				
				userFrame.setVisible(true);
				dispose();
			}
		});
		button.setBounds(16, 43, 93, 23);
		contentPane.add(button);
	}
}
