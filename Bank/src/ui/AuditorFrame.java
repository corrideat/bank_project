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
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import user.User;

import java.awt.Color;

public class AuditorFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public static User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuditorFrame frame = new AuditorFrame();
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
	public AuditorFrame() {
		setTitle("Auditor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 297, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Account:");
		label.setBounds(10, 41, 85, 14);
		contentPane.add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(91, 38, 139, 20);
		contentPane.add(comboBox);
		
		JLabel label_1 = new JLabel("Account Information");
		label_1.setBounds(10, 74, 154, 14);
		contentPane.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 94, 260, 100);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		
		JLabel label_2 = new JLabel("Account History");
		label_2.setBounds(10, 205, 106, 14);
		contentPane.add(label_2);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(10, 14, 85, 14);
		contentPane.add(lblCustomer);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(91, 11, 139, 20);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(10, 336, 154, 20);
		contentPane.add(comboBox_2);
		
		JButton btnReverse = new JButton("Reverse");
		btnReverse.setBounds(181, 335, 89, 23);
		contentPane.add(btnReverse);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(96, 367, 89, 23);
		contentPane.add(btnLogout);
		
		table = new JTable();
		table.setBackground(Color.GRAY);
		table.setBounds(10, 318, 260, -87);
		contentPane.add(table);
	}
}
