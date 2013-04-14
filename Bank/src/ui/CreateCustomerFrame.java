package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import backend.RuntimeAPI;

import date.DateTime;

import user.Customer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateCustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JButton btnCreate;
	private JButton btnCancel;
	private JLabel label;
	private JTextField textField_6;
	private JLabel label_1;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateCustomerFrame frame = new CreateCustomerFrame();
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
	public CreateCustomerFrame() {
		setTitle("Create Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 238, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(107, 10, 105, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 13, 105, 14);
		contentPane.add(lblFirstName);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(107, 40, 105, 20);
		contentPane.add(textField_1);
		
		JLabel lblLastname = new JLabel("Last Name:");
		lblLastname.setBounds(10, 43, 105, 14);
		contentPane.add(lblLastname);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(107, 70, 23, 20);
		contentPane.add(textField_2);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setBounds(10, 73, 105, 14);
		contentPane.add(lblBirthday);
		
		JLabel lblSsn = new JLabel("SSN:");
		lblSsn.setBounds(10, 103, 105, 14);
		contentPane.add(lblSsn);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(107, 100, 105, 20);
		contentPane.add(textField_3);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 133, 105, 14);
		contentPane.add(lblUsername);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(107, 130, 105, 20);
		contentPane.add(textField_4);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 163, 105, 14);
		contentPane.add(lblPassword);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(107, 160, 105, 20);
		contentPane.add(textField_5);
		
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer newC = new Customer(textField.getText(), textField_1.getText(), new DateTime(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_6.getText()), Integer.parseInt(textField_7.getText())), Integer.parseInt(textField_3.getText()), textField_4.getText(), textField_5.getText());
				if(RuntimeAPI.registerUser(textField_4.getText(), newC)){
					CreateAccountFrame accountFrame = new CreateAccountFrame();
					CreateAccountFrame.user = newC;
					accountFrame.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "Unable to create account. Username is taken or not all information is correct.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCreate.setBounds(14, 196, 89, 23);
		contentPane.add(btnCreate);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(117, 196, 89, 23);
		contentPane.add(btnCancel);
		
		label = new JLabel("/");
		label.setBounds(138, 73, 11, 14);
		contentPane.add(label);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(151, 71, 23, 20);
		contentPane.add(textField_6);
		
		label_1 = new JLabel("/");
		label_1.setBounds(179, 73, 11, 14);
		contentPane.add(label_1);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(189, 71, 23, 20);
		contentPane.add(textField_7);
	}
}
