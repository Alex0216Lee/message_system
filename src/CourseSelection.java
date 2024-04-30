import java.awt.Color;
import java.awt.Window;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CourseSelection extends JFrame {
	private DBAccesser db;
	private String studentID;
	
	private static JTextField textCourseID;
	private static JTextField textCourse;
	private static JTextField textTeacher;
	private static JTextField textTime;
	

	public CourseSelection(DBAccesser db, String studentID, String userName) {
		this.db = db;
		this.studentID = studentID;

		this.setTitle("Course Selection");
		this.setSize(754, 613);
		ImageIcon loginIcon = new ImageIcon("LoginIcon.png");
		this.setIconImage(loginIcon.getImage());
		
		JTable table = new JTable();
		table.setCellSelectionEnabled(true);
		Object[] columns = {"CourseID ", "Course Name", "Teacher", "Time"};
		DefaultTableModel model = new DefaultTableModel();
		
		this.getContentPane().setBackground(Color.BLACK);
		this.getContentPane().setForeground(Color.WHITE);
		this.setBounds(100, 100, 802, 613);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setVisible(true);
		
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		
		table.setBackground(new Color(128, 128, 255));
		table.setForeground(Color.black);
		table.setSelectionBackground(Color.lightGray);
		table.setGridColor(Color.gray);
		table.setSelectionForeground(Color.white);
		table.setFont(new java.awt.Font("Tahoma",java.awt.Font.PLAIN,17));
		table.setRowHeight(30);
		table.setAutoCreateRowSorter(true);
		table.setFont(new Font("宋体", Font.PLAIN, 12));
		
		
		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.RED);
		pane.setBackground(Color.WHITE);
		pane.setBounds(10, 60, 768, 323);
		this.getContentPane().add(pane);
		
		textCourseID = new JTextField();
		textCourseID.setBounds(103, 393, 209, 35);
		this.getContentPane().add(textCourseID);
		textCourseID.setColumns(10);
		
		textCourse = new JTextField();
		textCourse.setBounds(103, 438, 209, 35);
		this.getContentPane().add(textCourse);
		textCourse.setColumns(10);
		
		textTeacher = new JTextField();
		textTeacher.setBounds(413, 393, 218, 35);
		this.getContentPane().add(textTeacher);
		textTeacher.setColumns(10);
		
		textTime = new JTextField();
		textTime.setText("");
		textTime.setBounds(413, 439, 218, 34);
		this.getContentPane().add(textTime);
		textTime.setColumns(10);
		
		JLabel lbCourseID = new JLabel("CourseID");
		lbCourseID.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lbCourseID.setForeground(new Color(255, 255, 255));
		lbCourseID.setBounds(10, 395, 105, 33);
		this.getContentPane().add(lbCourseID);
		
		JLabel lbCourse = new JLabel("Course");
		lbCourse.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lbCourse.setForeground(new Color(255, 255, 255));
		lbCourse.setBounds(10, 444, 95, 29);
		this.getContentPane().add(lbCourse);
		
		JLabel lbTeacher = new JLabel("Teacher");
		lbTeacher.setForeground(Color.WHITE);
		lbTeacher.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lbTeacher.setBounds(322, 396, 130, 32);
		this.getContentPane().add(lbTeacher);
		
		JLabel lbTime = new JLabel("Time");
		lbTime.setForeground(Color.WHITE);
		lbTime.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lbTime.setBounds(322, 438, 130, 29);
		this.getContentPane().add(lbTime);
		
		JLabel Label_Name = new JLabel("Name:");
		Label_Name.setFont(new Font("Tahoma", Font.BOLD, 18));
		Label_Name.setForeground(new Color(255, 255, 255));
		Label_Name.setBounds(20, 14, 72, 36);
		getContentPane().add(Label_Name);
		
		JLabel name = new JLabel(userName);
		name.setFont(new Font("宋体", Font.PLAIN, 18));
		name.setForeground(new Color(255, 255, 255));
		name.setBounds(103, 14, 105, 36);
		getContentPane().add(name);
		
		JLabel Label_StudentID = new JLabel("StudentID:");
		Label_StudentID.setForeground(Color.WHITE);
		Label_StudentID.setFont(new Font("Tahoma", Font.BOLD, 18));
		Label_StudentID.setBounds(355, 14, 113, 36);
		getContentPane().add(Label_StudentID);
		
		JLabel Label_StudentID2 = new JLabel(studentID);
		Label_StudentID2.setForeground(Color.WHITE);
		Label_StudentID2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		Label_StudentID2.setBounds(488, 26, 105, 15);
		getContentPane().add(Label_StudentID2);
		
		JComboBox<String> comboBox_classSelection = new JComboBox<>();
		comboBox_classSelection.setBounds(10, 483, 338, 35);
		getContentPane().add(comboBox_classSelection);
		String dataAllClass[] = db.classSelection_addAllCourse();
		for(String allClass : dataAllClass) {
			comboBox_classSelection.addItem(allClass);
		}
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon("searchIcon.png"));
		btnSearch.setBounds(641, 393, 137, 137);
		btnSearch.setFocusable(false);
		getContentPane().add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comboBox_classSelection.removeAllItems();
				if(textCourseID.getText().isEmpty() != true) {
					String dataClass[] = db.classSelection_courseID(textCourseID.getText());
					for(String item : dataClass) {
						comboBox_classSelection.addItem(item);
					}
					textCourseID.setText("");
					
				}
				else if(textCourse.getText().isEmpty() != true) {
					String dataClass[] = db.classSelection_course(textCourse.getText());
					for(String item : dataClass) {
						comboBox_classSelection.addItem(item);
					}
					textCourse.setText("");
				}
				else if(textTeacher.getText().isEmpty() != true) {
					String dataClass[] = db.classSelection_teacher(textTeacher.getText());
					for(String item : dataClass) {
						comboBox_classSelection.addItem(item);
					}
					textTeacher.setText("");
				}
				else if(textTime.getText().isEmpty() != true) {
					String dataClass[] = db.classSelection_time(textTime.getText());
					for(String item : dataClass) {
						comboBox_classSelection.addItem(item);
					}
					textTime.setText("");
				}
				
			}
		});
		
		Object[] row = new Object[4];
		ArrayList<String> selectedCourses = new ArrayList<String>();
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 24));
		btnAdd.setBounds(9, 528, 166, 35);
		btnAdd.setFocusable(false);
		getContentPane().add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String course = comboBox_classSelection.getSelectedItem().toString();
				String[] courseParts = course.split(",");
				for (int i = 0 ; i < courseParts.length ; i++) {
		            row[i] = courseParts[i];
		        }
				model.addRow(row);
	            int rowCount = model.getRowCount();
	            selectedCourses.add(row[0].toString());
			}
		});
		
		JButton btnDel = new JButton("DELETE");
		btnDel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		btnDel.setBounds(185, 528, 163, 35);
		btnDel.setFocusable(false);
		getContentPane().add(btnDel);
		
		btnDel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				int rowSeleted = table.getSelectedRow();
				model.removeRow(rowSeleted);	
				selectedCourses.remove(rowSeleted-1);
			}
		});
		
		JButton btnFinish = new JButton("FINISH");
		btnFinish.setFont(new Font("Times New Roman", Font.BOLD, 40));
		btnFinish.setBounds(369, 483, 262, 80);
		getContentPane().add(btnFinish);
		
		btnFinish.addActionListener(e -> {
				
			db.addCourseIntoDatabase(selectedCourses, studentID);
			this.dispose();
			new LoginPage(db);
		});
		
		btnAdd.setFocusable(false);
		btnDel.setFocusable(false);
		btnFinish.setFocusable(false);
		btnSearch.setFocusable(false);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("方正姚体", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(641, 533, 137, 30);
		getContentPane().add(lblNewLabel);
		
	}
}