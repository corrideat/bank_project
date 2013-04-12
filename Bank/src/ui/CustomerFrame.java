package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerFrame frame = new CustomerFrame();
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
	public CustomerFrame() {
		setTitle("Customer");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 301, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Account Information");
		label.setBounds(10, 45, 154, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Account:");
		label_1.setBounds(10, 15, 85, 14);
		contentPane.add(label_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(75, 12, 139, 20);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 262, 64);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		textField = new JTextField();
		textField.setText("0.00");
		textField.setColumns(10);
		textField.setBounds(10, 137, 91, 20);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("0.00");
		textField_1.setColumns(10);
		textField_1.setBounds(10, 169, 91, 20);
		contentPane.add(textField_1);
		
		JButton button = new JButton("Withdraw");
		button.setBounds(111, 136, 89, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Deposit");
		button_1.setBounds(111, 168, 89, 23);
		contentPane.add(button_1);
		
		JLabel lblAccountHistory = new JLabel("Account History");
		lblAccountHistory.setBounds(10, 200, 106, 14);
		contentPane.add(lblAccountHistory);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 219, 262, 64);
		contentPane.add(scrollPane_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		scrollPane_1.setViewportView(textPane_1);
		
		JLabel lblTransferFunds = new JLabel("Transfer Funds");
		lblTransferFunds.setBounds(10, 294, 117, 14);
		contentPane.add(lblTransferFunds);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(49, 319, 91, 20);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(49, 350, 91, 20);
		contentPane.add(comboBox_2);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(10, 322, 46, 14);
		contentPane.add(lblFrom);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(10, 353, 23, 14);
		contentPane.add(lblTo);
		
		JLabel lblAmount = new JLabel("Amount: ");
		lblAmount.setBounds(150, 322, 71, 14);
		contentPane.add(lblAmount);
		
		textField_2 = new JTextField();
		textField_2.setText("0.00");
		textField_2.setBounds(204, 319, 79, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.setBounds(164, 349, 108, 23);
		contentPane.add(btnTransfer);
		
		JLabel lblMarkFradulentTransaction = new JLabel("Mark Fradulent Transaction");
		lblMarkFradulentTransaction.setBounds(10, 381, 190, 14);
		contentPane.add(lblMarkFradulentTransaction);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(10, 404, 139, 20);
		contentPane.add(comboBox_3);
		
		JButton btnMark = new JButton("Mark");
		btnMark.setBounds(164, 403, 89, 23);
		contentPane.add(btnMark);
		
		JButton btnNewButton = new JButton();
		btnNewButton.setIcon(new ImageIcon(CustomerFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		btnNewButton.setBounds(236, 11, 36, 23);
		contentPane.add(btnNewButton);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginFrame = new MainFrame();
				loginFrame.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(97, 438, 89, 23);
		contentPane.add(btnLogout);
	}
}
