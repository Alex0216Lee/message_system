import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;

public class MessagePage extends JFrame {

	private DBAccesser db;
	private String studentID;
	private String department;
	private String name;
	private String subject;
	private ArrayList<String> userCourse_ID;
	private ArrayList<String> userCourse_name;

	public MessagePage(DBAccesser db, String studentID) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setForeground(Color.GRAY);
		this.studentID = studentID;
		var inputField = new JTextField();
		var sendButton = new JButton("Send");
		sendButton.setFont(new Font("微软雅黑", Font.BOLD, 22));
		sendButton.setForeground(Color.pink);
		var outputScrollPane = new JScrollPane();
		ImageIcon loginIcon = new ImageIcon("LoginIcon.png");
		this.setIconImage(loginIcon.getImage());
		
		userCourse_ID = db.getUserCourse(studentID);
		userCourse_name = new ArrayList<String>();
		
		for(String soloCourse : userCourse_ID) {
			userCourse_name.add(db.getCourseNameByCourseID(soloCourse));
		}
		var list = new JComboBox<String>();
		for (String allClass : userCourse_name) {
			list.addItem(allClass);
		}

		var outputArea = new JTextArea();
		list.setBounds(10, 10, 241, 30);
		list.addActionListener(e -> {
			subject = (String) list.getSelectedItem();
			var subjectID = db.getCourseIDByCourseName(subject);
			outputArea.setText(db.showMes(subjectID));
		});

		outputScrollPane.setBounds(10, 50, 369, 329);
		inputField.setBounds(10, 384, 257, 61);
		sendButton.setBounds(277, 385, 102, 60);
		sendButton.setFocusable(false);
		sendButton.setBorderPainted(true);
		sendButton.setOpaque(true);
		sendButton.setBorder(new LineBorder(new Color(128, 128, 128), 5));
		sendButton.addActionListener(e -> {
			String subject = (String) list.getSelectedItem();
			String input = inputField.getText();
			String subjectID = db.getCourseIDByCourseName(subject);
			db.addMes(studentID, input, subjectID);
			outputArea.setText(db.showMes(subjectID));
			inputField.setText("");
		});

		getContentPane().add(outputScrollPane);
		outputScrollPane.setViewportView(outputArea);
		outputArea.setEditable(false);
		getContentPane().add(inputField);
		getContentPane().add(sendButton);
		getContentPane().add(list);

		subject = (String) list.getSelectedItem();
		var subjectID = db.getCourseIDByCourseName(subject);
		outputArea.setText(db.showMes(subjectID));
		
		this.setTitle("Message");
		this.setSize(404, 494);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setVisible(true);
	}
}