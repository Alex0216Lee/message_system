import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;

public class DBAccesser {

	private String url;
	private String username;
	private String password;
	private String department;
	private String name;
	private String[] dataCourse = {};

	public void setInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public boolean check(String studentID, String password) {
		try (var conn = DriverManager.getConnection(this.url, username, this.password)) {
			var stat = conn.createStatement();
			var query = "SELECT `studentID`, password FROM `user`";
			var result = stat.executeQuery(query);
			HashMap<String, String> users = new HashMap<>();
			while (result.next()) {
				users.put(result.getString(1), result.getString(2));
			}

			if (users.containsKey(studentID)) {
				if (users.get(studentID).equals(password))
					return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void addMes(String studentID, String input, String subject) {
		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT `department`, `name` FROM user where studentID = %s", studentID);
			var result = stat.executeQuery(query);
			while (result.next()) {
				department = result.getString(1);
				name = result.getString(2);
			}
			var dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String date = dateFormat.format(new Date().from(Instant.now()));

			query = String.format("INSERT INTO `messages` VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", studentID,
					department, name, subject, input, date);
			stat.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String showMes(String subject) {
		String output = "";
		
		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `messages` where `subject` = '%s'", subject);
			var result = stat.executeQuery(query);

			while (result.next()) {
				output += String.format(" %10s\t%10s\t%10s\t%10s%n%n %s%n", result.getString(1), result.getString(2),
						result.getString(3), result.getString(6), result.getString(5));
				output += "-".repeat(87);
				output += "\n\n";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;
	}

	public void enroll(String newStudentID, String newPassword, String newDepartment, String newName) {

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format(
					"INSERT INTO `user` (studentID, password, department, name) VALUES ('%s', '%s', '%s', '%s')",
					newStudentID, newPassword, newDepartment, newName);
			stat.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCourseIntoDatabase(ArrayList<String> courseIDs, String studentID) {

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			for (int i = 0; i < courseIDs.size(); i++) {
				var query = String.format("UPDATE `user` SET `courseID_%d` = '%s' WHERE `studentID` = '%s'", i + 1,
						courseIDs.get(i), studentID);
				stat.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCourse(int rowCount, String studentID) {

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("UPDATE `user` SET `courseID-%d` = 'NULL' WHERE `studentID` = '%s'", rowCount,
					studentID);
			stat.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void checkDataIsNull(int nextRow, String studentID) {

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM user WHERE `studentID` = '%s'", studentID);
			var result = stat.executeQuery(query);

			while (result.next()) {
				while (result.getString(nextRow + 4) != null) {
					// 檢查選取的下一列是否為 null
					String columnValue = result.getString(String.format("courseID-%d", nextRow));
					query = String.format("UPDATE `user` SET `courseID-%d` = '%s' WHERE `studentID` = '%s'",
							nextRow - 1, columnValue, studentID);
					stat.executeUpdate(query);
					query = String.format("UPDATE `user` SET `courseID-%d` = 'NULL' WHERE `studentID` = '%s'", nextRow,
							studentID);
					stat.executeUpdate(query);
					nextRow++;

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String[] classSelection_addAllCourse() {

		ArrayList<String> courses = new ArrayList<>();
		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `class`");
			ResultSet resultSet = stat.executeQuery(query);
			while (resultSet.next()) {
				String course = String.format(" %s,%s,%s,%s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4));
				courses.add(course);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] dataCourse = courses.toArray(new String[0]);
		return dataCourse;
	}

	public String[] classSelection_courseID(String courseID) {
		ArrayList<String> courses = new ArrayList<>();

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `class` WHERE `CourseID` LIKE '%%%s%%'", courseID);
			ResultSet resultSet = stat.executeQuery(query);
			while (resultSet.next()) {
				String course0 = String.format(" %s,%s,%s,%s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4));
				courses.add(course0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] dataCourse = courses.toArray(new String[0]);
		return dataCourse;
	}

	public String[] classSelection_course(String course) {
		ArrayList<String> courses = new ArrayList<>();

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `class` WHERE Course LIKE '%%%s%%'", course);
			ResultSet resultSet = stat.executeQuery(query);

			while (resultSet.next()) {
				String course1 = String.format(" %s,%s,%s,%s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4));
				courses.add(course1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] dataCourse = courses.toArray(new String[0]);
		return dataCourse;
	}

	public String[] classSelection_teacher(String teacher) {
		ArrayList<String> courses = new ArrayList<>();

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `class` WHERE `Teacher` LIKE '%%%s%%'", teacher);
			ResultSet resultSet = stat.executeQuery(query);

			while (resultSet.next()) {
				String course2 = String.format(" %s,%s,%s,%s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4));
				courses.add(course2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] dataCourse = courses.toArray(new String[0]);
		return dataCourse;
	}

	public String[] classSelection_time(String time) {
		ArrayList<String> courses = new ArrayList<>();

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT * FROM `class` WHERE Time LIKE '%%%s%%'", time);
			ResultSet resultSet = stat.executeQuery(query);

			while (resultSet.next()) {
				String course3 = String.format(" %s,%s,%s,%s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4));
				courses.add(course3);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] dataCourse = courses.toArray(new String[0]);
		return dataCourse;
	}

	public ArrayList<String> getUserCourse(String studentID) {

		ArrayList<String> userCourse_ID = new ArrayList<String>();

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format(
					"SELECT `courseID_1`, `courseID_2`, `courseID_3`, `courseID_4`, `courseID_5`, `courseID_6`, `courseID_7`, `courseID_8`,"
							+ " `courseID_9`, `courseID_10`, `courseID_11` FROM `user` WHERE `studentID` = '%s'", studentID);
			ResultSet resultSet = stat.executeQuery(query);
			int i = 1;
			while (resultSet.next()) {
				String userCourseID = resultSet.getString(i).strip();
				
				while(resultSet.getString(i) != null) {
					userCourseID = resultSet.getString(i).strip();
					userCourse_ID.add(userCourseID);
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userCourse_ID;
	}

	public String getCourseNameByCourseID(String courseID) {

		String courseName = "";

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT `Course` FROM `class` WHERE `CourseID` = '%s'", courseID);
			ResultSet resultSet = stat.executeQuery(query);

			while (resultSet.next()) {
				courseName = String.format("%s", resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseName;
	}
	
	public String getCourseIDByCourseName(String course) {

		String courseID = "";

		try (var conn = DriverManager.getConnection(url, username, password)) {
			var stat = conn.createStatement();
			var query = String.format("SELECT `CourseID` FROM `class` WHERE `course` = '%s'", course);
			ResultSet resultSet = stat.executeQuery(query);

			while (resultSet.next()) {
				courseID = String.format("%s", resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseID;
	}
}
