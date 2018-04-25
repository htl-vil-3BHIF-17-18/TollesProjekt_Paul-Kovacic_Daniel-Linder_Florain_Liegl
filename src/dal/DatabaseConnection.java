package dal;

import java.sql.*;
import java.sql.Date;
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
        return DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@192.168.128.152:1521:ora11g");
    }

    public void createTaskTable() {
        Connection con = null;
        Statement stmtCreate;
        try {
            con = createConnection();
            stmtCreate = con.createStatement();
            stmtCreate.execute("CREATE TABLE task (" +
                    "done VARCHAR2(1)," +
                    "category VARCHAR2(20)," +
                    "subject VARCHAR2(20)," +
                    "description VARCHAR2(50)," +
                    "von DATE," +
                    "until DATE," +
                    "CONSTRAINT pkTask PRIMARY KEY (category, subject, von)," +
                    "CONSTRAINT ckDatum CHECK (von <= until)," +
                    "CONSTRAINT ckErledigt CHECK (upper(done) LIKE 'Y' OR upper(done) LIKE 'N')" +
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
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtInsert = con.prepareStatement("INSERT INTO task (id, done, category, subject, description, von, until) VALUES (idTask.NEXTVAL,?,?,?,?,?,?);");
            stmtInsert.setString(1, task.isDone() ? "Y" : "N");
            stmtInsert.setString(2, task.getCategorie().toString());
            stmtInsert.setString(3, task.getSubject().toString());
            stmtInsert.setString(4, task.getDescription());
            stmtInsert.setDate(5, task.getFrom());
            stmtInsert.setDate(6, task.getUntil());
            con.commit();
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

    public void removeEntry(Task task) {
        Connection con = null;
        try {
            con = this.createConnection();
            Statement stmtDelete = con.createStatement();
            stmtDelete.execute("DELETE FROM task WHERE id = " + task.getId() + ";");
            con.close();
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

    public void updateEntry(Task oldTask, Task newTask) {
        removeEntry(oldTask);
        addEntry(newTask);
    }

    public void checkTaskTable() {
        try {
            Connection con = this.createConnection();
            DatabaseMetaData metadata = con.getMetaData();
            ResultSet rs = metadata.getTables(null, null, "TASK", new String[] {"TABLE"});
            rs.getRow();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}