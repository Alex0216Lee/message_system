import java.sql.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

	private DBAccesser db;

	public LoginPage(DBAccesser db) {
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		this.db = db;

		var studentIDLabel = new JLabel("USER:");
		studentIDLabel.setHorizontalAlignment(SwingConstants.LEFT);
		studentIDLabel.setForeground(Color.BLACK);
		studentIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		var studentIDField = new JTextField(10);
		var passwordLabel = new JLabel("PASSWORD:");
		passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		var passwordField = new JPasswordField(10);
		passwordField.setForeground(Color.LIGHT_GRAY);
		passwordField.setBackground(Color.WHITE);
		passwordField.setToolTipText("Input your password!");
		var loginBtn = new JButton("LOGIN");
		loginBtn.setFont(new Font("Times New Roman", Font.BOLD, 24));
		loginBtn.setForeground(Color.PINK);
		var enrollBtn = new JButton("ENROLL");
		enrollBtn.setFont(new Font("Times New Roman", Font.BOLD, 24));
		enrollBtn.setOpaque(true);
		enrollBtn.setForeground(Color.PINK);
		enrollBtn.setBorder(new LineBorder(new Color(128, 128, 128), 3));
		enrollBtn.setFocusable(false);

		studentIDLabel.setBounds(45, 164, 75, 25);
		passwordLabel.setBounds(45, 254, 140, 25);
		studentIDField.setBounds(45, 199, 309, 45);
		passwordField.setBounds(45, 289, 309, 45);
		loginBtn.setBounds(214, 366, 140, 45);
		loginBtn.setOpaque(true);
		loginBtn.setBorder(new LineBorder(new Color(128, 128, 128), 3));
		loginBtn.setFocusable(false);
		loginBtn.addActionListener(e -> {
			String inputStudentID = studentIDField.getText();
			String inputPassword = String.valueOf(passwordField.getPassword());
			boolean success = db.check(inputStudentID, inputPassword);
			if (!success) {
				JOptionPane.showMessageDialog(this, "Wrong ID or password", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				this.dispose();
				new MessagePage(db, inputStudentID);
			}
		});

		enrollBtn.setBounds(45, 366, 140, 45);
		enrollBtn.setFocusable(false);
		enrollBtn.addActionListener(e -> {
			this.dispose();
			new EnrollPage(db);
		});

		getContentPane().add(studentIDLabel);
		getContentPane().add(studentIDField);
		getContentPane().add(passwordLabel);
		getContentPane().add(passwordField);
		getContentPane().add(loginBtn);
		getContentPane().add(enrollBtn);

		this.setTitle("Login");
		this.setSize(408, 508);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon loginIcon = new ImageIcon("LoginIcon.png");
		this.setIconImage(loginIcon.getImage());
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 50));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(109, 40, 200, 109);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(80, 10, 261, 144);
		getContentPane().add(lblNewLabel_1);
		this.setVisible(true);
	}
}