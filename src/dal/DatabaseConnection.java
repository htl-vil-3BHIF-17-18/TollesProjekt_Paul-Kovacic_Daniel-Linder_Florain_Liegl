package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import bll.*;

public class DatabaseConnection {
	String username = null;
	String password = null;
	String targetDB = null;
	Connection con = null;
	
    public DatabaseConnection(String username, String password, String targetDB) {
		super();
		this.username = username;
		this.password = password;
		this.targetDB = targetDB;
	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@" + this.targetDB);
    }
	
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		try {
			Connection con = this.createConnection();
			Statement stmtSelect = con.createStatement();
			ResultSet rs = stmtSelect.executeQuery("SELECT * FROM task");
			
			while(rs.next()) {
				tasks.add(new Task(Categories.valueOf(rs.getString(1)), Subjects.valueOf(rs.getString(2)), rs.getString(3), rs.getDate(4), rs.getDate(5)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tasks;
	}
	
	public List<Task> getFilteredTasks(String tableCol, String filter) {
		List<Task> tasks = new ArrayList<>();
		try {
			Connection con = this.createConnection();
			Statement stmtSelect = con.createStatement();
			stmtSelect.executeQuery("SELECT * FROM task WHERE " + tableCol + " LIKE \'" + filter + "\'");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}
	
	public void syncToDatabase() {
		try {
			Connection con = this.createConnection();
			//TODO: Sync with database (maybe this was obvious)
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
