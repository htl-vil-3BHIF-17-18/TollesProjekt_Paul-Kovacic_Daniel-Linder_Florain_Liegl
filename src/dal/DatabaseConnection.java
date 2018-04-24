package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

import bll.*;

public class DatabaseConnection {
    private String username;
    private String password;

    public DatabaseConnection(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@192.168.128.152");
    }

    public void createTaskTable() {
        Connection con = null;
        Statement stmtCreate;
        try {
            con = createConnection();
            stmtCreate = con.createStatement();
            stmtCreate.execute("CREATE TABLE task (" +
                    "done VARCHAR2(1)," +
                    "    category VARCHAR2(20)," +
                    "    subject VARCHAR2(20)," +
                    "    description VARCHAR2(50)," +
                    "    von DATE," +
                    "    until DATE," +
                    "    CONSTRAINT pkTask PRIMARY KEY (category, subject, von)," +
                    "    CONSTRAINT ckDatum CHECK (von <= until)," +
                    "    CONSTRAINT ckErledigt CHECK (upper(done) LIKE 'Y' OR upper(done) LIKE 'N')" +
                    ");");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Connection con = null;
        try {
            con = this.createConnection();
            Statement stmtSelect = con.createStatement();
            ResultSet rs = stmtSelect.executeQuery("SELECT * FROM task");

            while (rs.next()) {
                tasks.add(new Task(rs.getString(1) == "Y", Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), rs.getDate(5), rs.getDate(6)));
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
            stmtSelect.executeQuery("SELECT * FROM task WHERE " + tableCol + " LIKE \'" + filter + "\';");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tasks;
    }

    public void addEntry(Task task) {
        try {
            Connection con = this.createConnection();
            Statement stmtInsert = con.createStatement();
            stmtInsert.executeQuery("INSERT INTO task VALUES (" + task.toString() + ");");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeEntry(Task task) {
        try {
            Connection con = this.createConnection();
            Statement stmtDelete = con.createStatement();
            stmtDelete.executeQuery("DELETE * FROM task WHERE category LIKE " + task.getCategorie().toString() + " AND subject LIKE " + task.getSubject().toString() + " AND description LIKE " + task.getDescription() + " AND von = " + task.getFrom() + " AND until = " + task.getUntil() + ";");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void renewEntry(Task oldTask, Task newTask) {
        removeEntry(oldTask);
        addEntry(newTask);
    }
}
