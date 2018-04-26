package dal;

import java.sql.*;
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
        return DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@192.168.128.152:1521:ora11g"); //212.152.179.117
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
                tasks.add(new Task(rs.getString(1).equals("Y"), Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), rs.getDate(5), rs.getDate(6)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
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
            e.printStackTrace();
        }
        return tasks;
    }

    public void addEntry(Task task) {
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtInsert = con.prepareStatement("INSERT INTO task (done, category, subject, description, von, until) VALUES (?,?,?,?,?,?);");
            stmtInsert.setString(1, task.isDone() ? "Y" : "N");
            stmtInsert.setString(2, task.getCategorie().toString());
            stmtInsert.setString(3, task.getSubject().toString());
            stmtInsert.setString(4, task.getDescription());
            stmtInsert.setDate(5, convertDate(this.convertDate(task.getFrom())));
            stmtInsert.setDate(6, convertDate(this.convertDate(task.getUntil())));
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
            PreparedStatement stmtDelete = con.prepareStatement("DELETE FROM task WHERE category LIKE ? AND subject LIKE ? AND von = ?;");
            stmtDelete.setString(1, task.getCategorie().toString());
            stmtDelete.setString(2, task.getSubject().toString());
            stmtDelete.setDate(3, this.convertDate(task.getFrom()));
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
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtDelete = con.prepareStatement("UPDATE task SET done LIKE ?, category LIKE ?, subject LIKE ?, description LIKE ?, von = ?, until = ? WHERE category LIKE ? AND subject LIKE ? AND von = ?;");
            stmtDelete.setString(1, newTask.isDone() ? "Y" : "N");
            stmtDelete.setString(2, newTask.getCategorie().toString());
            stmtDelete.setString(3, newTask.getSubject().toString());
            stmtDelete.setString(4, newTask.getDescription());
            stmtDelete.setDate(5, this.convertDate(newTask.getFrom()));
            stmtDelete.setDate(6, this.convertDate(newTask.getUntil()));
            stmtDelete.setString(7, oldTask.getCategorie().toString());
            stmtDelete.setString(8, oldTask.getSubject().toString());
            stmtDelete.setDate(9, this.convertDate(oldTask.getFrom()));
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

    public boolean checkConnection() {
        Connection con = null;
        boolean connnected = true;
        try {
            con = this.createConnection();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            connnected = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connnected;

    }

    private java.sql.Date convertDate(java.util.Date utilDate) {
        java.sql.Date sqlDate = null;
        System.out.print(utilDate.toString());
        return sqlDate;
    }
}