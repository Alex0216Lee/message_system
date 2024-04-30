import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class EnrollPage extends JFrame {

	DBAccesser db;

	public EnrollPage(DBAccesser db) {
		this.db = db;
		
		var studentIDLabel = new JLabel("StudentID:");
		studentIDLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		var studentIDField = new JTextField(10);
		var passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		var passwordField = new JPasswordField(10);
		var departmentLabel = new JLabel("Department:");
		departmentLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		var departmentField = new JTextField(10);
		var nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		var nameField = new JTextField(10);
		var enrollBtn = new JButton("Sign up");
		enrollBtn.setBackground(SystemColor.inactiveCaption);
		enrollBtn.setForeground(Color.PINK);
		enrollBtn.setFont(new Font("Times New Roman", Font.BOLD, 24));
		enrollBtn.setOpaque(true);
		enrollBtn.setBorder(new LineBorder(Color.PINK, 3));
		enrollBtn.setFocusable(false);

		studentIDLabel.setBounds(349, 24, 109, 25);
		studentIDField.setBounds(349, 59, 250, 33);

		passwordLabel.setBounds(349, 102, 109, 25);
		passwordField.setBounds(349, 137, 250, 33);

		departmentLabel.setBounds(349, 180, 125, 25);
		departmentField.setBounds(349, 215, 250, 33);

		nameLabel.setBounds(349, 260, 75, 25);
		nameField.setBounds(349, 295, 250, 33);

		enrollBtn.setBounds(349, 356, 250, 53);
		enrollBtn.addActionListener(e -> {
			String studentID = studentIDField.getText();
			String password = String.valueOf(passwordField.getPassword());
			String department = departmentField.getText();
			String name = nameField.getText();

			db.enroll(studentID, password, department, name);
			this.dispose();

			new CourseSelection(db, studentID, name);

		});

		getContentPane().add(studentIDLabel);
		getContentPane().add(studentIDField);
		getContentPane().add(passwordLabel);
		getContentPane().add(passwordField);
		getContentPane().add(departmentLabel);
		getContentPane().add(departmentField);
		getContentPane().add(nameLabel);
		getContentPane().add(nameField);
		getContentPane().add(enrollBtn);

		this.setTitle("Enroll");
		this.setSize(661, 465);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 339, 428);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel bg = new JLabel("New label");
		bg.setIcon(new ImageIcon("bg.jpg"));
		bg.setBounds(0, -36, 365, 338);
		panel.add(bg);
		
		JLabel lblNewLabel = new JLabel("KEEP GOING");
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(111, 312, 118, 20);
		panel.add(lblNewLabel);
		
		JLabel lblComeToJoin = new JLabel("...Come to join us...");
		lblComeToJoin.setForeground(Color.WHITE);
		lblComeToJoin.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblComeToJoin.setBounds(88, 363, 166, 20);
		panel.add(lblComeToJoin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(349, 328, 250, 2);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(349, 248, 250, 2);
		getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(349, 170, 250, 2);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(349, 92, 250, 2);
		getContentPane().add(separator_3);
		this.setVisible(true);
	}
}