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

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import backend.InsufficientCreditAvailableException;

import account.Account;
import account.AccountParameters;
import account.AccountType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EnumMap;

public class CreateAccountFrame extends JFrame {

	private JPanel contentPane;
	private JTextField principal;
	public static User user;
	private JTextField offset;
	private JTextField installments;
	private JTextField limit;
	private JTextField length;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setBounds(10, 11, 87, 14);
		contentPane.add(lblAccountType);
		
		final JComboBox accountTypeBox = new JComboBox();
		accountTypeBox.setBounds(141, 8, 101, 20);
		contentPane.add(accountTypeBox);
		
		JLabel lblPrincipal = new JLabel("Principal:");
		lblPrincipal.setBounds(10, 42, 121, 14);
		contentPane.add(lblPrincipal);
		
		principal = new JTextField();
		principal.setText("0.00");
		principal.setBounds(141, 39, 101, 20);
		contentPane.add(principal);
		principal.setColumns(10);
		
		offset = new JTextField();
		offset.setBounds(141, 70, 101, 20);
		contentPane.add(offset);
		offset.setColumns(10);
		
		installments = new JTextField();
		installments.setBounds(141, 101, 101, 20);
		contentPane.add(installments);
		installments.setColumns(10);
		
		JLabel lblOffset = new JLabel("Interest Rate Offset:");
		lblOffset.setBounds(10, 73, 121, 14);
		contentPane.add(lblOffset);
		
		JLabel lblInstallments = new JLabel("Installments:");
		lblInstallments.setBounds(10, 104, 101, 14);
		contentPane.add(lblInstallments);
		
		limit = new JTextField();
		limit.setBounds(141, 132, 101, 20);
		contentPane.add(limit);
		limit.setColumns(10);
		
		final JComboBox lengthBox = new JComboBox();
		lengthBox.setBounds(141, 163, 101, 20);
		contentPane.add(lengthBox);
		
		JLabel lblLimit = new JLabel("Limit:");
		lblLimit.setBounds(10, 135, 72, 14);
		contentPane.add(lblLimit);
		
		JLabel lblLength = new JLabel("Length:");
		lblLength.setBounds(10, 166, 87, 14);
		contentPane.add(lblLength);
		
		JEditorPane dtrpnOnlyTheField = new JEditorPane();
		dtrpnOnlyTheField.setText("Only the fields pertaining to your account type will be used.");
		dtrpnOnlyTheField.setBounds(261, 8, 142, 54);
		contentPane.add(dtrpnOnlyTheField);
		
		JLabel lblloanloc = new JLabel("(Loan/LoC)");
		lblloanloc.setBounds(252, 73, 94, 14);
		contentPane.add(lblloanloc);
		
		JLabel lblloan = new JLabel("(Loan)");
		lblloan.setBounds(252, 104, 79, 14);
		contentPane.add(lblloan);
		
		JLabel lblcd = new JLabel("(CD)");
		lblcd.setBounds(252, 135, 46, 14);
		contentPane.add(lblcd);
		
		JLabel label = new JLabel("(CD)");
		label.setBounds(252, 166, 46, 14);
		contentPane.add(label);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// I'm assuming each box gives an object of the correct type. 
				EnumMap<AccountParameters, Object> params = new EnumMap<AccountParameters, Object>(AccountParameters.class);
				params.put(AccountParameters.INSTALLMENTS, Double.parseDouble(installments.getText()));
				params.put(AccountParameters.OFFSET, Double.parseDouble(offset.getText()));
				params.put(AccountParameters.PRINCIPAL, Double.parseDouble(principal.getText()));
				params.put(AccountParameters.CD_TYPE, (account.CD.CD_type)lengthBox.getSelectedItem());
				
				try {
					user.m_ePrivileges.createAccount(user, (AccountType)accountTypeBox.getSelectedItem(), params);
				} catch (InsufficientCreditAvailableException e1) {
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure their is sufficient creedit.", "Account Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1){
					JOptionPane.showMessageDialog(null, "Creation unsuccessful. Please make sure your inputs are correct.", "Account Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCreateAccount.setBounds(64, 194, 135, 23);
		contentPane.add(btnCreateAccount);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(263, 194, 89, 23);
		contentPane.add(btnCancel);
	}
}
