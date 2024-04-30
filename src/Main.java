import java.sql.*;

public class Main {

	public static void main(String[] args) {

		var url = "jdbc:mysql://140.119.19.73:3315/111306071";
		var username = "111306071";
		var password = "r5iwz";

		var db = new DBAccesser();
		db.setInfo(url, username, password);

		new LoginPage(db);
	}
}