package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import user.*;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MailboxFrame extends JFrame {

	private JPanel contentPane;
	public static User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MailboxFrame frame = new MailboxFrame();
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
	public MailboxFrame() {
		setResizable(false);
		setTitle("Mailbox");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 314, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox<Message> message = new JComboBox<Message>();
		message.setBounds(15, 11, 178, 20);
		contentPane.add(message);
		for (int i = 0; i < CustomerFrame.user.m_mbMailbox.getMessages().length;i++){
			message.addItem(CustomerFrame.user.m_mbMailbox.getMessages()[i]);
		}
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(15, 42, 277, 219);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					textPane.setText(message.getSelectedItem().toString());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to retrieve mail.", "Mail Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(203, 10, 89, 23);
		contentPane.add(btnNewButton);
		
		
	
	}
}
